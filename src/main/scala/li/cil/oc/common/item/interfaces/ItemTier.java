package li.cil.oc.common.item.interfaces;

import li.cil.oc.Localization;
import li.cil.oc.util.Tooltip;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface ItemTier extends ISimpleItem {
  @OnlyIn(Dist.CLIENT)
  @Override
  default void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
    ISimpleItem.super.appendHoverText(stack, world, tooltip, flag);
    if (flag.isAdvanced()) {
      tooltip.add(new StringTextComponent(Localization.Tooltip.Tier(tierFromDriver(stack) + 1)).setStyle(Tooltip.DefaultStyle));
    }
  }
}
