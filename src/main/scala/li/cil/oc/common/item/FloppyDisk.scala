package li.cil.oc.common.item

import java.util
import li.cil.oc.{Constants, Localization, Settings}
import li.cil.oc.common.item.abstracts.SimpleItem
import li.cil.oc.common.item.data.DriveData
import li.cil.oc.util.Tooltip
import net.minecraft.client.renderer.model.ModelBakery
import net.minecraft.client.renderer.model.ModelResourceLocation
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.DyeColor
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraft.item.ItemStack
import net.minecraft.util.{ActionResult, ActionResultType, Hand, ResourceLocation}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.{ITextComponent, StringTextComponent}
import net.minecraft.world.{IWorldReader, World}
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.extensions.IForgeItem

import java.util.Optional

class FloppyDisk(props: Properties) extends SimpleItem(props) with IForgeItem with CustomModel with interfaces.FileSystemLike {

  override protected def tooltipName = Optional.empty()
  // Necessary for anonymous subclasses used for loot disks.
  unlocalizedName = "floppydisk"

  override def kiloBytes: Int = Settings.get.floppySize

  @OnlyIn(Dist.CLIENT)
  private def modelLocationFromDyeName(dye: DyeColor) = {
    new ModelResourceLocation(Settings.resourceDomain + ":" + Constants.ItemName.Floppy + "_" + dye.getName, "inventory")
  }

  @OnlyIn(Dist.CLIENT)
  override def getModelLocation(stack: ItemStack): ModelResourceLocation = {
    val dyeIndex =
      if (stack.hasTag && stack.getTag.contains(Settings.namespace + "color"))
        stack.getTag.getInt(Settings.namespace + "color")
      else
        DyeColor.GRAY.getId
    modelLocationFromDyeName(DyeColor.byId(dyeIndex max 0 min 15))
  }

  @OnlyIn(Dist.CLIENT)
  override def registerModelLocations(): Unit = {
    for (dye <- DyeColor.values) {
      val location = modelLocationFromDyeName(dye)
      ModelLoader.addSpecialModel(location)
    }
  }

  override def doesSneakBypassUse(stack: ItemStack, world: IWorldReader, pos: BlockPos, player: PlayerEntity): Boolean = true

  override def use(stack: ItemStack, world: World, player: PlayerEntity): ActionResult[ItemStack] = fsUse(stack, world, player)
}
