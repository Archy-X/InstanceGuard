package com.archyx.instanceguard.commands;

import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class WandCommand extends Command {

    public WandCommand() {
        super("/wand");

        addSyntax(((sender, context) -> {
            if (sender.isPlayer()) {
                Player player = sender.asPlayer();
                if (player.getInventory().addItemStack(new ItemStack(Material.WOODEN_AXE, (byte) 1))) {
                    player.sendMessage(ChatColor.DARK_CYAN + "You were given the wand");
                } else {
                    player.sendMessage(ChatColor.YELLOW + "Your inventory is full!");
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Only players can execute this command!");
            }
        }));
    }
}
