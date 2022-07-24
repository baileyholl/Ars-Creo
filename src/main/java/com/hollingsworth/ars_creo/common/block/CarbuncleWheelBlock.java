package com.hollingsworth.ars_creo.common.block;

import com.hollingsworth.ars_creo.common.registry.ModBlockRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.components.waterwheel.WaterWheelTileEntity;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CarbuncleWheelBlock extends DirectionalKineticBlock implements ITE<CarbuncleWheelTile> {
    public CarbuncleWheelBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction face = context.getClickedFace();
        Direction horizontalFacing = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();

        BlockState placedOn = world.getBlockState(pos.relative(face.getOpposite()));
        if (ModBlockRegistry.CARBY_WHEEL.defaultBlockState().is(placedOn.getBlock()))
            return defaultBlockState().setValue(FACING, placedOn.getValue(FACING));

        Direction facing = face;
        boolean sneaking = player != null && player.isShiftKeyDown();
        if (player != null) {

            Vector3d lookVec = player.getLookAngle();
            double tolerance = 0.985;

            if (!canSurvive(defaultBlockState().setValue(FACING, Direction.UP), world, pos))
                facing = horizontalFacing;
            else if (Vector3d.atLowerCornerOf(Direction.DOWN.getNormal())
                    .dot(lookVec.normalize()) > tolerance)
                facing = Direction.DOWN;
            else if (Vector3d.atLowerCornerOf(Direction.UP.getNormal())
                    .dot(lookVec.normalize()) > tolerance)
                facing = Direction.UP;
            else
                facing = horizontalFacing;

        }

        return defaultBlockState().setValue(FACING, sneaking ? facing.getOpposite() : facing);
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private void updateWheelSpeed(IWorld world, BlockPos pos) {
        withTileEntityDo(world, pos, CarbuncleWheelTile::updateGeneratedRotation);
        withTileEntityDo(world, pos, (te) -> te.setChanged());
    }

    public void updateAllSides(BlockState state, World worldIn, BlockPos pos) {
        updateWheelSpeed(worldIn, pos);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        if (worldIn instanceof WrappedWorld)
            return stateIn;

        updateWheelSpeed(worldIn, currentPos);
        return stateIn;
    }

    @Override
    public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        updateAllSides(state, worldIn, pos);
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CarbuncleWheelTile();
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
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
    public Class<CarbuncleWheelTile> getTileEntityClass() {
        return CarbuncleWheelTile.class;
    }
}
