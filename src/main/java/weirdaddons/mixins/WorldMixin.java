package weirdaddons.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

@Mixin(World.class)
public class WorldMixin {

    @ModifyArg(method = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z"), index = 2)
    private int adjustFlags(int flags) {
        return (WeirdAddonsSettings.blockUpdateHell > -2) ? WeirdAddonsSettings.blockUpdateHell : flags;
    }
}
