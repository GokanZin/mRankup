package br.com.gokan.mrankup.repository;

import br.com.gokan.mrankup.manager.ConfigManager;
import br.com.gokan.mrankup.repository.DatabaseManager;
import br.com.gokan.mrankup.repository.impl.MySQLDatabaseManager;
import br.com.gokan.mrankup.repository.impl.SQLiteDatabaseManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

public class PersistenceManager {

    @Setter(lombok.AccessLevel.NONE)
    @Getter
    private DatabaseManager databaseManager;
    private FileConfiguration config;

    public PersistenceManager(ConfigManager manager) {
        this.config = manager.getConfig();
        loadDatabase();
    }

    private static final String DATABASE_TYPE_KEY = "database.type";
    private static final String SQLITE_PATH_KEY = "database.sqlite.path";
    private static final String MYSQL_HOST_KEY = "database.mysql.host";
    private static final String MYSQL_PORT_KEY = "database.mysql.port";
    private static final String MYSQL_DATABASE_KEY = "database.mysql.database";
    private static final String MYSQL_USERNAME_KEY = "database.mysql.username";
    private static final String MYSQL_PASSWORD_KEY = "database.mysql.password";
    void loadDatabase() {
        String databaseType = config.getString(DATABASE_TYPE_KEY, "sqlite").toLowerCase();
        try {
            if ("mysql".equalsIgnoreCase(databaseType)) {
                connectMysql();
            } else if ("sqlite".equalsIgnoreCase(databaseType)) {
                connectSQLite();
            } else {
                throw new IllegalArgumentException("Tipo de banco de dados inválido: " + databaseType);
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados. Fallback para SQLite.");
            e.printStackTrace();
            connectSQLite();
        }
    }


    private void connectSQLite() {
        String path = config.getString(SQLITE_PATH_KEY, "database.sqlite");
        databaseManager = new SQLiteDatabaseManager(path);
        System.out.println("Conectado ao SQLite no caminho: " + path);
    }


    private void connectMysql() throws Exception {
        databaseManager = new MySQLDatabaseManager(
                config.getString(MYSQL_HOST_KEY),
                config.getInt(MYSQL_PORT_KEY),
                config.getString(MYSQL_DATABASE_KEY),
                config.getString(MYSQL_USERNAME_KEY),
                config.getString(MYSQL_PASSWORD_KEY)
        );
        databaseManager.connect();
        System.out.println("Conectado ao MySQL.");
    }
}
