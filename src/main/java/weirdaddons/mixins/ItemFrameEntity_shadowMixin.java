package weirdaddons.mixins;

import carpet.CarpetServer;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomStringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import weirdaddons.WeirdAddonsServer;
import weirdaddons.WeirdAddonsSettings;

import java.lang.reflect.InvocationTargetException;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntity_shadowMixin extends AbstractDecorationEntity {

    @Shadow
    public abstract ItemStack getHeldItemStack();

    protected ItemFrameEntity_shadowMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (WeirdAddonsSettings.itemShadowingMechanic == WeirdAddonsSettings.itemShadowingMechanics.SPECTRAL && source.getAttacker() instanceof SpectralArrowEntity sa && sa.isOnFire()) {
            BlockPos pos = this.attachmentPos.offset(this.facing.getOpposite());
            Inventory inv = HopperBlockEntity.getInventoryAt(this.world, pos);
            if (inv != null) {
                ItemStack stack = this.getHeldItemStack();
                if (HopperBlockEntity.transfer(null, inv, stack, this.facing.getOpposite()).isEmpty()) {
                    if (WeirdAddonsServer.isCarpetShadowLoaded) {
                        try {
                            int idLength = (int) CarpetServer.settingsManager.getRule("shadowItemIdSize").get();
                            String id = "W-" + RandomStringUtils.random(idLength, true, false);
                            WeirdAddonsServer.CarpetShadowGetByIdOrAdd.invoke(WeirdAddonsServer.CarpetShadowGlobals, id, stack);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            WeirdAddonsServer.isCarpetShadowLoaded = false;
                            System.out.println("Could not locate Carpet Shadow");
                        }
                        if (WeirdAddonsServer.isCarpetShadowLoaded) {
                            sa.discard();
                            cir.setReturnValue(false);
                        }
                    } else {
                        sa.discard();
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (WeirdAddonsSettings.itemShadowingMechanic == WeirdAddonsSettings.itemShadowingMechanics.LIGHTNING) {
            BlockPos pos = this.attachmentPos.offset(this.facing.getOpposite());
            Inventory inv = HopperBlockEntity.getInventoryAt(this.world, pos);
            if (inv != null) {
                ItemStack stack = this.getHeldItemStack();
                if (HopperBlockEntity.transfer(null, inv, stack, this.facing.getOpposite()).isEmpty()) {
                    if (WeirdAddonsServer.isCarpetShadowLoaded) {
                        try {
                            int idLength = (int)CarpetServer.settingsManager.getRule("shadowItemIdSize").get();
                            String id = "W-"+RandomStringUtils.random(idLength, true, false);
                            WeirdAddonsServer.CarpetShadowGetByIdOrAdd.invoke(WeirdAddonsServer.CarpetShadowGlobals,id,stack);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            WeirdAddonsServer.isCarpetShadowLoaded = false;
                            System.out.println("Could not locate Carpet Shadow");
                        }
                        if (WeirdAddonsServer.isCarpetShadowLoaded) lightning.discard();
                    } else {
                        lightning.discard();
                    }
                }
            }
        }
    }
}
