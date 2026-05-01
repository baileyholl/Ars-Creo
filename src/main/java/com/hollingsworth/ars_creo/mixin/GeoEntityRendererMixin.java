package com.hollingsworth.ars_creo.mixin;

import com.hollingsworth.ars_creo.client.render.GeoTrainHatRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

@Mixin(GeoEntityRenderer.class)
public abstract class GeoEntityRendererMixin<T extends Entity & GeoAnimatable> implements GeoRenderer<T> {
    @Inject(method = "renderRecursively(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/Entity;Lsoftware/bernie/geckolib/cache/object/GeoBone;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZFIII)V", at = @At("RETURN"))
    void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour, CallbackInfo ci) {
        if(animatable instanceof LivingEntity livingEntity)
            GeoTrainHatRenderer.render(poseStack, livingEntity, bone, bufferSource, packedLight);
    }
}