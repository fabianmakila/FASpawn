package fi.fabianadrian.faspawn.listener;

import fi.fabianadrian.faspawn.FASpawn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public final class ServerListener implements Listener {
	private final FASpawn plugin;

	public ServerListener(FASpawn plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerLoad(ServerLoadEvent event) {
		this.plugin.configurationManager().reload();
	}
}
