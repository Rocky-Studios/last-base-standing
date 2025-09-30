package net.rockystudios.lastbasestanding.items

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity


class TrophyBlockItem(block: Block?, settings: Settings) : BlockItem(block, settings) {
    override fun getRarity(stack: ItemStack?): Rarity {
        return Rarity.EPIC // Purple name!
    }
}
