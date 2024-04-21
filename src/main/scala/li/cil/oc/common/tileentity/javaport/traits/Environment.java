package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.common.tileentity.traits.Tickable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.extensions.IForgeTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Environment extends li.cil.oc.api.network.Environment, EnvironmentHost, IModelData, IForgeTileEntity, ITileEntity {

    void setIsChangeScheduled(boolean value);
    boolean getIsChangeScheduled();

    @Override
    default World world() {
        return te().getLevel();
    }

    @Override
    default double xPosition() {
        return te().x();
    }

    @Override
    default double yPosition() {
        return te().y();
    }

    @Override
    default double zPosition() {
        return te().z();
    }

    @Override
    default void markChanged() {
        if (this instanceof Tickable)
            setIsChangeScheduled(true);
        else
            te().getLevel().blockEntityChanged(te().getBlockPos(), this.te());
    }

    default boolean isConnected() {
        return node() != null && node().address() != null && node().network() != null;
    }

    // ----------------------------------------------------------------------- //
    // network.Node

    @Override
    default void onMessage(Message message) {}

    @Override
    default void onConnect(Node node) {}

    @Override
    default void onDisconnect(Node node) {
        if (node == this.node()) {
            if (node instanceof Connector) {
                Connector connector = (Connector) node;
                // Set it to zero to push all energy into other nodes, to
                // avoid energy loss when removing nodes. Set it back to the
                // original value though, as there are cases where the node
                // is re-used afterwards, without re-adjusting its buffer size.
                int bufferSize = (int) connector.localBufferSize();
                connector.setLocalBufferSize(0);
                connector.setLocalBufferSize(bufferSize);
            }
        }
    }

    // ----------------------------------------------------------------------- //


    @Nonnull
    @Override
    default IModelData getModelData() {
        return this;
    }

    @Override
    default boolean hasProperty(ModelProperty<?> prop) {
        return false;
    }

    @Nullable
    @Override
    default <T> T getData(ModelProperty<T> prop) {
        return null;
    }

    @Nullable
    @Override
    default <T> T setData(ModelProperty<T> prop, T data) {
        return null;
    }
}
