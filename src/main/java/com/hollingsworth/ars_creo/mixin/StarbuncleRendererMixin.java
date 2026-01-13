package com.hollingsworth.ars_creo.mixin;

import com.hollingsworth.ars_creo.client.render.GeoTrainHatRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.StarbuncleRenderer;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.GeoBone;

@Mixin(StarbuncleRenderer.class)
public class StarbuncleRendererMixin {
    @Inject(method = "renderRecursively(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/hollingsworth/arsnouveau/common/entity/Starbuncle;Lsoftware/bernie/geckolib/cache/object/GeoBone;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZFIII)V", at = @At("RETURN"))
    public void renderTrainHat(PoseStack stack, Starbuncle animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        if (bone.getName().equals("head")) {
            GeoTrainHatRenderer.render(new Vector3f(0.56f), new Vector3f(0f, 0.1f, 0.22f), stack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    }
}
