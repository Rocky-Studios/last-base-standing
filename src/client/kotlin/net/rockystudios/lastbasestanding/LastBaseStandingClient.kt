package net.rockystudios.lastbasestanding

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.rockystudios.lastbasestanding.blockentity.ModBlockEntities
import net.rockystudios.lastbasestanding.render.TrophyBlockEntityRenderer

object LastBaseStandingClient : ClientModInitializer {

    override fun onInitializeClient() {
        OverlayManager.registerOverlay()
        BlockEntityRendererRegistry.register(ModBlockEntities.TROPHY) { TrophyBlockEntityRenderer(it) }

    }


}