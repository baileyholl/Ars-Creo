package com.example.examplemod.events;

import com.example.examplemod.ArsCreo;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.common.items.VoidJar;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsCreo.MODID)
public class EventHandler {

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public static void itemPickupEvent(SpellResolveEvent.Pre event) {
        System.out.println(event.rayTraceResult);
        if(event.rayTraceResult instanceof EntityHitResult &&
                ((EntityHitResult) event.rayTraceResult).getEntity() instanceof AbstractContraptionEntity contraptionEntity){
            System.out.println(contraptionEntity.getContraption().getBlocks().get(new BlockPos(event.rayTraceResult.getLocation())).state);
//            if(contraptionEntity.getContraption().getBlocks().get(new BlockPos(event.rayTraceResult.getLocation()))){
//
//            }
        }
    }
}
