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


public class DiengS extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"HELP!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "What happened here?",
								"Are you ok?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "They came, took our villagers...", "Killed the rest..."}, IS_NPC, npcId, 9785);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "What! Who done this?" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "*Cough*", "Im unsure, all I know is they went north."}, IS_NPC, npcId, 9785);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Please help save them, I beg of you.", "I will even reward you if im still alive." },
			IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "But Hurry!", "Run North and save the villagers.."}, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "I wont let you down.","You can count on me!" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 11;
		} else if (stage == 11) {
		if(player.Talkedtosoldier == false){
			player.sendMessage("<col=ff0000> <shad=000>Quest Updated:</shad> Save the Villagers!");
			player.Talkedtosoldier = true;
			}
			player.sendMessage("You accepted his challenge to save his villagers");
			
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