package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.OpenComputers;
import li.cil.oc.Settings;
import li.cil.oc.client.Sound;
import li.cil.oc.common.SaveHandler;
import li.cil.oc.util.SideTracker;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public abstract class ExtendedTileEntity extends TileEntity implements ITileEntity, CustomNBT {
    private final String IsServerDataTag = Settings.namespace + "isServerData";

    public ExtendedTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public ExtendedTileEntity te() {
        return this;
    }

    public double x() {
        return getBlockPos().getX();
    }

    public double y() {
        return getBlockPos().getY();
    }

    public double z() {
        return getBlockPos().getZ();
    }

    public boolean isClient() {
        return !isServer();
    }

    public boolean isServer() {
        if (getLevel() != null)
            return !getLevel().isClientSide;
        else
            return SideTracker.isServer();
    }

    // ----------------------------------------------------------------------- //
    //

    public void updateEntity() {
        if (Settings.get().periodicallyForceLightUpdate && getLevel().getGameTime() % 40 == 0 && getBlockState().getBlock().getLightValue(getLevel().getBlockState(getBlockPos()), getLevel(), getBlockPos()) > 0) {
            getLevel().sendBlockUpdated(getBlockPos(), getLevel().getBlockState(getBlockPos()), getLevel().getBlockState(getBlockPos()), 3);
        }
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        initialize();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        dispose();
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        try {
            dispose();
        } catch (Throwable t) {
            OpenComputers.log().error("Failed properly disposing a tile entity, things may leak and or break.", t);
        }
    }

    protected void initialize() {}

    public void dispose() {
        if (isClient()) {
            // Note: chunk unload is handled by sound via event handler.
            Sound.stopLoop(this);
        }
    }

    // ----------------------------------------------------------------------- //
    // nbt stuff, move to interface?

    /*void loadForServer(CompoundNBT nbt) {}

    void saveForServer(CompoundNBT nbt) {
        nbt.putBoolean(IsServerDataTag, true);
        super.save(nbt);
    }

    @OnlyIn(Dist.CLIENT)
    void loadForClient(CompoundNBT nbt) {}

    void saveForClient(CompoundNBT nbt) {
        nbt.putBoolean(IsServerDataTag, false);
    }*/

    // ----------------------------------------------------------------------- //
    // TileEntity


    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        if (isServer() || nbt.getBoolean(IsServerDataTag)) {
            loadForServer(nbt);
        }
        else {
            loadForClient(nbt);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        if (isServer()) {
            saveForServer(nbt);
            super.save(nbt);
        }
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        // Obfuscation workaround. If it works.
        net.minecraft.tileentity.TileEntity te = this;
        return new SUpdateTileEntityPacket(te.getBlockPos(), 0, te.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();

        // See comment on savingForClients variable.
        SaveHandler.savingForClients_$eq(true);
        try {
            try {
                saveForClient(nbt);
            } catch (Throwable e) {
                OpenComputers.log().warn("There was a problem writing a TileEntity description packet. Please report this if you see it!", e);
            }
        } finally {
            SaveHandler.savingForClients_$eq(false);
        }

        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
        try {
            loadForClient(packet.getTag());
        } catch (Throwable e) {
            OpenComputers.log().warn("There was a problem reading a TileEntity description packet. Please report this if you see it!", e);
        }
    }
}
