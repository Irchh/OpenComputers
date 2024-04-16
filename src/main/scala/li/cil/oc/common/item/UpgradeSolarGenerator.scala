package li.cil.oc.common.item

import li.cil.oc.Settings
import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

import scala.jdk.CollectionConverters.SeqHasAsJava

class UpgradeSolarGenerator(props: Properties) extends SimpleItem(props) with IForgeItem with traits.ItemTier {
  override protected def tooltipData = Seq((Settings.get.solarGeneratorEfficiency * 100).toInt).map(_.asInstanceOf[AnyRef]).asJava
}
