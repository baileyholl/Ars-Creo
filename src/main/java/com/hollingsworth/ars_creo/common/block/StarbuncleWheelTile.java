package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.crank.HandCrankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StarbuncleWheelTile extends GeneratingKineticTileEntity implements IAnimatable {
    public StarbuncleWheelTile(BlockPos pos, BlockState state) {
        super(ModBlockRegistry.STARBY_TILE, pos, state);
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
        Direction direction = getBlockState().getValue(StarbuncleWheelBlock.FACING);
        if(direction != Direction.UP && direction != Direction.DOWN) {
            if (level.getBlockState(getBlockPos().relative(direction.getClockWise())).is(Tags.Blocks.STORAGE_BLOCKS_GOLD)) {
                spd = 24;
            }
        }

        return convertToDirection(spd, getBlockState().getValue(HandCrankBlock.FACING));
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        ModBlockRegistry.STARBY_WHEEL.updateAllSides(getBlockState(), level, worldPosition);
    }

    private PlayState idlePredicate(AnimationEvent event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
        return PlayState.CONTINUE;
    }

}
