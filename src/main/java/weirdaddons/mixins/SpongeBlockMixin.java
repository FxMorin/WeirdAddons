package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import weirdaddons.WeirdAddonsSettings;

import java.util.Random;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin extends Block {

    public SpongeBlockMixin(Settings settings) {super(settings);}

    @Shadow
    private boolean absorbWater(World world, BlockPos pos) {return false;}

    @ModifyConstant(method = "update", constant = @Constant(intValue = 2), require = 0)
    private int scheduledTick_maxDistance(int oldValue) {
        return WeirdAddonsSettings.spongeUpdate ? 3 : 2;
    }
}
