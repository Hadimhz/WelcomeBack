package dev.hadimhz.welcome.util;

import org.bukkit.Bukkit;

import java.util.function.Function;
public final class Chat {

    private final static Function<String, String> FUNCTION;

    static {
        if (Bukkit.getBukkitVersion().contains("1.16")) {
            FUNCTION = s -> net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s);
            //Else make it compatible with the bukkit class
        } else {
            FUNCTION = s -> org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
        }
    }

    private Chat() {
        throw new ExceptionInInitializerError("This class may not be initialized"); //Let no one invoke this class
    }

    //Method to invoke when we want to make a string colorized.
    public static String translate(String line) {
        return FUNCTION.apply(line);
    }

}
