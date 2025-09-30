package net.rockystudios.lastbasestanding

import dev.ftb.mods.ftbteams.data.PartyTeam
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.rockystudios.lastbasestanding.items.ModItems

object TrophyManager {
    fun onTeamCreated(team: PartyTeam, player: ServerPlayerEntity) {
        player.giveItemStack(ItemStack(ModItems.TROPHY, 1))
    }
}