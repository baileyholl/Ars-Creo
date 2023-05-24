package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;


public class SourceJarBehavior implements MovementBehaviour {


    @Override
    public boolean renderAsNormalBlockEntity() {
        return true;
    }

    public int getSource(MovementContext context){
        return context.blockEntityData.getInt(SourceJarTile.SOURCE_TAG);
    }

}
