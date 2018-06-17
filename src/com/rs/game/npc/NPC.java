package com.rs.game.npc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.rs.cache.Cache;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.combat.NPCCombat;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Slayer.SlayerMonsters;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.MapAreas;
import com.rs.utils.Misc;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class NPC extends Entity implements Serializable {

	private static final long serialVersionUID = -4794678936277614443L;

	private int id;
	private WorldTile respawnTile;
	private int mapAreaNameHash;
	private boolean canBeAttackFromOutOfArea;
	private boolean randomwalk;
	private int[] bonuses; // 0 stab, 1 slash, 2 crush,3 mage, 4 range, 5 stab
							// def, blahblah till 9
	private boolean spawned;
	private transient NPCCombat combat;
	private WorldTile forceWalk;

	private long lastAttackedByTarget;
	private boolean cantInteract;
	private int capDamage;
	private int lureDelay;
	private boolean cantFollowUnderCombat;
	private boolean forceAgressive;
	private int forceTargetDistance;
	private boolean forceFollowClose;
	private boolean forceMultiAttacked;

	// npc masks
	private transient Transformation nextTransformation;
	//name changing masks
	private String name;
	private transient boolean changedName;
	private int combatLevel;
	private transient boolean changedCombatLevel;

	public NPC(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea) {
		this(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
	}

	public boolean isFamiliar() {
		return this instanceof Familiar;
	}

	/*
	 * creates and adds npc
	 */
	public NPC(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(tile);
		this.id = id;
		this.respawnTile = new WorldTile(tile);
		this.mapAreaNameHash = mapAreaNameHash;
		this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
		this.spawned = spawned;
		combatLevel = -1;
		setHitpoints(getMaxHitpoints());
		setDirection(getRespawnDirection());
		setRandomWalk((getDefinitions().walkMask & 0x2) != 0
				|| forceRandomWalk(id));
		bonuses = NPCBonuses.getBonuses(id);
		combat = new NPCCombat(this);
		capDamage = -1;
		lureDelay = 12000;
		// npc is inited on creating instance
		initEntity();
		World.addNPC(this);
		World.updateEntityRegion(this);
		// npc is started on creating instance
		loadMapRegions();
		checkMultiArea();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || nextTransformation != null || changedCombatLevel || changedName;
	}

	public void transformIntoNPC(int id) {
		setNPC(id);
		nextTransformation = new Transformation(id);
	}

	public void setNPC(int id) {
		this.id = id;
		bonuses = NPCBonuses.getBonuses(id);
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		nextTransformation = null;
		changedCombatLevel = false;
		changedName = false;
	}

	public int getMapAreaNameHash() {
		return mapAreaNameHash;
	}

	public boolean canBeAttackFromOutOfArea() {
		return canBeAttackFromOutOfArea;
	}

	public NPCDefinitions getDefinitions() {
		return NPCDefinitions.getNPCDefinitions(id);
	}

	public NPCCombatDefinitions getCombatDefinitions() {
		return NPCCombatDefinitionsL.getNPCCombatDefinitions(id);
	}

	@Override
	public int getMaxHitpoints() {
		return getCombatDefinitions().getHitpoints();
	}

	public int getId() {
		return id;
	}

	public static void main(String[] args) throws IOException {
		Cache.init();
	}

	public void processNPC() {
		if (isDead())
			return;
		if (!combat.process()) { // if not under combat
			if (!isForceWalking()) {// combat still processed for attack delay
									// go down
				// random walk
				if (!cantInteract) {
					if (!checkAgressivity()) {
						if (getFreezeDelay() < Utils.currentTimeMillis()) {
							if (((hasRandomWalk()) && World.getRotation(
									getPlane(), getX(), getY()) == 0) // temporary
																		// fix
									&& Math.random() * 1000.0 < 100.0) {
								int moveX = (int) Math
										.round(Math.random() * 10.0 - 5.0);
								int moveY = (int) Math
										.round(Math.random() * 10.0 - 5.0);
								resetWalkSteps();
								if (getMapAreaNameHash() != -1) {
									if (!MapAreas.isAtArea(getMapAreaNameHash(), this)) {
										forceWalkRespawnTile();
										return;
									}
									addWalkSteps(getX() + moveX, getY() + moveY, 5);
								}else 
									addWalkSteps(respawnTile.getX() + moveX, respawnTile.getY() + moveY, 5);
							}
						}
					}
				}
			}
		}
		
		
			
			
			
							//Renamers
							if(id == 519)
									setName("Herb Store");
							if(id == 576)
									setName("Skilling Shop");
							if(id == 544)
   									setName("SkillCape Shop");
							if(id == 6537)
    								setName("Special Item Store");
							if(id == 524)
    								setName("Loyalty Shop");
							if(id == 551)
   									setName("Magic Shop");
							if(id == 530)
									setName("Lamp Exchange");
							if(id == 4555)
    								setName("F.O.G Shop");
							if(id == 15582)
    								setName("Prestige Master");
							if(id == 546)
   									setName("Magic Supplies");
							if(id == 530)
									setName("Lamp Exchange");
							if(id == 524)
									setName("Mage Gear");
							if(id == 400)
									setName("QBD Exchange");
							if(id == 346)
   									setName("Range Armour");
							if(id == 1694)
   									setName("Range Weapons");
							if(id == 211)
    								setName("Melee Armour");
							if(id == 6971)
    								setName("Summoning Supplies");
							if(id == 537)
   									setName("Pouch Shop");
							if(id == 2830)
									setName("Melee Weapons");
							if(id == 454)
    								setName("Food n Pots");	
							if(id == 5580)
                                    setName("Poanizer (Getting Started)");
							if(id == 11254)
									setName("Lewis");
							if(id == 1039)
									setName("Male Clothes Designer");
							if(id == 285)
									setName("Female Clothes Designer");
							if(id == 523)
									setName("Sell to Me");
							if(id == 3067)
									setName("Steel Kiwi");
							if(id == 6136)
									setName("Poanizer's first 'Custom' dialogue");		
							if(id == 219)
									setName("Fishing Supplies");
							if(id == 210)
									setName("Pure Accessories");
							if(id == 659)
									setName("Vote Shop");
							if(id == 3196)
									setName("Matey");
							if(id == 410)
									setName("Gambler");
							if(id == 2255)
									setName("Colin's Bed");
							if(id == 5027)
									setName("More Shops");
							if(id == 13295)
									setName("Training Master");
							if(id == 15032)
									setName("Dieng Soldier");
							if(id == 566)
									setName("Donator Shop");
							if(id == 692)
									setName("Super Donator Shop");
								
									
									
									
									
									
		if (isForceWalking()) {
			if (getFreezeDelay() < Utils.currentTimeMillis()) {
				setRandomWalk(false);                         
                             if(id == 519)
                                    setRandomWalk(false);
							if(id == 2342)
                                    setRandomWalk(false);
                             if(id == 550)
                                    setRandomWalk(false);
                             if(id == 546)
                                    setRandomWalk(false);
							if(id == 6136)
                                    setRandomWalk(true); //Notices Crier	
                             if(id == 549)
                                    setRandomWalk(false);
                             if(id == 683)
                                    setRandomWalk(false);
                             if(id == 2676)
                                    setRandomWalk(false);
                             if(id == 948)
                                    setRandomWalk(false);
	           if(id == 15581)
                                    setRandomWalk(true);
	           if(id == 2676)
                                    setRandomWalk(false);
                             if(id == 948)
                                    setRandomWalk(false);
                             if(id == 445)
                                    setRandomWalk(false);
                             if(id == 3299)
                                    setRandomWalk(false);
                             if(id == 2732)
                                    setRandomWalk(false);
                             if(id == 4906)
                                    setRandomWalk(false);
                             if(id == 3706)
                                    setRandomWalk(false);
//Home
                             if(id == 521)
                                    setRandomWalk(false);
                             if(id == 531)
                                    setRandomWalk(false);
                             if(id == 544)
                                    setRandomWalk(false);
                             if(id == 540)
                                    setRandomWalk(false);
                             if(id == 551)
                                    setRandomWalk(false);
                             if(id == 576)
                                    setRandomWalk(false);
                             if(id == 4555)
                                    setRandomWalk(false);
                             if(id == 598)
                                    setRandomWalk(false);
                             if(id == 2676)
                                    setRandomWalk(false);
							if(id == 7571)
                                    setRandomWalk(false);
							if(id == 14256)
                                    setRandomWalk(true);
							if(id == 546)
                                    setRandomWalk(false);
							if(id == 5867)
                                    setRandomWalk(false);
							
							if(id == 2025)
								setRandomWalk(false);
							if(id == 2026)
								setRandomWalk(false);
							if(id == 2027)
								setRandomWalk(false);		
							if(id == 2028)
								setRandomWalk(false);
							if(id == 2029)
								setRandomWalk(false);
							if(id == 2030)
								setRandomWalk(false);
							if(id == 8327)
								setRandomWalk(false);
									
									
				if (getX() != forceWalk.getX() || getY() != forceWalk.getY()) {
					if (!hasWalkSteps())
						addWalkSteps(forceWalk.getX(), forceWalk.getY(),
								getSize(), true);
					if (!hasWalkSteps()) { // failing finding route
						setNextWorldTile(new WorldTile(forceWalk)); // force
																	// tele
																	// to
																	// the
																	// forcewalk
																	// place
						forceWalk = null; // so ofc reached forcewalk place
					}
				} else
					// walked till forcewalk place
					forceWalk = null;
			}
		}
	}

	@Override
	public void processEntity() {
		super.processEntity();
		processNPC();
	}

	public int getRespawnDirection() {
		NPCDefinitions definitions = getDefinitions();
		if (definitions.anInt853 << 32 != 0 && definitions.respawnDirection > 0
				&& definitions.respawnDirection <= 8)
			return (4 + definitions.respawnDirection) << 11;
		return 0;
	}

	/*
	 * forces npc to random walk even if cache says no, used because of fake
	 * cache information
	 */
	private static boolean forceRandomWalk(int npcId) {
		switch (npcId) {
		case 11226:
			return true;
		default:
			return false;
			/*
			 * default: return NPCDefinitions.getNPCDefinitions(npcId).name
			 * .equals("Icy Bones");
			 */
		}
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (capDamage != -1 && hit.getDamage() > capDamage)
			hit.setDamage(capDamage);
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.getPrayer().hasPrayersOn()) {
				if (p2.getPrayer().usingPrayer(1, 18)) {
					final NPC target = this;
					if (hit.getDamage() > 0)
						World.sendProjectile(p2, this, 2263, 11, 11, 20, 5, 0,
								0);
					p2.heal(hit.getDamage() / 5);
					p2.getPrayer().drainPrayer(hit.getDamage() / 5);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							setNextGraphics(new Graphics(2264));
							if (hit.getDamage() > 0)
								World.sendProjectile(target, p2, 2263, 11, 11,
										20, 5, 0, 0);
						}
					}, 1);
				}
				if (hit.getDamage() == 0)
					return;
				if (!p2.getPrayer().isBoostedLeech()) {
					if (hit.getLook() == HitLook.MELEE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 19)) {
							p2.getPrayer().setBoostedLeech(true);
							return;
						} else if (p2.getPrayer().usingPrayer(1, 1)) { // sap
																		// att
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(0)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your sap curse has no effect.",
													true);
								} else {
									p2.getPrayer().increaseLeechBonus(0);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Attack from the enemy, boosting your Attack.",
													true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2214));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2215, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2216));
									}
								}, 1);
								return;
							}
						} else {
							if (p2.getPrayer().usingPrayer(1, 10)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(3)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.getPrayer().increaseLeechBonus(3);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Attack from the enemy, boosting your Attack.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2231, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2232));
										}
									}, 1);
									return;
								}
							}
							if (p2.getPrayer().usingPrayer(1, 14)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(7)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.getPrayer().increaseLeechBonus(7);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Strength from the enemy, boosting your Strength.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2248, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2250));
										}
									}, 1);
									return;
								}
							}

						}
					}
					if (hit.getLook() == HitLook.RANGE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 2)) { // sap range
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(1)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your sap curse has no effect.",
													true);
								} else {
									p2.getPrayer().increaseLeechBonus(1);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Range from the enemy, boosting your Range.",
													true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2217));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2218, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2219));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 11)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(4)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.getPrayer().increaseLeechBonus(4);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Range from the enemy, boosting your Range.",
													true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2236, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2238));
									}
								});
								return;
							}
						}
					}
					if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 3)) { // sap mage
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(2)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your sap curse has no effect.",
													true);
								} else {
									p2.getPrayer().increaseLeechBonus(2);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Magic from the enemy, boosting your Magic.",
													true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2220));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2221, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2222));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 12)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(5)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.getPrayer().increaseLeechBonus(5);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Magic from the enemy, boosting your Magic.",
													true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2240, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2242));
									}
								}, 1);
								return;
							}
						}
					}

					// overall

					if (p2.getPrayer().usingPrayer(1, 13)) { // leech defence
						if (Utils.getRandom(10) == 0) {
							if (p2.getPrayer().reachedMax(6)) {
								p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
							} else {
								p2.getPrayer().increaseLeechBonus(6);
								p2.getPackets()
										.sendGameMessage(
												"Your curse drains Defence from the enemy, boosting your Defence.",
												true);
							}
							p2.setNextAnimation(new Animation(12575));
							p2.getPrayer().setBoostedLeech(true);
							World.sendProjectile(p2, this, 2244, 35, 35, 20, 5,
									0, 0);
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									setNextGraphics(new Graphics(2246));
								}
							}, 1);
							return;
						}
					}
				}
			}
		}

	}

	@Override
	public void reset() {
		super.reset();
		setDirection(getRespawnDirection());
		combat.reset();
		bonuses = NPCBonuses.getBonuses(id); // back to real bonuses
		forceWalk = null;
	}

	@Override
	public void finish() {
		if (hasFinished())
			return;
		setFinished(true);
		World.updateEntityRegion(this);
		World.removeNPC(this);
	}

	public void setRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Error e) {
					e.printStackTrace();
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 600,
				TimeUnit.MILLISECONDS);
	}

	public void deserialize() {
		if (combat == null)
			combat = new NPCCombat(this);
		spawn();
	}

	public void spawn() {
		setFinished(false);
		World.addNPC(this);
		setLastRegionId(0);
		World.updateEntityRegion(this);
		loadMapRegions();
		checkMultiArea();
	}

	public NPCCombat getCombat() {
		return combat;
	}

	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		combat.removeTarget();
		setNextAnimation(null);
		
		WorldTasksManager.schedule(new WorldTask() {
		
			
			int loop;

			@Override
			public void run() {
			Player killer = getMostDamageReceivedSourcePlayer();
				if (loop == 0) {
					drop();
					setNextAnimation(new Animation(defs.getDeathEmote()));
				} else if (loop >= defs.getDeathDelay()) {
					reset();
			if(id == 17158){
		if (killer instanceof Player) {
			killer.Po2Kills += 1;
			}
				id = 17153;
			}
			if(id == 17154){
		if (killer instanceof Player) {
			killer.Po1Kills += 1;
				}
				id = 17153;
			}
		
					setLocation(respawnTile);
					finish();
					if (!spawned)
						setRespawnTask();
					stop();
				}
				loop++;
			}
		}, 0, 1);
		
		}
	
	public void drop() {
		try {
			Drop[] drops = NPCDrops.getDrops(id);
			if (drops == null)
				return;
			Player killer = getMostDamageReceivedSourcePlayer();
			if (killer == null)
				return;
				
		//elite knight for quest	
			if(id == 8327){
				killer.KilledEliteBK = true;
				killer.sendMessage("<col=ff0000> <shad=000>Quest Updated:</shad> Gear up!");
			
				}
		//elite knight for quest	
			if(id == 17176){
				if(killer.KilledJugg == false){
					killer.sendMessage("<col=ff0000> <shad=000>Quest Complete: </shad>The Jugg has Died!");
					killer.sendMessage("<col=ff0000> <shad=000>Quest Reward: </shad>You have unlocked a new area.");
					killer.KilledJugg = true;
				}
			}
		if (killer.slayerTask.getTaskMonstersLeft() > 0) {
			for (String m : killer.slayerTask.getTask().slayable) {
				if (getDefinitions().name.equals(m)) {
					killer.slayerTask.onMonsterDeath(killer, this);
					break;
				}
			}
		}
		
		if (killer.killcounting == true){
		killer.killcounter ++;
		killer.getPackets().sendGameMessage("<col=ff0000><shad=000> Kill Count: " + String.valueOf(killer.killcounter));
		}
		
		
		int [] charms = {12159, 12160, 12158};
		int li = Misc.random(3);
		int ratez = Misc.random(23,35);
		
		//charm collector
		 int ringId = killer.getEquipment().getRingId(); 
		 Drop dr = new Drop(charms[li],85,11,31,false);
  if ((ringId == 15015) || (ringId == 9104)) {
	killer.getInventory().addItem(charms[li],ratez);
  }
		
else{	
	sendDrop(killer,dr);
	}	
		
			Drop[] possibleDrops = new Drop[drops.length];
			int possibleDropsCount = 0;
			for (Drop drop : drops) {
				if (drop.getRate() == 100)
					sendDrop(killer, drop);
				else {
				
									//int ringId = killer.getEquipment().getRingId(); 
										if (ringId == 2572) {//wealth
										if ((Utils.getRandomDouble(99) + 1) <= drop.getRate() * 2.0){
											possibleDrops[possibleDropsCount++] = drop;
											}
										}
										else if(killer.getUsername().equalsIgnoreCase("wapen_van_nl")){
										
										if((Utils.getRandomDouble(99) + 1) <= drop.getRate() * 0.5 ){
											possibleDrops[possibleDropsCount++] = drop;
											}
										}
									
										else{
										if((Utils.getRandomDouble(99) + 1) <= drop.getRate() * 1.5){
											possibleDrops[possibleDropsCount++] = drop;
											}
										}
									
				}
			}
			if (possibleDropsCount > 0)
				sendDrop(killer,
						possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
	

	
	if (id == 116){
	//killer.getPackets().sendGameMessage( "Killed cyclops");
			
		int droppeddef = Utils.getRandom(100);
				  if (killer.getRights() == 12){
					killer.getPackets().sendGameMessage(String.valueOf(droppeddef));
						}
				 if(droppeddef <= 8){
				 //killer.getPackets().sendGameMessage( "Defender");
	//swap it D - B
													
					if(killer.getInventory().containsItem(20072, 1) || killer.getBank().containsItem(20072, 1)) {
								killer.getInventory().addItem(20072,1);
					
					}else  if(killer.getInventory().containsItem(8850, 1) || killer.getBank().containsItem(8850, 1)) {
								killer.getInventory().addItem(20072,1);
					
					}else  if(killer.getInventory().containsItem(8849, 1) || killer.getBank().containsItem(8849, 1)) {
								killer.getInventory().addItem(8850,1);
								
					}else  if(killer.getInventory().containsItem(8848, 1) || killer.getBank().containsItem(8848, 1)) {
								killer.getInventory().addItem(8849,1);
					
					}else  if(killer.getInventory().containsItem(8847, 1) || killer.getBank().containsItem(8847, 1)) {
								killer.getInventory().addItem(8848,1);
								
					}else  if(killer.getInventory().containsItem(8846, 1) || killer.getBank().containsItem(8846, 1)) {
								killer.getInventory().addItem(8847,1);
								 
								
					}else if(killer.getInventory().containsItem(8845, 1) || killer.getBank().containsItem(8845, 1)) {
								killer.getInventory().addItem(8846,1);
					
					}else if(killer.getInventory().containsItem(8844, 1) || killer.getBank().containsItem(8845, 1)) {
									killer.getInventory().addItem(8845,1);
					}else {
								killer.getInventory().addItem(8844,1);
								}
								
				}
			}
		
//WGuild
	if (id == 4278){//bronze
	killer.GuildTokens += 10;
	}if (id == 4279){//iron
	killer.GuildTokens += 15;
	}if (id == 4280){//steel
	killer.GuildTokens += 20;
	}if (id == 4281){//black
	killer.GuildTokens += 25;
	}if (id == 4282){//mith
	killer.GuildTokens += 30;
	}if (id == 4283){//addy
	killer.GuildTokens += 35;
	}if (id == 4284){//rune
	killer.GuildTokens += 60;
	}
//boss kills
	if (id == 3067){
	killer.KiwiKills += 1;
	}
	if (id == 6260){
	killer.BandosKills += 1;
	}
	if (id == 6203){
	killer.ZamyKills += 1;
	}
	if (id == 6247){
	killer.SaraKills += 1;
	}
	if (id == 6222){
	killer.ArmaKills += 1;
	}
	if (id == 8133){
	killer.CorpKills += 1;
	}
	if (id == 13450){
	killer.NexKills += 1;
	}
	if (id == 14301){
	killer.GlacorKills += 1;
	}
	if (id == 50){
	killer.KBDKills += 1;
	}
	if (id == 8349 || id == 8351 || id == 8353){
	killer.TDKills += 1;
	}
	if (id == 2025){
	killer.AhrimKills += 1;
	}
	if (id == 2026){
	killer.DHKills += 1;
	}
	if (id == 2027){
	killer.GuthanKills += 1;
	}
	if (id == 2028){
	killer.KarilKills += 1;
	}
	if (id == 2029){
	killer.ToragKills += 1;
	}
	//Copyright of Poanizer. Peepaw is a nigerian for sharing. and rhys is a noob. nuff. goml.
	if (id == 2030){
	killer.VeracKills += 1;
	}
	if (id == 14696){
	killer.GanoKills += 1;
	}
	if (id == 2881){
	killer.SupremeKills += 1;
	}
	if (id == 2882){
	killer.PrimeKills += 1;
	}
	if (id == 2883){
	killer.RexKills += 1;
	}
	if (id == 8596){
	killer.DestructionKills += 1;
	}
	if (id == 8528){
	killer.NomadKills += 1;
	}
	
	//revs
	if (id == 13470 || id == 13471 || id == 13472 || id == 13473 || id == 13474 || id == 13475 || id == 13476 || id == 13477 || id == 13478 || id == 13479 || id == 13480 || id == 13481){
	killer.RevKills += 1;
	}
	/*if (id == 17153){
	killer.Po1Kills += 1;
	}
	if (id == 17153){
	killer.Po2Kills += 1;
	}*/
//Boss end	
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void sendDrop(Player player, Drop drop) {
		int size = getSize();
		World.addGroundItem(new Item(drop.getItemId(), drop.getMinAmount()
				+ Utils.getRandom(drop.getExtraAmount())), new WorldTile(
				getCoordFaceX(size), getCoordFaceY(size), getPlane()), player,
				false, 180, true);
		
		int itemdroped = drop.getItemId();	
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(itemdroped);
	
		//shows item name and its drop rate
		if (player.getRights() == 12){
				if (player.showrate == false){
						player.getPackets().sendGameMessage(def.getName() + " " + String.valueOf(drop.getRate()));               
					
						 for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
				if(players.getRights() >= 12){
				players.getPackets().sendGameMessage("<shad=000><col=58FAF4>[Drops]: <shad=000><col=01DF01>"+ player.getUsername() + " just got a " + def.getName()+" " + String.valueOf(drop.getRate()) );
				}
						}		
						}
			}
		
		
			
		//shows a message for drops under 10% rate
		if (drop.getRate() <= 10.0){
		
			if (player.itemdropmess == true){	
				
				World.sendWorldWideMessage("<shad=000><col=58FAF4>[Drops]: <shad=000><col=01DF01>"+ player.getUsername() + " just got a " + def.getName() );
				}
		Player.dropLog(player, def.getName(), drop.getRate());
		}
	}

	@Override
	public int getSize() {
		return getDefinitions().size;
	}

	public int getMaxHit() {
		return getCombatDefinitions().getMaxHit();
	}

	public int[] getBonuses() {
		return bonuses;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0;
	}

	public WorldTile getRespawnTile() {
		return respawnTile;
	}

	public boolean isUnderCombat() {
		return combat.underCombat();
	}

	@Override
	public void setAttackedBy(Entity target) {
		super.setAttackedBy(target);
		if (target == combat.getTarget()
				&& !(combat.getTarget() instanceof Familiar))
			lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public boolean canBeAttackedByAutoRelatie() {
		return Utils.currentTimeMillis() - lastAttackedByTarget > lureDelay;
	}

	public boolean isForceWalking() {
		return forceWalk != null;
	}

	public void setTarget(Entity entity) {
		if (isForceWalking()) // if force walk not gonna get target
			return;
		combat.setTarget(entity);
		lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public void removeTarget() {
		if (combat.getTarget() == null)
			return;
		combat.removeTarget();
	}

	public void forceWalkRespawnTile() {
		setForceWalk(respawnTile);
	}

	public void setForceWalk(WorldTile tile) {
		resetWalkSteps();
		forceWalk = tile;
	}

	public boolean hasForceWalk() {
		return forceWalk != null;
	}

	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playerIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playerIndexes != null) {
				for (int npcIndex : playerIndexes) {
					Player player = World.getPlayers().get(npcIndex);
					if (player == null
							|| player.isDead()
							|| player.hasFinished()
							|| !player.isRunning()
							|| !player
									.withinDistance(
											this,
											forceTargetDistance > 0 ? forceTargetDistance
													: (getCombatDefinitions()
															.getAttackStyle() == NPCCombatDefinitions.MELEE ? 4
															: getCombatDefinitions()
																	.getAttackStyle() == NPCCombatDefinitions.SPECIAL ? 64
																	: 8))
							|| (!forceMultiAttacked
									&& (!isAtMultiArea() || !player
											.isAtMultiArea())
									&& player.getAttackedBy() != this && (player
									.getAttackedByDelay() > System
									.currentTimeMillis() || player
									.getFindTargetDelay() > System
									.currentTimeMillis()))
							|| !clipedProjectile(player, false)
							|| (!forceAgressive && !Wilderness.isAtWild(this) && player
									.getSkills().getCombatLevelWithSummoning() >= getDefinitions().combatLevel * 2))
						continue;
					possibleTarget.add(player);
				}
			}
		}
		return possibleTarget;
	}

	public boolean checkAgressivity() {
		// if(!(Wilderness.isAtWild(this) &&
		// getDefinitions().hasAttackOption())) {
		if (!forceAgressive) {
			NPCCombatDefinitions defs = getCombatDefinitions();
			if (defs.getAgressivenessType() == NPCCombatDefinitions.PASSIVE)
				return false;
		}
		// }
		ArrayList<Entity> possibleTarget = getPossibleTargets();
		if (!possibleTarget.isEmpty()) {
			Entity target = possibleTarget.get(Utils.getRandom(possibleTarget
					.size() - 1));
			setTarget(target);
			target.setAttackedBy(target);
			target.setFindTargetDelay(Utils.currentTimeMillis() + 10000);
			return true;
		}
		return false;
	}

	public boolean isCantInteract() {
		return cantInteract;
	}

	public void setCantInteract(boolean cantInteract) {
		this.cantInteract = cantInteract;
		if (cantInteract)
			combat.reset();
	}

	public int getCapDamage() {
		return capDamage;
	}

	public void setCapDamage(int capDamage) {
		this.capDamage = capDamage;
	}

	public int getLureDelay() {
		return lureDelay;
	}

	public void setLureDelay(int lureDelay) {
		this.lureDelay = lureDelay;
	}

	public boolean isCantFollowUnderCombat() {
		return cantFollowUnderCombat;
	}

	public void setCantFollowUnderCombat(boolean canFollowUnderCombat) {
		this.cantFollowUnderCombat = canFollowUnderCombat;
	}

	public Transformation getNextTransformation() {
		return nextTransformation;
	}

	@Override
	public String toString() {
		return getDefinitions().name + " - " + id + " - " + getX() + " "
				+ getY() + " " + getPlane();
	}

	public boolean isForceAgressive() {
		return forceAgressive;
	}

	public void setForceAgressive(boolean forceAgressive) {
		this.forceAgressive = forceAgressive;
	}

	public int getForceTargetDistance() {
		return forceTargetDistance;
	}

	public void setForceTargetDistance(int forceTargetDistance) {
		this.forceTargetDistance = forceTargetDistance;
	}

	public boolean isForceFollowClose() {
		return forceFollowClose;
	}

	public void setForceFollowClose(boolean forceFollowClose) {
		this.forceFollowClose = forceFollowClose;
	}

	public boolean isForceMultiAttacked() {
		return forceMultiAttacked;
	}

	public void setForceMultiAttacked(boolean forceMultiAttacked) {
		this.forceMultiAttacked = forceMultiAttacked;
	}

	public boolean hasRandomWalk() {
		return randomwalk;
	}

	public void setRandomWalk(boolean forceRandomWalk) {
		this.randomwalk = forceRandomWalk;
	}

	public String getCustomName() {
		return name;
	}
	
	public void setName(String string) {
		this.name = getDefinitions().name.equals(string) ? null : string;
		changedName = true;
	}
	
	public int getCustomCombatLevel() {
		return combatLevel;
	}
	
	public int getCombatLevel() {
		return combatLevel >= 0 ? combatLevel : getDefinitions().combatLevel;
	}
	
	public String getName() {
		return name != null ? name : getDefinitions().name;
	}
	
	public void setCombatLevel(int level) {
		combatLevel  = getDefinitions().combatLevel == level ? -1 : level;
		changedCombatLevel = true;
	}
	
	public boolean hasChangedName() {
		return changedName;
	}
	
	public boolean hasChangedCombatLevel() {
		return changedCombatLevel;
	}
	
	public WorldTile getMiddleWorldTile() {
		int size = getSize();
		return new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane());
	}
}
