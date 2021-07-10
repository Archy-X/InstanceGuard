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
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.util.Locale;

public class RegionCommand extends Command {

    public RegionCommand(InstanceGuard extension) {
        super("region", "rg");

        setDefaultExecutor(((sender, context) -> RegionCommand.subCommandUsage(sender, this)));
        addSubcommand(new RegionCreateCommand(extension));
        addSubcommand(new RegionFlagCommand(extension));
        addSubcommand(new RegionRemoveCommand(extension));
    }

    public static void subCommandUsage(CommandSender sender, Command command) {
        StringBuilder subCommandList = new StringBuilder();
        for (Command subCommand : command.getSubcommands()) {
            subCommandList.append(subCommand.getName()).append(", ");
        }
        subCommandList.delete(subCommandList.length() - 2, subCommandList.length());
        sender.sendMessage(Component.text("Unknown command, valid subcommands: ", NamedTextColor.YELLOW)
                .append(Component.text(subCommandList.toString(), NamedTextColor.WHITE)));
    }

    public static void pathRegionSuggestion(CommandSender sender, CommandContext context, Suggestion suggestion, InstanceGuard extension) {
        if (sender.isPlayer()) {
            Player player = sender.asPlayer();
            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager != null) {
                for (Region region : regionManager.getRegionMap().values()) {
                    String[] split = context.getInput().split(" ");
                    String last = split[split.length - 1];
                    if (region.getId().startsWith(last) || context.getInput().endsWith(" ")) {
                        suggestion.addEntry(new SuggestionEntry(region.getId()));
                    }
                }
            }
        }
    }

    public static void pathFlagSuggestion(CommandSender sender, CommandContext context, Suggestion suggestion) {
        for (FlagType flagType : FlagType.values()) {
            String[] split = context.getInput().split(" ");
            String last = split[split.length - 1];
            if (flagType.toString().startsWith(last.toUpperCase(Locale.ROOT)) || context.getInput().endsWith(" ")) {
                suggestion.addEntry(new SuggestionEntry(flagType.toString().toLowerCase(Locale.ROOT)));
            }
        }
    }

}
