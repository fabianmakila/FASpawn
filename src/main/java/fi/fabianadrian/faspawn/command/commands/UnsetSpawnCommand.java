package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;

public class UnsetSpawnCommand extends FASpawnCommand {
	public UnsetSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(
				this.manager.commandBuilder("unsetspawn").permission("faspawn.command.unsetspawn").handler(this::unsetSpawnHandler)
		);
	}

	private void unsetSpawnHandler(CommandContext<CommandSender> context) {
		if (this.plugin.spawnManager().unsetGroupSpawn("default")) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupspawn").arguments(Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupspawn.spawn-not-set").arguments(Component.text("default")));
		}
	}
}
