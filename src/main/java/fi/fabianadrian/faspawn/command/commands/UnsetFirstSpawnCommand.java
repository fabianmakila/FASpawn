package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;

public class UnsetFirstSpawnCommand extends FASpawnCommand {
	public UnsetFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(
				this.manager.commandBuilder("unsetfirstspawn").permission("faspawn.command.unsetfirstspawn").handler(this::unsetFirstSpawnHandler)
		);
	}

	private void unsetFirstSpawnHandler(CommandContext<CommandSender> context) {
		if (this.plugin.spawnManager().unsetGroupFirstSpawn("default")) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupfirstspawn").arguments(Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupfirstspawn.spawn-not-set").arguments(Component.text("default")));
		}
	}
}
