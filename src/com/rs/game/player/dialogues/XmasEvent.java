package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

public class XmasEvent extends Dialogue {

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
				player.getPackets().sendGameMessage("");
				player.useStairs(10584, new WorldTile(3087, 3491, 0), 2, 3,
						"Welcome Home. Hope you had fun");
				player.addWalkSteps(1599, 4515, -1, false);
				end();
			}
			if (componentId == 3) {
				player.sendMessage("<col=0000FF>----------------------------------------------------------------");
				player.getPackets().sendGameMessage("Welcome to Extinction's 1st Christmas");
				player.useStairs(10584, new WorldTile(2762, 5730, 0), 2, 3,
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
				"Home", "Christmas Event", "Never Mind");
	}

}
