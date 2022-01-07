package weirdaddons.mixins;

import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import weirdaddons.WeirdAddonsSettings;

@Mixin(WorldAccess.class)
public interface WorldAccess_fastTickMixin {

    @ModifyArg(
            method = "createAndScheduleBlockTick*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;createOrderedTick(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;ILnet/minecraft/world/TickPriority;)Lnet/minecraft/world/tick/OrderedTick;"
            ),
            index = 2
    )
    private int fastBlockTick(int delay) {
        return WeirdAddonsSettings.fastTileTicks ? 1 : delay;
    }

    @ModifyArg(
            method = "createAndScheduleFluidTick*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;createOrderedTick(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;ILnet/minecraft/world/TickPriority;)Lnet/minecraft/world/tick/OrderedTick;"
            ),
            index = 2
    )
    private int fastFluidTick(int delay) {
        return WeirdAddonsSettings.fastLiquidFlow ? 1 : delay;
    }
}
