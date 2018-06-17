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


public class Guide extends Dialogue {

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
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Can you tell me things I need to know about the server?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Of course, lets start off by telling you how how","  to find most commands on the project, Type ::commands"}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Although  the commands list shows most of the commands"," not all commands will be  there", "The most recently added are at the bottom" },IS_NPC, npcId, 9827);
			
			stage = 3;
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Also not all teleports will be a command.","Most Pvm bosses are in the red portal."}, IS_NPC, npcId, 9827);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Its found north of me","Max can take you to most of the skilling locations" }, IS_NPC, npcId, 9827);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Now You may be wondering how to make some money", "a good way to make some money is ::cash"}, IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage ==6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "After you obtain a decent amount go to ::shop","and buy some armour."}, IS_NPC, npcId, 9827);
			stage = 7;
		} else if (stage == 7) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You may need to gain some levels though.","Which can be done on any monster. Or at ::train"}, IS_NPC, npcId, 9827);
			stage = 8;
		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Make sure to ::vote every day and .","claim your reward with ::claim"}, IS_NPC, npcId, 9827);
			stage = 9;
		} else if (stage == 9) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "If you wish to apply for staff", "check out our site ::forum."}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "May aswell sign up to forums anyway.","Most important things are posted on there."}, IS_NPC, npcId, 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Pick up fog tokens when you see them. ","they can be spent at the fog shops"}, IS_NPC, npcId, 9827);
			stage = 12;
		} else if (stage == 12) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Also if your wondering","The Owner and Coder is Poanizer."}, IS_NPC, npcId, 9827);
			stage = 13;
		} else if (stage == 13) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Have fun on the Project","If you need any help just ask in the FC."}, IS_NPC, npcId, 9827);
			stage = 14;
		} else if (stage ==14) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "To talk in the FC"," type a slash '/' before your message."}, IS_NPC, npcId, 9827);
			stage = 15;
		} else if (stage == 15) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "But come on....","standing here is an XP waste."}, IS_NPC, npcId, 9827);
			stage = 16;
		} else if (stage == 16) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Get out there and explore!"}, IS_NPC, npcId, 9827);
			stage = 17;
		} else if (stage == 17) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "And Goodluck."}, IS_NPC, npcId, 9827);
			stage = 18;	
		
		} else if (stage == 18) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
			new String[] { player.getDisplayName(), "Ok Thanks" },
			IS_PLAYER, player.getIndex(), 9827);
			
			
			stage = 19;
		} else if (stage == 19) {
			player.sendMessage("Type ::Forum and look under 'Guides' for more info.");
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