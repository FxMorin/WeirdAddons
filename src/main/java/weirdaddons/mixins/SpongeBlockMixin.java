package weirdaddons.mixins;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import weirdaddons.WeirdAddonsSettings;

import java.util.Queue;
import java.util.Random;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin extends Block {

    public SpongeBlockMixin(Settings settings) {super(settings);}

    @Redirect(method = "Lnet/minecraft/block/SpongeBlock;update(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/SpongeBlock;absorbWater(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"
    ))
    private boolean absorbEverything(SpongeBlock spongeBlock, World world, BlockPos pos) {
        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos = pair.getLeft();
            int j = pair.getRight();
            Direction[] var8 = Direction.values();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Direction direction = var8[var10];
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);
                Material material = blockState.getMaterial();
                if (WeirdAddonsSettings.spongeEverything || fluidState.isIn(FluidTags.WATER)) {
                    if (!WeirdAddonsSettings.spongeEverything && blockState.getBlock() instanceof FluidDrainable && ((FluidDrainable)blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState) != Fluids.EMPTY) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    } else if (WeirdAddonsSettings.spongeEverything || blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    } else if (material == Material.UNDERWATER_PLANT || material == Material.REPLACEABLE_UNDERWATER_PLANT) {
                        BlockEntity blockEntity = blockState.getBlock().hasBlockEntity() ? world.getBlockEntity(blockPos2) : null;
                        dropStacks(blockState, world, blockPos2, blockEntity);
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > WeirdAddonsSettings.spongeLimit) {
                break;
            }
        }

        return i > 0;
    }

    @ModifyConstant(method = "update", constant = @Constant(intValue = 2), require = 0)
    private int spongeUpdate(int oldValue) {
        return WeirdAddonsSettings.spongeUpdate ? 3 : 2;
    }
}
