package weirdaddons.mixins;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Shadow @Final private List<BlockPos> affectedBlocks;
    @Shadow @Final @Nullable private Entity entity;
    @Shadow @Final private World world;
    @Shadow @Final private float power;
    @Shadow @Final private double x;
    @Shadow @Final private double y;
    @Shadow @Final private double z;
    @Shadow @Final private ExplosionBehavior behavior;
    @Shadow @Final private DamageSource damageSource;
    @Shadow @Final private Map<PlayerEntity, Vec3d> affectedPlayers;

    @Shadow public static float getExposure(Vec3d vec3d, Entity entity) { return 0.0f;}

    @Inject(method = "collectBlocksAndDamageEntities", at = @At("HEAD"),
            cancellable = true)
    private void onSpongeExplosion(CallbackInfo ci)
    {
        if (WeirdAddonsSettings.spongeAbsorbsExplosions)
        {
            Set<BlockPos> set = Sets.newHashSet();
            boolean spongeAbsorb = false;
            int k, l;
            for(int j = 0; j < 16; ++j) {
                for(k = 0; k < 16; ++k) {
                    for(l = 0; l < 16; ++l) {
                        if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                            double d = (double)((float)j / 15.0F * 2.0F - 1.0F);
                            double z = (double)((float)k / 15.0F * 2.0F - 1.0F);
                            double f = (double)((float)l / 15.0F * 2.0F - 1.0F);
                            double g = Math.sqrt(d * d + z * z + f * f);
                            d /= g;
                            z /= g;
                            f /= g;
                            float h = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                            double m = this.x;
                            double n = this.y;
                            double o = this.z;
                            for(float var21 = 0.3F; h > 0.0F; h -= 0.22500001F) {
                                BlockPos blockPos = new BlockPos(m, n, o);
                                BlockState blockState = this.world.getBlockState(blockPos);
                                if (blockState.isOf(Blocks.SPONGE)) {
                                    spongeAbsorb = true;
                                    break;
                                }
                                FluidState fluidState = this.world.getFluidState(blockPos);
                                Optional<Float> optional = this.behavior.getBlastResistance(null, this.world, blockPos, blockState, fluidState);
                                if (optional.isPresent()) {
                                    h -= ((Float)optional.get() + 0.3F) * 0.3F;
                                }
                                if (h > 0.0F && this.behavior.canDestroyBlock(null, this.world, blockPos, blockState, h)) {
                                    set.add(blockPos);
                                }
                                m += d * 0.30000001192092896D;
                                n += z * 0.30000001192092896D;
                                o += f * 0.30000001192092896D;
                            }
                            if (spongeAbsorb) { break; }
                        }
                    }
                    if (spongeAbsorb) { break; }
                }
                if (spongeAbsorb) { break; }
            }
            if (!spongeAbsorb) {
                this.affectedBlocks.addAll(set);
            }
            float q = this.power * 2.0F;
            k = MathHelper.floor(this.x - (double)q - 1.0D);
            l = MathHelper.floor(this.x + (double)q + 1.0D);
            int t = MathHelper.floor(this.y - (double)q - 1.0D);
            int u = MathHelper.floor(this.y + (double)q + 1.0D);
            int v = MathHelper.floor(this.z - (double)q - 1.0D);
            int w = MathHelper.floor(this.z + (double)q + 1.0D);
            List<Entity> list = this.world.getOtherEntities(this.entity, new Box((double)k, (double)t, (double)v, (double)l, (double)u, (double)w));
            Vec3d vec3d = new Vec3d(this.x, this.y, this.z);
            for(int x = 0; x < list.size(); ++x) {
                Entity entity = (Entity)list.get(x);
                if (!entity.isImmuneToExplosion()) {
                    double y = (double)(MathHelper.sqrt(entity.squaredDistanceTo(vec3d)) / q);
                    if (y <= 1.0D) {
                        double z = entity.getX() - this.x;
                        double aa = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y;
                        double ab = entity.getZ() - this.z;
                        double ac = (double)MathHelper.sqrt(z * z + aa * aa + ab * ab);
                        if (ac != 0.0D) {
                            z /= ac;
                            aa /= ac;
                            ab /= ac;
                            double ad = getExposure(vec3d, entity);
                            double ae = (1.0D - y) * ad;
                            entity.damage(this.damageSource, (float)((int)((ae * ae + ae) / 2.0D * 7.0D * (double)q + 1.0D)));
                            double af = ae;
                            if (entity instanceof LivingEntity) {
                                af = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)entity, ae);
                            }
                            entity.setVelocity(entity.getVelocity().add(z * af, aa * af, ab * af));
                            if (entity instanceof PlayerEntity) {
                                PlayerEntity playerEntity = (PlayerEntity)entity;
                                if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.abilities.flying)) {
                                    this.affectedPlayers.put(playerEntity, new Vec3d(z * ae, aa * ae, ab * ae));
                                }
                            }
                        }
                    }
                }
            }
            ci.cancel();
        }
    }
}
