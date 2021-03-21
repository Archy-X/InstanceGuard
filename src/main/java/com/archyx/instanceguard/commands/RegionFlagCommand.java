package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import net.minestom.server.command.builder.Command;

public class RegionFlagCommand extends Command {

    public RegionFlagCommand(InstanceGuard extension) {
        super("flag");

        setDefaultExecutor(((sender, context) -> RegionCommand.subCommandUsage(sender, this)));
        addSubcommand(new RegionFlagSetCommand(extension));
        addSubcommand(new RegionFlagUnsetCommand(extension));
    }
}
