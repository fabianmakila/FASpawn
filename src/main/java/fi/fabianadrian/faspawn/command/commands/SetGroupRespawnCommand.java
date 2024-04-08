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
import org.incendo.cloud.parser.standard.StringParser;

public class SetGroupRespawnCommand extends FASpawnCommand {
	public SetGroupRespawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setgrouprespawn").permission("faspawn.command.setgrouprespawn").required("group", StringParser.stringParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setGroupRespawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setGroupRespawnCoordinateHandler)
		);
	}

	private void setGroupRespawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		String group = context.get("group");

		this.plugin.spawnManager().setGroupLocation(group, sender.getLocation(), LocationType.RESPAWN);

		sender.sendMessage(Component.translatable("faspawn.command.setgrouprespawn").arguments(Component.text(group), ComponentUtils.locationComponent(sender.getLocation())));
	}

	private void setGroupRespawnCoordinateHandler(CommandContext<CommandSender> context) {
		String group = context.get("group");
		Location location = context.get("location");

		this.plugin.spawnManager().setGroupLocation(group, location, LocationType.RESPAWN);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setgrouprespawn").arguments(Component.text(group), ComponentUtils.locationComponent(location)));
	}
}
