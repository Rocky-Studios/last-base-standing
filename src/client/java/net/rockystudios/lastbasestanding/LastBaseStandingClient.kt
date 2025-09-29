package net.rockystudios.lastbasestanding

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.util.Identifier
import net.rockystudios.lastbasestanding.hardening.HardeningHandler

object LastBaseStandingClient : ClientModInitializer {

    override fun onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register { context ->
            val matrices = context.matrixStack()
            val camera = context.camera()
            val camX = camera.pos.x
            val camY = camera.pos.y
            val camZ = camera.pos.z
            val hardenedBlocks = HardeningHandler.getHardenedBlockPositions()
            val client = MinecraftClient.getInstance()
            val texture = client.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
                .apply(Identifier("minecraft", "block/stone")) // Example: stone texture

            val vertexConsumer = context.consumers()?.getBuffer(RenderLayer.getTranslucent())

            for (pos in hardenedBlocks) {
                val x = pos.x.toDouble() - camX
                val y = pos.y.toDouble() - camY
                val z = pos.z.toDouble() - camZ


                // Example: render the top face as a textured quad
                val yOffset = 0.001

                drawTopTexturedQuad(
                    matrices,
                    vertexConsumer,
                    x, y + 1.0 + yOffset, z,
                    x + 1.0, z + 1.0,
                    texture.minU, texture.minV, texture.maxU, texture.maxV,
                    1.0f, 1.0f, 1.0f, 1.0f
                )
                // Repeat for other faces as needed
            }
        }
    }

    fun drawTopTexturedQuad(
        matrices: net.minecraft.client.util.math.MatrixStack,
        vertexConsumer: VertexConsumer?,
        x1: Double, y: Double, z1: Double,
        x2: Double, z2: Double,
        minU: Float, minV: Float, maxU: Float, maxV: Float,
        r: Float, g: Float, b: Float, a: Float
    ) {
        val posMatrix = matrices.peek().positionMatrix
        val normalMatrix = matrices.peek().normalMatrix

        // Correct winding order: (x1, z1) -> (x1, z2) -> (x2, z2) -> (x2, z1)
        vertexConsumer?.vertex(posMatrix, x1.toFloat(), y.toFloat(), z1.toFloat())
            ?.color(r, g, b, a)
            ?.texture(minU, minV)
            ?.overlay(net.minecraft.client.render.OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, 0f, 1f, 0f)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x1.toFloat(), y.toFloat(), z2.toFloat())
            ?.color(r, g, b, a)
            ?.texture(minU, maxV)
            ?.overlay(net.minecraft.client.render.OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, 0f, 1f, 0f)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x2.toFloat(), y.toFloat(), z2.toFloat())
            ?.color(r, g, b, a)
            ?.texture(maxU, maxV)
            ?.overlay(net.minecraft.client.render.OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, 0f, 1f, 0f)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x2.toFloat(), y.toFloat(), z1.toFloat())
            ?.color(r, g, b, a)
            ?.texture(maxU, minV)
            ?.overlay(net.minecraft.client.render.OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, 0f, 1f, 0f)
            ?.next()
    }




}
