package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public class PortalMovingInteraction extends MovingInteractionBehaviour {

    @Override
    public void handleEntityCollision(Entity entity, BlockPos localPos, AbstractContraptionEntity contraptionEntity) {
        MovementContext ctx = contraptionEntity.getContraption().getActorAt(localPos).right;
        if (ctx == null)
            return;

        PortalBehavior.handleEntity(ctx, entity);
    }
}