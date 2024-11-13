package br.com.gokan.mreward.commands;

import br.com.gokan.mreward.Main;
import br.com.gokan.mreward.utils.frameworks.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static br.com.gokan.mattributes.Main.getAttributeStatsAPI;

public class AdmCommands {



    public void register(){
        new CommandBuilder("mreward").aliases("mreward")
                .permission("mreward.adm")
                .permissionMessage("§cVocê não tem permissão para usar esse comando.")
                .executor((CommandSender sender, String[] args) -> {
                    if (args.length == 0){
                        sender.sendMessage("§c/mreward reload");
                        sender.sendMessage("§c/mreward give <player>");
                        sender.sendMessage("§c/mreward give <player> <inicial> <final>");
                        sender.sendMessage("§c/mreward give <player> <num>");
                        return true;
                    }
                    String subComando = args[0].toLowerCase();
                    switch (subComando) {
                        case "reload":
                            reload(sender);
                            break;
                        case "give":
                            giveF(sender, args);
                            break;
                        default: {
                            sender.sendMessage("§cComando inválido.");
                            break;
                        }
                    }
                    return false;
                }).build();
    }

    void giveAll(CommandSender sender, String[] args){
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null){
            player.sendMessage("§cO jogador não foi encontrado.");
            return;
        }
        Main.getGiveAPI().getRewardServices().giveReward(player);
        sender.sendMessage("§aRecompensa dada com sucesso para o jogador §f" + player.getName() + ".");
    }

    void give(CommandSender sender, String[] args){
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null){
            player.sendMessage("§cO jogador não foi encontrado.");
            return;
        }
        int inicial = 0;
        int finali = 0;
        try {
            inicial = Integer.parseInt(args[2]);
            finali = Integer.parseInt(args[3]);

            if (inicial > finali){
                sender.sendMessage("§cO número inicial não pode ser maior que o número final.");
                return;
            }

            if (inicial < 0 || finali < 0){
                sender.sendMessage("§cO número inicial e final não podem ser menores que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("§cNúmero inválido.");
            return;
        }
        Double sorteJogador = getAttributeStatsAPI().getPlayerAtrributeService().getProfile(player).getAttributes().getSorte();

        Main.getGiveAPI().getRewardServices().giveReward(player, inicial, finali, sorteJogador);
        sender.sendMessage("§aRecompensa dada com sucesso para o jogador §f" + player.getName() + ".");
    }

    void giveOne(CommandSender sender, String[] args){
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null){
            player.sendMessage("§cO jogador não foi encontrado.");
            return;
        }

        int inicial = 0;
        try {
            inicial = Integer.parseInt(args[2]);
        }catch (NumberFormatException e) {
            sender.sendMessage("§cNúmero inválido.");
            return;
        }
        if (Main.getGiveAPI().getRewardServices().hasReward(inicial)){
            Main.getGiveAPI().getRewardServices().giveReward(player, inicial);
            sender.sendMessage("§aRecompensa dada com sucesso para o jogador §f" + player.getName() + ".");
        }else {
            sender.sendMessage("§cRecompensa não encontrada.");
        }
    }

    void giveF(CommandSender sender, String[] args){
        if (args.length == 2){
            giveAll(sender, args);
            return;
        }
        if (args.length >= 4){
            give(sender, args);
            return;
        }
        if (args.length == 3){
            giveOne(sender, args);
            return;
        }
        sender.sendMessage("§cComando inválido, utilize: ");
        sender.sendMessage("§c/mreward give <player>");
        sender.sendMessage("§c/mreward give <player> <inicial> <final>");
        sender.sendMessage("§c/mreward give <player> <num>");
    }

    void reload(CommandSender sender){
        Main.getGiveAPI().reloadAll();
        Main.getGiveAPI().rewardmanager.load(sender);
        sender.sendMessage("§aConfigurações recarregadas.");

    }
}
