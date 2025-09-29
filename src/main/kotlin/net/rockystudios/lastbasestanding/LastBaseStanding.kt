package net.rockystudios.lastbasestanding

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.item.Items
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

object LastBaseStanding : ModInitializer {

	override fun onInitialize() {
		HardeningHandler.initHardening()
	}
}