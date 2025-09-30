package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.entity.player.PlayerInventory;
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

        ItemsNotAllowedInContainers.Companion.getItemsNotAllowedInContainers().forEach(item -> {
            if (stack.isOf(item)) {
                ScreenHandler self = (ScreenHandler) (Object) this;

                // Figure out if the target range is a chest
                for (int i = startIndex; i < endIndex && i < self.slots.size(); i++) {
                    var slot = self.slots.get(i);
                    Inventory inv = slot.inventory;

                    if (!(inv instanceof PlayerInventory)) {
                        cir.setReturnValue(false); // cancel shift-click
                        return;
                    }
                }
            }
        });
    }
}
