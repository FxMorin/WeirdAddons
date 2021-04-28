package weirdaddons.mixins;

import com.google.common.collect.Sets;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.TickPriority;
import net.minecraft.world.TickScheduler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.Set;
import java.util.function.Predicate;

@Mixin(ServerTickScheduler.class)
public class ServerTickSchedulerMixin<T> implements TickScheduler<T> {

    @Shadow @Final private Set<ScheduledTick<T>> scheduledTickActions = Sets.newHashSet();
    @Shadow @Final protected Predicate<T> invalidObjPredicate;
    @Shadow @Final private ServerWorld world;

    @Shadow public boolean isScheduled(BlockPos pos, T object) { return false;}
    @Shadow public boolean isTicking(BlockPos pos, T object) { return false;}
    @Shadow private void addScheduledTick(ScheduledTick<T> scheduledTick) {}
    @Shadow public void tick() {}

    @Override
    public void schedule(BlockPos pos, T object, int delay, TickPriority priority) {
        if (!this.invalidObjPredicate.test(object)) {
            this.addScheduledTick(new ScheduledTick(pos, object, ((WeirdAddonsSettings.fastTileTicks && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.fastLiquidFlow && !WeirdAddonsSettings.insideBlockTicks)) ? 0L : (long)delay + this.world.getTime(), priority));
        }
    }

    @Redirect(method = "Lnet/minecraft/server/world/ServerTickScheduler;tick()V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;getTime()J"
    ))
    public long CaptainAreYouSureAboutThis(ServerWorld serverWorld) {
        return ((WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks)) ? Integer.MAX_VALUE : world.getTime();
    }

    @Redirect(method = "Lnet/minecraft/server/world/ServerTickScheduler;tick()V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerChunkManager;shouldTickBlock(Lnet/minecraft/util/math/BlockPos;)Z"
    ))
    public boolean OfCourseYouCanTickThatBlock(ServerChunkManager serverChunkManager, BlockPos pos) {
        return (WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks) || serverChunkManager.shouldTickBlock(pos);
    }

    @Inject(method = "Lnet/minecraft/server/world/ServerTickScheduler;tick()V", at = @At(
            value = "RETURN"))
    public void InjectIntoTick(CallbackInfo ci) {
        if (((WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks)) && this.scheduledTickActions.size() != 0) {
            this.tick();
        }
        WeirdAddonsSettings.insideBlockTicks = false;
    }
}
