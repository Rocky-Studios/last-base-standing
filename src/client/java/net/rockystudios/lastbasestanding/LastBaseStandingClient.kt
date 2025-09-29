package net.rockystudios.lastbasestanding

import net.fabricmc.api.ClientModInitializer

object LastBaseStandingClient : ClientModInitializer {

    override fun onInitializeClient() {
        OverlayManager.registerOverlay()
    }


}