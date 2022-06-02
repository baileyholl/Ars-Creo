package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.MovingInteractionBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.tuple.MutablePair;

public class BasicTurretBehavior extends MovingInteractionBehaviour implements ITurretBehavior {

    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand activeHand, BlockPos localPos, AbstractContraptionEntity contraptionEntity) {
        if(player.level.isClientSide)
            return true;
        StructureTemplate.StructureBlockInfo info = contraptionEntity.getContraption()
                .getBlocks()
                .get(localPos);

        if (info == null)
            return false;
        MovementContext ctx = null;
        for (MutablePair<StructureTemplate.StructureBlockInfo, MovementContext> pair : contraptionEntity.getContraption()
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
