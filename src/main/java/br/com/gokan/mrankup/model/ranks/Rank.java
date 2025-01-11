package br.com.gokan.mrankup.model.ranks;


import br.com.gokan.mrankup.model.ranks.other.ReqRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
@Getter
public class Rank {

    final int id;
    final String prefix;
    final String nome;
    private ReqRank req;
    private List<String> comandos;
    private List<String> grupos;




}
