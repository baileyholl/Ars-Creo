package com.hollingsworth.ars_creo.common.registry;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.client.render.CarbuncleWheelRenderer;
import com.hollingsworth.ars_creo.common.block.CarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.CarbuncleWheelTile;
import com.hollingsworth.ars_creo.common.lib.LibBlock;
import com.hollingsworth.arsnouveau.common.block.tile.ArchwoodChestTile;
import com.hollingsworth.arsnouveau.common.items.AnimBlockItem;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ArsCreo.MODID)
public class ModBlockRegistry {
    @ObjectHolder(LibBlock.CARBUNCLE_WHEEL) public static CarbuncleWheelBlock CARBY_WHEEL;
    @ObjectHolder(LibBlock.CARBUNCLE_WHEEL) public static TileEntityType<CarbuncleWheelTile> CARBY_TILE;

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
            registry.register(new CarbuncleWheelBlock(defaultProperties().noOcclusion()).setRegistryName(LibBlock.CARBUNCLE_WHEEL));
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
            event.getRegistry().register(TileEntityType.Builder.of(CarbuncleWheelTile::new, ModBlockRegistry.CARBY_WHEEL).build(null).setRegistryName(LibBlock.CARBUNCLE_WHEEL));

        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            registry.register(new AnimBlockItem(ModBlockRegistry.CARBY_WHEEL, ItemsRegistry.defaultItemProperties().setISTER(() -> CarbuncleWheelRenderer::getISTER)).setRegistryName(LibBlock.CARBUNCLE_WHEEL));
        }
    }
    public static Block.Properties defaultProperties(){
        return Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f);
    }
}
