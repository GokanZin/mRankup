package br.com.gokan.mrankup.repository.impl;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import br.com.gokan.mrankup.repository.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bukkit.Bukkit.getLogger;

public class SQLiteDatabaseManager implements DatabaseManager {
    private Connection connection;
    private final String databasePath;
    private final String table = "rankupUsers";
    private final String columns = "uuid TEXT PRIMARY KEY, rank INTEGER";
    public SQLiteDatabaseManager(String databasePath) {
        this.databasePath = databasePath;

    }


    @Override
    public void connect() throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Class.forName("org.sqlite.JDBC");
            File database = new File(databasePath);
            String url = "jdbc:sqlite:" + database.getAbsolutePath();
            connection = DriverManager.getConnection(url);
            try (Statement statement = connection.createStatement()) {
                if (!connection.isClosed()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
                }
            } catch (SQLException e) {
                getLogger().severe("Erro ao criar a tabela " + table + " no banco de dados:");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //voce deve tar se perguntando, PQ? '- e eu respondo,, pq sim
    @Override
    public void saveData(OfflinePlayer player, Rank rank) {
       saveData(player.getUniqueId(), rank.getId());
    }
    //pessoa preguiçosa
    @Override
    public void saveData(UUID player, Rank rank) {
       saveData(player, rank.getId());
    }

    @Override
    public CompletableFuture<Void> saveData(UUID player, int id) {
        return CompletableFuture.runAsync(() -> {
            String sql = "INSERT OR REPLACE INTO " + table + " (uuid, rank) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, player.toString());
                preparedStatement.setInt(2, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Override
    public CompletableFuture<PlayerRank> getData(OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM " + table + " WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, player.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new PlayerRank(resultSet.getInt("rank"), player);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
    @Override
    public CompletableFuture<Void> deleteData(OfflinePlayer player) {
        return CompletableFuture.runAsync(() -> {
            String sql = "DELETE FROM " + table + " WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void close() {

    }

    @Override
    public CompletableFuture<Void> updateData(OfflinePlayer player, Rank rank) {
        return CompletableFuture.runAsync(() -> {
            String sql = "UPDATE " + table + " SET rank = ? WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, rank.getId());
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }





    @Override
    public CompletableFuture<List<PlayerRank>> getAllDataRank() {
        return CompletableFuture.supplyAsync(() -> {
            List<PlayerRank> list = new ArrayList<>();
            String sql = "SELECT * FROM " + table;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(uuid);
                        list.add(new PlayerRank(resultSet.getInt("rank"), offPlayer));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        });
    }

    @Override
    public CompletableFuture<Void> saveAllData(List<PlayerRank> dataList) {
        return CompletableFuture.runAsync(() -> {
            String sql = "INSERT OR REPLACE INTO " + table + " (uuid, rank) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (PlayerRank playerRank : dataList) {
                    preparedStatement.setString(1, playerRank.getPlayer().getUniqueId().toString());
                    preparedStatement.setInt(2, playerRank.getRankid());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public Map<UUID, PlayerRank> getAllDataRankMap() {
        CompletableFuture<List<PlayerRank>> list = getAllDataRank();
        Map<UUID, PlayerRank> map = new HashMap<>();
        try {
            for (PlayerRank playerRank : list.get()) {
                map.put(playerRank.getPlayer().getUniqueId(), playerRank);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return  map;
    }
}
