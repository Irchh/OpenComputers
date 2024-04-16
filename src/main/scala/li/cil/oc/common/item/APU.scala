package li.cil.oc.common.item

import li.cil.oc.common.Tier
import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraftforge.common.extensions.IForgeItem

import scala.jdk.CollectionConverters.{ListHasAsScala, SeqHasAsJava}
import scala.language.existentials

class APU(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with traits.ItemTier with traits.CPULike with traits.GPULike {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override def cpuTier = math.min(Tier.Three, tier + 1)

  override def gpuTier = tier

  override protected def tooltipName = Option(unlocalizedName)

  override protected def tooltipData = {
    (super[CPULike].tooltipData.asScala ++ super[GPULike].tooltipData.asScala).asJava
  }
}
