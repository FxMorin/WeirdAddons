package weirdaddons;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.CREATIVE;

public class WeirdAddonsSettings
{

    @Rule(
            desc = "Makes it so that sponges give block updates when absorbing water",
            extra = {"Fixes Bug [MC-220636]"},
            category = {CREATIVE}
    )
    public static boolean spongeUpdate = false;

    @Rule(
            desc = "Re-implementing instant fall into 1.16, what could possibly go wrong",
            category = {CREATIVE}
    )
    public static boolean instantFall = false;

    @Rule(
            desc = "A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks.",
            category = {CREATIVE}
    )
    public static boolean instantFallMechanic = false;

    @Rule(
            desc = "Makes it so that a lamp on top of a barrier block when un-powered sends the status of the chunks around it",
            extra = "1-14 = radius of the map. default 0 disables the feature",
            category = {CREATIVE}
    )
    public static int lampChunkStatus = 0;

    @Rule(
            desc = "Chunk map display method. Default is just a simple chat display",
            extra = "lamp: Holding a lamp will show a much larger map",
            options = {"chat","lamp"},
            category = {CREATIVE}
    )
    public static String lampChunkDisplay = "chat";

}
