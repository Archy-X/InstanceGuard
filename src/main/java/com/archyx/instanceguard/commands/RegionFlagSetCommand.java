package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.flag.FlagValue;
import com.archyx.instanceguard.region.Region;
import com.archyx.instanceguard.region.RegionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentBoolean;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.util.Locale;

public class RegionFlagSetCommand extends Command {

    public RegionFlagSetCommand(InstanceGuard extension) {
        super("set");

        var idArgument = ArgumentType.String("id")
                .setSuggestionCallback(((sender, context, suggestion) -> RegionCommand.pathRegionSuggestion(sender, context, suggestion, extension)));
        var flagArgument = ArgumentType.String("flag").setSuggestionCallback(RegionCommand::pathFlagSuggestion);
        ArgumentBoolean valueArgument = ArgumentType.Boolean("value");

        setDefaultExecutor(this::usage);

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(Component.text("Only players can execute this command!", NamedTextColor.YELLOW));
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);
            String flag = context.get(flagArgument);
            boolean value = context.get(valueArgument);

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(Component.text("There are no chunks loaded in this instance!", NamedTextColor.YELLOW));
                return;
            }

            Region region = regionManager.getRegion(id);
            if (region == null) {
                player.sendMessage(Component.text("There is no region with id " + id + " in your instance!", NamedTextColor.YELLOW));
                return;
            }

            try {
                FlagType flagType = FlagType.valueOf(flag.toUpperCase(Locale.ROOT));
                region.setFlag(flagType, new FlagValue(value));
                player.sendMessage(Component.text("Set flag " + flag.toLowerCase(Locale.ROOT) + " in region " + id + " to " + value, NamedTextColor.DARK_AQUA));
            } catch (IllegalArgumentException e) {
                player.sendMessage(Component.text(flag + " is not a valid flag, valid flags are: " + FlagType.getValueList(), NamedTextColor.YELLOW));
            }
        }, idArgument, flagArgument, valueArgument);
    }

    private void usage(CommandSender sender, CommandContext context) {
        sender.sendMessage(Component.text("Usage: ", NamedTextColor.YELLOW)
                .append(Component.text("/region flag set <id> <flag> <value>", NamedTextColor.WHITE)));
    }

}
