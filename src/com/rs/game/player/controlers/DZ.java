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

public class DZ extends Controler {

	
	
	
	@Override
	public void start(){
		
	
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
					
					
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextAnimation(new Animation(-1));
					player.getAppearence().setRenderEmote(-1);
					player.getAppearence().transformIntoNPC(-1);
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
		player.getAppearence().transformIntoNPC(-1);
		player.getAppearence().setRenderEmote(-1);
		player.setNextWorldTile(new WorldTile(3794, 2844, 0));
		removeControler();
		return true;
	}

	@Override
	public void forceClose() {
		removeControler();
		player.getAppearence().transformIntoNPC(-1);
		player.getAppearence().setRenderEmote(-1);
	player.setNextWorldTile(new WorldTile(3794, 2844, 0));
		
	}



@Override
public boolean processObjectTeleport(WorldTile toTile) {
		player.getAppearence().transformIntoNPC(-1);
		player.getAppearence().setRenderEmote(-1);
		
return true;
}
	
	@Override
	public void moved() {
			if (!isNPCDZ(player)) {
				player.getAppearence().transformIntoNPC(-1);
				player.getAppearence().setRenderEmote(-1);
			removeControler();
	}
				
	}
	
	private void teleportPlayer(Player player) {
		player.getAppearence().transformIntoNPC(-1);
		player.getAppearence().setRenderEmote(-1);
		removeControler();
		
			}

public static boolean isNPCDZ(Player player) {
		return player.getX() >= 3773 && player.getY() >= 2811
				&& player.getX() <= 3849 && player.getY() <= 2882 ;
	}

}