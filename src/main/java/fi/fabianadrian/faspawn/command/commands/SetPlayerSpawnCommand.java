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

public final class SetPlayerSpawnCommand extends FASpawnCommand {
	public SetPlayerSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setplayerspawn").permission("faspawn.command.setplayerspawn").required("player", PlayerParser.playerParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setPlayerSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setPlayerSpawnCoordinateHandler)
		);
	}

	private void setPlayerSpawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		Player target = context.get("player");

		this.plugin.spawnManager().setPlayerLocation(target, sender.getLocation(), LocationType.SPAWN);

		sender.sendMessage(Component.translatable("faspawn.command.setplayerspawn", target.name(), ComponentUtils.locationComponent(sender.getLocation())));
	}

	private void setPlayerSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");
		Location location = context.get("location");

		this.plugin.spawnManager().setPlayerLocation(target, location, LocationType.SPAWN);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setplayerspawn", target.name(), ComponentUtils.locationComponent(location)));
	}
}
