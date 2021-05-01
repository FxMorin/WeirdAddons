package weirdaddons.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.EnchantCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

import java.util.Collection;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {

    @Redirect(method = "Lnet/minecraft/server/command/EnchantCommand;execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;I)I", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"
    ))
    private static int enchantmentOverride(Enchantment enchantment) {
        return WeirdAddonsSettings.enchantmentOverride ? Integer.MAX_VALUE : enchantment.getMaxLevel();
    }

    @Redirect(method = "Lnet/minecraft/server/command/EnchantCommand;execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;I)I", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z"
    ))
    private static boolean acceptableOverride(Enchantment enchantment, ItemStack stack) {
        return WeirdAddonsSettings.enchantmentOverride || enchantment.isAcceptableItem(stack);
    }

    @Redirect(method = "Lnet/minecraft/server/command/EnchantCommand;execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;I)I", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;isCompatible(Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;)Z"
    ))
    private static boolean compatibleOverride(Collection<Enchantment> existing, Enchantment candidate) {
        return WeirdAddonsSettings.enchantmentOverride || EnchantmentHelper.isCompatible(existing, candidate);
    }

}
