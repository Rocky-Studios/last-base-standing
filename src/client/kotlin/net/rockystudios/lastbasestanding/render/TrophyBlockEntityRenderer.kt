package net.rockystudios.lastbasestanding.render

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.math.RotationAxis
import net.rockystudios.lastbasestanding.blockentity.TrophyBlockEntity

@Environment(EnvType.CLIENT)
class TrophyBlockEntityRenderer(context: BlockEntityRendererFactory.Context) :
    BlockEntityRenderer<TrophyBlockEntity> {

    override fun render(
        entity: TrophyBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val camera = MinecraftClient.getInstance().gameRenderer.camera

        matrices.push()

        // Position above the block
        matrices.translate(0.5, 1.2, 0.5)

        // Rotate to face the camera
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.yaw))
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.pitch))

        // Scale down the text
        matrices.scale(-0.025f, -0.025f, 0.025f)

        val text = Text.literal("Trophy")
        val textRenderer = MinecraftClient.getInstance().textRenderer
        val x = -textRenderer.getWidth(text) / 2

        textRenderer.draw(
            text,
            x.toFloat(),
            0f,
            0xFFFFFF,
            false,
            matrices.peek().positionMatrix,
            vertexConsumers,
            TextRenderer.TextLayerType.NORMAL,
            0,
            light
        )

        matrices.pop()
    }
}
