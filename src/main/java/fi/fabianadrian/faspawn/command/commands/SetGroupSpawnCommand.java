package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public final class SetGroupSpawnCommand extends FASpawnCommand {
	public SetGroupSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setgroupspawn").permission("faspawn.command.setgroupspawn").required("group", StringParser.stringParser());

		this.manager.command(
				builder.senderType(Player.class).handler(this::setGroupSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setGroupSpawnCoordinateHandler)
		);
	}

	private void setGroupSpawnHandler(CommandContext<Player> context) {
		Player player = context.sender();
		String group = context.get("group");

		this.plugin.spawnManager().setGroupSpawn(group, player.getLocation());

		player.sendMessage(Component.translatable("faspawn.command.setgroupspawn").arguments(Component.text(group)));
	}

	private void setGroupSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		String group = context.get("group");
		Location location = context.get("location");

		this.plugin.spawnManager().setGroupSpawn(group, location);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setgroupspawn").arguments(Component.text(group)));
	}
}
