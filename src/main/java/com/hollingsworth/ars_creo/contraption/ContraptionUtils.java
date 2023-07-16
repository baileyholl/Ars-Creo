package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.ars_creo.contraption.source.SourceInfo;
import com.hollingsworth.arsnouveau.common.block.CreativeSourceJar;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;

public class ContraptionUtils {

    public static boolean removeSourceFromContraption(MovementContext context, int amount, BlockPos turretPos){
        ServerLevel world = (ServerLevel) context.world;
        int amountLeft = amount;
        int amountOnContraption = 0;
        List<SourceInfo> sourceInfos = getSourceBlocks(context.contraption);
        for(SourceInfo info : sourceInfos){
            if(info.blockInfo.state().getBlock() == BlockRegistry.CREATIVE_SOURCE_JAR.get()){
                return true;
            }
            amountOnContraption += info.getAmount();
        }
        if(amountOnContraption < amount){
            return false;
        }
        for(SourceInfo info : sourceInfos){
            if(info.getAmount() >= amountLeft){
                info.removeWithUpdate(world, amountLeft, context.contraption.entity);
                return true;
            }else{
                amountLeft -= info.getAmount();
                info.removeWithUpdate(world, info.getAmount(), context.contraption.entity);
            }
        }
        return true;
    }

    public static List<SourceInfo> getSourceBlocks(Contraption contraption){
        List<SourceInfo> sourceBlocks = new ArrayList<>();
        for(StructureTemplate.StructureBlockInfo blockInfo : contraption.getBlocks().values()){
            if(blockInfo.state().getBlock() instanceof SourceJar){
                int totalSource = blockInfo.nbt().getInt(SourceJarTile.SOURCE_TAG);
                sourceBlocks.add(new SourceInfo(blockInfo, totalSource));
            }
            if(blockInfo.state().getBlock() instanceof CreativeSourceJar){
                sourceBlocks.add(new SourceInfo(blockInfo, 10000));
            }
        }
        return sourceBlocks;
    }

    public static int getFillState(int source){
        int fillState = 0;
        if (source > 0 && source < 1000) {
            fillState = 1;
        } else if (source != 0) {
            fillState = source / 1000 + 1;
        }
        return fillState;
    }
}
