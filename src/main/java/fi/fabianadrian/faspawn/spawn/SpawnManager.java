package fi.fabianadrian.faspawn.spawn;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.configuration.ConfigurationManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SpawnManager {
	private static final String SPAWN_KEY = "spawn-location";
	private static final String FIRST_SPAWN_KEY = "first-spawn-location";
	private final FASpawn plugin;
	private final ConfigurationManager configurationManager;
	private LuckPerms luckPermsApi = null;

	public SpawnManager(FASpawn plugin) {
		this.plugin = plugin;
		this.configurationManager = plugin.configurationManager();

		try {
			this.luckPermsApi = plugin.getServer().getServicesManager().load(LuckPerms.class);
		} catch (NoClassDefFoundError ignored) {
		}
	}

	public Location spawnLocation(Player player) {
		return spawnLocation(player, SPAWN_KEY);
	}

	public Location firstSpawnLocation(Player player) {
		return spawnLocation(player, FIRST_SPAWN_KEY);
	}

	private Location spawnLocation(Player player, String key) {
		Location spawnLocation = nullablePlayerSpawn(player, key);
		if (spawnLocation != null) {
			return spawnLocation;
		}

		spawnLocation = nullablePlayerGroupSpawn(player, key);
		if (spawnLocation != null) {
			return spawnLocation;
		}

		return this.plugin.getServer().getWorlds().get(0).getSpawnLocation();
	}

	private @Nullable Location nullablePlayerSpawn(Player player, String key) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerSpawns().get(player.getUniqueId());
		if (playerSpawnLocations == null) {
			return null;
		}

		return playerSpawnLocations.get(key);
	}

	private @Nullable Location nullablePlayerGroupSpawn(Player player, String key) {
		Map<String, Map<String, Location>> groupSpawns = this.configurationManager.configuration().groupSpawns();

		if (groupSpawns.isEmpty()) {
			return null;
		}

		if (this.luckPermsApi != null) {
			User user = this.luckPermsApi.getPlayerAdapter(Player.class).getUser(player);

			// Player inherited groups from the highest weight to lowest
			List<String> groups = user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName).sorted(Comparator.comparingInt(groupName -> {
				Group group = this.luckPermsApi.getGroupManager().getGroup(groupName);
				return group != null ? -group.getWeight().orElse(0) : 0;
			})).toList();

			for (String group : groups) {
				Map<String, Location> groupSpawnLocations = groupSpawns.get(group);
				if (groupSpawnLocations != null) {
					Location spawnLocation = groupSpawnLocations.get(key);
					if (spawnLocation == null) {
						continue;
					}

					return spawnLocation;
				}
			}
		}

		// Lastly try to get the default group spawn.
		Map<String, Location> defaultGroupSpawnLocations = groupSpawns.get("default");
		if (defaultGroupSpawnLocations == null) {
			return null;
		}

		return defaultGroupSpawnLocations.get(key);
	}

	public void setGroupSpawn(String groupName, Location location) {
		setGroupSpawn(groupName, location, SPAWN_KEY);
	}

	public void setGroupFirstSpawn(String groupName, Location location) {
		setGroupSpawn(groupName, location, FIRST_SPAWN_KEY);
	}

	private void setGroupSpawn(String groupName, Location location, String key) {
		Map<String, Location> groupSpawnLocations = this.configurationManager.configuration().groupSpawns().getOrDefault(groupName, new HashMap<>());
		groupSpawnLocations.put(key, location);

		this.configurationManager.configuration().groupSpawns().put(groupName, groupSpawnLocations);
		this.configurationManager.save();
	}

	public boolean unsetGroupSpawn(String groupName) {
		return unsetGroupSpawn(groupName, SPAWN_KEY);
	}

	public boolean unsetGroupFirstSpawn(String groupName) {
		return unsetGroupSpawn(groupName, FIRST_SPAWN_KEY);
	}

	private boolean unsetGroupSpawn(String groupName, String key) {
		Map<String, Location> groupSpawnLocations = this.configurationManager.configuration().groupSpawns().get(groupName);
		if (groupSpawnLocations == null || groupSpawnLocations.remove(key) == null) {
			return false;
		}

		if (groupSpawnLocations.isEmpty()) {
			this.configurationManager.configuration().groupSpawns().remove(groupName);
		}

		this.configurationManager.save();
		return true;
	}

	public void setPlayerSpawn(OfflinePlayer player, Location location) {
		setPlayerSpawn(player, location, SPAWN_KEY);
	}

	public void setPlayerFirstSpawn(OfflinePlayer player, Location location) {
		setPlayerSpawn(player, location, FIRST_SPAWN_KEY);
	}

	private void setPlayerSpawn(OfflinePlayer player, Location location, String key) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerSpawns().getOrDefault(player.getUniqueId(), new HashMap<>());
		playerSpawnLocations.put(key, location);

		this.configurationManager.configuration().playerSpawns().put(player.getUniqueId(), playerSpawnLocations);
		this.configurationManager.save();
	}

	public boolean unsetPlayerSpawn(OfflinePlayer player) {
		return unsetPlayerSpawn(player, SPAWN_KEY);
	}

	public boolean unsetPlayerFirstSpawn(OfflinePlayer player) {
		return unsetPlayerSpawn(player, FIRST_SPAWN_KEY);
	}

	private boolean unsetPlayerSpawn(OfflinePlayer player, String key) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerSpawns().get(player.getUniqueId());
		if (playerSpawnLocations == null || playerSpawnLocations.remove(key) == null) {
			return false;
		}

		if (playerSpawnLocations.isEmpty()) {
			this.configurationManager.configuration().playerSpawns().remove(player.getUniqueId());
		}

		this.configurationManager.save();
		return true;
	}
}
