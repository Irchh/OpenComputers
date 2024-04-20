package li.cil.oc.common.item;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface CustomModel {
  @OnlyIn(Dist.CLIENT)
  ModelResourceLocation getModelLocation(ItemStack stack);

  @OnlyIn(Dist.CLIENT)
  default void registerModelLocations() {}

  @OnlyIn(Dist.CLIENT)
  default void bakeModels(ModelBakeEvent bakeEvent) {}
}
