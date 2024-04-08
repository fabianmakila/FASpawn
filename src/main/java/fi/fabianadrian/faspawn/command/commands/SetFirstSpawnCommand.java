package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;

public class SetFirstSpawnCommand extends AbstractCommand {
	public SetFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<Player> builder = this.manager.commandBuilder("setfirstspawn").senderType(Player.class).permission("faspawn.command.setfirstspawn");

		this.manager.command(
				builder.handler(this::setFirstSpawnHandler)
		);

		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setFirstSpawnCoordinateHandler)
		);
	}

	private void setFirstSpawnHandler(CommandContext<Player> context) {
		context.sender().sendMessage(Component.text("setFirstSpawnHandler"));
	}

	private void setFirstSpawnCoordinateHandler(CommandContext<Player> context) {
		context.sender().sendMessage(Component.text("setFirstSpawnCoordinateHandler"));
	}
}
