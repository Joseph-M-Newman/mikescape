package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;//<-- imports @@@@@ 
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.ForceMovement;
import com.rs.game.ForceTalk; 
import com.rs.game.World;


public class Training extends Dialogue {


	private int npcId;
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Training Master", "Hello there im the training master","Want to teleport to some training spots?"}, IS_NPC, npcId, 9785);
	
	}

	//final NPC npc = World.getNPCs().get(npcId);
	
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_5_OPTIONS, "Training Spots", "Yaks",
					"Hellhounds", "DZ Training", "Rock Crabs", "I need something else.");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == 1){
				 teleportPlayer(2322, 3795, 0);
			}else if (componentId == 2){
				teleportPlayer(2871, 9849, 0);
			}else if (componentId == 3){
				if (!player.isDonator()) {
						player.getPackets().sendGameMessage("You must be a Donator to Train here.");
						stage = 11;
					}
				else{
					teleportPlayer(3042, 4492, 0);
					}
			}else if (componentId == 4){
				teleportPlayer(2711, 3720, 0);
			}else if (componentId == 5) {
				
				sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { "Training Master", "What else would you want to know?"}, IS_NPC, npcId, 9827);
			stage = 3;
			} 
		}else if (stage == 2) {
		
		stage = 3;
		
		} else if (stage == 3) {
			sendDialogue(SEND_4_OPTIONS, "Ask Him...", "How do I train?",
					"Who are you?", "Whats this place?", "Send me Home");
			stage = 4;

		} else if (stage == 4) {	
		
		if (componentId == 1){
				sendEntityDialogue(SEND_3_TEXT_CHAT,
				new String[] { "Training Master", "Attack things to get xp?", "how else would you do it? with FOGS?", "Ha! dont make me laugh."}, IS_NPC, npcId, 9827);
			stage = 11;
		
			}else if (componentId == 2)	{
			sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { "Training Master", "Im the training Master!", "Gona' make all kinds of Gainz with me."}, IS_NPC, npcId, 9827);
			stage = 11;
			}else if (componentId == 3){
			sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { "Training Master", "This is the Yaks..."}, IS_NPC, npcId, 9827);
			stage = 5;
			}
			else if (componentId == 3){
			teleportPlayer(3087, 3492 ,0);	
			stage = 11;
			}
		 
		 }else if (stage == 5) {	
		
		sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { player.getDisplayName(), "But what about behind you?",
								"And what village is this?"}, IS_PLAYER, player.getIndex(), 9827);
					stage = 6;
		
		} else if (stage == 6) {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { "Training Master", "That place..... nothing interesting","I think you should focus on your training!"}, IS_NPC, npcId, 9827);
			stage = 7;
		
					
		} else if (stage == 7) {	
		
		sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { player.getDisplayName(), "I think I see people over there...", "I should go take a look"}, IS_PLAYER, player.getIndex(), 9827);
				stage = 8;
		}  else if (stage == 8) {
		sendEntityDialogue(SEND_3_TEXT_CHAT,
				new String[] { "Training Master", "NO YOU WONT!","Get Back to the Yaks!", "Dont try cross me!"}, IS_NPC, npcId, 9785);
			stage = 9;
		
			}
		 else if (stage == 9) {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { player.getDisplayName(), "I think your hiding something...", "What the hell is that!!!"}, IS_PLAYER, player.getIndex(), 9785);
		stage = 10;
		
			
	}else if (stage == 10) {
		if(player.KilledJugg == true){
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { "Training Master", "I Wont Fall for that again!", "hmmf! I think your training is over..."}, IS_NPC, npcId, 9785);
			stage = 11;
		player.sendMessage("Youve already completed this quest.");
			
		}
	else{
		if(player.TrainQuestStart == false){
			player.sendMessage("<col=ff0000> <shad=000>Quest started: </shad>Training is slavery!");
			player.TrainQuestStart = true;
		}
			player.sendMessage("You quickly distract him and jump the gate...");
			//npc.setNextForceTalk(new ForceTalk("Augh!"));
			player.setNextForceTalk(new ForceTalk("Sorry Mate!"));
			//npc.setNextAnimation(new Animation(7253));
			player.setNextAnimation(new Animation(10579));
			teleportPlayer(2326, 3802 ,0);	
			stage = 11;
		
			}
		}
	else if (stage == 11) {
			
			end();
			}
	}
	
		private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}
	private void teleportPlayer2(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}

	@Override
	public void finish() {

	}

}
