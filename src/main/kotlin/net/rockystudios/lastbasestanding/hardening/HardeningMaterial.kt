package net.rockystudios.lastbasestanding.hardening

import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier

class HardeningMaterial(val item: Item, val hardness: Float, val sprite: Identifier) {
    companion object {
        val materials: MutableList<HardeningMaterial> = mutableListOf(
            HardeningMaterial(Items.DIAMOND, 4f, Identifier("last-base-standing", "textures/overlay/diamond.png")),
            HardeningMaterial(Items.NETHERITE_INGOT, 10f, Identifier("last-base-standing", "textures/overlay/netherite.png"))
        )
    }
}
