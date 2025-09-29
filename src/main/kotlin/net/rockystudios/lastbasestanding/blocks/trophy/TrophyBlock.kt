package net.rockystudios.lastbasestanding.blocks.trophy

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.rockystudios.lastbasestanding.blockentity.TrophyBlockEntity

class TrophyBlock(settings: Settings) : BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TrophyBlockEntity(pos, state)
    }

    override fun getCodec(): MapCodec<TrophyBlock> = createCodec(::TrophyBlock)


    override fun getRenderType(state: BlockState): BlockRenderType {
        // Use baked model from blockstates and models JSON
        return BlockRenderType.MODEL
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        // Optional: match your model's bounding box
        return VoxelShapes.cuboid(0.25, 0.25, 0.25, 0.75, 0.75, 0.75)
    }
}
