package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FishingSpotsHandler;
import com.rs.utils.Utils;

/**
 * Handles the fishing.
 * 
 * @author Raghav/Own4g3.
 * 
 */
public class Fishing extends Action {

	public enum Fish {
        //NAME(raw, level, xp)
		SHRIMP(317, 1, 1), SARDINES(327, 5, 2), HERRING(345, 10, 3), 
		ANCHOVIES(321, 15, 4), MACKEREL(353, 16, 4), TROUT(335, 20, 6), 
		COD(341, 23, 6), PIKE(349, 25, 7), SALMON(331, 30, 8), SEAWEED(401, 30, 0),
		OYSTER(407, 30, 0), TUNA(359, 35, 10), LOBSTER(377, 40, 13), BASS(363, 46, 15), 
		SWORDFISH(371, 50, 19), MONKFISH(7944, 62, 23), SHARK(383, 76, 40), 
		SEA_TURTLE(395, 79, 63), MANTA(389, 81, 68), CAVE_FISH(15264, 85, 63), 
		ROCKTAIL(15270, 90, 80);
		
		 
		
		
		
		

		private final int id, level, xp;

		private Fish(int id, int level, int xp) {
			this.id = id;
			this.level = level;
			this.xp = xp;
		}

		public int getId() {
			return id;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}

	public enum FishingSpots {
		//NAME(npc,option,rod,bait,animation
		BAIT(327, 2, 307, 313, new Animation(622), Fish.SARDINES, Fish.HERRING), 
		BAIT4(8842, 1, 307, 15263, new Animation(622), Fish.ROCKTAIL), 
		NET(327, 1, 303, -1, new Animation(621), Fish.SHRIMP,Fish.ANCHOVIES), 
		LURE(328, 1, 309, 314, new Animation(622),Fish.TROUT, Fish.SALMON), 
		LURE2(329, 1, 309, 314,new Animation(622), Fish.TROUT, Fish.SALMON), 
		BAIT2(328, 2,307, 313, new Animation(622), Fish.PIKE),
		BAIT3(329, 2, 307,313, new Animation(622), Fish.PIKE, Fish.CAVE_FISH),
		CAGE(6267,1, 301, -1, new Animation(619), Fish.LOBSTER),
		CAGE2(312, 1,301, -1, new Animation(619), Fish.LOBSTER), 
		HARPOON(312, 2,311, -1, new Animation(618), Fish.TUNA, Fish.SWORDFISH,Fish.SHARK),
		BIG_NET(313, 1, 305, -1, new Animation(620),Fish.MACKEREL, Fish.COD, Fish.BASS, Fish.SEAWEED, Fish.OYSTER), 
		HARPOON2(313, 2, 311, -1, new Animation(618), Fish.TUNA, Fish.SWORDFISH,Fish.SHARK), 
		NET2(952, 1, 305, -1, new Animation(621), Fish.SHARK, Fish.SEA_TURTLE, Fish.MANTA), 
		HARPOON3(952, 2, 303, -1,new Animation(618), Fish.MONKFISH);

		private final Fish[] fish;
		private final int id, option, tool, bait;
		private final Animation animation;

		static final Map<Integer, FishingSpots> spot = new HashMap<Integer, FishingSpots>();

		public static FishingSpots forId(int id) {
			return spot.get(id);
		}

		static {
			for (FishingSpots spots : FishingSpots.values())
				spot.put(spots.id | spots.option << 24, spots);
		}

		private FishingSpots(int id, int option, int tool, int bait,
				Animation animation, Fish... fish) {
			this.id = id;
			this.tool = tool;
			this.bait = bait;
			this.animation = animation;
			this.fish = fish;
			this.option = option;
		}

		public Fish[] getFish() {
			return fish;
		}

		public int getId() {
			return id;
		}

		public int getOption() {
			return option;
		}

		public int getTool() {
			return tool;
		}

		public int getBait() {
			return bait;
		}

		public Animation getAnimation() {
			return animation;
		}
	}

	/**
	 * The fishing spot, where the player is fishing.
	 */
	private FishingSpots spot;

	/**
	 * The npc, fishing spot is an npc.
	 */
	private NPC npc;
	private WorldTile tile;
	/**
	 * The fish id.
	 */
	private int fishId;

	/**
	 * 
	 */
	private final int[] BONUS_FISH = { 341, 349, 401, 407 };

	/**
	 * If a player is capable to catch 2 fishes.
	 */
	private boolean multipleCatch;

	/**
	 * Constructs a new {@code Fishing} {@code Object}.
	 * 
	 * @param spot
	 *            The fishing spot.
	 * @param npc
	 *            The fishing npc.
	 */
	public Fishing(FishingSpots spot, NPC npc) {
		this.spot = spot;
		this.npc = npc;
		tile = new WorldTile(npc);
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		fishId = getRandomFish(player);
		if (spot.getFish()[fishId] == Fish.TUNA
				|| spot.getFish()[fishId] == Fish.SHARK
				|| spot.getFish()[fishId] == Fish.SWORDFISH) {
			if (Utils.getRandom(50) <= 5) {
				if (player.getSkills().getLevel(Skills.AGILITY) >= spot
						.getFish()[fishId].getLevel())
					multipleCatch = true;
			}
		}
		player.getPackets().sendGameMessage("You attempt to capture a fish...");
		setActionDelay(player, getFishingDelay(player));
		return true;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(spot.getAnimation());
		return checkAll(player);
	}

