package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.ars_creo.contraption.source.ContraptionSourceProvider;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.source.SourceManager;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;

public class SourceJarBehavior implements MovementBehaviour {

    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }

    @Override
    public void startMoving(MovementContext context) {
        addIfMissing(context);
    }

    @Override
    public void tick(MovementContext context) {
        addIfMissing(context);
    }

    @Override
    public void stopMoving(MovementContext context) {
        ISpecialSourceProvider toRemove = null;
        for(ISpecialSourceProvider specialSourceProvider : SourceManager.INSTANCE.getSetForLevel(context.world)){
            if(specialSourceProvider instanceof ContraptionSourceProvider contraptionSourceProvider && contraptionSourceProvider.contraption != null && contraptionSourceProvider.contraption.entity == context.contraption.entity){
                toRemove = specialSourceProvider;
            }
        }
        if(toRemove != null)
            SourceManager.INSTANCE.getSetForLevel(context.world).remove(toRemove);
    }

    public void addIfMissing(MovementContext context){
        if(context.contraption == null || context.contraption.entity == null || context.world == null || context.world.isClientSide)
            return;

        for(ISpecialSourceProvider specialSourceProvider : SourceManager.INSTANCE.getSetForLevel(context.world)){
            if(specialSourceProvider instanceof ContraptionSourceProvider contraptionSourceProvider && contraptionSourceProvider.contraption.entity == context.contraption.entity){
                return;
            }
        }
        SourceManager.INSTANCE.addInterface(context.world, new ContraptionSourceProvider(context.contraption));
    }
}
