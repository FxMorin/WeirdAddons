package weirdaddons;

import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;

import java.util.UUID;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class WeirdAddonsCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) { // This is totally not sketch or anything. Totally didn't just throw this together within a couple minutes...
        dispatcher.register(literal("weird").requires((player) -> SettingsManager.canUseCommand(player, WeirdAddonsSettings.commandWeird)).
                then(literal("chunk").
                        then(literal("watch").
                                executes((c) -> {
                                    UUID playerUUID = c.getSource().getPlayer().getUuid();
                                    if (WeirdAddonsUtils.playersWatching.contains(playerUUID)) {
                                        WeirdAddonsUtils.playersWatching.remove(playerUUID);
                                        WeirdAddonsUtils.sendToPlayer(playerUUID, "You are no longer watching chunk activity!");
                                    } else {
                                        WeirdAddonsUtils.playersWatching.add(playerUUID);
                                        WeirdAddonsUtils.sendToPlayer(playerUUID, "You are now watching chunk activity!");
                                    }
                                    return 1;
                                })
                        ).
                        then(literal("set").
                                then(argument("chunk", ColumnPosArgumentType.columnPos()).
                                        executes((c) -> {
                                            ColumnPos columnPos = ColumnPosArgumentType.getColumnPos(c, "chunk");
                                            WeirdAddonsSettings.chunkPos = new ChunkPos(columnPos.x, columnPos.z);
                                            WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Targeted chunk set to: " + WeirdAddonsSettings.chunkPos);
                                            return 1;
                                        }))).
                        then(literal("start").
                                executes((c) -> {
                                    if (WeirdAddonsSettings.chunkPos == null) {
                                        WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "A chunk must be specified before this can be enabled!");
                                    } else if (WeirdAddonsSettings.isDisplayingChunk) {
                                        WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Chunk has already started!");
                                    } else {
                                        WeirdAddonsUtils.playersWatching.add(c.getSource().getPlayer().getUuid());
                                        WeirdAddonsSettings.chunkWorld = c.getSource().getPlayer().world;
                                        WeirdAddonsSettings.isDisplayingChunk = true;
                                        WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Chunk has started!");
                                    }
                                    return 1;
                                })
                        ).
                        then(literal("stop").
                                executes((c) -> {
                                    if (WeirdAddonsSettings.isDisplayingChunk) {
                                        WeirdAddonsSettings.isDisplayingChunk = false;
                                        WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Chunk has stopped!");
                                    } else {
                                        WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Chunk is not running!");
                                    }
                                    WeirdAddonsUtils.playersWatching.clear();
                                    return 1;
                                })
                        ).
                        then(literal("radius").
                                then(argument("radius", integer(0)).
                                        executes((c) -> {
                                            WeirdAddonsSettings.chunkRadius = getInteger(c, "radius");
                                            WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Chunk Radius has been set to: " + WeirdAddonsSettings.chunkRadius);
                                            return 1;
                                        }))
                        )
                ).
                /*then(literal("tick").
                        then(literal("chunk").
                                then(argument("rate", floatArg(0.1F, 500.0F)).
                                        suggests( (c, b) -> suggestMatching(new String[]{"20.0"},b)).
                                        executes((c) -> setChunkTps(c.getSource(), getFloat(c, "rate")))))).*/
                        then(literal("control").
                        executes((c) -> {
                            WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(), "Not implemented yet...");
                            return 1;
                        })
                ).
                        //Just ignore this
                        then(literal("color").
                                executes((c) -> {
                                    WeirdAddonsUtils.sendToPlayer(c.getSource().getPlayer().getUuid(),
                                            "§l----------: §6§lColors: §r§l :----------" + "\n"
                                                    + "§aGreen: &a" + "\n"
                                                    + "§bAqua: &b" + "\n"
                                                    + "§cRed: &c" + "\n"
                                                    + "§dPink: &d" + "\n"
                                                    + "§eYellow: &e" + "\n"
                                                    + "§fWhite: &f" + "\n"
                                                    + "§0Black: &0" + "\n"
                                                    + "§1Dark Blue: &1" + "\n"
                                                    + "§2Dark Green: &2" + "\n"
                                                    + "§3Dark Aqua: &3" + "\n"
                                                    + "§4Dark Red: &4" + "\n"
                                                    + "§5Dark Purple: &5" + "\n"
                                                    + "§6Gold: &6" + "\n"
                                                    + "§7Grey: &7" + "\n"
                                                    + "§8Dark Grey &8" + "\n"
                                                    + "§9Indigo: &9§r" + "\n"
                                                    +"§l----------: §6§lFormats: §r§l :----------§r" + "\n"
                                                    + "Obfuscate: &k" + "\n"
                                                    + "§lBold: &l" + "\n"
                                                    + "§mStrike Through: &m§r" + "\n"
                                                    + "§nUnderline: &n§r" + "\n"
                                                    + "§oItalics &o§r" + "\n");
                                    return 1;
                                })
                        ));
    }
}
