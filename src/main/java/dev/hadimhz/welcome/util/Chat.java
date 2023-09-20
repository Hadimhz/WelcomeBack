package dev.hadimhz.welcome.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public static Component translate(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static String join(List<String> list) {
        return String.join("\n", list);
    }


    public static List<Component> translate(List<String> list) {

        final List<Component> toReturn = new ArrayList<>();

        for (String text : list) {
            toReturn.add(translate(text));
        }

        return toReturn;

    }

}
