package li.cil.oc.common.item.abstracts;

import li.cil.oc.common.item.interfaces.FileSystemLike;
import li.cil.oc.common.item.interfaces.ISimpleItem;
import li.cil.oc.common.item.interfaces.ItemTier;
import li.cil.oc.common.tileentity.DiskDrive;
import li.cil.oc.util.BlockPosition;
import li.cil.oc.util.Tooltip;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SimpleItem extends Item implements ISimpleItem {
    public SimpleItem(Properties props) {
        super(props);
    }

    @Deprecated
    protected String unlocalizedName = getClass().getSimpleName().toLowerCase();

    @Deprecated
    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)  {
        ISimpleItem.super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    @Deprecated
    public String getDescriptionId() {
        return "item.oc." + unlocalizedName;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player) {
        TileEntity entity = world.getBlockEntity(pos);
        if (entity instanceof DiskDrive) {
            return true;
        }
        return super.doesSneakBypassUse(stack, world, pos, player);
    }

    @Override
    @Deprecated
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Vector3d hitPos = ctx.getClickLocation();
        return onItemUseFirst(stack, ctx.getPlayer(), ctx.getPlayer().level, pos, ctx.getClickedFace(),
                (float)(hitPos.x - pos.getX()), (float)(hitPos.y - pos.getY()), (float)(hitPos.z - pos.getZ()), ctx.getHand());
    }

    @Deprecated
    public ActionResultType onItemUseFirst(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, Hand hand) {
        return ActionResultType.PASS;
    }

    @Deprecated
    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        // Why is this check here
        if (stack instanceof ItemStack) {
            World world = ctx.getLevel();
            BlockPosition pos = BlockPosition.apply(ctx.getClickedPos(), world);
            Vector3d hitPos = ctx.getClickLocation();
            boolean success = onItemUse(stack, ctx.getPlayer(), pos, ctx.getClickedFace(),
                    (float)(hitPos.x - pos.x()), (float)(hitPos.y - pos.y()), (float)(hitPos.z - pos.z()));
            if (success) return ActionResultType.sidedSuccess(world.isClientSide);
            else return ActionResultType.PASS;
        } else {
            return super.useOn(ctx);
        }
    }

    @Deprecated
    public boolean onItemUse(ItemStack stack, PlayerEntity player, BlockPosition position, Direction side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        // Why is this check here
        if (stack instanceof ItemStack) {
            return use(stack, world, player);
        } else {
            return super.use(world, player, hand);
        }
    }

    @Deprecated
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        return new ActionResult<>(ActionResultType.PASS, stack);
    }
}
