package li.cil.oc.common.tileentity.javaport;

import li.cil.oc.Constants;
import li.cil.oc.api.Items;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.common.tileentity.javaport.traits.*;
import li.cil.oc.util.Color$;
import li.cil.oc.util.ItemColorizer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class Cable extends EnvironmentTileEntity implements NotAnalyzable, ImmibisMicroBlock, Colored {
    private final Node _node;
    private int color = 0;
    protected boolean isChangeScheduled = false;

    public Cable(TileEntityType<? extends Cable> selfType) {
        super(selfType);
        this._node = Network.newNode(this, Visibility.None).create();
        setColor((Integer) Color$.MODULE$.rgbValues().get(DyeColor.LIGHT_GRAY).get());
    }

    @Override
    public Node node() {
        return _node;
    }

    @Override
    public void setIsChangeScheduled(boolean value) {
        isChangeScheduled = value;
    }

    @Override
    public boolean getIsChangeScheduled() {
        return isChangeScheduled;
    }

    public ItemStack createItemStack() {
        ItemStack stack = Items.get(Constants.BlockName.Cable).createItemStack(1);
        if (!((Integer) getColor()).equals(Color$.MODULE$.rgbValues().get(DyeColor.LIGHT_GRAY).get())) {
            ItemColorizer.setColor(stack, getColor());
        }
        return stack;
    }

    public void fromItemStack(ItemStack stack) {
        if (ItemColorizer.hasColor(stack)) {
            setColor(ItemColorizer.getColor(stack));
        }
    }

    @Override
    public int _getColor() {
        return color;
    }

    @Override
    public void _setColor(int value) {
        color = value;
    }

    @Override
    public boolean controlsConnectivity() {
        return true;
    }

    @Override
    public boolean consumesDye() {
        return true;
    }

    @Override
    public void onColorChanged() {
        Colored.super.onColorChanged();
        if (getLevel() != null && isServer()) {
            Network.joinOrCreateNetwork(this);
        }
    }

    @Override
    public void loadForServer(CompoundNBT nbt) {
        Colored.super.loadForServer(nbt);
        super.loadForServer(nbt);
    }

    @Override
    public void saveForServer(CompoundNBT nbt) {
        Colored.super.saveForServer(nbt);
        super.saveForServer(nbt);
    }

    @Override
    public void loadForClient(CompoundNBT nbt) {
        Colored.super.loadForClient(nbt);
        super.loadForClient(nbt);
    }

    @Override
    public void saveForClient(CompoundNBT nbt) {
        Colored.super.saveForClient(nbt);
        super.saveForClient(nbt);
    }
}
