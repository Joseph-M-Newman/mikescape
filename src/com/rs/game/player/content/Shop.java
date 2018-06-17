package com.rs.game.player.content;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.Inventory;
import com.rs.utils.Utils;
import com.rs.utils.Prices;
import com.rs.utils.ItemBonuses;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;

public class Shop {

	private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator
			.generateKey();

	private static final int MAX_SHOP_ITEMS = 36;
	public static final int COINS = 995;
	public static final int PK_TOKENS = 1464;
	public static final int DONATOR_TOKEN = 8851;
	public static final int SANTA_HAT = 1050;
	public static final int SLAYER_COINS = 13650;
	public static final int CASTLE_WARS_TICKET = 4067;
	public int id = 0;

	private String name;
	private Item[] mainStock;
	private int[] defaultQuantity;
	private Item[] generalStock;
	private int money;
	private CopyOnWriteArrayList<Player> viewingPlayers;

	public Shop(String name, int money, Item[] mainStock, boolean isGeneralStore, int id) {
		viewingPlayers = new CopyOnWriteArrayList<Player>();
		this.name = name;
		this.money = money;
		this.mainStock = mainStock;
		this.id = id;
		defaultQuantity = new int[mainStock.length];
		for (int i = 0; i < defaultQuantity.length; i++)
			defaultQuantity[i] = mainStock[i].getAmount();
		if (isGeneralStore && mainStock.length < MAX_SHOP_ITEMS)
			generalStock = new Item[MAX_SHOP_ITEMS - mainStock.length];
	}

	public boolean isGeneralStore() {
		return generalStock != null;
	}
	
