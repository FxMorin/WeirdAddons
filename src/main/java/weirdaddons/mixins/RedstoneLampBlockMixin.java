package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import weirdaddons.WeirdAddonsSettings;

@Mixin(RedstoneLampBlock.class)
class RedstoneLampBlockMixin extends Block {

    @Shadow public static BooleanProperty LIT;

    public RedstoneLampBlockMixin(Settings settings) { super(settings); }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved && (WeirdAddonsSettings.instantFallMechanic || WeirdAddonsSettings.instantTileTickMechanic != WeirdAddonsSettings.InstantTileTickEnum.FALSE) && state.get(LIT)) {
            boolean successInstantFall = WeirdAddonsSettings.instantFallMechanic;
            boolean successInstantTile = WeirdAddonsSettings.instantTileTickMechanic != WeirdAddonsSettings.InstantTileTickEnum.FALSE;
            ChunkPos cpos = new ChunkPos(pos);
            ChunkManager chunkManager = world.getChunkManager();
            for (int x = cpos.x - 1; x <= cpos.x + 1; x++) {
                for (int z = cpos.z - 1; z <= cpos.z + 1; z++) {
                    WorldChunk chunk = chunkManager.getWorldChunk(x, z, false);
                    if (chunk != null) {
                        ChunkHolder.LevelType levelType = chunk.getLevelType();
                        if (x == cpos.x && z == cpos.z) {
                            if (levelType != ChunkHolder.LevelType.BORDER) {
                                successInstantFall = false;
                                successInstantTile = false;
                                break;
                            }
                        } else {
                            if (levelType == ChunkHolder.LevelType.BORDER) {
                                successInstantTile = false;
                                if (!successInstantFall) {break;}
                            } else if (levelType == ChunkHolder.LevelType.TICKING) {
                                successInstantFall = false;
                                if (!successInstantTile) {break;}
                            } else {
                                successInstantFall = false;
                                successInstantTile = false;
                                break;
                            }
                        }
                    }
                }
                if (!successInstantFall && !successInstantTile) { break; }
            }
            if (successInstantFall) {
                WeirdAddonsSettings.instantFall = true;
            } else if (successInstantTile) {
                WeirdAddonsSettings.instantTileTick = true;
                if (WeirdAddonsSettings.instantTileTickMechanic == WeirdAddonsSettings.InstantTileTickEnum.VANILLA || WeirdAddonsSettings.instantTileTickMechanic == WeirdAddonsSettings.InstantTileTickEnum.VANILLACRASHFIX) {
                    WeirdAddonsSettings.instantLiquidFlow = true;
                }
            }
        }
        if (this.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }
}
