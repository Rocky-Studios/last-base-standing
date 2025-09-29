package net.rockystudios.lastbasestanding.blockentity

import ModBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.rockystudios.lastbasestanding.LastBaseStanding

object ModBlockEntities {
    lateinit var TROPHY: BlockEntityType<TrophyBlockEntity>

    fun register() {
        TROPHY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier(LastBaseStanding.MOD_ID, "trophy"),
            FabricBlockEntityTypeBuilder.create(::TrophyBlockEntity, ModBlocks.TROPHY).build(null)
        )

    }
}