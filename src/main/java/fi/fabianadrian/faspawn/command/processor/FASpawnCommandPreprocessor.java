package fi.fabianadrian.faspawn.command.processor;

import fi.fabianadrian.faspawn.FASpawn;
import fi.fabianadrian.faspawn.command.FASpawnContextKeys;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;

public class FASpawnCommandPreprocessor<C> implements CommandPreprocessor<C> {
	private final FASpawn plugin;

	public FASpawnCommandPreprocessor(FASpawn plugin) {
		this.plugin = plugin;
	}

	@Override
	public void accept(@NonNull CommandPreprocessingContext<C> context) {
		CommandContext<C> commandContext = context.commandContext();
		commandContext.store(FASpawnContextKeys.PLUGIN, this.plugin);
	}
}
