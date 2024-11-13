package br.com.gokan.mreward.manager;

import br.com.gokan.mreward.api.GiveAPI;
import br.com.gokan.mreward.model.Reward;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RewardManager {

    List<Reward> rewardslist = new ArrayList<Reward>();

    public void load(CommandSender sender){
        ConfigurationSection rewardConfig = GiveAPI.getRewardConfig().getConfigurationSection("rewards");
        rewardslist.clear();
        int counter = 0;
        if (rewardConfig != null) {
            for (String key : rewardConfig.getKeys(false)) {
                int pos = 0;
                try {
                    pos = Integer.parseInt(key);
                }catch (NumberFormatException ignored) {
                    sender.sendMessage("§7[§c!§7] Recompensa " + key + " nao possui um numero valido.");
                    continue;
                }
                if (hasReward(pos)){
                    sender.sendMessage("§7[§c!§7] §eRecompensas duplicadas, parece haver §f2 §enomes iguais ou mais com o numero §f" + key);
                    continue;
                }
                ConfigurationSection reward = rewardConfig.getConfigurationSection(key);
                List<String> cmd = reward.getStringList("cmds");
                rewardslist.add(new Reward(cmd, reward.getString("message"), reward.getDouble("chance"), pos));
                counter++;
            }
            sender.sendMessage("§aForam carregadas " + counter + " recompensas.");
        }else {
            sender.sendMessage("§cNão foi encontrado nenhuma recompensa.");
        }
    }


    public boolean hasReward(int pos){
        return rewardslist.stream().anyMatch(c -> c.getPos() == pos);
    }


    public List<Reward> getReward(int posInicial, int posFinal){
        return rewardslist.stream().filter(c -> c.getPos() >= posInicial && c.getPos() <= posFinal).collect(Collectors.toList());
    }

    public List<Reward> getReward(int pos){
        return rewardslist.stream().filter(c -> c.getPos() == pos).collect(Collectors.toList());
    }

    public List<Reward> getRewardslist() {
        return rewardslist;
    }

}
