package net.rockystudios.lastbasestanding.render

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI
import dev.ftb.mods.ftbteams.data.AbstractTeamBase
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
import java.util.*

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
        var text = "Trophy"
        try {
            val teamIDString = entity.teamId
            println("Raw teamID: '$teamIDString'")
            val teamUUID = try {
                UUID.fromString(teamIDString?.trim())
            } catch (e: IllegalArgumentException) {
                null
            }
            val teamName = (FTBTeamsAPI.api().manager.getTeamByID(teamUUID).get() as AbstractTeamBase).displayName
            text = "$teamName"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val textRenderer = MinecraftClient.getInstance().textRenderer
        val x = -textRenderer.getWidth(text) / 2

        textRenderer.draw(
            Text.literal(text),
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
