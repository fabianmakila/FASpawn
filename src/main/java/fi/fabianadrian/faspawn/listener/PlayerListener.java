package fi.fabianadrian.faspawn.listener;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.location.LocationType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class PlayerListener implements Listener {
	private final FASpawn plugin;

	public PlayerListener(FASpawn plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPlayedBefore()) {
			Location firstSpawnLocation = this.plugin.spawnManager().location(player, LocationType.FIRST_SPAWN);
			player.teleport(firstSpawnLocation);
			return;
		}

		if (this.plugin.configurationManager().configuration().teleportOnJoin()) {
			Location spawnLocation = this.plugin.spawnManager().location(player, LocationType.SPAWN);
			player.teleport(spawnLocation);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.isAnchorSpawn() && this.plugin.configurationManager().configuration().respectAnchors()) {
			return;
		}
		if (event.isBedSpawn() && this.plugin.configurationManager().configuration().respectBeds()) {
			return;
		}

		Player player = event.getPlayer();
		Location respawnLocation = this.plugin.spawnManager().location(player, LocationType.RESPAWN);
		player.teleport(respawnLocation);
	}
}
