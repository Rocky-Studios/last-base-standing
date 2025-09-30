package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public abstract class MixinScreenHandlerInsert {

    @Inject(
            method = "insertItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stopDragonEggShiftClick(
            ItemStack stack,
            int startIndex,
            int endIndex,
            boolean fromLast,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (stack.isOf(Items.DRAGON_EGG)) {
            ScreenHandler self = (ScreenHandler) (Object) this;

            // Figure out if the target range is a chest
            for (int i = startIndex; i < endIndex && i < self.slots.size(); i++) {
                var slot = self.slots.get(i);
                Inventory inv = slot.inventory;

                boolean isChest = inv instanceof ChestBlockEntity
                        || (inv instanceof DoubleInventory);//di &&
                //(di.getFirst() instanceof ChestBlockEntity || di.getSecond() instanceof ChestBlockEntity));

                if (isChest) {
                    cir.setReturnValue(false); // cancel shift-click
                    return;
                }
            }
        }
    }
}
