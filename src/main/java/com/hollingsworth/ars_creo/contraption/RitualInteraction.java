package com.hollingsworth.ars_creo.contraption;

import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class RitualInteraction extends MovingInteractionBehaviour {

    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand activeHand, BlockPos localPos, AbstractContraptionEntity contraptionEntity) {
//        MovementContext ctx = contraptionEntity.getContraption().getActorAt(localPos).right;
//        RitualBehavior.getData(ctx);
//        if (ctx == null || !(ctx.temporaryData instanceof RitualBehavior.RitualData data))
//            return false;
//
//        ItemStack heldStack = player.getMainHandItem();
//        if (heldStack.isEmpty() && data.ritual() != null && !data.ritual().getContext().isDone) {
//            RitualBehavior.startRitual(ctx, player);
//            RitualBehavior.writeToTag(ctx);
//        }
//        if(!heldStack.isEmpty() && !ctx.world.isClientSide()){
//            if(heldStack.getItem() instanceof RitualTablet tablet){
//                if(data.ritual() == null || data.ritual().isRunning()){
//                    RitualBehavior.setRitual(tablet.ritual, ctx);
//                    if (!player.hasInfiniteMaterials())
//                        heldStack.shrink(1);
//                }else{
//                    player.sendSystemMessage(Component.translatable("ars_nouveau.ritual.no_start"));
//                }
//            }else {
//                RitualBehavior.tryBurnStack(ctx.world, BlockPos.containing(ctx.position), data.ritual(), heldStack);
//                RitualBehavior.writeToTag(ctx);
//            }
//        }
        return super.handlePlayerInteraction(player, activeHand, localPos, contraptionEntity);
    }
}
