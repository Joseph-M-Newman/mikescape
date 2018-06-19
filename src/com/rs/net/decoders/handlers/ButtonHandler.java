package com.rs.net.decoders.handlers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;
import com.rs.game.ForceTalk;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.Smithing.ForgingInterface;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.summoning.SummonTrain;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Commands;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.exchange.GrandExchangeConstants;
import com.rs.game.player.controlers.DuelControler;
import com.rs.game.player.dialogues.LevelUp;
import com.rs.game.player.dialogues.Transportation;
import com.rs.game.player.dialogues.Talker3;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.minigames.CastleWars;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.utils.Hiscores;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class ButtonHandler {

	public static void handleButtons(final Player player, InputStream stream,
			int packetId) {
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		if (player.isDead()
				|| !player.getInterfaceManager().containsInterface(interfaceId))
			return;
		final int componentId = interfaceHash - (interfaceId << 16);
		if (componentId != 65535
				&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		final int slotId2 = stream.readUnsignedShortLE128();
		final int slotId = stream.readUnsignedShort();
		if (!player.getControlerManager().processButtonClick(interfaceId,
				componentId, slotId, packetId))
			return;
		if (interfaceId == 548 || interfaceId == 746) {
			if ((interfaceId == 548 && componentId == 180)
					|| (interfaceId == 746 && componentId == 182)) {
				if (player.getInterfaceManager().containsScreenInter()
						|| player.getInterfaceManager()
								.containsInventoryInter()) {
					// TODO cant open sound
					player.getPackets()
							.sendGameMessage(
									"Please finish what you're doing before opening the world map.");
					return;
				}
				// world map open
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash); // map open
				// center
				// pos
				player.getPackets().sendGlobalConfig(674, posHash); // player
				// position
			} else if ((interfaceId == 548 && componentId == 0)
					|| (interfaceId == 746 && componentId == 229)) {
				// xp counter reset
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					player.getSkills().resetXpCounter();
			}
		} else if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout();
					//Hiscores.saveHighScore(player);
			if (player.getRights() <= 1){
					//Highscores.saveHighScore(player);
			}
		else if (interfaceId == 103){
		if (componentId == 10) 
			player.stopAll();
			player.addStopDelay(6);
			player.useStairs(10584, new WorldTile(3080,3487, 0), 2, 3,"Wrong.");
			
			
		 if (componentId == 11) 
			player.stopAll();
			player.addStopDelay(6);
			player.useStairs(10584, new WorldTile(3080,3487, 0), 2, 3,"Wrong.");
		
		 if (componentId == 12) 
			player.stopAll();
			player.addStopDelay(6);
			player.useStairs(10584, new WorldTile(3080,3487, 0), 2, 3,"Wrong.");
		
		 if (componentId == 13) 
			player.stopAll();
			player.addStopDelay(2);
			player.closeInterfaces();
		
		
	}		
			
		} else if (interfaceId == 672){
			if (componentId == 16) {
				switch(slotId) {
				case 2: //				level,shards,Charm,Item,2nd item (-1 = null),Pouch,xp (times by 5)
					SummonTrain.CreatePouch(player, 1, 7,12158,2859,-1,12047,48);
					return;
				case 7:
					SummonTrain.CreatePouch(player, 4, 8,12158,2138,-1,12043,93);
					break;
				case 12:
					SummonTrain.CreatePouch(player, 10, 8,12158,6291,-1,12059,126);
					break;
				case 17:
					SummonTrain.CreatePouch(player, 13, 9,12158,3363,-1,12019,126);
					break;
				case 22:
					SummonTrain.CreatePouch(player, 16, 7,12158,440,-1,12009,216);
					break;
				case 27:
					SummonTrain.CreatePouch(player, 17, 1,12158,6319,-1,12778,465);
					break;
				case 32:
					SummonTrain.CreatePouch(player, 18, 45,12159,1783,-1,12049,312);
					break;
				case 37:
					SummonTrain.CreatePouch(player, 19, 57,12160,3095,-1,12055,832);
					break;
				case 42:
					SummonTrain.CreatePouch(player, 22, 64,12160,12168,-1,12808,968);
					break;
				case 47:
					SummonTrain.CreatePouch(player, 23, 75,12163,2134,-1,12067,1024);
					break;
				case 52:
					SummonTrain.CreatePouch(player, 25, 51,12163,3138,-1,12063,500);
					break;
				case 57:
					SummonTrain.CreatePouch(player, 28, 47,12159,6032,-1,12091,498);
					break;
				case 62:
					SummonTrain.CreatePouch(player, 29, 84,12163,9976,-1,12800,552);
					break;
				case 67:
					SummonTrain.CreatePouch(player, 31, 81,12160,3325,-1,12053,1360);
					break;
				case 72:
					SummonTrain.CreatePouch(player, 32, 84,12160,12156,-1,12065,1408);
					break;
				case 77:
					SummonTrain.CreatePouch(player, 33, 72,12159,1519, -1,12021,576);
					break;
				case 82:
					SummonTrain.CreatePouch(player, 34, 74,12159,12164,-1,12818,596);
					break;
				case 87:
					SummonTrain.CreatePouch(player, 34, 74, 12163,12166,-1, 12780,1096);
					break;
				case 92:
					SummonTrain.CreatePouch(player, 34, 74, 12163, 12167,-1,12798,1096);
					break;
				case 97:
					SummonTrain.CreatePouch(player, 34, 74, 12163,12165,-1,12814,2096);
					break;
				case 107:
					SummonTrain.CreatePouch(player, 40, 11, 12158, 6010,-1,12086,528);
					break;
				case 117:
					SummonTrain.CreatePouch(player, 42, 104, 12160, 12134, -1, 12051, 1848);
					break;
				case 122:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12109, -1, 12095, 752);
					break;
				case 127:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12111, -1, 12097, 752);
					break;
				case 132:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12113, -1, 12099, 752);
					break;
				case 137:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12115, -1, 12101, 752);
					break;
				case 347:
					SummonTrain.CreatePouch(player, 85, 150, 12160, 10149, -1, 12776, 4500);
					break;
