package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class FStyler extends Dialogue {

	private int npcId;

	//CLOTHES
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"I can change Females Shirt or Pants at the moment.", "However my magic is a bit buggy."}, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_4_OPTIONS, "What would you like to change?", "Shirt", "Arms", "Pants"," I like how I look thanks.");
				if (componentId == 1){
						//shirt
						sendDialogue(SEND_4_OPTIONS, "Choose a style Shirt", "Scoop Neck", "BoobTube mid-line", "U Neck", "Hour-Glass Scoop Neck");
						stage = 2;
							}
					if (componentId == 2){
						//Arm
						sendDialogue(SEND_5_OPTIONS, "Choose a style Sleeves", "Shoulder Length", "Straps", "Broad Shoulder Length", "Flared", "Long Cuffed");
						stage = 3;
							}
					if (componentId == 3){
						//pants
						sendDialogue(SEND_5_OPTIONS, "Choose a style Pants", "Skinny Pants", "Mid Skirt", "Flared Trousers", "Long Skirt","Side Stiched Pants");
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
						player.getAppearence().setLook(2,56);
						player.getAppearence().generateAppearenceData();
						end();
							}
				 if (componentId == 2){
						player.getAppearence().setLook(2,57);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(2,58);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 4){
						player.getAppearence().setLook(2,60);
						player.getAppearence().generateAppearenceData();
						end();
							}
					
					
					}
					
		
		
		else if (stage == 3) {
					if (componentId == 1){
						//arms
						player.getAppearence().setLook(3,61);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 2){
						player.getAppearence().setLook(3,62);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(3,63);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 4){
						player.getAppearence().setLook(3,64);
						player.getAppearence().generateAppearenceData();
						end();;
							}
					 if (componentId == 5){
						player.getAppearence().setLook(3,65);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}
					
		else if (stage == 4) {
					if (componentId == 1){
						//arms
						player.getAppearence().setLook(5,70);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 2){
						player.getAppearence().setLook(5,71);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 3){
						player.getAppearence().setLook(5,72);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 4){
						player.getAppearence().setLook(5,74);
						player.getAppearence().generateAppearenceData();
						end();
							}
					 if (componentId == 5){
						player.getAppearence().setLook(5,76);
						player.getAppearence().generateAppearenceData();
						end();
						}
					
					
					}

				}
		
			
	@Override
	public void finish() {

	}

}