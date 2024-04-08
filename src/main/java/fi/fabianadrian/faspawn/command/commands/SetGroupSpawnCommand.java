package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public class SetGroupSpawnCommand extends AbstractCommand {
	public SetGroupSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<Player> builder = this.manager.commandBuilder("setgroupspawn").senderType(Player.class).permission("faspawn.command.setgroupspawn").required("group", StringParser.stringParser());

		this.manager.command(
				builder.handler(this::setGroupSpawnHandler)
		);

		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setGroupSpawnCoordinateHandler)
		);
	}

	private void setGroupSpawnHandler(CommandContext<Player> context) {
		context.sender().sendMessage(Component.text("setGroupSpawnHandler"));
	}

	private void setGroupSpawnCoordinateHandler(CommandContext<Player> context) {
		context.sender().sendMessage(Component.text("setGroupSpawnCoordinateHandler"));
	}
}
