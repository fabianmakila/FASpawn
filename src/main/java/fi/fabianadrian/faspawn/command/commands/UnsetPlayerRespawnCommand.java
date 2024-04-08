package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.context.CommandContext;

public class UnsetPlayerRespawnCommand extends FASpawnCommand {
	public UnsetPlayerRespawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetplayerrespawn").permission("faspawn.command.unsetplayerrespawn").required("player", PlayerParser.playerParser()).handler(this::unsetPlayerRespawnHandler));
	}

	private void unsetPlayerRespawnHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");

		if (this.plugin.spawnManager().unsetPlayerLocation(target, LocationType.RESPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerrespawn").arguments(target.name()));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerrespawn.spawn-not-set").arguments(target.name()));
		}
	}
}
