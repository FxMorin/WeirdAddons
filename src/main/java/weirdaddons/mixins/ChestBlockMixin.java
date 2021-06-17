package weirdaddons.mixins;

import net.minecraft.block.ChestBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin {

    @Inject(method = "hasOcelotOnTop(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private static void isCatOnChestBypass(WorldAccess world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.catOnChestBypass) {
            cir.setReturnValue(false);
        }
    }
}
