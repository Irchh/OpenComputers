package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

class Memory(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with interfaces.ItemTier {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tooltipName = Option(unlocalizedName)
}
