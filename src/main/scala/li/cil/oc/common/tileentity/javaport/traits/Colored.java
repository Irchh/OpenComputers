package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.Settings;
import li.cil.oc.server.PacketSender;
import li.cil.oc.util.Color;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface Colored extends li.cil.oc.api.internal.Colored, ITileEntity, CustomNBT {
    default boolean consumesDye() {
        return false;
    }

    void _setColor(int value);
    int _getColor();

    @Override
    default void setColor(int value) {
        _setColor(value);
        onColorChanged();
    }

    @Override
    default int getColor() {
        return _getColor();
    }

    @Override
    default boolean controlsConnectivity() {
        return false;
    }

    default void onColorChanged() {
        if (te().getLevel() != null && te().isServer()) {
            PacketSender.sendColorChange(this);
        }
    }

    // ----------------------------------------------------------------------- //
    // NBT

    String RenderColorTag = Settings.namespace + "renderColorRGB";
    String RenderColorTagCompat = Settings.namespace + "renderColor";

    @Override
    default void loadForServer(CompoundNBT nbt) {
        if (nbt.contains(RenderColorTagCompat)) {
            _setColor((Integer) Color.rgbValues().get(DyeColor.byId(nbt.getInt(RenderColorTagCompat))).get());
        }
        if (nbt.contains(RenderColorTag)) {
            _setColor(nbt.getInt(RenderColorTag));
        }
    }

    @Override
    default void saveForServer(CompoundNBT nbt) {
        nbt.putInt(RenderColorTag, _getColor());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    default void loadForClient(CompoundNBT nbt) {
        _setColor(nbt.getInt(RenderColorTag));
    }

    @Override
    default void saveForClient(CompoundNBT nbt) {
        nbt.putInt(RenderColorTag, _getColor());
    }
}
