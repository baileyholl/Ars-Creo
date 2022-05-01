package com.example.examplemod.contraption;

import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;

public class AbstractTurretBehavior implements ITurretBehavior, MovementBehaviour {

    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }
}
