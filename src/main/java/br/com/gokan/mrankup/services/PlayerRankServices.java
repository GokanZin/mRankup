package br.com.gokan.mrankup.services;

import br.com.gokan.mrankup.manager.PlayerRankManager;
import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PlayerRankServices {

    RankServices rankServices;
    PlayerRankManager playerRank;

    public boolean setPlayerRank(OfflinePlayer player, Rank rank){
        return playerRank.changeRank(player, rank);
    }

    /**
     * Retorna o rank atual do jogador.
     * @param player o jogador cujo rank ser  retornado
     * @return o rank atual do jogador
     */
    public Rank getRank(OfflinePlayer player){
        int id = playerRank.getPlayerRank(player).getRankid();
        return rankServices.getRank(id);
    }

    /**
     * Retorna o rank posterior ao atual do jogador.
     * @param player o jogador cujo rank posterior ser  retornado
     * @return o rank posterior ao atual do jogador
     */
    public Rank getNextRank(OfflinePlayer player){
        return rankServices.getNextRank(getRank(player).getId());
    }


    /**
     * Retorna o rank anterior ao atual do jogador.
     * @param player o jogador cujo rank anterior ser  retornado
     * @return o rank anterior ao atual do jogador
     */
    public Rank getPrevRank(OfflinePlayer player){
        return rankServices.getPreviousRank(getRank(player).getId());
    }

    /**
     * Retorna uma lista com os jogadores mais bem ranqueados.
     * @param top a quantidade de jogadores que sero retornados
     * @return uma lista com os jogadores mais bem ranqueados
     */
    public List<PlayerRank> getTopRanked(int top) {
        return playerRank.getAllRankDatabase().stream()
                .sorted(Comparator.comparingInt(PlayerRank::getRankid).reversed())
                .limit(top)
                .collect(Collectors.toList());
    }

    /**
     * Retorna a posicao do jogador na lista de ranking.
     * @param player o jogador cuja posicao ser retornada
     * @return a posicao do jogador na lista de ranking
     */
    public int getTopRankedUser(Player player) {
        List<PlayerRank> sortedRanks = playerRank.getAllRankDatabase().stream()
                .sorted(Comparator.comparingInt(PlayerRank::getRankid).reversed())
                .collect(Collectors.toList());
        for (int i = 0; i < sortedRanks.size(); i++) {
            if (sortedRanks.get(i).getPlayer().equals(player)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Verifica se o jogador ja  esta  no rank m ximo.
     * @param player o jogador a ser verificado
     * @return true se o jogador ja  esta  no rank maximo, false caso contrario
     */
    public boolean isMaxRank(Player player){
        return getRank(player).getId() >= rankServices.getMaxRank().getId();
    }

    private void clearCachePlayer(Player player) {
        playerRank.removeCache(player);
    }
    public void clearCache(){
        playerRank.clearCache();
    }

}
