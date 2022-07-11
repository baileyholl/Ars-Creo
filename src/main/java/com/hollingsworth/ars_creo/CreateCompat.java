package com.hollingsworth.ars_creo;

import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import net.minecraft.resources.ResourceLocation;

public class CreateCompat {


    public static void setup(){
        AllInteractionBehaviours.addInteractionBehaviour(new ResourceLocation(ArsNouveau.MODID, "basic_spell_turret"), BasicTurretBehavior::new);
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "spell_turret"),new EnhancedTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "timer_spell_turret"), new TimerTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "basic_spell_turret"), new AbstractTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "source_jar"), new SourceJarBehavior());
//        AllInteractionBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET ? new BasicTurretBehavior() : null);
//        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET ? new AbstractTurretBehavior() : null);
//        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.TIMER_SPELL_TURRET ? new TimerTurretBehavior() : null);
//        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.ENCHANTED_SPELL_TURRET ? new EnhancedTurretBehavior() : null);
//        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.SOURCE_JAR ? new SourceJarBehavior() : null);

    }

}