	public void buyDung(Player player, int clickSlot, int quantity) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage(
					"There is no stock of that item at the moment.");
			return;
		}
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getDungPrice(item, dq);
		int amountCoins = player.getInventory().numberOf(18201);
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();

		boolean enoughCoins = maxQuantity >= buyQ;
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You don't have enough Coins.");
			buyQ = maxQuantity;
		} else if (quantity > buyQ){
			player.getPackets().sendGameMessage(
					"The shop has run out of stock.");
		}if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			player.getInventory().deleteItem(18201,(totalPrice));
			player.getInventory().addItem(item.getId(), buyQ);
			refreshShop();
			sendInventory(player);
		}
	}
	public void buyVote(Player player, int clickSlot, int quantity) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage(
					"There is no stock of that item at the moment.");
			return;
		}
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getVotePrice(item, dq);
		int amountCoins = player.VotePoints;
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();

		boolean enoughCoins = maxQuantity >= buyQ;
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You don't have enough points, you have "+player.VotePoints+" points.");
			buyQ = maxQuantity;
		} else if (quantity > buyQ)
			player.getPackets().sendGameMessage(
					"The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			player.VotePoints -= totalPrice;
			player.getInventory().addItem(item.getId(), buyQ);
			refreshShop();
			sendInventory(player);
		}
	}
	
	public void buySlay(Player player, int clickSlot, int quantity) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage(
					"There is no stock of that item at the moment.");
			return;
		}
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getSlayPrice(item, dq);
		int amountCoins = player.SlayerPoints;
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();

		boolean enoughCoins = maxQuantity >= buyQ;
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You don't have enough points, you have "+player.SlayerPoints+" points.");
			buyQ = maxQuantity;
		} else if (quantity > buyQ)
			player.getPackets().sendGameMessage(
					"The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			player.SlayerPoints -= totalPrice;
			player.getInventory().addItem(item.getId(), buyQ);
			refreshShop();
			sendInventory(player);
		}
	}

	public void addPlayer(final Player player) {
		viewingPlayers.add(player);
		player.getTemporaryAttributtes().put("Shop", this);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				viewingPlayers.remove(player);
				player.getTemporaryAttributtes().remove("Shop");
			}
		});
		player.getPackets().sendConfig(118, MAIN_STOCK_ITEMS_KEY); // sets
																	// mainstock
																	// items set
		player.getPackets().sendConfig(1496, -1); // sets samples items set
		player.getPackets().sendConfig(532, money);
		sendStore(player);
		player.getPackets().sendGlobalConfig(199, -1);// unknown
		player.getInterfaceManager().sendInterface(620); // opens shop
		for (int i = 0; i < MAX_SHOP_ITEMS; i++)
			player.getPackets().sendGlobalConfig(
					946 + i,
					i < defaultQuantity.length ? defaultQuantity[i]
							: generalStock != null ? 0 : -1);// prices
		player.getPackets().sendGlobalConfig(1241, 16750848);// unknown
		player.getPackets().sendGlobalConfig(1242, 15439903);// unknown
		player.getPackets().sendGlobalConfig(741, -1);// unknown
		player.getPackets().sendGlobalConfig(743, -1);// unknown
		player.getPackets().sendGlobalConfig(744, 0);// unknown
		if (generalStock != null)
			player.getPackets().sendHideIComponent(620, 19, false); // unlocks
																	// general
																	// store
																	// icon
		player.getPackets().sendIComponentSettings(620, 25, 0,
				getStoreSize() * 6, 1150); // unlocks stock slots
		sendInventory(player);
		player.getPackets().sendIComponentText(620, 20, name);
	}

	public void sendInventory(Player player) {
		player.getInterfaceManager().sendInventoryInterface(621);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendUnlockIComponentOptionSlots(621, 0, 0, 27, 0,
				1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(621, 0, 93, 4, 7,
				"Value", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "Examine");
	}

	public int getSlotId(int clickSlotId) {
		return clickSlotId / 6;
	}

	public void buy(Player player, int clickSlot, int quantity) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage(
					"There is no stock of that item at the moment.");
			return;
		}
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getBuyPrice(item, dq);
		int amountCoins = player.getInventory().getItems().getNumberOf(money);
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();

		boolean enoughCoins = maxQuantity >= buyQ;
		//if (player.getRights() <= 11) {
			if (!enoughCoins) {
				player.getPackets().sendGameMessage("You don't have enough coins.");
				buyQ = maxQuantity;
				} 
			//}
	//	else{
		if (quantity > buyQ)
			player.getPackets().sendGameMessage(
					"The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			player.getInventory().deleteItem(money, totalPrice);
			player.getInventory().addItem(item.getId(), buyQ);
			item.setAmount(item.getAmount() - buyQ);
			if (item.getAmount() <= 0 && slotId >= mainStock.length)
				generalStock[slotId - mainStock.length] = null;
			refreshShop();
			sendInventory(player);
		}
	}
	//}

	public void restoreItems() {
		boolean needRefresh = false;
		for (int i = 0; i < mainStock.length; i++) {
			if (mainStock[i].getAmount() < defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + 1);
				needRefresh = true;
			} else if (mainStock[i].getAmount() > defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + -1);
				needRefresh = true;
			}
		}
		if (generalStock != null) {
			for (int i = 0; i < generalStock.length; i++) {
				Item item = generalStock[i];
				if (item == null)
					continue;
				item.setAmount(item.getAmount() - 1);
				if (item.getAmount() <= 0)
					generalStock[i] = null;
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}

	private boolean addItem(int itemId, int quantity) {
		for (Item item : mainStock) {
			if (item.getId() == itemId) {
				item.setAmount(item.getAmount() + quantity);
				refreshShop();
				return true;
			}
		}
		if (generalStock != null) {
			for (Item item : generalStock) {
				if (item == null)
					continue;
				if (item.getId() == itemId) {
					item.setAmount(item.getAmount() + quantity);
					refreshShop();
					return true;
				}
			}
			for (int i = 0; i < generalStock.length; i++) {
				if (generalStock[i] == null) {
					generalStock[i] = new Item(itemId, quantity);
					refreshShop();
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean isSellable(int id){
		int[] nosell = {18347, 18349, 18351, 18353, 18355, 18357, 18359,23531, 
				18361, 18363, 18365, 18367, 18369, 18371, 18373, 
				18333, 18334, 18335, 18337, 19893, 19669, 4084, 18746, 18745, 18744,
				15704, 15703, 15702, 15701, 15444, 15443, 15442, 15441, 21999, 21989,
				21979, 21969, 23952, 23942, 23932, 23922, 23912, 23673, 20929, 22985,
				23805, 10404, 1057, 1055, 1053, 23659, 6570, 19335, 19336, 19337, 19338, 19339, 19340,19333,
				19346, 19348, 19350, 19352,10355,10354,10352,10353,20767,20768,
				16425,16403,20833,16955,19354,19356,19358,19360,15098//,
			//gems
				//6573,6574,1601,1602
				
				};
		for (int j: nosell){
			if (j != id){
				continue;
			}else if (j == id){
			return true;
			}
		}
		return false;
	}

	public void sell(Player player, int slotId, int quantity) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)	
			return;
		if (isSellable(item.getId())){
			player.sm("Mate you cant sell this.");
				return;
			}
		int originalId = item.getId();
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isDestroyItem()
				|| ItemConstants.getItemDefaultCharges(item.getId()) != -1
				|| !ItemConstants.isTradeable(item) || item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == 0 && generalStock == null) {
			player.getPackets().sendGameMessage(
					"You can't sell this item to this shop.");
			return;
		}
		int price = getSellPrice(item, dq);
		int numberOff = player.getInventory().getItems()
				.getNumberOf(originalId);
		if (quantity > numberOff)
			quantity = numberOff;
		if (!addItem(item.getId(), quantity)) {
			player.getPackets().sendGameMessage("Shop is currently full.");
			return;
		}
		player.getInventory().deleteItem(originalId, quantity);
		player.getInventory().addItem(money, price * quantity);
	}

	public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isNoted() || !ItemConstants.isTradeable(item)
				|| item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		if (isSellable(item.getId())){
			player.sm("You cant sell this item to the shop!");
				return;
			}
		int dq = getDefaultQuantity(item.getId());
		if (dq == 0 && generalStock == null) {
			player.getPackets().sendGameMessage(
					"You can't sell this item to this shop.");
			return;
		}
		int price = getSellPrice(item, dq);
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
						+ ": shop will buy for: "
						+ price
						+ " "
						+ ItemDefinitions.getItemDefinitions(money).getName()
								.toLowerCase()
						+ ". Right-click the item to sell.");
	}

	public int getDefaultQuantity(int itemId) {
		for (int i = 0; i < mainStock.length; i++)
			if (mainStock[i].getId() == itemId)
				return defaultQuantity[i];
		return 0;
	}

	public void sendInfo(Player player, int clickSlot) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getTemporaryAttributtes().put("ShopSelectedSlot", clickSlot);
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getBuyPrice(item, dq);
		double fixedprice;
		String endprice = "";
		int lengths = String.valueOf(price).length();
		
						if (lengths == 10){//bill
							fixedprice = price/1000000000;
							endprice =String.valueOf(fixedprice) + "Bill";
						}else if ((lengths == 7) || (lengths == 8)|| (lengths == 9)){//mill)
							fixedprice = price/1000000;
							endprice =String.valueOf(fixedprice) + "M";
						}
						else if ((lengths == 4) || (lengths == 5)|| (lengths == 6)){//k)
							fixedprice = price/1000;
							endprice =String.valueOf(fixedprice) + "K";
						}
						else{
						endprice = String.valueOf(price + ItemDefinitions.getItemDefinitions(money).getName()
								.toLowerCase());
						}
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
						+ ": currently costs <col=ff0000>"
						+ endprice
						+ " .  <col=000>  ("
						+ price
						+" "
						+ ItemDefinitions.getItemDefinitions(money).getName()
								.toLowerCase() + ")");
		}

	public void sendSlayInfo(Player player, int clickSlot) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getTemporaryAttributtes().put("ShopSelectedSlot", clickSlot);
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getSlayPrice(item, dq);
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
						+ ": currently costs "
						+ price
						+ " Slay Points.");
		player.getInterfaceManager().sendInventoryInterface(449);
		player.getPackets().sendGlobalConfig(741, item.getId());
		player.getPackets().sendGlobalConfig(743, money);
		player.getPackets().sendUnlockIComponentOptionSlots(449, 15, -1, 0, 0,
				1, 2, 3, 4); // unlocks buy
		player.getPackets().sendGlobalConfig(744, price);
		player.getPackets().sendGlobalConfig(745, 0);
		player.getPackets().sendGlobalConfig(746, -1);
		player.getPackets().sendGlobalConfig(168, 98);
		player.getPackets().sendGlobalString(25, ItemExamines.getExamine(item));
		player.getPackets().sendGlobalString(34, ""); // quest id for some items
		int[] bonuses = ItemBonuses.getItemBonuses(item.getId());
		if (bonuses != null) {
			HashMap<Integer, Integer> requiriments = item.getDefinitions()
					.getWearingSkillRequiriments();
			if (requiriments != null && !requiriments.isEmpty()) {
				String reqsText = "";
				for (int skillId : requiriments.keySet()) {
					if (skillId > 24 || skillId < 0)
						continue;
					int level = requiriments.get(skillId);
					if (level < 0 || level > 120)
						continue;
					boolean hasReq = player.getSkills().getLevelForXp(skillId) >= level;
					reqsText += "<br>"
							+ (hasReq ? "<col=00ff00>" : "<col=ff0000>")
							+ "Level " + level + " "
							+ Skills.SKILL_NAME[skillId];
				}
				player.getPackets().sendGlobalString(26,
						"<br>Worn on yourself, requiring: " + reqsText);
			} else
				player.getPackets()
						.sendGlobalString(26, "<br>Worn on yourself");
			player.getPackets().sendGlobalString(
					35,
					"<br>Attack<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_ATTACK]
							+ "<br><col=ffff00>---" + "<br>Strength"
							+ "<br>Ranged Strength" + "<br>Magic Damage"
							+ "<br>Absorve Melee" + "<br>Absorve Magic"
							+ "<br>Absorve Ranged" + "<br>Prayer Bonus");
			player.getPackets()
					.sendGlobalString(36,
							"<br><br>Stab<br>Slash<br>Crush<br>Magic<br>Ranged<br>Summoning");
			player.getPackets().sendGlobalString(
					52,
					"<<br>Defence<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SUMMONING_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STRENGTH_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.RANGED_STR_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.MAGIC_DAMAGE]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MELEE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MAGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_RANGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.PRAYER_BONUS]);
		} else
			player.getPackets().sendGlobalString(26, "");
	}
	public void sendVoteInfo(Player player, int clickSlot) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getTemporaryAttributtes().put("ShopSelectedSlot", clickSlot);
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getVotePrice(item, dq);
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
						+ ": currently costs "
						+ price
						+ " Vote Points.");
		player.getInterfaceManager().sendInventoryInterface(449);
		player.getPackets().sendGlobalConfig(741, item.getId());
		player.getPackets().sendGlobalConfig(743, money);
		player.getPackets().sendUnlockIComponentOptionSlots(449, 15, -1, 0, 0,
				1, 2, 3, 4); // unlocks buy
		player.getPackets().sendGlobalConfig(744, price);
		player.getPackets().sendGlobalConfig(745, 0);
		player.getPackets().sendGlobalConfig(746, -1);
		player.getPackets().sendGlobalConfig(168, 98);
		player.getPackets().sendGlobalString(25, ItemExamines.getExamine(item));
		player.getPackets().sendGlobalString(34, ""); // quest id for some items
		int[] bonuses = ItemBonuses.getItemBonuses(item.getId());
		if (bonuses != null) {
			HashMap<Integer, Integer> requiriments = item.getDefinitions()
					.getWearingSkillRequiriments();
			if (requiriments != null && !requiriments.isEmpty()) {
				String reqsText = "";
				for (int skillId : requiriments.keySet()) {
					if (skillId > 24 || skillId < 0)
						continue;
					int level = requiriments.get(skillId);
					if (level < 0 || level > 120)
						continue;
					boolean hasReq = player.getSkills().getLevelForXp(skillId) >= level;
					reqsText += "<br>"
							+ (hasReq ? "<col=00ff00>" : "<col=ff0000>")
							+ "Level " + level + " "
							+ Skills.SKILL_NAME[skillId];
				}
				player.getPackets().sendGlobalString(26,
						"<br>Worn on yourself, requiring: " + reqsText);
			} else
				player.getPackets()
						.sendGlobalString(26, "<br>Worn on yourself");
			player.getPackets().sendGlobalString(
					35,
					"<br>Attack<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_ATTACK]
							+ "<br><col=ffff00>---" + "<br>Strength"
							+ "<br>Ranged Strength" + "<br>Magic Damage"
							+ "<br>Absorve Melee" + "<br>Absorve Magic"
							+ "<br>Absorve Ranged" + "<br>Prayer Bonus");
			player.getPackets()
					.sendGlobalString(36,
							"<br><br>Stab<br>Slash<br>Crush<br>Magic<br>Ranged<br>Summoning");
			player.getPackets().sendGlobalString(
					52,
					"<<br>Defence<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SUMMONING_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STRENGTH_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.RANGED_STR_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.MAGIC_DAMAGE]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MELEE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MAGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_RANGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.PRAYER_BONUS]);
		} else
			player.getPackets().sendGlobalString(26, "");
	}

	public void sendDungInfo(Player player, int clickSlot) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getTemporaryAttributtes().put("ShopSelectedSlot", clickSlot);
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getDungPrice(item, dq);
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
						+ ": currently costs "
						+ price
						+ " Dung Tokens.");
		player.getInterfaceManager().sendInventoryInterface(449);
		player.getPackets().sendGlobalConfig(741, item.getId());
		player.getPackets().sendGlobalConfig(743, money);
		player.getPackets().sendUnlockIComponentOptionSlots(449, 15, -1, 0, 0,
				1, 2, 3, 4); // unlocks buy
		player.getPackets().sendGlobalConfig(744, price);
		player.getPackets().sendGlobalConfig(745, 0);
		player.getPackets().sendGlobalConfig(746, -1);
		player.getPackets().sendGlobalConfig(168, 98);
		player.getPackets().sendGlobalString(25, ItemExamines.getExamine(item));
		player.getPackets().sendGlobalString(34, ""); // quest id for some items
		int[] bonuses = ItemBonuses.getItemBonuses(item.getId());
		if (bonuses != null) {
			HashMap<Integer, Integer> requiriments = item.getDefinitions()
					.getWearingSkillRequiriments();
			if (requiriments != null && !requiriments.isEmpty()) {
				String reqsText = "";
				for (int skillId : requiriments.keySet()) {
					if (skillId > 24 || skillId < 0)
						continue;
					int level = requiriments.get(skillId);
					if (level < 0 || level > 120)
						continue;
					boolean hasReq = player.getSkills().getLevelForXp(skillId) >= level;
					reqsText += "<br>"
							+ (hasReq ? "<col=00ff00>" : "<col=ff0000>")
							+ "Level " + level + " "
							+ Skills.SKILL_NAME[skillId];
				}
				player.getPackets().sendGlobalString(26,
						"<br>Worn on yourself, requiring: " + reqsText);
			} else
				player.getPackets()
						.sendGlobalString(26, "<br>Worn on yourself");
			player.getPackets().sendGlobalString(
					35,
					"<br>Attack<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_ATTACK]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_ATTACK]
							+ "<br><col=ffff00>---" + "<br>Strength"
							+ "<br>Ranged Strength" + "<br>Magic Damage"
							+ "<br>Absorve Melee" + "<br>Absorve Magic"
							+ "<br>Absorve Ranged" + "<br>Prayer Bonus");
			player.getPackets()
					.sendGlobalString(36,
							"<br><br>Stab<br>Slash<br>Crush<br>Magic<br>Ranged<br>Summoning");
			player.getPackets().sendGlobalString(
					52,
					"<<br>Defence<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STAB_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SLASH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.CRUSH_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.MAGIC_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.RANGE_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.SUMMONING_DEF]
							+ "<br><col=ffff00>+"
							+ bonuses[CombatDefinitions.STRENGTH_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.RANGED_STR_BONUS]
							+ "<br><col=ffff00>"
							+ bonuses[CombatDefinitions.MAGIC_DAMAGE]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MELEE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_MAGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.ABSORVE_RANGE_BONUS]
							+ "%<br><col=ffff00>"
							+ bonuses[CombatDefinitions.PRAYER_BONUS]);
		} else
			player.getPackets().sendGlobalString(26, "");
	}
	

	
	

	public int getVotePrice(Item item, int dq) {
		  switch(item.getId()) {
		 
		case 20929://ornate katana
			return 4;
	//void		
		case 11663: //mage helm
			return 4;
		case 11664://range
			return 4;
		case 11665://melee
			return 4;
		case 8839://top
			return 3;
		case 8840://legs
			return 3;
		case 8841://mace
			return 4;
		case 8842://glove
			return 2;
		case 3062://seed box
			return 1;
		case 3063://sweets box
			return 1;
		
		}
             
		return -1;
	}
	
	public int getSlayPrice(Item item, int dq) {
		  switch(item.getId()) {
		 
		case 1://ornate katana
			return 69;
		case 13263:
			return 15;
		case 21371:
			return 40;
		case 11235:
			return 15;
		case 15701:
			return 15;
		case 15702:
			return 15;
		case 15703:
			return 15;
		case 15704:
			return 15;
		case 943:
			return 100;
		case 23058:
			return 75;
		case 23060:
			return 75;
		case 23062:
			return 75;
		case 23064:
			return 100;
		case 23066:
			return 75;
		

		
		}
             
		return -1;
	}
	  
	  public int getDungPrice(Item item, int dq) {
		  switch(item.getId()) {
		/*Chaotic*/
		case 18349:
		return 200000;
		case 18351:
		return 200000;
		case 18353:
		return 200000;
		case 18355:
		return 200000;
		case 18357:
		return 200000;
		case 18359:
		return 200000;
		case 18361:
		return 200000;
		case 18363:
		return 200000;
		/*Chaotic*/
		
		/*Arcane Knecklaces*/
		case 18333:
		return 20000;
		case 18334:
		return 40000;
		case 18335:
		return 60000;
		/*Arcane Knecklaces*/
		
		
		/*Other Knecklaces*/
		case 19887:
		return 70000;
		case 18337:
		return 45000;
		/*Other Knecklaces*/
		
		
		/*Gravite*/
		case 18365:
		return 75000;
		case 18367:
		return 75000;
		case 18369:
		return 75000;
		case 18371:
		return 75000;
		case 18373:
		return 75000;
		/*Gravite*/
		
		
		/*Other Items*/
		case 19893:
		return 125000;
		case 18347:
		return 70000;
		case 19669:
		return 100000;
		/**/
		/**/
		/**/
		/**/
		/**/
		/**/
		/**/
		}
             
		return -1;
	}
	
	

	//buy98987 (2521)
	  public int getBuyPrice(Item item, int dq) {
                switch (item.getId()) {
		
	
				
				
		//charm ring 
					case 9104:
                        item.getDefinitions().setValue(10000);
                        break;
						
		//charm ring i
					case 15015:
                        item.getDefinitions().setValue(20000);
                        break;
						
						
			//mime gloves
					case 18830:
                        item.getDefinitions().setValue(5000);
                        break;
		//mime gloves
					case 3060:
                        item.getDefinitions().setValue(50000);
                        break;
		//desert boots
					case 1837:
                        item.getDefinitions().setValue(50000);
                        break;
						
				
				
	//crystal key
					case 985:
                        item.getDefinitions().setValue(800);
                        break;
			
					case 987:
                        item.getDefinitions().setValue(80);
                        break;



						//mage pot	
					case 3040:
                        item.getDefinitions().setValue(800);
                        break;
				//mage pot noted
					case 3041:
                        item.getDefinitions().setValue(800);
                        break;
//range pot noted
			 case 2445:
                        item.getDefinitions().setValue(500);//buying price
                        break;
//range pot
			 case 2444:
                        item.getDefinitions().setValue(500);//buying price
                        break;
						
						
//Max cash coins
			 case 11179:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
			
//Max coins end
//pets
				case 12196:
                        item.getDefinitions().setValue(300000000);
                        break;
				case 21512:
                        item.getDefinitions().setValue(1000000000);
                        break;



//Ancient Stattuette

			case 14876:
                        item.getDefinitions().setValue(2147000000);//buying price
                        break;
						
//planks
			case 8782:
                        item.getDefinitions().setValue(1800000);//buying price
                        break;
			case 8780:
                        item.getDefinitions().setValue(3000000);//buying price
                        break;				

//Bonus items price
			case 10498:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 10499:
                        item.getDefinitions().setValue(50000);//buying price
                        break;
			case 20068:
                        item.getDefinitions().setValue(100000);//buying price
                        break;
			case 3840:
                        item.getDefinitions().setValue(500000);//buying price
                        break;
			case 3842:
                        item.getDefinitions().setValue(500000);//buying price
                        break;
			case 3844:
                        item.getDefinitions().setValue(300000);//buying price
                        break;
			case 2412:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			case 2413:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			case 2414:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;			
//Bonus items end

//Glory glitch
			case 10354:
                        item.getDefinitions().setValue(300000000);//300m a pice
                        break;
			case 10355:
                        item.getDefinitions().setValue(300000000);//300m a pice
                        break;
//Glory glitch end



			
//FOG STORE

		//Barb start
            case 10554:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 10555:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 15438:
                        item.getDefinitions().setValue(20000000);//buying price
                        break;
			case 10551:
                        item.getDefinitions().setValue(2500);//buying price
                        break;
			case 10548:
                        item.getDefinitions().setValue(1500);//buying price
                        break;
						
	//barb extras
	
			//barb extras

			case 10552:
                        item.getDefinitions().setValue(100);//buying price
						break;
			case 10547:
                        item.getDefinitions().setValue(750);//buying price
						break;
						
			case 10549:
                        item.getDefinitions().setValue(750);//buying price
						break;
//Barb end
		
//PK Token
			
						
			case 13887:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13893:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13899:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13905:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13884:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13890:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13896:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13902:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13858:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13861:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13864:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13867:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13870:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13873:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13876:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13879:
                        item.getDefinitions().setValue(5);//buying price
                        break;
			case 13883:
                        item.getDefinitions().setValue(5);//buying price
						break;
			
			
						
//Pk Token end

//PK Token 2
			case 13911:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13917:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13923:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13929:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13914:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13908:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13920:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13926:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13932:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13935:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13938:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13941:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 13944:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13947:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13950:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 13953:
                        item.getDefinitions().setValue(5);//buying price
                        break;
			case 13957:
                        item.getDefinitions().setValue(5);//buying price
                        break;
		//Pk Token 2 end
		
		
		//tokkul shop
		    case 6585:
                        item.getDefinitions().setValue(200000);//buying price
                        break;
			case 11128:
                        item.getDefinitions().setValue(100000);//buying price
                        break;
			case 6570:
                        item.getDefinitions().setValue(150000);//buying price
                        break;
		//tokkul shop end				
			
			
//Herb shop
		
		//Guam
			case 199:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//Marrentil				
			case 201:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
		//Tarromin				
			case 203:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		//Harralander
			case 205:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		//ranarr			
			case 207:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
		//Irit				
			case 209:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
		//Avantoe				
			case 211:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
		//kruarm			
			case 213:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//Snapdragon
			case 3051:
                        item.getDefinitions().setValue(20000);//buying price
                        break;	
		
		//Cadatine			
			case 215:
                        item.getDefinitions().setValue(23000);//buying price
                        break;
		//lantadyme
			case 2485:
                        item.getDefinitions().setValue(26000);//buying price
                        break;	
								
		//Dwarf Weed			
			case 217:
                        item.getDefinitions().setValue(29000);//buying price
                        break;			
								
		//Torstal		
			case 219:
                        item.getDefinitions().setValue(33000);//buying price
                        break;	
//NOTED					
		//Guam
			case 200:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//Marrentil				
			case 202:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
		//Tarromin				
			case 204:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		//Harralander
			case 206:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		//ranarr			
			case 208:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
		//Irit				
			case 210:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
		//Avantoe				
			case 212:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
		//kruarm			
			case 214:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//Snapdragon
			case 3052:
                        item.getDefinitions().setValue(20000);//buying price
                        break;	
		
		//Cadatine			
			case 216:
                        item.getDefinitions().setValue(23000);//buying price
                        break;
		//lantadyme
			case 2486:
                        item.getDefinitions().setValue(26000);//buying price
                        break;	
								
		//Dwarf Weed			
			case 218:
                        item.getDefinitions().setValue(29000);//buying price
                        break;			
								
		//Torstal		
			case 220:
                        item.getDefinitions().setValue(33000);//buying price
                        break;	
	//Ingrediants start	
	
		//vial of water	
			case 227:
                        item.getDefinitions().setValue(10);//buying price
                        break;
		//pestal and mortor
			case 233:
                        item.getDefinitions().setValue(300);//buying price
                        break;
	
		//snape grass		
			case 231:
                        item.getDefinitions().setValue(33000);//buying price
                        break;				
					
		//grenwall spikes			
			case 12539:
                        item.getDefinitions().setValue(50000);//buying price
                        break;	

		//eye of newt
			case 221:
                        item.getDefinitions().setValue(100);//buying price
                        break;
		//unicorn dust
			case 235:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//limpwort root
			case 225:
                        item.getDefinitions().setValue(1500);//buying price
                        break;
		//redspider egg
			case 223:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		//white berry	
			case 239:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		/*snapegrass	
			case 231:
                        item.getDefinitions().setValue(300);//buying price
                        break;*/
		
		
		//Herb shop end


//Herb clean
	
		//guam
			case 249:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//marrentil
			case 251:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
		//tarromin	
			case 253:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		//harralander
			case 255:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		//ranarr
			case 257:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
		//irit
			case 259:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
		//avantoe
			case 261:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//kwuarm
			case 263:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
		
		//snapdragon
			case 3000:
                        item.getDefinitions().setValue(20000);//buying price
                        break;	

		//cadatine	
			case 265:
                        item.getDefinitions().setValue(23000);//buying price
                        break;
		
		//lantadym	
			case 2481:
                        item.getDefinitions().setValue(26000);//buying price
                        break;
		//dwarf weed	
			case 267:
                        item.getDefinitions().setValue(29000);//buying price
                        break;
		//clean torstol
			case 269:
                        item.getDefinitions().setValue(32000);//buying price
                        break;
//Herb clean end.		

		//Skill shop 1
		
		//knife
			case 946: 
                        item.getDefinitions().setValue(50);//buying price
                        break;
		//bones
			case 526:
                        item.getDefinitions().setValue(200);//buying price
                        break;
			
		//big bones
			case 532:
                        item.getDefinitions().setValue(300);//buying price
                        break;
		//baby dragom	
			case 534:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//jogre	
			case 3125:
                        item.getDefinitions().setValue(750);//buying price
                        break;
		//Dragon bones	
			case 536:
                        item.getDefinitions().setValue(10000);//buying price
              			  break;
			
		
		//bronze hatchet	
			case 1351:
                        item.getDefinitions().setValue(200);//buying price
                        break;
						
		//iron hatchet
			case 1349:
                        item.getDefinitions().setValue(500);//buying price
                        break;
						
		//steel hatchet	
			case 1353:
                        item.getDefinitions().setValue(800);//buying price
                        break;
		//black hatchetS	
			case 1361:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		//mithril hatchet
			case 1355:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
		//adamant hatchet	
			case 1357:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
		//rune hatchet	
			case 1359:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//inferno adze	
			case 13661:
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		//tinderbox
			case 590:
                        item.getDefinitions().setValue(50);//buying price
                        break;
		//chisel	
			case 1755:
                        item.getDefinitions().setValue(50);//buying price
                        break;
	//gems

		//opal
			case 1625:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//jade	
			case 1627:
                        item.getDefinitions().setValue(600);//buying price
                        break;
		//saphire	
			case 1623:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
		//emerald	
			case 1621:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		//ruby	
			case 1619:
                        item.getDefinitions().setValue(4000);//buying price
                        break;
		//diamond
			case 1617:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//dragonstone
			case 1631:
                        item.getDefinitions().setValue(60000);//buying price
                        break;
		//onyx
			case 6571:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			

//NOTED

		//opal
			case 1626:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//jade	
			case 1628:
                        item.getDefinitions().setValue(600);//buying price
                        break;
		//saphire	
			case 1624:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
		//emerald	
			case 1622:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
		//ruby	
			case 1620:
                        item.getDefinitions().setValue(4000);//buying price
                        break;
		//diamond
			case 1618:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//dragonstone
			case 1632:
                        item.getDefinitions().setValue(60000);//buying price
                        break;
		//onyx
			case 6572:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			
			
//Skill shop 1 end



		
		//Defenders
		
		//iron
			case 8845:
                        item.getDefinitions().setValue(100);//buying price
                        break;
		//steel	
			case 8846:
                        item.getDefinitions().setValue(200);//buying price
                       break;
		//black	
			case 8847:
                        item.getDefinitions().setValue(300);//buying price
                        break;
		//mith	
			case 8848:
                        item.getDefinitions().setValue(450);//buying price
                        break;
		//adamant	
			case 8849:
                        item.getDefinitions().setValue(600);//buying price
                        break;
		//rune	
			case 8850:
                        item.getDefinitions().setValue(900);//buying price
                        break;
		//dragon	
			case 20072:
                        item.getDefinitions().setValue(1000);//buying price
                        break;	
	//Defenders end

		
	//Gloves start
		
		//leather
			case 7453:
                        item.getDefinitions().setValue(50);//buying price
                        break;
		//bronze
			case 7454:
                        item.getDefinitions().setValue(50);//buying price
                        break;
		//iron	
			case 7455:
                        item.getDefinitions().setValue(100);//buying price
                        break;
		//steel	
			case 7456:
                        item.getDefinitions().setValue(120);//buying price
                        break;
		//black	
			case 7457:
                        item.getDefinitions().setValue(200);//buying price
                        break;
		//mith	
			case 7458:
                        item.getDefinitions().setValue(300);//buying price
                        break;
		//adamant
			case 7459:
                        item.getDefinitions().setValue(750);//buying price
                        break;
		//rune		
			case 7460:
                        item.getDefinitions().setValue(500);//buying price
                        break;
		//dragon	
			case 7461:
                        item.getDefinitions().setValue(700);//buying price
                        break;
		//barrows
			case 7462:
                        item.getDefinitions().setValue(1000);//buying price
                        break;

	//Gloves end
		
		
	//fog extras
		//comp cape	
			case 20769:
                        item.getDefinitions().setValue(500000);//buying price
                        break;
		//comp cape trim
			case 20771:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
		//ornate katana	
			case 20929:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
		//troll potion	
			case 3265:
                        item.getDefinitions().setValue(50);//buying price
                        break;
		//bunny ears
			case 1037:
                        item.getDefinitions().setValue(20000);//buying price
                        break;
		//grim reaper hood
			case 11789:
                        item.getDefinitions().setValue(30000);//buying price
                        break;
		//santa top
			case 14595:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//santa gloves
			case 14602:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//santa pants	
			case 14603:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
		//santa boots	
			case 14605:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
						
	//fog extras end

	//extra shops start
		
		//gmaul
			case 4153:
                        item.getDefinitions().setValue(250000);//buying price
                        break;
						
		
		//dragon boots	
			case 11732:
                        item.getDefinitions().setValue(300000);//buying price
                        break;
			
			case 14497:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			
			case 14499:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			
			case 14501:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			
			case 6916:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
			
			case 6918:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
			
			case 6920:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
			
			case 6922:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
			
			case 6924:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
			
			case 6914:
                        item.getDefinitions().setValue(5000000);//buying price
                        break;
			
			case 6889:
                        item.getDefinitions().setValue(5000000);//buying price
                        break;
			
			case 151261:
                        item.getDefinitions().setValue(3500000);//buying price
                        break;			
		
//FOG Store end.

			case 20171:
                        item.getDefinitions().setValue(250000000);//Z bow
                        break;
			case 20173:
                        item.getDefinitions().setValue(250000000);//Z bow
                        break;
						



//Lamps
			case 23737:
                        item.getDefinitions().setValue(300);//buying price
                        break;
			case 23738:
                        item.getDefinitions().setValue(500);//buying price
                        break;
			case 23739:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 23814:
                        item.getDefinitions().setValue(300);//buying price
                        break;
			case 23815:
                        item.getDefinitions().setValue(500);//buying price
                        break;
			case 23816:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
//Lamps end


//tele tabs
		
		case 8007:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8008:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8009:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8010:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8011:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		
//tele tabs end

//Nex set
	//torva
		
		case 20135:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20139:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20143:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
	//pernix
		case 20147:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20151:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20155:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
	//Virtus
		case 20159:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20163:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		case 20167:
                        item.getDefinitions().setValue(200000000);//buying price
                        break;
		
//Nex set end


/*
//Void	
	//Helms
		case 11674:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 11675:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 11676:
                        item.getDefinitions().setValue(30);//buying price
                        break;
	//Outfit
		case 8839:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 8840:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 8842:
                        item.getDefinitions().setValue(30);//buying price
                        break;
	//Mace
		case 8841:
                        item.getDefinitions().setValue(30);//buying price
                        break;
						
//void end 
		
*/
//cut gems


                      
                      
			case 1609:
                        item.getDefinitions().setValue(600);//buying price
                        break;
			case 1611:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 1607:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
			case 1605:
                        item.getDefinitions().setValue(4000);//buying price
                        break;
			case 1603:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 1601:
                        item.getDefinitions().setValue(30000);//buying price
                        break;
			case 6573:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			


//cut gems end


	//skull
			case 24437:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24438:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24439:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24440:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24441:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			
	//skill end
	
	//frem blade
			case 3757:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
	//frem blade
	
	//HC
			case 15241:
                        item.getDefinitions().setValue(30000);//buying price
                        break;
	//HC end
	
	//Donatorshop
		
	
		//(i)  
		
		case 15018:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15220:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15020:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15019:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
			
		
		//kiln
			case 23659:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
			
		//SW capes	
			case 14642:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 14641:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
		//(or) kits
			//Fury
			case 19333:
                        item.getDefinitions().setValue(2000000000);//buying price
                        break;
			//dfh			
			case 19346:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Legs			
			case 19348:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//plate			
			case 19350:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Sq shield		
			case 19352:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Chaotics
		case 18349:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18351:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18353:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18355:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18357:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18359:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18361:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		case 18363:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		
	//end
	//auras
//shop 1
	case 20958://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
	case 20962://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
						  
	case 20980://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 20984://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 20966://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
	case 20965://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 20988://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
//shop 5						  
	
				  
	case 22268://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22270://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22272://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
	case 22282://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22286://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;	
	case 22274://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22276://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;	

	case 22290://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;	


//shop 10

					  
	case 22292://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22294://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22300://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
	case 22296://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 22298://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;	
	case 22302://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
	


//shop 20
	case 23880://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
						  
	case 23882://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 23884://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 23886://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;
	case 23888://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
						  
	case 23890://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;	
	case 23892://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
	

	case 23894://
						item.getDefinitions().setValue(1000000);//buying price
              			  break;					  
	

//end of auras	
	
	//Godswords
	case 11694://ags
                        item.getDefinitions().setValue(500000000);//buying price
              			  break;
	case 11696://bgs
                        item.getDefinitions().setValue(200000000);//buying price
              			  break;
	case 11698://sgs
                        item.getDefinitions().setValue(400000000);//buying price
              			  break;
	case 11700://zgs
                        item.getDefinitions().setValue(300000000);//buying price
              			  break;
	
	//end
	
	//claws
	
	case 14484://claws
                        item.getDefinitions().setValue(500000000);//buying price
              			  break;
						  
	//shard + pouch 
		case 12183://shard
                        item.getDefinitions().setValue(25);//buying price
              			  break;
		
		case 12155://pouch
                        item.getDefinitions().setValue(1);//buying price
              			  break;
						  
		//shard box
			case 15262:
                        item.getDefinitions().setValue(375000);//buying price
                        break;
						  
						  
	
						  
						  
	//Lucky gear
		//Bandos
		case 23689://boots 10m
                        item.getDefinitions().setValue(200000000);//buying price
              			  break;
						  
		case 23687://chestplate 50m
                        item.getDefinitions().setValue(700000000);//buying price
              			  break;
						  
		case 23688://tassy 50m
                        item.getDefinitions().setValue(700000000);//buying price
              			  break;				  
		

//Lucky end		

			//blesed shield
		case 13736://boots 10m
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		//noted
		case 13737://boots 10m
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
			  
			//QBD shop
		
		
		case 24376://gloves
                        item.getDefinitions().setValue(30);//buying price
              			  break;
						  
		case 24379://chaps
                        item.getDefinitions().setValue(50);//buying price
              			  break;
						  
		case 24382://body
                        item.getDefinitions().setValue(50);//buying price
              			  break;
						  
		case 24388://coif
                        item.getDefinitions().setValue(100);//buying price
              			  break;
		
		//end
		
		
		//Sythes
		
		case 22321://gold
                        item.getDefinitions().setValue(10000);//buying price
              			  break;
						  
		case 10735://original
                        item.getDefinitions().setValue(15000);//buying price
              			  break;
		
		//end
		
		//XMas items
		
		
		case 15422://original
                        item.getDefinitions().setValue(3);//buying price
              			  break;
		case 15423://origina
                        item.getDefinitions().setValue(3);//buying price
              			  break;
		case 15425://original
                        item.getDefinitions().setValue(3);//buying price
              			  break;
		case 1580://original
                        item.getDefinitions().setValue(5);//buying price
              			  break;
		case 4671://original
                        item.getDefinitions().setValue(5);//buying price
              			  break;
		case 11952://original
                        item.getDefinitions().setValue(10);//buying price
              			  break;
		case 14596://original
                        item.getDefinitions().setValue(10);//buying price
              			  break;
		case 18628://original
                       item.getDefinitions().setValue(1);//buying price
              			  break;
		case 18625://original
                        item.getDefinitions().setValue(1);//buying price
              			  break;
		case 22966://original
                       item.getDefinitions().setValue(5);//buying price
              			  break;
		
			//reward token
			
		case 9474://original
                       item.getDefinitions().setValue(10);//buying price
              			  break;
						  
		case 8794://saw
                        item.getDefinitions().setValue(100000);//buying price
              			  break;
	 //gloves	
		case 24454:
                        item.getDefinitions().setValue(2000000000);//buying price
                        break;
						
	//Pimal 
		case 16955://rapier
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
		case 16425://maul
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
		case 16403://longsword
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
						
		case 20833://longsword v2
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
//Armour
		
		
//PRIMAL end	

//Lantern
//Con Supplies
			case 7051://unlit
                        item.getDefinitions().setValue(50000);//buying price
                        break;
		
		
		

//Con Supplies
			case 6332://Teak log
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
		
		
		case 6333://Mahogany log
                        item.getDefinitions().setValue(2000000);//buying price
                        break;
		
			
		case 9592://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		

//end

//hoods 

		case 9749://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9752://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9755://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9758://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9761://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9764://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9767://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9770://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9773://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9776://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9779://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9782://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9785://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9788://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9791://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9794://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9797://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9800://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9803://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9806://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;

		case 9809://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9812://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9950://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 12171://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 18510://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		//end
		//barrows
		case 11846:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11848:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11850:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11852:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11854:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11856:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
	//third age
		case 11858:
                        item.getDefinitions().setValue(382000000);//buying price
                        break;
						
		case 11862:
                        item.getDefinitions().setValue(312000000);//buying price
                        break;
						
		case 11860:
                        item.getDefinitions().setValue(398000000);//buying price
                        break;
						
	//living minerals
	
		case 15263:
                        item.getDefinitions().setValue(200);//buying price
                        break;	
						
		//overload 

		case 23531:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;	
						
				
		//spec restore 

				case 15300:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;	
						
				

				case 4151:
                        item.getDefinitions().setValue(10000000);//whip
                        break;
	
				case 4710:
                        item.getDefinitions().setValue(10000000);
                        break;
           		case 4712:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4714:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4716:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4718:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4720:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4722:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4724:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4726:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4728:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4730:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4732:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4734:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4736:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4738:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4745:
                        item.getDefinitions().setValue(10000000);
                        break;
                 	case 9790:
                        item.getDefinitions().setValue(200000);
                        break;
					case 4747:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4749:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4751:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4753:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4755:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4757:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4759:
                        item.getDefinitions().setValue(10000000);
                        break;
					case 2453:
                        item.getDefinitions().setValue(2000);
                        break;
				
                }
                return item.getDefinitions().getValue();
        }
