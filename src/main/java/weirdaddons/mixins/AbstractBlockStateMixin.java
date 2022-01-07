package weirdaddons.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Shadow @Final private AbstractBlock.ContextPredicate solidBlockPredicate;
    @Shadow protected BlockState asBlockState() { return null; }

    @Inject(method = "isSolidBlock", at = @At(value = "HEAD"), cancellable = true)
    private void whatIsConsideredASolidBlock(BlockView world, BlockPos pos,CallbackInfoReturnable<Boolean> cir){
        if (WeirdAddonsSettings.blockTransparency != WeirdAddonsSettings.BlockTransparencyEnum.NORMAL) {
            if (world.getBlockState(pos).isAir()) {
                cir.setReturnValue(false);
            } else if (WeirdAddonsSettings.blockTransparency == WeirdAddonsSettings.BlockTransparencyEnum.SOLID) {
                cir.setReturnValue(true);
            } else if (WeirdAddonsSettings.blockTransparency == WeirdAddonsSettings.BlockTransparencyEnum.TRANSPARENT) {
                cir.setReturnValue(false);
            } else if (WeirdAddonsSettings.blockTransparency == WeirdAddonsSettings.BlockTransparencyEnum.INVERSE) {
                cir.setReturnValue(!this.solidBlockPredicate.test(this.asBlockState(), world, pos));
            }
        }
    }
}
