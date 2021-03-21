package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.region.Region;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.MinecraftServer;
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

        addSubcommand(new RegionCreateCommand(extension));
        addSubcommand(new RegionFlagCommand(extension));
        addSubcommand(new RegionRemoveCommand(extension));
    }

    public static void pathRegionSuggestion(CommandSender sender, CommandContext context, Suggestion suggestion, InstanceGuard extension) {
        MinecraftServer.LOGGER.info("pathing suggestion");
        if (sender.isPlayer()) {
            Player player = sender.asPlayer();
            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager != null) {
                for (Region region : regionManager.getRegionMap().values()) {
                    if (region.getId().startsWith(context.getInput())) {
                        suggestion.addEntry(new SuggestionEntry(region.getId()));
                    }
                }
            }
        }
    }

    public static void pathFlagSuggestion(CommandSender sender, CommandContext context, Suggestion suggestion) {
        for (FlagType flagType : FlagType.values()) {
            if (flagType.toString().startsWith(context.getInput().toUpperCase(Locale.ROOT))) {
                suggestion.addEntry(new SuggestionEntry(flagType.toString().toLowerCase(Locale.ROOT)));
            }
        }
    }

}
