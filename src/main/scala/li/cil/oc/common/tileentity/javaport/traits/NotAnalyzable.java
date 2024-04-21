package li.cil.oc.common.tileentity.javaport.traits;

import li.cil.oc.api.network.Analyzable;
import li.cil.oc.api.network.Node;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;

public interface NotAnalyzable extends Analyzable {
    @Override
    default Node[] onAnalyze(PlayerEntity player, Direction side, float hitX, float hitY, float hitZ) {
        return null;
    }
}
