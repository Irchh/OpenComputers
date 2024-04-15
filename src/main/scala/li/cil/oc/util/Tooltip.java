package li.cil.oc.util;

import li.cil.oc.Localization;
import li.cil.oc.Settings;
import li.cil.oc.client.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tooltip {
    private static int maxWidth = 220;
    private static FontRenderer font = Minecraft.getInstance().font;

    public static Style DefaultStyle = Style.EMPTY.applyFormat(TextFormatting.GRAY);

    public static List<String> get(String name, Object ...args) {
        if (!Localization.canLocalize(Settings.namespace + "tooltip." + name)) return new ArrayList<>();
        String tooltip = String.format(Localization.localizeImmediately("tooltip." + name), args);
        if (font == null) return Arrays.asList(tooltip.split("\\r?\\n"));
        boolean isSubTooltip = name.contains(".");
        boolean shouldShorten = (isSubTooltip || font.width(tooltip) > maxWidth) && !KeyBindings.showExtendedTooltips();
        if (shouldShorten) {
            if (isSubTooltip) return new ArrayList<>();
            else return Collections.singletonList(Localization.localizeImmediately("tooltip.toolong", KeyBindings.getKeyBindingName(KeyBindings.extendedTooltip())));
        } else {
            return Arrays.stream(tooltip.split("\\r?\\n"))
                    .flatMap(line -> wrap(font, line, maxWidth).stream().map(wrappedLine -> wrappedLine.trim() + " "))
                    .collect(Collectors.toList());
        }
    }

    public static List<String> extended(String name, Object ...args) {
        if (KeyBindings.showExtendedTooltips()) {
            return Arrays.stream(String.format(Localization.localizeImmediately("tooltip." + name), args)
                    .split("\\r?\\n"))
                    .flatMap(line -> wrap(font, line, maxWidth).stream().map(wrappedLine -> wrappedLine.trim() + " "))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private static List<String> wrap(FontRenderer font, String line, int width) {
        List<String> list = new ArrayList<>();
        font.getSplitter().splitLines(line, width, net.minecraft.util.text.Style.EMPTY, true, new CharacterManager.ISliceAcceptor() {
            @Override
            public void accept(Style style, int start, int end) {
                list.add(line.substring(start, end));
            }
        });
        return list;
    }
}
