package com.hollingsworth.ars_creo;

import com.hollingsworth.ars_creo.common.network.ACNetworking;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ACNetworking.registerMessages();
    }

}
