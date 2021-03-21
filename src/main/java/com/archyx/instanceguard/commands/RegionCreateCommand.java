package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.player.PlayerData;
import com.archyx.instanceguard.region.CuboidRegion;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;

public class RegionCreateCommand extends Command {

    public RegionCreateCommand(InstanceGuard extension) {
        super("create");

        ArgumentString idArgument = ArgumentType.String("id");

        setDefaultExecutor(this::usage);

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(ChatColor.YELLOW + "Only players can execute this command!");
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);
            PlayerData playerData = extension.getPlayerManager().getPlayerData(player);

            BlockPosition firstPosition = playerData.getFirstPosition();
            BlockPosition secondPosition = playerData.getSecondPosition();
            if (firstPosition == null || secondPosition == null) {
                player.sendMessage(ChatColor.YELLOW + "You do not have a region selected, use the wand to create a selection");
                return;
            }

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(ChatColor.YELLOW + "There are no chunks loaded in this instance!");
                return;
            }

            if (regionManager.getRegion(id) != null) {
                player.sendMessage(ChatColor.YELLOW + "There is already a region in this instance with the id " + id);
                return;
            }

            int tempX;
            if (secondPosition.getX() < firstPosition.getX()) {
                tempX = secondPosition.getX();
                secondPosition.setX(firstPosition.getX());
                firstPosition.setX(tempX);
            }
            int tempY;
            if (secondPosition.getY() < firstPosition.getY()) {
                tempY = secondPosition.getY();
                secondPosition.setY(firstPosition.getY());
                firstPosition.setY(tempY);
            }
            int tempZ;
            if (secondPosition.getZ() < firstPosition.getZ()) {
                tempZ = secondPosition.getZ();
                secondPosition.setZ(firstPosition.getZ());
                firstPosition.setZ(tempZ);
            }
            regionManager.addRegion(new CuboidRegion(id, firstPosition, secondPosition));
            player.sendMessage(ChatColor.DARK_CYAN + "Successfully created region with id " + id);
        }, idArgument);
    }

    private void usage(CommandSender sender, CommandContext context) {
        sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.WHITE + "/region create <id>");
    }

}
