package net.rockystudios.lastbasestanding.blocks.trophy

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
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


    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        super.onPlaced(world, pos, state, placer, itemStack)

        if (!world.isClient) {
            val blockEntity = world.getBlockEntity(pos) as? TrophyBlockEntity ?: return
            val nbt = itemStack.nbt ?: return

            if (nbt.contains("TeamID")) {
                blockEntity.teamId = nbt.getString("TeamID")
                blockEntity.markDirty()
                world.updateListeners(pos, state, state, 3)
            }
        }
    }
}
