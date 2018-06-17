package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

public class revs extends Dialogue {

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
				player.getPackets().sendGameMessage("You do the twist...");
				player.useStairs(818, new WorldTile(3087, 3491, 0), 2, 3,
						"You are now at home");
				player.addWalkSteps(1599, 4515, -1, false);
				end();
			}
			if (componentId == 3) {
				player.getPackets().sendGameMessage("What you talking bout willis?");
				player.useStairs(68459, new WorldTile(2815, 5511, 0), 2, 3,
						"You are at ruins! Prepare for battle.");
				player.addWalkSteps(1600, 4514, -1, false);
				end();
			} else
				end();
		}
	}

	@Override
	public void start() {
		sendDialogue(SEND_3_LARGE_OPTIONS, "Select an Option",
				"Home", "Ruins", "Never Mind");
	}

}
