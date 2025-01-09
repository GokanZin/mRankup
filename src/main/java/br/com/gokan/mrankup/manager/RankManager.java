package br.com.gokan.mrankup.manager;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.ranks.other.ReqRank;
import br.com.gokan.mrankup.cache.RankCache;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;

public class RankManager {

    RankCache rankCache;

    ConfigManager configManager;
    public RankManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public Rank getRank(int id ){
        return rankCache.getRank(id);
    }

    public Rank getRank(String key){
        return rankCache.getRankMap().values().stream().filter(rank -> rank.getNome().equals(key)).findFirst().orElse(null);
    }


    public void initRanks(CommandSender sender){
        ConfigurationSection rankConfig = configManager.getRankConfig().getConfigurationSection("ranks");
        if (!rankCache.getRankMap().isEmpty()){
            rankCache.clear();
        }
        if (rankConfig == null) return;
        int count = 0;
        for (val key : rankConfig.getKeys(false)){
            Rank rank = getRankProcess(rankConfig.getConfigurationSection(key), sender, key);
            if (rank == null) continue;
            int pos = rank.getId();
            if (rankCache.getRank(pos) != null){
                sender.sendMessage("§c[!] Rank com  a posição §f" + pos + "° §c está duplicada.");
                continue;
            }
            rankCache.addRank(rank);
            count++;
        }
        sender.sendMessage("§a[!] Foram carregados §f" + count + " §aranks com sucesso!");
    }


     Rank getRankProcess(ConfigurationSection rank, CommandSender sender, String key){
        if (!rank.contains("posicao") || rank.getString("posicao").isEmpty() || rank.getInt("posicao") < 0){
            sender.sendMessage("§c[!] Não foi possível carregar o rank §f" + key + "§c. Posição não foi encontrada!");
            return null;
        }
        val posicao = rank.getInt("posicao");
        val rq = getReq(rank.getConfigurationSection("requisitos"));
        val prefixo = rank.getString("prefixo", "");
        val comandos = rank.getStringList("comandos");
        val grupos = rank.getStringList("grupos");
        return new Rank(posicao, prefixo, key, rq, comandos, grupos);
    }


    ReqRank getReq(ConfigurationSection req){
        val permission = req.getString("permissao", "");
        val money = req.getDouble("money",0.0);
        return new ReqRank(permission, money);
    }

    public Collection<Rank> getRanks() {
        return rankCache.getRankMap().values();
    }
}
