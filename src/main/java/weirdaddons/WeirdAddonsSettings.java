package weirdaddons;

import carpet.CarpetServer;
import carpet.CarpetSettings;
import carpet.network.CarpetClient;
import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import carpet.utils.Messenger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class WeirdAddonsSettings {
    public static boolean insideBlockTicks = false;
    public static boolean isDisplayingChunk = false;
    public static ChunkPos chunkPos;
    public static int chunkRadius = 5;
    public static World chunkWorld;

    public enum InstantTileTickEnum {
        FALSE,
        TRUE,
        CRASHFIX,
        VANILLA,
        VANILLACRASHFIX
    }

    public enum BlockTransparencyEnum {
        NORMAL,
        SOLID,
        TRANSPARENT,
        INVERSE
    }

    public enum blueSkullTest {
        VANILLA,
        ENTITY,
        PASSIVE,
        ALL
    }

    private final static String WEIRD = "weird";

    @Rule(
            desc = "Enables the /weird command",
            extra = "The /weird command allows for chunk monitoring as well as other weird additions that need more arguments then a carpet rule",
            validate = {Validator._COMMAND_LEVEL_VALIDATOR.class},
            options = {"ops","false","true"},
            category = {WEIRD}
    )
    public static String commandWeird = "ops";

    @Rule(
            desc = "Change the delay length of observers (how long it takes to turn on)",
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
            category = {WEIRD,CREATIVE}
    )
    public static int observerDelay = 2;

    @Rule(
            desc = "Change the pulse length of observers (how long it stays on)",
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
            category = {WEIRD,CREATIVE}
    )
    public static int observerPulse = 2;

    @Rule(
            desc = "Allows the use of spigot formatting inside of anvils",
            extra = "To see formatting codes use /weird color",
            category = {WEIRD,EXPERIMENTAL}
    )
    public static boolean anvilColorFormatting = false;

    @Rule(
            desc = "Pistons will extend and retract with 1 tick of delay",
            category = {WEIRD,CREATIVE}
    )
    public static boolean fastPistons = false;


    @Rule(
            desc = "Makes moving_piston (B36) movable. Requires MovableBlockEntities to be on",
            validate = movableMovingPistonValidator.class,
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean movableMovingPiston = false;

    @Rule(
            desc = "Basically all setBlock calls will use this number",
            extra = "Custom block updates from: -1 to 127 | use -2 to disable",
            validate = blockUpdateValiator.class,
            strict = false,
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static int blockUpdateHell = -2;

    @Rule(
            desc = "Makes it so that sponges can absorb all blocks :}",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeEverything = false;

    @Rule(
            desc = "Change the max amount of that a sponge can absorb",
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false,
            category = {WEIRD,CREATIVE}
    )
    public static int spongeLimit = 64;

    @Rule(
            desc = "The sponge now absorbs lava instead of water, you happy now?",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeLava = false;

    @Rule(
            desc = "Removes all restrictions that sponges have",
            extra = "You can still change the spongeLimit to avoid crashing. spongeLimit is ignored if its the default value!",
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean spongeInfinite = false;

    @Rule(
            desc = "Modify sponges so that the blocks they remove don't create blockUpdates",
            extra = "Only noticeable when using spongeInfinite",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeFaster = false;

    @Rule(
            desc = "Sponge, sponges itself",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeCeption = false;

    @Rule(
            desc = "Sponge does not become wet when used",
            extra = "If spongeCeption is enabled, this will not do anything",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeReusable = false;

    @Rule(
            desc = "Sponge will prevent block explosion damage if its in the tnt list of blocks to break",
            category = {WEIRD,CREATIVE}
    )
    public static boolean spongeAbsorbsExplosions = false;

    @Rule(
            desc = "Allows you to enchant past the highest vanilla enchant limit using /enchant",
            extra = "Also makes it so every enchantment works on all items",
            category = {WEIRD,CREATIVE}
    )
    public static boolean enchantmentOverride = false;

    @Rule(
            desc = "Makes it so that tridents can move just as fast as they usually do on the y-axis on the x & z axis",
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean uncappedTridentSpeed = false;

    @Rule(
            desc = "Customizable execute volume limit",
            extra = "It's weird cause nobody knows this limit exists xD",
            options = {"32768", "250000", "1000000"},
            validate = ExecuteBlockLimit.class,
            strict = false,
            category = {WEIRD,CREATIVE}
    )
    public static int executeBlockLimit = 32768;

    @Rule(
            desc = "Scaffolding breaking rules. Change how scaffolding breaks", //be yourself xD
            options = {"break","float","gravity"},
            category = {WEIRD,CREATIVE}
    )
    public static String scaffoldingBreaking = "break";

    @Rule(
            desc = "Instant-Liquid flow in 1.16, basically instant tile ticks but for liquids",
            category = {WEIRD,CREATIVE}
    )
    public static boolean instantLiquidFlow = false;

    @Rule(
            desc = "Makes all liquid ticks happen within the next tick",
            category = {WEIRD,CREATIVE}
    )
    public static boolean fastLiquidFlow = false;

    @Rule(
            desc = "Makes all tile ticks happen within the next tick",
            category = {WEIRD,CREATIVE}
    )
    public static boolean fastTileTicks = false;

    @Rule(
            desc = "Re-implementing instant tile ticks into 1.16. Although it's more of an imitation",
            category = {WEIRD,CREATIVE}
    )
    public static boolean instantTileTick = false;

    @Rule(
            desc = "A game mechanic for instant tick ticks. Requires a lit redstone lamp to be moved in border chunks surrounded by Ticking Chunks",
            extra = {"Also makes it so that instantTileTick crashes the server when generating new chunks","vanilla makes it so that instantLiquidFlow works with instantTileTicks like its does in 1.12"},
            category = {WEIRD}
    )
    public static InstantTileTickEnum instantTileTickMechanic = InstantTileTickEnum.FALSE;

    @Rule(
            desc = "Re-implementing instant fall into 1.16, what could possibly go wrong",
            category = {WEIRD,CREATIVE}
    )
    public static boolean instantFall = false;

    @Rule(
            desc = "A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks.",
            extra = "Also makes it so that instantFall turns off when generating new chunks.",
            category = {WEIRD,EXPERIMENTAL}
    )
    public static boolean instantFallMechanic = false;

    @Rule(
            desc = "Prevents all players from breaking blocks",
            category = {WEIRD,CREATIVE}
    )
    public static boolean preventBreaking = false;

    @Rule(
            desc = "This is a joke, it actually just prevents all chunks from being unloaded ;)",
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean permaloader = false;

    @Rule(
            desc = "Makes it so blue skulls from wither always spawn to test if your wither based farm will break",
            extra = {"Entity: Makes it so skulls have a 100% chance of spawning when wither is targeting an entity","Passive: Makes it so theres 100% chance of blue skulls spawning passively"},
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static blueSkullTest blueSkullTesting = blueSkullTest.VANILLA;

    @Rule(
            desc = "Makes all the blocks in the game act like transparent blocks!",
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static BlockTransparencyEnum blockTransparency = BlockTransparencyEnum.NORMAL;

    @Rule(
            desc = "Everything works the same past the world border",
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean worldborderSpecial = false;

    @Rule(
            desc = "Makes it so that cats on chests do not prevent the chest from opening",
            category = {WEIRD}
    )
    public static boolean catOnChestBypass = false;

    @Rule(
            desc = "Elytra won't take damage from flight",
            extra = "Requested by Pixeils",
            category = {WEIRD,EXPERIMENTAL}
    )
    public static boolean totallyLegitElytra = false;

    @Rule(
            desc = "Allows you to toggle onlineMode",
            validate = onlineModeValidator.class,
            category = {WEIRD,CREATIVE,EXPERIMENTAL}
    )
    public static boolean onlineMode = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.isOnlineMode();

    @Rule(
            desc = "Allows the dragon egg to be mined",
            category = {WEIRD, EXPERIMENTAL}
    )
    public static boolean breakableDragonEgg = false;

    @Rule(
            desc = "Ender Dragon always drops a dragon egg when killed",
            category = {WEIRD, EXPERIMENTAL}
    )
    public static boolean dragonAlwaysDropsEgg = false;

    @Rule(
            desc = "Shulkers can become cancer, just place a bunch of shulkers next to each other!",
            category = {WEIRD, CREATIVE}
    )
    public static boolean shulkerCancer = false;

    @Rule(
            desc = "Feather falling prevents trampling crops",
            category = {WEIRD, CREATIVE}
    )
    public static boolean featherFallingPlus = false;

    /*@Rule(
            desc = "Placing a redstone on top of redstone ore will act as a zero tick generator",
            extra = {"If a number is entered, it will be the delay in ticks between pulses","Enabling this will cause all redstone ore blocks to be transparent"},
            options = {"false","true","1"},
            validate = zeroTickGeneratorValidator.class,
            category = {CREATIVE, WEIRD, EXPERIMENTAL}
    )
    public static String zeroTickGeneratorBlock = "false";*/

    /*

    Validators

     */

    private static class movableMovingPistonValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            return CarpetSettings.movableBlockEntities ? newValue : false;
        }
        @Override
        public String description() { return "MovableBlockEntities must be enabled to use this";}
    }

    private static class onlineModeValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            source.getMinecraftServer().setOnlineMode(currentRule.getBoolValue());
            return currentRule.getBoolValue();
        }
    }

    /*private static class zeroTickGeneratorValidator extends Validator<String> {
        @Override
        public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            if (!currentRule.get().equals(newValue)) {
                if (newValue.equalsIgnoreCase("false")) {
                    zeroTickGeneratorSetting = -1;
                } else if (newValue.equalsIgnoreCase("true")) {
                    zeroTickGeneratorSetting = 0;
                } else {
                    int parsedInt = getInteger(newValue);
                    if (parsedInt <= 0) {
                        zeroTickGeneratorSetting = -1;
                        return "false";
                    } else {
                        zeroTickGeneratorSetting = parsedInt;
                    }
                }
            }
            return newValue;
        }
        @Override
        public String description() {
            return "Cannot be negative, can be true, false, or # > 0";
        }
    }*/

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

    private static class ExecuteBlockLimit extends Validator<Integer> {
        @Override public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string) {
            return (newValue>0 && newValue <= 20000000) ? newValue : null;
        }
        @Override
        public String description() { return "You must choose a value from 1 to 20M";}
    }

}
