package br.com.gokan.mrankup.model.user;

import br.com.gokan.mrankup.model.ranks.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class PlayerRank {

    @Getter
    @Setter
    int rankid;
    @Getter
    OfflinePlayer player;





}
