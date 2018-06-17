package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk; 
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.PickPocketAction;
import com.rs.game.player.actions.PickPocketableNPC;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.Slayer.SlayerMonsters;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.exchange.GrandExchange;
import com.rs.game.player.dialogues.FremennikShipmaster;
import com.rs.game.WorldTile;
import com.rs.io.InputStream;
import com.rs.utils.Utils;
import com.rs.utils.ShopsHandler;

public class NPCHandler {

	public static void handleOption1(final Player player, InputStream stream) {
		@SuppressWarnings("unused")
		boolean unknown = stream.readByte128() == 1;
		int npcIndex = stream.readUnsignedShort128();
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!Slayer.checkRequirement(player,SlayerMonsters.forId(npc.getId()))){
				player.stopAll();
				return;
			}
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Banker", npc.getId());
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setSkill(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}
				npc.faceEntity(player);
				
				if(player.getRights() > 9){
						player.getPackets().sendGameMessage("NPC id: " + npc.getId());
					
					}
				
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				if(npc.getId() == 1419 || npc.getId() == 2240 || npc.getId() == 2241 || npc.getId() == 2593 || npc.getId() == 15529 || npc.getId() == 15530) {
				    GrandExchange.openGE(player);
				}
				if (npc.getId() == 659){
					ShopsHandler.openShop(player, 76);
					player.sm("You have "+player.VotePoints+" vote points. Go vote everyday for more.");
					return;
				}
			//shop tele
				if (npc.getId() == 5027){
					npc.setNextAnimation(new Animation(2246));
					player.setNextAnimation(new Animation(2246));
					player.setNextWorldTile(new WorldTile(2598,4774, 0));
					player.sm("You can also type ::shops to get here.");
					return;
				}
		//super shop
				if (npc.getId() == 692){
				
				if (player.getRights() >= 2) {
					ShopsHandler.openShop(player, 8);
					
				}
				
				else {
				
				player.sm("You need to be super donator+ to enter this store.");
				return;
				}}
			//end	
			
			else if (npc.getId() == 6971)//summon shop
					player.getDialogueManager().startDialogue("Summon", npc.getId());
			
			else if (npc.getId() == 6970)//summon shop
					player.getDialogueManager().startDialogue("Summon", npc.getId());
			
					//Minigame			
					else if (npc.getId() == 852)
					player.getDialogueManager().startDialogue("OgreGames", npc.getId());
						
						
			//Training slave quest			
				else if (npc.getId() == 13295)
					player.getDialogueManager().startDialogue("Training", npc.getId());
					
			//Training slave quest			
				else if (npc.getId() == 15032)
					player.getDialogueManager().startDialogue("DiengS", npc.getId());
					
				//poanizer
			else if (npc.getId() == 17153)
					player.getDialogueManager().startDialogue("Poanizerfight", npc.getId());
			
					
					
				else if (npc.getId() == 1576)
					player.getDialogueManager().startDialogue("Talker9", npc.getId());
					
					
					
			else if (npc.getId() == 17154)
					player.getDialogueManager().startDialogue("PoanizerPet", npc.getId());
					
					
				if (npc.getId() == 1569)
					player.getDialogueManager().startDialogue("Veliaf", 
							npc.getId());
							
		//clothes
			 if (npc.getId() == 1039)
					player.getDialogueManager().startDialogue("MStyler", npc.getId());
					
			 if (npc.getId() == 285)
				player.getDialogueManager().startDialogue("FStyler", npc.getId());
						
				
							
//ge man						
							if (npc.getId() == 2262)
					player.getDialogueManager().startDialogue("gemage",
							npc.getId());
//ge man							
//notices			
                if (npc.getId() == 6136)
					player.getDialogueManager().startDialogue("notices",
							npc.getId());
			
							
							
			
							
