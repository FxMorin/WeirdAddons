package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

public class WeirdAddonsServer implements CarpetExtension, ModInitializer
{
    public static boolean isCarpetExtraLoaded = false;

    @Override
    public String version()
    {
        return "weirdaddons";
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new WeirdAddonsServer());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(WeirdAddonsSettings.class);
    }

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer){
        for (CarpetExtension e : CarpetServer.extensions) {
            if (e.version().equals("carpet-extra")) {
                //Check if carpetExtra is loaded so we can use its rules
                isCarpetExtraLoaded = true;
                break;
            }
        }
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        WeirdAddonsCommands.register(dispatcher);
    }

    @Override
    public void onTick(MinecraftServer server) { //I really didn't care when I was implementing this xD
        if (WeirdAddonsSettings.isDisplayingChunk) {
            WeirdAddonsUtils.updateDisplayingChunks(server);
        }
    }

    public static void onShutdown() {}
}
