package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;

import java.util.ArrayList;
import java.util.List;

public class ContraptionCaster implements IWrappedCaster {

    AbstractContraptionEntity contraption;
    List<FilterableItemHandler> itemHandlers;

    public ContraptionCaster(MovementContext context, AbstractContraptionEntity contraption){
        this.contraption = contraption;
        itemHandlers = new ArrayList<>();
        itemHandlers.add(new FilterableItemHandler(contraption.getContraption().getStorage().getAllItems()));
    }

    @Override
    public SpellContext.CasterType getCasterType() {
        return SpellContext.CasterType.OTHER;
    }

    @Override
    public List<FilterableItemHandler> getInventory() {
        return itemHandlers;
    }

    @Override
    public void expendMana(int totalCost) {
        // not needed, manually expended
    }
}
