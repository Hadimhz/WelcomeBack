package dev.hadimhz.welcome.listener;

import dev.hadimhz.welcome.config.Config;
import dev.hadimhz.welcome.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerListener implements Listener {

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

        if (isVanished(event.getPlayer())) return;

        player = event.getPlayer();

        welcomed.clear();
        joinedAt = System.currentTimeMillis();

        if (config.onPlayerJoin.isEmpty()) return;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            if (onlinePlayer.getUniqueId().equals(event.getPlayer().getUniqueId())) continue;

            onlinePlayer.sendMessage(Chat.translate(config.onPlayerJoin.replaceAll("%player%", player.getName())));

        }

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
