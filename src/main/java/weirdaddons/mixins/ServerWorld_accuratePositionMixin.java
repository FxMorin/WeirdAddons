package weirdaddons.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weirdaddons.WeirdAddonsSettings;

@Mixin(ServerWorld.class)
public class ServerWorld_accuratePositionMixin {
    @Inject(method= "tickEntity(Lnet/minecraft/entity/Entity;)V",at=@At("HEAD"))
    public void updatePosEveryTick(Entity me, CallbackInfo ci) {
        if (WeirdAddonsSettings.accurateEntityPositions && !me.world.isClient && !(me instanceof ServerPlayerEntity)) {
            ((ServerWorld) me.getEntityWorld()).getChunkManager().sendToNearbyPlayers(me, new EntityPositionS2CPacket(me));
        }
    }
}
