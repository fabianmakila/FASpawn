package fi.fabianadrian.faspawn.listener;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {
	private final FASpawn plugin;

	public PlayerListener(FASpawn plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPlayedBefore()) {
			Location firstSpawnLocation = this.plugin.spawnManager().firstSpawnLocation(player);
			if (firstSpawnLocation != null) {
				player.teleport(firstSpawnLocation);
				return;
			}
		}

		if (this.plugin.configurationManager().configuration().teleportOnJoin()) {
			Location spawnLocation = this.plugin.spawnManager().spawnLocation(player);
			if (spawnLocation == null) {
				return;
			}
			player.teleport(spawnLocation);
		}
	}
}
