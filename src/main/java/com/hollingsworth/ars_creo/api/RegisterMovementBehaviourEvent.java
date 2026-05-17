package com.hollingsworth.ars_creo.api;

import com.hollingsworth.ars_creo.contraption.AbstractTurretBehavior;
import com.hollingsworth.ars_creo.contraption.BasicTurretBehavior;
import com.hollingsworth.ars_creo.contraption.SourceJarBehavior;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.Event;

@SuppressWarnings("unused")
public abstract class RegisterMovementBehaviourEvent extends Event {
    public static class SourceJar extends RegisterMovementBehaviourEvent {
        public void register(Block block) {
            MovementBehaviour.REGISTRY.register(block, new SourceJarBehavior());
        }
    }

    public static class AbstractTurret extends RegisterMovementBehaviourEvent {
        public void register(Block block, AbstractTurretBehavior behaviour) {
            MovementBehaviour.REGISTRY.register(block, behaviour);
        }
    }

    public static class BasicTurret extends RegisterMovementBehaviourEvent {
        public void register(Block block, BasicTurretBehavior behaviour) {
            MovingInteractionBehaviour.REGISTRY.register(block, behaviour);
        }
    }
}