							//notices	
//revs
				if (npc.getId() == 1413)
					player.getDialogueManager().startDialogue("revs",
							npc.getId());
//revs end
				if (npc.getId() == 12379)
					player.getDialogueManager().startDialogue("GrimReaper", 
							npc.getId());
				if (npc.getId() == 3709)
					player.getDialogueManager().startDialogue("MrEx",
							npc.getId());
//Poanizer
				if (npc.getId() == 5580)
					player.getDialogueManager().startDialogue("Poanizer", npc.getId());
			//lewis	
					if (npc.getId() == 11254){
						switch (Utils.getRandom(5)) {
									case 0: 
											npc.setNextForceTalk(new ForceTalk("Im buff."));
										break;
										
									case 1: 
											npc.setNextForceTalk(new ForceTalk("Suck it!"));
											npc.setNextAnimation(new Animation(10298));
										break;

									case 2: 
											player.setNextForceTalk(new ForceTalk(">:C"));
											player.setNextAnimation(new Animation(7181));
											npc.setNextAnimation(new Animation(734));
										break;
											
									case 3: 
										
											npc.setNextForceTalk(new ForceTalk("You cant see me."));
											npc.setNextAnimation(new Animation(7230));
										break;
										
									case 4: 
											npc.setNextForceTalk(new ForceTalk("Oh yea....."));
											npc.setNextAnimation(new Animation(7253));
											player.setNextAnimation(new Animation(7253));
										break;

									case 5: 
											npc.setNextForceTalk(new ForceTalk("Moooooney"));
											npc.setNextAnimation(new Animation(7307));
										break;
										
									
									
								
								}
							}
			/*	else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("vote",
							npc.getId()); */
				if (npc.getId() == 3374)
					player.getDialogueManager().startDialogue("Max",
							npc.getId());
				if (npc.getId() == 14078){
					player.getDialogueManager().startDialogue("Varnis",
							npc.getId());
							player.sm("Starting Varnis Dialogue");
							return;
							}
				//Construction			
				if (npc.getId() == 5867)
					player.getDialogueManager().startDialogue("ConBuilder",
							npc.getId());
				if (npc.getId() == 4247)
					player.getDialogueManager().startDialogue("ConHelp",
							npc.getId());
				if (npc.getId() == 945)
					player.getDialogueManager().startDialogue("Guide",
							npc.getId());
						
				//Arena
				if (npc.getId() == 6539)
					player.getDialogueManager().startDialogue("ArenaOut",
							npc.getId());
							
				else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), false);
				 if (npc.getId() == 8461){
					ShopsHandler.openShop(player, 31);
					}
					//player.getDialogueManager().startDialogue("SlayShop", npc.getId());
				//fish shop	
				if (npc.getId() == 219)
					ShopsHandler.openShop(player, 4);
					
				//QBD shop
				else if (npc.getId() == 400)
					ShopsHandler.openShop(player, 98);
					
			//pure shop
				else if (npc.getId() == 210)
					ShopsHandler.openShop(player, 7);

			//scrolls
				else if (npc.getId() == 220)
					player.getDialogueManager().startDialogue("Scrolls",
							npc.getId(), 0);
					
				//else if (npc.getId() == 1208)
					//ShopsHandler.openShop(player, ); PK TOKKEN
				else if (npc.getId() == 873)
					ShopsHandler.openShop(player, 34);
				else if (npc.getId() == 523)
					ShopsHandler.openShop(player, 49);
					
				else if (npc.getId() == 530)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 22);
				else if (npc.getId() == 6537)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 16);//Magic supplies
				else if (npc.getId() == 5113)
					ShopsHandler.openShop(player, 18);	
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 346)//range armour
					ShopsHandler.openShop(player, 14);
				else if (npc.getId() == 1694)//range weapon
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 211)//melee armour
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 454)
                    ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 9711)
					ShopsHandler.openShop(player, 89);
				else if (npc.getId() == 2253)
                    ShopsHandler.openShop(player, 26);
				//reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                else if (npc.getId() == 549)
                    ShopsHandler.openShop(player, 200);
				else if (npc.getId() == 544)
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 2830)
					ShopsHandler.openShop(player, 29);
				else if (npc.getId() == 7746)
					ShopsHandler.openShop(player, 90);	
				else if (npc.getId() == 4555)
					ShopsHandler.openShop(player, 99);	
				else if (npc.getId() == 948)
					ShopsHandler.openShop(player, 23);
			else if (npc.getId() == 524){//mage gear
				ShopsHandler.openShop(player, 6);	
				}	
				else if (npc.getId() == 445)
					ShopsHandler.openShop(player, 23);
				
				//else if (npc.getId() == 6970)//summon shop
				//	ShopsHandler.openShop(player, 25);
				
				else if (npc.getId() == 537)
					ShopsHandler.openShop(player, 26);	
				/*else if (npc.getId() == 637)
					ShopsHandler.openShop(player, 27);	*/
				else if (npc.getId() == 2732)
					ShopsHandler.openShop(player, 26);
				else if (npc.getId() == 566)
					ShopsHandler.openShop(player, 95);
				//else if (npc.getId() == 241)
				//	ShopsHandler.openShop(player, 12);
				else if (npc.getId() == 540)
					ShopsHandler.openShop(player, 29);
				//else if (npc.getId() == 14057)
				//	ShopsHandler.openShop(player, );  PK TOKEN 
				else if (npc.getId() == 15661)
					player.getInterfaceManager().sendInterface(506);	
				else if (npc.getId() == 598)
					player.getInterfaceManager().sendInterface(309);
				else if (npc.getId() == 4906)
					ShopsHandler.openShop(player, 27);						
				else if (npc.getId() == 2676)
					player.getDialogueManager().startDialogue("MakeOverMage",
							npc.getId(), 0);
				else if (npc.getId() == 8539)
					player.getDialogueManager().startDialogue("Queeeen",
							npc.getId(), 0);
			    else if (npc.getId() == 410)
                    player.getDialogueManager().startDialogue("Gambler", npc);
				else if (npc.getId() == 8540)
                    player.getDialogueManager().startDialogue("SantaClaus2013", npc);
				else if (npc.getId() == 8541)
                    player.getDialogueManager().startDialogue("Headsnowimp2013", npc);
				else if (npc.getId() == 819)
                    player.getDialogueManager().startDialogue("Frank", npc);
				else if (npc.getId() == 2825)
                    player.getDialogueManager().startDialogue("rebelpete", npc);
