package net.rockystudios.lastbasestanding

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

object LastBaseStanding : ModInitializer {
    private val logger = LoggerFactory.getLogger("last-base-standing")

	override fun onInitialize() {
		UseBlockCallback.EVENT.register(UseBlockCallback { player, world, hand, hitResult ->
			if (!world.isClient && hand == Hand.MAIN_HAND) {
				val heldItem = player.getStackInHand(hand)
				if (heldItem.item == Items.DIAMOND) {
					val pos = hitResult.blockPos
					HardeningHandler.harden(pos)
					player.sendMessage(Text.literal("Block hardened!"), true)
					return@UseBlockCallback ActionResult.SUCCESS
				}
			}
			ActionResult.PASS
		})
	}
}