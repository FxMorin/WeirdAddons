package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Objects;

public class WeirdAddonsServer implements CarpetExtension, ModInitializer
{
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static void noop() {}

    static {
        CarpetServer.manageExtension(new WeirdAddonsServer());
    }

    @Override
    public void onGameStarted() { CarpetServer.settingsManager.parseSettingsClass(WeirdAddonsSettings.class); }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        WeirdAddonsCommands.register(dispatcher);
    }

    @Override
    public void onTick(MinecraftServer server) {
        //I really didn't care when I was implementing this xD
        if (WeirdAddonsSettings.isDisplayingChunk && WeirdAddonsSettings.lampChunkStatus <= 0 && mc.world != null) { //Don't run both at the same time!
            WeirdAddonsUtils.sendToPlayer(WeirdAddonsUtils.DisplayChunks(server.getWorld(mc.player.world.getRegistryKey()), new ChunkPos(WeirdAddonsSettings.chunkPos.x, WeirdAddonsSettings.chunkPos.z), WeirdAddonsSettings.chunkRadius));
        }
    }

    @Override
    public void onInitialize() {}
}
