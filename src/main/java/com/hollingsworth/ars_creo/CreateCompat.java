package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.minecraft.resources.ResourceLocation;


public class CreateCompat {


    public static void setup(){
        AllInteractionBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET ? new BasicTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET ? new AbstractTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.TIMER_SPELL_TURRET ? new TimerTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.ENCHANTED_SPELL_TURRET ? new EnhancedTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.SOURCE_JAR ? new SourceJarBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.CREATIVE_SOURCE_JAR ? new SourceJarBehavior() : null);
        BlockStressDefaults.DEFAULT_CAPACITIES.put(new ResourceLocation(ArsCreo.MODID, "starbuncle_wheel"), 16.0);
    }

}
