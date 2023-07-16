package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class CarbuncleWheelRenderer extends GeoBlockRenderer<StarbuncleWheelTile> {
    public static CarbuncleWheelModel wheelModel = new CarbuncleWheelModel();

    public CarbuncleWheelRenderer(BlockEntityRendererProvider.Context renderManager) {
        super(wheelModel);
    }

    @Override
    public void actuallyRender(PoseStack stack, StarbuncleWheelTile animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Direction facing = animatable.getBlockState().getValue(StarbuncleWheelBlock.FACING);
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
        super.actuallyRender(stack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        stack.popPose();
    }

    public static GenericItemBlockRenderer getISTER(){
        return new GenericItemBlockRenderer(new CarbuncleWheelModel());
    }

}
