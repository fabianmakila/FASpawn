package fi.fabianadrian.faspawn.location;

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

public final class LocationManager {
	private final FASpawn plugin;
	private final ConfigurationManager configurationManager;
	private LuckPerms luckPermsApi = null;

	public LocationManager(FASpawn plugin) {
		this.plugin = plugin;
		this.configurationManager = plugin.configurationManager();

		try {
			this.luckPermsApi = plugin.getServer().getServicesManager().load(LuckPerms.class);
		} catch (NoClassDefFoundError ignored) {
		}
	}

	public Location location(Player player, LocationType locationType) {
		Location spawnLocation = playerLocation(player, locationType);
		if (spawnLocation != null) {
			return spawnLocation;
		}

		spawnLocation = groupLocation(player, locationType);
		if (spawnLocation != null) {
			return spawnLocation;
		}

		if (locationType == LocationType.RESPAWN) {
			return this.location(player, LocationType.SPAWN);
		}

		return this.plugin.getServer().getWorlds().get(0).getSpawnLocation().add(0, 1, 0);
	}

	private @Nullable Location playerLocation(Player player, LocationType locationType) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerLocations().get(player.getUniqueId());
		if (playerSpawnLocations == null) {
			return null;
		}

		return playerSpawnLocations.get(locationType.configurationKey);
	}

	private @Nullable Location groupLocation(Player player, LocationType locationType) {
		Map<String, Map<String, Location>> groupSpawns = this.configurationManager.configuration().groupLocations();

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
					Location spawnLocation = groupSpawnLocations.get(locationType.configurationKey);
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

		return defaultGroupSpawnLocations.get(locationType.configurationKey);
	}

	public void setGroupLocation(String groupName, Location location, LocationType locationType) {
		Map<String, Location> groupSpawnLocations = this.configurationManager.configuration().groupLocations().getOrDefault(groupName, new HashMap<>());
		groupSpawnLocations.put(locationType.configurationKey, location);

		this.configurationManager.configuration().groupLocations().put(groupName, groupSpawnLocations);
		this.configurationManager.save();
	}

	public boolean unsetGroupLocation(String groupName, LocationType locationType) {
		Map<String, Location> groupSpawnLocations = this.configurationManager.configuration().groupLocations().get(groupName);
		if (groupSpawnLocations == null || groupSpawnLocations.remove(locationType.configurationKey) == null) {
			return false;
		}

		if (groupSpawnLocations.isEmpty()) {
			this.configurationManager.configuration().groupLocations().remove(groupName);
		}

		this.configurationManager.save();
		return true;
	}

	public void setPlayerLocation(OfflinePlayer player, Location location, LocationType locationType) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerLocations().getOrDefault(player.getUniqueId(), new HashMap<>());
		playerSpawnLocations.put(locationType.configurationKey, location);

		this.configurationManager.configuration().playerLocations().put(player.getUniqueId(), playerSpawnLocations);
		this.configurationManager.save();
	}

	public boolean unsetPlayerLocation(OfflinePlayer player, LocationType locationType) {
		Map<String, Location> playerSpawnLocations = this.configurationManager.configuration().playerLocations().get(player.getUniqueId());
		if (playerSpawnLocations == null || playerSpawnLocations.remove(locationType.configurationKey) == null) {
			return false;
		}

		if (playerSpawnLocations.isEmpty()) {
			this.configurationManager.configuration().playerLocations().remove(player.getUniqueId());
		}

		this.configurationManager.save();
		return true;
	}
}
