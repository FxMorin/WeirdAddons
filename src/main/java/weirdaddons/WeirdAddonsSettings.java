package weirdaddons;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.CLIENT;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class WeirdAddonsSettings
{

    @Rule(
            desc = "Makes it so that sponges give block updates when absorbing water",
            extra = {"Fixes Bug [MC-220636]"},
            category = {CREATIVE,BUGFIX}
    )
    public static boolean spongeUpdate = false;

    @Rule(
            desc = "Re-implementing instant fall into 1.16, what could possibly go wrong",
            category = {CREATIVE}
    )
    public static boolean instantFall = false;

    @Rule(
            desc = "A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks.",
            extra = "Also makes it so that instantFall turns off when generating new chunks.",
            category = {CREATIVE,EXPERIMENTAL}
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
            category = {CREATIVE,CLIENT}
    )
    public static String lampChunkDisplay = "chat";

}