//Xmas Event		
/*			
				else if (npc.getId() == 9400)
					player.getDialogueManager().startDialogue("XmasEvent",
							npc.getId(), 0);
				else if (npc.getId() == 8541)
					player.getDialogueManager().startDialogue("XmasInfo",
							npc.getId(), 0);
							
				else if (npc.getId() == 6739)
					ShopsHandler.openShop(player, 46);	
					
		//HNS
		
				else if (npc.getId() == 2825)
					player.getDialogueManager().startDialogue("pirate1", npc.getId());
					
				else if (npc.getId() == 5447)
					player.getDialogueManager().startDialogue("dance1", npc.getId());
				else if (npc.getId() == 618)
					player.getDialogueManager().startDialogue("exam1", npc.getId());
							
			//Pirate
				else if (npc.getId() == 6745)
					player.getDialogueManager().startDialogue("snowman3",
							npc.getId(), 0);
			//Dwarf
				else if (npc.getId() == 6744)
					player.getDialogueManager().startDialogue("snowman2",
							npc.getId(), 0);	
			//Dragon
				else if (npc.getId() == 6743)
					player.getDialogueManager().startDialogue("snowman1",
							npc.getId(), 0);
					
				*/
							
							
				else if (npc.getId() == 9715)
					player.getDialogueManager().startDialogue("Talker1",
							npc.getId(), 0);
				else if (npc.getId() == 8516)
					player.getDialogueManager().startDialogue("Talker2",
							npc.getId(), 0);
				else if (npc.getId() == 8517)
					player.getDialogueManager().startDialogue("Talker5",
							npc.getId(), 0);
				else if (npc.getId() == 15582)
					player.getDialogueManager().startDialogue("Talker3",
							npc.getId(), 0);
				else if (npc.getId() == 7571)
					player.getDialogueManager().startDialogue("polypore",
							npc.getId(), 0);
				
				else {
						//player.getDialogueManager().startDialogue("Reply",
							//npc.getId(), 0);
							
				
				
				
				
				/*	player.getPackets().sendGameMessage(
							"Nothing interesting happens.");*/
					if (Settings.DEBUG)
						System.out.println("Unmade Click1 option at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
								
					if (Settings.SNOOP)
						System.out.println(player.getUsername() + " NPC'1' ID: "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());		
				}
			
			
			
			
			}
		}, npc.getSize()));
	}

	public static void handleOption2(final Player player, InputStream stream) {
		@SuppressWarnings("unused")
		boolean unknown = stream.readByte128() == 1;
		int npcIndex = stream.readUnsignedShort128();
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		}
	
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setSkill(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setSkill(
							new PickPocketAction(npc, pocket));
					return;
				}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage(
									"Your arent poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				if (npc.getDefinitions().hasPickupOption()
						|| npc.getDefinitions().hasTakeOption()) {
					if (!player.withinDistance(npc, 2)) {
						return;
					}
					player.faceEntity(npc);
					if (player.getPetFollow() != player.getIndex()) {
						player.sendMessage("This isn't your pet!");
						return;
					}
					if (player.getPetId() == 0) {
						return;
					}
					player.getPet().dissmissPet(false);
					return;
				}
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;
				if(npc.getId() == 1419 || npc.getId() == 2240 || npc.getId() == 2241 || npc.getId() == 2593 || npc.getId() == 15529 || npc.getId() == 15530) {
				    GrandExchange.openGE(player);
					}
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 13455)
					player.getBank().openBank();
		
				

					
			//lewis 2	
				if (npc.getId() == 11254){
					player.setNextAnimation(new Animation(881));
					player.getPackets().sendGameMessage("You touch Lewis's Balls...");
					npc.setNextForceTalk(new ForceTalk("Dont touch my Balls!"));
					npc.setNextAnimation(new Animation(10493));
					//player.getInventory().addItem(12130 , 1);
					player.getPackets().sendGameMessage("You then think Why did I do that?");
					}
						
				else if (npc.getId() == 8461)
					player.getDialogueManager().startDialogue("Turael", npc.getId());
		//Minigame			
				else if (npc.getId() == 852)
					player.getDialogueManager().startDialogue("OgreGames", npc.getId());
		/*xmas HNS			
				else if (npc.getId() == 519)
					player.getDialogueManager().startDialogue("bob1", npc.getId());*/
				
				
					//XP Lamp shop
				else if (npc.getId() == 520) 
					ShopsHandler.openShop(player, 27);
					
				//hood shop
				else if (npc.getId() == 544) 
					ShopsHandler.openShop(player, 5);
					
						//styler3
				else if (npc.getId() == 4518){
					player.getDialogueManager().startDialogue("MClothe", npc.getId());		
					}	
				if (npc.getId() == 14057){
					player.getDialogueManager().startDialogue("Velio",
							npc.getId());
							//player.sm("Starting Velio Dialogue");
							}
				if (npc.getId() == 14078){
					player.getDialogueManager().startDialogue("Varnis",
							npc.getId());
						//	player.sm("Starting Varnis Dialogue");
							}
				else if (npc.getId() == 521)
					ShopsHandler.openShop(player, 3);
				else if (npc.getId() == 522)
					ShopsHandler.openShop(player, 4);
				else if (npc.getId() == 526)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 15661)
					player.getDialogueManager().startDialogue("Talker3",
							npc.getId(), 0);
				else if (npc.getId() == 527)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 528)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 529)
					ShopsHandler.openShop(player, 11);
				else if (npc.getId() == 531)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 554)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 555)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 561)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 1699)
					ShopsHandler.openShop(player, 21);
				else if (npc.getId() == 1917)
					ShopsHandler.openShop(player, 22);
				else if (npc.getId() == 11678)
					ShopsHandler.openShop(player, 23);
				else if (npc.getId() == 11679)
					ShopsHandler.openShop(player, 24);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 556)
					ShopsHandler.openShop(player, 28);
				else if (npc.getId() == 540)
					ShopsHandler.openShop(player, 29);
			/*	else if (npc.getId() == 541)
					ShopsHandler.openShop(player, 30); */
				/*else if (npc.getId() == 542)
					ShopsHandler.openShop(player, 31);*/
				else if (npc.getId() == 545)
					ShopsHandler.openShop(player, 33);
	
					
				
				else if (npc.getId() == 6893)
					ShopsHandler.openShop(player, 36);
				else if (npc.getId() == 15582)
					player.getDialogueManager().startDialogue("Talker4",
							npc.getId(), 0);
				else if (npc.getId() == 2676)
					PlayerLook.openMageMakeOver(player);
				else {
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
					if (Settings.DEBUG)
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
					if (Settings.SNOOP)
						System.out.println(player.getUsername() + " NPC'2' ID: "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
								
								
			
				}
			}
		}, npc.getSize()));
	}
}