package com.hollingsworth.ars_creo.client.render;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelBlock;
import com.hollingsworth.ars_creo.common.block.StarbuncleWheelTile;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class CarbuncleWheelModel extends AnimatedGeoModel<StarbuncleWheelTile> {


    @Override
    public void setLivingAnimations(StarbuncleWheelTile entity, Integer uniqueID) {
        super.setLivingAnimations(entity, uniqueID);
    }

    @Override
    public void setLivingAnimations(StarbuncleWheelTile entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("wheel");
        Direction facing = entity.getBlockState().getValue(StarbuncleWheelBlock.FACING);
        float angle = getAngleForTe(entity, entity.getBlockPos(), ModBlockRegistry.STARBY_WHEEL.getRotationAxis(entity.getBlockState()));
        if(facing ==  Direction.SOUTH || facing == Direction.EAST) {
            angle = -angle;
        }
        head.setRotationY( angle);
    }

    public static float getAngleForTe(KineticBlockEntity te, final BlockPos pos, Direction.Axis axis) {
        float time = AnimationTickHolder.getRenderTime(te.getLevel());
        float offset = getRotationOffsetForPosition(te, pos, axis);
        return ((time * te.getSpeed() * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;
    }
    protected static float getRotationOffsetForPosition(KineticBlockEntity te, final BlockPos pos, final Direction.Axis axis) {
        float offset = ICogWheel.isLargeCog(te.getBlockState()) ? 11.25f : 0;
        double d = (((axis == Direction.Axis.X) ? 0 : pos.getX()) + ((axis == Direction.Axis.Y) ? 0 : pos.getY())
                + ((axis == Direction.Axis.Z) ? 0 : pos.getZ())) % 2;
        if (d == 0) {
            offset = 22.5f;
        }
        return offset;
    }

    @Override
    public ResourceLocation getModelLocation(StarbuncleWheelTile object) {
        return new ResourceLocation(ArsCreo.MODID, "geo/starbuncle_wheel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(StarbuncleWheelTile object) {
        return new ResourceLocation(ArsCreo.MODID, "textures/blocks/starbuncle_wheel.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(StarbuncleWheelTile animatable) {
        return new ResourceLocation(ArsCreo.MODID, "animations/starbuncle_wheel_animation.json");
    }
}
