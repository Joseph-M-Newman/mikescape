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

public class PvPDz extends Controler {

	
	
	
	@Override
	public void start(){
		player.setCanPvp(true);
		sendInterfaces();
	}
	
	
	
	public static void setHat(Player player, Item weapon) {
		player.getEquipment().getItems().set(Equipment.SLOT_HAT, weapon);
		player.getEquipment().refresh(Equipment.SLOT_HAT);
		player.getAppearence().generateAppearenceData();
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
					//same ip no kdr
						if (player.getSession().getIP().equals(killer.getSession().getIP())) {
								player.getPackets().sendGameMessage("You cannot Gain points with someone on the same IP.");
						}
						else{
								killer.removeDamage(player);
								killer.DZPVPPoints += 1;
								killer.getPackets().sendGameMessage("DZ PvP points increased! You now have: " + player.DZPVPPoints + " Points.");
					
						}
					
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
		player.setLocation(new WorldTile(3795, 2851, 0));
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
				player.setNextForceTalk(new ForceTalk("*PvP Disabled*"));
				player.getPackets().sendGameMessage("You have Left the PvP Zone.");
			} else 
				player.setCanPvp(true);
				
	}
	
	private void teleportPlayer(Player player) {
			player.setNextWorldTile(new WorldTile(3087, 3493, 0));
	}

	public static boolean isInPvP(Player player) {
		return player.getX() >= 3788 && player.getY() >= 2849
				&& player.getX() <= 3799 && player.getY() <= 2855 ;
	}
}