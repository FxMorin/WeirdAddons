package weirdaddons;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;

public class WeirdAddonsUtils {

    private static boolean chatHudLarge = false;

    public static void sendToPlayer(String msg) {
        WeirdAddonsServer.mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(msg), WeirdAddonsServer.mc.player.getUuid());
    }

    public static void largeChatHud(boolean large) {
        if (large && !chatHudLarge) {
            chatHudLarge = true;
            WeirdAddonsServer.mc.options.chatScale *= 0.8f;
            WeirdAddonsServer.mc.options.chatWidth *= 3.5f;
            WeirdAddonsServer.mc.options.chatHeightUnfocused *= 4.5f;
            WeirdAddonsServer.mc.options.chatHeightFocused *= 4.5f;
            WeirdAddonsServer.mc.options.chatLineSpacing *= 0.25f;
        } else if (!large && chatHudLarge) {
            chatHudLarge = false;
            WeirdAddonsServer.mc.options.chatScale *= 1.2f;
            WeirdAddonsServer.mc.options.chatWidth /= 3.5f;
            WeirdAddonsServer.mc.options.chatHeightUnfocused /= 4.5f;
            WeirdAddonsServer.mc.options.chatHeightFocused /= 4.5f;
            WeirdAddonsServer.mc.options.chatLineSpacing *= 1.75f;
        }
    }


    public static String DisplayChunks(World world, BlockPos pos, int radius){
        ClientPlayerEntity player = WeirdAddonsServer.mc.player;
        ChunkPos cpos = new ChunkPos(pos);
        ChunkManager chunkManager = world.getChunkManager();
        StringBuilder result = new StringBuilder();
        for (int x = cpos.x-radius; x <= cpos.x+radius; x++) {
            for (int z = cpos.z-radius; z <= cpos.z+radius; z++) {
                char icon = '█';
                WorldChunk chunk = chunkManager.getWorldChunk(x, z, false);
                if (x == cpos.x && z == cpos.z) {
                    icon = '◎';
                }
                if (chunk != null) {
                    if (player != null && player.chunkX == x && player.chunkZ == z) {
                        result.append("§5☻"); //dark_purple - player chunk
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
