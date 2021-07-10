package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.region.Region;
import com.archyx.instanceguard.region.RegionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.util.Locale;

public class RegionFlagUnsetCommand extends Command {

    public RegionFlagUnsetCommand(InstanceGuard extension) {
        super("unset");

        var idArgument = ArgumentType.String("id").setSuggestionCallback(((sender, context, suggestion) -> RegionCommand.pathRegionSuggestion(sender, context, suggestion, extension)));
        var flagArgument = ArgumentType.String("flag").setSuggestionCallback(RegionCommand::pathFlagSuggestion);

        setDefaultExecutor(this::usage);

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(Component.text("Only players can execute this command!", NamedTextColor.YELLOW));
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);
            String flag = context.get(flagArgument);

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
                if (region.hasFlag(flagType)) {
                    region.removeFlag(flagType);
                    player.sendMessage(Component.text("Unset flag " + flag.toLowerCase(Locale.ROOT) + " in region " + id, NamedTextColor.DARK_AQUA));
                } else {
                    player.sendMessage(Component.text("Region does not have flag of type " + flagType.toString().toLowerCase(Locale.ROOT) + " set", NamedTextColor.YELLOW));
                }
            } catch (IllegalArgumentException e) {
                player.sendMessage(Component.text(flag + " is not a valid flag, valid flags are: " + FlagType.getValueList(), NamedTextColor.YELLOW));
            }
        }, idArgument, flagArgument);
    }

    private void usage(CommandSender sender, CommandContext context) {
        sender.sendMessage(Component.text("Usage: ", NamedTextColor.YELLOW)
                .append(Component.text("/region flag unset <id> <flag>", NamedTextColor.WHITE)));
    }

}
