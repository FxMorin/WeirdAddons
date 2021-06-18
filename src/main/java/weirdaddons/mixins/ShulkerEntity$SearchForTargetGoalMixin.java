package weirdaddons.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import weirdaddons.WeirdAddonsSettings;

import java.util.function.Predicate;

@Mixin(targets = "net.minecraft.entity.mob.ShulkerEntity$SearchForTargetGoal")
public abstract class ShulkerEntity$SearchForTargetGoalMixin extends FollowTargetGoal<LivingEntity>  {

    public ShulkerEntity$SearchForTargetGoalMixin(MobEntity mob, Class<LivingEntity> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate.or((entity) -> {
                return (WeirdAddonsSettings.shulkerCancer) && entity instanceof ShulkerEntity;
        }));
    }

    @Override
    public boolean canStart() {
        if (WeirdAddonsSettings.shulkerCancer) {
            return super.canStart();
        }
        return this.mob.getScoreboardTeam() == null ? false : super.canStart();
    }
}
