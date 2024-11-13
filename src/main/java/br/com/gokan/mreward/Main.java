package br.com.gokan.mreward;

import br.com.gokan.mreward.api.GiveAPI;
import br.com.gokan.mreward.commands.AdmCommands;
import br.com.gokan.mreward.utils.ColorConsole;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    String prefix = "mReward";

    static GiveAPI giveAPI;

    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("mAttributes")){
            Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Plugin mAttributes nao foi encontrado, desligando o plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else {
            Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Plugin mAttributes encontrado, continuando..." + ColorConsole.RESET) ;
        }
        giveAPI = new GiveAPI(this);
        new AdmCommands().register();
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage( ColorConsole.GREEN + " Plugin foi inicializado com sucesso!" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Criador: [Gokan]" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Suporte: https://discord.gg/22gnYtuTTs" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
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


    public static GiveAPI getGiveAPI() {
        return giveAPI;
    }


}
