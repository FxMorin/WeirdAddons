package weirdaddons.mixins;

import carpet.utils.Messenger;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(method = "generateFeatures", at = @At("HEAD"))
    public void stopInstantMechanicsOnGenerateFeatures(ChunkRegion region, StructureAccessor accessor, CallbackInfo ci) {
        if (WeirdAddonsSettings.instantFallMechanic && WeirdAddonsSettings.instantFall) {
            WeirdAddonsSettings.instantFall = false;
        }
        if (WeirdAddonsSettings.instantTileTickMechanicNum != WeirdAddonsSettings.InstantTileTickEnum.FALSE && WeirdAddonsSettings.instantTileTick) {
            WeirdAddonsSettings.instantTileTick = false;
            if (WeirdAddonsSettings.instantTileTickMechanicNum == WeirdAddonsSettings.InstantTileTickEnum.VANILLA || WeirdAddonsSettings.instantTileTickMechanicNum == WeirdAddonsSettings.InstantTileTickEnum.VANILLACRASHFIX) {
                WeirdAddonsSettings.instantLiquidFlow = false;
            }
            if (WeirdAddonsSettings.instantTileTickMechanicNum != WeirdAddonsSettings.InstantTileTickEnum.CRASHFIX && WeirdAddonsSettings.instantTileTickMechanicNum != WeirdAddonsSettings.InstantTileTickEnum.VANILLACRASHFIX) { //If crashFix is not on
                throw new StackOverflowError();
            } else {
                Messenger.print_server_message(region.toServerWorld().getServer(), "InstantTileTick caused a server crash from region: "+region.toString()+" - Region Center Chunk: ["+region.getCenterChunkX()+","+region.getCenterChunkZ()+"]");
            }
        }
    }
}
