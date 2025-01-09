package br.com.gokan.mrankup.api;

import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.handler.RankupHandler;
import br.com.gokan.mrankup.manager.ConfigManager;
import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.services.RankServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RankAPI {


    Main main;
    @Getter
    RankServices rankServices;
    RankupHandler rankupHandler;
    RankManager rankManager;
    ConfigManager configManager;


    public RankAPI(Main main,  RankManager rankManager, ConfigManager configManager) {
        this.main = main;
        this.rankManager = rankManager;
        this.configManager = configManager;
        this.rankServices = new RankServices(rankManager);
    }








}
