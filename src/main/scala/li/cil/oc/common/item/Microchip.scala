package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraftforge.common.extensions.IForgeItem

class Microchip(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tooltipName = Option(unlocalizedName)
}
