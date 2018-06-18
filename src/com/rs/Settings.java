package com.rs;

import com.rs.game.WorldTile;

public final class Settings {

//Server Name
	public static final String SERVER_NAME = "The Newman Project";

//Information links
	public static final String WEBSITE_LINK = "http://www.poanizer.com/";
	public static final String ITEMLIST_LINK = "";
	public static final String RULES = "";
	public static final String YOUTUBE_LINK = "";
	public static final String VOTE_LINK = "";
	public static final String HIGHSCORES_LINK = "";
	public static final String FORUM = "";
	public static final String UPDATES = "";
	public static final String WIKI_LINK = "";
	public static final String DONATE_LINK = "";
	public static final String DONATE2_LINK = "";

//Server/Client Settings
	public static final String LASTEST_UPDATE = "Unused.";
	public static final int PORT_ID = 43594;
	public static final String CACHE_PATH = "data/cache/";
	public static final int RECEIVE_DATA_LIMIT = 7500;
	public static final int PACKET_SIZE_LIMIT = 7500;
	public static final int CLIENT_BUILD = 667;
	public static final int CUSTOM_CLIENT_BUILD = 1;

//Links
	public static final String ITEMDB_LINK = "http://www.runelocus.com/tools/itemlist.html";
	public static final String NPCDB_LINK = "http://www.runelocus.com/tools/npclist.html";
	public static final String OBJECTDB_LINK = "http://www.runelocus.com/tools/objectlist.html";
	
	public static final String CLIENT = "";
	public static final String ADV1 = "";
	public static final String ADV2 = "http://www.rune-server.org/runescape-development/rs-503-client-server/advertise/551439-poanizer-project-667-eco.html";
	public static final String STAFF = "http://www.poanizer.com/forum/";
	public static final String PORN_LINK = "http://meatspin.com/";
//Videos	
	public static final String LOITUMA1 = "http://www.youtube.com/watch?v=4om1rQKPijI";
	public static final String LOITUMA2 = "http://www.youtube.com/watch?v=_mdMb6bRXt4";	
	public static final String HEYYA = "http://www.youtube.com/watch?v=ZZ5LpwO-An4";	
	public static final String KIWI = "http://www.youtube.com/watch?v=2H2BOGGUbm4";
	public static final String TAKEN = "http://www.youtube.com/watch?v=KgmO32IdwuE";
	public static final String NIGGA1 = "http://www.youtube.com/watch?v=ESuWnNW5H7o";
	public static final String NIGGA2 = "http://www.youtube.com/watch?v=Fgk4Cd7h6ak";
	public static final String JEFF1 = "http://www.youtube.com/watch?v=VfFWkoP4bv0";
	public static final String JEFF2 = "http://www.youtube.com/watch?v=icXi8E4a3ME";	
	public static final String RICK = "http://www.youtube.com/watch?v=dQw4w9WgXcQ";	
	public static final String WAKEFUP= "http://www.youtube.com/watch?v=ZQVdGyfJlhk";
	public static final String WAKEFUP2= "http://youtu.be/wFAD1bp51bE?t=1m";	
	public static final String READ= "http://www.youtube.com/watch?v=KP3p9E7twmY";	
	public static final String IBANNED= "http://www.youtube.com/watch?v=wFAD1bp51bE";
	
//Custom Rights

//Up to 17 ranks have been added into the client to future proof.
//Their Chats just use the next image in line on the crown images list.
	public static final int Unused4 = 17;
	public static final int Unused3 = 16;
	public static final int Unused2 = 15;
	public static final int Unused1 = 14;
	public static final int IS_TRUSTED = 13;
	public static final int IS_OWNER = 12;
	public static final int IS_DEVELOPER = 11;
	public static final int IS_ADMIN = 10;
	public static final int IS_HIDDEN = 9;
	public static final int IS_GLOBAL = 8;
	public static final int IS_MOD = 7;
	public static final int IS_SUPPORT = 6;
	public static final int IS_FORUM = 5;
	public static final int IS_GRAPHIC = 4;
	public static final int IS_RESPECTED = 3;
	public static final int IS_SUPER = 2;
	public static final int IS_DONATOR = 1;
	public static final int IS_NORMAL_PLAYER = 0;

	
//Variables	
	public static boolean hackmess;
	public static boolean DEBUG;
	public static boolean SNOOP;
	public static boolean LOGIN;
	public static boolean HOSTED;
	public static boolean NEGGA = false;
	public static boolean POON = true;
	public static boolean removeiplock = false;
	public static long wokepo = 0;
	
// GUI Settings
	public static final String GUI_SIGN = "V 1.0";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
// World Settings
	public static final int START_PLAYER_HITPOINTS = 100;
	
	//Change these to your Home coords. Do ;;coords ingame
	public static final WorldTile START_PLAYER_LOCATION = new WorldTile(3087,3492, 0);
	public static final WorldTile RESPAWN_PLAYER_LOCATION = new WorldTile(3087,3492, 0);
	
	public static final String START_CONTROLER = "StartTutorial"; 
	public static final long MAX_PACKETS_DECODER_PING_DELAY = 30000; // 30seconds
	public static final int WORLD_CYCLE_TIME = 600; // the speed of world in ms
	public static final int XP_RATE = 1; // x1 for combat, x1 for skills
	public static final int SKILLING_XP_RATE = 1;
	public static final int COMBAT_XP_RATE = 1;
	public static final int AIR_GUITAR_MUSICS_COUNT = 25;
	
//Mem' Settings
	public static final int PLAYERS_LIMIT = 2000;
	public static final int LOCAL_PLAYERS_LIMIT = 250;
	public static final int NPCS_LIMIT = Short.MAX_VALUE;
	public static final int LOCAL_NPCS_LIMIT = 1000;
	public static final int MIN_FREE_MEM_ALLOWED = 30000000; // 30mb

//Game Constants
	public static final int[] MAP_SIZES = { 104, 120, 136, 168 };
	public static final int[] GRAB_SERVER_KEYS = { 1362, 77448, 44880, 39771,
			24563, 363672, 44375, 0, 1614, 0, 5340, 142976, 741080, 188204,
			358294, 416732, 828327, 19517, 22963, 16769, 1244, 11976, 10, 15,
			119, 817677, 1624243};
		

	public static String[] DONATOR_ITEMS = {"ceremonial", "lucky","Crystal saw", "primal", "blue cape", "red cape", "ornament", "dice (up to 100)", "dice (2, 6 sides)", "dice", 
        "(i)", "(or)", };
		
	public static String[] NON_TRADABLE = {"7570","23659","20767","20768", "8844", "8845", "8846", "8847", "8848", "8849", "8850", "20072"};
	
	
	//No point using unless you dont want people to "Copy" items.
	//public static String[] EARNED_ITEMS = { "castle wars ticket", "(class",	"sacred clay", "dominion" };

	

	private Settings() {

	}
}
