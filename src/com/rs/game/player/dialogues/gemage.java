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


public class gemage extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"YOU SHALL NOT PASS!!!!!!!!!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {player.getDisplayName(), "Oh mate, Lord of the Rings References?",
								"You must be kiwi.."}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "What chu' on about bro?"}, IS_NPC, npcId, 9785);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Oh not much aye! Bruh." },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Chur bro!"}, IS_NPC, npcId, 9785);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "What you doin' this weekend?" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Im putting up a retaining wall."}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Doin' it yaself?" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Na gona get some bloke in."}, IS_NPC, npcId, 9827);

			stage = 12;
			
		} else if (stage == 12) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Oh come on mate do it yourself!"}, IS_PLAYER, player.getIndex(), 9827);

			stage = 13;
			
		} else if (stage == 13) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Shes pretty big job."}, IS_NPC, npcId, 9827);

			stage = 14;
			
		}else if (stage == 14) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You'll be right.",
								"Just get a couple of mates around?"}, IS_PLAYER, player.getIndex(), 9827);

			stage = 15;
			
		} else if (stage == 15) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Hey Johnsey!"}, IS_NPC, npcId, 9785);

			stage = 16;
			
		} else if (stage == 16) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Whaaaaaaaaat?"},IS_PLAYER, player.getIndex(), 9827);

			stage = 17;
			
		} else if (stage == 17) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Give me a hand with the job saturday?"}, IS_NPC, npcId, 9785);

			stage = 18;
			
			
		} else if (stage == 18) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Maaaaate ya Dreamin!"},IS_PLAYER, player.getIndex(), 9827);

			stage = 19;
			
			
		} else if (stage == 19) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "tut, Aussies.... "}, IS_NPC, npcId, 9785);

			stage = 20;
			
			
		} else if (stage == 20) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "No suprises there..."}, IS_NPC, npcId, 9785);

			stage = 21;
			
		} else if (stage == 21) {
			player.setNextAnimation(new Animation(2246));
			teleportPlayer(3086,3495, 0);
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