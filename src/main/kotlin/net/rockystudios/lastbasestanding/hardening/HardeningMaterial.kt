package net.rockystudios.lastbasestanding.hardening

import net.minecraft.item.Item
import net.minecraft.item.Items

class HardeningMaterial(val item: Item, val hardness: Float) {
    companion object {
        val materials: MutableList<HardeningMaterial> = mutableListOf(
            HardeningMaterial(Items.DIAMOND, 4f),
            HardeningMaterial(Items.NETHERITE_INGOT, 10f)
        )
    }
}
