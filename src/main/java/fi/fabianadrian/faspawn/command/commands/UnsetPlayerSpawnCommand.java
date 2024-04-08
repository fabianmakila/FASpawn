package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.context.CommandContext;

public final class UnsetPlayerSpawnCommand extends FASpawnCommand {
	public UnsetPlayerSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetplayerspawn").permission("faspawn.command.unsetplayerspawn").required("player", PlayerParser.playerParser()).handler(this::unsetPlayerSpawnHandler));
	}

	private void unsetPlayerSpawnHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");

		if (this.plugin.spawnManager().unsetPlayerLocation(target, LocationType.SPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerspawn").arguments(target.name()));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerspawn.spawn-not-set").arguments(target.name()));
		}
	}
}
