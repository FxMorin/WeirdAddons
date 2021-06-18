package weirdaddons.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends GolemEntity  {

    protected ShulkerEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

    @Shadow private boolean isClosed() { return false; }

    @Redirect(method= "spawnNewShulker()V",at=@At(value="INVOKE",target="Lnet/minecraft/entity/mob/ShulkerEntity;isClosed()Z"))
    public boolean WhoCaresIfThereClosed(ShulkerEntity shulkerEntity) {
        return !WeirdAddonsSettings.shulkerCancer && this.isClosed();
    }

    @ModifyConstant(method= "tryTeleport()Z",constant = @Constant(intValue = 5))
    private int changeAttemptAmt(int original) {
        return (WeirdAddonsSettings.shulkerCancer) ? 8 : 5;
    }

    @Inject(method= "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",at=@At(value="INVOKE",target="Lnet/minecraft/entity/mob/ShulkerEntity;spawnNewShulker()V"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.setHealth(this.getMaxHealth()); //never die, this is the way
    }
}
