package weirdaddons;

import carpet.CarpetSettings;
import carpet.network.CarpetClient;
import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import carpet.utils.Messenger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.world.World;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class WeirdAddonsSettings
{
    public static boolean insideBlockTicks = false;
    public static InstantTileTickEnum instantTileTickMechanicNum = WeirdAddonsSettings.InstantTileTickEnum.FALSE;
    public static boolean isDisplayingChunk = false;
    public static ColumnPos chunkPos;
    public static int chunkRadius = 5;
    public static World chunkWorld;

    public enum InstantTileTickEnum {
        FALSE,
        TRUE,
        CRASHFIX,
        VANILLA,
        VANILLACRASHFIX
    }

    private final static String WEIRD = "weird";

    @Rule(
            desc = "End Crystals now explode when damaged from explosions. End Crystal chaining",
            extra = "Fixes Bug [MC-118429]",
            category = {CREATIVE,WEIRD,BUGFIX}
    )
    public static boolean crystalOverdose = false;

    @Rule(
            desc = "Change the delay length of observers (how long it takes to turn on)",
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
            category = {CREATIVE,WEIRD}
    )
    public static int observerDelay = 2;

    @Rule(
            desc = "Change the pulse length of observers (how long it stays on)",
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
            category = {CREATIVE,WEIRD}
    )
    public static int observerPulse = 2;

    @Rule(
            desc = "Basically all setBlock calls will use this number",
            extra = "Custom block updates from: -1 to 127 | use -2 to disable",
            validate = blockUpdateValiator.class,
            strict = false,
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
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
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
            desc = "Sponge does not become wet when used",
            extra = "If spongeCeption is enabled, this will not do anything",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeReusable = false;

    @Rule(
            desc = "Sponge will prevent block explosion damage if its in the tnt list of blocks to break",
            category = {CREATIVE,WEIRD}
    )
    public static boolean spongeAbsorbsExplosions = false;

    @Rule(
            desc = "Allows you to enchant past the highest vanilla enchant limit using /enchant",
            extra = "Also makes it so every enchantment works on all items",
            category = {CREATIVE,WEIRD}
    )
    public static boolean enchantmentOverride = false;

    @Rule(
            desc = "Makes it so that tridents can move just as fast as they usually do on the y-axis on the x & z axis",
            category = {CREATIVE,WEIRD,EXPERIMENTAL}
    )
    public static boolean uncappedTridentSpeed = false;

    @Rule(
            desc = "Usually the maximum viewDistance is 32, this will allow it to be much higher",
            category = {CREATIVE,WEIRD,EXPERIMENTAL}
    )
    public static boolean uncappedViewDistance = false;

    @Rule(
            desc = "Customizable execute volume limit",
            extra = "It's weird cause nobody knows this limit exists xD",
            options = {"32768", "250000", "1000000"},
            validate = ExecuteBlockLimit.class,
            strict = false,
            category = {CREATIVE,WEIRD}
    )
    public static int executeBlockLimit = 32768;

    @Rule(
            desc = "Scaffolding breaking rules. Change how scaffolding breaks", //be yourself xD
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
            extra = {"Also makes it so that instantTileTick crashes the server when generating new chunks","vanilla makes it so that instantLiquidFlow works with instantTileTicks like its does in 1.12"},
            category = {CREATIVE,WEIRD},
            options = {"false","true","crashFix","vanilla","vanillaCrashFix"},
            validate = tileTickValidator.class
    )
    public static String instantTileTickMechanic = "false";

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
            desc = "Prevents all players from breaking blocks",
            category = {CREATIVE,WEIRD}
    )
    public static boolean preventBreaking = false;

    /*

    Overriding Original Carpet Rules

     */
    private static class ViewDistanceValidator extends Validator<Integer>
    {
        @Override public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string)
        {
            if (currentRule.get().equals(newValue) || source == null)
            {
                return newValue;
            }
            if (newValue < 0 || (newValue > 32 && !uncappedViewDistance))
            {
                Messenger.m(source, "r view distance has to be between 0"+(uncappedViewDistance ? "" : " and 32"));
                return null;
            }
            MinecraftServer server = source.getMinecraftServer();

            if (server.isDedicated())
            {
                int vd = (newValue >= 2)?newValue:((DedicatedServer) server).getProperties().viewDistance;
                if (vd != server.getPlayerManager().getViewDistance())
                    server.getPlayerManager().setViewDistance(vd);
                return newValue;
            }
            else
            {
                Messenger.m(source, "r view distance can only be changed on a server");
                return 0;
            }
        }
        @Override
        public String description() {
            if (uncappedViewDistance) {
                return "You must choose a value from 0 (use server settings) or higher";
            } else {
                return "You must choose a value from 0 (use server settings) to 32";
            }
        }
    }
    @Rule(
            desc = "Changes the view distance of the server.",
            extra = "Set to 0 to not override the value in server settings.",
            options = {"0", "12", "16", "32"},
            category = CREATIVE,
            strict = false,
            validate = ViewDistanceValidator.class
    )
    public static int viewDistance = 0;


    /*

    Validators

     */
    private static class blockUpdateValiator extends Validator<Integer> {
        @Override public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string) {
            if (newValue < -2 || newValue > 127) {
                Messenger.m(source, "You must choose a value from -2 to 127");
                return null;
            }
            return newValue;
        }
        @Override
        public String description() { return "You must choose a value from -2 to 127";}
    }

    private static class tileTickValidator extends Validator<String> {
        @Override public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            if (newValue.equals("true")) {
                instantTileTickMechanicNum = InstantTileTickEnum.TRUE;
            } else if (newValue.equals("false")) {
                instantTileTickMechanicNum = InstantTileTickEnum.FALSE;
            } else if (newValue.equalsIgnoreCase("crashfix")) {
                instantTileTickMechanicNum = InstantTileTickEnum.CRASHFIX;
            } else if (newValue.equalsIgnoreCase("vanilla")) {
                instantTileTickMechanicNum = InstantTileTickEnum.VANILLA;
            } else if (newValue.equalsIgnoreCase("vanillacrashfix")) {
                instantTileTickMechanicNum = InstantTileTickEnum.VANILLACRASHFIX;
            }
            return newValue;
        }
    }

    private static class ExecuteBlockLimit extends Validator<Integer> {
        @Override public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string) {
            return (newValue>0 && newValue <= 20000000) ? newValue : null;
        }
        @Override
        public String description() { return "You must choose a value from 1 to 20M";}
    }

}
