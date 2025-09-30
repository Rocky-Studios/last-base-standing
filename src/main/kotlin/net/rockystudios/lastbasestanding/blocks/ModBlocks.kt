import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.rockystudios.lastbasestanding.LastBaseStanding
import net.rockystudios.lastbasestanding.blocks.trophy.TrophyBlock

object ModBlocks {
    val TROPHY: Block = TrophyBlock(FabricBlockSettings.create().strength(5.0f).nonOpaque())

    fun register() {
        Registry.register(Registries.BLOCK, Identifier(LastBaseStanding.MOD_ID, "trophy"), TROPHY)
    }

}
