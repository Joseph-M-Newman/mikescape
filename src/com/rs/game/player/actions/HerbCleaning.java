package com.rs.game.player.actions;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class HerbCleaning {

	public static enum Herbs {
		//Name(grimy,xp x1600,level,clean
		GUAM(199, 1, 3, 249), MARRENTILL(201, 2, 5, 251), TARROMIN(203, 3,
				11, 253), HARRALANDER(205, 4, 20, 255), RANARR(207, 5, 25,
				257), TOADFLAX(3049, 5.5, 30, 2998), SPIRIT_WEED(12174, 6, 35,
				12172), IRIT(209, 6.5, 40, 259), WERGALI(14836, 7, 41, 14854), AVANTOE(
				211, 8, 48, 261), KWUARM(213, 9, 54, 263), SNAPDRAGON(3051,
				9.5, 59, 3000), CADANTINE(215, 10, 65, 265), LANTADYME(2485,
				10.5, 67, 2481), DWARF_WEED(217, 11, 70, 267), TORSTOL(219,
				11.5, 75, 269);

		private int herbId;
		private int level;
		private int cleanId;
		private double xp;

		Herbs(int herbId, double xp, int level, int cleanId) {
			this.herbId = herbId;
			this.xp = xp;
			this.level = level;
			this.cleanId = cleanId;
		}

		public int getHerbId() {
			return herbId;
		}

		public double getExperience() {
			return xp;
		}

		public int getLevel() {
			return level;
		}

		public int getCleanId() {
			return cleanId;
		}
	}

	public static Herbs getHerb(int id) {
		for (final Herbs herb : Herbs.values())
			if (herb.getHerbId() == id)
				return herb;
		return null;
	}

	public static boolean clean(final Player player, Item item, final int slotId) {
		final Herbs herb = getHerb(item.getId());
		if (herb == null)
			return false;
		if (player.getSkills().getLevel(Skills.HERBLORE) < herb.getLevel()) {
			player.getPackets().sendGameMessage(
					"You do not have the required level to clean this.", true);
			return true;
		}
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				Item i = player.getInventory().getItem(slotId);
				if (i == null)
					return;
				if (i.getId() != herb.getHerbId())
					return;
				i.setId(herb.getCleanId());
				player.getInventory().refresh(slotId);
				player.getSkills().addXp(Skills.HERBLORE, herb.getExperience());
				player.getPackets()
						.sendGameMessage("You clean the herb.", true);
			}

		});
		return true;
	}

}
