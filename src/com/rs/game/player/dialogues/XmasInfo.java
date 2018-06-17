package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;


public class XmasInfo extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to Extinction 1st Christmas Event!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Where am I?",
								"What is the point in this?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "This is the first event Extinction has done."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Click each Portal to Go start each minigame." },
			IS_NPC, npcId, 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You will gain presants which you exchange for tokens."}, IS_NPC, npcId, 9785);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Then talk to the imp to spend your tokens on Rare items." },
			IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "These items will NEVER be released again!"}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Ok Thanks" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 11;
			
		} else if (stage == 11) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Try out the Hide and Seek Minigame to the north.","You will find a boss, which will get clues that lead to some good items."}, IS_NPC, npcId, 9827);
			stage = 12;
			
		

			
			
		} else if (stage == 12) {
			player.setNextAnimation(new Animation(1));
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("There are a few Guides on Forums, go check them out.");
			player.sendMessage("I hope you guys enjoy this event. I spent over 12 hours making it.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
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