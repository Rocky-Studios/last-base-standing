package net.rockystudios.lastbasestanding

import dev.ftb.mods.ftbteams.data.PartyTeam
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.rockystudios.lastbasestanding.items.ModItems

object TrophyManager {
    fun onTeamCreated(team: PartyTeam, player: ServerPlayerEntity) {
        val trophyStack = ItemStack(ModItems.TROPHY, 1)
        val nbt = trophyStack.orCreateNbt
        nbt.putString("TeamID", team.id.toString())
        player.giveItemStack(trophyStack)
    }


}