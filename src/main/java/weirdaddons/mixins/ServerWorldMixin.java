package weirdaddons.mixins;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V", at = @At("HEAD"))
    public void BeforeBlockTicks(CallbackInfo ci) {
        WeirdAddonsSettings.insideBlockTicks = true;
    }

}
