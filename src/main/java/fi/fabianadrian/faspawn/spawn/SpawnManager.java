package fi.fabianadrian.faspawn.spawn;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.configuration.ConfigurationManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//TODO Remove code duplication
public class SpawnManager {
	private final FASpawn plugin;
	private final ConfigurationManager configurationManager;
	private LuckPerms luckPermsApi;

	public SpawnManager(FASpawn plugin) {
		this.plugin = plugin;
		this.configurationManager = plugin.configurationManager();

		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
			this.luckPermsApi = provider.getProvider();
		}
	}

	public Location playerSpawnLocation(Player player) {
		// Return player spawn if exists
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().get(player.getUniqueId());
		if (data != null && data.spawnLocation() != null) {
			return data.spawnLocation();
		}

		// Return group spawn if exists
		// If Luckperms is present checks user's groups from the highest weight to lowest. Else defaults to "default" group.
		Map<String, SpawnLocationHolder> groupSpawns = this.configurationManager.configuration().spawns().groupSpawns();
		if (!groupSpawns.isEmpty()) {
			if (this.luckPermsApi != null) {
				User user = this.luckPermsApi.getPlayerAdapter(Player.class).getUser(player);
				List<String> groups = user.getNodes(NodeType.INHERITANCE).stream()
						.map(InheritanceNode::getGroupName)
						.sorted(Comparator.comparingInt(groupName -> {
							Group group = this.luckPermsApi.getGroupManager().getGroup(groupName);
							return group != null ? -group.getWeight().orElse(0) : 0;
						}))
						.toList();

				for (String group : groups) {
					data = groupSpawns.get(group);
					if (data != null && data.spawnLocation() != null) {
						return data.spawnLocation();
					}
				}
			}

			data = groupSpawns.get("default");
			if (data != null && data.spawnLocation() != null) {
				return data.spawnLocation();
			}
		}

		return this.plugin.getServer().getWorlds().get(0).getSpawnLocation();
	}

	public Location playerFirstSpawnLocation(Player player) {
		// Return player spawn if exists
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().get(player.getUniqueId());
		if (data != null && data.firstSpawnLocation() != null) {
			return data.firstSpawnLocation();
		}

		// Return group spawn if exists
		// If Luckperms is present checks user's groups from the highest weight to lowest. Else defaults to "default" group.
		Map<String, SpawnLocationHolder> groupSpawns = this.configurationManager.configuration().spawns().groupSpawns();
		if (!groupSpawns.isEmpty()) {
			if (this.luckPermsApi != null) {
				User user = this.luckPermsApi.getPlayerAdapter(Player.class).getUser(player);
				List<String> groups = user.getNodes(NodeType.INHERITANCE).stream()
						.map(InheritanceNode::getGroupName)
						.sorted(Comparator.comparingInt(groupName -> {
							Group group = this.luckPermsApi.getGroupManager().getGroup(groupName);
							return group != null ? -group.getWeight().orElse(0) : 0;
						}))
						.toList();

				for (String group : groups) {
					data = groupSpawns.get(group);
					if (data != null && data.firstSpawnLocation() != null) {
						return data.firstSpawnLocation();
					}
				}
			}

			data = groupSpawns.get("default");
			if (data != null && data.firstSpawnLocation() != null) {
				return data.firstSpawnLocation();
			}
		}

		return this.plugin.getServer().getWorlds().get(0).getSpawnLocation();
	}

	public void setGroupSpawn(String groupName, Location location) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().groupSpawns().getOrDefault(groupName, new SpawnLocationHolder());
		data.spawnLocation(location);

		this.configurationManager.configuration().spawns().groupSpawns().put(groupName, data);
		this.configurationManager.save();
	}

	public void setGroupFirstSpawn(String groupName, Location location) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().groupSpawns().getOrDefault(groupName, new SpawnLocationHolder());
		data.firstSpawnLocation(location);

		this.configurationManager.configuration().spawns().groupSpawns().put(groupName, data);
		this.configurationManager.save();
	}

	public boolean unsetGroupSpawn(String groupName) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().groupSpawns().get(groupName);
		if (data == null || data.spawnLocation() == null) {
			return false;
		}

		if (data.firstSpawnLocation() == null) {
			this.configurationManager.configuration().spawns().groupSpawns().remove(groupName);
		} else {
			data.spawnLocation(null);
			this.configurationManager.configuration().spawns().groupSpawns().put(groupName, data);
		}

		this.configurationManager.save();
		return true;
	}

	public boolean unsetGroupFirstSpawn(String groupName) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().groupSpawns().get(groupName);
		if (data == null || data.firstSpawnLocation() == null) {
			return false;
		}

		if (data.spawnLocation() == null) {
			this.configurationManager.configuration().spawns().groupSpawns().remove(groupName);
		} else {
			data.firstSpawnLocation(null);
			this.configurationManager.configuration().spawns().groupSpawns().put(groupName, data);
		}

		this.configurationManager.save();
		return true;
	}

	public void setPlayerSpawn(Player player, Location location) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().getOrDefault(player.getUniqueId(), new SpawnLocationHolder());
		data.spawnLocation(location);

		this.configurationManager.configuration().spawns().playerSpawns().put(player.getUniqueId(), data);
		this.configurationManager.save();
	}

	public void setPlayerFirstSpawn(Player player, Location location) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().getOrDefault(player.getUniqueId(), new SpawnLocationHolder());
		data.firstSpawnLocation(location);

		this.configurationManager.configuration().spawns().playerSpawns().put(player.getUniqueId(), data);
		this.configurationManager.save();
	}

	public boolean unsetPlayerSpawn(UUID uuid) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().get(uuid);
		if (data == null || data.spawnLocation() == null) {
			return false;
		}

		if (data.firstSpawnLocation() == null) {
			this.configurationManager.configuration().spawns().playerSpawns().remove(uuid);
		} else {
			data.spawnLocation(null);
			this.configurationManager.configuration().spawns().playerSpawns().put(uuid, data);
		}

		this.configurationManager.save();
		return true;
	}

	public boolean unsetPlayerFirstSpawn(UUID uuid) {
		SpawnLocationHolder data = this.configurationManager.configuration().spawns().playerSpawns().get(uuid);
		if (data == null || data.firstSpawnLocation() == null) {
			return false;
		}

		if (data.spawnLocation() == null) {
			this.configurationManager.configuration().spawns().playerSpawns().remove(uuid);
		} else {
			data.firstSpawnLocation(null);
			this.configurationManager.configuration().spawns().playerSpawns().put(uuid, data);
		}

		this.configurationManager.save();
		return true;
	}
}
