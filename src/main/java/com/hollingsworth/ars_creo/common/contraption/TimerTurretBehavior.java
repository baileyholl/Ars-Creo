package com.hollingsworth.ars_creo.common.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimerTurretBehavior extends AbstractTurretBehavior{

    @Override
    public void tick(MovementContext context) {
        super.tick(context);
        World level = context.world;
        int ticksToSignal = context.tileData.getInt("time");
        if (!level.isClientSide && ticksToSignal > 0 &&  level.getGameTime() % (long)ticksToSignal == 0L) {
            castSpell(context, new BlockPos(context.position));
        }
    }
}
