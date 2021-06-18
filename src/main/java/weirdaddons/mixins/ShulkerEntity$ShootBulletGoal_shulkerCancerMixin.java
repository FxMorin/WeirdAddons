package weirdaddons.mixins;

import carpet.CarpetServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

import java.util.EnumSet;
import java.util.Random;

@Mixin(targets = "net.minecraft.entity.mob.ShulkerEntity$ShootBulletGoal")
public abstract class ShulkerEntity$ShootBulletGoal_shulkerCancerMixin extends Goal {

    /*@Redirect(method= "tick()V",at=@At(value="INVOKE",target="Ljava/util/Random;nextInt(I)I"))
    public int shootFaster(Random random, int bound) {
        if (WeirdAddonsSettings.shulkerCancer) {

            int i = counter. getEntitiesByType(EntityType.SHULKER, box.expand(8.0D), Entity::isAlive).size();
        }
        return random.nextInt(10) + (WeirdAddonsSettings.shulkerCancer ? );
    }*/
}
