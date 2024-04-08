package fi.fabianadrian.faspawn.configuration;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class Configuration {
	private boolean teleportOnJoin = false;

	private boolean respectBeds = true;
	private boolean respectRespawnAnchors = true;

	private Map<String, Map<String, Location>> groupLocations;
	private Map<UUID, Map<String, Location>> playerLocations;

	public boolean teleportOnJoin() {
		return this.teleportOnJoin;
	}

	public boolean respectBeds() {
		return this.respectBeds;
	}

	public boolean respectAnchors() {
		return this.respectRespawnAnchors;
	}

	public Map<String, Map<String, Location>> groupLocations() {
		return this.groupLocations;
	}

	public Map<UUID, Map<String, Location>> playerLocations() {
		return this.playerLocations;
	}
}
