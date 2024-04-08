package fi.fabianadrian.faspawn;

import fi.fabianadrian.faspawn.command.AbstractCommand;
import fi.fabianadrian.faspawn.command.commands.*;
import fi.fabianadrian.faspawn.command.processor.FASpawnCommandPreprocessor;
import fi.fabianadrian.faspawn.configuration.ConfigurationManager;
import fi.fabianadrian.faspawn.listener.ServerListener;
import fi.fabianadrian.faspawn.spawn.SpawnManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.List;

public class FASpawn extends JavaPlugin {
	private PaperCommandManager<CommandSender> commandManager;
	private SpawnManager spawnManager;
	private ConfigurationManager configurationManager;

	@Override
	public void onEnable() {
		this.configurationManager = new ConfigurationManager(this);
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

	public void reload() {
		this.configurationManager.reload();
	}

	public SpawnManager spawnManager() {
		return this.spawnManager;
	}

	public ConfigurationManager configurationManager() {
		return this.configurationManager;
	}
}