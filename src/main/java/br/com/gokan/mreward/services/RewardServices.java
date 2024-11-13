package br.com.gokan.mreward.services;

import br.com.gokan.mreward.manager.RewardManager;
import br.com.gokan.mreward.model.Reward;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardServices {

    RewardManager manager;

    public RewardServices(RewardManager manager){
        this.manager = manager;
    }


    public void giveReward(Player player, int pos, int posmax, double sorteJogador) {
        List<Reward> list = manager.getReward(pos, posmax);
        List<Reward> sortedList = new ArrayList<>();
        int totalChance = (int) list.stream()
                .mapToDouble(reward -> reward.getChance() * (1 + (sorteJogador / 100.0)))
                .sum();

        int cumulativeChance = 0;
        int randomValue = new Random().nextInt(totalChance);
        for (Reward reward : list) {
            int adjustedChance = (int) (reward.getChance() * (1 + (sorteJogador / 100.0)));
            cumulativeChance += adjustedChance;
            if (randomValue < cumulativeChance) {
                sortedList.add(reward);
                break;
            }
        }
        for (Reward reward : sortedList) {
            reward.executeCmd(player);
        }
    }

    public void giveReward(Player player, int pos, int posmax){
        List<Reward> list = manager.getReward(pos, posmax);
        List<Reward> sortedList = new ArrayList<>();
        int totalChance = (int) list.stream().mapToDouble(Reward::getChance).sum();
        int cumulativeChance = 0;
        int randomValue = new Random().nextInt(totalChance);
        for (Reward reward : list) {
            cumulativeChance += reward.getChance();
            if (randomValue < cumulativeChance) {
                sortedList.add(reward);
                break;
            }
        }

        for (Reward reward : sortedList) {
            reward.executeCmd(player);
        }
    }

    public void giveReward(Player player){
        int totalChance = (int) manager.getRewardslist().stream().mapToDouble(Reward::getChance).sum();
        int randomValue = new Random().nextInt(totalChance);

        ArrayList<Reward> sortedList = new ArrayList<>();
        int cumulativeChance = 0;
        for (Reward reward : manager.getRewardslist()) {
            cumulativeChance += reward.getChance();
            if (randomValue < cumulativeChance) {
                sortedList.add(reward);
                break;
            }
        }
        for (Reward reward : sortedList) {
            reward.executeCmd(player);
        }
    }

    public void giveReward(Player player, int pos){
        List<Reward> v = manager.getReward(pos);
        if (!v.isEmpty()){
            v.forEach(c -> c.executeCmd(player));
        }
    }


    public boolean hasReward(int pos){
        return manager.hasReward(pos);
    }

}
