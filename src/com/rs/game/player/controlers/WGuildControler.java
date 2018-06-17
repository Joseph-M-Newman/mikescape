package com.rs.game.player.controlers;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.wguild.AnimatedArmour;
import com.rs.game.player.HintIcon;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class WGuildControler extends Controler {


private int tick;
	@Override
	public void start() {
		sendInterfaces();
	player.getPackets().sendGameMessage("WGuild on.");
			
	}
	
	@Override
	public void process() {
		sendInterfaces();
		if(player.inClopsRoom()) {
			if(player.GuildTokens <= 20) {
				player.getPackets().sendGameMessage("Your time is about to run out. Please leave the cyclops room.");
			}
			if(player.GuildTokens <= 10) {
				player.setNextWorldTile(new WorldTile(2843, 3536, 2));
				player.getPackets().sendGameMessage("Your time has run out.");
				player.setInClopsRoom(false);
				return;
			}
			if(tick == 20) {
				player.GuildTokens -= 10;
				sendInterfaces();
			if(player.wgmess == true){
				player.getPackets().sendGameMessage("Your tokens reduce by 10.", true);
				}
				tick = 0;
			}
			tick++;
		}
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		if(player.inClopsRoom()) {
			player.getDialogueManager()
			.startDialogue("SimpleMessage",
					new Object[] {"A magical force prevents you from teleporting." });
			return false;
		} else {
			stop();
			return true;
		}
	}
	
	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		if(player.inClopsRoom()) {
			player.getDialogueManager()
			.startDialogue("SimpleMessage",
					new Object[] {"A magical force prevents you from teleporting." });
			return false;
		} else {
			stop();
			return true;
		}
	}
	


	

	@Override
	public boolean processObjectTeleport(WorldTile tile) {
		if(player.inClopsRoom()) {
			player.getDialogueManager()
			.startDialogue("SimpleMessage",
					new Object[] {"A magical force prevents you from teleporting." });
			return false;
		} else {
			stop();
			return true;
		}
	}
	
	public void stop() {
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 10 : 8);
		removeControler();
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		start();
		if(object.getId() == 7353 || object.getId() == 7352) {
			if((player.getX() == 2846) || (player.getX() == 2845)) {
				enterClopsRoom();
			} else if((player.getX() == 2847)|| (player.getX() == 2848)) {
				leaveClopsRoom();
			}
			return false;
		}
		return true;
	}
	
	@Override
	public boolean logout() {
		return false;
	}
	
	@Override
	public boolean login() {
		start();
		return false;
	}
	
	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		if(interfaceId == 271) {
			if(player.getPrayer().isAncientCurses()) {
				player.getPackets().sendGameMessage("The warriors of this guild forbid such dark prayers.");
				return false;
			}
		}
		return true;
	}
	
	public void enterClopsRoom() {
		if(player.GuildTokens < 100) {
			player.getPackets().sendGameMessage("You must have over 100 tokens to enter.");
			return;
		}
		player.setInClopsRoom(true);
		player.setNextWorldTile(new WorldTile(player.getX()+1, player.getY(), player.getPlane()));
	}
	
	public void leaveClopsRoom() {
		player.setInClopsRoom(false);
		player.setNextWorldTile(new WorldTile(player.getX()-1, player.getY(), player.getPlane()));
	}
	
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 10 : 8, 1057);
		player.getPackets().sendConfig(2030, player.GuildTokens);
	}
	
	
}