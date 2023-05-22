package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;

public class EnhancedTurretBehavior extends AbstractTurretBehavior{

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        if (!context.world.isClientSide) {
            castSpell(context, pos);
        }
    }
}
