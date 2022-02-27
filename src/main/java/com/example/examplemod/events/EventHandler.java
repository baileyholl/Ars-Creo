package com.example.examplemod.events;

import com.example.examplemod.ArsCreo;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.common.items.VoidJar;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
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
