package weirdaddons;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class WeirdAddonsCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        // This is totally not sketch or anything. Totally didn't just throw this together within a couple minutes...
        dispatcher.register(literal("weird").
                then(literal("chunk").
                        then(literal("set").
                            then(argument("daChunk", ColumnPosArgumentType.columnPos()).
                                executes((c)-> {
                                    WeirdAddonsSettings.chunkPos = ColumnPosArgumentType.getColumnPos(c, "daChunk");
                                    WeirdAddonsUtils.sendToPlayer("Targetted chunk set to: "+WeirdAddonsSettings.chunkPos);
                                    return 1;
                                }))).
                        then(literal("start").
                                executes((c)-> {
                                    if (!WeirdAddonsServer.mc.isInSingleplayer()) {
                                        WeirdAddonsUtils.sendToPlayer("Currently this feature is only available in singleplayer cause im lazy :)");
                                    } else if (WeirdAddonsSettings.chunkPos == null) {
                                        WeirdAddonsUtils.sendToPlayer("A chunk must be specified before this can be enabled!");
                                    } else if (WeirdAddonsSettings.isDisplayingChunk) {
                                        WeirdAddonsUtils.sendToPlayer("Chunk has already started!");
                                    } else {
                                        WeirdAddonsSettings.isDisplayingChunk = true;
                                        WeirdAddonsUtils.sendToPlayer("Chunk has started!");
                                    }
                                    return 1;
                                })
                        ).
                        then(literal("stop").
                                executes((c)-> {
                                    if (WeirdAddonsSettings.isDisplayingChunk) {
                                        WeirdAddonsSettings.isDisplayingChunk = false;
                                        WeirdAddonsUtils.sendToPlayer("Chunk has stopped!");
                                    } else {
                                        WeirdAddonsUtils.sendToPlayer("Chunk is not running!");
                                    }
                                    return 1;
                                })
                        ).
                        then(literal("radius").
                                then(argument("radius", IntegerArgumentType.integer(0)).
                                    executes((c)-> {
                                        WeirdAddonsSettings.chunkRadius = IntegerArgumentType.getInteger(c, "radius");
                                        WeirdAddonsUtils.sendToPlayer("Chunk Radius has been set to: "+WeirdAddonsSettings.chunkRadius);
                                    return 1;
                                }))
                        )
                ).
                then(literal("control").
                        executes((c)-> {
                            WeirdAddonsUtils.sendToPlayer("Not implemented yet...");
                            return 1;
                                })
                        ));

    }
}
