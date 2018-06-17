package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class MStyler extends Dialogue {

	private int npcId;

	//CLOTHES
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"I can change Male Shirt or Pants at the moment"}, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_4_OPTIONS, "What would you like to change?", "Shirt", "Arms", "Pants"," I like how I look thanks.");
				if (componentId == 1){
						//shirt
						sendDialogue(SEND_5_OPTIONS, "Choose a style Shirt", "Tee Shirt", "Black Stiching", "Open, Collar", "Open, Pockets", "Half n' half");
						stage = 2;
							}
					if (componentId == 2){
						//Arm
						sendDialogue(SEND_5_OPTIONS, "Choose a style Sleeves", "Normal Arms", "Sleeves", "Cuffed sleeves", "Long Skinnys", "Shoulder Spikes");
						stage = 3;
							}
					if (componentId == 3){
						//pants
						sendDialogue(SEND_5_OPTIONS, "Choose a style Pants", "Skinny Cuffed", "Shorts", "Flared", "Side Stich Cuffed","Torn");
						stage = 4;
							}		
					if (componentId == 4){
						//end
							end();
							}
				
				
				
		} 
							
					
		
		else if (stage == 2) {
					if (componentId == 1){
						//shirt
						player.getAppearence().setLook(2,18);
						player.getAppearence().generateAppearenceData();
						end();
							}
				 if (componentId == 2){
						player.getAppearence().setLook(2,20);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(2,21);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 4){
						player.getAppearence().setLook(2,22);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 5){
						player.getAppearence().setLook(2,25);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}
					
		
		
		else if (stage == 3) {
					if (componentId == 1){
						//arms
						player.getAppearence().setLook(3,26);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 2){
						player.getAppearence().setLook(3,28);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(3,29);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 4){
						player.getAppearence().setLook(3,30);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 5){
						player.getAppearence().setLook(3,31);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}
					
		else if (stage == 4) {
					if (componentId == 1){
						//arms
						player.getAppearence().setLook(5,36);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 2){
						player.getAppearence().setLook(5,37);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(5,38);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 4){
						player.getAppearence().setLook(5,39);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 5){
						player.getAppearence().setLook(5,40);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}

				}
		
			
	@Override
	public void finish() {

	}

}