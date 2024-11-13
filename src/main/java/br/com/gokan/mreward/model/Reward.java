package br.com.gokan.mreward.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Reward {

    List<String> cmd = new ArrayList<>();
    String message;
    double chance;
    int pos;

    public Reward(List<String> cmd, String message, double chance, int pos) {
        this.cmd = cmd;
        this.message = message;
        this.chance = chance;
        this.pos = pos;
    }

    public List<String> getCmd() {
        return cmd;
    }

    public int getPos() {
        return pos;
    }

    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }

    public String getMessage() {
        return message;
    }

    public double getChance() {
        return chance;
    }

    public void executeCmd(Player player){
        if (!cmd.isEmpty() && cmd != null){
            for (String s : cmd) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", player.getName()));
            }
        }
        sendMessage(player);
    }

    public void sendMessage(Player player){
        if (!message.isEmpty() && message != null){
            player.sendMessage(message.replace("&", "§"));
        }
    }
}
