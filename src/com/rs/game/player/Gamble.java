package com.rs.game.player;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class Gamble {
        
        private final int CHANCE_OF_WINNING = 75;

        public Gamble(Player player, int cost) {
                if (canAfford(player, cost)) {
                        if (calculateWin()) {
                                        player.getInventory().addItem(995, cost);
                                player.sm("Congratulations, you have won " + cost + " coins!");
                                return;
                        }
                       player.getInventory().deleteItem(995, cost);
                        return;
                }
                player.sm("You can't afford to gamble " + cost + " coins.");
        }

        public boolean calculateWin() {
                return Utils.random(100) >= CHANCE_OF_WINNING;
        }
        
        public boolean canAfford(Player player, int amount) {
                return player.getInventory().containsItem(995, amount);
        }

}