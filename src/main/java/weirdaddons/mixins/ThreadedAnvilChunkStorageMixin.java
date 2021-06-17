package weirdaddons.mixins;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.function.BooleanSupplier;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin {

    @Inject(method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V", at = @At("HEAD"), cancellable = true)
    private void chunkUnloadingWhatsThat(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (WeirdAddonsSettings.permaloader && shouldKeepTicking.getAsBoolean()) {
            ci.cancel();
        }
    }
}
