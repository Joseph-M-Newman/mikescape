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


public class Poanizer extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to The Poanizer Project!"}, IS_NPC, npcId, 9827);}

	@Override
	public void run(int interfaceId, int componentId) {

				if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "I will tell you how to start things up.", "You need to move the Shops/Objects to a new home."}, IS_NPC, npcId, 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "You should of downloaded 2 folders.","The Source and Client."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
								new String[] { "Poanizer", "To log into Poanizer (Admin) use:", "Username: Poanizer","Password: k" },IS_NPC, npcId, 9827);
			
			stage = 3;
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "When ingame do ::coords to show your coordinates","You will be using these to move thing."}, IS_NPC, npcId, 9827);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] {"Poanizer", "Navigate to: src/com/rs/utils", "Open NPCSpawning.java."}, IS_NPC, npcId, 9827);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "You should see at the top a comment.", "This will tell you how to Edit/Move the NPCs"}, IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage ==6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "The obejcts code is under the shops.","They are also placed around this area."}, IS_NPC, npcId, 9827);
			stage = 7;
		} else if (stage == 7) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] { "Poanizer", "After you have moved shops to a new home","Open src/com/rs","Then open Settings.java"}, IS_NPC, npcId, 9827);
			stage = 8;
		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "In there change the 'START_PLAYER_LOCATION'","and the 'RESPAWN_PLAYER_LOCATION'"}, IS_NPC, npcId, 9827);
			stage = 9;
		} else if (stage == 9) {
			sendEntityDialogue(SEND_4_TEXT_CHAT,
					new String[] { "Poanizer", "Once you have done that your basically set.","", "If you need any more help","Read the 'information/Notes' in Extras"}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "There will be a Beginners RSPS guide in there","Along with Links to video tutorials."}, IS_NPC, npcId, 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "The extras folder also has RSPS Tools","These are used to edit your RSPS"}, IS_NPC, npcId, 9827);
			stage = 12;
		} else if (stage == 12) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "The GFX are for background or logos","And the id's are just lists"}, IS_NPC, npcId, 9827);
			stage = 13;
		} else if (stage == 13) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "Have fun on the Project","Goodluck with the coding."}, IS_NPC, npcId, 9827);
			stage = 14;
		} else if (stage ==14) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "Remember to reply on the Rune-Server Post","If you want some specific help."}, IS_NPC, npcId, 9827);
			stage = 15;
		} else if (stage == 15) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Poanizer", "Click NPC's for the id, Objects use examines","Source Developed by: Poanizer"}, IS_NPC, npcId, 9827);
			stage = 16;
		} else if (stage == 16) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { "Poanizer", "Other Credits are in 'Extras'"}, IS_NPC, npcId, 9827);
			stage = 17;
		} else if (stage == 17) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { "Poanizer", "Goodluck."}, IS_NPC, npcId, 9827);
			stage = 18;	
		
		} else if (stage == 18) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
			new String[] { player.getDisplayName(), "Thanks for the help." },
			IS_PLAYER, player.getIndex(), 9827);
			
			
			stage = 19;
		} else if (stage == 19) {
			player.sendMessage("Main Points to remember:");
			player.sendMessage("------------------------");
			player.sendMessage("1. Open the 'RSPS Guide' in the Information/Notes folder. ");
			player.sendMessage("2. Change the RSPS Name and Home. ");
			player.sendMessage("3. Remember the client ip '127.0.0.1' will only work for you.");
			player.sendMessage("4. Reply on the Rune Server Download Post for Help.");
			player.sendMessage("5. You don't need to add 'credits' just don't claim my work is yours");
			player.sendMessage("6. Have fun with the RSPS, and don't trust everyone who can 'code'.");
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