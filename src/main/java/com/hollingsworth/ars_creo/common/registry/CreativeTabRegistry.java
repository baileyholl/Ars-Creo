package com.hollingsworth.ars_creo.common.registry;

import com.hollingsworth.ars_creo.ArsCreo;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hollingsworth.ars_creo.common.registry.ModBlockRegistry.BLOCK_REG;
import static com.hollingsworth.ars_creo.common.registry.ModBlockRegistry.STARBY_WHEEL;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsCreo.MODID);
    public static final RegistryObject<CreativeModeTab> BLOCKS = TABS.register("general", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ArsCreo.MODID + ".general"))
            .icon(() -> STARBY_WHEEL.get().asItem().getDefaultInstance())
            .displayItems((params, output) -> {
                for (RegistryObject<Block> entry : BLOCK_REG.getEntries()) {
                    output.accept(entry.get().asItem().getDefaultInstance());
                }
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());
}
