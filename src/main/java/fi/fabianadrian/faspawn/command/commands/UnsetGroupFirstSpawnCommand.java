package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public final class UnsetGroupFirstSpawnCommand extends FASpawnCommand {
	public UnsetGroupFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetgroupfirstspawn").permission("faspawn.command.unsetgroupfirstspawn").required("group", StringParser.stringParser()).handler(this::unsetGroupFirstSpawnHandler));
	}

	private void unsetGroupFirstSpawnHandler(CommandContext<CommandSender> context) {
		String groupName = context.get("group");

		if (this.plugin.spawnManager().unsetGroupFirstSpawn(groupName)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupfirstspawn").arguments(Component.text("default")));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetgroupfirstspawn.spawn-not-set").arguments(Component.text("default")));
		}
	}
}
