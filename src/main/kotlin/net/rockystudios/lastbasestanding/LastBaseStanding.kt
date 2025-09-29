package net.rockystudios.lastbasestanding

import net.fabricmc.api.ModInitializer
import net.rockystudios.lastbasestanding.hardening.HardeningHandler

object LastBaseStanding : ModInitializer {

	override fun onInitialize() {
		HardeningHandler.initHardening()
	}
}