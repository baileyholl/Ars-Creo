package com.hollingsworth.ars_creo;

import com.hollingsworth.ars_creo.network.ACNetworking;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsCreo.MODID)
public class ArsCreo
{
    // Directly reference a log4j logger.
    public static ForgeConfigSpec SERVER_CONFIG;
    public static final String MODID = "ars_creo";

    public ArsCreo() {
        ArsNouveauRegistry.registerGlyphs();
        CreateCompat.setup();
        ExampleConfig.registerGlyphConfigs();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ACNetworking.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
