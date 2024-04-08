package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;

public class SetPlayerFirstSpawnCommand extends AbstractCommand {
	public SetPlayerFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setplayerfirstspawn").permission("faspawn.command.setplayerfirstspawn").required("player", PlayerParser.playerParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setPlayerFirstSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setPlayerFirstSpawnCoordinateHandler)
		);
	}

	private void setPlayerFirstSpawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		Player target = context.get("player");

		this.plugin.spawnManager().setPlayerFirstSpawn(target, sender.getLocation());

		sender.sendMessage(Component.translatable("faspawn.command.setplayerspawn").arguments(target.name()));
	}

	private void setPlayerFirstSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");
		Location location = context.get("location");

		this.plugin.spawnManager().setPlayerFirstSpawn(target, location);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setplayerspawn").arguments(target.name()));
	}
}