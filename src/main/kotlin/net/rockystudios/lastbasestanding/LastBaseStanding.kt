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
    private val logger = LoggerFactory.getLogger("last-base-standing")

	override fun onInitialize() {
		UseBlockCallback.EVENT.register(UseBlockCallback { player, world, hand, hitResult ->
			if (!world.isClient && player.getStackInHand(hand).item == Items.DIAMOND) {
				val pos = hitResult.blockPos
				HardeningHandler.harden(pos)
				player.sendMessage(Text.of("Block hardened!"), true)

				// Spawn particles at the block position (server-side)
				if (world is ServerWorld) {
					for (i in 0 until 20) {
						val offsetX = world.random.nextDouble()
						val offsetY = world.random.nextDouble()
						val offsetZ = world.random.nextDouble()
						world.spawnParticles(
							ParticleTypes.END_ROD,
							pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
							1, 0.0, 0.0, 0.0, 0.0
						)
					}
				}
				ActionResult.SUCCESS
			} else {
				ActionResult.PASS
			}
		})
	}
}