package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.rockystudios.lastbasestanding.BreakTracker;
import net.rockystudios.lastbasestanding.HardeningHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackBlock", at = @At("HEAD"))
    private void onStartBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BreakTracker.INSTANCE.setBreakingPos(pos);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && HardeningHandler.INSTANCE.isHardened(pos)) {
            for (int i = 0; i < 20; i++) {
                double offsetX = client.world.random.nextDouble();
                double offsetY = client.world.random.nextDouble();
                double offsetZ = client.world.random.nextDouble();
                client.world.addParticle(
                        ParticleTypes.END_ROD,
                        pos.getX() + offsetX,
                        pos.getY() + offsetY,
                        pos.getZ() + offsetZ,
                        0, 0, 0
                );
            }
        }
    }
}