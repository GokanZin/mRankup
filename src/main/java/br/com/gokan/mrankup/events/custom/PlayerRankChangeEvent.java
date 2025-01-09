package br.com.gokan.mrankup.events.custom;

import br.com.gokan.mrankup.model.ranks.Rank;
import br.com.gokan.mrankup.model.user.PlayerRank;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRankChangeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final int rankId;
    private final Rank rank;
    private final PlayerRank playerData;
    private boolean cancelled;


    public PlayerRankChangeEvent(Player player, int rankId, Rank rank, PlayerRank playerData) {
        this.player = player;
        this.rankId = rankId;
        this.rank = rank;
        this.playerData = playerData;
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
