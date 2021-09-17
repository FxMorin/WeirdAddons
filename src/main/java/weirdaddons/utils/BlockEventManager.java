package weirdaddons.utils;

public class BlockEventManager {
    private static boolean is_frozen = false;
    private static int steps = 0;

    public static void setFrozen(boolean isFrozen) {
        BlockEventManager.is_frozen = isFrozen;
    }

    public static boolean isFrozen() {
        return is_frozen;
    }

    public static void setSteps(int steps) {
        BlockEventManager.steps = steps;
    }

    public static int getSteps() {return BlockEventManager.steps;}

    public static boolean doStep() {
        if (BlockEventManager.steps > 0) {
            BlockEventManager.steps--;
            return true;
        }
        return BlockEventManager.steps == -1;
    }

    public static void setSkip(boolean shouldSkip) {
        BlockEventManager.steps = shouldSkip ? -1 : 0;
    }

    public static boolean getSkip() {
        return BlockEventManager.steps == -1;
    }

    public static void finishTick() {
        BlockEventManager.steps = 0;
    }
}
