package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.hats.EntityHats;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.util.RenderUtil;

import java.util.Objects;

public class StarbuncleHatRenderLayer {
    public static void render(PoseStack poseStack, Starbuncle animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!Objects.equals(bone.getName(), "head")) {
            return;
        }

        PartialModel hat = EntityHats.getHatFor(animatable);
        if (hat == null) {
            return;
        }

        poseStack.pushPose();

        var msr = TransformStack.of(poseStack);

        var scale = animatable.getScale();
        poseStack.scale(scale, scale, scale);

        RenderUtil.translateMatrixToBone(poseStack, bone);
        RenderUtil.translateToPivotPoint(poseStack, bone);
        RenderUtil.rotateMatrixAroundBone(poseStack, bone);
        RenderUtil.scaleMatrixForBone(poseStack, bone);

        poseStack.scale(-0.56f, 0.56f, -0.56f);
        poseStack.translate(0, 0.1, 0.22);

        msr.rotateXDegrees(-8.5f);

        BlockState air = Blocks.AIR.defaultBlockState();
        CachedBuffers.partial(hat, air)
                .disableDiffuse()
                .light(packedLight)
                .renderInto(poseStack, bufferSource.getBuffer(Sheets.cutoutBlockSheet()));

        poseStack.popPose();
    }
}
