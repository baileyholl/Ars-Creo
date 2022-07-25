package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ArsCreo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(ModBlockRegistry.CARBY_WHEEL, RenderType.cutout());
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockRegistry.CARBY_TILE, CarbuncleWheelRenderer::new);
    }
}
