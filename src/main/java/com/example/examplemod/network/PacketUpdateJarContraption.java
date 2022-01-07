package com.example.examplemod.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateJarContraption {
    public int entityID;
    public BlockPos structurePos;
    public CompoundTag structureTag;
    public int fillLevel;

    public PacketUpdateJarContraption(FriendlyByteBuf buf) {
        this.structurePos = buf.readBlockPos();
        this.structureTag = buf.readNbt();
        this.fillLevel = buf.readInt();
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(structurePos);
        buf.writeNbt(structureTag);
        buf.writeInt(fillLevel);
        buf.writeInt(entityID);
    }

    public PacketUpdateJarContraption(int entityId, BlockPos structurePos, CompoundTag tag, int fillLevel) {
        this.entityID = entityId;
        this.structurePos = structurePos;
        this.structureTag = tag;
        this.fillLevel = fillLevel;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Entity entity = ArsNouveau.proxy.getClientWorld().getEntity(entityID);
            if(entity instanceof AbstractContraptionEntity contraption){
                contraption.getContraption().getBlocks().put(structurePos,
                        new StructureTemplate.StructureBlockInfo(structurePos, BlockRegistry.SOURCE_JAR.defaultBlockState().setValue(SourceJar.fill, fillLevel), structureTag));
                ContraptionRenderDispatcher.invalidate(contraption.getContraption());
            }
        });
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
