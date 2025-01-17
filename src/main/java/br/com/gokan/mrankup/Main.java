package br.com.gokan.mrankup;

import br.com.gokan.mrankup.api.RankAPI;
import br.com.gokan.mrankup.commands.adminstrator.AdmCommands;
import br.com.gokan.mrankup.events.PlayerJoinListener;
import br.com.gokan.mrankup.manager.ConfigManager;
import br.com.gokan.mrankup.manager.PlayerRankManager;
import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.repository.PersistenceManager;
import br.com.gokan.mrankup.utils.ColorConsole;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    String prefix = "mRankup";
    ConfigManager configManager;
    @Getter
    static RankAPI rankAPI;
    RankManager rankManager;
    PlayerRankManager playerRankManager;
    PersistenceManager persistenceManager;


    @Override
    public void onLoad() {
        this.configManager = new ConfigManager(this);
        this.rankManager = new RankManager(configManager);
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage( ColorConsole.GREEN + " Plugin foi inicializado com sucesso!" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Criador: [Gokan]" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Suporte: https://discord.gg/22gnYtuTTs" + ColorConsole.RESET);
        this.persistenceManager = new PersistenceManager(configManager);
        rankAPI = new RankAPI(this, rankManager, configManager, playerRankManager);
        rankManager.initRanks(Bukkit.getConsoleSender());
        this.playerRankManager = new PlayerRankManager(persistenceManager.getDatabaseManager());
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerRankManager), this);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        new AdmCommands(this).register();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Plugin foi desligado com sucesso!" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Criador: [Gokan]" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Suporte: https://discord.gg/22gnYtuTTs" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "<-------[" + prefix + "]------->" + ColorConsole.RESET);

    }




}
