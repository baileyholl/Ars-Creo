package com.hollingsworth.ars_creo.contraption;


import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;

public class AbstractTurretBehavior implements ITurretBehavior, MovementBehaviour {

    @Override
    public boolean renderAsNormalBlockEntity() {
        return true;
    }
}
