package com.hollingsworth.ars_creo.api;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class SourceInfo {
    private static final ConcurrentHashMap<Block, Function<StructureTemplate.StructureBlockInfo, ? extends SourceInfo>> REGISTRY = new ConcurrentHashMap<>();

    public static void register(Block block, Function<StructureTemplate.StructureBlockInfo, ? extends SourceInfo> supplier) {
        REGISTRY.put(block, supplier);
    }

    public static SourceInfo get(StructureTemplate.StructureBlockInfo blockInfo) {
        Function<StructureTemplate.StructureBlockInfo, ? extends SourceInfo> supplier = REGISTRY.get(blockInfo.state().getBlock());
        if (supplier == null)
            return null;
        return supplier.apply(blockInfo);
    }

    public StructureTemplate.StructureBlockInfo blockInfo;

    public SourceInfo(StructureTemplate.StructureBlockInfo blockInfo) {
        this.blockInfo = blockInfo;
    }

    public abstract int getSource();

    public abstract int getMaxSource();

    public abstract int getTransferRate();

    public abstract void removeSource(int source);

    public abstract void addSource(int source);

    protected abstract StructureTemplate.StructureBlockInfo removeWithUpdate(Level level, int source);

    protected abstract StructureTemplate.StructureBlockInfo addWithUpdate(Level level, int source);

    public void removeWithUpdate(Level level, int source, AbstractContraptionEntity entity) {
        StructureTemplate.StructureBlockInfo info = removeWithUpdate(level, source);
        if (info != null)
            entity.setBlock(info.pos(), info);
    }

    public void addWithUpdate(Level level, int source, AbstractContraptionEntity entity) {
        StructureTemplate.StructureBlockInfo info = addWithUpdate(level, source);
        if (info != null)
            entity.setBlock(info.pos(), info);
    }
}