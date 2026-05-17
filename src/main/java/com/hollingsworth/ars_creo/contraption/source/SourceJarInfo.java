package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.ars_creo.api.SourceInfo;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SourceJarInfo extends SourceInfo {
    protected int source;
    protected int maxSource;

    @SuppressWarnings("all")
    public SourceJarInfo(StructureTemplate.StructureBlockInfo blockInfo) {
        super(blockInfo);
        this.source = blockInfo.nbt().getInt(SourceJarTile.SOURCE_TAG);
        this.maxSource = 10_000;
    }

    @Override
    public int getSource() {
        return source;
    }

    @Override
    public int getMaxSource() {
        return maxSource;
    }

    @Override
    public int getTransferRate() {
        return maxSource;
    }

    @Override
    @SuppressWarnings("all")
    public void removeSource(int source) {
        this.source -= source;
        blockInfo.nbt().putInt(SourceJarTile.SOURCE_TAG, this.source);
    }

    @Override
    @SuppressWarnings("all")
    public void addSource(int source) {
        this.source += source;
        blockInfo.nbt().putInt(SourceJarTile.SOURCE_TAG, this.source);
    }

    @Override
    public StructureTemplate.StructureBlockInfo removeWithUpdate(Level level, int source) {
        int currentFillState = getFillState();
        removeSource(source);
        int nextFillState = getFillState();
        if (currentFillState != nextFillState)
            return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), BlockRegistry.SOURCE_JAR.defaultBlockState().setValue(SourceJar.fill, nextFillState), blockInfo.nbt());
        return null;
    }

    @Override
    public StructureTemplate.StructureBlockInfo addWithUpdate(Level level, int source) {
        int currentFillState = getFillState();
        addSource(source);
        int nextFillState = getFillState();
        if(currentFillState != nextFillState)
            return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), BlockRegistry.SOURCE_JAR.defaultBlockState().setValue(SourceJar.fill, nextFillState), blockInfo.nbt());
        return null;
    }

    protected int getFillState() {
        return Math.min(11, Math.max((source / 1000) + 1, 0));
    }
}