package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.region.Region;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.util.Arrays;
import java.util.Locale;

public class RegionFlagUnsetCommand extends Command {

    public RegionFlagUnsetCommand(InstanceGuard extension) {
        super("unset");

        var idArgument = ArgumentType.String("id").setSuggestionCallback(((sender, context, suggestion) -> RegionCommand.pathRegionSuggestion(sender, context, suggestion, extension)));
        var flagArgument = ArgumentType.String("flag").setSuggestionCallback(RegionCommand::pathFlagSuggestion);

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(ChatColor.YELLOW + "Only players can execute this command!");
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);
            String flag = context.get(flagArgument);

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(ChatColor.YELLOW + "There are no chunks loaded in this instance!");
                return;
            }

            Region region = regionManager.getRegion(id);
            if (region == null) {
                player.sendMessage(ChatColor.YELLOW + "There is no region with id " + id + " in your instance!");
                return;
            }

            try {
                FlagType flagType = FlagType.valueOf(flag.toUpperCase(Locale.ROOT));
                region.removeFlag(flagType);
                player.sendMessage(ChatColor.DARK_CYAN + "Unset flag " + flag.toLowerCase(Locale.ROOT) + " in region " + id);
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.YELLOW + flag + " is not a valid flag, valid flags are: " + Arrays.toString(FlagType.values()));
            }
        }, idArgument, flagArgument);
    }

}
