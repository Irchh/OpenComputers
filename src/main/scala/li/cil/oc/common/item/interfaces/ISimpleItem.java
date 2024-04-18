package li.cil.oc.common.item.interfaces;

import com.mojang.blaze3d.matrix.MatrixStack;
import li.cil.oc.Settings;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.DriverItem;
import li.cil.oc.api.driver.item.UpgradeRenderer;
import li.cil.oc.api.event.RobotRenderEvent;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.client.renderer.item.UpgradeRenderer$;
import li.cil.oc.util.Tooltip;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import scala.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ISimpleItem extends IItemProvider, UpgradeRenderer {
    default ItemStack createItemStack(int amount) {
        return new ItemStack(this, amount);
    }

    default ItemStack createItemStack() {
        return new ItemStack(this, 1);
    }

    default int tierFromDriver(ItemStack stack) {
        DriverItem driver = Driver.driverFor(stack);
        if (driver instanceof DriverItem) {
            return driver.tier(stack);
        } else {
            return 0;
        }
    }

    String getUnlocalizedName();

    default Option<String> tooltipName() {
        return Option.apply(getUnlocalizedName());
    };

    default List<Object> tooltipData() {
        return new ArrayList<>();
    }

    @OnlyIn(Dist.CLIENT)
    default void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (tooltipName().isDefined()) {
            for (String curr: Tooltip.get(tooltipName().get(), tooltipData().toArray(new Object[0]))) {
                tooltip.add(new StringTextComponent(curr).setStyle(Tooltip.DefaultStyle));
            }
            tooltipExtended(stack, tooltip);
        }
        else {
            for (String curr: Tooltip.get(getClass().getSimpleName().toLowerCase())) {
                tooltip.add(new StringTextComponent(curr).setStyle(Tooltip.DefaultStyle));
            }
        }
        tooltipCosts(stack, tooltip);
    }

    // For stuff that goes to the normal 'extended' tooltip, before the costs.
    default void tooltipExtended(ItemStack stack, List<ITextComponent> tooltip) {}

    default void tooltipCosts(ItemStack stack, List<ITextComponent> tooltip) {
        if (stack.hasTag() && stack.getTag().contains(Settings.namespace + "data")) {
            CompoundNBT data = stack.getTag().getCompound(Settings.namespace + "data");
            if (data.contains("node") && data.getCompound("node").contains("address")) {
                tooltip.add(new StringTextComponent("§8" + data.getCompound("node").getString("address").substring(0, 13) + "...§7"));
            }
        }
    }

    // ----------------------------------------------------------------------- //

    @Override
    default String computePreferredMountPoint(ItemStack stack, Robot robot, Set<String> availableMountPoints) {
        return UpgradeRenderer$.MODULE$.preferredMountPoint(stack, availableMountPoints);
    }

    @Override
    default void render(MatrixStack matrix, IRenderTypeBuffer buffer, ItemStack stack, RobotRenderEvent.MountPoint mountPoint, Robot robot, float pt) {
        UpgradeRenderer$.MODULE$.render(matrix, buffer, stack, mountPoint);
    }
}
