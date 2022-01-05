package com.example.examplemod.contraption;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.simibubi.create.content.contraptions.components.deployer.DeployerFakePlayer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerHandler;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class SpellTurretBehavior extends MovementBehaviour {

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        if (!context.world.isClientSide) {
            if(context.state.isAir())
                return;

            ServerLevel world = (ServerLevel) context.world;
            Position iposition = getDispensePosition(pos, context.state);
            Direction direction = (Direction)context.state.getValue(BasicSpellTurret.FACING);
            FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
            fakePlayer.setPos((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            EntitySpellResolver resolver = new EntitySpellResolver((new SpellContext(new Spell(MethodProjectile.INSTANCE), fakePlayer)).withType(SpellContext.CasterType.TURRET));
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

                    resolver.onCastOnBlock(new BlockHitResult(new Vec3((double)touchPos.getX(), (double)touchPos.getY(), (double)touchPos.getZ()), direction.getOpposite(), new BlockPos(touchPos.getX(), touchPos.getY(), touchPos.getZ()), false), fakePlayer);
                }

            }
        }
    }

    @Override
    public boolean renderAsNormalTileEntity() {
        return true;
    }

    public void shootProjectile(ServerLevel world, BlockPos pos, SpellResolver resolver, BlockState state, MovementContext context) {
        Position iposition = getDispensePosition(pos, state);
        Direction direction = state.getValue(DispenserBlock.FACING);
        FakePlayer fakePlayer = FakePlayerFactory.getMinecraft(world);
        fakePlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
        EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
        spell.setOwner(fakePlayer);
        spell.setPos(iposition.x(), iposition.y(), iposition.z());
        Vec3i normal = direction.getNormal();
        Vec3 facingVec = Vec3.atLowerCornerOf((direction.getNormal()));
        facingVec = context.rotation.apply(facingVec);
        facingVec.normalize();

        spell.shoot(direction.getStepX(), (double)((float)direction.getStepY()), (double)direction.getStepZ(), 0.5F, 0.0F);
        spell.setDeltaMovement(spell.getDeltaMovement().add(facingVec));
        System.out.println(spell.getXRot());
        world.addFreshEntity(spell);
    }



    public static Position getDispensePosition(BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.getValue(BasicSpellTurret.FACING);
        double d0 = pos.getX() + 0.5D * (double)direction.getStepX();
        double d1 = pos.getY() + 0.5D * (double)direction.getStepY();
        double d2 = pos.getZ() + 0.5D * (double)direction.getStepZ();
        return new PositionImpl(d0, d1, d2);
    }
}
