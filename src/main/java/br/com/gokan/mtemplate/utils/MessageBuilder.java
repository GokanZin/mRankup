package br.com.gokan.mtemplate.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class MessageBuilder {

    private String message;
    private List<String> messageList;
    private Sound sound;
    public MessageBuilder(String message) {
        this.message = message;
    }

    public MessageBuilder(List<String> messageList) {
        this.messageList = messageList;
    }

    @SuppressWarnings("unchecked")
    public MessageBuilder(Object object) {
        if (object instanceof List){
            List<?> list = (List<?>) object;
            if (!list.isEmpty()){
                this.messageList = (List<String>) list;
            }
            Bukkit.getConsoleSender().sendMessage(object.toString());
        }
        if (object instanceof String){
            this.message = (String) object;
        }
    }


    public MessageBuilder setTypeSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public MessageBuilder addPlaceholder(String value, String replacement) {
        if (message != null) {
            message = message.replace("{" + value + "}", replacement);
        }
        if (messageList != null) {
            messageList.replaceAll(c -> c.replace("{" + value + "}", replacement));
        }
        return this;
    }

    public MessageBuilder addPlaceholderOther(String value, String replacement) {
        if (message != null) {
            message = message.replace(value, replacement);
        }
        if (messageList != null) {
            messageList.replaceAll(c -> c.replace(value, replacement));
        }
        return this;
    }

    public void sendToPlayer(CommandSender player) {
        if (message != null) {
            player.sendMessage(colorize(message));
        }
        if (messageList != null) {
            messageList.forEach(c -> player.sendMessage(colorize(c)));
        }
    }

    public void sendToPlayer(Player player) {
        if (message != null) {
            player.sendMessage(colorize(message));
        }
        if (messageList != null) {
            messageList.forEach(c -> player.sendMessage(colorize(c)));
        }
        if (sound != null) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    private String colorize(String message) {
        return message.replace("&", "§");
    }
}