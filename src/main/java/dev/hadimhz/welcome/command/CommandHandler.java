package dev.hadimhz.welcome.command;

import dev.hadimhz.welcome.config.Config;
import dev.hadimhz.welcome.listener.PlayerListener;
import dev.hadimhz.welcome.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CommandHandler implements CommandExecutor {

    private final Plugin plugin;
    private final PlayerListener listener;
    private final Config config;

    public CommandHandler(Plugin plugin, PlayerListener listener, Config config) {
        this.plugin = plugin;
        this.listener = listener;
        this.config = config;
    }

    @Override public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.translate("Only Players can run this command."));

            return false;
        }

        final Player player = (Player) sender;

        if (System.currentTimeMillis() - listener.getJoinedAt() >= config.duration * 1000L || !listener.getPlayer().isOnline()) {
            player.sendMessage(Chat.translate(config.welcomePartyExpired));
            return false;
        }

        if (player.getUniqueId().equals(listener.getPlayer().getUniqueId())) {
            player.sendMessage(Chat.translate(config.welcomeYourselfError));
            return false;
        }

        if (listener.getWelcomed().contains(player.getUniqueId())) {
            player.sendMessage(Chat.translate(config.alreadyWelcomed));
            return false;
        }


        final Random random = ThreadLocalRandom.current();

        if (!listener.getPlayer().hasPlayedBefore()) {
            if (config.displayFirstXWelcomeMessages == -1 || listener.getWelcomed().size() < config.displayFirstXWelcomeMessages)
                player.chat(config.firstJoin.get(random.nextInt(config.firstJoin.size())).replaceAll("%player%", listener.getPlayer().getName()));

            for (String command : config.CommandsToExecuteOnFirstJoin) {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
            }

        } else {
            if (config.displayFirstXWelcomeMessages == -1 || listener.getWelcomed().size() < config.displayFirstXWelcomeMessages)
                player.chat(config.joinBack.get(random.nextInt(config.joinBack.size())).replaceAll("%player%", listener.getPlayer().getName()));

            for (String command : config.commandsToExecute) {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
            }
        }

        listener.getWelcomed().add(player.getUniqueId());

        return false;

    }
}
