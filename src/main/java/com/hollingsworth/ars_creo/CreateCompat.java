package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.common.display.SourceJarDisplaySource;
import com.hollingsworth.ars_creo.common.display.TurretDisplaySource;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.contraption.BlockMovementChecks;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;


public class CreateCompat {
    public static final DeferredRegister<DisplaySource> DISPLAY_SOURCES = DeferredRegister.create(CreateRegistries.DISPLAY_SOURCE, ArsCreo.MODID);

    public static final DeferredHolder<DisplaySource, DisplaySource> TURRET_DISPLAY_SOURCE = DISPLAY_SOURCES.register("turret", TurretDisplaySource::new);
    public static final  DeferredHolder<DisplaySource, DisplaySource> JAR_DISPLAY_SOURCE = DISPLAY_SOURCES.register("source_jar", SourceJarDisplaySource::new);

    public static void setup(){
        MovingInteractionBehaviour.REGISTRY.register(BlockRegistry.BASIC_SPELL_TURRET.get(), new BasicTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.BASIC_SPELL_TURRET.get(),  new AbstractTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.TIMER_SPELL_TURRET.get(),  new TimerTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.ENCHANTED_SPELL_TURRET.get(),  new EnhancedTurretBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.SOURCE_JAR.get(), new SourceJarBehavior());
        MovementBehaviour.REGISTRY.register(BlockRegistry.CREATIVE_SOURCE_JAR.get(), new SourceJarBehavior());
        BlockStressValues.CAPACITIES.register(ModBlockRegistry.STARBY_WHEEL.get(), () -> CreoConfig.WHEEL_STRESS_CAPACITY.getAsDouble());
        MovementBehaviour.REGISTRY.register(BlockRegistry.PORTAL_BLOCK.get(), new PortalBehavior());
        MovingInteractionBehaviour.REGISTRY.register(BlockRegistry.PORTAL_BLOCK.get(), new PortalMovingInteraction());
        BlockMovementChecks.registerMovementAllowedCheck(new BlockMovementChecks.MovementAllowedCheck() {
            @Override
            public BlockMovementChecks.CheckResult isMovementAllowed(BlockState state, Level world, BlockPos pos) {
                return state.is(BlockRegistry.PORTAL_BLOCK.get()) ? BlockMovementChecks.CheckResult.SUCCESS : BlockMovementChecks.CheckResult.PASS;
            }
        });

    }

    public static void setupDisplayBehaviors(){
        List<DisplaySource> turretSource = new ArrayList<>();
        turretSource.add(TURRET_DISPLAY_SOURCE.get());

        DisplaySource.BY_BLOCK.register(BlockRegistry.BASIC_SPELL_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.TIMER_SPELL_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.ROTATING_TURRET.get(), turretSource);
        DisplaySource.BY_BLOCK.register(BlockRegistry.ENCHANTED_SPELL_TURRET.get(), turretSource);

        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.BASIC_SPELL_TURRET_TILE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.ENCHANTED_SPELL_TURRET_TYPE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.TIMER_SPELL_TURRET_TILE.get(), turretSource);
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.ROTATING_TURRET_TILE.get(), turretSource);

        DisplaySource.BY_BLOCK.register(BlockRegistry.SOURCE_JAR.get(), List.of(JAR_DISPLAY_SOURCE.get()));
        DisplaySource.BY_BLOCK_ENTITY.register(BlockRegistry.SOURCE_JAR_TILE.get(), List.of(JAR_DISPLAY_SOURCE.get()));

    }

}
