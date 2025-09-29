package net.rockystudios.lastbasestanding.hardening

import net.minecraft.util.math.BlockPos

object BreakTracker {
    private var currentBreakingPos: BlockPos? = null

    fun setBreakingPos(pos: BlockPos) {
        currentBreakingPos = pos.toImmutable()
    }

    fun getBreakingPos(): BlockPos? {
        return currentBreakingPos
    }
}