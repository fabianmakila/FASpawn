package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;
import org.spongepowered.configurate.ConfigurateException;

public class RootCommand extends AbstractCommand {
	public RootCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("faspawn");

		// Reload
		this.manager.command(
				builder.literal("reload").permission("faspawn.command.root.reload").handler(this::reloadHandler)
		);
	}

	private void reloadHandler(CommandContext<CommandSender> context) {
		try {
			this.plugin.reload();
			context.sender().sendMessage(Component.translatable("faspawn.command.root.reload.success"));
		} catch (ConfigurateException e) {
			context.sender().sendMessage(Component.translatable("faspawn.command.root.reload.failed"));
			throw new IllegalStateException("Failed to load config", e);
		}
	}
}
