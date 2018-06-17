package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;//<-- imports @@@@@ 
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class purpleportal extends Dialogue {

	@Override
	public void start() {
		sendDialogue(SEND_2_TEXT_CHAT, "The Poanizer Project's Portal",
				"I can take you to.", "Most of the bosses on The Poanizer Project.");
	}

	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue(SEND_5_OPTIONS, "Bossing", "Bandos.",
					"Zamorak.", "Armadyl.", "Saradomin", "More Options");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == 1)
				 teleportPlayer(2870, 5363, 2);
			else if (componentId == 2)
				teleportPlayer(2924, 5325, 2);
			else if (componentId == 3)
				teleportPlayer(2838, 5297, 2);
			else if (componentId == 4)
				teleportPlayer(2901, 5264, 0);
			else if (componentId == 5) {
				stage = 2;
				sendDialogue(SEND_5_OPTIONS, "Bossing",
						"Nex.", "Corporeal Beast.", "King Black Dragon.",
						"Tormented Demons", "More Options");
			}
		} else if (stage == 2) {
			if (componentId == 1) {
				teleportPlayers(2905, 5203, 0);
			} else if (componentId == 2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 4383, 0));
			else if (componentId == 3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2285, 4694, 0));
			else if (componentId == 4)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2562,5739, 0));
			else if (componentId == 5) {
				stage = 3;
				sendDialogue(SEND_5_OPTIONS, "PvP",
						"Easts.", "West(Green dragons). ", "Multi.",
						"Mage Bank", "More Options");
			}
		} else if (stage == 3) {
			if (componentId == 1){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360, 3658, 0));
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2984, 3596, 0));
				player.getControlerManager().startControler("Wilderness");
			} else if (componentId == 3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240, 3611, 0));
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2539,4712, 0));
			} else if (componentId == 5) {
				stage = 4;
				sendDialogue(SEND_5_OPTIONS, "PvM",
						"Barrows", "Brimhaven Dungeon", "Dragons", "Rock Crabs",
						"More Options");
			}
		} else if (stage == 4) {
			if (componentId == 1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3565, 3289, 0));
			} else if (componentId == 2){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2709, 9464, 0));
			}else if (componentId == 3){
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2892, 9784, 0));
			}else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2708, 3709, 0));
			} else if (componentId == 5) {
				stage = 5;
				sendDialogue(SEND_5_OPTIONS, "Fun Areas",
						"Duel Arena", "Clan Wars", "Safe Pvp (Home)", "Kalphite King(Team PvM)",
						"More Options");
			}
		} else if (stage == 5) {
			if (componentId == 1) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3365,3275, 0));
			player.getControlerManager().startControler("DuelControler");
			} else if (componentId == 2){
				teleportPlayer2(2994, 9679, 0);
		}	else if (componentId == 3){
				teleportPlayer2(3086, 3493, 0);
				player.getPackets().sendGameMessage("Stay on the main home strip, then type ::Startpvp"); 
			}else if (componentId == 4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3215, 2833, 0));
				 player.getPackets().sendGameMessage("<col=ff0000>Warning Kalphite King inbound good luck "+player.getDisplayName()); 
			} else if (componentId == 5) {
				stage = 6;
				sendDialogue(SEND_5_OPTIONS, "PvM",
						"Jadinko Lair", "Tzhar Volcano", "Chickens", "Polypore Dungeon",
						"More Options");
			}
		} else if (stage == 6) {
			if (componentId == 1){
				teleportPlayer2(3011, 9275, 0);
			}else if (componentId == 2){
				teleportPlayer2(2445, 5172, 0);
			}else if (componentId == 3){
				teleportPlayer2(2986, 9636, 0);
			}else if (componentId == 4){
				teleportPlayer2(3297, 9824, 0);
			}else if (componentId == 5) {
				stage = 7;
				sendDialogue((short) 238, new String[] {
						"PvM", "Bork (Wilderness).",
						"Kalphite Queen.", "Dagonoth Kings", "Revenants(wilderness)",
						"More Options" });
			}
		} else if (stage == 7) {
			if (componentId == 1){
				teleportPlayer2(3373, 3891, 0);
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 2){
			teleportPlayer2(3507, 9493, 0);
			}else if (componentId == 3){
			teleportPlayer2(2912, 4450, 0);
			}else if (componentId == 4){
				teleportPlayer2(3081, 10058, 0);
				player.getControlerManager().startControler("Wilderness");
			}else if (componentId == 5) {
				sendDialogue(SEND_5_OPTIONS, "Bossing", "Bandos.",
					"Zamorak.", "Armadyl.", "Saradomin", "More Options");
				stage = 1;
			}
		}
	}
	
		private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		//player.getControlerManager().startControler("GodWars");
	}
	private void teleportPlayers(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("GodWars");
	}
	private void teleportPlayer2(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}

	@Override
	public void finish() {

	}

}
