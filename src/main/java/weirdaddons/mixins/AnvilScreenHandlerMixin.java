package weirdaddons.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ScreenHandler {

    protected AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow
    private String newItemName;

    @Shadow
    public abstract void updateResult();

    @Inject(at = @At("INVOKE"), method = "setNewItemName", cancellable = true)
    public void setNewItemName(String string, CallbackInfo info) {
        if (WeirdAddonsSettings.anvilColorFormatting) {

            info.cancel();

            if (newItemName.matches(".*&(0|1|2|3|4|5|6|7|8|9|a|b|c|d|e|f|k|l|m|n|o|r).*")) {
                this.newItemName = string.replace("&", "\u00A7");
            }

            if (this.getSlot(2).hasStack()) {
                ItemStack itemStack = this.getSlot(2).getStack();
                if (StringUtils.isBlank(string)) {
                    itemStack.removeCustomName();
                } else {
                    itemStack.setCustomName(new LiteralText(this.newItemName));
                }
            }

            this.updateResult();
        }
    }
}

