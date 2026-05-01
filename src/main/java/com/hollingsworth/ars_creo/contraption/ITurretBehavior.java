package com.hollingsworth.ars_creo.contraption;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.ANCodecs;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

public interface ITurretBehavior {

    default void castSpell(MovementContext context, BlockPos pos) {
        ServerLevel world = (ServerLevel) context.world;
        Position iposition = getDispensePosition(pos, context.state);
        Direction direction = context.state.getValue(BasicSpellTurret.FACING);
        FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        SpellCaster spellCaster = ANCodecs.decode(SpellCaster.CODEC.codec(), context.blockEntityData.get("spell_caster"));
        Spell spell = spellCaster.getSpell();
        if(!spell.isValid())
            return;

        EntitySpellResolver resolver = new EntitySpellResolver((new SpellContext(world, spell, fakePlayer, new ContraptionCaster(context, context.contraption.entity))));
        if(!ContraptionUtils.removeSourceFromContraption(context, spell.getCost())) {
            boolean hasNearby = SourceUtil.hasSourceNearby(pos, world, 6, spell.getCost());
            if(!hasNearby || SourceUtil.takeSourceMultipleWithParticles(pos, world, 6, spell.getCost()) == null)
                return;
        }
        if (resolver.castType instanceof MethodProjectile) {
            spellCaster.playSound(pos, world, null, spellCaster.getCurrentSound(), SoundSource.BLOCKS);
            this.shootProjectile(world, pos, resolver, context);
        } else {
            if (resolver.castType instanceof MethodTouch) {
                BlockPos touchPos = BlockPos.containing(iposition.x(), iposition.y(), iposition.z());
                if (direction == Direction.WEST || direction == Direction.NORTH)
                    touchPos = touchPos.relative(direction);

                if (direction == Direction.DOWN)
                    touchPos = touchPos.below();

                resolver.onCastOnBlock(new BlockHitResult(new Vec3(touchPos.getX(), touchPos.getY(), touchPos.getZ()), direction.getOpposite(), new BlockPos(touchPos.getX(), touchPos.getY(), touchPos.getZ()), false));
            }
        }
    }

    default void shootProjectile(ServerLevel world, BlockPos pos, SpellResolver resolver, MovementContext context) {
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

    default Position getDispensePosition(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(BasicSpellTurret.FACING);
        double d0 = pos.getX() + 0.5D * (double)direction.getStepX();
        double d1 = pos.getY() + 0.5D * (double)direction.getStepY();
        double d2 = pos.getZ() + 0.5D * (double)direction.getStepZ();
        return new Vec3(d0, d1, d2);
    }
}