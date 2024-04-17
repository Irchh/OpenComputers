package li.cil.oc.common.item;

import li.cil.oc.common.Tier;
import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.traits.CPULike;
import li.cil.oc.common.item.traits.GPULike;
import li.cil.oc.common.item.traits.ItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

public class APU extends SimpleItem implements IForgeItem, ItemTier, CPULike, GPULike {
    private final int tier;

    public APU(Properties props, int tier) {
        super(props);
        this.tier = tier;
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId() + tier;
    }

    @Override
    public int cpuTier() {
        return Math.min(Tier.Three, tier + 1);
    }

    @Override
    public int gpuTier() {
        return tier;
    }

    @Override
    public Option<String> tooltipName() {
        return Option.apply(unlocalizedName);
    }

    @Override
    public List<Object> tooltipData() {
        ArrayList<Object> combinedData = new ArrayList<>();
        combinedData.addAll(cpuTooltipData());
        combinedData.addAll(gpuTooltipData());
        return combinedData;
    }

    @Override
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        return cpuUse(stack, world, player);
    }
}
