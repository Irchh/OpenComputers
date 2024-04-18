package li.cil.oc.common.item;

import li.cil.oc.Constants;
import li.cil.oc.Localization;
import li.cil.oc.Settings;
import li.cil.oc.api.detail.ItemInfo;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.network.*;
import li.cil.oc.common.init.Items;
import li.cil.oc.common.item.abstracts.SimpleItem;
import li.cil.oc.common.tileentity.Screen;
import li.cil.oc.server.PacketSender$;
import li.cil.oc.util.BlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Analyzer extends SimpleItem implements IForgeItem {
    public Analyzer(Properties props) {
        super(props);
    }

    @Override
    public ActionResult<ItemStack> use(ItemStack stack, World world, PlayerEntity player) {
        if (player.isCrouching() && stack.hasTag()) {
            stack.removeTagKey(Settings.namespace + "clipboard");
        }
        return super.use(stack, world, player);
    }

    @Override
    public boolean onItemUse(ItemStack stack, PlayerEntity player, BlockPosition position, Direction side, float hitX, float hitY, float hitZ) {
        World world = player.level;
        TileEntity entity = world.getBlockEntity(position.toBlockPos());
        if (entity instanceof Screen) {
            Screen screen = (Screen)entity;
            if (side == screen.facing()) {
                if (player.isCrouching()) {
                    screen.copyToAnalyzer(hitX, hitY, hitZ);
                }
                else if (stack.hasTag() && stack.getTag().contains(Settings.namespace + "clipboard")) {
                    if (!world.isClientSide) {
                        screen.origin().buffer().clipboard(stack.getTag().getString(Settings.namespace + "clipboard"), player);
                    }
                    return true;
                }
                else
                    return false;
            }
        }
        return Analyzer.analyze(position.world().get().getBlockEntity(position.toBlockPos()), player, side, hitX, hitY, hitZ);
    }

    // Scala object
    private static ItemInfo _analyzer = null;
    private static ItemInfo analyzer() {
        if (_analyzer == null) {
            _analyzer = Items.get(Constants.ItemName.Analyzer);
        }
        return _analyzer;
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract e) {
        PlayerEntity player = e.getPlayer();
        ItemStack held = player.getItemInHand(e.getHand());
        if (Items.get(held) == analyzer()) {
            if (analyze(e.getTarget(), player, Direction.DOWN, 0, 0, 0)) {
                player.swing(e.getHand());
                e.setCanceled(true);
            }
        }
    }

    public static boolean analyze(Object thing, PlayerEntity player, Direction side, float hitX, float hitY, float hitZ) {
        World world = player.level;
        if (thing instanceof Analyzable) {
            Analyzable analyzable = (Analyzable)thing;
            if (!world.isClientSide) {
                analyzeNodes(analyzable.onAnalyze(player, side, hitX, hitY, hitZ), player);
            }
            return true;
        } else if (thing instanceof SidedEnvironment) {
            SidedEnvironment host = (SidedEnvironment)thing;
            if (!world.isClientSide) {
                analyzeNodes(Collections.singletonList(host.sidedNode(side)).toArray(new Node[0]), player);
            }
            return true;
        } else if (thing instanceof Environment) {
            Environment host = (Environment)thing;
            if (!world.isClientSide) {
                analyzeNodes(Collections.singletonList(host.node()).toArray(new Node[0]), player);
            }
            return true;
        }
        return false;
    }

    private static void analyzeNodes(Node[] nodes, PlayerEntity player) {
        if (nodes != null) for (Node node: nodes) {
            if (node == null) {
                continue;
            }
            if (player instanceof FakePlayer) {
                // Nope
            } else if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                Environment host = node.host();
                if (host instanceof Machine) {
                    Machine machine = (Machine) host;
                    if (machine.lastError() != null) {
                        playerMP.sendMessage(Localization.Analyzer.LastError(machine.lastError()), Util.NIL_UUID);
                    }
                    playerMP.sendMessage(Localization.Analyzer.Components(machine.componentCount(), machine.maxComponents()), Util.NIL_UUID);
                    String[] list = machine.users();
                    if (list.length > 0) {
                        playerMP.sendMessage(Localization.Analyzer.Users(List.of(list)), Util.NIL_UUID);
                    }
                }
                if (node instanceof Connector) {
                    Connector connector = (Connector) node;
                    if (connector.localBufferSize() > 0) {
                        playerMP.sendMessage(Localization.Analyzer.StoredEnergy(String.format("%.2f/%.2f", connector.localBuffer(), connector.localBufferSize())), Util.NIL_UUID);
                    }
                    playerMP.sendMessage(Localization.Analyzer.TotalEnergy(String.format("%.2f/%.2f", connector.globalBuffer(), connector.globalBufferSize())), Util.NIL_UUID);
                }
                if (node instanceof Component) {
                    Component component = (Component) node;
                    playerMP.sendMessage(Localization.Analyzer.ComponentName(component.name()), Util.NIL_UUID);
                }
                String address = node.address();
                if (address != null && !address.isEmpty()) {
                    playerMP.sendMessage(Localization.Analyzer.Address(address), Util.NIL_UUID);
                    PacketSender$.MODULE$.sendAnalyze(address, playerMP);
                }
            }
        }
    }
}
