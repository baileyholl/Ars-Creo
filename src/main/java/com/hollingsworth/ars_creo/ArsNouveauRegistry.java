package com.hollingsworth.ars_creo;

import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder;
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry;
import com.simibubi.create.AllBlocks;

public class ArsNouveauRegistry {
    public static void registerDocumentation(ReloadDocumentationEvent ignored){
        DocumentationRegistry.registerEntry(DocumentationRegistry.CRAFTING, new DocEntryBuilder(ArsCreo.MODID, DocumentationRegistry.CRAFTING, ModBlockRegistry.STARBY_WHEEL.get().asItem())
                .withIntroPage()
                .withCraftingPages(ModBlockRegistry.STARBY_WHEEL.get()).build());
        DocumentationRegistry.registerEntry(DocumentationRegistry.CRAFTING, new DocEntryBuilder(ArsCreo.MODID, DocumentationRegistry.CRAFTING, AllBlocks.DISPLAY_LINK.get().asItem()).withName("ars_creo.display_link")
                .withIntroPage()
                .withCraftingPages(AllBlocks.DISPLAY_LINK, AllBlocks.DISPLAY_BOARD).build());
        DocumentationRegistry.registerEntry(DocumentationRegistry.CRAFTING, new DocEntryBuilder(ArsCreo.MODID, DocumentationRegistry.CRAFTING, AllBlocks.FLUID_TANK.get().asItem())
                        .withName("ars_creo.fluid_tank")
                .withIntroPage().build());
    }
}