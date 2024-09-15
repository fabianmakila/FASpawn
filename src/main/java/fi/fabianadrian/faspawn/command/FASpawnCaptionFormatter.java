package fi.fabianadrian.faspawn.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;
import org.incendo.cloud.minecraft.extras.caption.RichVariable;

import java.util.List;
import java.util.stream.Collectors;

public final class FASpawnCaptionFormatter implements ComponentCaptionFormatter<CommandSender> {
	@Override
	public @NonNull Component formatCaption(@NonNull Caption captionKey, @NonNull CommandSender recipient, @NonNull String caption, @NonNull List<@NonNull CaptionVariable> variables) {
		return Component.translatable("cloud." + captionKey.key(), variables.stream().map(variable -> {
			if (variable instanceof RichVariable) {
				return (RichVariable) variable;
			} else {
				return Component.text(variable.value());
			}
		}).collect(Collectors.toList()));
	}
}
