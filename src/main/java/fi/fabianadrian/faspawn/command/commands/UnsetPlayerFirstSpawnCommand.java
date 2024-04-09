package fi.fabianadrian.faspawn.command.commands;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnCommand;
import fi.fabianadrian.faspawn.location.LocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.context.CommandContext;

public final class UnsetPlayerFirstSpawnCommand extends FASpawnCommand {
	public UnsetPlayerFirstSpawnCommand(FASpawn plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		this.manager.command(this.manager.commandBuilder("unsetplayerfirstspawn").permission("faspawn.command.unsetplayerfirstspawn").required("player", PlayerParser.playerParser()).handler(this::unsetPlayerFirstSpawnHandler));
	}

	private void unsetPlayerFirstSpawnHandler(CommandContext<CommandSender> context) {
		Player target = context.get("player");

		if (this.plugin.spawnManager().unsetPlayerLocation(target, LocationType.FIRST_SPAWN)) {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerfirstspawn", target.name()));
		} else {
			context.sender().sendMessage(Component.translatable("faspawn.command.unsetplayerfirstspawn.spawn-not-set", target.name()));
		}
	}
}
