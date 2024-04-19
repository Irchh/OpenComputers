package li.cil.oc.common.item;

import li.cil.oc.Settings;
import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.interfaces.ItemTier;
import net.minecraftforge.common.extensions.IForgeItem;
import scala.Option;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ComponentBus extends SimpleItem implements IForgeItem, ItemTier {
    public final int tier;

    public ComponentBus(Properties props, int tier) {
        super(props);
        this.tier = tier;
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId() + tier;
    }

    @Override
    public Optional<String> tooltipName() {
        return Optional.of(unlocalizedName);
    }

    @Override
    public List<Object> tooltipData() {
        return Collections.singletonList(Settings.get().cpuComponentSupport[tier]);
    }
}
