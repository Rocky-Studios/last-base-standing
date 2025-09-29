package net.rockystudios.lastbasestanding.hardening

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import java.util.concurrent.ConcurrentHashMap

object HardeningHandler {
    private val hardenedBlocks = ConcurrentHashMap.newKeySet<HardenedBlock>()

    fun harden(hardenedBlock: HardenedBlock) {
        hardenedBlocks.add(hardenedBlock)
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

                var itemPlayerIsHolding = player.getStackInHand(hand).item;

                for (hardeningMaterial in HardeningMaterial.materials) {


                    if (itemPlayerIsHolding == hardeningMaterial.item) {
                        val pos = hitResult.blockPos
                        harden(
                            HardenedBlock(
                                pos.toImmutable(),
                                hardeningMaterial
                            )
                        )

                        var blockName = world.getBlockState(pos).block.name.string;

                        var blockHardeningText = Text.of("$blockName hardened with ${hardeningMaterial.item.name.string}! Hardness: ${hardeningMaterial.hardness}")

                        player.sendMessage(blockHardeningText, true)

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
}