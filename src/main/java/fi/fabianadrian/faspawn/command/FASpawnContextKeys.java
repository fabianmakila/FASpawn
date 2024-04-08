package fi.fabianadrian.faspawn.command;

import fi.fabianadrian.faspawn.FASpawn;
import org.incendo.cloud.key.CloudKey;

public class FASpawnContextKeys {
	public static final CloudKey<FASpawn> PLUGIN = CloudKey.cloudKey("FASpawn", FASpawn.class);
}
