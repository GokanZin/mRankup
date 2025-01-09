package br.com.gokan.mrankup.manager;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.ranks.other.ReqRank;
import br.com.gokan.mrankup.storage.cache.RankCache;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class RankManager {

    RankCache rankCache = new RankCache();
    ConfigManager configManager;

//    posicao: 0
//    prefixo: '§7*'
    public void initRanks(CommandSender sender){
        ConfigurationSection rankConfig = configManager.getRankConfig().getConfigurationSection("ranks");
        if (!rankCache.getRankMap().isEmpty()){
            rankCache.clear();
        }
        if (rankConfig == null) return;
        for (val key : rankConfig.getKeys(false)){
            Rank rank = getRank(rankConfig.getConfigurationSection(key), sender, key);
            if (rank == null) continue;

        }
    }


     Rank getRank(ConfigurationSection rank, CommandSender sender, String key){
        if (!rank.contains("posicao") || rank.getString("posicao").isEmpty()){
            sender.sendMessage("§c[!] Não foi possível carregar o rank §f" + key + "§c. Posição não foi encontrada!");
            return null;
        }
        ReqRank rq = getReq(rank.getConfigurationSection("requisitos"));
        val prefixo = rank.getString("prefixo", "");
        return null;
    }


    ReqRank getReq(ConfigurationSection req){
        val permission = req.getString("permissao", "");
        val money = req.getDouble("money",0.0);
        return new ReqRank(permission, money);
    }
}
