package li.cil.oc.common.item;

import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.interfaces.GPULike;
import li.cil.oc.common.item.interfaces.ItemTier;
import net.minecraftforge.common.extensions.IForgeItem;
import scala.Option;

import java.util.List;

public class GraphicsCard extends SimpleItem implements IForgeItem, ItemTier, GPULike {
    private final int tier;
    public GraphicsCard(Properties props, int tier) {
        super(props);
        this.tier = tier;
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId() + tier;
    }

    @Override
    public int gpuTier() {
        return tier;
    }

    @Override
    public Option<String> tooltipName() {
        return Option.apply(unlocalizedName);
    }

    @Override
    public List<Object> tooltipData() {
        return gpuTooltipData();
    }
}