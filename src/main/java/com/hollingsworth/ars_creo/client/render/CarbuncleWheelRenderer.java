package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class CarbuncleWheelRenderer extends GeoBlockRenderer<StarbuncleWheelTile> {
    public CarbuncleWheelRenderer(BlockEntityRendererProvider.Context renderManager) {
        super(new CarbuncleWheelModel());
    }

    @Override
    public void renderFinal(PoseStack stack, StarbuncleWheelTile tile, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Direction facing = tile.getBlockState().getValue(StarbuncleWheelBlock.FACING);
        stack.pushPose();
        if(facing == Direction.WEST){
            stack.mulPose(Axis.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.EAST) {
            stack.mulPose(Axis.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.SOUTH){
            stack.mulPose(Axis.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        if(facing == Direction.NORTH) {
            stack.mulPose(Axis.YP.rotationDegrees(-90));
            stack.translate(0, 0, -1);
        }
        super.renderFinal(stack, tile, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        stack.popPose();
    }

    public static GenericItemBlockRenderer getISTER(){
        return new GenericItemBlockRenderer(new CarbuncleWheelModel());
    }

}
