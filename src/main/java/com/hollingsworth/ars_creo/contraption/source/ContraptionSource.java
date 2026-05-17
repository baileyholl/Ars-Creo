package com.hollingsworth.ars_creo.contraption.source;

import com.hollingsworth.ars_creo.contraption.ContraptionUtils;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.simibubi.create.content.contraptions.Contraption;
import org.apache.commons.lang3.tuple.MutablePair;

public class ContraptionSource implements ISourceTile {
    @SuppressWarnings("all")
    private Contraption contraption;

    public ContraptionSource(Contraption contraption) {
        this.contraption = contraption;
    }

    @Override
    public int getTransferRate() {
        return ContraptionUtils.processSourceBlocks(contraption, 0, (info, amount) -> Math.max(amount, info.getTransferRate()));
    }

    @Override
    public boolean canAcceptSource() {
        MutablePair<Integer, Integer> sourceData = ContraptionUtils.processSourceBlocks(contraption, MutablePair.of(0, 0), (info, pair) -> {
            pair.left += info.getSource();
            pair.right += info.getMaxSource();
            return pair;
        });
        return sourceData.getLeft() < sourceData.getRight(); // source < maxSource
    }

    @Override
    public int getSource() {
        return ContraptionUtils.processSourceBlocks(contraption, 0, (info, amount) -> amount + info.getSource());
    }

    @Override
    public int getMaxSource() {
        return ContraptionUtils.processSourceBlocks(contraption, 0, (info, amount) -> amount + info.getMaxSource());
    }

    @Override
    public int setSource(int source) {
        int currentSource = getSource();
        if (source > currentSource) {
            addSource(source - currentSource);
            return getSource();
        }
        if (source < currentSource) {
            removeSource(currentSource - source);
            return getSource();
        }
        return currentSource;
    }

    @Override
    public int addSource(int source) {
        ContraptionUtils.processSourceBlocks(contraption, source, (info, remaining) -> {
            if (remaining <= 0)
                return remaining;
            int room = info.getMaxSource() - info.getSource();
            if (room > 0) {
                int toAdd = Math.min(room, remaining);
                info.addWithUpdate(contraption.entity.level(), toAdd, contraption.entity);
                return remaining - toAdd;
            }
            return remaining;
        });
        return getSource();
    }

    @Override
    public int removeSource(int source) {
        ContraptionUtils.processSourceBlocks(contraption, source, (info, remaining) -> {
            if (remaining <= 0)
                return remaining;
            int amountInJar = info.getSource();
            if (amountInJar > 0) {
                int toRemove = Math.min(amountInJar, remaining);
                info.removeWithUpdate(contraption.entity.level(), toRemove, contraption.entity);
                return remaining - toRemove;
            }
            return remaining;
        });
        return getSource();
    }

    public boolean hasInfiniteSource() {
        return ContraptionUtils.processSourceBlocks(contraption, false, (info, value) -> value || info instanceof CreativeSourceJarInfo);
    }
}