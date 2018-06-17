package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class Talker1 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];

		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"What do you want kid?"}, IS_NPC, npcId, 9850);
	}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
				sendDialogue(SEND_2_OPTIONS, "Choose",
				"Have you seen Jack?","Kid? Who df are you?");
			stage = 1;

		} else if (stage == 1) {
		if (componentId == 1) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "I'm not sure.. I saw someone leave the boat and ",
								"Head west, He looked pretty sad, Maybe you can catch up",
								"To him.. He went by not too long ago.."}, IS_NPC, npcId, 9850);
			stage = 2;
		} else if (componentId == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Im 185 Thousand years old..."}, IS_NPC, npcId, 9850);
			stage = -1;
}
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Oh alright well thanks." },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "No problem."}, IS_NPC, npcId, 9850);
			end();
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "How much is it going to cost me?" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Im just kidding. He said something",
								"About heading down south and taking a boat."}, IS_NPC, npcId, 9850);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Thank you very much." },
			IS_PLAYER, player.getIndex(), 9827);
			end();
			}
                }

    @Override
    public void finish() {
    }
}
