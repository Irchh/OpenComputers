package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Node;

public interface ImmibisMicroBlock extends ITileEntity {
    default boolean ImmibisMicroblocks_isSideOpen(int side) {
        return true;
    }

    default void ImmibisMicroblocks_onMicroblocksChanged() {
        Network.joinOrCreateNetwork(this.te());
    }
}
