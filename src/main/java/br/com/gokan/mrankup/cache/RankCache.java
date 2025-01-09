package br.com.gokan.mrankup.cache;

import br.com.gokan.mrankup.model.ranks.Rank;

import java.util.HashMap;
import java.util.Map;

public class RankCache {

    Map<Integer, Rank> rankMap = new HashMap<>();


    public Rank getRank(int id) {
        return rankMap.get(id);
    }

    public void addRank(Rank rank) {
        rankMap.put(rank.getId(), rank);
    }

    public void removeRank(int id) {
        rankMap.remove(id);
    }

    public void clear() {
        rankMap.clear();
    }

    public Map<Integer, Rank> getRankMap() {
        return rankMap;
    }

    public void setRankMap(Map<Integer, Rank> rankMap) {
        this.rankMap = rankMap;
    }


}
