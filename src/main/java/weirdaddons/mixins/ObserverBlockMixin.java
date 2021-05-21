package weirdaddons.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ObserverBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import weirdaddons.WeirdAddonsSettings;

import java.util.Random;

@Mixin(ObserverBlock.class)
public class ObserverBlockMixin extends FacingBlock {

    @Shadow public static BooleanProperty POWERED;

    protected ObserverBlockMixin(Settings settings) {super(settings);}

    @Shadow protected void updateNeighbors(World world, BlockPos pos, BlockState state) {}

    @ModifyArg(method = "scheduleTick(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/TickScheduler;schedule(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;I)V"), index = 2)
    private int adjustDelay(int delay) {
        return WeirdAddonsSettings.observerDelay;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            world.setBlockState(pos, state.with(POWERED, false), 2);
        } else {
            world.setBlockState(pos, state.with(POWERED, true), 2);
            world.getBlockTickScheduler().schedule(pos, this, WeirdAddonsSettings.observerPulse);
        }
        this.updateNeighbors(world, pos, state);
    }
}
