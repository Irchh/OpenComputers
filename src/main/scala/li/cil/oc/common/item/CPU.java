package li.cil.oc.common.item;

import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.interfaces.CPULike;
import li.cil.oc.common.item.interfaces.ItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;
import scala.Option;

import java.util.List;
import java.util.Optional;

public class CPU extends SimpleItem implements IForgeItem, ItemTier, CPULike {
    private final int tier;

    public CPU(Properties props, int tier) {
        super(props);
        this.tier = tier;
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId() + tier;
    }

    @Override
    public int cpuTier() {
        return tier;
    }

    @Override
    public Optional<String> tooltipName() {
        return Optional.of(unlocalizedName);
    }

    @Override
    public List<Object> tooltipData() {
        return cpuTooltipData();
    }

    @Override
    public void tooltipExtended(ItemStack stack, List<ITextComponent> tooltip) {
        cpuTooltipExtended(stack, tooltip);
    }

    @Override
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        return cpuUse(stack, world, player);
    }
}
