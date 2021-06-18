package weirdaddons.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(LivingEntity.class)
public class LivingEntity_shulkerCancerMixin {
    @Inject(method= "canTarget(Lnet/minecraft/entity/LivingEntity;)Z",at=@At("HEAD"),cancellable = true)
    public void canTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.shulkerCancer && target instanceof ShulkerEntity) cir.setReturnValue(true);
    }

    @Inject(method= "isTarget(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/ai/TargetPredicate;)Z",at=@At("HEAD"),cancellable = true)
    public void isTarget(LivingEntity entity, TargetPredicate predicate, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.shulkerCancer && entity instanceof ShulkerEntity) cir.setReturnValue(true);
    }
}
