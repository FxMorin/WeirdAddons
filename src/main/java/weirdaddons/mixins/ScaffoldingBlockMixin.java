package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.Random;

@Mixin(ScaffoldingBlock.class)
class ScaffoldingBlockMixin extends Block{

    @Shadow public static final BooleanProperty WATERLOGGED;

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public ScaffoldingBlockMixin(Settings settings) {
        super(settings);
    }


    @Redirect(method = "scheduledTick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
    ))
    public boolean ScaffoldingBreaking(ServerWorld serverWorld, BlockPos pos, boolean drop) {
        if (WeirdAddonsSettings.scaffoldingBreaking.equals("float")) {
            return true;
        } else if (WeirdAddonsSettings.scaffoldingBreaking.equals("gravity")) {
            return serverWorld.spawnEntity(new FallingBlockEntity(serverWorld, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D,serverWorld.getBlockState(pos).with(WATERLOGGED, false)));
        } else {
            return serverWorld.breakBlock(pos, true);
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    public void SuppressTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (WeirdAddonsSettings.scaffoldingBreaking.equals("float")) {
            ci.cancel();
        }
    }
}
