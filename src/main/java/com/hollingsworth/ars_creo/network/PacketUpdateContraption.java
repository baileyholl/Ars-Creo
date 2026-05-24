package com.hollingsworth.ars_creo.network;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.network.AbstractPacket;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class PacketUpdateContraption  extends AbstractPacket {
    public static final Type<PacketUpdateContraption> TYPE = new Type(ArsCreo.prefix("update_contraption"));
    public static final StreamCodec<ByteBuf, PacketUpdateContraption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, i-> i.entityID,
            BlockPos.STREAM_CODEC, i -> i.structurePos,
            CatnipStreamCodecs.BLOCK_STATE, i -> i.newState,
            ByteBufCodecs.COMPOUND_TAG, i -> i.structureTag,
            PacketUpdateContraption::new
    );

    public int entityID;
    public BlockPos structurePos;
    public BlockState newState;
    public CompoundTag structureTag;

    public PacketUpdateContraption(int entityID, BlockPos localPos, BlockState newState, CompoundTag structureTag) {
        this.entityID = entityID;
        this.structurePos = localPos;
        this.newState = newState;
        this.structureTag = structureTag;
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        Entity entity = ArsNouveau.proxy.getClientWorld().getEntity(entityID);
        if (entity instanceof AbstractContraptionEntity contraption) {
            contraption.getContraption().getBlocks().put(structurePos,
                    new StructureTemplate.StructureBlockInfo(structurePos, newState, structureTag));
            contraption.getContraption().getActorAt(structurePos).right.blockEntityData = structureTag;
            contraption.getContraption().invalidateClientContraptionChildren();
        }
    }
}