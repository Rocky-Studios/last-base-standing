package net.rockystudios.lastbasestanding

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.WorldRenderer
import net.rockystudios.lastbasestanding.hardening.HardeningHandler
import kotlin.text.toDouble

object LastBaseStandingClient : ClientModInitializer {

    // Explanation: Subtract camera position from block position for correct rendering
    override fun onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register { context ->
            val matrices = context.matrixStack()
            val camera = context.camera()
            val camX = camera.pos.x
            val camY = camera.pos.y
            val camZ = camera.pos.z
            val hardenedBlocks = HardeningHandler.getHardenedBlockPositions()

            for (pos in hardenedBlocks) {
                val vertexConsumer = context.consumers()?.getBuffer(RenderLayer.getDebugLineStrip(0.1))
                WorldRenderer.drawBox(
                    matrices,
                    vertexConsumer,
                    pos.x.toDouble() - camX, pos.y.toDouble() - camY, pos.z.toDouble() - camZ,
                    pos.x.toDouble() + 1.0 - camX, pos.y.toDouble() + 1.0 - camY, pos.z.toDouble() + 1.0 - camZ,
                    1.0f, 0.5f, 0.0f, // Orange color
                    1.0f // Full opacity
                )
            }
        }
    }

}
