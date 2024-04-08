package fi.fabianadrian.faspawn.configuration;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.configuration.serializer.LocationSerializer;
import org.bukkit.Location;
import org.spongepowered.configurate.ConfigurateException;

public class ConfigurationManager {
	private final ConfigurationLoader<Configuration> configurationLoader;
	private Configuration configuration;

	public ConfigurationManager(FASpawn plugin) {
		this.configurationLoader = new ConfigurationLoader<>(
				Configuration.class,
				plugin.getServer().getPluginsFolder().toPath().resolve("FabianAdrian/spawn.yml"),
				options -> options.serializers(build -> build.register(Location.class, LocationSerializer.INSTANCE))
		);
	}

	public void reload() {
		try {
			this.configuration = this.configurationLoader.load();
			this.configurationLoader.save(this.configuration);
		} catch (ConfigurateException e) {
			throw new IllegalStateException("Failed to load config", e);
		}
	}

	public Configuration configuration() {
		if (this.configuration == null) {
			throw new IllegalStateException("Config has not yet been loaded");
		}
		return this.configuration;
	}

	public void save() {
		try {
			this.configurationLoader.save(this.configuration);
		} catch (ConfigurateException e) {
			throw new IllegalStateException("Failed to save config", e);
		}
	}
}
