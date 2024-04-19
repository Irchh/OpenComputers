package li.cil.oc.common.item;

import li.cil.oc.common.item.abstracts.SimpleItem;
import net.minecraftforge.common.extensions.IForgeItem;
import scala.Option;

import java.util.Optional;

public class ButtonGroup extends SimpleItem implements IForgeItem {
    @Override
    public Optional<String> tooltipName() {
        return Optional.empty();
    }

    public ButtonGroup(Properties props) {
        super(props);
    }
}
