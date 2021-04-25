package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import weirdaddons.WeirdAddonsSettings;
import weirdaddons.WeirdAddonsUtils;

import java.util.Random;

@Mixin(RedstoneLampBlock.class)
class RedstoneLampBlockMixin extends Block {

    @Shadow
    public static BooleanProperty LIT;

    public RedstoneLampBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(method = "Lnet/minecraft/block/RedstoneLampBlock;neighborUpdate(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;Z)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
    ))
    public boolean neighborSetState(World world, BlockPos pos, BlockState state, int flags) {
        if (WeirdAddonsSettings.lampChunkStatus > 0 && !state.get(LIT) && world.getBlockState(pos.down()).isOf(Blocks.BARRIER)) {
            WeirdAddonsUtils.sendToPlayer(WeirdAddonsUtils.DisplayChunks(world, pos, WeirdAddonsSettings.lampChunkStatus));
        }
        return world.setBlockState(pos, state, flags);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(LIT), 2);
            if (WeirdAddonsSettings.lampChunkStatus > 0 && world.getBlockState(pos.down()).isOf(Blocks.BARRIER)) {
                WeirdAddonsUtils.sendToPlayer(WeirdAddonsUtils.DisplayChunks(world, pos, WeirdAddonsSettings.lampChunkStatus));
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved && WeirdAddonsSettings.instantFallMechanic && state.get(LIT)) {
            boolean success = true;
            ChunkPos cpos = new ChunkPos(pos);
            ChunkManager chunkManager = world.getChunkManager();
            for (int x = cpos.x-1; x <= cpos.x+1; x++) {
                for (int z = cpos.z-1; z <= cpos.z+1; z++) {
                    WorldChunk chunk = chunkManager.getWorldChunk(x, z, false);
                    if (chunk != null) {
                        ChunkHolder.LevelType levelType = chunk.getLevelType();
                        if (levelType != ChunkHolder.LevelType.BORDER) {
                            success = false;
                            break;
                        }
                    }
                }
                if (!success) { break;}
            }
            if (success){ WeirdAddonsSettings.instantFall = true;}
        }
        if (this.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }
}
