package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;

public class UnsetRespawnCommand extends FASpawnCommand {
	public UnsetRespawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(
				this.manager.commandBuilder("unsetrespawn").permission("faspawn.command.unsetrespawn").handler(this::unsetRespawnHandler)
		);
	}

	private void unsetRespawnHandler(CommandContext<CommandSender> context) {
		if (this.plugin.spawnManager().unsetGroupLocation("default", LocationType.RESPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgrouprespawn").arguments(Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgrouprespawn.spawn-not-set").arguments(Component.text("default")));
		}
	}
}
