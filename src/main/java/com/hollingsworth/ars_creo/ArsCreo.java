package com.hollingsworth.ars_creo;

import com.hollingsworth.ars_creo.client.render.ClientHandler;
import com.hollingsworth.ars_creo.common.PotionTank;
import com.hollingsworth.ars_creo.common.registry.CreativeTabRegistry;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ArsCreo.MODID)
public class ArsCreo {
    public static final String MODID = "ars_creo";

    public ArsCreo(IEventBus modBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(ArsNouveauRegistry::registerDocumentation);
        modContainer.registerConfig(ModConfig.Type.COMMON, CreoConfig.SERVER_CONFIG);
        modBus.addListener(this::clientSetup);
        modBus.addListener(ArsCreo::registerEvents);
        modBus.addListener(ArsCreo::registerCapability);
        modBus.addListener(ArsCreo::commonSetup);
        registers(modBus);
    }

    public static void registers(IEventBus event) {
        CreateCompat.DISPLAY_SOURCES.register(event);
        ModBlockRegistry.ITEMS.register(event);
        ModBlockRegistry.BLOCK_REG.register(event);
        ModBlockRegistry.BLOCK_ENTITY_REG.register(event);
        CreativeTabRegistry.TABS.register(event);
    }

    public static void registerEvents(RegisterEvent event) {
        event.register(Registries.ITEM, helper ->{
            ModBlockRegistry.onBlockItemsRegistry();
        });
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        TooltipModifier.REGISTRY.register(ModBlockRegistry.STARBY_WHEEL_ITEM.get(), KineticStats.create(ModBlockRegistry.STARBY_WHEEL_ITEM.get()));
        CreateCompat.setup();
        CreateCompat.setupDisplayBehaviors();
    }

    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockRegistry.POTION_JAR_TYPE.get(), (tile, ctx) -> new PotionTank(tile));
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ModLoadingContext.get().getActiveContainer().getEventBus().addListener(ClientHandler::init);
    }
}