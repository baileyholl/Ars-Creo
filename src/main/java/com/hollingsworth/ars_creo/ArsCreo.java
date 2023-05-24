package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.network.ACNetworking;
import com.hollingsworth.ars_creo.client.render.ClientHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsCreo.MODID)
public class ArsCreo
{

    public static final String MODID = "ars_creo";

    public ArsCreo() {
        ArsNouveauRegistry.registerGlyphs();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CreoConfig.SERVER_CONFIG);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ACNetworking.registerMessages();
        event.enqueueWork(() ->{
            CreateCompat.setup();
        });
    }


    public void clientSetup(final FMLClientSetupEvent event) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::init);
    }
}
