package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

import java.util
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

import scala.language.existentials

class CPU(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with interfaces.ItemTier with interfaces.CPULike {
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override def cpuTier = tier

  override protected def tooltipName = Option(unlocalizedName)

  override protected def tooltipData = cpuTooltipData

  override protected def tooltipExtended(stack: ItemStack, tooltip: util.List[ITextComponent]) = cpuTooltipExtended(stack, tooltip)

  override def use(stack: ItemStack, world: World, player: PlayerEntity): ActionResult[ItemStack] = cpuUse(stack, world, player)
}