//Custom adds	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					
				case 387:
					SummonTrain.CreatePouch(player, 99, 200, 12160, 1119, -1, 12790, 10000);//Steel titan
					break; 
	
				case 102:
					SummonTrain.CreatePouch(player, 36, 102, 12163, 2349, -1, 12073, 2300);//Bronze mino
					break; 
					
				case 112:
					SummonTrain.CreatePouch(player, 41, 78, 12159, 249, -1, 12071, 1000);//Macaw
					break; 
				
					
				case 142:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12117, -1, 12103, 1000);//sp pengatrece
					break; 
					
				case 147:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12119, -1, 12105, 1000);//sp coraxatrice
					break; 
					
					
				case 152:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12121, -1, 12107, 1000);//sp vulatrice
					break; 
					
				case 157:
					SummonTrain.CreatePouch(player, 46, 125, 12163, 2351, -1, 12075, 2900);//iron mino
					break; 
					
				case 162:
					SummonTrain.CreatePouch(player, 46, 111, 12160, 590, -1, 12816, 1030);//Pyrelord
					break; 
					
				case 167:
					SummonTrain.CreatePouch(player, 47, 88, 12159, 1635, -1, 12041, 1020);//Magpie
					break;

				case 172:
					SummonTrain.CreatePouch(player, 49, 117, 12160, 2132, -1, 12061, 1040);//Bloated leech
					break; 
					
				case 177:
					SummonTrain.CreatePouch(player, 52, 12, 12158, 9978, -1, 12007, 2000);//terror bird
					break; 
					
				case 182:
					SummonTrain.CreatePouch(player, 54, 106, 12159, 12161, -1, 12035, 2000);//abyssal parasite
					break; 
					
				case 187:
					SummonTrain.CreatePouch(player, 55, 151, 12163, 1937, -1, 12027, 3200);//Spirit Jelly
					break; 
					
				case 192:
					SummonTrain.CreatePouch(player, 56, 141, 12163, 2353, -1, 12077, 3400);//steel mino
					break; 
					
				case 197:
					SummonTrain.CreatePouch(player, 56, 109, 12159, 311, -1, 12531, 2500);//ibis
					break; 
					
				case 202:
					SummonTrain.CreatePouch(player, 57, 153, 12163, 10103, -1, 12812, 3700);//spirit kyatt
					break; 
					
				case 207:
					SummonTrain.CreatePouch(player, 57, 155, 12163, 10095, -1, 12784, 3700);//spirit larupia
					break;

				case 212:
					SummonTrain.CreatePouch(player, 57, 153, 12163, 10099, -1, 12810, 3700);//spirit garaahk
					break; 
					
				case 217:
					SummonTrain.CreatePouch(player, 58, 144, 12163, 6667, -1, 12023, 4000);//karamthulu overlord
					break;

				case 222:
					SummonTrain.CreatePouch(player, 61, 141, 12160, 9736, -1, 12085, 3000);//dust devil
					break; 
					
				case 227:
					SummonTrain.CreatePouch(player, 62, 119, 12159, 12161, -1, 12037, 3100);//abysal lurker
					break;
					
				case 232:
					SummonTrain.CreatePouch(player, 63, 116, 12160, 6287, -1, 12015, 3200);//spirit cobra
					break; 
					
				case 237:
					SummonTrain.CreatePouch(player, 64, 128, 12160, 8431, -1, 12045, 3250);//stranger plant
					break; 
					
				case 242:
					SummonTrain.CreatePouch(player, 66, 152, 12163, 2359, -1, 12079, 4500);//mithril mino
					break; 
					
				case 247:
					SummonTrain.CreatePouch(player, 66, 11, 12158, 2150, -1, 12123, 3400);//barker toad
					break; 
					
				case 252:
					SummonTrain.CreatePouch(player, 67, 1, 12158, 7939, -1, 12031, 3600);//war tortise
					break;
					
				case 257:
					SummonTrain.CreatePouch(player, 68, 110, 12159, 383, -1, 12029, 3700);//bunyip
					break;
					
				case 262:
					SummonTrain.CreatePouch(player, 69, 130, 12159, 1963, -1, 12033, 3800);//fruit bat
					break;
					
				case 267:
					SummonTrain.CreatePouch(player, 70, 130, 12160, 1933, -1, 12820, 3900);//ravenging locust
					break;
					
				case 272:
					SummonTrain.CreatePouch(player, 71, 14, 12158, 10117, -1, 12057, 4000);//arctic bear
					break;
					
				case 277:
					SummonTrain.CreatePouch(player, 72, 165, 12160, 14616, -1, 14623, 4100);//pheonix pouch
					break;
					
				case 282:
					SummonTrain.CreatePouch(player, 73, 195, 12163, 12168, -1, 12792, 6000);//obsidion golem
					break;
					
				case 287:
					SummonTrain.CreatePouch(player, 74, 166, 12160, 6983, -1, 12069, 4300);//granite lobster
					break;
					
				case 292:
					SummonTrain.CreatePouch(player, 75, 168, 12160, 2460, -1, 12011, 4400);//mantis
					break;
					
				case 297:
					SummonTrain.CreatePouch(player, 76, 141, 12159, 10020, -1, 12782, 4500);//forge regent
					break;
					
				case 302:
					SummonTrain.CreatePouch(player, 76, 144, 12163, 2361, -1, 12081, 5400);//ady mino
					break;
				
				case 307:
					SummonTrain.CreatePouch(player, 77, 174, 12160, 12162, -1, 12794, 4700);//talon beast
					break;
					
				case 312:
					SummonTrain.CreatePouch(player, 78, 124, 12159, 5933, -1, 12013, 4800);//giant ent
					break;
					
				case 317:
					SummonTrain.CreatePouch(player, 79, 198, 12163, 1442, -1, 12802, 6000);//fire titan
					break;
					
				case 322:
					SummonTrain.CreatePouch(player, 79, 202, 12163, 1440, -1, 12804, 6500);//moss titan
					break;
					
				case 327:
					SummonTrain.CreatePouch(player, 79, 198, 12163, 1440, 1438, 1444, 7000);//ice titan
					break;
					
				case 332:
					SummonTrain.CreatePouch(player, 80, 128, 12159, 571, -1, 12025, 5100);//hydra
					break;
					
				case 337:
					SummonTrain.CreatePouch(player, 83, 1, 12160, 6155, -1, 12017, 5200);//spirit dagonoth
					break;
					
				case 342:
					SummonTrain.CreatePouch(player, 83, 219, 12160, 12168, -1, 12788, 5300);//lava titan
					break;
				
					
				case 352:
					SummonTrain.CreatePouch(player, 86, 1, 12163, 2363, -1, 12083, 7000);//rune mino
					break;
					
				case 357:
					SummonTrain.CreatePouch(player, 88, 140, 12159, 237, -1, 12039, 5500);//unicorn stallion
					break;
					
				case 362:
					SummonTrain.CreatePouch(player, 89, 222, 12163, 1444, -1, 12786, 7600);//geyser titan
					break;
					
				case 367:
					SummonTrain.CreatePouch(player, 92, 203, 12160, 3226, 2859, 12089, 7000);//wolpertinger
					break;
				
				case 372:
					SummonTrain.CreatePouch(player, 93, 113, 12159, 12161, -1, 12796, 7500);//abyss titan
					break;
					
				case 377:
					SummonTrain.CreatePouch(player, 95, 198, 12160, 1115, -1, 12822, 9000);//iron titan
					break;
					
				case 382:
					SummonTrain.CreatePouch(player, 96, 211, 12160, 10818, -1, 12093, 9500);//pack yak
					break;


					//				level,shards,Charm,Item,2nd item (-1 = null),Pouch,xp (times by 5)
				default:
					player.sm("This pouch is going to be added soon.");
					//logger.debug("summonButton: "+buttonId2+".");
					break;
				}
				
			if (componentId == 19) {
				player.closeInterfaces();
				player.getDialogueManager().startDialogue("Scrolls");
				
				
				}	
			} 
			
			
			
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null)
				return;
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 747) {
			if (componentId == 7) {
				Familiar.selectLeftOption(player);
			} else if (player.getFamiliar() == null)
				return;
			if (componentId == 10 || componentId == 19)
				player.getFamiliar().call();
			else if (componentId == 11 || componentId == 20)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 12 || componentId == 21)
				player.getFamiliar().takeBob();
			else if (componentId == 13 || componentId == 22)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 18 || componentId == 18)
				player.getFamiliar().sendFollowerDetails();
			else if (componentId == 17) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 309) {
			PlayerLook.handleBeardButtons(player, componentId, slotId);
		} else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendExecMessage(
						"cmd.exe /c start " + Settings.WEBSITE_LINK);
			}
		} else if (interfaceId == 590) {
			player.getEmotesManager().useBookEmote(slotId);
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 336) {
			if (componentId == 0)
				if(packetId == 81) {
					player.getTemporaryAttributtes().put("offerX",
							Integer.valueOf(slotId));
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
				if (packetId == 61)
					player.getTrade().addItem(player, slotId, 1);
				else if (packetId == 64)
					player.getTrade().addItem(player, slotId, 5);
				else if (packetId == 4)
					player.getTrade().addItem(player, slotId, 10);
				else if (packetId == 52)
					player.getTrade().addItem(player, slotId, 0x7fffffff);
				else if (packetId == 91)
					player.getPackets().sendGameMessage(
							(new StringBuilder("A "))
									.append((new Item(slotId2, 1))
											.getDefinitions().getName()
											.toLowerCase())
									.append("'s value is ")
									.append(ItemDefinitions.getItemDefinitions(
											slotId2).getValue())
									.append(" gold.").toString());
				} else if (interfaceId == 105){
					System.out.println("Component: "+componentId+" Packet: "+stream.getId());
						switch(componentId) {
							case 190:
								player.getPackets().setGeSearch(GrandExchangeConstants.SEARCH);
							break;
							default:
					World.getGrandExchange().handleButtons(player, componentId, stream.getId());
						break;
						}
					return;
		} else if (interfaceId == 335) {
			if (componentId == 34)
				if (packetId == 25)
					player.getBank().sendExamine(slotId2);
				else if (packetId == 61)
					player.getPackets().sendGameMessage(
							(new StringBuilder("A "))
									.append((new Item(slotId2, 1))
											.getDefinitions().getName()
											.toLowerCase())
									.append("'s value is ")
									.append(ItemDefinitions.getItemDefinitions(
											slotId2).getValue())
									.append(" gold.").toString());
			if (componentId == 31) 
				if (packetId == 61)
					player.getTrade().removeItem(player, slotId, 1);
				else if (packetId == 64)
					player.getTrade().removeItem(player, slotId, 5);
				else if (packetId == 4)
					player.getTrade().removeItem(player, slotId, 10);
				else if (packetId == 52)
					player.getTrade()
					.removeItem(player, slotId, 0x7fffffff);
				else if (packetId == 25)
					//layer.getBank().sendExamine(slotId);
					return;
				else if (packetId == 91)
					player.getPackets().sendGameMessage(
							(new StringBuilder("A "))
									.append((new Item(slotId2, 1))
											.getDefinitions().getName()
											.toLowerCase())
									.append("'s value is ")
									.append(ItemDefinitions.getItemDefinitions(
											slotId2).getValue())
									.append(" gold.").toString());
				else if (packetId == 81)  {
					player.getTemporaryAttributtes().put("removeX",
							Integer.valueOf(slotId));
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			if (componentId == 18 || componentId == 12) {
				player.getTrade().tradeFailed();
			}
			else if (componentId == 16)
				player.getTrade().acceptPressed(player);
		} else if (interfaceId == 334) {
			if (componentId == 22) {
				player.getTrade().tradeFailed();
			}
			else if (componentId == 21)
				player.getTrade().acceptPressed(player);
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("pc_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
/*		} else if (interfaceId == 672) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					Summoning.sendCreatePouch(player, slotId2, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					Summoning.sendCreatePouch(player, slotId2, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					Summoning.sendCreatePouch(player, slotId2, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					Summoning.sendCreatePouch(player, slotId2,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					Summoning.sendCreatePouch(player, slotId2, 28);// x
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					player.getPackets().sendGameMessage(
							"You currently need "
									+ ItemDefinitions.getItemDefinitions(
											slotId2)
											.getCreateItemRequirements());
				}
			}*/
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 665) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				}
				
		} else if (interfaceId == 72) {
				//messy as fark but YOLO
				String levelmess = String.valueOf(player.levelmessage);
				String Plists = String.valueOf(player.PList);
				String loginmess = String.valueOf(player.loginmessages);
				String donarmess = String.valueOf(player.donorMess);
				String wgmess = String.valueOf(player.wgmess);
				String itemdropmess = String.valueOf(player.itemdropmess);
				
		String Colour1;	
		String Colour2;	
		String Colour3;	
		String Colour4;	
		String Colour5;	
		String Colour6;	
			if(levelmess == "false"){
					Colour1 ="<col=ff0000>";
					}
					else{
					Colour1 ="<col=00FF00>"; 
					}
			if(Plists == "false"){
					Colour2 ="<col=ff0000>"; 
					}
					else{
					Colour2 ="<col=00FF00>"; 
					}
			if(loginmess == "false"){
					Colour3 ="<col=ff0000>"; 
					}
					else{
					Colour3 ="<col=00FF00>";
					}
			if(donarmess == "false"){
					Colour4 ="<col=ff0000>"; 
					}
					else{
					Colour4 ="<col=00FF00>"; 
					}
			if(wgmess == "false"){
					Colour5 ="<col=ff0000>"; 
					}
					else{
					Colour5 ="<col=00FF00>"; 
					}
			if(itemdropmess == "false"){
					Colour6 ="<col=ff0000>"; 
					}
					else{
					Colour6 ="<col=00FF00>"; 
					}
				
				player.getPackets().sendIComponentText(72, 55, "Enable/Disable Option settings.");
				
				player.getPackets().sendIComponentText(72, 31,Colour1 + "Level up Messages: " + levelmess.replace("true", "Enabled").replace("false", "Disabled"));
				player.getPackets().sendIComponentText(72, 32,Colour2 + "Player List on login: " + Plists.replace("true", "Enabled").replace("false", "Disabled"));
				player.getPackets().sendIComponentText(72, 33,Colour3 +  "Login News: " + loginmess.replace("true", "Enabled").replace("false", "Disabled"));
				player.getPackets().sendIComponentText(72, 34,Colour4 +  "Donator Information: " + donarmess.replace("true", "Enabled").replace("false", "Disabled"));
				player.getPackets().sendIComponentText(72, 35,Colour5 +  "WGuild Tokken Message: " + wgmess.replace("true", "Enabled").replace("false", "Disabled"));
				player.getPackets().sendIComponentText(72, 36,Colour6 +  "Loot Messages: " + itemdropmess.replace("true", "Enabled").replace("false", "Disabled"));;
				player.getPackets().sendIComponentText(72, 37, "Empty");
				player.getPackets().sendIComponentText(72, 38, "Empty");
				player.getPackets().sendIComponentText(72, 39, "Empty");
				player.getPackets().sendIComponentText(72, 40, "Empty");
				player.getInterfaceManager().sendInterface(72);
			
			switch (componentId) {
				case 68: 
					player.levelmessage = !player.levelmessage;
					player.sm("Level Messages option has been Changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				
				case 67: 
					player.PList = !player.PList;
					player.sm("Player List on login option has been Changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				
				case 66: 
					player.loginmessages = !player.loginmessages;
					player.sm("Login News option has been Changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				
				case 65: 
					player.donorMess = !player.donorMess;
					player.sm("Donator Information option has been Changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				case 64: 
					player.wgmess = !player.wgmess;
					player.sm("The Warriors guild sending 'minus 10 tokkens' has changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				case 73: 
					player.itemdropmess = !player.itemdropmess;
					player.sm("Item Drop Messages has changed");
					Commands.processCommand(player, "enables",true, false);
					break;
				}
	
	
	
				} else if (interfaceId == 204) {
			if (player.unlocking == false){
				player.getPackets().sendIComponentText(204, 9, "Secure your Account with Ip Lock?");
				player.getPackets().sendIComponentText(204, 30, "Lock to");
				player.getPackets().sendIComponentText(204, 46, player.getSession().getIP());
				player.getPackets().sendIComponentText(204, 62, "?");
				player.getPackets().sendIComponentText(204, 22, "Confirm");
			switch (componentId) {
				case 18: 
					player.iplocked = true;
					player.lockedwith = player.getSession().getIP();
					player.sm("<col=000000>You have just locked your account to this ip: " + player.getSession().getIP());
					player.closeInterfaces();
					break;
				}
				}
		
			if(player.unlocking == true){
				String playerip = player.getSession().getIP();
				player.getPackets().sendIComponentText(204, 9, "Do you want to disable your IPLock");
				player.getPackets().sendIComponentText(204, 30, "Unlock:");
				player.getPackets().sendIComponentText(204, 46, player.lockedwith);
				player.getPackets().sendIComponentText(204, 62, "?");
				player.getPackets().sendIComponentText(204, 22, "Confirm");
				
                       switch (componentId) {
			
			case 18: 
			player.iplocked = false;
			player.lockedwith = null;
			player.sm("<col=000000>You have just unlocked your account from this ip: " + player.getSession().getIP());
			player.closeInterfaces();
			break;
			}
				} 
				}
				
				
				
				
                else if (interfaceId == 506) {
				
				Calendar cal = Calendar.getInstance();
				cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
                player.getPackets().sendIComponentText(506, 0,  "<col=ff00ff><Img=6>Micro Scape");
                player.getPackets().sendIComponentText(506, 2, "<col=FCFF00><Img=6>Prestige Rank:<col=ffffff>"+player.prestige+"");
                player.getPackets().sendIComponentText(506, 4, "<col=FCFF00><Img=6>Vote Point:<col=ffffff>" + player.VotePoints);
                 player.getPackets().sendIComponentText(506, 6, "<col=FCFF00><Img=6>Time: <col=ffffff>" +  String.valueOf(sdf.format(cal.getTime()))) ;
                player.getPackets().sendIComponentText(506, 8, "<col=FCFF00><Img=6>Home");
                player.getPackets().sendIComponentText(506, 10, "<col=FCFF00><Img=6>Vote");
                player.getPackets().sendIComponentText(506, 12, "<col=FCFF00><Img=6>Forum");
                player.getPackets().sendIComponentText(506, 14, "<col=FCFF00><Img=6>Donate");
                        switch (componentId) {
			
			case 6: // Staf list
			player.sm("<col=000000>Thats Poanizers current time (24hr): " +  String.valueOf(sdf.format(cal.getTime())));
			break;
			
			
			case 4: // vote
			player.sm("<col=000000>You have voted: " + player.timesVoted + " times.");
			player.setNextForceTalk(new ForceTalk("<col=ff0000> I have voted: " + player.timesVoted + " times."));
				
			break; 
			
			case 8: // Home // was youtube previously
			player.sm("<col=000000>Welcome home scrub."); //next line needs to tp person to home
			player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
			/*setNextWorldTile(new WorldTile(
				       Settings.RESPAWN_PLAYER_LOCATION));*/
			//player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.YOUTUBE_LINK);
			break;
			
                        case 10:  // vote
			player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.VOTE_LINK);
			break;
			case 12: // forum
			player.sm("<col=000000>If nothing happens go to www.MicroScape.com/forums."); //next line ??
			player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.FORUM);
			break;
                        case 14: // donor
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.DONATE_LINK);
            		player.sm("<col=000000>This is our donation shop. If nothing happens type ::Donate.");
			break;
			}
		} else if (interfaceId == 671) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bob_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 14) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
							.sendGameMessage(
									"Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 4)
				player.switchAllowChatEffects();
			else if (componentId == 5) {
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 6)
				player.switchMouseButtons();
			else if (componentId == 16) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
							.sendGameMessage(
									"Please close the interface you have open before setting your audio options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(743);
			}
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 42)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1
						: 0);
			else if (componentId >= 49 && componentId <= 61)
				player.setPrivateChatSetup(componentId - 48);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43
							&& player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 200: // Attack
				skillMenu = 1;
				if (player.getTemporaryAttributtes().remove("leveledUp[0]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 1);
				} else {
					lvlupSkill = 0;
					player.getPackets().sendConfig(1230, 10);
				}
				break;
			case 11: // Strength
				skillMenu = 2;
				if (player.getTemporaryAttributtes().remove("leveledUp[2]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 2);
				} else {
					lvlupSkill = 2;
					player.getPackets().sendConfig(1230, 20);
				}
				break;
			case 28: // Defence
				skillMenu = 5;
				if (player.getTemporaryAttributtes().remove("leveledUp[1]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 5);
				} else {
					lvlupSkill = 1;
					player.getPackets().sendConfig(1230, 40);
				}
				break;
			case 52: // Ranged
				skillMenu = 3;
				if (player.getTemporaryAttributtes().remove("leveledUp[4]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 3);
				} else {
					lvlupSkill = 4;
					player.getPackets().sendConfig(1230, 30);
				}
				break;
			case 76: // Prayer
				if (player.getTemporaryAttributtes().remove("leveledUp[5]") != Boolean.TRUE) {
					skillMenu = 7;
					player.getPackets().sendConfig(965, 7);
				} else {
					lvlupSkill = 5;
					player.getPackets().sendConfig(1230, 60);
				}
				break;
			case 93: // Magic
				if (player.getTemporaryAttributtes().remove("leveledUp[6]") != Boolean.TRUE) {
					skillMenu = 4;
					player.getPackets().sendConfig(965, 4);
				} else {
					lvlupSkill = 6;
					player.getPackets().sendConfig(1230, 33);
				}
				break;
			case 110: // Runecrafting
				if (player.getTemporaryAttributtes().remove("leveledUp[20]") != Boolean.TRUE) {
					skillMenu = 12;
					player.getPackets().sendConfig(965, 12);
				} else {
					lvlupSkill = 20;
					player.getPackets().sendConfig(1230, 100);
				}
				break;
			case 134: // Construction
				skillMenu = 22;
				if (player.getTemporaryAttributtes().remove("leveledUp[21]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 22);
				} else {
					lvlupSkill = 21;
					player.getPackets().sendConfig(1230, 698);
				}
				break;
			case 193: // Hitpoints
				skillMenu = 6;
				if (player.getTemporaryAttributtes().remove("leveledUp[3]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 6);
				} else {
					lvlupSkill = 3;
					player.getPackets().sendConfig(1230, 50);
				}
				break;
			case 19: // Agility
				skillMenu = 8;
				if (player.getTemporaryAttributtes().remove("leveledUp[16]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 8);
				} else {
					lvlupSkill = 16;
					player.getPackets().sendConfig(1230, 65);
				}
				break;
			case 36: // Herblore
				skillMenu = 9;
				if (player.getTemporaryAttributtes().remove("leveledUp[15]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 9);
				} else {
					lvlupSkill = 15;
					player.getPackets().sendConfig(1230, 75);
				}
				break;
			case 60: // Thieving
				skillMenu = 10;
				if (player.getTemporaryAttributtes().remove("leveledUp[17]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 10);
				} else {
					lvlupSkill = 17;
					player.getPackets().sendConfig(1230, 80);
				}
				break;
			case 84: // Crafting
				skillMenu = 11;
				if (player.getTemporaryAttributtes().remove("leveledUp[12]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 11);
				} else {
					lvlupSkill = 12;
					player.getPackets().sendConfig(1230, 90);
				}
				break;
			case 101: // Fletching
				skillMenu = 19;
				if (player.getTemporaryAttributtes().remove("leveledUp[9]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 19);
				} else {
					lvlupSkill = 9;
					player.getPackets().sendConfig(1230, 665);
				}
				break;
			case 118: // Slayer
				skillMenu = 20;
				if (player.getTemporaryAttributtes().remove("leveledUp[18]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 20);
				} else {
					lvlupSkill = 18;
					player.getPackets().sendConfig(1230, 673);
				}
				break;
			case 142: // Hunter
				skillMenu = 23;
				if (player.getTemporaryAttributtes().remove("leveledUp[22]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 23);
				} else {
					lvlupSkill = 22;
					player.getPackets().sendConfig(1230, 689);
				}
				break;
			case 186: // Mining
				skillMenu = 13;
				if (player.getTemporaryAttributtes().remove("leveledUp[14]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 13);
				} else {
					lvlupSkill = 14;
					player.getPackets().sendConfig(1230, 110);
				}
				break;
			case 179: // Smithing
				skillMenu = 14;
				if (player.getTemporaryAttributtes().remove("leveledUp[13]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 14);
				} else {
					lvlupSkill = 13;
					player.getPackets().sendConfig(1230, 115);
				}
				break;
			case 44: // Fishing
				skillMenu = 15;
				if (player.getTemporaryAttributtes().remove("leveledUp[10]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 15);
				} else {
					lvlupSkill = 10;
					player.getPackets().sendConfig(1230, 120);
				}
				break;
			case 68: // Cooking
				skillMenu = 16;
				if (player.getTemporaryAttributtes().remove("leveledUp[7]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 16);
				} else {
					lvlupSkill = 7;
					player.getPackets().sendConfig(1230, 641);
				}
				break;
			case 172: // Firemaking
				skillMenu = 17;
				if (player.getTemporaryAttributtes().remove("leveledUp[11]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 17);
				} else {
					lvlupSkill = 11;
					player.getPackets().sendConfig(1230, 649);
				}
				break;
			case 165: // Woodcutting
				skillMenu = 18;
				if (player.getTemporaryAttributtes().remove("leveledUp[8]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 18);
				} else {
					lvlupSkill = 8;
					player.getPackets().sendConfig(1230, 660);
				}
				break;
			case 126: // Farming
				skillMenu = 21;
				if (player.getTemporaryAttributtes().remove("leveledUp[19]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 21);
				} else {
					lvlupSkill = 19;
					player.getPackets().sendConfig(1230, 681);
				}
				break;
			case 150: // Summoning
				skillMenu = 24;
				if (player.getTemporaryAttributtes().remove("leveledUp[23]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 24);
				} else {
					lvlupSkill = 23;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			case 158: // Dung
				skillMenu = 25;
				if (player.getTemporaryAttributtes().remove("leveledUp[24]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 25);
				} else {
					lvlupSkill = 24;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			}

			player.getInterfaceManager().sendInterface(
					lvlupSkill != -1 ? 741 : 499);
			if (lvlupSkill != -1)
				LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getTemporaryAttributtes().put("skillMenu", skillMenu);
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getTemporaryAttributtes().get("skillMenu") != null)
				skillMenu = (Integer) player.getTemporaryAttributtes().get(
						"skillMenu");
			switch (componentId) {
			case 10:
				player.getPackets().sendConfig(965, skillMenu);
				break;
			case 11:
				player.getPackets().sendConfig(965, 1024 + skillMenu);
				break;
			case 12:
				player.getPackets().sendConfig(965, 2048 + skillMenu);
				break;
			case 13:
				player.getPackets().sendConfig(965, 3072 + skillMenu);
				break;
			case 14:
				player.getPackets().sendConfig(965, 4096 + skillMenu);
				break;
			case 15:
				player.getPackets().sendConfig(965, 5120 + skillMenu);
				break;
			case 16:
				player.getPackets().sendConfig(965, 6144 + skillMenu);
				break;
			case 17:
				player.getPackets().sendConfig(965, 7168 + skillMenu);
				break;
			case 18:
				player.getPackets().sendConfig(965, 8192 + skillMenu);
				break;
			case 19:
				player.getPackets().sendConfig(965, 9216 + skillMenu);
				break;
			case 20:
				player.getPackets().sendConfig(965, 10240 + skillMenu);
				break;
			case 21:
				player.getPackets().sendConfig(965, 11264 + skillMenu);
				break;
			case 22:
				player.getPackets().sendConfig(965, 12288 + skillMenu);
				break;
			case 23:
				player.getPackets().sendConfig(965, 13312 + skillMenu);
				break;
			case 29: // close inter
				player.stopAll();
				break;
			}
		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6)
				ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
			else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				int amuletId = player.getEquipment().getAmuletId();
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704 || amuletId == 10362)
						player.getPackets()
								.sendGameMessage(
										"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15)
				ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
			else if (componentId == 18)
				ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
			else if (componentId == 21)
				ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
			else if (componentId == 24)
				ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
			else if (componentId == 27)
				ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
			else if (componentId == 30)
				ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
			else if (componentId == 33)
				ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
			else if (componentId == 36)
				ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
			else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAuraManager().activate();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 40) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(17);
			} else if (componentId == 41) {
				player.getInterfaceManager().sendInterface(1178); // tool belt
			} else if (componentId == 37) {
				player.setInfiniteStopDelay();
				player.resetStopDelay();
				player.getInterfaceManager().sendInventoryInterface(670);
				player.getInterfaceManager().sendInterface(667);
				player.getPackets().sendItems(93,
						player.getInventory().getItems());
				player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93,
						4, 7, "Equip", "Compare", "Stats", "Examine");
				player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0,
						27, 0, 1, 2, 3);
				player.getPackets()
						.sendIComponentSettings(667, 14, 0, 13, 1030);
				refreshEquipBonuses(player);
			} else if (componentId == -1) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
							.sendGameMessage(
									"Please finish what you're doing before opening the price checker.");
					return;
				}
				player.stopAll();
				player.getPriceCheckManager().initPriceCheck();
			}
		} else if (interfaceId == 449) {
			if (componentId == 1) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				shop.sendInventory(player);
			} else if (componentId == 21) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				Integer slot = (Integer) player.getTemporaryAttributtes().get(
						"ShopSelectedSlot");
				if (slot == null)
					return;
				if (shop.id == 89){
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.buyDung(player, slot, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.buyDung(player, slot, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.buyDung(player, slot, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.buyDung(player, slot, 50);
				return;
				}
				if (shop.id == 76){
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.buyVote(player, slot, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.buyVote(player, slot, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.buyVote(player, slot, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.buyVote(player, slot, 50);
				return;
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.buy(player, slot, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.buy(player, slot, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.buy(player, slot, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.buy(player, slot, 50);

			}
		} else if (interfaceId == 620) {
			if (componentId == 25) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				if (shop.id == 89){
						if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
							shop.sendDungInfo(player, slotId);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
							shop.buyDung(player, slotId, 1);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
							shop.buyDung(player, slotId, 5);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
							shop.buyDung(player, slotId, 10);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
							shop.buyDung(player, slotId, 50);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
							shop.buyDung(player, slotId, 500);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
							shop.sendExamine(player, slotId);
						return;
					}
				if (shop.id == 76){

					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendVoteInfo(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.buyVote(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.buyVote(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.buyVote(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.buyVote(player, slotId, 50);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						shop.buyVote(player, slotId, 500);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						shop.sendExamine(player, slotId);
					return;
				}if (shop.id == 31){

					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendSlayInfo(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.buySlay(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.buySlay(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.buySlay(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.buySlay(player, slotId, 50);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						shop.buySlay(player, slotId, 500);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						shop.sendExamine(player, slotId);
					return;
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.sendInfo(player, slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.buy(player, slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.buy(player, slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.buy(player, slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					shop.buy(player, slotId, 50);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					shop.buy(player, slotId, 500);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					shop.sendExamine(player, slotId);
			}
		} else if (interfaceId == 621) {
			if (componentId == 0) {

				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getTemporaryAttributtes().get(
							"Shop");
					if (shop.id == 76 || shop.id == 89){
						player.sm("You cannot sell to this shop.");
						return;
					}
					if (shop == null)
						return;
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
			}
		} else if (interfaceId == 640) {
			if (componentId == 18 || componentId == 22) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", true);
				player.getPackets().sendConfig(283, 67108864);
			} else if (componentId == 19 || componentId == 21) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", false);
				player.getPackets().sendConfig(283, 134217728);
			} else if (componentId == 20) {
				DuelControler.challenge(player);
			}
		} else if (interfaceId == 650) {
			if (componentId == 17) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, 0));
				player.getControlerManager().startControler(
						"CorpBeastControler");
			} else if (componentId == 18)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;
				if (packetId == 3)
					player.getPackets().sendGameMessage(
							ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27
						|| player.getInterfaceManager()
								.containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != slotId2)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					InventoryOptionsHandler.handleItemOption1(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					InventoryOptionsHandler.handleItemOption2(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					InventoryOptionsHandler.handleItemOption3(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					InventoryOptionsHandler.handleItemOption4(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					InventoryOptionsHandler.handleItemOption5(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					InventoryOptionsHandler.handleItemOption6(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					InventoryOptionsHandler.handleItemOption7(player, slotId,
							slotId2, item);
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		} else if (interfaceId == 749) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 750) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an emote.");
						return;
					}
					if (player.getStopDelay() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setSkill(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			} else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			else if (componentId == 20)
				player.getBank().depositAllEquipment(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 35)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 44) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBank().openBank();
					}
				});
			} else if (componentId >= 44 && componentId <= 62) {
				int tabId = 9 - ((componentId - 44) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 93) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bank_isWithdraw",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			}
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBank().openBank();
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				if (player.hasInstantSpecial(player.getEquipment()
						.getWeaponId()))
					return;
				CoresManager.fastExecutor.schedule(new TimerTask() {
					@Override
					public void run() {
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								player.getCombatDefinitions()
										.switchUsingSpecialAttack();
							}
						}, 0);

					}
				}, 200);
			} else if (componentId >= 11 && componentId <= 14)
				player.getCombatDefinitions().setAttackStyle(componentId - 11);
			else if (componentId == 15)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 2);
		} else if (interfaceId == 20)
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		else if (interfaceId == 1056) {
			if (componentId == 102)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 25) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
			} else if (componentId == 25) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			}
		} else if (interfaceId == 631 || interfaceId == 628
				|| interfaceId == 626 || interfaceId == 637
				|| interfaceId == 639)
			player.getDuelConfigurations().processButtonClick(player,
					interfaceId, componentId, slotId, packetId);
		else if (interfaceId == 1163 || interfaceId == 1164
				|| interfaceId == 1168 || interfaceId == 1170
				|| interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
			PlayerLook.handleCharacterCustomizingButtons(player, componentId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId,
					componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces();
/*
	//drop item save. Potential dupe fix, may make you lagg if lots of people drop at once.
		if ((interfaceId == 679) && (packetId == 10)){
			if(Settings.SNOOP){
				Logger.log("Drops:", "Saved player - Potential Dupe");
			}
		SerializableFilesManager.savePlayer(player);
		}
*/
		if (Settings.DEBUG)
			Logger.log("ButtonHandler", "InterfaceId " + interfaceId
					+ ", componentId " + componentId + ", slotId " + slotId
					+ ", slotId2 " + slotId2 + ", PacketId: " + packetId);
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		Item item = player.getEquipment().getItem(slotId);
		if (item == null
				|| !player.getInventory().addItem(item.getId(),
						item.getAmount()))
			return;
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale()) && item.getDefinitions().id != 4084) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		
		
	
		
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to spawn " + itemName + ".");
				return true;
			}
		}
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return true;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments) {
						player.getPackets()
								.sendGameMessage(
										"You are not high enough level to use this item.");
					}
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return true;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(5).getId(),
						player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(3).getId(),
						player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(
						new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
		
		
		
		//END
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		//Kiln	
		if (itemId == 23659 && !player.isDonator()){
		player.getPackets().sendGameMessage(
						"You need to be a donator to equip this.");
				return false;
		}
		
		
		
		/*
		if (itemId == 24161 && player.getprestige() >= 5){
		player.getPackets().sendGameMessage(
						"You need to be atleast 5th Prestige to wear this.");
				return false;
		}
		
		if (itemId == 24162 && player.getprestige() >= 10){
		player.getPackets().sendGameMessage(
						"You need to be atleast 10th Prestige to wear this.");
				return false;
		}
		*/
		if (itemId == 24163 && player.getprestige() >= 15){
		player.getPackets().sendGameMessage(
						"You need to be atleast 15th Prestige to wear this.");
				return false;
		}
			
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale())) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		
		
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to equip " + itemName + ".");
				return false;
			}
		}
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets()
								.sendGameMessage(
										"You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
			} else
				player.getInventory()
						.getItems()
						.add(new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static void sendWear(Player player, int[] slotIds) {
		if (player.hasFinished() || player.isDead())
			return;

		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

	public static void refreshEquipBonuses(Player player) {
		player.getPackets().sendIComponentText(667, 31,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 32,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 33,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 34,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 35,
				"Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 36,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 37,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 38,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 39,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 40,
				"Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets()
				.sendIComponentText(
						667,
						41,
						"Summoning: +"
								+ player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets()
				.sendIComponentText(
						667,
						42,
						"Absorve Melee: "
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						43,
						"Absorve Magic: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						44,
						"Absorve Ranged: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
								+ "%");
		player.getPackets().sendIComponentText(667, 45,
				"Strength: " + player.getCombatDefinitions().getBonuses()[14]);
		player.getPackets()
				.sendIComponentText(
						667,
						46,
						"Ranged Str: "
								+ player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 47,
				"Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(
				667,
				48,
				"Magic Damage: +"
						+ player.getCombatDefinitions().getBonuses()[17] + "%");
	}
}
