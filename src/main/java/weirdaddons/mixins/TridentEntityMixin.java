package weirdaddons.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;

@Mixin(TridentEntity.class)
public class TridentEntityMixin extends PersistentProjectileEntity {

    @Shadow @Final private static TrackedData<Byte> LOYALTY;
    @Shadow @Final private static TrackedData<Boolean> ENCHANTED;
    @Shadow private ItemStack tridentStack;
    @Shadow private boolean dealtDamage;
    @Shadow public int returnTimer;

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow private boolean isOwnerAlive() { return false; }
    @Shadow protected ItemStack asItemStack() { return null; }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoClip()) && entity != null) {
            int i = (Byte)this.dataTracker.get(LOYALTY);
            if (i > 0 && !this.isOwnerAlive()) {
                if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }
                this.remove();
            } else if (i > 0) {
                this.setNoClip(true);
                Vec3d vec3d = new Vec3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                if (WeirdAddonsSettings.uncappedTridentSpeed) {
                    this.setPos(this.getX() + vec3d.x * 0.015D * (double) i, this.getY() + vec3d.y * 0.015D * (double) i, this.getZ() + vec3d.z * 0.015D * (double) i);
                } else {
                    this.setPos(this.getX(), this.getY() + vec3d.y * 0.015D * (double) i, this.getZ());
                }
                if (this.world.isClient) {
                    this.lastRenderY = this.getY();
                }
                double d = 0.05D * (double)i;
                this.setVelocity(this.getVelocity().multiply(0.95D).add(vec3d.normalize().multiply(d)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returnTimer;
            }
        }

        super.tick();
    }
}
