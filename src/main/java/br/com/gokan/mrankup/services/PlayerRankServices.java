package br.com.gokan.mrankup.services;

import br.com.gokan.mrankup.cache.PlayerRankCache;
import br.com.gokan.mrankup.handler.RankupHandler;
import br.com.gokan.mrankup.model.ranks.Rank;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class PlayerRankServices {

    RankServices rankServices;
    RankupHandler rankupHandler;
    PlayerRankCache playerRankCache;

    public void setRank(int id, OfflinePlayer player){
        rankupHandler.changeRank(player, rankServices.getRank(id));
    }

    public void nextRank(OfflinePlayer player){
        int id = playerRankCache.getPlayerRank(player).getRankid();
        rankupHandler.changeRank(player, rankServices.getNextRank(id));
    }

    public void prevRank(OfflinePlayer player){
        int id = playerRankCache.getPlayerRank(player).getRankid();
        rankupHandler.changeRank(player, rankServices.getPreviousRank(id));
    }

    public Rank getRank(OfflinePlayer player){
        int id = playerRankCache.getPlayerRank(player).getRankid();
        return rankServices.getRank(id);
    }

    public Rank getNextRank(OfflinePlayer player){
        return rankServices.getNextRank(getRank(player).getId());
    }


    public Rank getPrevRank(OfflinePlayer player){
        return rankServices.getPreviousRank(getRank(player).getId());
    }
}
