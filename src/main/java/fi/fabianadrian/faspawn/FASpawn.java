package fi.fabianadrian.faspawn;

import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.command.commands.*;
import fi.fabianadrian.faspawn.command.processor.FASpawnCommandPreprocessor;
import fi.fabianadrian.faspawn.configuration.ConfigurationManager;
import fi.fabianadrian.faspawn.listener.PlayerListener;
import fi.fabianadrian.faspawn.listener.ServerListener;
import fi.fabianadrian.faspawn.locale.TranslationManager;
import fi.fabianadrian.faspawn.location.LocationManager;
import net.kyori.adventure.identity.Identity;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.translations.LocaleExtractor;
import org.incendo.cloud.translations.TranslationBundle;
import org.incendo.cloud.translations.bukkit.BukkitTranslationBundle;

import java.util.List;
import java.util.Locale;

public final class FASpawn extends JavaPlugin {
	private PaperCommandManager<CommandSender> commandManager;
	private LocationManager spawnManager;
	private ConfigurationManager configurationManager;

	@Override
	public void onEnable() {
		new TranslationManager(getSLF4JLogger());

		this.configurationManager = new ConfigurationManager(this);
		this.spawnManager = new LocationManager(this);

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

		LocaleExtractor<CommandSender> extractor = commandSender -> commandSender.getOrDefault(Identity.LOCALE, Locale.ENGLISH);
		TranslationBundle<CommandSender> coreBundle = TranslationBundle.core(extractor);
		TranslationBundle<CommandSender> bukkitBundle = BukkitTranslationBundle.bukkit(extractor);
		this.commandManager.captionRegistry().registerProvider(coreBundle);
		this.commandManager.captionRegistry().registerProvider(bukkitBundle);

		registerCommands();
		registerListeners();
	}

	public PaperCommandManager<CommandSender> commandManager() {
		return this.commandManager;
	}

	public void reload() {
		this.configurationManager.reload();
	}

	public LocationManager spawnManager() {
		return this.spawnManager;
	}

	public ConfigurationManager configurationManager() {
		return this.configurationManager;
	}

	private void registerCommands() {
		List.of(
				new RootCommand(this),
				new SetFirstSpawnCommand(this),
				new SetGroupFirstSpawnCommand(this),
				new SetGroupRespawnCommand(this),
				new SetGroupSpawnCommand(this),
				new SetPlayerFirstSpawnCommand(this),
				new SetPlayerRespawnCommand(this),
				new SetPlayerSpawnCommand(this),
				new SetRespawnCommand(this),
				new SetSpawnCommand(this),
				new SpawnCommand(this),
				new UnsetFirstSpawnCommand(this),
				new UnsetGroupFirstSpawnCommand(this),
				new UnsetGroupRespawnCommand(this),
				new UnsetGroupSpawnCommand(this),
				new UnsetPlayerFirstSpawnCommand(this),
				new UnsetPlayerRespawnCommand(this),
				new UnsetPlayerSpawnCommand(this),
				new UnsetRespawnCommand(this),
				new UnsetSpawnCommand(this)
		).forEach(FASpawnCommand::register);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		List.of(
				new PlayerListener(this),
				new ServerListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}
