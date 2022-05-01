package com.hollingsworth.ars_creo.common.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.MovingInteractionBehaviour;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.commons.lang3.tuple.MutablePair;

public class BasicTurretBehavior extends MovingInteractionBehaviour implements ITurretBehavior {

    @Override
    public boolean handlePlayerInteraction(PlayerEntity player, Hand activeHand, BlockPos localPos, AbstractContraptionEntity contraptionEntity) {
        if(player.level.isClientSide)
            return true;
        Template.BlockInfo info = contraptionEntity.getContraption()
                .getBlocks()
                .get(localPos);

        if (info == null)
            return false;
        MovementContext ctx = null;
        for (MutablePair<Template.BlockInfo, MovementContext> pair : contraptionEntity.getContraption()
                .getActors()) {
            if (info.equals(pair.left)) {
                ctx = pair.right;
                break;
            }
        }
        if (ctx == null)
            return false;

        castSpell(ctx,new BlockPos(ctx.position));

        return true;
    }
}
