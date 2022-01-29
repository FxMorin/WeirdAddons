package weirdaddons.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.network.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import weirdaddons.WeirdAddonsSettings;

@Environment(EnvType.SERVER)
@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntry_accuratePositionMixin {


    @ModifyConstant(
            method = "tick()V",
            constant = @Constant(intValue = 400)
    )
    private static int smallerPlease(int i) {
        return WeirdAddonsSettings.accurateEntityPositions ? Integer.MIN_VALUE : i;
    }
}
