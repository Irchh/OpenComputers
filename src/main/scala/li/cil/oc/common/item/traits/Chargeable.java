package li.cil.oc.common.item.traits;

import li.cil.oc.Settings;
import li.cil.oc.integration.opencomputers.ModOpenComputers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// TODO: Forge power capabilities.
public interface Chargeable extends li.cil.oc.api.driver.item.Chargeable {
    double maxCharge(ItemStack stack);

    double getCharge(ItemStack stack);

    void setCharge(ItemStack stack, double amount);

    default boolean canExtract(ItemStack stack) {
        return false;
    }

    // Object
    ResourceLocation KEY = new ResourceLocation(ModOpenComputers.getMod().id(), "chargeable");

    static double convertForgeEnergyToOpenComputers(int fe) {
        return fe / Settings.get().ratioForgeEnergy;
    }

    static int convertOpenComputersToForgeEnergy(double oc) {
        return (int) (oc * Settings.get().ratioForgeEnergy);
    }

    @FunctionalInterface
    interface SaveFunction {
        void save(double value);
    }

    static double applyCharge(double amount, double current, double maximum, SaveFunction save) {
        double target = current + amount;
        double result = Math.min(Math.max(target, 0), maximum);
        double used = result - current;
        double unused = amount - used;
        if (used > Double.MIN_NORMAL || used < -Double.MIN_NORMAL) {
            save.save(used);
        }
        return unused;
    }

    class Provider implements ICapabilityProvider, NonNullSupplier<Provider>, IEnergyStorage {
        private LazyOptional<Provider> wrapper = LazyOptional.of(this);

        private ItemStack stack;
        private Chargeable item;

        public Provider(ItemStack stack, Chargeable item) {
            this.stack = stack;
            this.item = item;
        }

        @Nonnull
        @Override
        public Provider get() {
            return this;
        }

        public void invalidate() {
            wrapper.invalidate();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
            if (capability == CapabilityEnergy.ENERGY)
                return wrapper.cast();
            return LazyOptional.empty();
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return maxReceive - convertOpenComputersToForgeEnergy(item.charge(stack, convertForgeEnergyToOpenComputers(maxReceive), simulate));
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (canExtract()) {
                return -receiveEnergy(-maxExtract, simulate);
            } else {
                return 0;
            }
        }

        @Override
        public int getEnergyStored() {
            return convertOpenComputersToForgeEnergy(item.getCharge(stack));
        }

        @Override
        public int getMaxEnergyStored() {
            return convertOpenComputersToForgeEnergy(item.maxCharge(stack));
        }

        @Override
        public boolean canExtract() {
            return item.canExtract(stack);
        }

        @Override
        public boolean canReceive() {
            return item.canCharge(stack);
        }
    }
}
