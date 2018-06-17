package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;
import com.rs.utils.ShopsHandler;

public class Scrolls extends Dialogue {

	private int npcId;

	//CLOTHES
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Which Scroll shop do you want to open?"}, IS_NPC, npcId, -1);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_4_OPTIONS, "Which supply shop do you need?", "1", "2", "3","None of them.");
				if (componentId == 1){
						//shirt
						ShopsHandler.openShop(player, 10);
						end();
							}
					if (componentId == 2){
						//Arm
						ShopsHandler.openShop(player, 11);
						end();
							}
					if (componentId == 3){
						//pants
						ShopsHandler.openShop(player, 12);
						end();
							}
					if (componentId == 4){
						//end
							end();
							}
					}
		
			}
	@Override
	public void finish() {

	}

}