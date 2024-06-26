package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;

public final class RootCommand extends FASpawnCommand {
	public RootCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("faspawn");

		this.manager.command(
				builder.literal("reload").permission("faspawn.command.root.reload").handler(this::reloadHandler)
		);
	}

	private void reloadHandler(CommandContext<CommandSender> context) {
		this.plugin.reload();
		context.sender().sendMessage(Component.translatable("faspawn.command.root.reload"));
	}
}
