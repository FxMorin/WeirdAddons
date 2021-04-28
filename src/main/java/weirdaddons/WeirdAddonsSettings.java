package weirdaddons;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ColumnPos;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.CLIENT;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class WeirdAddonsSettings
{
    public static boolean insideBlockTicks = false;
    public static int instantTileTickMechanicNum = 0;
    public static boolean isDisplayingChunk = false;
    public static ColumnPos chunkPos;
    public static int chunkRadius = 5;

    private final static String WEIRD = "weird";

    @Rule(
            desc = "End Crystals now explode when damaged from explosions. End Crystal chaining",
            extra = "Fixes Bug [MC-118429]",
            category = {CREATIVE,WEIRD,BUGFIX}
    )
    public static boolean crystalOverdose = false;

    @Rule(
            desc = "Change the delay length of observers (how long it takes to turn on)",
            category = {CREATIVE,WEIRD}
    )
    public static int observerDelay = 2;

    @Rule(
            desc = "Change the pulse length of observers (how long it stays on)",
            category = {CREATIVE,WEIRD}
    )
    public static int observerPulse = 2;

    @Rule(
            desc = "Basically all setBlock calls will use this number",
            extra = "Custom block updates from: -1 to 127 | use -2 to disable",
            category = {CREATIVE,WEIRD}
    )
    public static int blockUpdateHell = -2;

    @Rule(
            desc = "Makes it so that sponges give block updates when absorbing water",
            extra = "Fixes Bug [MC-220636]",
            category = {CREATIVE,BUGFIX,WEIRD}
    )
    public static boolean spongeUpdate = false;

    @Rule(
            desc = "Makes it so that sponges can absorb all blocks :}",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeEverything = false;

    @Rule(
            desc = "The sponge now absorbs lava instead of water, you happy now?",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeLava = false;

    @Rule(
            desc = "Change the max amount of that a sponge can absorb",
            category = {CREATIVE,WEIRD}
    )
    public static int spongeLimit = 64;

    @Rule(
            desc = "Removes all restrictions that sponges have",
            extra = "You can still change the spongeLimit to avoid crashing. spongeLimit is ignored if its the default value!",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeInfinite = false;

    @Rule(
            desc = "Modify sponges so that the blocks they remove don't create blockUpdates",
            extra = "Only noticeable when using spongeInfinite",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeFaster = false;

    @Rule(
            desc = "Sponge, sponges itself",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeCeption = false;

    @Rule(
            desc = "Scaffolding breaking rules. Change how scaffolding breaks",
            options = {"break","float","gravity"},
            category = {CREATIVE,WEIRD}
    )
    public static String scaffoldingBreaking = "break";

    @Rule(
            desc = "Instant-Liquid flow in 1.16, basically instant tile ticks but for liquids",
            category = {CREATIVE,WEIRD}
    )
    public static boolean instantLiquidFlow = false;

    @Rule(
            desc = "Makes all liquid ticks happen within the next tick",
            category = {CREATIVE,WEIRD}
    )
    public static boolean fastLiquidFlow = false;

    @Rule(
            desc = "Makes all tile ticks happen within the next tick",
            category = {CREATIVE,WEIRD}
    )
    public static boolean fastTileTicks = false;

    @Rule(
            desc = "Re-implementing instant tile ticks into 1.16. Although it's more of an imitation",
            category = {CREATIVE,WEIRD}
    )
    public static boolean instantTileTick = false;

    @Rule(
            desc = "A game mechanic for instant tick ticks. Requires a lit redstone lamp to be moved in border chunks surrounded by Ticking Chunks",
            extra = "Also makes it so that instantTileTick crashes the server when generating new chunks",
            category = {CREATIVE,WEIRD},
            options = {"false","true","crashFix"},
            validate = tileTickValidator.class
    )
    public static String instantTileTickMechanic = "false";

    private static class tileTickValidator extends Validator<String> {
        @Override public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            if (newValue.equals("true")) {
                instantTileTickMechanicNum = 1;
            } else if (newValue.equals("false")) {
                instantTileTickMechanicNum = 0;
            } else if (newValue.equalsIgnoreCase("crashfix")) {
                instantTileTickMechanicNum = 2;
            }
            return newValue;
        }
    }

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

}
