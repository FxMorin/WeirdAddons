package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;    
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.Method;

public class WeirdAddonsServer implements CarpetExtension, ModInitializer {
    public static boolean isCarpetExtraLoaded = false;
    public static boolean isCarpetShadowLoaded = false;
    public static Class CarpetShadowGlobals;
    public static Method CarpetShadowGetByIdOrAdd;

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
        isCarpetShadowLoaded = FabricLoader.getInstance().isModLoaded("carpet-shadow");
        if (isCarpetShadowLoaded) {
            try {
                CarpetShadowGlobals = Class.forName("com.carpet_shadow.Globals");
                CarpetShadowGetByIdOrAdd = CarpetShadowGlobals.getMethod("getByIdOrAdd");
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                isCarpetShadowLoaded = false;
                e.printStackTrace();
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
