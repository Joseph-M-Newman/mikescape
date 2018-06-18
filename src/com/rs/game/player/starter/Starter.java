package com.rs.game.player.starter;

import com.rs.game.player.Player;

/**
 * This class handles the giving of a starter kit.
 * 
 * @author Emperial
 * 
 */
public class Starter {

	public static final int MAX_STARTER_COUNT = 1;

	public static void appendStarter(Player player) {
		String ip = player.getSession().getIP();
		int count = StarterMap.getSingleton().getCount(ip);
		player.starter = 1;
		if (count >= MAX_STARTER_COUNT) {
			return;
		}

		//player.getCutscenesManager().play(5);
		
		
		//player.getInventory().addItem(2437, 20); // attack pot
		//player.getInventory().addItem(2441, 20); // str pot
		//player.getInventory().addItem(2443, 20); // def pot
		//player.getInventory().addItem(2435, 20); // Prayer pot
		
		
		player.getInventory().addItem(379, 20); // 20 lobster
		player.getInventory().addItem(995, 2000000); // 4m gp
		player.getInventory().addItem(1007, 1); // Cape
		player.getInventory().addItem(1725, 1); // Ammy
		
		player.getInventory().addItem(1153, 1); // helm
		player.getInventory().addItem(3105, 1); // boots
		player.getInventory().addItem(1115, 1); // top
		player.getInventory().addItem(1067, 1); // bottom
		
		player.getInventory().addItem(556, 1000); // air rune
		player.getInventory().addItem(558, 1000); // mind rune
		player.getInventory().addItem(884, 100); // iron arrows
		player.getInventory().addItem(841, 1); // short bow
		
		player.getInventory().addItem(1323, 1); // iron scim
		//player.getInventory().addItem(1333, 1); // rune scim
		//player.getInventory().addItem(4587, 1); // d scim
		//player.getInventory().addItem(1215, 1); // dds
		player.softreset = true; 

		player.getHintIconsManager().removeUnsavedHintIcon();
		player.getMusicsManager().reset();
		player.getCombatDefinitions().setAutoRelatie(false);
		player.getCombatDefinitions().refreshAutoRelatie();
		player.getCutscenesManager().play(5);
		StarterMap.getSingleton().addIP(ip);
	}

}
