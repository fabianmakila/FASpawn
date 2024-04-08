package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.context.CommandContext;

public class SpawnCommand extends AbstractCommand {
	public SpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("spawn").permission("faspawn.command.spawn");

		this.manager.command(
				builder.senderType(Player.class).handler(this::spawnHandler)
		);
		this.manager.command(
				builder.required("player", PlayerParser.playerParser()).permission("faspawn.command.spawn.other").handler(this::spawnOtherHandler)
		);
	}

	private void spawnHandler(CommandContext<Player> context) {
		Player sender = context.sender();

		Location spawnLocation = this.plugin.spawnManager().spawn(sender);
		sender.teleport(spawnLocation);

		context.sender().sendMessage(Component.translatable("faspawn.command.spawn"));
	}

	private void spawnOtherHandler(CommandContext<CommandSender> context) {
		Player player = context.get("player");

		Location spawnLocation = this.plugin.spawnManager().spawn(player);
		player.teleport(spawnLocation);
		player.sendMessage(Component.translatable("faspawn.command.spawn"));

		context.sender().sendMessage(Component.translatable("faspawn.command.spawn.other").arguments(player.name()));
	}
}
