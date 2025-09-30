package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.rockystudios.lastbasestanding.items.ItemsNotAllowedInContainers;
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
        if (ItemsNotAllowedInContainers.Companion.getItemsNotAllowedInContainers().stream().anyMatch(stack::isOf)) {
            ScreenHandler self = (ScreenHandler) (Object) this;
            for (int i = startIndex; i < endIndex && i < self.slots.size(); i++) {
                var slot = self.slots.get(i);
                Inventory inv = slot.inventory;
                // Only allow inserting into chests
                if (!(inv instanceof ChestBlockEntity || inv instanceof DoubleInventory)) {
                    cir.setReturnValue(false); // cancel shift-click
                    return;
                }
            }
        }
    }
}

