package com.example.examplemod;

import com.example.examplemod.contraption.SpellTurretBehavior;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.relays.belt.transport.BeltMovementHandler;
import net.minecraft.resources.ResourceLocation;

public class CreateCompat {


    public static void setup(){
        AllMovementBehaviours.addMovementBehaviour(new ResourceLocation(ArsNouveau.MODID, "basic_spell_turret"),new SpellTurretBehavior());
    }
}
