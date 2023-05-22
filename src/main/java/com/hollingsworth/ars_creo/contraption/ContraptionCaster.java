package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ContraptionCaster implements IWrappedCaster {

    AbstractContraptionEntity contraption;
    List<FilterableItemHandler> itemHandlers;
    public ContraptionCaster(AbstractContraptionEntity contraption){
        this.contraption = contraption;
        itemHandlers = new ArrayList<>();
        itemHandlers.add(new FilterableItemHandler(contraption.getContraption().getSharedInventory()));
    }

    @Override
    public SpellContext.CasterType getCasterType() {
        return SpellContext.CasterType.OTHER;
    }

    @Override
    public @NotNull List<FilterableItemHandler> getInventory() {
        return itemHandlers;
    }
}
