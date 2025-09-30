package net.rockystudios.lastbasestanding.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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

        if (!(inv instanceof PlayerInventory)) return;

        ItemStack slotStack = slot.getStack();
        ItemStack cursorStack = self.getCursorStack();

        ItemsNotAllowedInContainers.Companion.getItemsNotAllowedInContainers().forEach(item -> {
            if (slotStack.isOf(item) || cursorStack.isOf(item)) {
                ci.cancel();

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.sendMessage(
                            Text.literal("You cannot put this item in a container!"),
                            false
                    );
                }
            }
        });
    }
}
