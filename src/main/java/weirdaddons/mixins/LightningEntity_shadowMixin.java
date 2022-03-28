package weirdaddons.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

@Mixin(LightningEntity.class)
public class LightningEntity_shadowMixin {


    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isAlive()Z"
            )
    )
    private boolean isAliveOrItemFrame(Entity entity) {
        if (WeirdAddonsSettings.itemShadowingMechanic == WeirdAddonsSettings.itemShadowingMechanics.LIGHTNING &&
                entity instanceof ItemFrameEntity) return true;
        return entity.isAlive();
    }
}
