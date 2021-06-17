package weirdaddons.mixins;

import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import weirdaddons.WeirdAddonsSettings;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {
    @ModifyConstant(method = "shootSkullAt(ILnet/minecraft/entity/LivingEntity;)V", constant = @Constant(floatValue = 0.001F))
    private float higherEntityChance(float original) {
        return (WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ENTITY || WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ALL) ? 1F : original;
    }

    @ModifyConstant(method = "mobTick()V", constant = @Constant(intValue = 15))
    private int higherPassiveChance(int original) {
        return (WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.PASSIVE || WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ALL) ? 0 : original;
    }
}
