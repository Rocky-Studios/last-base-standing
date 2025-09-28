package net.rockystudios.lastbasestanding

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object LastBaseStanding : ModInitializer {
    private val logger = LoggerFactory.getLogger("last-base-standing")

	override fun onInitialize() {
		logger.info("Hello Fabric world!")


	}
}