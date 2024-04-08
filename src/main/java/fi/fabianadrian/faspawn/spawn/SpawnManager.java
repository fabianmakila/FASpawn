package fi.fabianadrian.faspawn.spawn;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class SpawnManager {
	private final FASpawn plugin;

	public SpawnManager(FASpawn plugin) {
		this.plugin = plugin;
	}

	public Location spawn(Player player) {
		return this.plugin.getServer().getWorlds().get(0).getSpawnLocation();
	}

	public void setGroupSpawn(String group, Location location) {

	}

	public void setGroupFirstSpawn(String group, Location location) {

	}

	public void unsetGroupSpawn(String group) {

	}

	public void setPlayerSpawn(Player player, Location location) {

	}

	public void setPlayerFirstSpawn(Player player, Location location) {

	}

	public void unsetPlayerSpawn(UUID uuid) {

	}

	private List<String> playerGroups() {
		return null;
	}
}
