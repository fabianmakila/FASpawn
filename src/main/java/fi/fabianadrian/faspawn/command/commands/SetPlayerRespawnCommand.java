package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import fi.fabianadrian.faspawn.util.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;

public class SetPlayerRespawnCommand extends FASpawnCommand {
	public SetPlayerRespawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setplayerrespawn").permission("faspawn.command.setplayerrespawn").required("player", PlayerParser.playerParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setPlayerRespawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setPlayerRespawnCoordinateHandler)
		);
	}

	private void setPlayerRespawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		Player target = context.get("player");

		this.plugin.spawnManager().setPlayerLocation(target, sender.getLocation(), LocationType.RESPAWN);

		sender.sendMessage(Component.translatable("faspawn.command.setplayerrespawn", target.name(), ComponentUtils.locationComponent(sender.getLocation())));
	}

	private void setPlayerRespawnCoordinateHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");
		Location location = context.get("location");

		this.plugin.spawnManager().setPlayerLocation(target, location, LocationType.RESPAWN);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setplayerrespawn", target.name(), ComponentUtils.locationComponent(location)));
	}
}
