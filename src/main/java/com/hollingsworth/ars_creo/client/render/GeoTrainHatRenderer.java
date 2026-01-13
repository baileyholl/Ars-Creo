package com.hollingsworth.ars_creo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.hats.EntityHats;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.util.RenderUtil;

public class GeoTrainHatRenderer {
    public static void render(Vector3f hatScale, Vector3f hatTranslation, PoseStack poseStack, LivingEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
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

        poseStack.scale(-hatScale.x, hatScale.y, -hatScale.z);
        poseStack.translate(hatTranslation.x, hatTranslation.y, hatTranslation.z);

        msr.rotateXDegrees(-8.5f);

        BlockState air = Blocks.AIR.defaultBlockState();
        CachedBuffers.partial(hat, air)
                .disableDiffuse()
                .light(packedLight)
                .renderInto(poseStack, bufferSource.getBuffer(Sheets.cutoutBlockSheet()));

        poseStack.popPose();
    }
}
