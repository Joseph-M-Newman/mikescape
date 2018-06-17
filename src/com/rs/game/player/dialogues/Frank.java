package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;//<-- imports @@@@@ 
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;
import com.rs.game.item.Item;

public class Frank extends Dialogue {

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
			} else 
			    end();
		}
	}

	@Override
	public void start() {
		sendDialogue(SEND_2_LARGE_OPTIONS, "Take one on the house!",
				"Beer", "Never Mind");
	}

}
