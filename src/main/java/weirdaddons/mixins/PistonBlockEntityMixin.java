package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(PistonBlockEntity.class)
public abstract class PistonBlockEntityMixin extends BlockEntity {
    @Shadow private float progress;
    @Shadow private boolean source;
    @Shadow private BlockState pushedBlock;
    @Shadow private boolean extending;
    @Shadow private Direction facing;
    @Shadow @Final private static final ThreadLocal<Direction> field_12205 = ThreadLocal.withInitial(() -> null);

    public PistonBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }


    @Shadow public Direction getMovementDirection() { return Direction.UP; }
    @Shadow private float getAmountExtended(float progress) { return 0.0f; }

    @Inject(method = "tick", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1, target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private static void onTickInjectBeforeSetBlockState(World world, BlockPos pos, BlockState state, PistonBlockEntity blockEntity, CallbackInfo ci) {
        if (blockEntity.getPushedBlock().isOf(Blocks.MOVING_PISTON)) { // This makes sure the block entity is placed properly
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 16);
        }
    }

    @Redirect(method = "tick", at = @At(value = "FIELD",target="Lnet/minecraft/world/World;isClient:Z"))
    private static boolean IAmAClientNow(World world) {
        return WeirdAddonsSettings.fastPistons || world.isClient;
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "INVOKE"), cancellable = true)
    public void getCollisionShape(BlockView world, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (WeirdAddonsSettings.movableMovingPiston) {
            VoxelShape voxelShape2 = (!this.extending && this.source) ? (this.pushedBlock.with(PistonBlock.EXTENDED, true)).getCollisionShape(world, pos) : VoxelShapes.empty();
            Direction direction = field_12205.get();
            if ((double) this.progress < 1.0D && direction == this.getMovementDirection()) {
                cir.setReturnValue(voxelShape2);
            } else {
                BlockState blockState2 = this.source ? (Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, this.facing)).with(PistonHeadBlock.SHORT, this.extending != 1.0F - this.progress < 0.25F) : this.pushedBlock;
                float f = this.getAmountExtended(this.progress);
                double d = ((float) this.facing.getOffsetX() * f);
                double e = ((float) this.facing.getOffsetY() * f);
                double g = ((float) this.facing.getOffsetZ() * f);
                if (blockState2.isOf(Blocks.MOVING_PISTON)) {
                    cir.setReturnValue(VoxelShapes.union(voxelShape2, VoxelShapes.fullCube()));
                } else {
                    cir.setReturnValue(VoxelShapes.union(voxelShape2, blockState2.getCollisionShape(world, pos).offset(d, e, g)));
                }
            }
        }
    }
}
