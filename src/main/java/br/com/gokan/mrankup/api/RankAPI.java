package br.com.gokan.mrankup.api;

import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.manager.ConfigManager;
import br.com.gokan.mrankup.manager.PlayerRankManager;
import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.services.PlayerRankServices;
import br.com.gokan.mrankup.services.RankServices;
import lombok.Getter;

public class RankAPI {


    Main main;
    @Getter
    RankServices rankServices;
    RankManager rankManager;
    @Getter
    PlayerRankServices playerRankServices;
    @Getter
    ConfigManager configManager;



    public RankAPI(Main main, RankManager rankManager, ConfigManager configManager, PlayerRankManager playerRankManager) {
        this.main = main;
        this.rankManager = rankManager;
        this.configManager = configManager;
        this.rankServices = new RankServices(rankManager);
        this.playerRankServices = new PlayerRankServices(rankServices, playerRankManager);
    }








}
