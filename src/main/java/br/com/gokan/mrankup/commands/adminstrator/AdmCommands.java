package br.com.gokan.mrankup.commands.adminstrator;

import br.com.gokan.mrankup.Main;
import br.com.gokan.mrankup.utils.frameworks.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AdmCommands {

    Main main;
    public AdmCommands(Main main) {
        this.main = main;
    }
    String COMMAND = "mrank";
    String PERMISSION = COMMAND + ".adm";
    String[] ALIASES = new String[] { "mrankup", "mranks" };
    public void register(){
        new CommandBuilder(COMMAND)
                .aliases(ALIASES)
                .permission(PERMISSION)
                .permissionMessage("§cVocê não tem permissão para usar esse comando.")
                .executor((CommandSender sender, String[] args) -> {
                    if (args.length == 0){
                        return true;
                    }
                    String subComando = args[0].toLowerCase();
                    switch (subComando) {
                        case "reload": {
                            Main.getRankAPI().getConfigManager().reloadAllConfigs(sender);
                            break;
                        }
                        case "next":
                            break;
                        case "previous":
                            break;
                        case "set":
                            break;
                        case "remove":
                            break;
                        case "list":
                            break;
                        case "perfil":
                            break;
                        case "criar":
                            sender.sendMessage("§cFuturas Atualização!!");
                            break;
                        default: {
                            sender.sendMessage("§cComando inválido.");
                            break;
                        }
                    }
                    return false;
                })
                .build();
    }


    void nextRank(CommandSender sender, String[] args) {

    }

    void previousRank(CommandSender sender, String[] args) {

    }

    void setRank(CommandSender sender, String[] args) {

    }

    void removeRank(CommandSender sender, String[] args) {

    }

    void listRank(CommandSender sender, String[] args) {

    }

    void perfilUser(CommandSender sender, String[] args) {

    }
    void criarRank(CommandSender sender, String[] args) {

    }


}
