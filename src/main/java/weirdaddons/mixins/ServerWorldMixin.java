package weirdaddons.mixins;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {


    @Inject(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/tick/WorldTickScheduler;tick(JILjava/util/function/BiConsumer;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    public void BeforeBlockTicks(CallbackInfo ci) {
        WeirdAddonsSettings.insideBlockTicks = true;
    }


    @Inject(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/tick/WorldTickScheduler;tick(JILjava/util/function/BiConsumer;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 1
            )
    )
    public void BeforeFluidTicks(CallbackInfo ci) {
        WeirdAddonsSettings.insideBlockTicks = false;
    }


    @Redirect(method = "isTickingFutureReady(J)Z", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerChunkManager;isTickingFutureReady(J)Z"
    ))
    public boolean OfCourseYouCanTickThatBlock(ServerChunkManager serverChunkManager, long l) {
        return (WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks) || serverChunkManager.isTickingFutureReady(l);
    }
}
