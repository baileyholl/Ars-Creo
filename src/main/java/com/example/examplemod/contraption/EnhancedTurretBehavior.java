package com.example.examplemod.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.util.math.BlockPos;

public class EnhancedTurretBehavior extends AbstractTurretBehavior{

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        if (!context.world.isClientSide) {
            castSpell(context, pos);
        }
    }
}
