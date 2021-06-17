package weirdaddons.mixins;

import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

import java.util.UUID;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {

    @Final @Shadow private ServerBossBar bossBar;
    @Shadow private UUID dragonUuid;
    @Shadow protected abstract void generateEndPortal(boolean previouslyKilled);
    @Shadow protected abstract void generateNewEndGateway();
    @Final @Shadow private ServerWorld world;
    @Shadow private boolean previouslyKilled;
    @Shadow private boolean dragonKilled;

    @Inject(method="dragonKilled(Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;)V",at=@At("HEAD"),cancellable = true)
    public void dragonKilled(EnderDragonEntity dragon, CallbackInfo ci) {
        if (WeirdAddonsSettings.dragonAlwaysDropsEgg) {
            if (dragon.getUuid().equals(this.dragonUuid)) {
                this.bossBar.setPercent(0.0F);
                this.bossBar.setVisible(false);
                this.generateEndPortal(true);
                this.generateNewEndGateway();
                this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.DRAGON_EGG.getDefaultState());
                this.previouslyKilled = this.dragonKilled = true;
            }
            ci.cancel();
        }
    }
}