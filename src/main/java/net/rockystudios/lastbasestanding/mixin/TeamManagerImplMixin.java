package net.rockystudios.lastbasestanding.mixin;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rockystudios.lastbasestanding.TrophyManager;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(TeamManagerImpl.class)
public class TeamManagerImplMixin {

    @Inject(method = "createParty(Ljava/util/UUID;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/lang/String;Ljava/lang/String;Ldev/ftb/mods/ftblibrary/icon/Color4I;)Lorg/apache/commons/lang3/tuple/Pair;", at = @At("RETURN"), remap = false)
    private void onTeamCreated(UUID playerId, @Nullable ServerPlayerEntity player, String name, @Nullable String description, @Nullable Color4I color, CallbackInfoReturnable<Pair<Integer, PartyTeam>> cir) {
        PartyTeam team = cir.getReturnValue().getValue();
        TrophyManager.INSTANCE.onTeamCreated(team, player);
    }
}
