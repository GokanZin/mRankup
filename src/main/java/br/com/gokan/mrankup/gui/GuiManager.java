package br.com.gokan.mrankup.gui;

import br.com.gokan.mrankup.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GuiManager {

    RanksGui ranksGui;


    public void openRanks(Player player){
        if (ranksGui != null){
            ranksGui.open(player);
        }
    }

    public void openRank(Player player){

    }





    public void reloadAllGuis(CommandSender sender){
        ranksGui = getRanksGui(sender);
    }



    RanksGui getRanksGui(CommandSender sender){
        FileConfiguration gui = Main.getRankAPI().getConfigManager().getGui();
        int rows = gui.getInt("gui-ranks.size");
        if (rows < 0 || rows > 9){
            sender.sendMessage("§cOcorreu um erro ao carregar o arquivo 'gui.yml'. O campo 'gui-ranks.size' deve estar entre 0 e 9.");
            return null;
        }
        String title = gui.getString("gui-ranks.title","");
        ranksGui = new RanksGui(rows, title, sender);
        return ranksGui;
    }






}
