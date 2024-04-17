package li.cil.oc.common.item.traits;

import li.cil.oc.Localization;
import li.cil.oc.Settings;
import li.cil.oc.client.gui.Drive;
import li.cil.oc.common.item.data.DriveData;
import li.cil.oc.util.Tooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface FileSystemLike extends ISimpleItem {
    int kiloBytes();

    @OnlyIn(Dist.CLIENT)
    default void showGui(ItemStack stack, PlayerEntity player) {
        Minecraft.getInstance().pushGuiLayer(new Drive(player.inventory, () -> stack));
    }

    @OnlyIn(Dist.CLIENT)
    default void fsAppendHoverText(ItemStack stack, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(Settings.namespace + "data")) {
                CompoundNBT data = nbt.getCompound(Settings.namespace + "data");
                if (data.contains(Settings.namespace + "fs.label")) {
                    tooltip.add(new StringTextComponent(data.getString(Settings.namespace + "fs.label")).setStyle(Tooltip.DefaultStyle));
                }
                if (flag.isAdvanced() && data.contains("fs")) {
                    CompoundNBT fsNbt = data.getCompound("fs");
                    if (fsNbt.contains("capacity.used")) {
                        long used = fsNbt.getLong("capacity.used");
                        tooltip.add(new StringTextComponent(Localization.Tooltip.DiskUsage(used, kiloBytes() * 1024L)).setStyle(Tooltip.DefaultStyle));
                    }
                }
            }
            DriveData data = new DriveData(stack);
            tooltip.add(new StringTextComponent(Localization.Tooltip.DiskMode(data.isUnmanaged())).setStyle(Tooltip.DefaultStyle));
            tooltip.add(new StringTextComponent(Localization.Tooltip.DiskLock(data.lockInfo())).setStyle(Tooltip.DefaultStyle));
        }
    }

    default ActionResult<ItemStack> fsUse(ItemStack stack, World world, PlayerEntity player) {
        if (!player.isCrouching() && (!stack.hasTag() || !stack.getTag().contains(Settings.namespace + "lootFactory"))) {
            if (world.isClientSide) showGui(stack, player);
            player.swing(Hand.MAIN_HAND);
        }
        return new ActionResult(ActionResultType.sidedSuccess(world.isClientSide), stack);
    }
}
