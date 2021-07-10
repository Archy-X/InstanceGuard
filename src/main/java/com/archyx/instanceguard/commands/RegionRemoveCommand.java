package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.region.RegionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public class RegionRemoveCommand extends Command {

    public RegionRemoveCommand(InstanceGuard extension) {
        super("remove");

        var idArgument = ArgumentType.String("id")
                .setSuggestionCallback(((sender, context, suggestion) -> RegionCommand.pathRegionSuggestion(sender, context, suggestion, extension)));

        setDefaultExecutor(this::usage);

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(Component.text("Only players can execute this command!", NamedTextColor.YELLOW));
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(Component.text("There are no chunks loaded in this instance!", NamedTextColor.YELLOW));
                return;
            }

            if (regionManager.getRegion(id) != null) {
                regionManager.removeRegion(id);
                player.sendMessage(Component.text("Removed region " + id, NamedTextColor.DARK_AQUA));
            } else {
                player.sendMessage(Component.text("There is no region with id " + id + " in this instance", NamedTextColor.YELLOW));
            }
        }, idArgument);
    }

    private void usage(CommandSender sender, CommandContext context) {
        sender.sendMessage(Component.text("Usage: ", NamedTextColor.YELLOW)
                .append(Component.text("/region remove <id>", NamedTextColor.WHITE)));
    }

}
