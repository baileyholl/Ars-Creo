package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StarbuncleWheelBlock extends DirectionalKineticBlock implements IBE<StarbuncleWheelTile> {
    public StarbuncleWheelBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        Direction horizontalFacing = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Player player = context.getPlayer();

        BlockState placedOn = world.getBlockState(pos.relative(face.getOpposite()));
        if(placedOn.is(ModBlockRegistry.STARBY_WHEEL)){
            return defaultBlockState().setValue(FACING, placedOn.getValue(FACING));
        }

        Direction facing = face;
        boolean sneaking = player != null && player.isShiftKeyDown();
        facing = horizontalFacing;

        return defaultBlockState().setValue(FACING, sneaking ? facing.getOpposite() : facing);
    }

    private void updateWheelSpeed(LevelAccessor world, BlockPos pos) {
        withBlockEntityDo(world, pos, StarbuncleWheelTile::updateGeneratedRotation);
        withBlockEntityDo(world, pos, (te) -> te.setChanged());
    }

    public void updateAllSides(BlockState state, Level worldIn, BlockPos pos) {
        updateWheelSpeed(worldIn, pos);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        if (worldIn instanceof WrappedWorld)
            return stateIn;

        updateWheelSpeed(worldIn, currentPos);
        return stateIn;
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        updateAllSides(state, worldIn, pos);
    }


    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return state.getValue(FACING)
                .getAxis() == face.getAxis();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }

    @Override
    public float getParticleTargetRadius() {
        return 1.125f;
    }

    @Override
    public float getParticleInitialRadius() {
        return 1f;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return super.getMinimumRequiredSpeedLevel();
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }


    @Override
    public Class<StarbuncleWheelTile> getBlockEntityClass() {
        return StarbuncleWheelTile.class;
    }

    @Override
    public BlockEntityType<? extends StarbuncleWheelTile> getBlockEntityType() {
        return ModBlockRegistry.STARBY_TILE;
    }
}
