package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.Settings;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.SidedEnvironment;
import li.cil.oc.common.EventHandler$;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public abstract class EnvironmentTileEntity extends ExtendedTileEntity implements Environment, CustomNBT {
    public EnvironmentTileEntity(TileEntityType<?> type) {
        super(type);
    }

    // ----------------------------------------------------------------------- //
    // CustomNBT

    String NodeTag = Settings.namespace + "node";

    @Override
    public void loadForServer(CompoundNBT nbt) {
        super.loadForServer(nbt);
        if (node() != null && node().host() == this) {
            node().loadData(nbt.getCompound(NodeTag));
        }
    }

    @Override
    public void saveForServer(CompoundNBT nbt) {
        super.saveForServer(nbt);
        if (node() != null && node().host() == this) {
            CompoundNBT t = new CompoundNBT();
            node().saveData(t);
            nbt.put(NodeTag, t);
        }
    }

    // ----------------------------------------------------------------------- //
    // ExtendedTileEntity

    @Override
    protected void initialize() {
        super.initialize();
        if (isServer()) {
            EventHandler$.MODULE$.scheduleServer(this);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (getIsChangeScheduled()) {
            getLevel().blockEntityChanged(getBlockPos(), this);
            setIsChangeScheduled(false);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (isServer()) {
            if (node() != null) {
                node().remove();
            }
            if (this instanceof SidedEnvironment) {
                SidedEnvironment sidedEnvironment = (SidedEnvironment)this;
                for (Direction side: Direction.values()) {
                    Node sidedNode = sidedEnvironment.sidedNode(side);
                    if (sidedNode != null) {
                        sidedNode.remove();
                    }
                }
            }
        }
    }
}
