package br.com.gokan.mrankup.model.ranks;


import br.com.gokan.mrankup.model.ranks.other.ReqRank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Rank {

    final int id;
    final String prefix;
    final String permission;
    final String nome;
    private ReqRank req ;
}
