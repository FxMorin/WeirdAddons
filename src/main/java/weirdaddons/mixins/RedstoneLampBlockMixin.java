package weirdaddons.mixins;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import weirdaddons.WeirdAddonsSettings;

@Mixin(RedstoneLampBlock.class)
class RedstoneLampBlockMixin extends Block {

    @Final @Shadow public static BooleanProperty LIT;

    public RedstoneLampBlockMixin(Settings settings) { super(settings); }

    private static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved && state.get(LIT)) {
            if (WeirdAddonsSettings.instantFallMechanic || WeirdAddonsSettings.instantTileTickMechanic != WeirdAddonsSettings.InstantTileTickEnum.FALSE) {
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
                                    if (!successInstantFall) {
                                        break;
                                    }
                                } else if (levelType == ChunkHolder.LevelType.TICKING) {
                                    successInstantFall = false;
                                    if (!successInstantTile) {
                                        break;
                                    }
                                } else {
                                    successInstantFall = false;
                                    successInstantTile = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (!successInstantFall && !successInstantTile) {
                        break;
                    }
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
        } else if (WeirdAddonsSettings.fallingBlockMechanic && world.getBlockState(pos.up()).getBlock() instanceof BeaconBlock && world.getBlockState(pos.up(2)).getBlock() instanceof FallingBlock) {
            world.removeBlock(pos.up(2), false);
            BlockState blockBelow = world.getBlockState(pos.down());
            world.spawnEntity(new FallingBlockEntity(world, (double) pos.getX() + 0.5D, pos.down().getY(), (double) pos.getZ() + 0.5D, blockBelow));
        }
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }
}
