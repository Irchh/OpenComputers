package li.cil.oc.common.item

import li.cil.oc.Settings
import li.cil.oc.common.item.abstracts.SimpleItem
import li.cil.oc.common.item.data.NodeData
import li.cil.oc.common.item.interfaces.Chargeable
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraftforge.common.extensions.IForgeItem

import scala.jdk.CollectionConverters.SeqHasAsJava

class UpgradeBattery(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with interfaces.ItemTier with Chargeable {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override protected def tooltipName = Option(unlocalizedName)

  override protected def tooltipData = Seq(Settings.get.bufferCapacitorUpgrades(tier).toInt).map(_.asInstanceOf[AnyRef]).asJava

  override def showDurabilityBar(stack: ItemStack) = true

  override def getDurabilityForDisplay(stack: ItemStack): Double = {
    val data = new NodeData(stack)
    1 - data.buffer.getOrElse(0.0) / Settings.get.bufferCapacitorUpgrades(tier)
  }

  // ----------------------------------------------------------------------- //

  override def canCharge(stack: ItemStack): Boolean = true

  override def charge(stack: ItemStack, amount: Double, simulate: Boolean): Double = {
    val data = new NodeData(stack)
    val buffer = data.buffer match {
      case Some(value) => value
      case _ => 0.0
    }
    interfaces.Chargeable.applyCharge(amount, buffer, Settings.get.bufferCapacitorUpgrades(tier), used => if (!simulate) {
      data.buffer = Option(buffer + used)
      data.saveData(stack)
    })
  }

  override def maxCharge(stack: ItemStack): Double = Settings.get.bufferCapacitorUpgrades(tier)

  override def getCharge(stack: ItemStack): Double = new NodeData(stack).buffer.getOrElse(0.0)

  override def setCharge(stack: ItemStack, amount: Double): Unit = {
    val data = new NodeData(stack)
    data.buffer = Option((0.0 max amount) min maxCharge(stack))
    data.saveData(stack)
  }

  override def canExtract(stack: ItemStack): Boolean = true
}
