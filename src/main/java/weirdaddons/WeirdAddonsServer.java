package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class WeirdAddonsServer implements CarpetExtension, ModInitializer
{
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
    public void onTick(MinecraftServer server) { //I really didn't care when I was implementing this xD
        if (WeirdAddonsSettings.isDisplayingChunk) {
            WeirdAddonsUtils.updateDisplayingChunks(server);
        }
    }

    @Override
    public void onInitialize() {}
}
