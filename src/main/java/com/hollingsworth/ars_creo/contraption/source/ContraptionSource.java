package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.ars_creo.contraption.ContraptionUtils;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.Contraption;

import java.util.List;

public class ContraptionSource implements ISourceTile{
    Contraption contraption;

    public ContraptionSource(Contraption contraption){
        this.contraption = contraption;
    }

    @Override
    public int getTransferRate() {
        return 10000;
    }

    @Override
    public boolean canAcceptSource() {
        return this.getSource() < this.getMaxSource();
    }

    @Override
    public int getSource() {
        int amount = 0;
        for(SourceInfo sourceInfo : ContraptionUtils.getSourceBlocks(contraption)){
            amount += sourceInfo.getAmount();
        }
        return amount;
    }

    @Override
    public int getMaxSource() {
        return ContraptionUtils.getSourceBlocks(contraption).size() * 10000;
    }

    @Override
    public void setMaxSource(int max) {}

    @Override
    public int setSource(int source) {
        if(source > getSource()){
            this.addSource(source - getSource());
        }
        if(source < getSource()){
            this.removeSource(getSource() - source);
        }
        return this.getSource();
    }

    @Override
    public int addSource(int source) {
        int remaining = source;
        for(SourceInfo sourceInfo : ContraptionUtils.getSourceBlocks(contraption)){
            if(remaining <= 0)
                break;
            int room = 10000 - sourceInfo.getAmount();
            if(room > 0){
                int toAdd = Math.min(room, remaining);
                sourceInfo.addWithUpdate(contraption.entity.level, toAdd, contraption.entity);
                remaining -= toAdd;
            }
        }
        return this.getSource();
    }

    @Override
    public int removeSource(int source) {
        int remaining = source;
        for(SourceInfo sourceInfo : ContraptionUtils.getSourceBlocks(contraption)){
            if(remaining <= 0)
                break;
            int amountInJar = sourceInfo.getAmount();
            if(amountInJar > 0){
                int toRemove = Math.min(amountInJar, remaining);
                sourceInfo.removeWithUpdate(contraption.entity.level, toRemove, contraption.entity);
                remaining -= toRemove;
                System.out.println(amountInJar + " " + toRemove + " " + remaining);
            }
        }
        return this.getSource();
    }
}
