package com.hollingsworth.ars_creo;

import com.hollingsworth.arsnouveau.common.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Random;

@ObjectHolder(ArsCreo.MODID)
public class ModEntities {

    public static EntityType<EntityProjectileSpell> SPELL_PROJ = null;

    @Mod.EventBusSubscriber(modid = ArsCreo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {


        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
//            SPELL_PROJ = build(
//                    LibEntityNames.SPELL_PROJ,
//                    EntityType.Builder.<EntityProjectileSpell>of(EntityProjectileSpell::new, MobCategory.MISC)
//                            .sized(0.5f, 0.5f)
//                            .setTrackingRange(20)
//                            .setShouldReceiveVelocityUpdates(true)
//                            .setUpdateInterval(120).setCustomClientFactory(EntityProjectileSpell::new));
//
//            event.getRegistry().registerAll(
//                    SPELL_PROJ
//            );
        }

        @SubscribeEvent
        public static void registerEntities(final EntityAttributeCreationEvent event) {
//            event.put(ENTITY_BOOKWYRM_TYPE, EntityBookwyrm.attributes().build());
        }
    }

    public static boolean genericGroundSpawn(EntityType<? extends Entity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getRawBrightness(pos, 0) > 8;
    }

    /**
     * Build an {@link EntityType} from a {@link EntityType.Builder} using the specified name.
     *
     * @param name    The entity type name
     * @param builder The entity type builder to build
     * @return The built entity type
     */
    private static <T extends Entity> EntityType<T> build(final String name, final EntityType.Builder<T> builder) {
        final ResourceLocation registryName = new ResourceLocation(ArsCreo.MODID, name);

        final EntityType<T> entityType = builder
                .build(registryName.toString());

        entityType.setRegistryName(registryName);

        return entityType;
    }
}
