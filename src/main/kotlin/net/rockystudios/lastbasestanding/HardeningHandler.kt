package net.rockystudios.lastbasestanding

import net.minecraft.util.math.BlockPos
import java.util.concurrent.ConcurrentHashMap

object HardeningHandler {
    private val hardenedBlocks = ConcurrentHashMap.newKeySet<BlockPos>()

    fun harden(pos: BlockPos) {
        hardenedBlocks.add(pos.toImmutable())
    }

    fun isHardened(pos: BlockPos): Boolean {
        return pos.toImmutable() in hardenedBlocks
    }
}