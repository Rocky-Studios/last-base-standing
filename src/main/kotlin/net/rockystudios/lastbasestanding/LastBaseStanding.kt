package net.rockystudios.lastbasestanding

import ModBlocks
import net.fabricmc.api.ModInitializer
import net.rockystudios.lastbasestanding.blockentity.ModBlockEntities
import net.rockystudios.lastbasestanding.hardening.HardeningHandler
import net.rockystudios.lastbasestanding.items.ModItems

object LastBaseStanding : ModInitializer {

	override fun onInitialize() {
		ModItems.initialize()
		ModBlocks.register()
		ModBlockEntities.register()
		HardeningHandler.initHardening()
	}

	const val MOD_ID = "last-base-standing"

}