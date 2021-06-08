package weirdaddons.mixins;

import carpet.CarpetServer;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsServer;
import weirdaddons.WeirdAddonsSettings;


import java.util.Random;

@Mixin(FallingBlock.class)
public class FallingBlockMixin extends Block {
    public FallingBlockMixin(Settings settings) {
        super(settings);
    }
    @Shadow public static boolean canFallThrough(BlockState state){
        return true;
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    public void onTryInstantFall(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (WeirdAddonsSettings.instantFall) {
            if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
                world.setBlockState(pos,Blocks.AIR.getDefaultState());
                BlockPos blockpos;
                int minY = WeirdAddonsServer.isCarpetExtraLoaded ? (CarpetServer.settingsManager.getRule("y0DragonEggBedrockBreaking").getBoolValue() ? -1 : 0) : 0;
                for (blockpos = pos.down(); canFallThrough(world.getBlockState(blockpos)) && blockpos.getY() > minY; blockpos = blockpos.down()) {}
                if (blockpos.getY() > minY) {
                    world.setBlockState(state.isOf(Blocks.DRAGON_EGG) ? blockpos : blockpos.up(),this.getDefaultState(),2);
                }
            }
            ci.cancel();
        }
    }
}
