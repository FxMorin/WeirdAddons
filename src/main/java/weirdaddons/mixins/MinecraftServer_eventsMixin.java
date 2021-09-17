package weirdaddons.mixins;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsServer;

@Mixin(MinecraftServer.class)
public class MinecraftServer_eventsMixin {

    @Inject(method= "shutdown()V",at=@At("HEAD"))
    public void shutdownEvent(CallbackInfo ci) {
        WeirdAddonsServer.onShutdown();
    }
}
