package weirdaddons.mixins;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import weirdaddons.WeirdAddonsSettings;

@Mixin(World.class)
public abstract class WorldMixin {

    @ModifyArg(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z"), index = 2)
    private int adjustFlags(int flags) {
        return (WeirdAddonsSettings.blockUpdateHell > -2) ? WeirdAddonsSettings.blockUpdateHell : flags;
    }
}
