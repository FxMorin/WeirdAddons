package weirdaddons.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(DragonEggBlock.class)
public abstract class DragonEggBlockMixin {

    @Inject(method = "onBlockBreakStart(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V",at = @At("INVOKE"), cancellable = true)
    private void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player, CallbackInfo info) {
        if (WeirdAddonsSettings.breakableDragonEgg) { info.cancel(); }
    }
}
