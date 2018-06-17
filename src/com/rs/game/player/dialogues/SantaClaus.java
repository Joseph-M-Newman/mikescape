package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class SantaClaus extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			if (player.getQp() == 3) 
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Thank you for helping me!" },
			IS_NPC, npcId, 9864);
		else 
			if (player.getQp() == 1) 
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Please read and follow the instructions!" },
			IS_NPC, npcId, 9810);
		else 
			if (player.getQp() == 2) 
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"I will send you back to jack." },
			IS_NPC, npcId, 9810);
		else 

		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Oh, no! Ohhh, No!"}, IS_NPC, npcId, 9770);
	}

	@Override
	public void run(int interfaceId, int componentId) {

			if (player.getQp() == 3) {
			end();

			} else if (player.getQp() == 2) {
				player.setQp(2);
			teleportPlayer(3444, 3676, 0);
				end();

		} else if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
			new String[] { player.getDisplayName(), "What is it Santa? What's wrong??" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Oh please, Help me.",
								"Jack has stolen the key to my factory,",
								"If I don't get it back we could all be in trouble!"}, IS_NPC, npcId, 9775);
			stage = 2;

		} else if (stage == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "I need you to help me find him",
								"And do whatever it takes to get my key back from him. "}, IS_NPC, npcId, 9775);
			stage = 8;

		} else if (stage == 3) {
				sendDialogue(SEND_2_OPTIONS, "Will You help me??",
				"Sure","Of course");
				stage = 9;

		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "I heard Jack was located somewhere in Lumbridge Graveyard",
								"But, I have no Idea where.. Maybe you can ask around there."}, IS_NPC, npcId, 9780);
			stage = 3;

		} else if (stage == 9) {
		if (componentId == 1)
				player.setQp(1);
			teleportPlayer(3235, 3215, 0);
			end();
		if (componentId == 2)
				player.setQp(1);
			end();
                }
        }

	private void teleportPlayer(int x, int y, int z) {
		Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(x, y, z),
				new int[0]);
	}

    @Override
    public void finish() {
    }
}

