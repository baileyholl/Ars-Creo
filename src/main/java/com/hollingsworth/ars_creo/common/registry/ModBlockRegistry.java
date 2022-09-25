package com.hollingsworth.ars_creo.common.registry;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.client.render.CarbuncleWheelRenderer;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.ars_creo.common.lib.LibBlock;
import com.hollingsworth.arsnouveau.common.items.RendererBlockItem;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModBlockRegistry {
    public static final DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(ForgeRegistries.BLOCKS, ArsCreo.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REG = DeferredRegister.create(Registry.BLOCK_ENTITY_TYPE_REGISTRY, ArsCreo.MODID);


    public static RegistryObject<StarbuncleWheelBlock> STARBY_WHEEL = BLOCK_REG.register(LibBlock.STARBUNCLE_WHEEL, () -> new StarbuncleWheelBlock(defaultProperties().noOcclusion().lightLevel((state) -> 10)));
    public static RegistryObject<BlockEntityType<StarbuncleWheelTile>> STARBY_TILE = BLOCK_ENTITY_REG.register(LibBlock.STARBUNCLE_WHEEL, () -> BlockEntityType.Builder.of(StarbuncleWheelTile::new, STARBY_WHEEL.get()).build(null));

    public static void onBlockItemsRegistry(IForgeRegistry<Item> registry) {
        registry.register(LibBlock.STARBUNCLE_WHEEL, new RendererBlockItem(ModBlockRegistry.STARBY_WHEEL.get(), ItemsRegistry.defaultItemProperties()) {
            @Override
            public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
                return CarbuncleWheelRenderer::getISTER;
            }
        });
    }

    public static Block.Properties defaultProperties(){
        return Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f);
    }
}
