package fi.fabianadrian.faspawn.spawn;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class SpawnLocationHolder {
	private Location spawnLocation;
	private Location firstSpawnLocation;

	public SpawnLocationHolder() {}

	public SpawnLocationHolder(Location spawnLocation, Location firstSpawnLocation) {
		this.spawnLocation = spawnLocation;
		this.firstSpawnLocation = firstSpawnLocation;
	}

	public Location spawnLocation() {
		return this.spawnLocation;
	}

	public void spawnLocation(Location location) {
		this.spawnLocation = location;
	}

	public Location firstSpawnLocation() {
		return this.firstSpawnLocation;
	}

	public void firstSpawnLocation(Location location) {
		this.firstSpawnLocation = location;
	}
}
