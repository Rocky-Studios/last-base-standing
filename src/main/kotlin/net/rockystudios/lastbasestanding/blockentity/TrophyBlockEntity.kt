package net.rockystudios.lastbasestanding.blockentity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos

class TrophyBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(ModBlockEntities.TROPHY, pos, state) {

    var teamId: String? = null

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        teamId?.let { nbt.putString("TeamId", it) }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        teamId = if (nbt.contains("TeamId")) nbt.getString("TeamId") else null
    }
}