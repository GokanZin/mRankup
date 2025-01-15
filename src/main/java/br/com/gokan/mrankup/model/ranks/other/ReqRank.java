package br.com.gokan.mrankup.model.ranks.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@NoArgsConstructor
@Setter
@Getter
public class ReqRank {

    String permission;
    double money;



//    public boolean isValid(Player player) {
//        return player..hasPermission(permission) && player.getBalance() >= money;
//    }



}
