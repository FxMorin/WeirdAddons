package weirdaddons.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Environment(EnvType.CLIENT)
@Mixin(BoatEntity.class)
public abstract class BoatEntity_accuratePositionMixin extends Entity {

    @Shadow private int field_7708; //this is clientInterpolationSteps
    //Unlike minecarts, boats don't attempt to get the new position once all interpolation steps are done


    public BoatEntity_accuratePositionMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;isLogicalSideForUpdatingMovement()Z"
            )
    )
    public boolean alwaysLogical(BoatEntity instance) {
        return WeirdAddonsSettings.accurateEntityPositions || this.isLogicalSideForUpdatingMovement();
    }


    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/World;isClient:Z",
                    ordinal = 1
            )
    )
    public boolean notClient(World world) {
        return (!WeirdAddonsSettings.accurateEntityPositions || this.isLogicalSideForUpdatingMovement()) && world.isClient;
    }


    @Inject(
            method = "method_7555()V",
            at = @At("RETURN")
    )
    private void onNoMoreInterpolation(CallbackInfo ci) {
        if (WeirdAddonsSettings.accurateEntityPositions && this.field_7708 <= 0) { //Match minecart behaviour here
            this.refreshPosition();
            this.setRotation(this.getYaw(), this.getPitch());
        }
    }
}
