package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.registry.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.api.registry.RitualRegistry;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class RitualBehavior implements MovementBehaviour {

    public static RitualData getData(MovementContext context) {
        if (context.temporaryData instanceof RitualData d) {
            return d;
        }
        return setTemporaryData(context);
    }

    private static RitualData setTemporaryData(MovementContext context) {
        CompoundTag tag = context.blockEntityData;
        AbstractRitual ritual = null;
        String ritualIDString = tag.getString("ritualID");
        if (!ritualIDString.isEmpty()) {
            ResourceLocation ritualID = ResourceLocation.tryParse(ritualIDString);
            ritual = RitualRegistry.getRitual(ritualID);
            if (ritual != null) {
                ritual.tile = createFakeTile(context);
                ritual.read(context.world.registryAccess(), tag);
            }
        }
        ParticleColor color = ParticleColorRegistry.from(tag.getCompound("color"));
        boolean isDecorative = tag.getBoolean("decorative");
        boolean isOff = tag.getBoolean("off");
        RitualData data = new RitualData(ritual, color, isDecorative, isOff);
        context.temporaryData = data;
        return data;
    }

//    public static void setRitual(AbstractRitual ritual, MovementContext context) {
//        RitualData data = getData(context);
//        ritual.tile = createFakeTile(context);
//        context.temporaryData = new RitualData(ritual, data.color(), false, data.isOff());
//        // Write to tag here so we have the updated tag when setting the new blockState on the contraption
//        writeToTag(context);
//        Level world = context.world;
//        BlockState state = context.state;
//        BlockState newState = state.setValue(RitualBrazierBlock.LIT, true);
//
//        if(!world.isClientSide){
//            StructureTemplate.StructureBlockInfo info = context.contraption.getBlocks()
//                    .get(context.localPos);
//            context.contraption.entity.setBlock(context.localPos, new StructureTemplate.StructureBlockInfo(info.pos(), newState, context.blockEntityData));
//            ACNetworking.sendToNearbyClient(world, BlockPos.containing(context.position), new PacketUpdateContraption(context.contraption.entity.getId(), context.localPos, newState, context.blockEntityData));
//        }
//        world.playSound(null, BlockPos.containing(context.position), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
//    }
//
//    public static void startRitual(MovementContext context, Player player) {
//        RitualData data = getData(context);
//        AbstractRitual ritual = data.ritual;
//        Level level = context.world;
//        if (ritual == null || !ritual.canStart(player) || ritual.isRunning())
//            return;
//        level.playSound(null, BlockPos.containing(context.position), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.NEUTRAL, 1.0f, 1.0f);
//        ritual.onStart(player);
//    }

    @Override
    public void tick(MovementContext context) {
        // Rituals have access to a tile that we are now mocking, some rituals not working is to be expected.
        try {
            tickBrazierBehavior(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tickBrazierBehavior(MovementContext context) {
        RitualData data = getData(context);
        Level level = context.world;
        BlockPos pos = BlockPos.containing(context.position);
        AbstractRitual ritual = data.ritual();
        RitualBrazierTile standIn = createFakeTile(context);
        if (data.isDecorative() && level.isClientSide) {
            makeParticle(context, data.color.transition((int) level.getGameTime() * 20), data.color.transition((int) level.getGameTime() * 20 + 200), 10);
            return;
        }
        if (level.isClientSide && ritual != null) {
            makeParticle(context, ritual.getCenterColor(), ritual.getOuterColor(), ritual.getParticleIntensity());
        }
        if (ritual == null || data.isOff()) {
            return;
        }

        standIn.ritual = ritual;
        ritual.tile = standIn;

        if (ritual.getContext().isDone) {
            ritual.onEnd();
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0f, 1.0f);
            context.temporaryData = new RitualData(null, data.color(), data.isDecorative(), data.isOff());
            writeToTag(context);
            return;
        }
        if (!ritual.isRunning() && !level.isClientSide && level.getGameTime() % 5 == 0) {
            for (ItemEntity i : level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(1))) {
                tryBurnStack(level, pos, ritual, i.getItem());
            }
        }
        if (ritual.consumesSource() && ritual.needsSourceNow()) {
            int cost = ritual.getSourceCost();
            if (SourceUtil.takeSourceMultipleWithParticles(pos, level, 6, cost) != null) {
                ritual.setNeedsSource(false);
            } else {
                return;
            }
        }
        ritual.tryTick(standIn);
        writeToTag(context);
    }

    public static void makeParticle(MovementContext context, ParticleColor centerColor, ParticleColor outerColor, int intensity) {
        Level level = context.world;
        Position position = context.position;
        AbstractRitual ritual = getData(context).ritual;
        double xzOffset = 0.25;
        boolean isWeakFire = ritual != null && ritual.needsSourceNow();
        double centerYMax = isWeakFire ? 0.1 : 0.2;
        double outerYMax = isWeakFire ? 0.5 : 0.7;
        double ySpeed = isWeakFire ? 0.02f : 0.05f;
        intensity = isWeakFire ? intensity / 2 : intensity;
        for (int i = 0; i < intensity; i++) {
            level.addParticle(
                    GlowParticleData.createData(centerColor.transition((int) level.getGameTime() * 10)),
                    position.x() + ParticleUtil.inRange(-xzOffset / 2, xzOffset / 2), position.y() + 0.5 + ParticleUtil.inRange(-0.05, centerYMax), position.z() + ParticleUtil.inRange(-xzOffset / 2, xzOffset / 2),
                    0, ParticleUtil.inRange(0.0, ySpeed), 0);
        }
        for (int i = 0; i < intensity; i++) {
            level.addParticle(
                    GlowParticleData.createData(outerColor.transition((int) level.getGameTime() * 10)),
                    position.x() + ParticleUtil.inRange(-xzOffset, xzOffset), position.y() + 0.5 + ParticleUtil.inRange(0, outerYMax), position.z() + ParticleUtil.inRange(-xzOffset, xzOffset),
                    0, ParticleUtil.inRange(0.0, ySpeed), 0);
        }
    }

    private static RitualBrazierTile createFakeTile(MovementContext context) {
        RitualBrazierTile standIn = new RitualBrazierTile(BlockPos.containing(context.position), context.state);
        standIn.setLevel(context.world);
        return standIn;
    }

    public static void tryBurnStack(Level level, BlockPos pos, @Nullable AbstractRitual ritual, ItemStack stack) {
        if (ritual != null && !ritual.isRunning() && !level.isClientSide && ritual.canConsumeItem(stack)) {
            ritual.onItemConsumed(stack);
            ParticleUtil.spawnPoof((ServerLevel) level, pos);
            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.3f, 1.0f);
        }
    }

    public static void writeToTag(MovementContext context) {
        Level level = context.world;
        if (!(context.temporaryData instanceof RitualData data)) {
            return;
        }
        CompoundTag tag = new CompoundTag();
        AbstractRitual ritual = data.ritual;
        ParticleColor color = data.color;
        boolean isDecorative = data.isDecorative();
        boolean isOff = data.isOff();

        if (ritual != null) {
            tag.putString("ritualID", ritual.getRegistryName().toString());
            ritual.write(level.registryAccess(), tag);
        } else {
            tag.remove("ritualID");
        }
        tag.put("color", color.serialize());
        tag.putBoolean("decorative", isDecorative);
        tag.putBoolean("off", isOff);
        context.blockEntityData = tag;
    }

    public record RitualData(@Nullable AbstractRitual ritual, ParticleColor color,
                             boolean isDecorative, boolean isOff) {
    }
}
