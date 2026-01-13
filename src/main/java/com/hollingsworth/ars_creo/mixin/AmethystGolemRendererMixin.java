package com.hollingsworth.ars_creo.mixin;

import com.hollingsworth.ars_creo.client.render.GeoTrainHatRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.AmethystGolemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mixin(AmethystGolemRenderer.class)
public class AmethystGolemRendererMixin<T extends LivingEntity & GeoEntity> extends GeoEntityRenderer<T> {
    public AmethystGolemRendererMixin(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Inject(method = "renderRecursively(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/LivingEntity;Lsoftware/bernie/geckolib/cache/object/GeoBone;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZFIII)V", at = @At("RETURN"))
    public void renderTrainHat(PoseStack stack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour, CallbackInfo ci) {
        if (bone.getName().equals("body")) {
            GeoTrainHatRenderer.render(new Vector3f(1.4f), new Vector3f(0, 0.4f, 0), stack, animatable, bone, renderType, bufferSource, bufferIn, partialTick, packedLight, packedOverlay);
        }
    }
}
