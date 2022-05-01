package com.hollingsworth.ars_creo.common.contraption;

import com.hollingsworth.ars_creo.common.network.ACNetworking;
import com.hollingsworth.ars_creo.common.network.PacketUpdateJarContraption;
import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.NbtTags;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.CreativeManaJar;
import com.hollingsworth.arsnouveau.common.block.ManaJar;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.block.BlockState;
import net.minecraft.dispenser.Position;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.Map;

public interface ITurretBehavior {

    default void castSpell(MovementContext context, BlockPos pos){
        ServerWorld world = (ServerWorld) context.world;
        Position iposition = getDispensePosition(pos, context.state);
        Direction direction = context.state.getValue(BasicSpellTurret.FACING);
        FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        Spell spell = Spell.deserialize(context.tileData.getString("spell"));
        EntitySpellResolver resolver = new EntitySpellResolver((new SpellContext(spell, fakePlayer)).withType(SpellContext.CasterType.TURRET));
        if(!canTakeFromJar(context, spell.getCastingCost(), pos) && ManaUtil.takeManaNearbyWithParticles(pos, world, 6, spell.getCastingCost()) == null) {
            return;
        }
        if (resolver.castType instanceof MethodProjectile) {
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

                resolver.onCastOnBlock(new BlockRayTraceResult(new Vector3d(touchPos.getX(), touchPos.getY(), touchPos.getZ()), direction.getOpposite(), new BlockPos(touchPos.getX(), touchPos.getY(), touchPos.getZ()), false), fakePlayer);
            }

        }
    }

    default void shootProjectile(ServerWorld world, BlockPos pos, SpellResolver resolver, BlockState state, MovementContext context) {
        Vector3d facingVec = Vector3d.atLowerCornerOf(context.state.getValue(BasicSpellTurret.FACING).getNormal());
        facingVec = context.rotation.apply(facingVec);
        facingVec.normalize();

        Vector3d effectiveMovementVec = facingVec.scale(0.5f).add(context.motion);


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

    default Direction getClosestFacingDirection(Vector3d exactFacing) {
        return Direction.getNearest(exactFacing.x, exactFacing.y, exactFacing.z);
    }

    default boolean canTakeFromJar(MovementContext context, int amount, BlockPos turretPos){
        ServerWorld world = (ServerWorld) context.world;
        Map<BlockPos, Template.BlockInfo> structureBlocks =  context.contraption.getBlocks();
        for(Template.BlockInfo blockInfo : structureBlocks.values()){
            if(blockInfo.state.getBlock() instanceof CreativeManaJar)
                return true;
            if(blockInfo.state.getBlock() instanceof ManaJar){
                int totalSource = blockInfo.nbt.getInt(NbtTags.MANA_TAG);
                if(totalSource >= amount) {
                    int currentFillState = getFillState(totalSource);
                    int adjustedAmount = totalSource - amount;
                    int nextFillState = getFillState(adjustedAmount);
                    blockInfo.nbt.putInt(NbtTags.MANA_TAG, adjustedAmount);
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
        return new Position(d0, d1, d2);
    }
}
