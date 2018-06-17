package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class MClothe extends Dialogue {

	private int npcId;

	//CLOTHES
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"You can change the colour of your Shirt or Pants"}, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_2_OPTIONS, "What would you like to change?", "Shirt", "Pants");
				if (componentId == 1){
						//shirt
						sendDialogue(SEND_5_OPTIONS, "Choose a Colour Shirt", "Black", "Green", "Orange", "Purple", "Dark Red");
						stage = 2;
							}
					if (componentId == 2){
						//Arm
						sendDialogue(SEND_5_OPTIONS, "Choose a Colour Pants", "Black", "Green", "Orange", "Violet", "Dark Blue");
						stage = 3;
							}	
					
				
				
				
		} 
							
					
		
		else if (stage == 2) {
					if (componentId == 1){
						//shirt
						player.getAppearence().setColor(1,16);
						player.getAppearence().generateAppearenceData();
						end();
							}
				 if (componentId == 2){
						player.getAppearence().setColor(1,15);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setColor(1,30);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 4){
						player.getAppearence().setColor(1,27);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 5){
						player.getAppearence().setColor(1,24);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}
					
		
		
		else if (stage == 3) {
					if (componentId == 1){
						//arms
						player.getAppearence().setColor(2,2);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 2){
						player.getAppearence().setColor(2,9);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setColor(2,30);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 4){
						player.getAppearence().setColor(2,11);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 5){
						player.getAppearence().setColor(2,21);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}
					

				}
		
			
	@Override
	public void finish() {

	}

}