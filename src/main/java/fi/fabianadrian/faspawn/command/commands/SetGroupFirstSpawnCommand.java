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

public final class SetGroupFirstSpawnCommand extends FASpawnCommand {
	public SetGroupFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setgroupfirstspawn").permission("faspawn.command.setgroupfirstspawn").required("group", StringParser.stringParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setGroupFirstSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setGroupFirstSpawnCoordinateHandler)
		);
	}

	private void setGroupFirstSpawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		String group = context.get("group");

		this.plugin.spawnManager().setGroupLocation(group, sender.getLocation(), LocationType.FIRST_SPAWN);

		sender.sendMessage(Component.translatable("faspawn.command.setgroupfirstspawn", Component.text(group), ComponentUtils.locationComponent(sender.getLocation())));
	}

	private void setGroupFirstSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		String group = context.get("group");
		Location location = context.get("location");

		this.plugin.spawnManager().setGroupLocation(group, location, LocationType.FIRST_SPAWN);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setgroupspawn", Component.text(group), ComponentUtils.locationComponent(location)));
	}
}
