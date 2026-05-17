package com.hollingsworth.ars_creo.api;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.Event;

import java.util.List;

@SuppressWarnings("unused")
public abstract class RegisterDisplayBehaviourEvent extends Event {
    protected final List<DisplaySource> source;

    protected RegisterDisplayBehaviourEvent(List<DisplaySource> source) {
        this.source = source;
    }

    public static class SourceJar extends RegisterDisplayBehaviourEvent {
        public SourceJar(List<DisplaySource> source) {
            super(source);
        }

        public void register(Block block) {
            DisplaySource.BY_BLOCK.register(block, this.source);
        }

        public void register(BlockEntityType<?> blockEntityType) {
            DisplaySource.BY_BLOCK_ENTITY.register(blockEntityType, this.source);
        }
    }

    public static class Turret extends RegisterDisplayBehaviourEvent {
        public Turret(List<DisplaySource> source) {
            super(source);
        }

        public void register(Block block) {
            DisplaySource.BY_BLOCK.register(block, this.source);
        }

        public void register(BlockEntityType<?> blockEntityType) {
            DisplaySource.BY_BLOCK_ENTITY.register(blockEntityType, this.source);
        }
    }
}