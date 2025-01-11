package br.com.gokan.mrankup.repository;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DatabaseManager {

    void connect() throws Exception;
    void saveData(OfflinePlayer player, Rank rank);
    void saveData(UUID player, Rank rank);
    CompletableFuture<Void> saveData(UUID player, int id);
    CompletableFuture<PlayerRank> getData(OfflinePlayer player);
    CompletableFuture<Void> deleteData(OfflinePlayer player);
    void close();
    CompletableFuture<Void> updateData(OfflinePlayer player, Rank rank);
    CompletableFuture<Void> saveAllData(List<PlayerRank> dataList);
    CompletableFuture<List<PlayerRank>> getAllDataRank();
    Map<UUID, PlayerRank> getAllDataRankMap();

}
