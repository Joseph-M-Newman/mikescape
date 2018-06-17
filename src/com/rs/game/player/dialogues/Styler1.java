package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class Styler1 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Would you like to change your look?"}, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_2_OPTIONS, "What would you like to change", "Clothes", "Colour.");
				stage = 1;
		} else if (stage == 1) {
			if (componentId == 1){
				//ClOTHES
					end();
					player.getDialogueManager().startDialogue("Styler2");
					}
			else if (componentId == 2){
				//COLOUR
					end();
					player.getDialogueManager().startDialogue("Styler3");
					}		
			}
			}
	@Override
	public void finish() {

	}

}