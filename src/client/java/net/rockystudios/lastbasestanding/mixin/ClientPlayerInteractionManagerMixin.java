package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.rockystudios.lastbasestanding.BreakTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackBlock", at = @At("HEAD"))
    private void onStartBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BreakTracker.INSTANCE.setBreakingPos(pos);
    }
}