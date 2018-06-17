package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class ArenaOut extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hi i am the teleport staller. ",
						" Want to escape my lair?" }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Let me out!!!" },
					IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendDialogue(SEND_5_OPTIONS, "My name is?", "Nastroth.",
					"Mandrith.", "Poanizer.", "James Blonde.", "No idea.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 1)
				  stage = 3;
				sendDialogue(SEND_5_OPTIONS, "Click 'Cat'",
						"Dog.", "Donkey", "Horse",
						"Cat", "Chair");
			 if (componentId == 2)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 4)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 5) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			}
			
			
		} else if (stage == 3) {
			if (componentId == 1) {
				player.getPackets().sendGameMessage("Wrong.");
				  
			} else if (componentId == 2)
				player.getPackets().sendGameMessage("Wrong.");
				
			else if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 4)
				stage = 4;
				sendDialogue(SEND_5_OPTIONS, "Where are you?",
						"At Bandos", "The Deathmatch Arena", "Fight Pits.",
						"Home", "My House.");
			 if (componentId == 5) {
				player.getPackets().sendGameMessage("Wrong.");
				  
			}
		} else if (stage == 4) {
			if (componentId == 1)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 2) {
				stage = 5;
				sendDialogue(SEND_5_OPTIONS, "Is this New Zealand?",
						"Yep", "Its Australia mate.", "Im in England", "Its ma Garden",
						"No, Its the DeathMatchArena");
			} else if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				  
			else if (componentId == 4) {
				player.getPackets().sendGameMessage("Wrong.");
				  
			} else if (componentId == 5) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			}
		} else if (stage == 5) {
			if (componentId == 1) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			} else if (componentId == 2)
				player.getPackets().sendGameMessage("Wrong.");
				 
			else if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				
			else if (componentId == 4) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			} else if (componentId == 5) {
				stage = 6;
				sendDialogue(SEND_5_OPTIONS, "What is 1 + 1",
						"69", "2", "16", "10",
						"800000");
			}
		} else if (stage == 6) {
			if (componentId == 1) {
				player.getPackets().sendGameMessage("Wrong.");
				  
			} else if (componentId == 2)
				stage = 7;
				sendDialogue(SEND_5_OPTIONS, "What do you want to do.",
						"Die.", "Get out of here.", "Stay and fight", "Go do pvm",
						"To late im dead.");
			 if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				 
			else if (componentId == 4) {
				player.getPackets().sendGameMessage("Wrong.");
				
			} else if (componentId == 5) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			}
		} else if (stage == 7) {
			if (componentId == 1)
				player.getPackets().sendGameMessage("Wrong.");
				 
			else if (componentId == 2)
				 player.useStairs(3170, new WorldTile(3092,3485, 0), 2, 3,"You leave the arena."); 
			else if (componentId == 3)
				player.getPackets().sendGameMessage("Wrong.");
				 
			else if (componentId == 4)
				player.getPackets().sendGameMessage("Wrong.");
				 
			else if (componentId == 5) {
				player.getPackets().sendGameMessage("Wrong.");
				 
			}
		}
	}

	private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("GodWars");
	}

	@Override
	public void finish() {

	}
}
