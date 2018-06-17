package com.rs.game.player;


import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.ClanWars;
import com.rs.game.minigames.CommenceWar;
import com.rs.game.minigames.PestControl;
import com.rs.game.minigames.War;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.jad.Harken;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.Slayer;
import com.rs.game.player.actions.Slayer.SlayerMonsters;
import com.rs.game.player.actions.summoning.SummonTrain;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.Notes;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.Trade;
import com.rs.game.player.controlers.CastleWarsPlaying;
import com.rs.game.player.controlers.CastleWarsWaiting;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.DTControler;
import com.rs.game.player.controlers.Duelarena;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.PestControler;
import com.rs.game.player.controlers.PitsControler;
import com.rs.game.player.controlers.TowersPkControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.ZGDControler;
import com.rs.game.player.starter.Starter;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.content.exchange.Offer;
import com.rs.game.player.content.slayer.SlayerTask;
import com.rs.game.player.CutscenesManager;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.Logger;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.utils.*;
import com.rs.game.player.content.slayer.SlayerTask;
import com.rs.io.SQL;

public class Player extends Entity {

	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1,
			RUN_MOVE_TYPE = 2;
public int farmob = -1;
public boolean Planted;

//Train Quest
public boolean TrainQuestStart;
public boolean Talkedtosoldier;
public boolean LootedMonk;
public boolean LootedSoldier;
public boolean KilledEliteBK;
public boolean KilledJugg;

//Kill Counter
public boolean killcounting;
public int killcounter;

//Ip Lock
public boolean iplocked; //If they are Ip locked or not.
public boolean unlocking;  //Checks if they are locking ip
public boolean disablelocking;//Will remove ip Lock
public String lockedwith;  //which ip Iplock is on to
public String waslockedwith; //What their ip was locked with.


//Enables
public boolean PList; //Shows player list on login.
public boolean donorMess;//Whether or not the donator messages will show
public boolean loginmessages;//Shows player login messages.
public boolean Logins;//Dunno
public boolean mutewarn; //warning for auto mute timer in worldPacketsDecoder.java
public boolean levelmessage; //Turns off the level messages when you level up.
public boolean ShowDrops; //Shows drop messages for you.

//Grain Minigame
public boolean depositgrain;
public int storedgrain = 0;
public int grain2flour = 0;

//Staff Pin
public boolean staffpindone = false;
public int staffpin = 0;

//Custom booleans
public int bossid;
public boolean Kicked;	//my kick test, failed hard.
public boolean hackattempt; //Antihack
public boolean GotVote;
public boolean hasmax;
public boolean itemdropmess;
public boolean showrate;
public boolean CompPvm;
public boolean CompDone;
public boolean infowarning;
public int timesVoted = 0;
public long Dchest = 0;
public long lastSpec = 0;
public boolean unlockMusic = false;
public boolean wgmess;//Admins see random number for defender.

public int SlayerPoints = 0;
public int GuildTokens = 0;
public int VotePoints = 0;
public int PvPPoints = 0;
public int DZPVPPoints = 0;
public int QuestPoints = 0;

//Boss kills

public int BandosKills = 0;
public int ZamyKills = 0;
public int SaraKills = 0;
public int ArmaKills = 0;

public int KiwiKills = 0;
public int CorpKills = 0;
public int NexKills = 0;
public int GlacorKills = 0;
public int KBDKills = 0;
public int TDKills = 0;

public int AhrimKills = 0;
public int DHKills = 0;
public int GuthanKills = 0;
public int KarilKills = 0;
public int ToragKills = 0;
public int VeracKills = 0;

public int GanoKills = 0;

public int SupremeKills = 0;
public int PrimeKills = 0;
public int RexKills = 0;

public int DestructionKills = 0;
public int NomadKills = 0;

public int RevKills = 0;

public int QBDKills = 0;
public int Po1Kills = 0;
public int Po2Kills = 0;
//Boss end


//Ge stuff.
public int geItem = 0;
public int price = 0;
public int geAmount = 0;
public int box = 0;

private static final long serialVersionUID = 2011932556974180375L;
public boolean isOnline;
public int currentSlot;
public boolean isSup;//Super donator. Never knew was here really.


public boolean Ahrim;
public boolean Guthan;
public boolean Kharil;
public boolean Torag;
public boolean Dharrock;
public boolean Verac;
public boolean Akrisea;


// transient stuff
private transient Trade trade;
private transient ClanWars clanWars;
private transient PestControl pestControl;
private transient String username;
private transient Session session;
private transient boolean clientLoadedMapRegion;
private transient int displayMode;
private transient int screenWidth;
private transient boolean usingTicket;
private transient int trapAmount;
private transient int screenHeight;
public boolean buying;
private transient InterfaceManager interfaceManager;
private transient DialogueManager dialogueManager;
private transient HintIconsManager hintIconsManager;
private transient ActionManager actionManager;
private transient CutscenesManager cutscenesManager;
private transient DuelConfigurations duelConfigurations;
private transient PriceCheckManager priceCheckManager;
private transient CoordsEvent coordsEvent;
private transient FriendChatsManager currentFriendChat;

// used for packets logic
private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;

// used for update
private transient LocalPlayerUpdate localPlayerUpdate;
private transient LocalNPCUpdate localNPCUpdate;

private int temporaryMovementType;
private boolean updateMovementType;

//This voting system isnt used.
public boolean checkVotes(String playerName) {               
		try {                        
		String urlString = "http://extinction-pk.com/vote.php?type=checkvote&username="+getDisplayName();                  
		urlString = urlString.replaceAll(" ", "%20");                        
		URL url = new URL(urlString);                        
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));                     
		String results = reader.readLine();                       
		if(results.length() > 0) {                              
		if(results.equals(""))                                 
		return true;                              
		else                                       
		return false;                       
		}                
		} catch (MalformedURLException e) {                      
		System.out.println("Malformed URL Exception in checkVotes(String playerName)");             
		} catch (IOException e) {                       
		System.out.println("IO Exception in checkVotes(String playerName)");           
		}                
		return false;      
		}

	// player stages
	private transient boolean started;
	private transient boolean running;

	private transient long packetsDecoderPing;
	private transient boolean resting;
	private transient boolean canPvp;
	private transient long stopDelay; // used for doors and stuff like that
	private transient long foodDelay;
	private transient long potDelay;
	private transient long boneDelay;
	private transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	private transient long polDelay;
	private transient Runnable interfaceListenerEvent;// used for static
	private transient List<Integer> switchItemCache;
	private transient boolean disableEquip;
	
	//dung, unused.
	public int toks = 0;
	public int dungtime = 0;
	public int dungdeaths = 0;
	
	
	// interface

	// saving stuff
	private String password;
	private int rights;
	private String displayName;
	private String lastIP;
	private Appearence appearence;
	private int Qp;
	public int prestige;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private CombatDefinitions combatDefinitions;
	private Prayer prayer;
	private Bank bank;
	private ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private DominionTower dominionTower;
	private Kilner kiln;
	private Familiar familiar;
	private AuraManager auraManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean mouseButtons;
	private int privateChatSetup;
	private int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	private long poisonImmune;
	private long fireImmune;
	private int lastVeng;
	private boolean castedVeng;
	private int[] pouches;
	private long displayTime;
	private long muted;
	private long jailed;
	private long caged;
	private long banned;
	private boolean permBanned;
	private boolean filterGame;

	public boolean donator;
	private long donatorTill;
	
	//Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;

	// honor
	private int killCount, deathCount;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	private int pestPoints;

	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;

	private int overloadDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;
        public SlayerTask slayerTask;
		public void sendMessage(String string) {
		getPackets().sendGameMessage(string);
	}
	public void init(Session session, String string) {
		//isinLobby = true;
		
		
		//if (dominionTower == null)
			//dominionTower = new DominionTower();
		username = string;
		this.session = session;
		//packetsDecoderPing = System.currentTimeMillis();
		//World.addPlayer(this);
		//World.updateEntityRegion(this);
		if (Settings.DEBUG)
			Logger.log(this, new StringBuilder("Inited Player: ").append
					(string).append
					(", pass: ").append
					(password).toString());
	}
	


	public void sm(String Msg) {
		getPackets().sendGameMessage(Msg);
	}
	/*public void VoteCheck() {
		if(SQL.checkVotes(this.getUsername())){
			if(SQL.voteGiven(this.getUsername()) || this.GotVote == true)
				{
					this.GotVote = true;
					this.getDialogueManager().startDialogue("Vote");
				}
		} else {
				getPackets().sendGameMessage("You have not voted please use the command ::govote to vote!");
				}
		}*/
	public Player(String password) {
		super(Settings.START_PLAYER_LOCATION);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		if (slayerTask == null)
			slayerTask = new SlayerTask();
		this.password = password;
		appearence = new Appearence();
		inventory = new Inventory();
		equipment = new Equipment();
		skills = new Skills();
		combatDefinitions = new CombatDefinitions();
		prayer = new Prayer();
		bank = new Bank();
		controlerManager = new ControlerManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		dominionTower = new DominionTower();
		//kiln = new Kilner();
		charges = new ChargesManager();
		auraManager = new AuraManager();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		pouches = new int[4];
		killedBarrowBrothers = new boolean[6];
		SkillCapeCustomizer.resetSkillCapes(this);
		ownedObjectsManagerKeys = new LinkedList<String>();
	}

	public void init(Session session, String username, int displayMode,
			int screenWidth, int screenHeight) {
		// temporary deleted after reset all chars
		if (dominionTower == null)
			dominionTower = new DominionTower();

	//	if (kiln == null)
	//			kiln = new Kilner();
		if (slayerTask == null)
			slayerTask = new SlayerTask();
		if (auraManager == null)
			auraManager = new AuraManager();
		this.session = session;
		this.username = username;
		this.displayMode = displayMode;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
	
		
		// loads player on saved instances
		appearence.setPlayer(this);
		inventory.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		combatDefinitions.setPlayer(this);
		prayer.setPlayer(this);
		bank.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
	//	kiln.setPlayer(this);
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		setDirection(Utils.getFaceDirection(0, -1));
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		switchItemCache = Collections
				.synchronizedList(new ArrayList<Integer>());
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		// inited so lets add it
		World.addPlayer(this);
		World.updateEntityRegion(this);
		if (Settings.DEBUG)
			Logger.log(this, "Player Logged in: " + username + ", pass: "
					+ password);
					
					
	
		
					
		//Owner			
		if (username.equalsIgnoreCase("joe"))
		{
            rights = 12;
			}
			
		if (username.equalsIgnoreCase(""))
		{
            rights = 13;
			}
			
			
		if (username.equalsIgnoreCase("mike"))
		{
            rights = 12;
			}
			
			
		//Developer
		if (username.equalsIgnoreCase(""))
		{
            rights = 11;
			}
			
		//Admin	
		if (username.equalsIgnoreCase(""))
		{
            rights = 10;
			}
			
		//Hidden	
		if (username.equalsIgnoreCase("") || username.equalsIgnoreCase(""))
		{
            rights = 9;
			}
			
		//Global Mod	
		if (username.equalsIgnoreCase("")|| username.equalsIgnoreCase(""))
		{
            rights = 8;
			}
			
		//Mod	
		if (username.equalsIgnoreCase("") || username.equalsIgnoreCase("") || username.equalsIgnoreCase(""))
		{
            rights = 7;
			}
			
		//Support	
		if (username.equalsIgnoreCase("") || username.equalsIgnoreCase(""))
		{
            rights = 6;
			}
			
			
		//Forum	
		if (username.equalsIgnoreCase(""))
		{
            rights = 5;
			}
			
			
		//Graphic	
		if (username.equalsIgnoreCase(""))
		{
            rights = 4;
			}
			
		//Respected	
		if (username.equalsIgnoreCase(""))
		{
            rights = 3;
			}
			
		//Super	
		if (username.equalsIgnoreCase(""))
		{
            rights = 2;
			}
			
			
		//Donator	
		if (username.equalsIgnoreCase(""))
		{
            rights = 1;
			}
			
			
			
	//staff pin
		staffpindone = false;
			
          }			

	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearence.generateAppearenceData();
	}
	
	

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	private boolean inClops;

	private int wGuildTokens;
	public int farmtim;
	private SummonTrain summontrain;
	public boolean softreset;
	private int hstime;

        public int getWGuildTokens() {
		return wGuildTokens;
	}

	public void setWGuildTokens(int tokens) {
		wGuildTokens = tokens;
	}

	public boolean inClopsRoom() {
		return inClops;
	}

	public void setInClopsRoom(boolean in) {
		inClops = in;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	} 

	public void refreshSpawnedItems() {
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendRemoveGroundItem(item);
			}
		}
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendGroundItem(item);
			}
		}
	}

	public void refreshSpawnedObjects() {
		for (int regionId : getMapRegionsIds()) {
			List<WorldObject> spawnedObjects = World.getRegion(regionId)
					.getSpawnedObjects();
			if (spawnedObjects != null) {
				for (WorldObject object : spawnedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendSpawnedObject(object);
			}
			List<WorldObject> removedObjects = World.getRegion(regionId)
					.getRemovedObjects();
			if (removedObjects != null) {
				for (WorldObject object : removedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendDestroyObject(object);
			}
		}
	}

		public static void chatLog(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/chatlog.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    //+ " "
                   // + Calendar.getInstance().getTimeZone().getDisplayName()
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " : "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
		public static void dropLog(Player player, String itemnamez, double dropratez) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/droplog.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + "  got: "+ itemnamez + "" + String.valueOf(dropratez));
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void tradelog(Player partner, Player trader, int tvalue, int pvalue) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/tradelog.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(partner.getUsername() + " traded (" + pvalue + ") : " +trader.getUsername()+ "(" + tvalue + ") : " ));
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }public static void chatLogip(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/chatlogip.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" IP: " + player.getSession().getIP() + " : "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void antihack(Player player, String cmd) {
		
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/Antihack.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                  + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " Ip: " + player.getSession().getIP() + ": Antihacked: " + cmd);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	
	
	public static void keyWordLog(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/keywords.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	/*public static void commandLog(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/commandlog.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": "+message + getSession().getIP(););
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }*/
	
	/*public static void rankloginlog(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/commandlog.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": " + getIP());
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }*/
	
	
	
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}

	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces) {
		if (getTrade() != null) {
			return;
		}
		coordsEvent = null;
		if (stopInterfaces && getTradeSession() == null)
			closeInterfaces();
		if (stopWalk)
			resetWalkSteps();
		actionManager.forceStop();
		combatDefinitions.resetSpells(false);
	}
	@Override
	public void reset() {
		super.reset();
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		resting = false;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		lastVeng = 0;
		castedVeng = false;
		dungeon = null;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}

	public void closeInterfaces() {
		if (getTrade() != null) {
			return;
		}
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
	}

	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (!started) {
			if (isAtDynamicRegion()) {
				getPackets().sendMapRegion(!started);
				forceNextMapLoadRefresh = true;
			}
		}
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(wasAtDynamicRegion);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}

	@Override//this is the tick
	public void processEntity() {
	//	isAtQBD(this.getLocation());
		if (hstime > 0)
			hstime--;
		if (hstime == 0){
		if (this.getRights() <= 1){
			//Highscores.saveHighScore(this);
			hstime += 45;
		}
		}
		processLogicPackets();
		cutscenesManager.process();
		super.processEntity();
		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearence.generateAppearenceData();
		}

		if (farmtim == 1){
		sm("You have 1 seconds left before you can pick your plant!");
		}
		if (farmtim == 1){
		sm("You have 1 seconds left before you can pick your plant!");
		}
		if (farmtim == 1){
		sm("You have 1 seconds left before you can pick your plant!");
		}
		if (farmtim == 245){
		sm("You have 245 seconds left before you can pick your plant!");
		}
		if (farmtim == 225){
		sm("You have 225 seconds left before you can pick your plant!");
		}
		if (farmtim == 205){
		sm("You have 205 seconds left before you can pick your plant!");
		}
		if (farmtim == 175){
		sm("You have 175 seconds left before you can pick your plant!");
		}
		if (farmtim == 135){
		sm("You have 135 seconds left before you can pick your plant!");
		}
		if (farmtim == 105){
		sm("You have 105 seconds left before you can pick your plant!");
		}
		if (farmtim == 91){
		sm("You have 90 seconds left before you can pick your plant!");
		}
		if (farmtim == 75){
		sm("You have 75 seconds left before you can pick your plant!");
		}
		if (farmtim == 61){
		sm("You have 60 seconds left before you can pick your plant!");
		}
		if (farmtim == 45){
		sm("You have 45 seconds left before you can pick your plant!");
		}
		if (farmtim == 30){
		sm("You have 30 seconds left before you can pick your plant!");
		}
		if (farmtim == 15){
		sm("You have 15 seconds untill your plant is ready to be picked!");
		}
		if (farmtim == 1){
		sm("Your plant is now ready!");
		}
		if (farmtim > 0){
			farmtim--;
		}
		if (polDelay == 1)
			getPackets()
					.sendGameMessage(
							"The power of the light fades. Your resistance to melee attacks return to normal.");
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
			overloadDelay--;
		}
		if (lastVeng > 0) {
			lastVeng--;
			if (lastVeng == 0 && castedVeng) {
				castedVeng = false;
				getPackets().sendGameMessage("Your vengeance has faded.");
			}
		}
		if (dungtime >= 1){
		dungtime--;
		}
		charges.process();
		auraManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		actionManager.process();
		prayer.processPrayer();
		controlerManager.process();

	}

	@Override
	public void processReceivedHits() {
		if (stopDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != 0
				|| updateMovementType;
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = 0;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (resting && runEnergy < 100)
				runEnergy++;
			getPackets().sendRunEnergy();
		}
		

		
	}

	// lets leave welcome screen and start playing
	public void run() {
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		
		
		
		
		lastIP = getSession().getIP();
		interfaceManager.sendInterfaces();
		getPackets().sendRunEnergy();
		refreshAllowChatEffects();
		refreshMouseButtons();
		refreshPrivateChatSetup();
		sendRunButtonConfig();
		appendStarter();
		getEmotesManager().unlockAllEmotes();
		levelmessage = true;
//Staffpin Disabled
	//This will make it so you dont need to enter a staffpin.
		staffpindone = true;
		//Delete it to re enable the staffpin security. Staffpin by default is 0.
		//When a Mod+ uses a command he must do ::staffpin 0, to unlock the mod+ commands.
		
//Login Messages	

			
	if (loginmessages == true){//if they are off this wont show.
		getPackets()
		.sendGameMessage("<col=0101DF>----------------------------------------------------------------------.");
		
		
		getPackets()
		.sendGameMessage("<col=FF0000><shad=000000>Welcome to The Poanizer Project.");
		
		getPackets()
		.sendGameMessage("<col=FFFF00><shad=000000>Talk to Poanizer at ::home to get started setting up your RSPS");
		
		getPackets()
		.sendGameMessage("<col=FFFF00><shad=000000>Remember to also read the 'Information/Notes' in Extras.");
			
		getPackets()
		.sendGameMessage("<col=FFFF00><shad=000000>And remember to change this message in Player.java........");
		
		getPackets()
		.sendGameMessage("<col=0101DF>-------------------------------------------------------------------.");
		
		getPackets()
		.sendGameMessage("<col=FFf000><shad=000000>There are currently <col=FF0000><shad=000000>" + (World.getPlayers().size() - 1) + "<col=FFf000><shad=000000> Players Online.");
		}
		else{
		getPackets()
		.sendGameMessage("<col=FF0000><shad=000000>Type ::enables to see your settings.");
		
		}
		
	//Use this to give someone a message on login. They can do ::ok ::notok etc to re enable.
	if(infowarning == true){	
		if ((username.equalsIgnoreCase("")) ||(username.equalsIgnoreCase(""))){
			getPackets().sendIComponentText(21, 1, "");
				getInterfaceManager().sendInterface(21);	
				
				}
		}
			
		
		
		
		
//P List		
		//shows players online as a login message
		if(PList == true){
			getPackets() .sendGameMessage("<col=0101DF>-----------------------------------");
		  for(Player p : World.getPlayers()) {
                   if( p.getUsername() == getUsername()){
				   continue;
				   }
                  getPackets().sendGameMessage("<col=ff0000>" + p.getUsername());
				}
		 getPackets().sendGameMessage("<col=0000ff> To disable type ::plistoff");
		}
		
		
		
		getPackets() .sendGameMessage("<col=0101DF>-----------------------------------");
		
		
//Snoop settings.
		if (Settings.SNOOP){
			Logger.log(this, "Player Login: " + username + ", pass: " + password + ", Rights " + getRights() + ", IP: " + getSession().getIP());
					}
		
		if (Settings.LOGIN == true){
			for (Player p : World.getPlayers()) {
				p.getPackets().sendGameMessage("Player " + getUsername() + " has logged in. :" +getRights());
			}
	   }
		


		
//IpLock Message if someone tried.
		if (hackattempt == true){
		getPackets().sendGameMessage("<shad=000><col=F7FE2E>[</col><col=FF0000>#WARNING</col><col=F7FE2E>]:</col> <shad=000><col=01DF01> Someone has tried to login to your account on a different IP. Contact Staff ASAP");
		hackattempt = false;
		
		}

		
		
//Anithack		
		if (getRights() >= 12) {
			if (Settings.hackmess == true){
				  getPackets().sendGameMessage("<col=ff0000> The Antihack has been activated!");
				  getPackets().sendGameMessage("<col=ff0000> The Antihack has been activated!");
				  getPackets().sendGameMessage("<col=ff0000> The Antihack has been activated!");
			}
	   }
	

	
//Rank login messages
	else if (getRights() == 7) 
       for (Player p : World.getPlayers()) {
       p.getPackets().sendGameMessage("<img=0><col=FF8000>Moderator: " + getUsername() + " has logged in.");
	}
	
	else if (getRights() == 6) 
       for (Player p : World.getPlayers()) {
       p.getPackets().sendGameMessage("<img=9><col=00449c>Player Support: " + getUsername() + " has logged in.");
	}
	
	else if (getRights() == 5) 
       for (Player p : World.getPlayers()) {
       p.getPackets().sendGameMessage("<img=8><col=FFF00>Forum Moderator: " + getUsername() + " has logged in.");
	}

	if (username.equalsIgnoreCase("steel_kiwi"))
       for (Player p : World.getPlayers()) {
       p.getPackets().sendGameMessage("<col=2e2efe>Legend: Steel Kiwi has logged in.");
	}

//Login log
	try {
	File file = new File("data/logs/Login.txt");
	BufferedWriter writer = new BufferedWriter(new FileWriter(
			file, true));
	writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] "+ getUsername()  + " Ip: " + getSession().getIP() + ": Rights: " + getRights());
	writer.newLine();
	writer.flush();
 } catch (IOException ignored) {
		}

			
