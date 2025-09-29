package net.rockystudios.lastbasestanding.items

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.rockystudios.lastbasestanding.LastBaseStanding


object ModItems {
    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
            .register(ModifyEntries { itemGroup: FabricItemGroupEntries? -> itemGroup?.add(HAMMER) })
    }

    fun registerItem(item: Item, id: String): Item {
        val itemID = Identifier(LastBaseStanding.MOD_ID, id)

        val registeredItem = Registry.register(Registries.ITEM, itemID, item)
        return registeredItem
    }

    val HAMMER: Item = registerItem(
        Item(FabricItemSettings().maxCount(1)), "hammer"
    )
}