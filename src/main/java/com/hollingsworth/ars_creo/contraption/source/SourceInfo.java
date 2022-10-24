package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.ars_creo.network.ACNetworking;
import com.hollingsworth.ars_creo.network.PacketUpdateJarContraption;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import static com.hollingsworth.ars_creo.contraption.ContraptionUtils.getFillState;

public class SourceInfo {

    public StructureTemplate.StructureBlockInfo blockInfo;
    public int amount;

    public SourceInfo(StructureTemplate.StructureBlockInfo blockInfo, int amount){
        this.blockInfo = blockInfo;
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }

    public void removeAmount(int amount){
        this.amount -= amount;
        blockInfo.nbt.putInt(SourceJarTile.SOURCE_TAG, this.amount);
    }

    public void addAmount(int amount){
        this.amount += amount;
        blockInfo.nbt.putInt(SourceJarTile.SOURCE_TAG, this.amount);
    }

    public void removeWithUpdate(Level level, int amount, AbstractContraptionEntity entity){
        int currentFillState = getFillState(this.getAmount());
        this.removeAmount(amount);
        int nextFillState = getFillState(this.amount);

            ACNetworking.sendToNearby(level, blockInfo.pos, new PacketUpdateJarContraption(entity.getId(), blockInfo.pos, blockInfo.nbt, nextFillState));

    }

    public void addWithUpdate(Level level, int amount, AbstractContraptionEntity entity){
        int currentFillState = getFillState(this.getAmount());
        this.addAmount(amount);
        int nextFillState = getFillState(this.amount);
        if(currentFillState != nextFillState) {
            ACNetworking.sendToNearby(level, blockInfo.pos, new PacketUpdateJarContraption(entity.getId(), blockInfo.pos, blockInfo.nbt, nextFillState));
        }
    }
}
