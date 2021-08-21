package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;    
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
        //A better way to check if carpetExtra is loaded
        isCarpetExtraLoaded = FabricLoader.getInstance().isModLoaded("carpet-extra");
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
}