//sell98987
	public int getSellPrice(Item item, int dq) {
		switch (item.getId()) {
	
		case 4708://barrows starts here
                        item.getDefinitions().setValue(10000000);//10m a pice
                        break;
               	case 4710:
                        item.getDefinitions().setValue(10000000);
                        break;
           		case 4712:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4714:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4716:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4718:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4720:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4722:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4724:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4726:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4728:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4730:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4732:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4734:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4736:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4738:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4745:
                        item.getDefinitions().setValue(10000000);
                        break;
                 	case 9790:
                        item.getDefinitions().setValue(200000);
                        break;
					case 4747:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4749:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4751:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4753:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4755:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4757:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 4759:
                        item.getDefinitions().setValue(10000000);
                        break;
	
	//Max cash coins
			 
			case 11179:
                        item.getDefinitions().setValue(2000000000);//buying price
                        break;/*
//Max coins end

	
//mime gloves
					case 3060:
                        item.getDefinitions().setValue(50000);
                        break;
		//desert boots
					case 1837:
                        item.getDefinitions().setValue(50000);
                        break;
						

//crystal key
					case 985:
                        item.getDefinitions().setValue(800);
                        break;
			
					case 987:
                        item.getDefinitions().setValue(80);
                        break;	
	//range pot noted
			 case 2445:
                        item.getDefinitions().setValue(500);//buying price
                        break;
//range pot
			 case 2444:
                        item.getDefinitions().setValue(500);//buying price
                        break;
							
		//spec restore 

		case 15300:
                        item.getDefinitions().setValue(2000000);//buying price
                        break;	
						
		
//overload 

		case 23531:
                        item.getDefinitions().setValue(5000000);//buying price
                        break;	
						
//living minereals  

		case 15263:
                        item.getDefinitions().setValue(2000);//buying price
                        break;		
		
		
//setss

	//barrows
		case 11846:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		
						
		case 11848:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11850:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11852:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11854:
                        item.getDefinitions().setValue(40000000);//buying price
                        break;
						
		case 11856:
                        item.getDefinitions().setValue(50000000);//buying price
                        break;
	//third age
		case 11858:
                        item.getDefinitions().setValue(382000000);//buying price
                        break;
						
		case 11862:
                        item.getDefinitions().setValue(312000000);//buying price
                        break;
						
		case 11860:
                        item.getDefinitions().setValue(398000000);//buying price
                        break;
						
		
		
//end
		
		
		//hoods 

		case 9749://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9752://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9755://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9758://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9761://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9764://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9767://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9770://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9773://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9776://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9779://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9782://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9785://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9788://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9791://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9794://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9797://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9800://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9803://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 9806://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;

		case 9809://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9812://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 9950://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		case 12171://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		case 18510://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		//end
		//Lantern
//Con Supplies
			case 7051://unlit
                        item.getDefinitions().setValue(5000);//buying price
                        break;
						
						
//Con Supplies
		case 6332://Teak log
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		
		
		case 6333://Mahogany log
                        item.getDefinitions().setValue(200000);//buying price
                        break;
		


		case 9592://magic glue
                        item.getDefinitions().setValue(100000);//buying price
                        break;
		

//end
//Pimal 
		case 16955://rapier
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
		case 16425://maul
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
		case 16403://longsword
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
						
		case 20833://longsword v2
                        item.getDefinitions().setValue(1500000000);//buying price
                        break;
		
//Armour
		
		
//PRIMAL end		
	//gloves	
		case 24454:
                        item.getDefinitions().setValue(2000000000);//buying price
                        break;
		
//saw

		case 8794://saw
                        item.getDefinitions().setValue(100000);//buying price
              			  break;
		
		
		//XMas items
		
		
		case 15422://original
                        item.getDefinitions().setValue(3);//buying price
              			  break;
						  
						  
						  
		case 15423://origina
                        item.getDefinitions().setValue(3);//buying price
              			  break;
		case 15425://original
                        item.getDefinitions().setValue(3);//buying price
              			  break;
		case 1580://original
                        item.getDefinitions().setValue(5);//buying price
              			  break;
		case 4671://original
                        item.getDefinitions().setValue(5);//buying price
              			  break;
		case 11952://original
                        item.getDefinitions().setValue(10);//buying price
              			  break;
		case 14596://original
                        item.getDefinitions().setValue(10);//buying price
              			  break;
		case 18628://original
                       item.getDefinitions().setValue(1);//buying price
              			  break;
		case 18625://original
                        item.getDefinitions().setValue(1);//buying price
              			  break;
		case 22966://original
                       item.getDefinitions().setValue(5);//buying price
              			  break;
		
		//Sythes
		
		case 22321://gold
                        item.getDefinitions().setValue(10000);//buying price
              			  break;
						  
		case 10735://original
                        item.getDefinitions().setValue(15000);//buying price
              			  break;
		
		//end
		
		//QBD shop
		
		
		case 24376://gloves
                        item.getDefinitions().setValue(30);//buying price
              			  break;
						  
		case 24379://chaps
                        item.getDefinitions().setValue(50);//buying price
              			  break;
						  
		case 24382://body
                        item.getDefinitions().setValue(50);//buying price
              			  break;
						  
		case 24388://coif
                        item.getDefinitions().setValue(100);//buying price
              			  break;
		
		//end
		
		
		
		//blesed shield
		case 13736://boots 10m
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
		//noted
		case 13737://boots 10m
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
//Lucky gear
		//Bandos
		case 23689://boots 10m
                        item.getDefinitions().setValue(20000000);//buying price
              			  break;
						  
		case 23687://chestplate 50m
                        item.getDefinitions().setValue(70000000);//buying price
              			  break;
						  
		case 23688://tassy 50m
                        item.getDefinitions().setValue(70000000);//buying price
              			  break;				  
		

//Lucky end
		case 536://Dragon bones
                        item.getDefinitions().setValue(10000);//buying price
              			  break;
		
		//shard + pouch 
		case 12183://shard
                        item.getDefinitions().setValue(25);//buying price
              			  break;
		
		case 12155://pouch
                        item.getDefinitions().setValue(1);//buying price
              			  break;
						  
			//shard box
			case 15262:
                        item.getDefinitions().setValue(375000);//buying price
                        break;
		
		//Godswords
	case 11694://ags
                        item.getDefinitions().setValue(250000000);//buying price
              			  break;
	case 11696://bgs
                        item.getDefinitions().setValue(90000000);//buying price
              			  break;
	case 11698://sgs
                        item.getDefinitions().setValue(150000000);//buying price
              			  break;
	case 11700://zgs
                        item.getDefinitions().setValue(110000000);//buying price
              			  break;
	
	//end
	
	//claws
	
	case 14484://claws
                        item.getDefinitions().setValue(400000000);//buying price
              			  break;
	
	//Chaotics
		case 18349:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18351:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18353:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18355:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18357:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18359:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18361:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		case 18363:
                        item.getDefinitions().setValue(1000000000);//buying price
              			  break;
		
	//end
		
		
		
	//donatorshop
	
		//(i)  
		
		case 15018:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15220:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15020:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
		case 15019:
                        item.getDefinitions().setValue(1000000);//buying price
              			  break;
			
		
		//kiln
			case 23659:
                        item.getDefinitions().setValue(100000000);//buying price
              			  break;
			
		//SW capes	
			case 14642:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			case 14641:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
		//(or) kits
			//Fury
			case 19333:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
			//dfh			
			case 19346:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Legs			
			case 19348:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//plate			
			case 19350:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Sq shield		
			case 19352:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			
		//(sp) kits
			
			
			//dfh			
			case 19354:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Legs			
			case 19356:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//plate			
			case 19358:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			//Sq shield		
			case 19360:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			
						
			
			
		
		
		//frem blade
			case 3757:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
	//frem blade
		
	//HC
			case 15241:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
	//HC end
	//skull
			case 24437:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24438:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24439:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24440:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			case 24441:
                        item.getDefinitions().setValue(10000000);//buying price
                        break;
			
	//skill end
		
		
		
//cut gems



                        
                        
			case 1609:
                        item.getDefinitions().setValue(600);//buying price
                        break;
			case 1611:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 1607:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
			case 1605:
                        item.getDefinitions().setValue(4000);//buying price
                        break;
			case 1603:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 1601:
                        item.getDefinitions().setValue(30000);//buying price
                        break;
			case 6573:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			


//cut gems end		
		
//Void	
	//Helms
		case 11674:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 11675:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 11676:
                        item.getDefinitions().setValue(30);//buying price
                        break;
	//Outfit
		case 8839:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 8840:
                        item.getDefinitions().setValue(30);//buying price
                        break;
		case 8842:
                        item.getDefinitions().setValue(30);//buying price
                        break;
	//Mace
		case 8841:
                        item.getDefinitions().setValue(30);//buying price
                        break;
						
//void end
		
//Nex set
	//torva
		
		case 20135:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20139:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20143:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
	//pernix
		case 20147:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20151:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20155:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
	//Virtus
		case 20159:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20163:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		case 20167:
                        item.getDefinitions().setValue(1000000000);//buying price
                        break;
		
//Nex set end
		
//tele tabs
		
		case 8007:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8008:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8009:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8010:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		case 8011:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
		
//tele tabs end

//Lamps
			case 23737:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 23738:
                        item.getDefinitions().setValue(200);//buying price
                        break;
			case 23739:
                        item.getDefinitions().setValue(300);//buying price
                        break;
			case 23814:
                        item.getDefinitions().setValue(150);//buying price
                        break;
			case 23815:
                        item.getDefinitions().setValue(200);//buying price
                        break;
			case 23816:
                        item.getDefinitions().setValue(300);//buying price
                        break;
//Lamps end
					case 20171:
                        item.getDefinitions().setValue(250000000);//Z bow
                        break;
			case 20173:
                        item.getDefinitions().setValue(250000000);//Z bow
                        break;

//Bonus items price
			case 10498:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 10499:
                        item.getDefinitions().setValue(50000);//buying price
                        break;
			case 20068:
                        item.getDefinitions().setValue(100000);//buying price
                        break;
			case 3840:
                        item.getDefinitions().setValue(500000);//buying price
                        break;
			case 3842:
                        item.getDefinitions().setValue(500000);//buying price
                        break;
			case 3844:
                        item.getDefinitions().setValue(300000);//buying price
                        break;
			case 2412:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			case 2413:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;
			case 2414:
                        item.getDefinitions().setValue(1000000);//buying price
                        break;						
//Bonus items end

	//Barb start
            case 10554:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 10555:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 15438:
                        item.getDefinitions().setValue(20000000);//buying price
                        break;
			case 10551:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 10548:
                        item.getDefinitions().setValue(750);//buying price
                        break;
						
	//barb extras
	
			//barb extras

			case 10552:
                        item.getDefinitions().setValue(100);//buying price
						break;
			case 10547:
                        item.getDefinitions().setValue(750);//buying price
						break;
						
			case 10549:
                        item.getDefinitions().setValue(750);//buying price
						break;
//Barb end
		
//Ancient Stattuette

			case 14876:
                        item.getDefinitions().setValue(500000000);//buying price
                        break;

			


//Glory glitch
			case 10354:
                        item.getDefinitions().setValue(300000000);//300m a pice
                        break;
			case 10355:
                        item.getDefinitions().setValue(300000000);//300m a pice
                        break;
//Glory glitch end


//Herb shop
			case 199:
                        item.getDefinitions().setValue(500);//buying price
                        break;
			case 201:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 203:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
			case 205:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
			case 207:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
			case 209:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
			case 211:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
			case 213:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 215:
                        item.getDefinitions().setValue(14000);//buying price
                        break;
			case 217:
                        item.getDefinitions().setValue(15000);//buying price
                        break;
			case 219:
                        item.getDefinitions().setValue(18000);//buying price
                        break;
			case 3051:
                        item.getDefinitions().setValue(20000);//buying price
                        break;			
//Herb in shop end.	

//Herb clean
			case 249:
                        item.getDefinitions().setValue(500);//buying price
                        break;
			case 251:
                        item.getDefinitions().setValue(1000);//buying price
                        break;
			case 253:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
			case 255:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
			case 257:
                        item.getDefinitions().setValue(5000);//buying price
                        break;
			case 259:
                        item.getDefinitions().setValue(7000);//buying price
                        break;
			case 263:
                        item.getDefinitions().setValue(8000);//buying price
                        break;
			case 261:
                        item.getDefinitions().setValue(10000);//buying price
                        break;
			case 265:
                        item.getDefinitions().setValue(14000);//buying price
                        break;
			case 267:
                        item.getDefinitions().setValue(15000);//buying price
                        break;
			case 269:
                        item.getDefinitions().setValue(18000);//buying price
                        break;
			case 3000:
                        item.getDefinitions().setValue(20000);//buying price
                        break;			
//Herb clean end.	
//Ingrediants start		
			case 221:
                        item.getDefinitions().setValue(100);//buying price
                        break;
			case 235:
                        item.getDefinitions().setValue(500);//buying price
                        break;
			case 225:
                        item.getDefinitions().setValue(1500);//buying price
                        break;
			case 223:
                        item.getDefinitions().setValue(2000);//buying price
                        break;
			case 239:
                        item.getDefinitions().setValue(3000);//buying price
                        break;
			case 231:
                        item.getDefinitions().setValue(300);//buying price
                        break;
			case 227:
                        item.getDefinitions().setValue(10);//buying price
                        break;
			case 233:
                        item.getDefinitions().setValue(300);//buying price
                        break;
		//Herb shop end

		
		
		
			
			case 2859:
                        item.getDefinitions().setValue(57);//Bones
                        break;
			case 2138:
                        item.getDefinitions().setValue(78);//chicken
                        break;
						
		//seeds	
			case 5318:
                        item.getDefinitions().setValue(500);//Potato seed
                        break;
			case 5291:
                        item.getDefinitions().setValue(5000);//avantoe
                        break;
			case 5295:
                        item.getDefinitions().setValue(7500);//ranar
                        break;
			case 5299:
                        item.getDefinitions().setValue(10000);//kwuarm
                        break;
			case 5302:
                        item.getDefinitions().setValue(15000);//landatime
                        break;
			case 5304:
                        item.getDefinitions().setValue(25000);//torstol
                        break;
	//seeds end					
	
			case 590:
                        item.getDefinitions().setValue(593);//tinderbox
                        break;
			case 1755:
                        item.getDefinitions().setValue(132);//chisel
                        break;
			case 1436:
                        item.getDefinitions().setValue(79);//rune essence
                        break;
			case 7936:
                        item.getDefinitions().setValue(324);//pure ess
                        break;
			case 15332:
                        item.getDefinitions().setValue(3000000);//overload
                        break;
			
			case 952:
                        item.getDefinitions().setValue(5324);//spade
                        break;
			case 11283:
                        item.getDefinitions().setValue(57000000);//DFS
                        break;
			case 11284:
                        item.getDefinitions().setValue(57000000);//dfs 2
                        break;
		//THIRD AGE				
						
			case 10330:
                        item.getDefinitions().setValue(97000000);//10m a pice
                        break;
			case 10332:
                        item.getDefinitions().setValue(97000000);//10m a pice
                        break;
			case 10334:
                        item.getDefinitions().setValue(97000000);//10m a pice
                        break;
			case 10336:
                        item.getDefinitions().setValue(97000000);//10m a pice
                        break;
			case 10338:
                        item.getDefinitions().setValue(75654000);//10m a pice
                        break;
			case 10340:
                        item.getDefinitions().setValue(75654000);//10m a pice
                        break;
			case 10342:
                        item.getDefinitions().setValue(75654000);//10m a pice
                        break;
			case 10344:
                        item.getDefinitions().setValue(75654000);//10m a pice
                        break;
			case 10346:
                        item.getDefinitions().setValue(124400043);//10m a pice
                        break;
			case 10348:
                        item.getDefinitions().setValue(124400043);//10m a pice
                        break;
			case 10350:
                        item.getDefinitions().setValue(124400043);//10m a pice
                        break;
			case 10352:
                        item.getDefinitions().setValue(124400043);//10m a pice
                        break;
						
			//THIRD AGE END			
						
			case 22458:
                        item.getDefinitions().setValue(23000000);//10m a pice
                        break;
			case 22462:
                        item.getDefinitions().setValue(23000000);//10m a pice
                        break;
			case 22466:
                        item.getDefinitions().setValue(23000000);//10m a pice
                        break;
			case 22452:
                        item.getDefinitions().setValue(5060403);//10m a pice
                        break;
			case 22454:
                        item.getDefinitions().setValue(5060403);//10m a pice
                        break;
			case 22456:
                        item.getDefinitions().setValue(5060403);//10m a pice
                        break;
			case 11286:
                        item.getDefinitions().setValue(56795890);//10m a pice
                        break;
			case 11702:
                        item.getDefinitions().setValue(250000000);//10m a pice
                        break;
			case 11704:
                        item.getDefinitions().setValue(90000000);//10m a pice
                        break;
			case 11706:
                        item.getDefinitions().setValue(150000000);//10m a pice
                        break;
			case 11708:
                        item.getDefinitions().setValue(110000000);//10m a pice
                        break;
			
			
			case 13746:
                        item.getDefinitions().setValue(137000000);//10m a pice
                        break;
			case 13748:
                        item.getDefinitions().setValue(493000000);//10m a pice
                        break;
			case 13750:
                        item.getDefinitions().setValue(427000000);//10m a pice
                        break;
			case 13752:
                        item.getDefinitions().setValue(87000000);//10m a pice
                        break;
			
			case 13738:
                        item.getDefinitions().setValue(196000000);//10m a pice
                        break;
			case 13740:
                        item.getDefinitions().setValue(552000000);//10m a pice
                        break;
			case 13742:
                        item.getDefinitions().setValue(486000000);//10m a pice
                        break;
			case 13744:
                        item.getDefinitions().setValue(146000000);//10m a pice
                        break;
			case 11335:
                        item.getDefinitions().setValue(78000000);//10m a pice
                        break;
			case 14479:
                        item.getDefinitions().setValue(29000000);//10m a pice
                        break;
			case 11730:
                        item.getDefinitions().setValue(14000000);//10m a pice
                        break;
			case 6585:
                        item.getDefinitions().setValue(200000);//10m a pice
                        break;
			case 22494:
                        item.getDefinitions().setValue(210000000);//10m a pice
                        break;
			case 22498:
                        item.getDefinitions().setValue(37000000);//10m a pice
                        break;
			case 22482:
                        item.getDefinitions().setValue(350000000);//10m a pice
                        break;
			case 22486:
                        item.getDefinitions().setValue(350000000);//10m a pice
                        break;
			case 22490:
                        item.getDefinitions().setValue(350000000);//10m a pice
                        break;
			case 22470:
                        item.getDefinitions().setValue(100000000);//10m a pice
                        break;
			case 22474:
                        item.getDefinitions().setValue(100000000);//10m a pice
                        break;
			case 22478:
                        item.getDefinitions().setValue(100000000);//10m a pice
                        break;
			case 11718:
                        item.getDefinitions().setValue(45000000);//10m a pice
                        break;
			case 11720:
                        item.getDefinitions().setValue(45000000);//10m a pice
                        break;
			case 11722:
                        item.getDefinitions().setValue(45000000);//10m a pice
                        break;
			
	//Bandos
			case 11724:
                        item.getDefinitions().setValue(100000000);//10m a pice
                        break;
			case 11726:
                        item.getDefinitions().setValue(100000000);//10m a pice
                        break;
			case 11728:
                        item.getDefinitions().setValue(25000000);//10m a pice
                        break;
						
	//					
			case 21787:
                        item.getDefinitions().setValue(157000000);//10m a pice
                        break;
			case 21790:
                        item.getDefinitions().setValue(94740000);//10m a pice
                        break;
			case 21793:
                        item.getDefinitions().setValue(74650000);//10m a pice
                        break;
              		
                	case 21736:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 21744:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 21752:
                        item.getDefinitions().setValue(10000000);
                        break;
                	case 21760:
                        item.getDefinitions().setValue(10000000);
                        break;//barrows ends
						
						
						
                //saradomin brew	
					case 6685:
                        item.getDefinitions().setValue(130000);
                        break;
				//saradomin brew	noted
					case 6686:
                        item.getDefinitions().setValue(130000);
                        break;


				//mage pot	
					case 3040:
                        item.getDefinitions().setValue(800);
                        break;
				//mage pot noted
					case 3041:
                        item.getDefinitions().setValue(800);
                        break;

				
				//Super restore
					case 3024:
                        item.getDefinitions().setValue(130000);
                        break;
						
				//super restore noted
					case 3025:
                        item.getDefinitions().setValue(130000);
                        break;
				
				//saradomin brew flask
					case 23351:
                        item.getDefinitions().setValue(200000);
                        break;
				//saradomin brew flask noted
					case 23352:
                        item.getDefinitions().setValue(200000);
                        break;
						
				
				//Super restore flask
					case 23399:
                        item.getDefinitions().setValue(200000);
                        break;
						
				//super restore flask noted
					case 23400:
                        item.getDefinitions().setValue(200000);
                        break;
						
						
				//dragon hatchet
					case 6739:
                        item.getDefinitions().setValue(5000000);
                        break;
						
				//dragon hatchet
					case 15259:
                        item.getDefinitions().setValue(5000000);
                        break;
						
						
						
                	case 11716:
                        item.getDefinitions().setValue(10000000);
                        break; // edge shop ends	
               		case 15486://mage shop starts here
                        item.getDefinitions().setValue(5000000);
                        break;
			case 6916:
                        item.getDefinitions().setValue(5000000);
                        break;						
                	case 6918:
                        item.getDefinitions().setValue(17000000);
                        break;
                	case 6924:
                        item.getDefinitions().setValue(12000000);
                        break;
                	case 6922:
                        item.getDefinitions().setValue(17000000);
                        break;
                	case 6920:
                        item.getDefinitions().setValue(3000000);
                        break;
                	case 6914:
                        item.getDefinitions().setValue(6000000);
                        break; // mage ends here	
			case 13887:
                        item.getDefinitions().setValue(150);
                        break;
			case 13893:
                        item.getDefinitions().setValue(150);
                        break;
			case 13884:
                        item.getDefinitions().setValue(150);
                        break;
			case 13890:
                        item.getDefinitions().setValue(150);
                        break;
			case 13896:
                        item.getDefinitions().setValue(150);
                        break;
			case 13899:
                        item.getDefinitions().setValue(50);
                        break;
			case 13902:
                        item.getDefinitions().setValue(50);
                        break;
			case 1050:
                        item.getDefinitions().setValue(30000);
                        break;
			case 7462:
                        item.getDefinitions().setValue(1000);
                        break;
			case 13867:
                        item.getDefinitions().setValue(50);
                        break;
			case 13858:
                        item.getDefinitions().setValue(50);
                        break;
			case 13861:
                        item.getDefinitions().setValue(50);
                        break;
			case 13864:
                        item.getDefinitions().setValue(50);
                        break;
			case 13870:
                        item.getDefinitions().setValue(50);
                        break;
			case 13873:
                        item.getDefinitions().setValue(50);
                        break;
			case 13876:
                        item.getDefinitions().setValue(50);
                        break;
			case 8845:
                        item.getDefinitions().setValue(100);
                        break;
			case 8846:
                        item.getDefinitions().setValue(125);
                        break;
			case 8847:
                        item.getDefinitions().setValue(150);
                        break;
			case 8848:
                        item.getDefinitions().setValue(200);
                        break;
			case 13879:
                        item.getDefinitions().setValue(10);
                        break;
			case 7459:
                        item.getDefinitions().setValue(200);
                        break;
			case 8849:
                        item.getDefinitions().setValue(300);
                        break;
			case 8850:
                        item.getDefinitions().setValue(400);
                        break;
			
			
			
			
			case 15088:			
                        item.getDefinitions().setValue(50000000);
                        break;
			case 15098:			
                        item.getDefinitions().setValue(100000000);
                        break;
			
						
			case 23740:
                        item.getDefinitions().setValue(2000);
                        break;
			case 23817:			
                        item.getDefinitions().setValue(2000);
                        break;
			case 20072:
                        item.getDefinitions().setValue(700);
                        break;
	
		//Start Rares
			case 1038:			
                        item.getDefinitions().setValue(20000000);
                        break;
			case 1040:			
                        item.getDefinitions().setValue(20000000);
                        break;
			case 1042:			
                        item.getDefinitions().setValue(20000000);
                        break;
			case 1044:			
                        item.getDefinitions().setValue(20000000);
                        break;
			case 1046:			
                        item.getDefinitions().setValue(200000000);
                        break;
			case 1048:			
                        item.getDefinitions().setValue(20000000);
                        break;
		//End Rares
			case 4566:			
                        item.getDefinitions().setValue(250000000);
                        break;
                	case 2497://range shop starts here
                        item.getDefinitions().setValue(2000);
                        break;
                	case 2491:
                        item.getDefinitions().setValue(5000);
                        break;
                	case 2581:
                        item.getDefinitions().setValue(2500000);
                        break;
                	case 2577:
                        item.getDefinitions().setValue(30000000);
                        break;
                	case 11235:
                        item.getDefinitions().setValue(5000000);
                        break;
                	case 11212:
                        item.getDefinitions().setValue(500);
                        break;
                	case 9341:
                        item.getDefinitions().setValue(300);
                        break;
                	case 4212:
                        item.getDefinitions().setValue(200000);
                        break;
                	
                	case 9144:
                        item.getDefinitions().setValue(800);
                        break;//range shop ends
			case 18830:
                        item.getDefinitions().setValue(80000);
			break;
			case 534:
                        item.getDefinitions().setValue(8000);
			break;
			case 532:
                        item.getDefinitions().setValue(4000);
			break;		
			case 3124:
                        item.getDefinitions().setValue(40000);
			break;
			*/
			
                }
		return item.getDefinitions().getValue() / 2;
	}

	public void sendExamine(Player player, int clickSlot) {
		int slotId = getSlotId(clickSlot);
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public void refreshShop() {
		for (Player player : viewingPlayers) {
			sendStore(player);
			player.getPackets().sendIComponentSettings(620, 25, 0,
					getStoreSize() * 6, 1150);
		}
	}

	public int getStoreSize() {
		return mainStock.length
				+ (generalStock != null ? generalStock.length : 0);
	}

	public void sendStore(Player player) {
		Item[] stock = new Item[mainStock.length
				+ (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(mainStock, 0, stock, 0, mainStock.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, mainStock.length,
					generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

}
