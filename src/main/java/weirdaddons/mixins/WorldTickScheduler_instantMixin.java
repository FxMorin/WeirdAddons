package weirdaddons.mixins;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.tick.WorldTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import weirdaddons.WeirdAddonsSettings;

import java.util.function.BiConsumer;

@Mixin(WorldTickScheduler.class)
public abstract class WorldTickScheduler_instantMixin {

    @Shadow public abstract int getTickCount();

    @Shadow public abstract <T> void tick(long time, int maxTicks, BiConsumer<BlockPos, T> ticker);

    @ModifyArgs(
            method = "tick(JILjava/util/function/BiConsumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/tick/WorldTickScheduler;collectTickableTicks(JILnet/minecraft/util/profiler/Profiler;)V"
            )
    )
    private void tickAllChunkTickSchedulers(Args args) {
        boolean isInstant = ((WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks));
        if (isInstant) {
            args.set(0,Long.MAX_VALUE);
            args.set(1,Integer.MAX_VALUE);
        }
    }


    @Inject(
            method = "tick(JILjava/util/function/BiConsumer;)V",
            at = @At("RETURN")
    )
    public <T> void tick(long time, int maxTicks, BiConsumer<BlockPos, T> ticker, CallbackInfo ci) {
        if ((WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks)) {
            if (this.getTickCount() > 0) this.tick(time,maxTicks,ticker);
        }
    }
}
