package li.cil.oc.common.item.interfaces;

import li.cil.oc.Settings;
import li.cil.oc.api.Driver;
import li.cil.oc.api.Machine;
import li.cil.oc.api.driver.DriverItem;
import li.cil.oc.api.driver.item.MutableProcessor;
import li.cil.oc.api.machine.Architecture;
import li.cil.oc.integration.opencomputers.DriverCPU$;
import li.cil.oc.util.Tooltip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CPULike {
    int cpuTier();

    default List<Object> cpuTooltipData() {
        return Collections.singletonList(Settings.get().cpuComponentSupport[cpuTier()]);
    }

    default void cpuTooltipExtended(ItemStack stack, List<ITextComponent> tooltip) {
        for (String curr: Tooltip.get("cpu.Architecture", Machine.getArchitectureName(DriverCPU$.MODULE$.architecture(stack)))) {
            tooltip.add(new StringTextComponent(curr).setStyle(Tooltip.DefaultStyle));
        }
    }

    default ActionResult<ItemStack> cpuUse(ItemStack stack, World world, PlayerEntity player) {
        if (player.isCrouching()) {
            if (!world.isClientSide) {
                DriverItem driver = Driver.driverFor(stack);
                if (driver instanceof MutableProcessor) {
                    MutableProcessor processor = (MutableProcessor) driver;
                    List<Class<? extends Architecture>> architectures = new ArrayList<>(processor.allArchitectures());
                    if (!architectures.isEmpty()) {
                        int currentIndex = architectures.indexOf(processor.architecture(stack));
                        int newIndex = (currentIndex + 1) % architectures.toArray().length;
                        Class<? extends Architecture> archClass = architectures.get(newIndex);
                        String archName = Machine.getArchitectureName(archClass);
                        processor.setArchitecture(stack, archClass);
                        player.sendMessage(new TranslationTextComponent(Settings.namespace + "tooltip.cpu.Architecture", archName), Util.NIL_UUID);
                    }
                    player.swing(Hand.MAIN_HAND);
                }
            }
        }
        return new ActionResult<>(ActionResultType.sidedSuccess(world.isClientSide), stack);
    }
}
