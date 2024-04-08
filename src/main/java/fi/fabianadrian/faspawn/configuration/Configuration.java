package fi.fabianadrian.faspawn.configuration;

import fi.fabianadrian.faspawn.spawn.SpawnData;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class Configuration {
	private boolean teleportOnJoin = false;

	public boolean teleportOnJoin() {
		return this.teleportOnJoin;
	}

	public static class Spawns {
		private Map<String, SpawnData> groupSpawns;
		private Map<UUID, SpawnData> playerSpawns;

		public Map<String, SpawnData> groupSpawns() {
			return this.groupSpawns;
		}

		public Map<UUID, SpawnData> playerSpawns() {
			return this.playerSpawns;
		}
	}
}
