package fi.fabianadrian.faspawn.configuration;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class Configuration {
	private boolean teleportOnJoin = false;

	private Map<String, Map<String, Location>> groupSpawns;
	private Map<UUID, Map<String, Location>> playerSpawns;

	public boolean teleportOnJoin() {
		return this.teleportOnJoin;
	}

	public Map<String, Map<String, Location>> groupSpawns() {
		return this.groupSpawns;
	}

	public Map<UUID, Map<String, Location>> playerSpawns() {
		return this.playerSpawns;
	}
}
