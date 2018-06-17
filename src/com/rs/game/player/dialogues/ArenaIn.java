package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.Graphics;

public class ArenaIn extends Dialogue {

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
				player.getPackets().sendGameMessage( "<col=DC0000>To escape the DeathMatch Arena, Answer Nastroths questions correctly.:");
				player.getPackets().sendGameMessage( "<col=DC0000>This helps ensure people dont teleport away from the DeathMatch.");
				player.useStairs(3170, new WorldTile(2462,4256, 0), 2, 3,"Goodluck.");
				player.setNextGraphics(new Graphics(3238));
				player.getControlerManager().startControler("Wilderness");
				end();
			
			} else
				end();
		}
	}

	@Override
	public void start() {
		sendDialogue(SEND_2_LARGE_OPTIONS, "Enter the DeathMatch Arena?",
				"Yes. (Very Dangerous!)"," No Thanks.");
	}

}
