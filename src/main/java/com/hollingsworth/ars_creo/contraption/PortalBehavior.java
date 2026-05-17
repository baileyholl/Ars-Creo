package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketWarpPosition;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static com.hollingsworth.arsnouveau.common.block.tile.PortalTile.getServerLevel;

public class PortalBehavior implements MovementBehaviour {
    public BlockPos warpPos;
    public String dimID;
    public Vec2 rotationVec;
    public @Nullable String displayName;
    public boolean isHorizontal;
    public Set<Entity> entityQueue = new HashSet<>();
    @Override
    public void startMoving(MovementContext context) {
        MovementBehaviour.super.startMoving(context);
        CompoundTag compound = context.blockEntityData;
        this.dimID = compound.getString("dim");
        this.warpPos = NBTUtil.getBlockPos(compound, "warp");
        this.rotationVec = new Vec2(compound.getFloat("xRot"), compound.getFloat("yRot"));
        this.displayName = compound.getString("display");
        this.isHorizontal = compound.getBoolean("horizontal");
    }

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        Level level = context.world;
        for (Entity entityIn : context.world.getEntitiesOfClass(Entity.class, new AABB(pos))) {
            if(entityIn instanceof Player player) {
                entityQueue.add(player);
            }else{
                if ((level instanceof ServerLevel serverLevel)
                        && warpPos != null
                        && dimID != null
                        && PortalTile.teleportEntityTo(entityIn, getServerLevel(dimID, serverLevel), this.warpPos, rotationVec) != null) {
                    ServerLevel serverWorld = getServerLevel(dimID, serverLevel);
                    if(serverWorld == null){
                        return;
                    }
                    Networking.sendToNearbyClient(serverWorld, entityIn, new PacketWarpPosition(entityIn.getId(), entityIn.getX() + 0.5, entityIn.getY(), entityIn.getZ() + 0.5, rotationVec.x, rotationVec.y));
                    serverLevel.sendParticles(ParticleTypes.PORTAL, warpPos.getX(), warpPos.getY() + 1, warpPos.getZ(),
                            4, (serverWorld.random.nextDouble() - 0.5D) * 2.0D, -serverWorld.random.nextDouble(), (serverWorld.random.nextDouble() - 0.5D) * 2.0D, 0.1f);
                }
                entityIn.fallDistance = 0;
            }
        }
    }

    @Override
    public void tick(MovementContext context) {
        Level level = context.world;
        if (level != null && level instanceof ServerLevel serverLevel && warpPos != null) {
            Set<Entity> entities = entityQueue;
            if (!entities.isEmpty()) {
                for (Entity e : entities) {
                    if (e instanceof EntityFollowProjectile)
                        continue;
                    if(dimID != null && PortalTile.teleportEntityTo(e, getServerLevel(dimID, serverLevel), this.warpPos, rotationVec) != null){
                        level.playSound(null, warpPos, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        serverLevel.sendParticles(ParticleTypes.PORTAL, warpPos.getX(), warpPos.getY() + 1, warpPos.getZ(),
                                4, (level.random.nextDouble() - 0.5D) * 2.0D, -level.random.nextDouble(), (level.random.nextDouble() - 0.5D) * 2.0D, 0.1f);
                    }
                }
                entityQueue.clear();
            }
        }

    }
}
