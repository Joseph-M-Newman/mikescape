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

public class PvP extends Controler {

	
	
	
	@Override
	public void start(){
		//setHat(player, new Item(PvPHat));
		//setWeapon(player, new Item(10171));
		player.setCanPvp(true);
		sendInterfaces();
	}
	
	/*public static void setWeapon(Player player, Item weapon) {
		player.getEquipment().getItems().set(Equipment.SLOT_CAPE, weapon);
		player.getEquipment().refresh(Equipment.SLOT_CAPE);
		player.getAppearence().generateAppearenceData();
	}*/
	
	public static void setHat(Player player, Item weapon) {
		player.getEquipment().getItems().set(Equipment.SLOT_HAT, weapon);
		player.getEquipment().refresh(Equipment.SLOT_HAT);
		player.getAppearence().generateAppearenceData();
	}
	
	/*public static int PvPHat{
	if(Player.PvPPoints > 0) {
		PvPhat = 20801;
	}
	else if (Player.PvPPoints > 10) {
		PvPhat = 20802;
	}
	else if (Player.PvPPoints > 100) {
		PvPhat = 20803;
	}
	else if (Player.PvPPoints > 500) {
		PvPhat = 20804;
	}
	else if (Player.PvPPoints > 1000) {
		PvPhat = 20805;
	}
	else if (Player.PvPPoints > 2000) {
		PvPhat = 20806;
	}
	}*/
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
					Player killer = player.getMostDamageReceivedSourcePlayer();
					if (killer != null) {
						killer.removeDamage(player);
						//killer.increaseKillCount(player);
						killer.PvPPoints += 1;
						killer.getPackets().sendGameMessage("you have gained a PvP Point");
					}
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false; 
	}

	@Override
	public boolean logout() {
		player.setNextWorldTile(new WorldTile(3087, 3491, 0));
		return true;
	}

	@Override
	public void forceClose() {
		player.setCanPvp(false);
	}





@Override
public boolean processObjectTeleport(WorldTile toTile) {
Long teleblock = (Long) player.getTemporaryAttributtes().get(
"TeleBlocked");
if (teleblock != null && teleblock > Utils.currentTimeMillis()) {
player.getPackets().sendGameMessage(
"A mysterious force prevents you from teleporting.");
return false;
}
return true;
}
	
	@Override
	public void moved() {
			if (!isInPvP(player)) {
				player.setCanPvp(false);
				removeControler();
				player.setNextForceTalk(new ForceTalk("-PvP Disabled-"));
				player.getPackets().sendGameMessage("You have Left the PvP Zone.");
			} else 
				player.setCanPvp(true);
				
	}
	
	private void teleportPlayer(Player player) {
			player.setNextWorldTile(new WorldTile(3087, 3493, 0));
	}

	public static boolean isInPvP(Player player) {
		return player.getX() >= 3086 && player.getY() >= 3487
				&& player.getX() <= 3089 && player.getY() <= 3499 ;
	}
}