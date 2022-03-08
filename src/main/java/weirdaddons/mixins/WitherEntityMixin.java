package weirdaddons.mixins;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {


    @ModifyConstant(
            method = "shootSkullAt(ILnet/minecraft/entity/LivingEntity;)V",
            constant = @Constant(floatValue = 0.001F)
    )
    private float higherEntityChance(float original) {
        return (WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ENTITY ||
                WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ALL) ?
                1F :
                original;
    }


    @ModifyConstant(
            method = "mobTick()V",
            constant = @Constant(intValue = 15)
    )
    private int higherPassiveChance(int original) {
        return (WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.PASSIVE ||
                WeirdAddonsSettings.blueSkullTesting == WeirdAddonsSettings.blueSkullTest.ALL) ?
                0 :
                original;
    }


    @Redirect(
            method = "mobTick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"
            )
    )
    private Difficulty modifyDifficulty(World instance) {
        return WeirdAddonsSettings.easyWither ? Difficulty.EASY : instance.getDifficulty();
    }
}
