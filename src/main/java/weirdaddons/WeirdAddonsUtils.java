package weirdaddons;

import carpet.CarpetServer;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.WorldChunk;

import java.util.*;

public class WeirdAddonsUtils {

    public static HashSet<UUID> playersWatching = new HashSet<>();

    public static void sendToPlayer(UUID playerUUID, String msg) {
        CarpetServer.minecraft_server.getPlayerManager().getPlayer(playerUUID).sendMessage(new LiteralText(msg), MessageType.SYSTEM,playerUUID);
    }

    public static void updateDisplayingChunks(MinecraftServer server) {
        for (UUID player : playersWatching) {
            sendToPlayer(player, DisplayChunks(WeirdAddonsSettings.chunkWorld, WeirdAddonsSettings.chunkPos, WeirdAddonsSettings.chunkRadius, player));
        }
    }

    public static String DisplayChunks(World world, ChunkPos pos, int radius, UUID playerUUID){
        ChunkManager chunkManager = world.getChunkManager();
        StringBuilder result = new StringBuilder();
        for (int x = pos.x-radius; x <= pos.x+radius; x++) {
            for (int z = pos.z-radius; z <= pos.z+radius; z++) {
                char icon = '█';
                WorldChunk chunk = chunkManager.getWorldChunk(x, z, false);
                if (x == pos.x && z == pos.z) {
                    icon = '◎';
                }
                if (chunk != null) {
                    boolean isPlayerChunk = false;
                    for (ServerPlayerEntity player : CarpetServer.minecraft_server.getPlayerManager().getPlayerList()) {
                        if (player != null && player.getChunkPos().x == x && player.getChunkPos().z == z) {
                            isPlayerChunk = true;
                            break;
                        }
                    }
                    if (isPlayerChunk) {
                        ServerPlayerEntity player = CarpetServer.minecraft_server.getPlayerManager().getPlayer(playerUUID);
                        if (player != null && player.getChunkPos().x == x && player.getChunkPos().z == z) {
                            result.append("§5☻");
                        } else {
                            result.append("§5"+icon);
                        }
                    } else {
                        ChunkHolder.LevelType levelType = chunk.getLevelType();
                        if (levelType == ChunkHolder.LevelType.TICKING) {
                            result.append("§a"+icon); //green
                        } else if (levelType == ChunkHolder.LevelType.ENTITY_TICKING) {
                            result.append("§2"+icon); //dark_green
                        } else if (levelType == ChunkHolder.LevelType.BORDER) {
                            result.append("§7"+icon); //gray
                        } else if (levelType == ChunkHolder.LevelType.INACCESSIBLE) {
                            result.append("§4"+icon); //red
                        } else {
                            result.append("§8"+icon); //dark_gray - null levelType
                        }
                    }
                } else {
                    result.append("§0"+icon); //black - null chunk
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

}
