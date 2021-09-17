package weirdaddons.mixins;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("HEAD"))
    public void BeforeBlockTicks(CallbackInfo ci) {
        WeirdAddonsSettings.insideBlockTicks = true;
    }

    @Redirect(method = "method_37117(Lnet/minecraft/util/math/BlockPos;)Z", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerChunkManager;method_37114(J)Z"
    ))
    public boolean OfCourseYouCanTickThatBlock(ServerChunkManager serverChunkManager, long l) {
        return (WeirdAddonsSettings.instantTileTick && WeirdAddonsSettings.insideBlockTicks) || (WeirdAddonsSettings.instantLiquidFlow && !WeirdAddonsSettings.insideBlockTicks) || serverChunkManager.method_37114(l);
    }
}
