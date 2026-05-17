package com.hollingsworth.ars_creo.api.client;

import com.hollingsworth.ars_creo.client.render.TrainHatMap;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.Event;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class RegisterTrainHatDataEvent extends Event {
    public void register(EntityType<?> entityType, String bone, Vector3f hatScale, Vector3f hatTranslation) {
        TrainHatMap.put(entityType, bone, hatScale, hatTranslation);
    }
}