package com.rs.game.player;

import java.io.Serializable;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DTRank;
import com.rs.utils.Utils;

public final class Boxing implements Serializable {

	public static final int CLIMBER = 0, ENDURANCE = 1, MAX_FACTOR = 10000000;
	private static final long serialVersionUID = -5230255619877910203L;

	private transient Player player;
	private transient int[] mapBaseCoords;

	private int nextBossIndex;
	private int progress;
	private int knockouts;
	private long totalScore;
	private boolean talkedWithFace;
	private int killedBossesCount;
	private int maxFloorEndurance;
	private int maxFloorClimber;
/*
	public void setPlayer(Player player) {
		this.player = player;
	}

	public Boxing() {
		nextBossIndex = -1;
	}

	public boolean hasRequiriments() {
		return player.getSkills().getCombatLevelWithSummoning() >= 110;
	}

	

	public void openModes() {
		if (progress == 10) {
			player.getDialogueManager()
					.startDialogue(
							"SimpleMessage",
							"You have beaten every champion",
							"Your the victor.");
			return;
		}
		player.getInterfaceManager().sendInterface(1008);
												//inter,compid, text,  (? means if that is true do first : means or do x)
		//player.getPackets().sendIComponentText( 1164, 27, progress == 0 ? "Ready for a new match" : "Floor progress: " + progress);
	}

	public void handleButtons(int interfaceId, int componentId) {
		if (interfaceId == 1008) {
			if (componentId == 29){
				startEnduranceMode();
				}
			else if (componentId == 29){
				openEnduranceMode();
				}
				
		}
		}
	

	private static final int[] MUSICS = { 1015, 1022, 1018, 1016, 1021 };

	public static final class Boss {

		private String name;
		private String text;
		private int[] ids;
		private boolean forceMulti;
		private Item item;

		public Boss(String name, String text, int... ids) {
			this(name, text, false, null, ids);
		}

		public Boss(String name, String text, boolean forceMulti, Item item,
				int... ids) {
			this.name = name;
			this.text = text;
			this.forceMulti = forceMulti;
			this.ids = ids;
			this.item = item;
		}

		public boolean isForceMulti() {
			return forceMulti;
		}

		public String getName() {
			return name;
		}
	}

	private static final Boss[] BOSSES = { 
			new Boss("Elvarg", "Grrrr",false,null, 14548)
			
			
			
			};

	private void startEnduranceMode() {
		if (progress == 10) {
			player.getDialogueManager()
					.startDialogue(
							"SimpleMessage",
							"You have beaten every champion",
							"Your the victor.");
			return;
		}
		createArena(1);
	}

	public void createArena(final int mode) {
		player.closeInterfaces();
		player.setInfiniteStopDelay();
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
						if(World.BoxInUse){
						player.getDialogueManager()
							.startDialogue(
							"SimpleMessage",
							"Somebodys already in the ring",
							"wait your turn.");
						}
					teleportToArena(1);
					
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Error e) {
					e.printStackTrace();
				}

			}
		});
		
		
		
	}

	private void teleportToArena(int mode) {
		player.setNextFaceWorldTile(new WorldTile(1887,5405, 0));
		player.getControlerManager().startControler("BoxingC", 1);//dtcontroler, mode
		player.resetStopDelay();
		player.getMusicsManager().playMusic(MUSICS[Utils.getRandom(MUSICS.length - 1)]);
	}

	public String getStartFightText(int message) {
		switch (message) {
		case 0:
			return "Goodluck!";
		case 1:
			return "You look pretty tough.";
		case 2:
			return "Swing like a Sling! Hit like a Maul!";
		default:
			return "Bring it on!";
		}
	}

	public void startFight(final NPC[] bosses) {
		for (NPC boss : bosses) {
			boss.setCantInteract(true);
			boss.setNextFaceWorldTile(new WorldTile(boss.getX() - 1, boss
					.getY(), 0));
		}
		player.setInfiniteStopDelay();
		WorldTasksManager.schedule(new WorldTask() {

			private int count;

			@Override
			public void run() {
				if (count == 0) {
					player.getInterfaceManager()
							.sendTab(
									player.getInterfaceManager()
											.hasRezizableScreen() ? 9 : 8, 1172);
					player.getPackets().sendHideIComponent(1172, 2, true);
					player.getPackets().sendHideIComponent(1172, 7, true);
					player.getPackets().sendIComponentText(1172, 4,
							player.getDisplayName());
					player.getPackets().sendConfig(1241, 1);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 25),
							Cutscene.getY(player, getBaseY() + 38), 1800);
					player.getPackets().sendCameraLook(
							Cutscene.getX(player, getBaseX() + 25),
							Cutscene.getY(player, getBaseY() + 29), 800);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 32),
							Cutscene.getY(player, getBaseY() + 38), 1800, 6, 6);
				} else if (count == 1) {
					player.setNextForceTalk(new ForceTalk(
							getStartFightText(Utils.getRandom(1))));
				} else if (count == 3) {
					player.getPackets().sendHideIComponent(1172, 2, false);
					player.getPackets().sendHideIComponent(1172, 5, true);
					player.getPackets().sendIComponentText(1172, 6,
							BOSSES[nextBossIndex].name);
					player.getPackets().sendConfig(1241, 0);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 37), 1800);
					player.getPackets().sendCameraLook(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 28), 800);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 42),
							Cutscene.getY(player, getBaseY() + 37), 1800, 6, 6);
				} else if (count == 4) {
					if (BOSSES[nextBossIndex].text != null)
						bosses[0].setNextForceTalk(new ForceTalk(
								BOSSES[nextBossIndex].text));
				} else if (count == 6) {
					player.getPackets()
							.closeInterface(
									player.getInterfaceManager()
											.hasRezizableScreen() ? 9 : 8);
					player.getInterfaceManager().sendInterface(1172);
					player.getPackets().sendHideIComponent(1172, 2, true);
					player.getPackets().sendHideIComponent(1172, 5, true);
					player.getPackets().sendIComponentText(1172, 8, "Fight!");
					player.getPackets().sendHideIComponent(1172, 10, true);
					player.getControlerManager().sendInterfaces();
					player.getPackets().sendCameraLook(
							Cutscene.getX(player, getBaseX() + 32),
							Cutscene.getY(player, getBaseY() + 36), 0);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 32),
							Cutscene.getY(player, getBaseY() + 16), 5000);
					player.getPackets().sendVoice(7882);
				} else if (count == 8) {
					if (BOSSES[nextBossIndex].item != null)
						World.addGroundItem(BOSSES[nextBossIndex].item,
								new WorldTile(getBaseX() + 26, getBaseY() + 33,
										2));
					player.closeInterfaces();
					player.getPackets().sendResetCamera();
					for (NPC boss : bosses) {
						boss.setCantInteract(false);
						boss.setTarget(player);
					}
					player.resetStopDelay();
					stop();
				}
				count++;
			}

		}, 0, 1);
	}

	public void removeItem() {
		if (BOSSES[nextBossIndex].item != null) {
			player.getInventory().deleteItem(
					BOSSES[nextBossIndex].item.getId(),
					BOSSES[nextBossIndex].item.getAmount());
			player.getEquipment().deleteItem(
					BOSSES[nextBossIndex].item.getId(),
					BOSSES[nextBossIndex].item.getAmount());
			player.getAppearence().generateAppearenceData();
		}
	}

	public void loss(final int mode) {
		removeItem();
		nextBossIndex = -1;
		player.setInfiniteStopDelay();
		//player.setNextFaceWorldTile(new WorldTile(player.getX() + 1, player .getY(), 0));

		WorldTasksManager.schedule(new WorldTask() {
			int count;

			@Override
			public void run() {
				if (count == 0) {
					player.setNextAnimation(new Animation(836));
					player.getPackets()
							.closeInterface(
									player.getInterfaceManager()
											.hasRezizableScreen() ? 10 : 8);
					player.getInterfaceManager().sendInterface(1172);
					player.getPackets().sendHideIComponent(1172, 2, true);
					player.getPackets().sendHideIComponent(1172, 5, true);
					player.getPackets().sendIComponentText(1172, 8,
							"Unlucky, you lost!");
					player.getPackets().sendIComponentText(
							1172,
							10,
							"You leave with a Knockout Count of: "
									+ knockouts);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 37), 2500);
					player.getPackets().sendCameraLook(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 28), 800);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 42),
							Cutscene.getY(player, getBaseY() + 37), 2500, 6, 6);
					player.getPackets().sendVoice(7874);
				} else if (count == 4) {
					player.setNextWorldTile(new WorldTile(1887,5399,0));
					player.setForceMultiArea(false);
					player.reset();
					player.setNextAnimation(new Animation(-1));
					player.closeInterfaces();
					player.getPackets().sendResetCamera();
					player.resetStopDelay();
					destroyArena(false, 1);
					stop();
				}
				count++;
			}
		}, 0, 1);
	}

	public void win(int mode) {
		removeItem();
		int knockedout = 1;
		progress++;
		
			if (progress > maxFloorEndurance)
				maxFloorEndurance = progress;
		

		killedBossesCount++;
		knockouts += knockedout;
		totalScore += knockedout;
		if (knockouts > MAX_FACTOR) {
			knockouts = MAX_FACTOR;
			player.getPackets() .sendGameMessage("You've reached the maximum knockouts!");
		}
		//DTRank.checkRank(player, mode, BOSSES[nextBossIndex].name);
		nextBossIndex = -1;
		player.setInfiniteStopDelay();
		player.setNextWorldTile(new WorldTile(getBaseX() + 35, getBaseY() + 31,2));//send to out of ring
		

		WorldTasksManager.schedule(new WorldTask() {

			private int count;

			@Override
			public void run() {
				if (count == 0) {
					player.getPackets()
							.closeInterface(
									player.getInterfaceManager()
											.hasRezizableScreen() ? 10 : 8);
					player.getInterfaceManager().sendInterface(1172);
					player.getPackets().sendHideIComponent(1172, 2, true);
					player.getPackets().sendHideIComponent(1172, 5, true);
					player.getPackets().sendIComponentText(1172, 8,
							"Yeah! ` won!");
					player.getPackets().sendIComponentText(
							1172,
							10,
							"You now have a Knockout Count of: "
									+ knockouts);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 37), 2500);
					player.getPackets().sendCameraLook(
							Cutscene.getX(player, getBaseX() + 35),
							Cutscene.getY(player, getBaseY() + 28), 800);
					player.getPackets().sendCameraPos(
							Cutscene.getX(player, getBaseX() + 42),
							Cutscene.getY(player, getBaseY() + 37), 2500, 6, 6);
				//	player.getPackets().sendVoice(7897);
				} else if (count == 4) {
					player.reset();
					player.closeInterfaces();
					player.getPackets().sendResetCamera();
					player.resetStopDelay();
					stop();
				}
				count++;
			}
		}, 0, 1);

	}

	/*
	 * 4928 15936
	 */
	/*
	 * 4960, 15968
	 */
/*
	public void destroyArena(final boolean logout, int mode) {
		WorldTile tile = new WorldTile(1887, 5400, 0);
		if (logout)
			player.setLocation(tile);
		else {
			player.getControlerManager().removeControlerWithoutCheck();
			player.setInfiniteStopDelay();
			player.setNextWorldTile(tile);
			
			
				progress = 0;
		}
	
	}

	public NPC[] createBosses() {
		NPC[] bosses = new NPC[BOSSES[nextBossIndex].ids.length];
		for (int i = 0; i < BOSSES[nextBossIndex].ids.length; i++)
			bosses[i] = World
					.spawnNPC(BOSSES[nextBossIndex].ids[i], new WorldTile(
							getBaseX() + 37 + (i * 2), getBaseY() + 31, 2), -1,
							true, true);
		return bosses;
	}


	public int getBaseX() {
		return mapBaseCoords[0] << 3;
	}

	public int getBaseY() {
		return mapBaseCoords[1] << 3;
	}

	public void selectBoss() {
		if (nextBossIndex < 0 || nextBossIndex >= BOSSES.length)
			nextBossIndex = Utils.getRandom(BOSSES.length - 1);
	}


	public void openEnduranceMode() {//edit 
		selectBoss();
		player.getInterfaceManager().sendScreenInterface(96, 1173);
		player.getPackets().sendIComponentText(1173, 20,
				BOSSES[nextBossIndex].name); // current
		player.getPackets().sendIComponentText(1173, 33,
				String.valueOf(progress + 1)); // current
		player.getPackets().sendIComponentText(1173, 47, "None. Good luck :o."); // current
		player.getPackets().sendIComponentText(1173, 24,
				String.valueOf(knockouts)); // current
		player.getPackets().sendIComponentText(
				1173,
				26,
				knockouts == MAX_FACTOR ? "" : String
						.valueOf(getBossesTotalLevel() * 10)); // on win
		player.getPackets().sendIComponentText(1173, 28,
				String.valueOf(knockouts)); // on death
	}

	public int getBossesTotalLevel() {
		int level = 0;
		for (int id : BOSSES[nextBossIndex].ids)
			level = +NPCDefinitions.getNPCDefinitions(id).combatLevel;
		return level;
	}



	public void openRewardsChest() {
		progress = 0;
		knockouts = 0;
		player.getInterfaceManager().sendInterface(1171);
	}

	public void openBankChest() {
		player.getBank().openBank();
	}




	public void setTalkedWithFace(boolean talkedWithFace) {
		this.talkedWithFace = talkedWithFace;
	}

	public int getProgress() {
		return progress;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public int getknockouts() {
		return knockouts;
	}

	public Boss getNextBoss() {
		return BOSSES[nextBossIndex];
	}

	public int getMaxFloorClimber() {
		return maxFloorClimber;
	}

	public int getMaxFloorEndurance() {
		return maxFloorEndurance;
	}

	public int getKilledBossesCount() {
		return killedBossesCount;
	}
*/
}
