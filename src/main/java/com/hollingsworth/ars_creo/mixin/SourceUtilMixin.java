package com.hollingsworth.ars_creo.mixin;

import com.google.common.collect.Multimap;
import com.hollingsworth.ars_creo.contraption.source.ContraptionSource;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;
import java.util.Map;

@Mixin(SourceUtil.class)
public class SourceUtilMixin {
    @Inject(method = "takeSourceMultiple", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/hollingsworth/arsnouveau/api/source/ISpecialSourceProvider;getSource()Lcom/hollingsworth/arsnouveau/api/source/ISourceTile;", ordinal = 0), cancellable = true)
    private static void contraptionInfiniteSource(BlockPos pos, Level level, int range, int source, CallbackInfoReturnable<List<ISpecialSourceProvider>> cir, @Local(name = "provider") ISpecialSourceProvider provider, @Local(name = "sourceTile") ISourceTile sourceTile, @Local(name = "takenFrom") Multimap<ISpecialSourceProvider, Integer> takenFrom) {
        if (sourceTile instanceof ContraptionSource contraptionSource && contraptionSource.hasInfiniteSource()) {
            for (Map.Entry<ISpecialSourceProvider, Integer> entry : takenFrom.entries())
                entry.getKey().getSource().addSource(entry.getValue());
            cir.setReturnValue(List.of(provider));
        }
    }

    @SuppressWarnings("all")
    @Inject(method = "hasSourceNearby", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/hollingsworth/arsnouveau/api/source/ISpecialSourceProvider;getSource()Lcom/hollingsworth/arsnouveau/api/source/ISourceTile;"), cancellable = true)
    private static void contraptionInfiniteSource(BlockPos pos, Level world, int range, int source, CallbackInfoReturnable<Boolean> cir, @Local(name = "sourceTile") ISourceTile sourceTile) {
        if (sourceTile instanceof ContraptionSource contraptionSource && contraptionSource.hasInfiniteSource())
            cir.setReturnValue(true);
    }
}