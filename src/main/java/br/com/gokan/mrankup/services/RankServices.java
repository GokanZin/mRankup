package br.com.gokan.mrankup.services;

import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.model.ranks.Rank;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RankServices {

    RankManager rankManager;


    public Rank getRank(int id) {
        return rankManager.getRank(id);
    }

    public Rank getRank(String name) {
        return rankManager.getRank(name);
    }




}
