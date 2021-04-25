package weirdaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class WeirdAddonsServer implements CarpetExtension, ModInitializer
{
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static void noop() {}

    static {
        CarpetServer.manageExtension(new WeirdAddonsServer());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(WeirdAddonsSettings.class);
    }

    @Override
    public void onInitialize() {}
}
