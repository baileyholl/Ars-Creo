package com.example.examplemod.contraption;

import com.hollingsworth.arsnouveau.api.NbtTags;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;

public class SourceJarBehavior extends MovementBehaviour {

    @Override
    public void tick(MovementContext context) {
        super.tick(context);
    }

    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }

    public int getSource(MovementContext context){
        return context.tileData.getInt(NbtTags.MANA_TAG);
    }


}
