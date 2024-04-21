package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.Settings;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface CustomNBT {
    String IsServerDataTag = Settings.namespace + "isServerData";

    default void loadForServer(CompoundNBT nbt) {}

    default void saveForServer(CompoundNBT nbt) {
        nbt.putBoolean(IsServerDataTag, true);
    }

    @OnlyIn(Dist.CLIENT)
    default void loadForClient(CompoundNBT nbt) {}

    default void saveForClient(CompoundNBT nbt) {
        nbt.putBoolean(IsServerDataTag, false);
    }
}
