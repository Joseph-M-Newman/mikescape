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

import com.rs.utils.ShopsHandler;

public class Nigel extends Dialogue {


	private int npcId;
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Nigel", "Howdy there, im Nigel","Help me with my flour? Or buy some goods!"}, IS_NPC, npcId, 9500);
	
	}

	//final NPC npc = World.getNPCs().get(npcId);
	
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_2_OPTIONS, "What to do?", "See Shop.",
					"Help with the flour.");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == 1){
				 if (!player.isDonator()) {
						player.getPackets().sendGameMessage("You must be a Donator to do this.");
						stage = 11;
					}
				else{
					ShopsHandler.openShop(player, 20);
					}
			}else if (componentId == 2){
				 if (!player.isDonator()) {
						player.getPackets().sendGameMessage("You must be a Donator to do this.");
						stage = 11;
					}
				else{
					stage = 5;
					}
			}
			}
			else if (stage == 5) {	
		
		sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "So how can I help with the flour"}, IS_PLAYER, player.getIndex(), 9827);
					stage = 6;
		
		} else if (stage == 6) {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { "Nigel", "Well first go pick some wheat from over there.","Then bring it to the top.."}, IS_NPC, npcId, 9827);
			stage = 7;
		
					
		} else if (stage == 7) {	
		
		sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { "Nigel","Chuck it in the hopper", "Then pull the leaver"}, IS_NPC, npcId, 9827);
				stage = 8;
		}  else if (stage == 8) {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { "Nigel", "Thats it!","Ill reward you the more you help!"}, IS_NPC, npcId, 9785);
			stage = 11;
		
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
