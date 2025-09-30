package net.rockystudios.lastbasestanding.blockentity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos

class TrophyBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(ModBlockEntities.TROPHY, pos, state) {

    var teamId: String? = null

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        teamId?.let { nbt.putString("TeamID", it) }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        teamId = nbt.getString("TeamID")
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        return createNbt()
    }

}