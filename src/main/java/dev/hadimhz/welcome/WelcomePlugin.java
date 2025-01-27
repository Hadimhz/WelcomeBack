package dev.hadimhz.welcome;

import dev.hadimhz.welcome.command.CommandHandler;
import dev.hadimhz.welcome.config.Config;
import dev.hadimhz.welcome.listener.PlayerListener;
import dev.hadimhz.welcome.util.config.ConfigRegistry;
import dev.hadimhz.welcome.util.config.JsonConfigRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class WelcomePlugin extends JavaPlugin {



    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        final ConfigRegistry configRegistry = new JsonConfigRegistry();

        final Config config = configRegistry.register(Config.class, new Config(), new File(getDataFolder(), "config.json"));

        final PlayerListener playerListener = new PlayerListener(this, config);

        Objects.requireNonNull(getCommand("welcome")).setExecutor(new CommandHandler(this, playerListener, config));

    }
}
