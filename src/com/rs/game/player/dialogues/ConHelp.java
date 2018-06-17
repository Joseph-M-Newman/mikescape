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


public class ConHelp extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to the Construction Skill Area."}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

				if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "So can you tell me how to train construction?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Of course my friend,"," Start by buying a Saw, Chisel and a Hammer."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "The first thing to do is to make planks out of logs."," Use your saw on logs." },IS_NPC, npcId, 9827);
			
			stage = 3;
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Logs you can use:","Normal, Oak, Teak, Mahogany"}, IS_NPC, npcId, 9785);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "After you use the saw on the logs.","They will turn into planks." }, IS_NPC, npcId, 9827);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Now that you have planks, It starts getting hard."}, IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage ==6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You must use the different tools in a cetain order.","To create different cuts of wood."}, IS_NPC, npcId, 9827);
			stage = 7;
		} else if (stage == 7) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "A full list of all combinations will be on the forums.","Type ::Forum and look under 'Guides'."}, IS_NPC, npcId, 9827);
			stage = 8;
		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Keep doing the combinations untill you have a bench.","The bench's are in a packed form."}, IS_NPC, npcId, 9827);
			stage = 9;
		} else if (stage == 9) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You must use the packed bench in your inventory.", "On another bench ingame."}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "This will give you an Unpacked Bench","These combinations will also vary so check the forums."}, IS_NPC, npcId, 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "If you want to go even higher for the maximum XP.","Purchase some of the expensive items at my shop."}, IS_NPC, npcId, 9827);
			stage = 12;
		} else if (stage == 12) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Use the items on the bench's"," This will make them worth more aswell."}, IS_NPC, npcId, 9827);
			stage = 13;
		} else if (stage == 13) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You can sell Your final creations to my store or other players","It is always best to make the final result."}, IS_NPC, npcId, 9827);
			stage = 14;
		} else if (stage ==14) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Doing that will gain you more Profit and more Experiance."}, IS_NPC, npcId, 9827);
			stage = 15;
		} else if (stage == 15) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "My wares aren't cheap so if you want the best XP rates.","And the best Profit and outcomes..."}, IS_NPC, npcId, 9827);
			stage = 16;
		} else if (stage == 16) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You need to have the gold in the firstplace."}, IS_NPC, npcId, 9827);
			stage = 17;
		} else if (stage == 17) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Good Items are not made cheap."}, IS_NPC, npcId, 9827);
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