package fi.fabianadrian.faspawn.configuration;

import fi.fabianadrian.faspawn.spawn.SpawnLocationHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class Configuration {
	private boolean teleportOnJoin = false;

	private SpawnDataSection spawns = new SpawnDataSection();

	public boolean teleportOnJoin() {
		return this.teleportOnJoin;
	}

	public SpawnDataSection spawns() {
		return this.spawns;
	}

	public static class SpawnDataSection {
		private Map<String, SpawnLocationHolder> groupSpawns;
		private Map<UUID, SpawnLocationHolder> playerSpawns;

		public Map<String, SpawnLocationHolder> groupSpawns() {
			return this.groupSpawns;
		}

		public Map<UUID, SpawnLocationHolder> playerSpawns() {
			return this.playerSpawns;
		}
	}
}
