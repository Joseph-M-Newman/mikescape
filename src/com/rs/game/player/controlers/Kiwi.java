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

public class Kiwi extends Controler {

	
	
	
	@Override
	public void start(){
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
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
					removeControler();
					player.setNextWorldTile(new WorldTile(3087, 3491, 0));
				}
				loop++;
			}
		}, 0, 1);
		return false; 
	}

	@Override
	public boolean logout() {
		removeControler();
		player.setNextWorldTile(new WorldTile(3087, 3491, 0));
		return true;
	}

	@Override
	public void forceClose() {
		removeControler();
	player.setNextWorldTile(new WorldTile(3087, 3491, 0));
	}





	
	@Override
	public void moved() {
			if (!isInZone(player)) {
				
				removeControler();
				player.getPackets().sendGameMessage("You have Left the Kiwi Zone.");
			} 
				
	}
	
	private void teleportPlayer(Player player) {
		player.setNextWorldTile(new WorldTile(3087, 3491, 0));
	}

	public static boolean isInZone(Player player) {
		return player.getX() >= 1686 && player.getY() >= 5461
				&& player.getX() <= 1704 && player.getY() <= 5476 ;
	}
}