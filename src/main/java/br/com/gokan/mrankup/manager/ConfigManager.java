package br.com.gokan.mrankup.manager;

import br.com.gokan.mrankup.Main;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    @Getter
    private FileConfiguration config;

    @Getter
    private FileConfiguration rankConfig;

    private final Main main;

    public ConfigManager(Main main) {
        this.main = main;
        loadDefaultConfig();
        loadRankConfig();
    }

    private void loadDefaultConfig() {
        main.saveDefaultConfig();
        main.reloadConfig();
        config = main.getConfig();
    }

    private void loadRankConfig() {
        File file = new File(main.getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            main.saveResource("ranks.yml", false);
        }
        rankConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void saveRankConfig() {
        File file = new File(main.getDataFolder(), "ranks.yml");
        try {
            rankConfig.save(file);
        } catch (IOException e) {
            main.getLogger().severe("Não foi possível salvar ranks.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reloadAllConfigs(CommandSender sender) {
        try {
            loadDefaultConfig();
            loadRankConfig();
            sender.sendMessage("§aAs configurações foram recarregadas com sucesso.");
        } catch (Exception e) {
            sender.sendMessage("§cOcorreu um erro ao recarregar as configurações. Verifique os arquivos.");
            main.getLogger().severe("Erro ao recarregar as configurações: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
