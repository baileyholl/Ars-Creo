package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.crank.HandCrankBlock;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.config.AllConfigs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CarbuncleWheelTile extends GeneratingKineticTileEntity implements IAnimatable {
    public CarbuncleWheelTile() {
        super(ModBlockRegistry.CARBY_TILE);
        setLazyTickRate(20);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1, this::idlePredicate));
    }

    AnimationFactory factory = new AnimationFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public float getGeneratedSpeed() {
        int spd = 16;
        Direction direction = getBlockState().getValue(CarbuncleWheelBlock.FACING);
        if (direction != Direction.DOWN && direction != Direction.UP) {
            if(level.getBlockState(getBlockPos().relative(direction.getClockWise())).is(Tags.Blocks.STORAGE_BLOCKS_GOLD)){
                spd = 24;
            }
        }

        return convertToDirection(spd, getBlockState().getValue(HandCrankBlock.FACING));
    }

    @Override
    public boolean shouldRenderNormally() {
        return true;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        ModBlockRegistry.CARBY_WHEEL
                .updateAllSides(getBlockState(), level, worldPosition);
    }

    private <E extends TileEntity & IAnimatable > PlayState idlePredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
        return PlayState.CONTINUE;
    }

}
