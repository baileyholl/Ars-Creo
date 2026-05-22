package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static com.hollingsworth.arsnouveau.common.block.tile.PortalTile.getServerLevel;

public class PortalBehavior implements MovementBehaviour {

    public static PortalData getData(MovementContext context) {
        if (context.temporaryData instanceof PortalData data) {
            return data;
        }
        CompoundTag compound = context.blockEntityData;
        PortalData data = new PortalData(
                NBTUtil.getBlockPos(compound, "warp"),
                compound.getString("dim"),
                new Vec2(compound.getFloat("xRot"), compound.getFloat("yRot")),
                compound.getString("display"),
                compound.getBoolean("horizontal"),
                new HashSet<>()
        );
        context.temporaryData = data;
        return data;
    }

    public static void handleEntity(MovementContext context, Entity entityIn) {
        PortalData data = getData(context);
        Level level = context.world;
        if (entityIn.is(context.contraption.entity)) {
            return;
        }

        if ((level instanceof ServerLevel serverLevel)
                && data.warpPos() != null
                && data.dimID() != null
                && PortalTile.teleportEntityTo(entityIn, getServerLevel(data.dimID(), serverLevel), data.warpPos(), data.rotationVec()) != null) {
            ServerLevel serverWorld = getServerLevel(data.dimID(), serverLevel);
            if (serverWorld == null) {
                return;
            }
            level.playSound(null, data.warpPos(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.PORTAL, data.warpPos().getX(), data.warpPos().getY() + 1, data.warpPos().getZ(),
                    4, (serverWorld.random.nextDouble() - 0.5D) * 2.0D, -serverWorld.random.nextDouble(), (serverWorld.random.nextDouble() - 0.5D) * 2.0D, 0.1f);
        }
        entityIn.fallDistance = 0;
    }

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        if (context.world.isClientSide) {
            return;
        }
        for (Entity entityIn : context.world.getEntitiesOfClass(Entity.class, new AABB(pos))) {
            handleEntity(context, entityIn);
        }
    }

    @Override
    public void tick(MovementContext context) {
        if (context.world.isClientSide) {
            return;
        }
        for (Entity entityIn : context.world.getEntitiesOfClass(Entity.class, new AABB(BlockPos.containing(context.position)))) {
            handleEntity(context, entityIn);
        }

    }

    public record PortalData(BlockPos warpPos, String dimID, Vec2 rotationVec,
                             @Nullable String displayName, boolean isHorizontal,
                             Set<Entity> entityQueue) {
    }
}