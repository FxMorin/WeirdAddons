package weirdaddons.mixins;

import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.List;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(method = "generateFeatures", at = @At("HEAD"))
    public void stopInstantFallOnGenerateFeatures(ChunkRegion region, StructureAccessor accessor, CallbackInfo ci) {
        if (WeirdAddonsSettings.instantFallMechanic && WeirdAddonsSettings.instantFall) {
            WeirdAddonsSettings.instantFall = false;
        }
    }
}
