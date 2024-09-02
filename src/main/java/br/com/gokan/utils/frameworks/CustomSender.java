package br.com.gokan.utils.frameworks;

import org.bukkit.command.CommandSender;

public class CustomSender {
    private final CommandSender sender;

    public CustomSender( CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }
}
