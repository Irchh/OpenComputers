package li.cil.oc.common.item;

import li.cil.oc.api.Nanomachines;
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

public class Acid extends SimpleItem implements IForgeItem {
    public Acid(Properties props) {
        super(props);
    }

    @Override
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        if (player.getItemInHand(Hand.MAIN_HAND) == stack)
            player.startUsingItem(Hand.MAIN_HAND);
        else
            player.startUsingItem(Hand.OFF_HAND);
        return new ActionResult<>(ActionResultType.sidedSuccess(world.isClientSide), stack);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            if (!world.isClientSide) {
                PlayerEntity player = (PlayerEntity) entity;
                player.addEffect(new EffectInstance(Effects.BLINDNESS, 200));
                player.addEffect(new EffectInstance(Effects.POISON, 100));
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 600));
                player.addEffect(new EffectInstance(Effects.CONFUSION, 1200));
                player.addEffect(new EffectInstance(Effects.SATURATION, 2000));

                // Remove nanomachines if installed.
                Nanomachines.uninstallController(player);
            }
            stack.shrink(1);
            if (stack.getCount() > 0)
                return stack;
            else
                return ItemStack.EMPTY;
        } else {
            return stack;
        }
    }
}
