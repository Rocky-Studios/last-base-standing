package net.rockystudios.lastbasestanding.items

import net.minecraft.item.Item
import net.minecraft.item.Items

class ItemsNotAllowedInContainers {
    companion object {
        val itemsNotAllowedInContainers: MutableList<Item> = mutableListOf(
            Items.DRAGON_EGG,
            ModItems.TROPHY
        )
    }
}
