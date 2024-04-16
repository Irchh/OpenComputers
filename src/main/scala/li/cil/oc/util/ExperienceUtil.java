package li.cil.oc.util;

import li.cil.oc.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExperienceUtil {
  public final static String XpTag = Settings.namespace + "xp";

  public static double getExperience(CompoundNBT nbt) {
    return Math.max(nbt.getDouble(XpTag), 0);
  }

  public static double getExperience(ItemStack stack) {
    if (!stack.hasTag()) return 0;
    else return getExperience(stack.getTag());
  }

  public static void setExperience(CompoundNBT nbt, double experience) {
    nbt.putDouble(XpTag, experience);
  }

  public static double xpForLevel(int level) {
    if (level == 0) return 0;
    else return Settings.get().baseXpToLevel + Math.pow(level * Settings.get().constantXpGrowth, Settings.get().exponentialXpGrowth);
  }

  public static double calculateExperienceLevel(int level, double experience) {
    double xpNeeded = xpForLevel(level + 1) - xpForLevel(level);
    double xpProgress = Math.max(0, experience - xpForLevel(level));
    return level + xpProgress / xpNeeded;
  }

  public static int calculateLevelFromExperience(double experience) {
    return Math.min((int)(Math.pow(experience - Settings.get().baseXpToLevel, 1 / Settings.get().exponentialXpGrowth) / Settings.get().constantXpGrowth), 30);
  }
}
