package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.common.block.CarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.CarbuncleWheelTile;
import com.hollingsworth.arsnouveau.client.renderer.entity.CarbuncleModel;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.PressModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class CarbuncleWheelRenderer extends GeoBlockRenderer<CarbuncleWheelTile> {
    public CarbuncleWheelRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new CarbuncleWheelModel());
    }

    @Override
    public void render(CarbuncleWheelTile tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Direction facing = tile.getBlockState().getValue(CarbuncleWheelBlock.FACING);
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

    @Override
    public void render(TileEntity tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(tile, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public void render(GeoModel model, CarbuncleWheelTile animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public static GenericItemRenderer getISTER(){
        return new GenericItemRenderer(new CarbuncleWheelModel());
    }

}
