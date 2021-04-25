package weirdaddons.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsServer;
import weirdaddons.WeirdAddonsSettings;
import weirdaddons.WeirdAddonsUtils;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
    @Shadow public ClientWorld world;

    @Inject(at = @At("HEAD"), method = "tick")
    private void onClientTick(CallbackInfo info) {
        if (this.world != null) {
            if (WeirdAddonsSettings.lampChunkStatus > 0 && WeirdAddonsSettings.lampChunkDisplay.equals("lamp") && WeirdAddonsServer.mc.player != null && WeirdAddonsServer.mc.player.inventory.getMainHandStack().getName().getString().equals("Redstone Lamp")) {
                WeirdAddonsUtils.largeChatHud(true);
            } else {
                WeirdAddonsUtils.largeChatHud(false);
            }
        }
    }
}