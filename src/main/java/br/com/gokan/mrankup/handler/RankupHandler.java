package br.com.gokan.mrankup.handler;

import br.com.gokan.mrankup.events.custom.PlayerRankChangeEvent;
import br.com.gokan.mrankup.model.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RankupHandler {

    public boolean changeRank(Player player, Rank newRank) {
        PlayerRank playerData = DataManager.getInstance().getPlayerData(player);
        Rank currentRank = playerData.getCurrentRank();
        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, newRank.getId(), newRank, playerData);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        playerData.setCurrentRank(newRank);
        DataManager.getInstance().savePlayerData(playerData);
        player.sendMessage("§aVocê agora está no rank: " + newRank.getName());
        return true;
    }

    public boolean changeRank(OfflinePlayer player, Rank newRank) {
        PlayerRank playerData = DataManager.getInstance().getPlayerData(player);
        Rank currentRank = playerData.getCurrentRank();
        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, newRank.getId(), newRank, playerData);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        playerData.setCurrentRank(newRank);
        DataManager.getInstance().savePlayerData(playerData);
        player.sendMessage("§aVocê agora está no rank: " + newRank.getName());
        return true;
    }

}
