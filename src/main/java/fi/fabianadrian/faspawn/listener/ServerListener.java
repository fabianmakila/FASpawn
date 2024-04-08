package fi.fabianadrian.faspawn.listener;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.spongepowered.configurate.ConfigurateException;

public final class ServerListener implements Listener {
	private final FASpawn plugin;

	public ServerListener(FASpawn plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerLoad(ServerLoadEvent event) {
		try {
			this.plugin.reload();
		} catch (ConfigurateException e) {
			throw new IllegalStateException("Failed to load config", e);
		}
	}
}
