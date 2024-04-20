package li.cil.oc.common.item;

import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.interfaces.ItemTier;
import net.minecraftforge.common.extensions.IForgeItem;

public class DataCard extends SimpleItem implements IForgeItem, ItemTier {
  public final int tier;

  public DataCard(Properties props, int tier) {
    super(props);
    this.tier = tier;
  }

  @Override
  public String getDescriptionId() {
    return super.getDescriptionId() + tier;
  }
}