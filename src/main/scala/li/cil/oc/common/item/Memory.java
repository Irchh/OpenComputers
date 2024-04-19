package li.cil.oc.common.item;

import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.item.interfaces.ItemTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.List;
import java.util.Optional;

public class Memory extends SimpleItem implements IForgeItem, ItemTier {
    public final int tier;

    public Memory(Properties props, int tier) {
        super(props);
        this.tier = tier;
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId() + tier;
    }

    @Override
    public Optional<String> tooltipName() {
        return Optional.of(unlocalizedName);
    }
}
