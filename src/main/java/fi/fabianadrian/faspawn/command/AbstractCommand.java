package fi.fabianadrian.faspawn.command;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.paper.PaperCommandManager;

public abstract class AbstractCommand {
	protected final FASpawn plugin;
	protected final PaperCommandManager<CommandSender> manager;

	public AbstractCommand(FASpawn plugin) {
		this.plugin = plugin;
		this.manager = plugin.commandManager();
	}

	public abstract void register();
}
