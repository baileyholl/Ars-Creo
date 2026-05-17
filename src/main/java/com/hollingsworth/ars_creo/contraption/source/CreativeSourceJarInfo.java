package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.ars_creo.api.SourceInfo;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class CreativeSourceJarInfo extends SourceInfo {
    protected int source = 1_000_000;

    public CreativeSourceJarInfo(StructureTemplate.StructureBlockInfo blockInfo) {
        super(blockInfo);
    }

    @Override
    public int getSource() {
        return source;
    }

    @Override
    public int getMaxSource() {
        return source;
    }

    @Override
    public int getTransferRate() {
        return source;
    }

    @Override
    public void removeSource(int source) {

    }

    @Override
    public void addSource(int source) {

    }

    @Override
    protected StructureTemplate.StructureBlockInfo removeWithUpdate(Level level, int source) {
        return null;
    }

    @Override
    protected StructureTemplate.StructureBlockInfo addWithUpdate(Level level, int source) {
        return null;
    }
}