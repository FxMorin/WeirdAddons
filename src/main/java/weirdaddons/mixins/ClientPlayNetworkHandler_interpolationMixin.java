package weirdaddons.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import weirdaddons.WeirdAddonsSettings;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandler_interpolationMixin {


    @ModifyArg(
            method = "onEntityPosition(Lnet/minecraft/network/packet/s2c/play/EntityPositionS2CPacket;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFIZ)V"
            ),
            index = 6
    )
    public boolean onEntityPosition(boolean shouldInterpolate) {
        return !WeirdAddonsSettings.disableEntityInterpolation;
    }
}
