package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.Contraption;
import net.minecraft.core.BlockPos;

public class ContraptionSourceProvider implements ISpecialSourceProvider {
    public ISourceTile contraptionSource;
    public Contraption contraption;

    public ContraptionSourceProvider(Contraption contraption) {
        this.contraption = contraption;
        this.contraptionSource = new ContraptionSource(contraption);
    }

    @Override
    public ISourceTile getSource() {
        return new ContraptionSource(contraption);
    }

    @Override
    public boolean isValid() {
        return !contraption.entity.isRemoved();
    }

    @Override
    public BlockPos getCurrentPos() {
        return contraption.entity.getOnPos();
    }
}
