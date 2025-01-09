package br.com.gokan.mrankup.api;

import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.handler.RankupHandler;
import br.com.gokan.mrankup.manager.ConfigManager;
import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.services.RankServices;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class RankAPI {


    Main main;
    RankManager rankManager;
    @Getter
    ConfigManager configManager;
    @Getter
    RankServices rankServices;
    RankupHandler rankupHandler;


    public RankAPI(Main main) {
        this.main = main;
    }




}
