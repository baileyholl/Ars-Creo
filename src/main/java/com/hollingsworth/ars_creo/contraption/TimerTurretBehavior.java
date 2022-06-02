package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TimerTurretBehavior extends AbstractTurretBehavior{

    @Override
    public void tick(MovementContext context) {
        super.tick(context);
        Level level = context.world;
        int ticksToSignal = context.tileData.getInt("time");
        if (!level.isClientSide && ticksToSignal > 0 &&  level.getGameTime() % (long)ticksToSignal == 0L) {
            castSpell(context, new BlockPos(context.position));
        }
    }
}
