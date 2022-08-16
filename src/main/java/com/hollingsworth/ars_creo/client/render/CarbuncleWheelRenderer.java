package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CarbuncleWheelRenderer extends GeoBlockRenderer<StarbuncleWheelTile> {
    public CarbuncleWheelRenderer(BlockEntityRendererProvider.Context renderManager) {
        super(renderManager, new CarbuncleWheelModel());
    }

    @Override
    public void render(StarbuncleWheelTile tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        Direction facing = tile.getBlockState().getValue(StarbuncleWheelBlock.FACING);
        stack.pushPose();
        if(facing == Direction.WEST){
            stack.mulPose(Vector3f.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.EAST) {
            stack.mulPose(Vector3f.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.SOUTH){
            stack.mulPose(Vector3f.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.NORTH) {
            stack.mulPose(Vector3f.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
        stack.popPose();
    }

    public static GenericItemBlockRenderer getISTER(){
        return new GenericItemBlockRenderer(new CarbuncleWheelModel());
    }

}