//ipmute
		if(IPMute.isMuted(getSession().getIP())){
		setMuted(Utils.currentTimeMillis()+ (8760 * 60 * 60 * 1000));
		}
		//For ip X to work, you must copy the ipbanL file.
		//Then add the init() on launcher so it sets the correct file as "iplist" otherwise it will null.
		//then you need to use save() to make a save of the file.
		
		

	
//Honesly i think this is pointless since does anyone sell month donator?
		if(donorMess == true){
		
		}
		else{
		if (donator || donatorTill != 0) {
		if (!donator && donatorTill < Utils.currentTimeMillis())
			getPackets().sendGameMessage("Your donator rank expired.");
		else
			getPackets().sendGameMessage(
					"Your donator rank expires " + getDonatorTill());
		}}
		
		
		
//Owner Iplock 98987	
	
	if (username.equalsIgnoreCase("")){//if not my ip kicks acc	
		if(getSession().getIP().equals("") || getSession().getIP().equals("")){
			World.sendWorldWideMessage("<shad=000><col=F7FE2E>[</col><col=FF0000>#LEGIT</col><col=F7FE2E>]:</col> <shad=000><col=01DF01> Owner and Coder Poanizer logged in");					
		}
	else{
		setRights(0); 
		World.sendWorldWideMessage("<shad=000><col=F7FE2E>[</col><col=FF0000>#LEGIT</col><col=F7FE2E>]:</col> <shad=000><col=01DF01> This wasn't Poanizers normal IP, he was kicked. ");
		World.removePlayer(this);
		getSession().getChannel().disconnect();
		Player Remove = World.getPlayerByDisplayName(getUsername());
		World.removePlayer(Remove);
		//some un necessary removes but YOLO
	}}
		
		
		
