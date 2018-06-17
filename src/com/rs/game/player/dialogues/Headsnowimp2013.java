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


public class Headsnowimp2013 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to The Poanizer Project's Christmas event."}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

				if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "So can you tell me how I can help this Christmas?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Of course,"," You can help by Slaying the Ice Titans that have taken all the Gifts."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Now you may be wondering What you get from this."," Each Time you kill a Titian you have a chance of getting a gift" },IS_NPC, npcId, 9827);
			
			stage = 3;
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Well enjoy","Merry Christmas from The Poanizer Project"}, IS_NPC, npcId, 9785);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "And good luck!."}, IS_NPC, npcId, 9827);
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