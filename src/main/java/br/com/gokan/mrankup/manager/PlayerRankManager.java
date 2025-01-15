package br.com.gokan.mrankup.manager;

import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.events.custom.PlayerRankChangeEvent;
import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import br.com.gokan.mrankup.repository.DatabaseManager;
import br.com.gokan.mrankup.services.RankServices;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PlayerRankManager {
    @Setter(lombok.AccessLevel.NONE)
    private HashMap<UUID, PlayerRank> playerRank = new HashMap<>();
    DatabaseManager databaseManager;

    public PlayerRankManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    public void load(Player player){
        if (playerRank.containsKey(player.getUniqueId())){
            Bukkit.getConsoleSender().sendMessage("Rank ja carregado");
            return;
        }
        RankServices rankServices = Main.getRankAPI().getRankServices();
        databaseManager.getData(player)
                .thenAccept(rank -> {
                    if (rank == null) {
                        setLoad(player, rankServices.getFirstRank().getId());
                        Bukkit.getConsoleSender().sendMessage("Rank nao encontrado");
                        return;
                    }
                    Bukkit.getConsoleSender().sendMessage("Rank  encontrado");
                    setLoad(player, rank.getRankid());
                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                }).join();

    }

    void setLoad(Player player, int id){
        PlayerRank playerRank = new PlayerRank(id, player);
        this.playerRank.put(player.getUniqueId(), playerRank);
        databaseManager.saveData(player.getUniqueId(), id);
    }

    public PlayerRank getPlayerRank(OfflinePlayer player){
        RankServices rankServices = Main.getRankAPI().getRankServices();
        return playerRank.getOrDefault(player.getUniqueId(), new PlayerRank(rankServices.getFirstRank().getId(), player));
    }

    public PlayerRank getPlayerRank(UUID uuid){
        RankServices rankServices = Main.getRankAPI().getRankServices();
        return playerRank.getOrDefault(uuid, new PlayerRank(rankServices.getFirstRank().getId(), null));
    }



    public List<PlayerRank> getAllRankDatabase() {
        return databaseManager.getAllDataRank().join();
    }



    public boolean changeRank(OfflinePlayer player, Rank newRank) {
        PlayerRank playerRank = getPlayerRank(player);
        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, newRank.getId(), newRank, playerRank);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        databaseManager.saveData(player.getUniqueId(), newRank.getId());
        playerRank.setRankid(newRank.getId());
        List<String> list = newRank.getComandos();
        if (list != null && !list.isEmpty()) {
            for (String s : list) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", player.getName())
                       .replace("{player}", player.getName())
                       .replace("/", ""));
            }
        }
        return true;
    }

    public void clearCache(){
        playerRank.clear();;
    }

    public void removeCache(OfflinePlayer player){
        playerRank.remove(player.getUniqueId());
    }

}
