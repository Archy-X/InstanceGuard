package com.archyx.instanceguard.listeners;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.player.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;

public class BlockListener extends InstanceGuardListener {

    public BlockListener(InstanceGuard extension, Instance instance) {
        super(extension, instance);
        init();
    }

    private void init() {
        EventNode<PlayerEvent> node = EventNode.type("BlockListener", EventFilter.PLAYER, (event, player) -> instance.equals(player.getInstance()));
        extension.getEventNode().addChild(node);
        node.addListener(PlayerBlockBreakEvent.class, event -> {
            BlockPosition blockPosition = event.getBlockPosition();
            Player player = event.getPlayer();
            if (cancelIfFalse(event, blockPosition.toPosition(), FlagType.BLOCK_BREAK)) {
                player.sendMessage(Component.text("Sorry, but you can't break that block here", NamedTextColor.RED));
            }
            // Wand behavior
            if (player.getItemInMainHand().getMaterial() == Material.WOODEN_AXE) {
                PlayerData playerData = extension.getPlayerManager().getPlayerData(player);
                playerData.setFirstPosition(blockPosition);
                player.sendMessage(Component.text("First position set to " + blockPosition.getX() + ", " + blockPosition.getY() + ", " + blockPosition.getZ(), NamedTextColor.DARK_AQUA));
                event.setCancelled(true);
            }
        });
        node.addListener(PlayerBlockPlaceEvent.class, event -> {
            if (cancelIfFalse(event, event.getBlockPosition().toPosition(), FlagType.BLOCK_PLACE)) {
                event.getPlayer().sendMessage(Component.text("Sorry, but you can't place that block here", NamedTextColor.RED));
            }
        });
        node.addListener(PlayerBlockInteractEvent.class, event -> {
            BlockPosition blockPosition = event.getBlockPosition();
            Player player = event.getPlayer();
            if (cancelIfFalse(event, blockPosition.toPosition(), FlagType.BLOCK_INTERACT)) {
                player.sendMessage(Component.text("Sorry, but you can't use that block here", NamedTextColor.RED));
            }
            // Wand behavior
            if (player.getItemInMainHand().getMaterial() == Material.WOODEN_AXE) {
                PlayerData playerData = extension.getPlayerManager().getPlayerData(player);
                playerData.setSecondPosition(blockPosition);
                player.sendMessage(Component.text("Second position set to " + blockPosition.getX() + ", " + blockPosition.getY() + ", " + blockPosition.getZ(), NamedTextColor.DARK_AQUA));
                event.setCancelled(true);
            }
        });
    }

}
