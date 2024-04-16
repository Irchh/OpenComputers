package li.cil.oc.common.item.abstracts;

import com.mojang.blaze3d.matrix.MatrixStack;
import li.cil.oc.Settings;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.DriverItem;
import li.cil.oc.api.driver.item.UpgradeRenderer;
import li.cil.oc.api.event.RobotRenderEvent;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.client.renderer.item.UpgradeRenderer$;
import li.cil.oc.common.tileentity.DiskDrive;
import li.cil.oc.util.BlockPosition;
import li.cil.oc.util.Tooltip;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
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
import scala.Option;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class SimpleArmorItem extends ArmorItem implements UpgradeRenderer {
    public SimpleArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    public ItemStack createItemStack(int amount) {
        return new ItemStack(this, amount);
    }

    public ItemStack createItemStack() {
        return new ItemStack(this, 1);
    }

    @Deprecated
    protected String unlocalizedName = getClass().getSimpleName().toLowerCase();

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

    public int tierFromDriver(ItemStack stack) {
        DriverItem driver = Driver.driverFor(stack);
        if (driver instanceof DriverItem) {
            return driver.tier(stack);
        } else {
            return 0;
        }
    }

    protected Option<String> tooltipName() {
        return Option.apply(unlocalizedName);
    };

    protected List<Object> tooltipData() {
        return new ArrayList<>();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (tooltipName().isDefined()) {
            for (String curr: Tooltip.get(tooltipName().get(), tooltipData())) {
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
    protected void tooltipExtended(ItemStack stack, List<ITextComponent> tooltip) {}

    protected void tooltipCosts(ItemStack stack, List<ITextComponent> tooltip) {
        if (stack.hasTag() && stack.getTag().contains(Settings.namespace + "data")) {
            CompoundNBT data = stack.getTag().getCompound(Settings.namespace + "data");
            if (data.contains("node") && data.getCompound("node").contains("address")) {
                tooltip.add(new StringTextComponent("§8" + data.getCompound("node").getString("address").substring(0, 13) + "...§7"));
            }
        }
    }

    // ----------------------------------------------------------------------- //


    @Override
    public String computePreferredMountPoint(ItemStack stack, Robot robot, Set<String> availableMountPoints) {
        return UpgradeRenderer$.MODULE$.preferredMountPoint(stack, availableMountPoints);
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer buffer, ItemStack stack, RobotRenderEvent.MountPoint mountPoint, Robot robot, float pt) {
        UpgradeRenderer$.MODULE$.render(matrix, buffer, stack, mountPoint);
    }
}
