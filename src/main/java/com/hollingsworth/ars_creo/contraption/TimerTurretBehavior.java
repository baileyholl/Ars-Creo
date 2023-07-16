package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TimerTurretBehavior extends AbstractTurretBehavior{

    @Override
    public void tick(MovementContext context) {
        super.tick(context);
        Level level = context.world;
        int ticksToSignal = context.blockEntityData.getInt("time");
        if (!level.isClientSide && ticksToSignal > 0 &&  level.getGameTime() % (long)ticksToSignal == 0L) {
            castSpell(context, BlockPos.containing(context.position));
        }
    }
}
