package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.Random;

@Mixin(FallingBlock.class)
public class FallingBlockMixin extends Block {
    public FallingBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public static boolean canFallThrough(BlockState state){
        return true;
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    public void onTryInstantFall(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (WeirdAddonsSettings.instantFall) {
            if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
                world.setBlockState(pos,Blocks.AIR.getDefaultState());
                BlockPos blockpos;
                for (blockpos = pos; canFallThrough(world.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down()) {}
                if (blockpos.getY() > 0) {
                    world.setBlockState(state.isOf(Blocks.DRAGON_EGG) ? blockpos : blockpos.up(),this.getDefaultState(),2);
                }
            }
            ci.cancel();
        }
    }

    @Shadow
    protected void configureFallingBlockEntity(FallingBlockEntity fallingBlockEntity) {
    }
}
