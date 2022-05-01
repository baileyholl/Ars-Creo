package com.hollingsworth.ars_creo.common.events;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsCreo.MODID)
public class EventHandler {

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public static void itemPickupEvent(SpellResolveEvent.Pre event) {
//        System.out.println(event.rayTraceResult);
//        if(event.rayTraceResult instanceof EntityRayTraceResult &&
//                ((EntityRayTraceResult) event.rayTraceResult).getEntity() instanceof AbstractContraptionEntity contraptionEntity){
//
//        }
    }
}
