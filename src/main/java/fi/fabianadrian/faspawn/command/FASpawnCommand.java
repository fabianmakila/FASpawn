package fi.fabianadrian.faspawn.command;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.paper.PaperCommandManager;

public abstract class FASpawnCommand {
	protected final FASpawn plugin;
	protected final PaperCommandManager<CommandSender> manager;

	public FASpawnCommand(FASpawn plugin) {
		this.plugin = plugin;
		this.manager = plugin.commandManager();
	}

	public abstract void register();
}
