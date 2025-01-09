package br.com.gokan.mrankup.repository;

import br.com.gokan.mrankup.model.ranks.Rank;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DatabaseManager {

    void connect() throws Exception;
    void saveData(OfflinePlayer player, Rank rank);
    Rank getData(OfflinePlayer player);
    void deleteData(OfflinePlayer player);
    void close();
    void updateData(OfflinePlayer player, Rank rank);
    void saveAllData();
    List<Rank> getAllDatarRank();
    Map<UUID, Rank> getAllDataRankMap();

}
