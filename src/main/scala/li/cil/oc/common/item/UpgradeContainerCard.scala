package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

import java.util.Optional
import scala.jdk.CollectionConverters.SeqHasAsJava

class UpgradeContainerCard(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with interfaces.ItemTier {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tooltipName = Optional.of(unlocalizedName)

  override protected def tooltipData = Seq(tier + 1).map(_.asInstanceOf[AnyRef]).asJava
}
