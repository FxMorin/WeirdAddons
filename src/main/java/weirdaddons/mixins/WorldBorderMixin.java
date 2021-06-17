package weirdaddons.mixins;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {

    @Inject(method = "contains(Lnet/minecraft/util/math/BlockPos;)Z", at = @At(value = "INVOKE"), cancellable = true)
    public void contains(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.worldborderSpecial) { cir.setReturnValue(true); }
    }

    @Inject(method = "contains(Lnet/minecraft/util/math/ChunkPos;)Z", at = @At(value = "INVOKE"), cancellable = true)
    public void contains(ChunkPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.worldborderSpecial) { cir.setReturnValue(true); }
    }

    @Inject(method = "contains(Lnet/minecraft/util/math/Box;)Z", at = @At(value = "INVOKE"), cancellable = true)
    public void contains(Box box, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.worldborderSpecial) { cir.setReturnValue(true); }
    }
}
