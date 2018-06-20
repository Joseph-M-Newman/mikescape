package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;
import com.sun.org.glassfish.gmbal.ManagedObject;

public class MrEx extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"I can teleport you all around the Project,",
						"Where would you want to go?" }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
					"Skilling.", "MiniGames.", "Bosses", "Cities.");
			stage = 1;
		} else if (stage == 1) {
			//training
			if (componentId == 1) {
				 stage = 2;
			sendDialogue(SEND_5_OPTIONS, "Training: ",
					"Rock Crabs","Yaks","Taverley Dungeon","Brimhaven Dungeon","More Options");
			}
			//skilling
			else if (componentId == 2)
			{
				stage = 3;
				sendDialogue(SEND_5_OPTIONS, "Skilling", "Agility","Cooking","Crafting","Fishing","More");
			}
			//minigames
			else if (componentId == 3) {
				stage = 4;
				sendDialogue(SEND_5_OPTIONS, "MiniGames","Barrows","Fight Caves","Clan Wars","--","More");
			}
			//bosses
			else if (componentId == 4)
			{
				stage = 5;
			sendDialogue(SEND_5_OPTIONS, "Bosses","God Wars", "Corp","Nex","Nomad","More Options");
			}
			//cities
			else if (componentId == 5) {
				stage = 6;
				sendDialogue(SEND_5_OPTIONS, "Cities",
						"Varrock.", " Ardy.", "Falador.",
						"Camelot", "More Options");
			}
			//TRAINING
		} else if (stage == 2) {
			if (componentId == 1) {
				teleportPlayer(2905, 5203, 0); // rock crabs
			} else if (componentId == 2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 4383, 0)); //yaks
			else if (componentId == 3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884, 9800, 0)); //talv dung
			else if (componentId == 4)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2710, 9466, 0)); //brim dung
			else if (componentId == 5) {
				stage = 7;
				sendDialogue(SEND_5_OPTIONS, "Training:",
						"Slayer Tower", "Skeleton Monkeys", "Dungeoneering",
						"Relleka cave", "Return");
			}
			//SKILLING
		} else if (stage == 3) {
			if (componentId == 1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360, 3658, 0));
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2984, 3596, 0));
				player.getControlerManager().startControler("Wilderness");
			} else if (componentId == 3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240, 3611, 0));
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2539,4712, 0));
			} else if (componentId == 5) {
				sendDialogue(SEND_5_OPTIONS, "Skillingg",
						"Mining", "Smithing", "Farming", "Summoning",
						"Return");
				stage = 10;
			}
		} else if (stage == 4) {
			if (componentId == 1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3565, 3289, 0));
			} else if (componentId == 2){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2709, 9464, 0));
			}else if (componentId == 3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2892, 9784, 0));
			}else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2708, 3709, 0));
			} else if (componentId == 5) {
				stage = 5;
				sendDialogue(SEND_5_OPTIONS, "Fun Areas",
						"Duel Arena", "Clan Wars", "Safe Pvp (Home)", "Coming Soon",
						"Return");
			}
		} else if (stage == 5) {
			if (componentId == 1) {
				sendDialogue(SEND_5_OPTIONS, "GWD: ", "Bandos.",
						"Armadyl.", "Zammy.", "Sara","Return");
				stage = 12;
			} else if (componentId == 2){
				teleportPlayer2(2994, 9679, 0);
		}	else if (componentId == 3){
				teleportPlayer(2905,5203,0);
			}else if (componentId == 4) {
				 player.getPackets().sendGameMessage("Got a suggestion? Tell a member of staff."); 
			} else if (componentId == 5) {
				stage = 1;
				sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
						"Skilling.", "MiniGames.", "Bosses", "Cities.");
			}
		} else if (stage == 6) {
			if (componentId == 1){
				teleportPlayer2(3011, 9275, 0);
			}else if (componentId == 2){
				teleportPlayer2(2445, 5172, 0);
			}else if (componentId == 3){
				teleportPlayer2(2986, 9636, 0);
			}else if (componentId == 4){
				teleportPlayer2(3297, 9824, 0);
			}else if (componentId == 5) {
			stage = 1;
			sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
					"Skilling.", "MiniGames.", "Bosses", "Cities.");
			}
		} else if (stage == 7) {
			if (componentId == 1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3297,9824,0));
			}else if (componentId == 2){
			teleportPlayer2(3507, 9493, 0);
			}else if (componentId == 3){
			teleportPlayer2(2912, 4450, 0);
			}else if (componentId == 4){
				teleportPlayer2(3081, 10058, 0);
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 5) {
				sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
						"Skilling.", "MiniGames.", "Bosses", "Cities.");
				stage = 1;
				}
		}
			else if(stage == 10)
			{
				if(componentId == 1)
				{
					sendDialogue(SEND_4_OPTIONS, "Mining",
							"Varrock Mine", "Falador Mine", "Al-Karid Mine", "NA");
					stage = 11;
				}else if (componentId == 2)
				{
			
				}else if(componentId == 3)
				{
					
				}else if(componentId == 4)
				{
					sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
							"Skilling.", "MiniGames.", "Bosses", "Cities.");
					stage = 1;
					
				}else if (componentId == 5)
				{
					sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
							"Skilling.", "MiniGames.", "Bosses", "Cities.");
					stage = 1;
					
				}
			}
			else if(stage == 11)
			{
				if (componentId == 1)
				{
					teleportPlayer2(3087,3047,0);
					
				}else if (componentId == 2)
				{
					
				}else if (componentId == 3)
				{
					
				}else if (componentId == 4)
				{
					
				}else if (componentId == 5)
				{
					
				}
			}else if(stage == 12)
			{
				if(componentId == 1)
				{
					teleportPlayer(2864,5354,2);
				}else if (componentId == 2)
				{
					teleportPlayer(2839,5296,2);
				}else if (componentId == 3)
				{
					teleportPlayer(2925,5331,2);
				}else if (componentId == 4)
				{
					teleportPlayer(2907,5265,0);
				}else if (componentId == 5)
				{
					sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
							"Skilling.", "MiniGames.", "Bosses", "Cities.");
					stage = 1;
				}
				//Agility 
			}else if(stage == 13)
			{
				if(componentId == 1)
				{
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2474,3438,0));
				}else if (componentId == 2)
				{
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552,3556,0));
				}else if (componentId == 3)
				{
					Magic.sendAncientTeleportSpell(player, 0, 0, new WorldTile(2997,3933,0));
				}else if (componentId == 4)
				{
					sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
							"Skilling.", "MiniGames.", "Bosses", "Cities.");
					stage = 1;
				}
				//Dung
			} else if (stage == 14)
			{
				if (componentId == 1)
				{
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3449,3747,0));
				} else if (componentId == 2)
				{
					sendDialogue(SEND_5_OPTIONS, "Select: ", "Training.",
							"Skilling.", "MiniGames.", "Bosses", "Cities.");
					stage = 1;
				} 
			}else if (stage == 15)
			{
				
			}
		}
		
		
		//end else ifs
	

	private void teleportPlayer(int x, int y, int z) { //<-- Teleport method things
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("GodWars");
	}
	private void teleportPlayer2(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}

	@Override
	public void finish() {

	}
}
