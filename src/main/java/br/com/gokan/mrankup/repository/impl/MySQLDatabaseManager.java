package br.com.gokan.mrankup.repository.impl;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import br.com.gokan.mrankup.repository.DatabaseManager;
import com.mysql.jdbc.Connection;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class MySQLDatabaseManager implements DatabaseManager {

    @Setter(lombok.AccessLevel.NONE)
    private Connection connection;

    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;

    public MySQLDatabaseManager(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }


    @Override
    public void connect() throws Exception {

    }

    @Override
    public void saveData(OfflinePlayer player, Rank rank) {

    }

    @Override
    public void saveData(UUID player, Rank rank) {

    }

    @Override
    public CompletableFuture<Void> saveData(UUID player, int id) {

        return null;
    }

    @Override
    public CompletableFuture<PlayerRank> getData(OfflinePlayer player) {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteData(OfflinePlayer player) {

        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public CompletableFuture<Void> updateData(OfflinePlayer player, Rank rank) {

        return null;
    }

    @Override
    public CompletableFuture<Void> saveAllData(List<PlayerRank> dataList) {
        return null;
    }

    @Override
    public CompletableFuture<List<PlayerRank>> getAllDataRank() {
        return null;
    }


    @Override
    public Map<UUID, PlayerRank> getAllDataRankMap() {
        return null;
    }
}
