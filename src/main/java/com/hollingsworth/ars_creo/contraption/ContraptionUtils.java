package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.ars_creo.api.SourceInfo;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ContraptionUtils {

    public static boolean removeSourceFromContraption(MovementContext context, int amount, BlockPos turretPos){
        ServerLevel world = (ServerLevel) context.world;
        int amountLeft = amount;
        int amountOnContraption = 0;
        List<SourceInfo> sourceInfos = getSourceBlocks(context.contraption);
        for (SourceInfo info : sourceInfos) {
            if (info.blockInfo.state().is(BlockRegistry.CREATIVE_SOURCE_JAR.get()))
                return true;
            amountOnContraption += info.getSource();
        }
        if (amountOnContraption < amount)
            return false;
        for (SourceInfo info : sourceInfos) {
            if (info.getSource() >= amountLeft) {
                info.removeWithUpdate(world, amountLeft, context.contraption.entity);
                return true;
            }
            amountLeft -= info.getSource();
            info.removeWithUpdate(world, info.getSource(), context.contraption.entity);
        }
        return true;
    }

    public static List<SourceInfo> getSourceBlocks(Contraption contraption) {
        List<SourceInfo> sourceBlocks = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo blockInfo : contraption.getBlocks().values()) {
            SourceInfo info = SourceInfo.get(blockInfo);
            if (info != null)
                sourceBlocks.add(info);
        }
        return sourceBlocks;
    }

    public static <T> T processSourceBlocks(Contraption contraption, T defVal, BiFunction<SourceInfo, T, T> consumer) {
        T result = defVal;
        for (StructureTemplate.StructureBlockInfo blockInfo : contraption.getBlocks().values()) {
            SourceInfo info = SourceInfo.get(blockInfo);
            if (info != null)
                result = consumer.apply(info, result);
        }
        return result;
    }
}