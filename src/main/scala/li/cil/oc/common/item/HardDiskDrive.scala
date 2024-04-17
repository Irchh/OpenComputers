package li.cil.oc.common.item

import java.util
import li.cil.oc.Localization
import li.cil.oc.Settings
import li.cil.oc.common.item.data.DriveData
import li.cil.oc.util.Tooltip
import net.minecraft.client.util.ITooltipFlag
import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}
import net.minecraftforge.common.extensions.IForgeItem

class HardDiskDrive(props: Properties, val tier: Int) extends SimpleItem(props) with IForgeItem with traits.ItemTier with traits.FileSystemLike {
  override protected def tooltipName = None
  @Deprecated
  override def getDescriptionId = super.getDescriptionId + tier

  override def kiloBytes: Int = Settings.get.hddSizes(tier)
  val platterCount: Int = Settings.get.hddPlatterCounts(tier)

  override def getName(stack: ItemStack): ITextComponent = {
    val localizedName = super.getName(stack).copy()
    if (kiloBytes >= 1024) {
      localizedName.append(s" (${kiloBytes / 1024}MB)")
    }
    else {
      localizedName.append(s" (${kiloBytes}KB)")
    }
    localizedName
  }

  @OnlyIn(Dist.CLIENT)
  override def appendHoverText(stack: ItemStack, world: World, tooltip: util.List[ITextComponent], flag: ITooltipFlag) = {
    super.appendHoverText(stack, world, tooltip, flag)
    this.fsAppendHoverText(stack, tooltip, flag)
  }

  override def use(stack: ItemStack, world: World, player: PlayerEntity): ActionResult[ItemStack] = fsUse(stack, world, player)
}