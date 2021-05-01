package weirdaddons.mixins;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin {

    @ModifyConstant(method = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;setViewDistance(I)V", constant = @Constant(intValue = 33))
    private int uncappedHigherLimit(int original)
    {
        return WeirdAddonsSettings.uncappedViewDistance ? Integer.MAX_VALUE : original;
    }

}
