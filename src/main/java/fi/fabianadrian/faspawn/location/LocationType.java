package fi.fabianadrian.faspawn.location;

public enum LocationType {
	SPAWN("spawn-location"),
	FIRST_SPAWN("first-spawn-location"),
	RESPAWN("respawn-location");

	public final String configurationKey;

	LocationType(String configurationKey) {
		this.configurationKey = configurationKey;
	}
}
