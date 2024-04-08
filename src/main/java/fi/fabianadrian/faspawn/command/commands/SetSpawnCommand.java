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
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;

public final class SetSpawnCommand extends FASpawnCommand {
	public SetSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setspawn").permission("faspawn.command.setspawn");

		this.manager.command(
				builder.senderType(Player.class).handler(this::setSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setSpawnCoordinateHandler)
		);
	}

	private void setSpawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();

		this.plugin.spawnManager().setGroupLocation("default", sender.getLocation(), LocationType.SPAWN);

		sender.sendMessage(Component.translatable("faspawn.command.setgroupspawn").arguments(Component.text("default"), ComponentUtils.locationComponent(sender.getLocation())));
	}

	private void setSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		Location location = context.get("location");

		this.plugin.spawnManager().setGroupLocation("default", location, LocationType.SPAWN);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setgroupspawn").arguments(Component.text("default"), ComponentUtils.locationComponent(location)));
	}
}