//Iplock Normal.
	if(Settings.removeiplock == false){
		if(lockedwith == null){
		
	}else{
		if((getSession().getIP().equals(lockedwith))){
			
			}
			else{
			//Put your Owner Ip in here to make you able to log into locked accounts.
			 if (getSession().getIP().equals("")){
			 getPackets().sendGameMessage("<[Legit] Poanizer logged into " + getUsername());
			
			 }
			 else{
				getPackets().sendGameMessage("<shad=000><col=F7FE2E>[</col><col=FF0000>#LEGIT</col><col=F7FE2E>]:</col> <shad=000><col=01DF01> This isnt your locked IP.");
				hackattempt = true;
				Player Remove = World.getPlayerByDisplayName(getUsername());
				World.removePlayer(Remove);
				getSession().getChannel().disconnect();
					}
				}
		}
	}	
		
		
		
		
		
		
//comp check Unfinished. 
		 if(
		 (BandosKills == 100 ) &&
		 (ZamyKills == 100 ) && 
		 (SaraKills == 100 ) && 
		 (ArmaKills == 100 ) && 
		 
		 (KiwiKills == 5 ) && 
		 
		 (CorpKills == 80 ) && 
		 (NexKills  == 25 ) && 
		 
		 (GlacorKills == 40 ) &&
		 (KBDKills == 100 ) &&
		 (TDKills == 50 ) && 
		 
		 (AhrimKills == 100 ) && 
		 (DHKills == 100 ) && 
		 (GuthanKills == 100 ) && 
		 (KarilKills == 100 ) && 
		 (ToragKills == 100 ) && 
		 (VeracKills == 100 ) &&
		 
		 (GanoKills == 100 ) &&
		 
		 (SupremeKills == 30 ) &&
		 (PrimeKills == 30 ) &&
		 (RexKills  == 30 ) && 
		 
		 (DestructionKills == 20 ) && 
		 (NomadKills == 50 ) &&
		 
		 (RevKills == 100 )  && 
		 
		 (QBDKills == 40 ) && 
		 
		 (Po1Kills == 10 ) 
		 && (Po2Kills== 5 )){
		 
		  getPackets().sendGameMessage( "You have completed the PVM requirements for Comp Cape.");
			CompPvm = true;
		
		}
		else{
		CompPvm = false;
		}


