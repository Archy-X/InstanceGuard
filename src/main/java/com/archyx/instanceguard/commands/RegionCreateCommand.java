package com.archyx.instanceguard.commands;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.player.PlayerData;
import com.archyx.instanceguard.region.CuboidRegion;
import com.archyx.instanceguard.region.RegionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                sender.sendMessage(Component.text("Only players can execute this command!", NamedTextColor.YELLOW));
                return;
            }

            Player player = sender.asPlayer();
            String id = context.get(idArgument);
            PlayerData playerData = extension.getPlayerManager().getPlayerData(player);

            BlockPosition firstPosition = playerData.getFirstPosition();
            BlockPosition secondPosition = playerData.getSecondPosition();
            if (firstPosition == null || secondPosition == null) {
                player.sendMessage(Component.text("You do not have a region selected, use the wand to create a selection", NamedTextColor.YELLOW));
                return;
            }

            Instance instance = player.getInstance();
            RegionManager regionManager = extension.getRegionManager(instance);
            if (regionManager == null) {
                player.sendMessage(Component.text("There are no chunks loaded in this instance!", NamedTextColor.YELLOW));
                return;
            }

            if (regionManager.getRegion(id) != null) {
                player.sendMessage(Component.text("There is already a region in this instance with the id " + id, NamedTextColor.YELLOW));
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
            player.sendMessage(Component.text("Successfully created region with id " + id, NamedTextColor.DARK_AQUA));
        }, idArgument);
    }

    private void usage(CommandSender sender, CommandContext context) {
        sender.sendMessage(Component.text("Usage: ", NamedTextColor.WHITE)
                .append(Component.text("/region create <id>", NamedTextColor.YELLOW)));
    }

}
