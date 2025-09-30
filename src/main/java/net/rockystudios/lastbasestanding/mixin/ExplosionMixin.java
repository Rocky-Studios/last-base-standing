package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.rockystudios.lastbasestanding.hardening.HardeningHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ExplosionBehavior.class)
public class ExplosionMixin {

    @Inject(
            method = "getBlastResistance",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideResistance(
            Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, CallbackInfoReturnable<Optional<Float>> cir
    ) {
        if (HardeningHandler.INSTANCE.isHardened(pos)) {
            float original = blockState.getBlock().getBlastResistance();
            float multiplier = HardeningHandler.INSTANCE.getHardenedBlock(pos).getMaterial().getHardness();
            cir.setReturnValue(Optional.of(original * multiplier));
        }
    }

}