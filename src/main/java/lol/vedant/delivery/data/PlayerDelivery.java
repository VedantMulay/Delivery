package lol.vedant.delivery.data;

import org.bukkit.entity.Player;

public class PlayerDelivery {

    private final Player player;
    private final String delivery;

    public PlayerDelivery(Player player, String delivery) {
        this.player = player;
        this.delivery = delivery;
    }

    public Player getPlayer() {
        return player;
    }

    public String getDelivery() {
        return delivery;
    }
}
