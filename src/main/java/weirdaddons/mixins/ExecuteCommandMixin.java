package weirdaddons.mixins;

import net.minecraft.server.command.ExecuteCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ExecuteCommand.class)
public class ExecuteCommandMixin {

    @ModifyConstant(method = "Lnet/minecraft/server/command/ExecuteCommand;testBlocksCondition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Z)Ljava/util/OptionalInt;", constant = @Constant(intValue = 32768))
    private static int muchLarger(int maxSize) {
        return WeirdAddonsSettings.executeBlockLimit;
    }
}
