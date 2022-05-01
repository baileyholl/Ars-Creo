package com.hollingsworth.ars_creo.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.block.ManaJar;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateJarContraption {
    public int entityID;
    public BlockPos structurePos;
    public CompoundNBT structureTag;
    public int fillLevel;

    public PacketUpdateJarContraption(PacketBuffer buf) {
        this.structurePos = buf.readBlockPos();
        this.structureTag = buf.readNbt();
        this.fillLevel = buf.readInt();
        this.entityID = buf.readInt();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(structurePos);
        buf.writeNbt(structureTag);
        buf.writeInt(fillLevel);
        buf.writeInt(entityID);
    }

    public PacketUpdateJarContraption(int entityId, BlockPos structurePos, CompoundNBT tag, int fillLevel) {
        this.entityID = entityId;
        this.structurePos = structurePos;
        this.structureTag = tag;
        this.fillLevel = fillLevel;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        (ctx.get()).enqueueWork(() -> {
            Entity entity = ArsNouveau.proxy.getClientWorld().getEntity(entityID);
            if(entity instanceof AbstractContraptionEntity){
                AbstractContraptionEntity contraption = (AbstractContraptionEntity) entity;
                contraption.getContraption().getBlocks().put(structurePos,
                        new Template.BlockInfo(structurePos, BlockRegistry.MANA_JAR.defaultBlockState().setValue(ManaJar.fill, fillLevel), structureTag));
                ContraptionRenderDispatcher.invalidate(contraption.getContraption());
            }
        });
        (ctx.get()).setPacketHandled(true);
    }
}
