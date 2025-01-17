package br.com.gokan.mrankup.manager;

import br.com.gokan.mrankup.Main;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigManager {

    private FileConfiguration config;

    private FileConfiguration rankConfig;
    FileConfiguration locations;
    private FileConfiguration messages;

    private FileConfiguration gui;

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
        File file = getFile("ranks.yml");
        if (!file.exists()) {
            main.saveResource("ranks.yml", false);
        }
        rankConfig = YamlConfiguration.loadConfiguration(file);
    }


    public void saveRankConfig() {
        File file = getFile("ranks.yml");
        try {
            rankConfig.save(file);
        } catch (IOException e) {
            main.getLogger().severe("Não foi possível salvar ranks.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void loadMessages() {
        File file = getFile("messages.yml");
        if (!file.exists()) {
            main.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }

    File getFile(String fileName) {
        File file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            main.saveResource(fileName, false);
        }
        return file;
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
