package com.archyx.instanceguard.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                if (player.getInventory().addItemStack(ItemStack.of(Material.WOODEN_AXE))) {
                    player.sendMessage(Component.text("You were given the wand", NamedTextColor.DARK_AQUA));
                } else {
                    player.sendMessage(Component.text("Your inventory is full!", NamedTextColor.YELLOW));
                }
            } else {
                sender.sendMessage(Component.text("Only players can execute this command!", NamedTextColor.YELLOW));
            }
        }));
    }
}
