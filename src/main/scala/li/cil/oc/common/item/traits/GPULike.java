package li.cil.oc.common.item.traits;

import li.cil.oc.Settings;
import li.cil.oc.util.PackedColor;

import java.util.Arrays;
import java.util.List;

public interface GPULike {
    int gpuTier();

    default List<Object> gpuTooltipData() {
        int w = Settings.screenResolutionsByTier.get(gpuTier()).getKey();
        int h = Settings.screenResolutionsByTier.get(gpuTier()).getValue();
        int depth = PackedColor.Depth$.MODULE$.bits(Settings.screenDepthsByTier[gpuTier()]);

        switch (Math.max(0, Math.min(gpuTier(), 2))) {
            case 0: return Arrays.asList(w, h, depth, "1/1/4/2/2");
            case 1: return Arrays.asList(w, h, depth, "2/4/8/4/4");
            case 2: return Arrays.asList(w, h, depth, "4/8/16/8/8");
        }
        throw new RuntimeException("Unreachable code");
    }
}
