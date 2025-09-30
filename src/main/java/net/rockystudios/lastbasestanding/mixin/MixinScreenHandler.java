package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.rockystudios.lastbasestanding.items.ItemsNotAllowedInContainers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MixinScreenHandler {

    @Inject(
            method = "onSlotClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stopDragonEggDragDrop(
            int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci
    ) {
        if (player.getWorld().isClient) return;

        ScreenHandler self = (ScreenHandler) (Object) this;
        if (slotIndex < 0 || slotIndex >= self.slots.size()) return;

        var slot = self.slots.get(slotIndex);
        Inventory inv = slot.inventory;


        ItemStack slotStack = slot.getStack();
        ItemStack cursorStack = self.getCursorStack();

        // Only block if trying to put the item into the chest
        if ((inv instanceof ChestBlockEntity || inv instanceof DoubleInventory)
                && ItemsNotAllowedInContainers.Companion.getItemsNotAllowedInContainers().stream()
                .anyMatch(cursorStack::isOf)) {
            ci.cancel();
            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.sendMessage(
                        Text.literal("You cannot put this item in a container!"),
                        false
                );
            }
        }

    }
}
