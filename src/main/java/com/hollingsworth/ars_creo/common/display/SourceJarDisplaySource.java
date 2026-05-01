package com.hollingsworth.ars_creo.common.display;

import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SourceJarDisplaySource extends DisplaySource {
    public static final List<MutableComponent> notEnoughSpaceSingle =
            List.of(Component.translatable("ars_creo.display_source.turret.not_enough_space")
                    .append(Component.translatable("ars_creo.display_source.for_source_status")));
    @Override
    public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
        if (stats.maxRows() < 2)
            return notEnoughSpaceSingle;
        boolean isBook = context.getTargetBlockEntity() instanceof LecternBlockEntity;

        if (isBook) {
            Stream<MutableComponent> componentList = getComponents(context).map(components -> {
                Optional<MutableComponent> reduce = components.stream()
                        .reduce(MutableComponent::append);
                return reduce.orElse(EMPTY_LINE);
            });

            return List.of(componentList.reduce((comp1, comp2) -> comp1.append(Component.literal("\n"))
                            .append(comp2))
                    .orElse(EMPTY_LINE));
        }

        return getComponents(context).map(components -> {
                    Optional<MutableComponent> reduce = components.stream()
                            .reduce(MutableComponent::append);
                    return reduce.orElse(EMPTY_LINE);
                })
                .toList();
    }

    private Stream<List<MutableComponent>> getComponents(DisplayLinkContext context) {
        BlockEntity sourceBE = context.getSourceBlockEntity();
        if (!(sourceBE instanceof SourceJarTile turretTile))
            return Stream.empty();

        return Stream.of(List.of(
                        Component.translatable("ars_creo.display_source.source_count", turretTile.getSource())));
    }
}