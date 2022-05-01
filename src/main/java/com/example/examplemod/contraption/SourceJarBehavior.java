package com.example.examplemod.contraption;

import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionMatrices;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SourceJarBehavior implements MovementBehaviour {


    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }

    public int getSource(MovementContext context){
        return context.tileData.getInt(SourceJarTile.SOURCE_TAG);
    }

}
