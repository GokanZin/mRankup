package br.com.gokan.mrankup.gui;

import br.com.gokan.mattributes.utils.Util;
import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.api.other.inventory.fastinv_edited.FastInv;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class RanksGui extends FastInv {

    public RanksGui(int rows, String title, CommandSender sender) {
        super(rows, title);
    }


    public void loadRanks(CommandSender sender){
        FileConfiguration gui = Main.getRankAPI().getConfigManager().getGui();
        if (gui == null) return;
        ConfigurationSection ranksGui = gui.getConfigurationSection("gui-ranks");
        if (ranksGui == null){
            sender.sendMessage("§cOcorreu um erro ao carregar o arquivo 'gui.yml'. O campo 'gui-ranks' não foi encontrado.");
            return;
        }
        List<String> items = ranksGui.getStringList("items");
        for (String item : items) {
            if (item.equalsIgnoreCase("loop-ranks")) {
                loopRanks(sender, gui.getConfigurationSection("items." + item);
            }
        }
    }

    private void loopRanks(CommandSender sender, ConfigurationSection config) {
        if (config == null) return;
        String displayItem = config.getString("nome");
        int[] slots = Util.convertSplit(config.getString("slots"));
        String action = config.getString("action", "none");
        String sound = config.getString("sound");
    }


}
