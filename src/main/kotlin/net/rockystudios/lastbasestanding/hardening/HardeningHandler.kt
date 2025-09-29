package net.rockystudios.lastbasestanding.hardening

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.concurrent.ConcurrentHashMap

object HardeningHandler {
    private val hardenedBlocks = ConcurrentHashMap.newKeySet<HardenedBlock>()

    fun harden(hardenedBlock: HardenedBlock, world: World, player: PlayerEntity) {
        val blockPos = hardenedBlock.position
        if (isHardened(blockPos)) {

            val newMaterial = hardenedBlock.material
            val oldMaterial = getHardenedBlock(blockPos).material

            if(oldMaterial == newMaterial)
            {
                player.sendMessage(Text.of("Block is already hardened with ${newMaterial.item.name.string}!"), true)
                return
            } else if (newMaterial.hardness != oldMaterial.hardness)
            {
                hardenedBlocks.removeIf { it.position == blockPos }
                hardenedBlocks.add(hardenedBlock)
                var blockName = world.getBlockState(blockPos).block.name.string
                var hardeningMaterial = hardenedBlock.material

                player.sendMessage(Text.of("$blockName re-hardened with ${hardeningMaterial.item.name.string}! Hardness: ${hardeningMaterial.hardness}"), true)
                if (!player.abilities.creativeMode) player.giveItemStack(ItemStack(oldMaterial.item, 1))

                return
            }
            return
        }
        else
        {
            hardenedBlocks.add(hardenedBlock)
            var blockName = world.getBlockState(blockPos).block.name.string
            var hardeningMaterial = hardenedBlock.material

            player.sendMessage(Text.of("$blockName hardened with ${hardeningMaterial.item.name.string}! Hardness: ${hardeningMaterial.hardness}"), true)
        }

    }

    fun getHardenedBlock(pos: BlockPos): HardenedBlock {
        return hardenedBlocks.first { it.position == pos.toImmutable()}
    }

    fun isHardened(pos: BlockPos): Boolean {
        return pos.toImmutable() in hardenedBlocks.map(HardenedBlock::position)
    }

    fun initHardening()
    {

        UseBlockCallback.EVENT.register(UseBlockCallback { player, world, hand, hitResult ->
            if (!world.isClient) {

                var itemPlayerIsHolding = player.getStackInHand(hand).item

                for (hardeningMaterial in HardeningMaterial.materials) {


                    if (itemPlayerIsHolding == hardeningMaterial.item) {
                        val pos = hitResult.blockPos
                        harden(
                            HardenedBlock(
                                pos.toImmutable(),
                                hardeningMaterial
                            ), world, player
                        )
                        if (!player.abilities.creativeMode) player.getStackInHand(hand).decrement(1)

                        // Spawn particles at the block position (server-side)
                        if (world is ServerWorld) {
                            for (i in 0 until 20) {
                                val offsetX = world.random.nextDouble()
                                val offsetY = world.random.nextDouble()
                                val offsetZ = world.random.nextDouble()
                                world.spawnParticles(
                                    ParticleTypes.END_ROD,
                                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                                    1, 0.0, 0.0, 0.0, 0.0
                                )
                            }
                        }
                        ActionResult.SUCCESS
                    } else {
                        ActionResult.PASS
                    }
                }

                ActionResult.PASS
            } else {
                ActionResult.PASS
            }
        })
    }

    fun getHardenedBlockPositions(): Set<BlockPos> {
        return hardenedBlocks.map { it.position }.toSet()
    }
}