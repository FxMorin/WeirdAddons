package weirdaddons.mixins;

import carpet.helpers.TickSpeed;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.utils.BlockEventManager;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_blockEventMixin extends World {

    @Shadow @Final private ObjectLinkedOpenHashSet<BlockEvent> syncedBlockEventQueue;
    @Shadow @Final private MinecraftServer server;

    @Shadow private boolean processBlockEvent(BlockEvent event) {return false;}

    protected ServerWorld_blockEventMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);}


    @Inject(
            method = "processSyncedBlockEvents()V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void processSyncedBlockEvents(CallbackInfo ci) {
        if(BlockEventManager.isFrozen()) {
            ci.cancel();
        }
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(BooleanSupplier keepTicking, CallbackInfo ci) {
        if(TickSpeed.isPaused() && BlockEventManager.isFrozen()) {
            if(!syncedBlockEventQueue.isEmpty()) {
                if (BlockEventManager.doStep()) {
                    BlockEvent blockEvent = this.syncedBlockEventQueue.removeFirst();
                    if (this.processBlockEvent(blockEvent)) {
                        this.server.getPlayerManager().sendToAround(null, blockEvent.getPos().getX(), blockEvent.getPos().getY(), blockEvent.getPos().getZ(), 64.0D, this.getRegistryKey(), new BlockEventS2CPacket(blockEvent.getPos(), blockEvent.getBlock(), blockEvent.getType(), blockEvent.getData()));
                    }
                    if (this.syncedBlockEventQueue.isEmpty()) {
                        BlockEventManager.finishTick();
                    }
                }
            }
        }
    }
}
