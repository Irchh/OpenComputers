package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraftforge.common.extensions.IForgeItem

class TabletCase(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with traits.ItemTier {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tierFromDriver(stack: ItemStack) = tier

  override protected def tooltipName = Option(unlocalizedName)
}