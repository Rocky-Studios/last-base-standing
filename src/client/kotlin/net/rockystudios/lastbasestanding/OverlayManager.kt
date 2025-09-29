package net.rockystudios.lastbasestanding

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import net.rockystudios.lastbasestanding.hardening.HardeningHandler

object OverlayManager {

    fun registerOverlay()
    {
        WorldRenderEvents.AFTER_ENTITIES.register { context ->
            val matrices = context.matrixStack()
            val camera = context.camera()
            val camX = camera.pos.x
            val camY = camera.pos.y
            val camZ = camera.pos.z
            val hardenedBlocks = HardeningHandler.getHardenedBlockPositions()


            for (pos in hardenedBlocks) {
                val overlayTexture = HardeningHandler.getHardenedBlock(pos).material.sprite
                val renderLayer = RenderLayer.getEntityTranslucent(overlayTexture)

                val vertexConsumer = context.consumers()?.getBuffer(renderLayer)

                val x = pos.x.toDouble() - camX
                val y = pos.y.toDouble() - camY
                val z = pos.z.toDouble() - camZ

                val offset = 0.001 // prevent z-fighting

                // Top
                drawTexturedQuad(matrices, vertexConsumer, x, y + 1.0 + offset, z, x, y + 1.0 + offset, z + 1.0, x + 1.0, y + 1.0 + offset, z + 1.0, x + 1.0, y + 1.0 + offset, z, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, 0f, 1f, 0f)
                // Bottom
                drawTexturedQuad(matrices, vertexConsumer, x, y - offset, z, x + 1.0, y - offset, z, x + 1.0, y - offset, z + 1.0, x, y - offset, z + 1.0, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, 0f, -1f, 0f)
                // North
                drawTexturedQuad(matrices, vertexConsumer, x, y, z - offset, x, y + 1.0, z - offset, x + 1.0, y + 1.0, z - offset, x + 1.0, y, z - offset, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, 0f, 0f, -1f)
                // South
                drawTexturedQuad(matrices, vertexConsumer, x, y, z + 1.0 + offset, x + 1.0, y, z + 1.0 + offset, x + 1.0, y + 1.0, z + 1.0 + offset, x, y + 1.0, z + 1.0 + offset, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, 0f, 0f, 1f)
                // West
                drawTexturedQuad(matrices, vertexConsumer, x - offset, y, z, x - offset, y, z + 1.0, x - offset, y + 1.0, z + 1.0, x - offset, y + 1.0, z, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, -1f, 0f, 0f)
                // East
                drawTexturedQuad(matrices, vertexConsumer, x + 1.0 + offset, y, z, x + 1.0 + offset, y + 1.0, z, x + 1.0 + offset, y + 1.0, z + 1.0, x + 1.0 + offset, y, z + 1.0, 0f, 0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 0.8f, 1f, 0f, 0f)

            }
        }
    }

    private fun drawTexturedQuad(
        matrices: MatrixStack,
        vertexConsumer: VertexConsumer?,
        x1: Double, y1: Double, z1: Double,
        x2: Double, y2: Double, z2: Double,
        x3: Double, y3: Double, z3: Double,
        x4: Double, y4: Double, z4: Double,
        minU: Float, minV: Float, maxU: Float, maxV: Float,
        r: Float, g: Float, b: Float, a: Float,
        normalX: Float, normalY: Float, normalZ: Float
    ) {
        val posMatrix = matrices.peek().positionMatrix
        val normalMatrix = matrices.peek().normalMatrix

        vertexConsumer?.vertex(posMatrix, x1.toFloat(), y1.toFloat(), z1.toFloat())
            ?.color(r, g, b, a)
            ?.texture(minU, minV)
            ?.overlay(OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, normalX, normalY, normalZ)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x2.toFloat(), y2.toFloat(), z2.toFloat())
            ?.color(r, g, b, a)
            ?.texture(minU, maxV)
            ?.overlay(OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, normalX, normalY, normalZ)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x3.toFloat(), y3.toFloat(), z3.toFloat())
            ?.color(r, g, b, a)
            ?.texture(maxU, maxV)
            ?.overlay(OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, normalX, normalY, normalZ)
            ?.next()

        vertexConsumer?.vertex(posMatrix, x4.toFloat(), y4.toFloat(), z4.toFloat())
            ?.color(r, g, b, a)
            ?.texture(maxU, minV)
            ?.overlay(OverlayTexture.DEFAULT_UV)
            ?.light(0xF000F0)
            ?.normal(normalMatrix, normalX, normalY, normalZ)
            ?.next()
    }
}