package br.com.gokan.mrankup.events;

import br.com.gokan.mrankup.manager.PlayerRankManager;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class PlayerJoinListener implements Listener{


    PlayerRankManager playerRankManager;

    @EventHandler
    void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
        playerRankManager.load(event.getPlayer());
    }
}
