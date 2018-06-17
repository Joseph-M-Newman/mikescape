package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;//<-- imports @@@@@ 
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;
import com.rs.game.item.Item;

public class SantaClaus2013 extends Dialogue {

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1)
			stage = 0;
		if (stage == 0) {
			if (componentId == 2) {
				player.getInventory().addItem(1917 , 1);
				player.getInventory().addItem(6858 , 1);
				player.getInventory().addItem(6859 , 1);
				end();
			}
			if (componentId == 3) {
				player.sendMessage("<col=0000FF>----------------------------------------------------------------");
				player.getPackets().sendGameMessage("Welcome to The Poanizer Project's 1st Christmas");
				player.useStairs(10584, new WorldTile(2084, 5795, 0), 2, 3,//teleportPlayer(2084, 5795, 0);
						"Talk to the Head Snow Imp for information.");
				player.sendMessage("<col=0000FF>----------------------------------------------------------------");

				player.addWalkSteps(1600, 4514, -1, false);
				end();
			} else
				end();
		}
	}

	@Override
	public void start() {
		sendDialogue(SEND_3_LARGE_OPTIONS, "Select an Option",
				"Holiday Items", "Christmas Event", "Never Mind");
	}

}
