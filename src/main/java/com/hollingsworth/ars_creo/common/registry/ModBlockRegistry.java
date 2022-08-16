package com.hollingsworth.ars_creo.common.registry;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.client.render.CarbuncleWheelRenderer;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.ars_creo.common.lib.LibBlock;
import com.hollingsworth.arsnouveau.common.items.RendererBlockItem;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(ArsCreo.MODID)
public class ModBlockRegistry {
    @ObjectHolder(LibBlock.STARBUNCLE_WHEEL) public static StarbuncleWheelBlock STARBY_WHEEL;
    @ObjectHolder(LibBlock.STARBUNCLE_WHEEL) public static BlockEntityType<StarbuncleWheelTile> STARBY_TILE;

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
            registry.register(new StarbuncleWheelBlock(defaultProperties().noOcclusion().lightLevel((state) -> 10)).setRegistryName(LibBlock.STARBUNCLE_WHEEL));
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<BlockEntityType<?>> event){
            event.getRegistry().register(BlockEntityType.Builder.of(StarbuncleWheelTile::new, ModBlockRegistry.STARBY_WHEEL).build(null).setRegistryName(LibBlock.STARBUNCLE_WHEEL));

        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            registry.register(new RendererBlockItem(ModBlockRegistry.STARBY_WHEEL, ItemsRegistry.defaultItemProperties()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
                    return CarbuncleWheelRenderer::getISTER;
                }
            }.setRegistryName(LibBlock.STARBUNCLE_WHEEL));
        }
    }
    public static Block.Properties defaultProperties(){
        return Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f);
    }
}
