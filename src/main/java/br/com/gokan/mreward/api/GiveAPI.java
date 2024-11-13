package br.com.gokan.mreward.api;

import br.com.gokan.mreward.Main;
import br.com.gokan.mreward.manager.RewardManager;
import br.com.gokan.mreward.services.RewardServices;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GiveAPI {

    private Main main;
    public RewardManager rewardmanager;

    private RewardServices rewardServices;
    static FileConfiguration rewardConfig;

    public GiveAPI(Main main){
        this.main = main;
        reloadAll();
        loadClass();
        rewardmanager.load(Bukkit.getConsoleSender());
    }


    public void reloadAll(){
        File tags = new File(main.getDataFolder(), "rewards.yml");
        if (!tags.exists()){
            main.saveResource("rewards.yml", false);
        }
        rewardConfig = YamlConfiguration.loadConfiguration(tags);
    }


    public static FileConfiguration getRewardConfig() {
        return rewardConfig;
    }

    void loadClass(){
        this.rewardmanager = new RewardManager();
        this.rewardServices = new RewardServices(rewardmanager);
    }

    public RewardServices getRewardServices() {
        return rewardServices;
    }
}
