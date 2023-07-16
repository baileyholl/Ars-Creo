package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.minecraft.resources.ResourceLocation;


public class CreateCompat {


    public static void setup(){
        AllInteractionBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET.get() ? new BasicTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET.get() ? new AbstractTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.TIMER_SPELL_TURRET.get() ? new TimerTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.ENCHANTED_SPELL_TURRET.get() ? new EnhancedTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.SOURCE_JAR.get() ? new SourceJarBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.CREATIVE_SOURCE_JAR.get() ? new SourceJarBehavior() : null);
        BlockStressDefaults.DEFAULT_CAPACITIES.put(new ResourceLocation(ArsCreo.MODID, "starbuncle_wheel"), 16.0);
    }

}
