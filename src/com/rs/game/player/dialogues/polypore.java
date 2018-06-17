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


public class polypore extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello Welcome to the Polypore Dungeon"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Each section has a different monster in it"}, IS_NPC, npcId, 9785);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "The North East dungeon contains Fungal Mages, Level 120"}, IS_NPC, npcId, 9785);
			stage = 2;
		
		
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "The South East dungeon contains Grifolaroo's, Level 180"}, IS_NPC, npcId, 9785);
			stage = 3;
		
		} else if (stage == 3) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "The South West dungeon contains Ganodermic Beasts, Level 280"}, IS_NPC, npcId, 9785);
			stage = 4;
			
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "And the North West dungeon contains Grifolapine's Level 200"," and ganodermic Runts Level 140"}, IS_NPC, npcId, 9785);
			stage = 5;
		
		} else if (stage == 5) {
			
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