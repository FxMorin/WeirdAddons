package weirdaddons.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {

    private static boolean isPushableBlockEntity(Block block) { //Overrides Carpet's isPushableBlockEntity()
        //Making PISTON_EXTENSION (BlockPistonMoving) pushable would not work as its createNewTileEntity()-method returns null
        return block != Blocks.ENDER_CHEST && block != Blocks.ENCHANTING_TABLE &&
                block != Blocks.END_GATEWAY && block != Blocks.END_PORTAL &&
                block != Blocks.SPAWNER && (WeirdAddonsSettings.movableMovingPiston ||  block != Blocks.MOVING_PISTON);
    }

    @Inject(method = "isMovable", at = @At( value = "INVOKE", target ="Lnet/minecraft/block/BlockState;getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"), cancellable = true)
    private static void movingPistonIsHard(BlockState blockState, World world, BlockPos blockPos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.movableMovingPiston && blockState.isOf(Blocks.MOVING_PISTON)) {
            boolean valid = true;
            if (direction == pistonDir && direction == blockState.get(Properties.FACING)) {
                BlockEntity be = world.getBlockEntity(blockPos);
                if (be instanceof PistonBlockEntity) {
                    PistonBlockEntity pistonBlockEntity = (PistonBlockEntity) be;
                    valid = !(pistonBlockEntity.isSource() && pistonBlockEntity.isExtending());
                }
            }
            cir.setReturnValue(valid);
        }
    }
}
