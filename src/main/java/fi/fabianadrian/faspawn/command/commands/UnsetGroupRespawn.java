package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public class UnsetGroupRespawn extends FASpawnCommand {
	public UnsetGroupRespawn(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetgrouprespawn").permission("faspawn.command.unsetgrouprespawn").required("group", StringParser.stringParser()).handler(this::unsetGroupRespawnHandler));
	}

	private void unsetGroupRespawnHandler(CommandContext<CommandSender> context) {
		String groupName = context.get("group");

		if (this.plugin.spawnManager().unsetGroupLocation(groupName, LocationType.RESPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgrouprespawn").arguments(Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgrouprespawn.spawn-not-set").arguments(Component.text("default")));
		}
	}
}
