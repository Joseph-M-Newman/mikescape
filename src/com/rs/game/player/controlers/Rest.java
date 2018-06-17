package com.rs.game.player.controlers;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Rest extends Controler {

	
	
	
	@Override
	public void start(){
		int restemote = Utils.getRandom(2);
		int seat = Utils.getRandom(5);
	
	if(player.getRights() >= 12){
		player.getPackets().sendPanelBoxMessage(String.valueOf(seat) + String.valueOf(restemote));
	}
//emote
		
		 if(restemote == 0){
			player.getAppearence().setRenderEmote(1482);
		}
		else if(restemote == 1){
			player.getAppearence().setRenderEmote(1484);
		}
	//	else if(restemote == 2){
		//	player.getAppearence().setRenderEmote(1486);
		//}
		else if(restemote == 2){
			player.getAppearence().setRenderEmote(1489);
		}
		//else if(restemote == 4){
			//player.getAppearence().setRenderEmote(1488);
		//}
	//seat
		if(seat == 0){
			player.setNextWorldTile(new WorldTile(3090, 3495, 0));
			player.setNextFaceWorldTile(new WorldTile(3091, 3495, 0));
		}
		else if(seat == 1){
			player.setNextWorldTile(new WorldTile(3090, 3496, 0));
			player.setNextFaceWorldTile(new WorldTile(3091, 3496, 0));
		}
		
		
		else if(seat == 2){
			player.setNextWorldTile(new WorldTile(3082, 3492, 0));
			player.setNextFaceWorldTile(new WorldTile(3081, 3492, 0));
		}
		else if(seat == 3){
			player.setNextWorldTile(new WorldTile(3082, 3493, 0));
			player.setNextFaceWorldTile(new WorldTile(3081, 3493, 0));
		}
		else if(seat == 4){
			player.setNextWorldTile(new WorldTile(3082, 3494, 0));
			player.setNextFaceWorldTile(new WorldTile(3081, 3494, 0));
		}
		else if(seat == 5){
			player.setNextWorldTile(new WorldTile(3082, 3491, 0));
			player.setNextFaceWorldTile(new WorldTile(3081, 3491, 0));
		}
	
	}
	
	
	

	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 10 : 19, 789);
	}

	@Override
	public boolean login() {
		removeControler();
		return false;
	}
	
	/*@Override
	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (interfaceId == 387 && componentId == 9) {
			player.getPackets().sendGameMessage(
					"You can't remove your Cape.");
			return false;
		}
		return true;
	}*/

	
	
	/*public boolean canEquip(int slotId, int itemId) {
		if (slotId == Equipment.SLOT_WEAPON) {
			player.getPackets().sendGameMessage(
					"You can't remove your Cape.");
			return false;
		}
		return true;
	}*/

	
	
	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("Oh dear, you have died.");
				} else if (loop == 3) {
					
					
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextAnimation(new Animation(-1));
					player.getAppearence().setRenderEmote(-1);
				} else if (loop == 4) {
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false; 
	}

	@Override
	public boolean logout() {
	removeControler();
		player.getAppearence().setRenderEmote(-1);
		player.setNextWorldTile(new WorldTile(3086, 3490, 0));
		removeControler();
		return true;
	}

	@Override
	public void forceClose() {
		removeControler();
		player.getAppearence().setRenderEmote(-1);
		player.setNextWorldTile(new WorldTile(3086, 3490, 0));
		
	}



@Override
public boolean processObjectTeleport(WorldTile toTile) {
		player.getAppearence().setRenderEmote(-1);
		//player.setLocation(new WorldTile(3795, 2851, 0));
		
return true;
}
	
	@Override
	public void moved() {
			if (!isRest(player)) {
				player.getAppearence().setRenderEmote(-1);
			removeControler();
	}
				
	}
	
	private void teleportPlayer(Player player) {
			player.setNextWorldTile(new WorldTile(3087, 3493, 0));
		player.getAppearence().setRenderEmote(-1);
		removeControler();
		
			}

	public static boolean isRest(Player player) {
		return player.getX() == 3090 && player.getY() == 3495
			|| player.getX() == 3090 && player.getY() == 3496
			|| player.getX() == 3082 && player.getY() == 3491
			|| player.getX() == 3082 && player.getY() == 3492
			|| player.getX() == 3082 && player.getY() == 3493
			|| player.getX() == 3082 && player.getY() == 3494;
	}
	
//Failed hard.
	/*public static boolean Seat1(Player player) {
		return player.getX() == 3090 && player.getY() == 3495
			|| player.getX() == 3090 && player.getY() == 3496;
	}
	public static boolean Seat2(Player player) {
		return player.getX() == 3082 && player.getY() == 3491
			|| player.getX() == 3082 && player.getY() == 3492
			|| player.getX() == 3082 && player.getY() == 3493
			|| player.getX() == 3082 && player.getY() == 3494;
	}*/
}