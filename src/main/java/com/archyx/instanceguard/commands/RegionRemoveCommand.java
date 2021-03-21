package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public class RegionRemoveCommand extends Command {

    public RegionRemoveCommand(InstanceGuard extension) {
        super("remove");

        var idArgument = ArgumentType.String("id")
                .setSuggestionCallback(((sender, context, suggestion) -> RegionCommand.pathRegionSuggestion(sender, context, suggestion, extension)));

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(ChatColor.YELLOW + "Only players can execute this command!");
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(ChatColor.YELLOW + "There are no chunks loaded in this instance!");
                return;
            }

            if (regionManager.getRegion(id) != null) {
                regionManager.removeRegion(id);
                player.sendMessage(ChatColor.DARK_CYAN + "Removed region " + id);
            } else {
                player.sendMessage(ChatColor.YELLOW + "There is no region with id " + id + " in this instance");
            }
        }, idArgument);
    }
}
