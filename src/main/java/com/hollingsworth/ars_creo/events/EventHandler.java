package com.hollingsworth.ars_creo.events;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsCreo.MODID)
public class EventHandler {

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public static void itemPickupEvent(SpellResolveEvent.Pre event) {
        if(event.rayTraceResult instanceof EntityHitResult &&
                ((EntityHitResult) event.rayTraceResult).getEntity() instanceof AbstractContraptionEntity contraptionEntity){
//            if(contraptionEntity.getContraption().getBlocks().get(new BlockPos(event.rayTraceResult.getLocation()))){
//
//            }
        }
    }
}
