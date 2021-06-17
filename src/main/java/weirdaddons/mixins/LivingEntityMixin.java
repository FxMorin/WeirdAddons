package weirdaddons.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Redirect(method = "tickFallFlying()V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"
    ))
    public <T extends LivingEntity> void damageElytra(ItemStack itemStack, int amount, T entity, Consumer<T> breakCallback) {
        if (!WeirdAddonsSettings.totallyLegitElytra) {
            itemStack.damage(amount, entity, breakCallback);
        }
    }

}
