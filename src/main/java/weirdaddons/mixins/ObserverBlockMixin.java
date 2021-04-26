package weirdaddons.mixins;

import net.minecraft.block.ObserverBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ObserverBlock.class)
public class ObserverBlockMixin {

    @ModifyArg(method = "Lnet/minecraft/block/ObserverBlock;scheduleTick(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/TickScheduler;schedule(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;I)V"), index = 2)
    private int adjustDelay(int delay) {
        return WeirdAddonsSettings.observerDelay;
    }

    //@ModifyArg(method = "Lnet/minecraft/block/ObserverBlock;scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/TickScheduler;schedule(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;I)V"), index = 2)
    //private int adjustDelayAlso(int delay) {
    //    return WeirdAddonsSettings.observerPulse;
    //}
}
