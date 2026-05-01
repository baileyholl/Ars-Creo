package com.hollingsworth.ars_creo.common;

import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class PotionTank extends FluidTank {
    public static final double POTION_TO_MB = 2.5;
    public static final double MB_TO_POTION = 0.4; // (1 / 2.5)
    private final PotionJarTile jar;

    public PotionTank(PotionJarTile tile) {
        super((int) (tile.getMaxFill() * POTION_TO_MB), stack -> stack.getFluid().isSame(AllFluids.POTION.getSource()));
        jar = tile;
    }

    @Override
    public boolean isFluidValid(@NotNull FluidStack stack) {
        return super.isFluidValid(stack) && stack.has(DataComponents.POTION_CONTENTS) && jar.canAccept(stack.get(DataComponents.POTION_CONTENTS), 1);
    }

    @NotNull
    public FluidStack getFluid() {
        return PotionFluidHandler.getFluidFromPotion(jar.getData(), PotionFluid.BottleType.REGULAR, this.getFluidAmount());
    }

    @Override
    public boolean isEmpty() {
        return jar.getAmount() <= 0;
    }

    @Override
    public int getFluidAmount() {
        return (int) (jar.getAmount() * POTION_TO_MB);
    }

    @Override
    public int fill(FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource))
            return 0;
        PotionContents data = resource.get(DataComponents.POTION_CONTENTS);

        if (!jar.canAccept(data, resource.getAmount()))
            return 0;

        if (action.simulate()) {
            if (jar.getAmount() <= 0 )
                return Math.min(capacity, resource.getAmount());
            return Math.min(capacity - getFluidAmount(), resource.getAmount());
        }

        if (jar.getAmount() <= 0) {
            int amountToAdd = Mth.ceil(resource.getAmount() * MB_TO_POTION);
            if (amountToAdd > 0) {
                jar.add(data, amountToAdd);
                return resource.getAmount();
            }
            return 0;
        }


        int filled = capacity - getFluidAmount(); // room left
        int amountToAdd = Mth.ceil(resource.getAmount() * MB_TO_POTION);

        if (amountToAdd > 0) {
            jar.add(data, amountToAdd);
            if (resource.getAmount() < filled) {
                filled = resource.getAmount(); // room > fluid, return amount filled
            }
        }

        return filled;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        int drained = maxDrain;
        if (getFluidAmount() < drained)
            drained = getFluidAmount();

        FluidStack stack = PotionFluidHandler.getFluidFromPotion(jar.getData(), PotionFluid.BottleType.REGULAR, drained);
        if (action.execute() && drained > 0)
            jar.remove(Mth.ceil(drained * MB_TO_POTION)); // Use the constant for conversion from drained MB to POTION
        return stack;
    }
}