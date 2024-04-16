package li.cil.oc.common.item

import li.cil.oc.Settings
import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

import scala.jdk.CollectionConverters.SeqHasAsJava

class UpgradeHover(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with traits.ItemTier {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tooltipName = Option(unlocalizedName)

  override protected def tooltipData = Seq(Settings.get.upgradeFlightHeight(tier)).map(_.asInstanceOf[AnyRef]).asJava
}
