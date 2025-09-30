package net.rockystudios.lastbasestanding.mixin;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.rockystudios.lastbasestanding.hardening.BreakTracker;
import net.rockystudios.lastbasestanding.hardening.HardeningHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void slowDownHardenedBlocks(BlockState state, CallbackInfoReturnable<Float> cir) {
        System.out.println("[Mixin] getBlockBreakingSpeed called for block: " + state.getBlock().toString());
        BlockPos pos = BreakTracker.INSTANCE.getBreakingPos();
        if (pos != null && HardeningHandler.INSTANCE.isHardened(pos)) {
            System.out.println("[Mixin] Block is hardened at pos: " + pos);
            var hardnessMultiplier = HardeningHandler.INSTANCE.getHardenedBlock(pos).getMaterial().getHardness();
            cir.setReturnValue(cir.getReturnValue() * 1/hardnessMultiplier);
        }
    }
}