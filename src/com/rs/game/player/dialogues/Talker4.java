package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.World;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.ShopsHandler;

import com.rs.game.player.Skills;

public class Talker4 extends Dialogue {

	private int npcId;
	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Would you like to go into the prestige shops?.", }, IS_NPC, npcId, 9850);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "My prestige is "+player.prestige+". "}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendDialogue(SEND_2_OPTIONS, "Would you like to open the shops?",
					"Shops 1 5 10", "Shops 15 20 50");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 1) {
sendDialogue(SEND_4_OPTIONS, "Prestige 1-10", "Prestige Shop 1",
"Prestige Shop 5", "Prestige Shop 10","");
				stage = 3;
			} else if (componentId == 2) {
sendDialogue(SEND_4_OPTIONS, "Prestige 15-50", "Prestige Shop 15",
"Prestige Shop 20", "Prestige Shop 50","");
					stage = 4;
					}
		} else if (stage == 3) {
			if (componentId == 1) {
			if (player.getprestige() >= 1)
				ShopsHandler.openShop(player, 37);
				end();
			} else if (componentId == 2) {
			if (player.getprestige() >= 5)
				ShopsHandler.openShop(player, 39);
				end();
			} else if (componentId == 3) {
			if (player.getprestige() >= 10)
				ShopsHandler.openShop(player, 40);
				end();
}
		} else if (stage == 4) {
			if (componentId == 1) {
			if (player.getprestige() >= 15)
				ShopsHandler.openShop(player, 41);
				end();
			} else if (componentId == 2) {
			if (player.getprestige() >= 20)
				ShopsHandler.openShop(player, 42);
				end();
			} else if (componentId == 3) {
			if (player.getprestige() >= 50)
				ShopsHandler.openShop(player, 43);
				end();
				}

}
}
    @Override
    public void finish() {
    }
}