//END	
		
		
		sendDefaultPlayersOptions();
		checkMultiArea();
		inventory.init();
		equipment.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		friendsIgnores.init();
		Notes.sendUnlockNotes(this);
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		for (int i = 0; i < 150; i++) {
			getPackets().sendIComponentSettings(590, i, 0, 190, 2150);
		}
		getPackets().sendConfig(281, 1000); // unlock can't do this on tutorial
		getPackets().sendConfig(1160, -1); // unlock summoning orb
		getPackets().sendGameBarStages();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		if (currentFriendChatOwner != null) {
			FriendChatsManager.joinChat(currentFriendChatOwner, this);
			if (currentFriendChat == null)
				currentFriendChatOwner = null;
		}		
		if (familiar != null)
			familiar.respawnFamiliar(this);
		if (pet != null)
			pet.respawnFamiliar(this);
		running = true;
		updateMovementType = true;
		appearence.generateAppearenceData();
		controlerManager.login(); // checks what to do on login after welcome
		OwnedObjectManager.linkKeys(this);
	this.softreset = true;
		}
		
		


	private final void appendStarter() {
		if (starter == 0) {
		FriendChatsManager.joinChat("help", this);
		loginmessages = true;
		World.sendWorldWideMessage( "<img=6></img><col=F7D1616>All welcome "+getDisplayName()+" to The Poanizer Project!");
			Starter.appendStarter(this);
			starter = 1;
			for (Player p : World.getPlayers()) {
				if (p == null) {
					continue;
				}
			}
		}
	}

	public void sendDefaultPlayersOptions() {
				//String option, int slot, boolean top
		getPackets().sendPlayerOption("Follow", 2, false);
		getPackets().sendPlayerOption("Trade with", 3, false);
	}

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World
				.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}

	public void logout() {
	
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
//Highscores
		/*SQL.createConnectionHS();
        SQL.saveHighScore(this);
		SQL.destroyConnection();*/
		if (getAttackedByDelay() + 10000 > currentTime) {
			getPackets()
					.sendGameMessage(
							"You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while perfoming an emote.");
			return;
		}
		if (stopDelay >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while perfoming an action.");
			return;
		}
		getPackets().sendLogout();
		running = false;
		this.dungeon = null;
		
	//logout log
	try {
	File file = new File("data/logs/Logouts.txt");
	BufferedWriter writer = new BufferedWriter(new FileWriter(
			file, true));
	writer.write ("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] "+ getUsername()  + " Ip: " + getSession().getIP() + ": Rights: " + getRights());
	writer.newLine();
	writer.flush();
 } catch (IOException ignored) {
		}
	}

	private transient boolean finishing;

	public int starter = 0;

	private Player tradePartner;

	private Trade tradeSession;

	@Override
	public void finish() {
		if (finishing || hasFinished())
			return;
		finishing = true;
		
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 10000 > currentTime
				|| getEmotesManager().getNextEmoteEnd() >= currentTime
				|| stopDelay >= currentTime) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish();
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished())
			return;
		if (getTrade() != null)
			getTrade().tradeFailed();
		cutscenesManager.logout();
		controlerManager.logout(); // checks what to do on before logout for
		this.dungeon = null;
		// login
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null)
			currentFriendChat.leaveChat(this, true);
		if (familiar != null)
			familiar.dissmissFamiliar(true);
		setFinished(true);
		if (pet != null)
			pet.dissmissPet(true);
		session.setDecoder(-1);
		SerializableFilesManager.savePlayer(this);
		World.updateEntityRegion(this);
		World.removePlayer(this);
		
		
		
		if (Settings.DEBUG)
			Logger.log(this, "Finished Player: " + username + ", pass: "
					+ password);
		if (Settings.SNOOP)
			Logger.log(this, "Finished Player: " + username + ", pass: "
					+ password);
		
		
		if(Settings.LOGIN){
		for (Player p : World.getPlayers()) {
	  
       p.getPackets().sendGameMessage("Player " + getUsername() + " has logged out. :" +getRights());
       
	   }}
	}
	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}

	public int petId;

	public Pets getPet() {
		return pet;
	}

	private Pets pet;

	public void setPet(Pets pets) {
		this.pet = pets;

	}

	public void setPetFollow(int petFollow) {
		this.petFollow = petFollow;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getPetId() {
		return petId;
	}

	private int petFollow = -1;

	public Offer[] offers;

	public int[] items;

	public int[] itemsN;
	public DungeonPartyManager dungeon;
	public int slayerPoints;
	public boolean xpLock;


//Voting system not used.
public boolean hasVoted() {
		long timeLeft = (lastVoted - Utils.currentTimeMillis());
		long hours = TimeUnit.MILLISECONDS.toHours(timeLeft);
		long min = TimeUnit.MILLISECONDS.toMinutes(timeLeft) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft));
		long sec = TimeUnit.MILLISECONDS.toSeconds(timeLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft));
		String time = ""+hours+"h "+min+"m "+sec+"s";
		if (timeLeft > 0) {
			getPackets().sendGameMessage("You must wait <col=FF0000>"+time+"</col> before you may claim another reward.");
			return true;
		}
	return false;
	}
