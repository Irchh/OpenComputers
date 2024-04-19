package li.cil.oc.common.item;

import li.cil.oc.Settings;
import li.cil.oc.common.item.abstracts.SimpleItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class Chamelium extends SimpleItem implements IForgeItem {
    public Chamelium(Properties props) {
        super(props);
    }

    @Override
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        if (Settings.get().chameliumEdible) {
            if (player.getItemInHand(Hand.MAIN_HAND) == stack)
                player.startUsingItem(Hand.MAIN_HAND);
            else
                player.startUsingItem(Hand.OFF_HAND);
        }
        return new ActionResult<>(ActionResultType.sidedSuccess(world.isClientSide), stack);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity player) {
        if (!world.isClientSide) {
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 100, 0));
            player.addEffect(new EffectInstance(Effects.BLINDNESS, 200, 0));
        }
        stack.shrink(1);
        if (stack.getCount() > 0)
            return stack;
        else
            return ItemStack.EMPTY;
    }
}
