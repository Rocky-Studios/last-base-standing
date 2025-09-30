package net.rockystudios.lastbasestanding

import ModBlocks
import net.fabricmc.api.ModInitializer
import net.rockystudios.lastbasestanding.blockentity.ModBlockEntities
import net.rockystudios.lastbasestanding.hardening.HardeningHandler
import net.rockystudios.lastbasestanding.items.ModItems
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LastBaseStanding : ModInitializer {

	override fun onInitialize() {
		ModItems.initialize()
		ModBlocks.register()
		ModBlockEntities.register()
		HardeningHandler.initHardening()
	}

	private val logger = LoggerFactory.getLogger("last-base-standing")
	fun getLogger(): Logger {
		return logger
	}
	const val MOD_ID = "last-base-standing"
}