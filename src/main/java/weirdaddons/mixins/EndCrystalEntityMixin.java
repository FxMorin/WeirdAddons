package weirdaddons.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import weirdaddons.WeirdAddonsSettings;

@Mixin(EndCrystalEntity.class)
public class EndCrystalEntityMixin extends Entity {

    public EndCrystalEntityMixin(EntityType<?> type, World world) { super(type, world); }

    @Shadow protected void initDataTracker() {}
    @Shadow protected void readCustomDataFromTag(CompoundTag tag) {}
    @Shadow protected void writeCustomDataToTag(CompoundTag tag) {}
    @Shadow public Packet<?> createSpawnPacket() {return null;}

    @Shadow private void crystalDestroyed(DamageSource source) {}

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.getAttacker() instanceof EnderDragonEntity) {
            return false;
        } else {
            if (!this.removed && !this.world.isClient) {
                this.remove();
                if (WeirdAddonsSettings.crystalOverdose || !source.isExplosive()) {
                    this.world.createExplosion(null, this.getX(), this.getY(), this.getZ(), 6.0F, Explosion.DestructionType.DESTROY);
                }

                this.crystalDestroyed(source);
            }

            return true;
        }
    }
}
