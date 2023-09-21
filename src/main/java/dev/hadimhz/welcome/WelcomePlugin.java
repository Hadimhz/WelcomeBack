package dev.hadimhz.welcome;

import com.vertmix.config.ConfigRegistry;
import com.vertmix.config.json.JsonConfigRegistry;
import dev.hadimhz.welcome.command.Command;
import dev.hadimhz.welcome.config.Config;
import dev.hadimhz.welcome.listener.PlayerListener;
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

        Objects.requireNonNull(getCommand("welcome")).setExecutor(new Command(this, playerListener, config));

    }
}
