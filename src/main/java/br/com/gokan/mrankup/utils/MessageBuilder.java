package br.com.gokan.mrankup.utils;

import br.com.gokan.mrankup.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

    public class MessageBuilder {

        private String message;
        private final List<String> messages;
        private final String type;
        private final String sound;

        public MessageBuilder(String key) {
            FileConfiguration config = Main.getRankAPI().getConfigManager().getMessages();
            if (config.isString(key + ".message")) {
                this.message = config.getString(key + ".message");
                this.messages = null;
            } else if (config.isList(key + ".message")) {
                this.messages = config.getStringList(key + ".message");
                this.message = null;
            } else {
                this.message = null;
                this.messages = null;
            }
            this.type = config.getString(key + ".type", "chat");
            this.sound = config.getString(key + ".sound", null);
        }

        public MessageBuilder replace(String target, String replacement) {
            if (isSingleMessage() && message != null) {
                message = message.replace(target, replacement);
            } else if (messages != null) {
                for (int i = 0; i < messages.size(); i++) {
                    messages.set(i, messages.get(i).replace(target, replacement));
                }
            }
            return this;
        }

        public void sendTo(CommandSender sender) {
            if (sender == null) {
                return;
            }
            if (isSingleMessage()) {
                sendMessage(sender, message);
            } else if (messages != null) {
                for (String line : messages) {
                    sendMessage(sender, line);
                }
            }

            if (hasSound() && sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }
        }

        private void sendMessage(CommandSender sender, String msg) {
            String[] types = type.split(",");
            for (String type : types) {
                switch (type.trim().toLowerCase()) {
                    case "chat":
                        sender.sendMessage(msg);
                        break;
                    case "actionbar":
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            player.spigot().sendMessage(
                                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                                    new net.md_5.bungee.api.chat.TextComponent(msg)
                            );
                        }
                        break;
                    case "title":
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            player.sendTitle(msg, "", 10, 70, 20);
                        }
                        break;
                    case "subtitle":
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            player.sendTitle("", msg, 10, 70, 20);
                        }
                        break;
                    case "broadcast":
                        Bukkit.broadcastMessage(msg);
                        break;
                    default:
                        sender.sendMessage(msg);
                        break;
                }
            }
        }

        public boolean isSingleMessage() {
            return message != null;
        }

        public boolean hasSound() {
            return sound != null && !sound.equalsIgnoreCase("null");
        }
    }