package fi.fabianadrian.faspawn;

import fi.fabianadrian.faspawn.command.AbstractCommand;
import fi.fabianadrian.faspawn.command.commands.*;
import fi.fabianadrian.faspawn.command.processor.FASpawnCommandPreprocessor;
import fi.fabianadrian.faspawn.configuration.Configuration;
import fi.fabianadrian.faspawn.configuration.ConfigurationLoader;
import fi.fabianadrian.faspawn.configuration.serializer.LocationSerializer;
import fi.fabianadrian.faspawn.listener.ServerListener;
import fi.fabianadrian.faspawn.spawn.SpawnManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.spongepowered.configurate.ConfigurateException;

import java.util.List;

public class FASpawn extends JavaPlugin {
	private final ConfigurationLoader<Configuration> configLoader = new ConfigurationLoader<>(
			Configuration.class,
			getServer().getPluginsFolder().toPath().resolve("FabianAdrian/spawn.yml"),
			options -> options.serializers(build -> build.register(Location.class, LocationSerializer.INSTANCE))
	);
	private Configuration config;
	private PaperCommandManager<CommandSender> commandManager;
	private SpawnManager spawnManager;

	@Override
	public void onEnable() {
		this.spawnManager = new SpawnManager(this);

		this.commandManager = PaperCommandManager.createNative(
				this,
				ExecutionCoordinator.simpleCoordinator()
		);

		if (this.commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
			this.commandManager.registerBrigadier();
		} else if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
			commandManager.registerAsynchronousCompletions();
		}

		this.commandManager.registerCommandPreProcessor(new FASpawnCommandPreprocessor<>(this));

		List.of(
			new SpawnCommand(this),
			new SetSpawnCommand(this),
			new SetFirstSpawnCommand(this),
			new SetGroupSpawnCommand(this),
			new SetGroupFirstSpawnCommand(this),
			new SetPlayerSpawnCommand(this),
			new SetPlayerFirstSpawnCommand(this),
			new RootCommand(this)
		).forEach(AbstractCommand::register);

		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
	}

	public PaperCommandManager<CommandSender> commandManager() {
		return this.commandManager;
	}

	public Configuration config() {
		if (this.config == null) {
			throw new IllegalStateException("Config has not yet been loaded");
		}
		return this.config;
	}

	public void reload() throws ConfigurateException {
		this.config = this.configLoader.load();
		this.configLoader.save(this.config);
	}

	public SpawnManager spawnManager() {
		return this.spawnManager;
	}
}
