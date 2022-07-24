package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.common.contraption.*;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.minecraft.util.ResourceLocation;

public class CreateCompat {


    public static void setup(){
        AllInteractionBehaviours.addInteractionBehaviour(new ResourceLocation(ArsNouveau.MODID, "basic_spell_turret"), BasicTurretBehavior::new);
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "spell_turret"),new EnhancedTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "timer_spell_turret"), new TimerTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "basic_spell_turret"), new AbstractTurretBehavior());
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "source_jar"), new SourceJarBehavior());

        BlockStressDefaults.DEFAULT_CAPACITIES.put(new ResourceLocation(ArsCreo.MODID, "starbuncle_wheel"), 16.0);
    }
}
