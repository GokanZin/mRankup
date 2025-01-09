package br.com.gokan.mrankup.repository.impl;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.repository.DatabaseManager;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MySQLDatabaseManager implements DatabaseManager {
    @Override
    public void connect() throws Exception {

    }

    @Override
    public void saveData(OfflinePlayer player, Rank rank) {

    }

    @Override
    public Rank getData(OfflinePlayer player) {
        return null;
    }

    @Override
    public void deleteData(OfflinePlayer player) {

    }

    @Override
    public void close() {

    }

    @Override
    public void updateData(OfflinePlayer player, Rank rank) {

    }

    @Override
    public void saveAllData() {

    }

    @Override
    public List<Rank> getAllDatarRank() {
        return List.of();
    }

    @Override
    public Map<UUID, Rank> getAllDataRankMap() {
        return Map.of();
    }
}
