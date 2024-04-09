package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public final class UnsetGroupSpawnCommand extends FASpawnCommand {
	public UnsetGroupSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetgroupspawn").permission("faspawn.command.unsetgroupspawn").required("group", StringParser.stringParser()).handler(this::unsetGroupSpawnHandler));
	}

	private void unsetGroupSpawnHandler(CommandContext<CommandSender> context) {
		String groupName = context.get("group");

		if (this.plugin.spawnManager().unsetGroupLocation(groupName, LocationType.SPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupspawn", Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupspawn.spawn-not-set", Component.text("default")));
		}
	}
}
