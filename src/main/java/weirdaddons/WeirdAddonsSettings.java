package weirdaddons;

import carpet.settings.Rule;
import net.minecraft.util.math.ColumnPos;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.CLIENT;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class WeirdAddonsSettings
{
    public static boolean isDisplayingChunk = false;
    public static ColumnPos chunkPos;
    public static int chunkRadius = 5;

    private final static String WEIRD = "weird";

    @Rule(
            desc = "End Crystals now explode when damaged from explosions. End Crystal chaining",
            category = {CREATIVE,WEIRD}
    )
    public static boolean crystalOverdose = false;

    @Rule(
            desc = "Change delay length of observers",
            category = {CREATIVE,WEIRD}
    )
    public static int observerDelay = 2;

    @Rule(
            desc = "Basically all setBlock calls will use this number",
            extra = "Custom block updates from: -1 to 127 | use -2 to disable",
            category = {CREATIVE,WEIRD}
    )
    public static int blockUpdateHell = -2;

    @Rule(
            desc = "Makes it so that sponges give block updates when absorbing water",
            extra = {"Fixes Bug [MC-220636]"},
            category = {CREATIVE,BUGFIX,WEIRD}
    )
    public static boolean spongeUpdate = false;

    @Rule(
            desc = "Makes it so that sponges can absorb all blocks :}",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeEverything = false;

    @Rule(
            desc = "Change the max amount of that a sponge can absorb",
            category = {CREATIVE,WEIRD}
    )
    public static int spongeLimit = 64;

    @Rule(
            desc = "Scaffolding breaking rules. Change how scaffolding breaks",
            options = {"break","float","gravity"},
            category = {CREATIVE,WEIRD}
    )
    public static String scaffoldingBreaking = "break";

    @Rule(
            desc = "Re-implementing instant fall into 1.16, what could possibly go wrong",
            category = {CREATIVE,WEIRD}
    )
    public static boolean instantFall = false;

    @Rule(
            desc = "A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks.",
            extra = "Also makes it so that instantFall turns off when generating new chunks.",
            category = {CREATIVE,EXPERIMENTAL,WEIRD}
    )
    public static boolean instantFallMechanic = false;

    @Rule(
            desc = "Makes it so that a lamp on top of a barrier block when un-powered sends the status of the chunks around it",
            extra = "1-14 = radius of the map. default 0 disables the feature",
            category = {CREATIVE,WEIRD}
    )
    public static int lampChunkStatus = 0;

    @Rule(
            desc = "Chunk map display method. Default is just a simple chat display",
            extra = "lamp: Holding a lamp will show a much larger map",
            options = {"chat","lamp"},
            category = {CREATIVE,CLIENT,WEIRD}
    )
    public static String lampChunkDisplay = "chat";

    @Rule(
            desc = "COLOR!",
            category = {CLIENT,WEIRD}
    )
    public static boolean colorify = false;

}
