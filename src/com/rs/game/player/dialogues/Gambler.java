package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Gamble;

public class Gambler extends Dialogue {

        private int npcId;

        @Override
        public void start() {
                sendEntityDialogue(SEND_1_TEXT_CHAT,
                                new String[] { "Gambler",
                                                "Hello, how much would you like to gamble?"}, IS_NPC, npcId, 9827);
        }
        
        @Override
        public void run(int interfaceId, int componentId) {
                if (stage == -1) {
			sendDialogue(SEND_5_OPTIONS, "How much do you wish to gamble?", "10000gp (10k)",
                                        "100000gp (100k)", "1000000gp (1mil)", "10000000gp (10m)", "I don't want to bet.");
                        stage = 1;
                //	} else if (stage == 1) {
				//	if (componentId == 1) {  <- like that
				} else if (stage == 1) {
                        if (componentId == 1) {
                                new Gamble(player, 10000);
                                end();
                                }
                        else if (componentId == 2) {
                                new Gamble(player, 100000);
                                end();
                                }
                        else if (componentId == 3) {
                                new Gamble(player, 1000000);
                                end();
                                }
                        else if (componentId == 4) {
                                new Gamble(player, 10000000);
                                end();
                                }
                        else if (componentId == 5) {
                                player.sm("I hope you don't wimp out next time!");
                                end();
                                }
                        }
                }

        @Override
        public void finish() {

        }
}