	/**
	 * Gets the fishing delay.
	 * 
	 * @param player
	 *            The player.
	 * @return Delay
	 */
	private int getFishingDelay(Player player) {
		int playerLevel = player.getSkills().getLevel(Skills.FISHING);
		int fishLevel = spot.getFish()[fishId].getLevel();
		int modifier = spot.getFish()[fishId].getLevel();
		int randomAmt = Utils.random(4);
		double cycleCount = 1, otherBonus = 0;
		if (player.getFamiliar() != null)
			otherBonus = getSpecialFamiliarBonus(player.getFamiliar().getId());
		cycleCount = Math
				.ceil(((fishLevel + otherBonus) * 50 - playerLevel * 10)
						/ modifier * 0.25 - randomAmt * 4);
		if (cycleCount < 1) {
			cycleCount = 1;
		}
		int delay = (int) cycleCount + 1;
		delay /= player.getAuraManager().getFishingAccurayMultiplier();
		return delay;

	}

	private int getSpecialFamiliarBonus(int id) {
		switch (id) {
		case 6796:
		case 6795:// rock crab
			return 1;
		}
		return -1;
	}

	/**
	 * Gets the random fish.
	 * 
	 * @param player
	 *            The player
	 * @return Random fish index.
	 */
	private int getRandomFish(Player player) {
		int random = Utils.random(spot.getFish().length);
		int difference = player.getSkills().getLevel(Skills.FISHING)
				- spot.getFish()[random].getLevel();
		if (difference < -1)
			return random = 0;
		if (random < -1)
			return random = 0;
		return random;
	}

	@Override
	public int processWithDelay(Player player) {
		addFish(player);
		return getFishingDelay(player);
	}

	/**
	 * Adds the fish in player's inventory and give exp.
	 * 
	 * @param player
	 *            The player.
	 */
	private void addFish(Player player) {
		Item fish = new Item(spot.getFish()[fishId].getId(), multipleCatch ? 2
				: 1);
		player.getPackets().sendGameMessage(getMessage(fish));
		player.getInventory().deleteItem(spot.getBait(), 1);
		player.getSkills()
				.addXp(Skills.FISHING, spot.getFish()[fishId].getXp());
		player.getInventory().addItem(fish);
		if (player.getFamiliar() != null) {
			if (Utils.getRandom(50) == 0
					&& getSpecialFamiliarBonus(player.getFamiliar().getId()) > 0) {
				player.getInventory()
						.addItem(
								new Item(BONUS_FISH[Utils
										.getRandom(BONUS_FISH.length)]));
				player.getSkills().addXp(Skills.FISHING, 5.5);
			}
		}
		fishId = getRandomFish(player);
		if (Utils.getRandom(50) == 0 && FishingSpotsHandler.moveSpot(npc))
			player.setNextAnimation(new Animation(-1));
	}

	/**
	 * Get's the message.
	 * 
	 * @param fish
	 *            The fish catch player is getting.
	 * @return Message
	 */
	private String getMessage(Item fish) {
		if (spot.getFish()[fishId] == Fish.ANCHOVIES
				|| spot.getFish()[fishId] == Fish.SHRIMP)
			return "You manage to catch some "
					+ fish.getDefinitions().getName().toLowerCase() + ".";
		else if (multipleCatch)
			return "Your quick reactions allow you to catch two "
					+ fish.getDefinitions().getName().toLowerCase() + ".";
		else
			return "You manage to catch a "
					+ fish.getDefinitions().getName().toLowerCase() + ".";
	}

	private boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.FISHING) < spot.getFish()[fishId]
				.getLevel()) {
			player.getDialogueManager().startDialogue(
					"SimpleMessage",
					"You need a fishing level of "
							+ spot.getFish()[fishId].getLevel()
							+ " to fish here.");
			return false;
		}
		if (!player.getInventory().containsOneItem(spot.getTool())) {
			player.getPackets()
					.sendGameMessage(
							"You need a "
									+ new Item(spot.getTool()).getDefinitions()
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		if (!player.getInventory().containsOneItem(spot.getBait())
				&& spot.getBait() != -1) {
			player.getPackets()
					.sendGameMessage(
							"You don't have "
									+ new Item(spot.getBait()).getDefinitions()
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		if (!player.getInventory().hasFreeSlots()) {
			player.setNextAnimation(new Animation(-1));
			player.getDialogueManager().startDialogue("SimpleMessage",
					"You don't have enough inventory space.");
			return false;
		}
		if (tile.getX() != npc.getX() || tile.getY() != npc.getY())
			return false;
		return true;
	}

	@Override
	public void stop(Player player) {
	}
}
