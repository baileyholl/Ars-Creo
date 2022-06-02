package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;

public class AbstractTurretBehavior implements ITurretBehavior, MovementBehaviour {

    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }
}
