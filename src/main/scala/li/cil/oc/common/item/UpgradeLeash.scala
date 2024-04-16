package li.cil.oc.common.item

import li.cil.oc.common.item.abstracts.SimpleItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import net.minecraftforge.common.extensions.IForgeItem

class UpgradeLeash(props: Properties) extends SimpleItem(props) with IForgeItem with traits.ItemTier
