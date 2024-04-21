package li.cil.oc.common.tileentity;

import li.cil.oc.OpenComputers;
import li.cil.oc.Constants;
import li.cil.oc.api.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("opencomputers")
public final class TileEntityTypes {
    public static final TileEntityType<Adapter> ADAPTER = null;
    public static final TileEntityType<Assembler> ASSEMBLER = null;
    public static final TileEntityType<li.cil.oc.common.tileentity.javaport.Cable> CABLE = null;
    public static final TileEntityType<Capacitor> CAPACITOR = null;
    public static final TileEntityType<CarpetedCapacitor> CARPETED_CAPACITOR = null;
    public static final TileEntityType<Case> CASE = null;
    public static final TileEntityType<Charger> CHARGER = null;
    public static final TileEntityType<Disassembler> DISASSEMBLER = null;
    public static final TileEntityType<DiskDrive> DISK_DRIVE = null;
    public static final TileEntityType<Geolyzer> GEOLYZER = null;
    public static final TileEntityType<Hologram> HOLOGRAM = null;
    public static final TileEntityType<Keyboard> KEYBOARD = null;
    public static final TileEntityType<Microcontroller> MICROCONTROLLER = null;
    public static final TileEntityType<MotionSensor> MOTION_SENSOR = null;
    public static final TileEntityType<NetSplitter> NET_SPLITTER = null;
    public static final TileEntityType<PowerConverter> POWER_CONVERTER = null;
    public static final TileEntityType<PowerDistributor> POWER_DISTRIBUTOR = null;
    public static final TileEntityType<Print> PRINT = null;
    public static final TileEntityType<Printer> PRINTER = null;
    public static final TileEntityType<Rack> RACK = null;
    public static final TileEntityType<Raid> RAID = null;
    public static final TileEntityType<Redstone> REDSTONE_IO = null;
    public static final TileEntityType<Relay> RELAY = null;
    // We use the RobotProxy instead of Robot here because those are the ones actually found in the world.
    // Beware of TileEntityType.create for this as it will construct a new, empty robot.
    public static final TileEntityType<RobotProxy> ROBOT = null;
    public static final TileEntityType<Screen> SCREEN = null;
    public static final TileEntityType<Transposer> TRANSPOSER = null;
    public static final TileEntityType<Waypoint> WAYPOINT = null;

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> e) {
        register(e.getRegistry(), "adapter", TileEntityType.Builder.of(() -> new Adapter(ADAPTER),
            Items.get(Constants.BlockName.Adapter).block()));
        register(e.getRegistry(), "assembler", TileEntityType.Builder.of(() -> new Assembler(ASSEMBLER),
            Items.get(Constants.BlockName.Assembler).block()));
        register(e.getRegistry(), "cable", TileEntityType.Builder.of(() -> new li.cil.oc.common.tileentity.javaport.Cable(CABLE),
            Items.get(Constants.BlockName.Cable).block()));
        register(e.getRegistry(), "capacitor", TileEntityType.Builder.of(() -> new Capacitor(CAPACITOR),
            Items.get(Constants.BlockName.Capacitor).block()));
        register(e.getRegistry(), "carpeted_capacitor", TileEntityType.Builder.of(() -> new CarpetedCapacitor(CARPETED_CAPACITOR),
            Items.get(Constants.BlockName.CarpetedCapacitor).block()));
        register(e.getRegistry(), "case", TileEntityType.Builder.of(() -> new Case(CASE),
            Items.get(Constants.BlockName.CaseCreative).block(),
            Items.get(Constants.BlockName.CaseTier1).block(),
            Items.get(Constants.BlockName.CaseTier2).block(),
            Items.get(Constants.BlockName.CaseTier3).block()));
        register(e.getRegistry(), "charger", TileEntityType.Builder.of(() -> new Charger(CHARGER),
            Items.get(Constants.BlockName.Charger).block()));
        register(e.getRegistry(), "disassembler", TileEntityType.Builder.of(() -> new Disassembler(DISASSEMBLER),
            Items.get(Constants.BlockName.Disassembler).block()));
        register(e.getRegistry(), "disk_drive", TileEntityType.Builder.of(() -> new DiskDrive(DISK_DRIVE),
            Items.get(Constants.BlockName.DiskDrive).block()));
        register(e.getRegistry(), "geolyzer", TileEntityType.Builder.of(() -> new Geolyzer(GEOLYZER),
            Items.get(Constants.BlockName.Geolyzer).block()));
        register(e.getRegistry(), "hologram", TileEntityType.Builder.of(() -> new Hologram(HOLOGRAM),
            Items.get(Constants.BlockName.HologramTier1).block(),
            Items.get(Constants.BlockName.HologramTier2).block()));
        register(e.getRegistry(), "keyboard", TileEntityType.Builder.of(() -> new Keyboard(KEYBOARD),
            Items.get(Constants.BlockName.Keyboard).block()));
        register(e.getRegistry(), "microcontroller", TileEntityType.Builder.of(() -> new Microcontroller(MICROCONTROLLER),
            Items.get(Constants.BlockName.Microcontroller).block()));
        register(e.getRegistry(), "motion_sensor", TileEntityType.Builder.of(() -> new MotionSensor(MOTION_SENSOR),
            Items.get(Constants.BlockName.MotionSensor).block()));
        register(e.getRegistry(), "net_splitter", TileEntityType.Builder.of(() -> new NetSplitter(NET_SPLITTER),
            Items.get(Constants.BlockName.NetSplitter).block()));
        register(e.getRegistry(), "power_converter", TileEntityType.Builder.of(() -> new PowerConverter(POWER_CONVERTER),
            Items.get(Constants.BlockName.PowerConverter).block()));
        register(e.getRegistry(), "power_distributor", TileEntityType.Builder.of(() -> new PowerDistributor(POWER_DISTRIBUTOR),
            Items.get(Constants.BlockName.PowerDistributor).block()));
        register(e.getRegistry(), "print", TileEntityType.Builder.of(() -> new Print(PRINT),
            Items.get(Constants.BlockName.Print).block()));
        register(e.getRegistry(), "printer", TileEntityType.Builder.of(() -> new Printer(PRINTER),
            Items.get(Constants.BlockName.Printer).block()));
        register(e.getRegistry(), "rack", TileEntityType.Builder.of(() -> new Rack(RACK),
            Items.get(Constants.BlockName.Rack).block()));
        register(e.getRegistry(), "raid", TileEntityType.Builder.of(() -> new Raid(RAID),
            Items.get(Constants.BlockName.Raid).block()));
        register(e.getRegistry(), "redstone_io", TileEntityType.Builder.of(() -> new Redstone(REDSTONE_IO),
            Items.get(Constants.BlockName.Redstone).block()));
        register(e.getRegistry(), "relay", TileEntityType.Builder.of(() -> new Relay(RELAY),
            Items.get(Constants.BlockName.Relay).block()));
        register(e.getRegistry(), "robot", TileEntityType.Builder.of(() -> new RobotProxy(ROBOT),
            Items.get(Constants.BlockName.Robot).block()));
        register(e.getRegistry(), "screen", TileEntityType.Builder.of(() -> new Screen(SCREEN),
            Items.get(Constants.BlockName.ScreenTier1).block(),
            Items.get(Constants.BlockName.ScreenTier2).block(),
            Items.get(Constants.BlockName.ScreenTier3).block()));
        register(e.getRegistry(), "transposer", TileEntityType.Builder.of(() -> new Transposer(TRANSPOSER),
            Items.get(Constants.BlockName.Transposer).block()));
        register(e.getRegistry(), "waypoint", TileEntityType.Builder.of(() -> new Waypoint(WAYPOINT),
            Items.get(Constants.BlockName.Waypoint).block()));
    }

    private static void register(IForgeRegistry<TileEntityType<?>> registry, String name, TileEntityType.Builder<?> builder) {
        TileEntityType<?> type = builder.build(null);
        type.setRegistryName(new ResourceLocation(OpenComputers.ID, name));
        registry.register(type);
    }

    private TileEntityTypes() {
        throw new Error();
    }
}
