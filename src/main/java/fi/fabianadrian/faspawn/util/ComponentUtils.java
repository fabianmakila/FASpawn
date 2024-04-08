package fi.fabianadrian.faspawn.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public class ComponentUtils {

	public static Component locationComponent(Location location) {
		String content = String.format(
				"%.1f, %.1f, %.1f [%.1f, %.1f]",
				location.x(),
				location.y(),
				location.z(),
				location.getPitch(),
				location.getYaw()
				);
		return Component.text(content);
	}
}
