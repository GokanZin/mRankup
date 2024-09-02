package br.com.gokan.mtemplate2;

import br.com.gokan.mtemplate2.utils.ColorConsole;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    String prefix = "mTemplate";

    @Override
    public void onEnable() {
        configreloader();
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



    public void configreloader(){
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        reloadConfig();
    }

}