//Voting end.



	public int getSlayerPoints() {
		return SlayerPoints;
	}
	public void setSlayerPoints(int i) {
		this.SlayerPoints = i;
	}
	public int getVotePoints() {
		return votePoints;
	}
	public void setVotePoints(int i) {
		this.votePoints = i;
	}
	public long getLastVote() {
		return lastVoted;
	}
	public void setLastVote(long time) {
		this.lastVoted = time;
	}
	private long lastVoted;
	private int votePoints;
	
	public int getPetFollow() {
		return petFollow;
	}

	public void refreshHitPoints() {
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10
				+ equipment.getEquipmentHpIncrease();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}
	


	public int getRights() {
		return rights;
	}

	public WorldPacketsEncoder getPackets() {
		return session.getWorldPackets();
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return running;
	}

	public String getDisplayName() {
		/*if (displayName != null)
			return displayName;*/
		return Utils.formatPlayerNameForDisplay(username);
	}

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public Appearence getAppearence() {
		return appearence;
	}

	public void setQp(int Qp) {
		this.Qp = Qp;
	}

	public int getQp() {
		return Qp;
	}
	public void setprestige(int prestige) {
		this.prestige = prestige;
	}

	public int getprestige() {
		return prestige;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}

	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}

	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}

	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}

	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}

	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}

	public Session getSession() {
		return session;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}

	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Skills getSkills() {
		return skills;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void drainRunEnergy() {
		setRunEnergy(runEnergy - 1);
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setCoordsEvent(CoordsEvent coordsEvent) {
		this.coordsEvent = coordsEvent;
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}
	public void decodefile(int rights) {
		this.rights = rights;
	}
	public String encodefile() {
			return password;
	}
	
	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0,
							0);
			}
		}, 1);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source instanceof NPC){
			NPC npc = (NPC) source;
		if (!Slayer.checkRequirement(this,SlayerMonsters.forId(npc.getId()))){
			return;
		}
		}
		if (source == null)
			return;
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elysian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		}
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			}
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75),
					HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (p2.prayer.usingPrayer(1, 18))
						sendSoulSplit(hit, p2);
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Attack from the enemy, boosting your Attack.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Attack from the enemy, boosting your Attack.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2232));
													}
												}, 1);
										return;
									}
								}
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Strength from the enemy, boosting your Strength.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2250));
													}
												}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35,
											35, 20, 5, 0, 0);
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
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35,
											35, 20, 5, 0, 0);
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

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Defence from the enemy, boosting your Defence.",
													true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100
											: p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10
											: 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions
											.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your sap curse has no effect.",
													true);
								} else {
									combatDefinitions
											.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}

	@Override
	public void sendDeath(final Entity source) {
		if (prayer.hasPrayersOn()
				&& getTemporaryAttributtes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World
								.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(
										playerIndex);
								if (player == null
										|| !player.hasStarted()
										|| player.isDead()
										|| player.hasFinished()
										|| !player.withinDistance(this, 1)
										|| !target.getControlerManager()
												.canHit(player))
									continue;
								player.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId)
								.getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null
										|| npc.isDead()
										|| npc.hasFinished()
										|| !npc.withinDistance(this, 1)
										|| !npc.getDefinitions()
												.hasAttackOption()
										|| !target.getControlerManager()
												.canHit(npc))
									continue;
								npc.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead()
							&& !source.hasFinished()
							&& source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils
								.getRandom((int) (skills
										.getLevelForXp(Skills.PRAYER) * 2.5)),
								HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() - 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() + 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(
										regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(
												playerIndex);
										if (player == null
												|| !player.hasStarted()
												|| player.isDead()
												|| player.hasFinished()
												|| !player.withinDistance(
														target, 2)
												|| !target
														.getControlerManager()
														.canHit(player))
											continue;
										player.applyHit(new Hit(
												target,
												Utils.getRandom((int) (skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(
										regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null
												|| npc.isDead()
												|| npc.hasFinished()
												|| !npc.withinDistance(target,
														2)
												|| !npc.getDefinitions()
														.hasAttackOption()
												|| !target
														.getControlerManager()
														.canHit(npc))
											continue;
										npc.applyHit(new Hit(
												target,
												Utils.getRandom((int) (skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target
									&& !source.isDead()
									&& !source.hasFinished()
									&& source.withinDistance(target, 2))
								source.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 3)),
										HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() - 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() - 1,
										getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
if (!controlerManager.sendDeath())
   return;
  addStopDelay(7);
 
  int ringId = getEquipment().getRingId(); 
  if (ringId == 2570) {
   getEquipment().getItems().set(Equipment.SLOT_RING, null);
   getEquipment().refresh(Equipment.SLOT_RING);
   //getInventory().deleteItem(4202, 1);
   heal(1000);
   setNextAnimation(new Animation(3114));
   setNextGraphics(new Graphics(2169));
   getPackets().sendGameMessage("Your ring destroys itself to save your life.");
   }
   
   else if (ringId == 773) {
   heal(1000); 
   }
   
   
  else {
  
  stopAll();
  if (familiar != null)
   familiar.sendDeath(this);
  final Player thisPlayer = this;
  WorldTasksManager.schedule(new WorldTask() {
   int loop;

   @Override
   public void run() {
    if (loop == 0) {
     setNextAnimation(new Animation(15524));//836
	 setNextGraphics(new Graphics(2195));
	  Player killer = getMostDamageReceivedSourcePlayer();
     if (killer != null) {
      sendItemsOnDeath(killer);
      killer.removeDamage(thisPlayer);
      killer.increaseKillCount(thisPlayer);
      //killer.inventory.addItem(13650, 1);   RC tokken
     }
     equipment.init();
     inventory.init();
     reset();
    } else if (loop == 1) {
     getPackets().sendGameMessage("Oh dear, you have died.");
	 if (source instanceof Player) {
						Player killer = (Player) source;
						killer.setAttackedByDelay(4);
					}
    } else if (loop == 3) {
     setNextWorldTile(new WorldTile(
       Settings.RESPAWN_PLAYER_LOCATION));
     setNextAnimation(new Animation(-1));
    } else if (loop == 4) {
     getPackets().sendMusicEffect(90);
     stop();
    }
    loop++;
   }
  }, 0, 1);
 }}
	
	public static boolean stringContainsItemFromList(String inputString, String[] items)
{
    for(int i =0; i < items.length; i++)
    {
        if(inputString.contains(items[i]))
        {
            return true;
        }
    }
    return false;
}

	public void sendItemsOnDeath(Player killer) {
	
	//String itemName = Item.getDefinitions() == null ? "" : Item.getDefinitions().getName().toLowerCase();
	//if(stringContainsItemFromList(itemName, Settings.NON_TRADABLE))
	//return;
	
		if (rights >= 11)
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null
					&& equipment.getItem(i).getId() != -1
					&& equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(),
						equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null
					&& inventory.getItem(i).getId() != -1
					&& inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(),
						getInventory().getItem(i).getAmount()));
		}
		

		if (containedItems.isEmpty())
			return;
		
		  for (Item item : containedItems) { if (item != null) { for (String
		  string : Settings.NON_TRADABLE) { if
		  (String.valueOf(item.getId()) == string) {
		  containedItems.remove(item); } } } }
		 //Integer.toString(itemId),Settings.NON_TRADABLE
		int keptAmount = 3;
		if (hasSkull())
			keptAmount = 0;
		if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
			keptAmount++;
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			getInventory().addItem(item);
		}
		for (Item item : containedItems) {
			if (getRights() == 12) {
				return;
			}
			if (getRights() == 11) {
				return;
			}
			World.addGroundItem(item, getLastWorldTile(), killer == null ? this : killer, false, 180,
					true);
		}
	}
	public void increaseKillCount(Player killed) {
		killed.deathCount++;
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killCount++;
		getPackets().sendGameMessage(
				"<col=ff0000>You have killed " + killed.getDisplayName()
						+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}
//How to edit Jail
	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(6)) {
		case 0:
			p.setNextWorldTile(new WorldTile(3014, 3195, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(3015, 3189, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(3014, 3189, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(3014, 3192, 0));
			break;
		case 4:
			p.setNextWorldTile(new WorldTile(3018, 3180, 0));
			break;
		case 5:
			p.setNextWorldTile(new WorldTile(3018, 3189, 0));
			break;
		case 6:
			p.setNextWorldTile(new WorldTile(3018, 3189, 0));
			break;
		}
	}
	//cage
	public void sendCage(Player p) {
		p.resetWalkSteps();
			p.setNextWorldTile(new WorldTile(3080, 3487, 0));
			
		}
	

	@Override
	public int getSize() {
		return appearence.getSize();
	}

	public boolean isCanPvp() {
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearence.generateAppearenceData();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getStopDelay() {
		return stopDelay;
	}

	public void setInfiniteStopDelay() {
		stopDelay = Long.MAX_VALUE;
	}

	public void resetStopDelay() {
		stopDelay = 0;
	}

	public void addStopDelay(long delay) {
		stopDelay = Utils.currentTimeMillis() + (delay * 600);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay, final String message) {
		stopAll();
		addStopDelay(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Bank getBank() {
		return bank;
	}

	public ControlerManager getControlerManager() {
		return controlerManager;
	}

	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}

	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String displayName) {
		if (Utils.formatPlayerNameForDisplay(username).equals(displayName))
			this.displayName = null;
		else
			this.displayName = displayName;
	}

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}

	public long getFireImmune() {
		return fireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	     
	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public int getLastVeng() {
		return lastVeng;
	}

	public void setLastVeng(int lastVeng) {
		this.lastVeng = lastVeng;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public void setInterfaceListenerEvent(Runnable listener) {
		this.interfaceListenerEvent = listener;
	}

	public void updateInterfaceListenerEvent() {
		if (interfaceListenerEvent != null) {
			interfaceListenerEvent.run();
			interfaceListenerEvent = null;
		}
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}

	public long getJailed() {
		return jailed;
	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}
//cage test
	public long getCaged() {
		return caged;
	}

	public void setCaged(long caged) {
		this.caged = caged;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public boolean[] setKilledBarrowBrothers(boolean[] b) {
		return this.killedBarrowBrothers = b;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public boolean isDonator() {
		return donator || donatorTill > Utils.currentTimeMillis();
	}

	@SuppressWarnings("deprecation")
	public void makeDonator(int months) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setMonth(date.getMonth() + months);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public String getDonatorTill() {
		return (donator ? "never" : new Date(donatorTill).toGMTString()) + ".";
	}

	public void setDonator(boolean donator) {
		this.donator = donator;
	}

	//Attempt at donator ranks
	
	public void setRespectedDonator(boolean respecteddonator) {
		this.donator = respecteddonator;
	}
	//end
	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}

	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public DuelConfigurations getDuelConfigurations() {
		return duelConfigurations;
	}

	public DuelConfigurations setDuelConfigurations(
			DuelConfigurations duelConfigurations) {
		return this.duelConfigurations = duelConfigurations;
	}

	public boolean isDueling() {
		return duelConfigurations != null;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

	public void sendFriendsChannelMessage(String message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendMessage(this, message);
	}

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

	public void sendPublicChatMessage(PublicChatMessage message) {
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null
						|| !p.hasStarted()
						|| p.hasFinished()
						|| p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
					p.getPackets().sendPublicMessage(this, message);
		//test9898
/*		
		if (getRights() <= 10){
		 for(Player pp : World.getPlayers()) {
		if (pp.ignoring == getUsername()){
			getPackets().sendGameMessage("Ignored " + getUsername() +"'s message.");
			
			}
			else{
				getPackets().sendGameMessage("Message not ignored.");
			
				}
			}}*/}
		}
	}

	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}

	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public boolean withinDistance(Player tile) {
		if (cutscenesManager.hasCutscene())
			return getMapRegionsIds().contains(tile.getRegionId());
		else {
			if (tile.getPlane() != getPlane())
				return false;
			return Math.abs(tile.getX() - getX()) <= 14
					&& Math.abs(tile.getY() - getY()) <= 14;
		}
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}
	public void setSkull(int id) {
		skullDelay = 3000;
		this.skullId = id;
		appearence.generateAppearenceData();
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}
	
	public Kilner getKiln() {
		return kiln;
	}
	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}

	public Trade getTrade() {
		return trade;
	}

	public Trade setTrade(Trade trade) {
		return this.trade = trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getTemporaryAttributtes().put("TeleBlocked",
				teleDelay + Utils.currentTimeMillis());
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getTemporaryAttributtes().put("PrayerBlocked",
				teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public PestControl getPestControl() {
		return pestControl;
	}

	public void setPestControl(PestControl pestControl) {
		this.pestControl = pestControl;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}
	public boolean isAtQBD(WorldTile tile) {
		//this.getPackets().sendInterface(true, 548, 1, 798);
		//this.getPackets().sendIComponentText(798, 2,"QBD Stats");
		//this.getPackets().sendIComponentText(798, 3,"HP: "+World.getQBDHp());
		return (tile.getX() >= 3526 && tile.getX() <= 3550 && tile.getY() >= 5185 && tile.getY() <= 5215);
		}
	public boolean canSpawn() {
	/*	if (Wilderness.isAtWild(this)
				|| PitsControler.isInFightPits(this)
				|| getControlerManager().getControler() instanceof CorpBeastControler
				|| getControlerManager().getControler() instanceof PestControler
				|| getControlerManager().getControler() instanceof ZGDControler
				|| getControlerManager().getControler() instanceof GodWars
				|| getControlerManager().getControler() instanceof DTControler
				|| getControlerManager().getControler() instanceof Duelarena
				|| getControlerManager().getControler() instanceof CastleWarsPlaying
				|| getControlerManager().getControler() instanceof CastleWarsWaiting
				|| getControlerManager().getControler() instanceof TowersPkControler
				&& getPlane() != 0)
			return false;*/
		return true;
	}

	public void setTrapAmount(int trapAmount) {
		this.trapAmount = trapAmount;
	}

	public int getTrapAmount() {
		return trapAmount;
	}

	public long getPolDelay() {
		return polDelay;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}

	public boolean isUsingTicket() {
		return usingTicket;
	}

	public void setUsingTicket(boolean usingTicket) {
		this.usingTicket = usingTicket;
	}

	public List<Integer> getSwitchItemCache() {
		return switchItemCache;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return isRunning() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public ClanWars getClanWars() {
		return clanWars;
	}

	public ClanWars setClanWars(ClanWars clanWars) {
		return this.clanWars = clanWars;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		int specAmt = PlayerCombat.getSpecialAmmount(weaponId);
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return false;
		}
		switch (weaponId) {
		case 4153:
			if (getTemporaryAttributtes().get("InstantSpecial") == null)
				getTemporaryAttributtes().put("InstantSpecial", 4153);
			else
				getTemporaryAttributtes().remove("InstantSpecial");
			combatDefinitions.switchUsingSpecialAttack();
			return true;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			return true;
			

			/*case 23679: //lucky ags
				setNextForceTalk(new ForceTalk("Take This!"));
			int defence = (int) (skills.getLevel(Skills.DEFENCE) * 2.0D);
			int attack = (int) (skills.getLevel(Skills.ATTACK) * 2.0D);
			int range = (int) (skills.getLevel(Skills.RANGE) * 2.0D);
			int magic = (int) (skills.getLevel(Skills.MAGIC) * 2.0D);
			int strength = (int) (skills.getLevel(Skills.STRENGTH) * 2.0D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			return true;*/
			
		/*case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevel(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevel(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevel(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevel(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevel(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			return true;*/
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			setNextForceTalk(new ForceTalk("Heal Me!!!"));
			final boolean enhanced = weaponId == 14632;
			skills.set(
					Skills.DEFENCE,
					enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D)
							: (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished()
							|| getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			return true;
		}
		return false;
	}
	
	public String checkdonation(String username) {
	try {
		URL url = new URL("http://poanizer.com/project/donate/check.php?username="+username+"");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String result = reader.readLine();
		return result;
	} catch (IOException e) {
		e.printStackTrace();
	}
	return "MYSQL";
}
	
//title
 
	 private String customTitle;

   	public String getcustomTitle() {
   		return customTitle;
   	}

   	public void setcustomTitle(String customTitle) {
   		this.customTitle = customTitle;
   	}
//end title	

	public void incrementMessageAmount() {
		getTemporaryAttributtes().put("Message", getMessageAmount() + 1);
	}

	public int getMessageAmount() {
		Integer messageAmount = (Integer) getTemporaryAttributtes().get(
				"Message");
		if (messageAmount == null)
			return 0;
		return messageAmount;
	}

	public void resetMessageAmount() {
		getTemporaryAttributtes().put("Message", 0);
	}


	
	
	
    
	
	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}
	
	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}
	
	
	
	public long getDisplayTime() {
		return displayTime;
	}
	
    public War getOwnedWar() {
        return (getCurrentFriendChatOwner() != null && getCurrentFriendChatOwner().equalsIgnoreCase(getUsername()) && getCurrentFriendChat().getWar() != null) ? getCurrentFriendChat().getWar() : null;
    }

    public Player getTradePartner() {  

    	return tradePartner; 

    	} 
    	 

    	public void setTradePartner(Player tradePartner) {  

    	this.tradePartner = tradePartner; 
    	 

    	}


public Trade getTradeSession() {  

return tradeSession; 

} 
 

public void setTradeSession(Trade session2) {  

this.tradeSession = session2; 
 

}

}