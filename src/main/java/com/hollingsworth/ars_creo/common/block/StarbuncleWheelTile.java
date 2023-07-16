package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.CreoConfig;
import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class StarbuncleWheelTile extends GeneratingKineticBlockEntity implements GeoBlockEntity {

    public StarbuncleWheelTile(BlockPos pos, BlockState state) {
        super(ModBlockRegistry.STARBY_TILE.get(), pos, state);
        setLazyTickRate(20);
    }

    @Override
    public float getGeneratedSpeed() {
        int spd = CreoConfig.WHEEL_BASE_SPEED.get();
        Direction direction = getBlockState().getValue(StarbuncleWheelBlock.FACING);
        if(direction != Direction.UP && direction != Direction.DOWN) {
            if (level.getBlockState(getBlockPos().relative(direction.getClockWise())).is(Tags.Blocks.STORAGE_BLOCKS_GOLD)) {
                spd = CreoConfig.WHEEL_BONUS_SPEED.get();
            }
        }

        return convertToDirection(spd, getBlockState().getValue(HandCrankBlock.FACING));
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        ModBlockRegistry.STARBY_WHEEL.get().updateAllSides(getBlockState(), level, worldPosition);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 1, (s) ->{
            s.getController().setAnimation(RawAnimation.begin().thenPlay("run"));
            return PlayState.CONTINUE;
        }));
    }
    AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
