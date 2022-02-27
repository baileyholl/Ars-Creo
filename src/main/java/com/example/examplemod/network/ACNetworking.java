package com.example.examplemod.network;

import com.example.examplemod.ArsCreo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ACNetworking {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    public static int nextID(){return ID++;}
    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ArsCreo.MODID, "network"), () -> "1.0", s->true, s->true);

        INSTANCE.registerMessage(nextID(),
                PacketUpdateJarContraption.class,
                PacketUpdateJarContraption::toBytes,
                PacketUpdateJarContraption::new,
                PacketUpdateJarContraption::handle);
    }

    public static void sendToNearby(World world, BlockPos pos, Object toSend){
        if (world instanceof ServerWorld) {
            ServerWorld ws = (ServerWorld) world;
            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(World world, Entity e, Object toSend) {
        sendToNearby(world, e.blockPosition(), toSend);
    }

    public static void sendToPlayer(Object msg, PlayerEntity player) {
        if (EffectiveSide.get() == LogicalSide.SERVER) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
        }
    }
}
