package br.com.gokan.mrankup.cache;

import br.com.gokan.mrankup.model.user.PlayerRank;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class PlayerRankCache {

    private HashMap<OfflinePlayer, PlayerRank> playerRanks = new HashMap<>();

    public PlayerRank getPlayerRank(OfflinePlayer player) {
        return playerRanks.get(player);
    }

    public void setPlayerRank(OfflinePlayer player, PlayerRank playerRank) {
        playerRanks.put(player, playerRank);
    }

    public void removePlayerRank(OfflinePlayer player) {
        playerRanks.remove(player);
    }


}
