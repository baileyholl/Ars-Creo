package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.api.RegisterDisplayBehaviourEvent;
import com.hollingsworth.ars_creo.api.RegisterMovementBehaviourEvent;
import com.hollingsworth.ars_creo.common.display.SourceJarDisplaySource;
import com.hollingsworth.ars_creo.common.display.TurretDisplaySource;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.api.stress.BlockStressValues;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class CreateCompat {
    public static final DeferredRegister<DisplaySource> DISPLAY_SOURCES = DeferredRegister.create(CreateRegistries.DISPLAY_SOURCE, ArsCreo.MODID);

    public static final DeferredHolder<DisplaySource, DisplaySource> TURRET_DISPLAY_SOURCE = DISPLAY_SOURCES.register("turret", TurretDisplaySource::new);
    public static final  DeferredHolder<DisplaySource, DisplaySource> JAR_DISPLAY_SOURCE = DISPLAY_SOURCES.register("source_jar", SourceJarDisplaySource::new);

    public static void setup() {
        MovingInteractionBehaviour.REGISTRY.register(BlockRegistry.BASIC_SPELL_TURRET.get(), new BasicTurretBehavior());
        NeoForge.EVENT_BUS.post(new RegisterMovementBehaviourEvent.BasicTurret());
        MovementBehaviour.REGISTRY.register(BlockRegistry.BASIC_SPELL_TURRET.get(),  new AbstractTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.TIMER_SPELL_TURRET.get(),  new TimerTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.ENCHANTED_SPELL_TURRET.get(),  new EnhancedTurretBehavior());
        NeoForge.EVENT_BUS.post(new RegisterMovementBehaviourEvent.AbstractTurret());
        MovementBehaviour.REGISTRY.register(BlockRegistry.SOURCE_JAR.get(), new SourceJarBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.CREATIVE_SOURCE_JAR.get(), new SourceJarBehavior());
        NeoForge.EVENT_BUS.post(new RegisterMovementBehaviourEvent.SourceJar());
        BlockStressValues.CAPACITIES.register(ModBlockRegistry.STARBY_WHEEL.get(), () -> CreoConfig.WHEEL_STRESS_CAPACITY.getAsDouble());
    }

    public static void setupDisplayBehaviors() {
        List<DisplaySource> turretSource = List.of(TURRET_DISPLAY_SOURCE.get());
        List<DisplaySource> jarSource = List.of(JAR_DISPLAY_SOURCE.get());

        DisplaySource.BY_BLOCK.register(BlockRegistry.BASIC_SPELL_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.TIMER_SPELL_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.ROTATING_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.ENCHANTED_SPELL_TURRET.get(), turretSource);

        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.BASIC_SPELL_TURRET_TILE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.ENCHANTED_SPELL_TURRET_TYPE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.TIMER_SPELL_TURRET_TILE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.ROTATING_TURRET_TILE.get(), turretSource);

        NeoForge.EVENT_BUS.post(new RegisterDisplayBehaviourEvent.Turret(turretSource));

        DisplaySource.BY_BLOCK.register(BlockRegistry.SOURCE_JAR.get(), jarSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.SOURCE_JAR_TILE.get(), jarSource);

        NeoForge.EVENT_BUS.post(new RegisterDisplayBehaviourEvent.SourceJar(jarSource));
    }
}