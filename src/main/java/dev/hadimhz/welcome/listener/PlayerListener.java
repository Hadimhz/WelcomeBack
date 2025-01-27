package dev.hadimhz.welcome.listener;

import dev.hadimhz.welcome.config.Config;
import dev.hadimhz.welcome.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PlayerListener implements Listener {

    private final Map<UUID, Long> userJoinTimer = new HashMap<>();
    private final Set<UUID> welcomed;
    private final Config config;
    private long joinedAt;
    private Player player;


    public PlayerListener(Plugin plugin, Config config) {

        this.config = config;

        this.welcomed = new HashSet<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        final UUID uuid = event.getPlayer().getUniqueId();
        final long elapsed = (System.currentTimeMillis() - userJoinTimer.getOrDefault(uuid, 0L)) / 1000;

        System.out.println(elapsed + " / " + config.onlyWelcomeAfterXDelay);
        System.out.println((config.onlyWelcomeAfterXDelay != -1 && (elapsed < config.onlyWelcomeAfterXDelay)) + " : " + (config.onlyWelcomeAfterXDelay != -1) + " " + (elapsed < config.onlyWelcomeAfterXDelay));
        System.out.println("vanished?" + isVanished(event.getPlayer()));

        if (isVanished(event.getPlayer()) || (config.onlyWelcomeAfterXDelay != -1 && (elapsed < config.onlyWelcomeAfterXDelay)))
            return;

        System.out.println(1);

        player = event.getPlayer();

        welcomed.clear();
        joinedAt = System.currentTimeMillis();


        if (config.onPlayerJoin.isEmpty()) return;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            if (onlinePlayer.getUniqueId().equals(uuid)) continue;

            onlinePlayer.sendMessage(Chat.translate(config.onPlayerJoin.replaceAll("%player%", player.getName())));
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        userJoinTimer.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }


    public Set<UUID> getWelcomed() {
        return welcomed;
    }

    public long getJoinedAt() {
        return joinedAt;
    }

    public Player getPlayer() {
        return player;
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

}
