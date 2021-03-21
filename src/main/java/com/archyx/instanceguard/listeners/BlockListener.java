package com.archyx.instanceguard.listeners;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.player.PlayerData;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;

public class BlockListener extends InstanceGuardListener {

    public BlockListener(InstanceGuard extension, Instance instance) {
        super(extension, instance);
        init();
    }

    private void init() {
        instance.addEventCallback(PlayerBlockBreakEvent.class, event -> {
            BlockPosition blockPosition = event.getBlockPosition();
            Player player = event.getPlayer();
            if (cancelIfFalse(event, blockPosition.toPosition(), FlagType.BLOCK_BREAK)) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't break that block here");
            }
            // Wand behavior
            if (player.getItemInMainHand().getMaterial() == Material.WOODEN_AXE) {
                PlayerData playerData = extension.getPlayerManager().getPlayerData(player);
                playerData.setFirstPosition(blockPosition);
                player.sendMessage(ChatColor.DARK_CYAN + "First position set to " + blockPosition.getX() + ", " + blockPosition.getY() + ", " + blockPosition.getZ());
                event.setCancelled(true);
            }
        });
        instance.addEventCallback(PlayerBlockPlaceEvent.class, event -> {
            if (cancelIfFalse(event, event.getBlockPosition().toPosition(), FlagType.BLOCK_PLACE)) {
                event.getPlayer().sendMessage(ChatColor.RED + "Sorry, but you can't place that block here");
            }
        });
        instance.addEventCallback(PlayerBlockInteractEvent.class, event -> {
            BlockPosition blockPosition = event.getBlockPosition();
            Player player = event.getPlayer();
            if (cancelIfFalse(event, blockPosition.toPosition(), FlagType.BLOCK_INTERACT)) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't use that block here");
            }
            // Wand behavior
            if (player.getItemInMainHand().getMaterial() == Material.WOODEN_AXE) {
                PlayerData playerData = extension.getPlayerManager().getPlayerData(player);
                playerData.setSecondPosition(blockPosition);
                player.sendMessage(ChatColor.DARK_CYAN + "Second position set to " + blockPosition.getX() + ", " + blockPosition.getY() + ", " + blockPosition.getZ());
                event.setCancelled(true);
            }
        });
    }

}
