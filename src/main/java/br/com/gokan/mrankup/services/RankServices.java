package br.com.gokan.mrankup.services;

import br.com.gokan.mrankup.manager.RankManager;
import br.com.gokan.mrankup.model.ranks.Rank;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class RankServices {

    RankManager rankManager;
    /**
     * Retorna o rank com o id especificado.
     * @param id do rank que deseja obter
     * @return o rank com o id especificado, ou null se nao encontrar
     */
    public Rank getRank(int id) {
        return rankManager.getRank(id);
    }

    /**
     * Retorna o rank com o nome especificado.
     * @param name do rank que deseja obter
     * @return o rank com o nome especificado, ou null se nao encontrar
     */
    public Rank getRank(String name) {
        return rankManager.getRank(name);
    }

    /**
     * Retorna uma lista com todos os ranks.
     * @return lista com todos os ranks
     */
    public Collection<Rank> getRanks() {
        return rankManager.getRanks();
    }

    /**
     * Retorna o rank posterior ao rank de id especificado.
     * @param id do rank que deseja obter o posterior
     * @return o rank posterior, ou null se o rank especificado for o ultimo
     */
    public Rank getNextRank(int id) {
        return rankManager.getRanks().stream().filter(rank -> rank.getId() > id).min(Comparator.comparingInt(Rank::getId)).orElse(null);
    }

    /**
     * Retorna o rank anterior ao rank de id especificado.
     * @param id do rank que deseja obter o anterior
     * @return o rank anterior, ou null se o rank especificado for o primeiro
     */
    public Rank getPreviousRank(int id) {
        return rankManager.getRanks().stream().filter(rank -> rank.getId() < id).max(Comparator.comparingInt(Rank::getId)).orElse(getFirstRank());
    }


    /**
     * Retorna o rank padrão com o menor ID.
     * @return o rank padrão com o menor ID
     * @throws NullPointerException se nenhum rank for encontrado
     */
    public Rank getFirstRank() {
        return rankManager.getRanks().stream()
            .min(Comparator.comparingInt(Rank::getId))
            .orElseThrow(() -> new NullPointerException("Não foi encontrado o rank default"));
    }

    /**
     * Retorna o rank com o maior ID.
     * @return o rank com o maior ID
     * @throws NullPointerException se nenhum rank for encontrado
     */
    public Rank getMaxRank() {
        return rankManager.getRanks().stream()
            .max(Comparator.comparingInt(Rank::getId))
            .orElseThrow(() -> new NullPointerException("Não foi encontrado rank máximo"));
    }





}
