package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.ars_creo.network.ACNetworking;
import com.hollingsworth.ars_creo.network.PacketUpdateJarContraption;
import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.CreativeSourceJar;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.Map;

public interface ITurretBehavior {

    default void castSpell(MovementContext context, BlockPos pos){
        ServerLevel world = (ServerLevel) context.world;
        Position iposition = getDispensePosition(pos, context.state);
        Direction direction = context.state.getValue(BasicSpellTurret.FACING);
        FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        TurretSpellCaster spellCaster = new TurretSpellCaster(context.tileData);
        Spell spell = spellCaster.getSpell();
        if(!spell.isValid()){
            return;
        }
        EntitySpellResolver resolver = new EntitySpellResolver((new SpellContext(spell, fakePlayer)).withType(SpellContext.CasterType.TURRET));
        if(!canTakeFromJar(context, spell.getCastingCost(), pos) && SourceUtil.takeSourceNearbyWithParticles(pos, world, 6, spell.getCastingCost()) == null) {
            return;
        }
        if (resolver.castType instanceof MethodProjectile) {
            spellCaster.playSound(pos, world, null, spellCaster.getCurrentSound(), SoundSource.BLOCKS);
            this.shootProjectile(world, pos, resolver, context.state, context);
        } else {
            if (resolver.castType instanceof MethodTouch) {
                BlockPos touchPos = new BlockPos(iposition.x(), iposition.y(), iposition.z());
                if (direction == Direction.WEST || direction == Direction.NORTH) {
                    touchPos = touchPos.relative(direction);
                }

                if (direction == Direction.DOWN) {
                    touchPos = touchPos.below();
                }

                resolver.onCastOnBlock(new BlockHitResult(new Vec3(touchPos.getX(), touchPos.getY(), touchPos.getZ()), direction.getOpposite(), new BlockPos(touchPos.getX(), touchPos.getY(), touchPos.getZ()), false), fakePlayer);
            }

        }
    }

    default void shootProjectile(ServerLevel world, BlockPos pos, SpellResolver resolver, BlockState state, MovementContext context) {
        Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(BasicSpellTurret.FACING).getNormal());
        facingVec = context.rotation.apply(facingVec);
        facingVec.normalize();

        Vec3 effectiveMovementVec = facingVec.scale(0.5f).add(context.motion);


        FakePlayer fakePlayer = FakePlayerFactory.getMinecraft(world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
        spell.setOwner(fakePlayer);
        double x = pos.getX() + facingVec.x * .7 + .5;
        double y = pos.getY() + facingVec.y * .7 + .5;
        double z = pos.getZ() + facingVec.z * .7 + .5;
        spell.setPos(x,y,z);
        spell.shoot(effectiveMovementVec.x, effectiveMovementVec.y, effectiveMovementVec.z, 0.4f, 0);

        world.addFreshEntity(spell);
    }

    default Direction getClosestFacingDirection(Vec3 exactFacing) {
        return Direction.getNearest(exactFacing.x, exactFacing.y, exactFacing.z);
    }

    default boolean canTakeFromJar(MovementContext context, int amount, BlockPos turretPos){
        ServerLevel world = (ServerLevel) context.world;
        Map<BlockPos, StructureTemplate.StructureBlockInfo> structureBlocks =  context.contraption.getBlocks();
        for(StructureTemplate.StructureBlockInfo blockInfo : structureBlocks.values()){
            if(blockInfo.state.getBlock() instanceof CreativeSourceJar)
                return true;
            if(blockInfo.state.getBlock() instanceof SourceJar){
                int totalSource = blockInfo.nbt.getInt(SourceJarTile.SOURCE_TAG);
                if(totalSource >= amount) {
                    int currentFillState = getFillState(totalSource);
                    int adjustedAmount = totalSource - amount;
                    int nextFillState = getFillState(adjustedAmount);
                    blockInfo.nbt.putInt(SourceJarTile.SOURCE_TAG, adjustedAmount);
                    if(currentFillState != nextFillState)
                        ACNetworking.sendToNearby(world, turretPos, new PacketUpdateJarContraption(context.contraption.entity.getId(), blockInfo.pos, blockInfo.nbt, nextFillState));
                    return true;
                }
            }
        }
        return false;
    }

    default int getFillState(int source){
        int fillState = 0;
        if (source > 0 && source < 1000) {
            fillState = 1;
        } else if (source != 0) {
            fillState = source / 1000 + 1;
        }
        return fillState;
    }

    default Position getDispensePosition(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(BasicSpellTurret.FACING);
        double d0 = pos.getX() + 0.5D * (double)direction.getStepX();
        double d1 = pos.getY() + 0.5D * (double)direction.getStepY();
        double d2 = pos.getZ() + 0.5D * (double)direction.getStepZ();
        return new PositionImpl(d0, d1, d2);
    }
}
