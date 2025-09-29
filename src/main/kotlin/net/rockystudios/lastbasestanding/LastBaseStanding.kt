package net.rockystudios.lastbasestanding

import net.fabricmc.api.ModInitializer
import net.rockystudios.lastbasestanding.hardening.HardeningHandler
import net.rockystudios.lastbasestanding.items.ModItems

object LastBaseStanding : ModInitializer {

	override fun onInitialize() {
		ModItems.initialize()
		HardeningHandler.initHardening()
	}

	const val MOD_ID = "last-base-standing"

}