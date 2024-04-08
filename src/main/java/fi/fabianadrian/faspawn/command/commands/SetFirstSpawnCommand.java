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

public class SetFirstSpawnCommand extends FASpawnCommand {
	public SetFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("setfirstspawn").permission("faspawn.command.setfirstspawn");

		this.manager.command(
				builder.senderType(Player.class).handler(this::setFirstSpawnHandler)
		);
		this.manager.command(
				builder.required("location", LocationParser.locationParser()).handler(this::setFirstSpawnCoordinateHandler)
		);
	}

	private void setFirstSpawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();

		this.plugin.spawnManager().setGroupFirstSpawn("default", sender.getLocation());

		sender.sendMessage(Component.translatable("faspawn.command.setgroupspawn").arguments(Component.text("default")));
	}

	private void setFirstSpawnCoordinateHandler(CommandContext<CommandSender> context) {
		Location location = context.get("location");

		this.plugin.spawnManager().setGroupFirstSpawn("default", location);
		if (context.sender() instanceof Player sender) {
			sender.teleport(location);
		}

		context.sender().sendMessage(Component.translatable("faspawn.command.setgroupfirstspawn").arguments(Component.text("default")));
	}
}
