package com.rs.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
//REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE joe gay never
public class NPCSpawning {

	/**
	 * Contains the custom npc spawning
	 */

	public static void spawnNPCS() {
		/**
		 * NPCS
		 */
		 

/*

Things you need to move to make your home.


To move Shops/Objects, login to an admin account. Find your new home. Then do ::Coords. You will get an x, y and z 
coordinate for the tile you are standing on. 

Then go ahead and find every coordinate for each NPC. Where it says "WorldTile(2595, 4771, 0)" 

it means: WorldTile(x, y, z)

Change those numbers to the x y z coords you got from doing ;;coords. and then compile.
*/


//Shop NPC's
	World.spawnNPC(6537, new WorldTile(2629, 4893, 2), 0, true, true); //Special item shop
	World.spawnNPC(2830, new WorldTile(2630, 4893, 2), 0, true, true); //melee wepons
	World.spawnNPC(211, new WorldTile(2631, 4893, 2), 0, true, true);//melee armour
	World.spawnNPC(1694, new WorldTile(2632, 4893, 2), 0, true, true);//range weapon
	World.spawnNPC(346, new WorldTile(2633, 4893, 2), 0, true, true);//range armour
	World.spawnNPC(524, new WorldTile(2634, 4893, 2), 0, true, true); //mage gear
	World.spawnNPC(546, new WorldTile(2635, 4893, 2), 0, true, true); //Mmagic supplies
	
	World.spawnNPC(454, new WorldTile(2636, 4893, 2), 0, true, true);//food n pots
	World.spawnNPC(4555, new WorldTile(2636, 4892, 2), 0, true, true); //Fog Shop
	World.spawnNPC(210, new WorldTile(2636, 4891, 2), 0, true, true); //pure acces
	World.spawnNPC(576, new WorldTile(2636, 4890, 2), 0, true, true); //skill shop
	World.spawnNPC(519, new WorldTile(2636, 4889, 2), 0, true, true); //herb sho
	World.spawnNPC(523, new WorldTile(2636, 4888, 2), 0, true, true); //sell to me
	World.spawnNPC(544, new WorldTile(2636, 4887, 2), 0, true, true); //skilcape shop
	World.spawnNPC(6971, new WorldTile(2636, 4886, 2), 0, true, true);//summoning supplies
	
	World.spawnNPC(15582, new WorldTile(2629, 4893, 2), 0, true, true); //Prestige
	World.spawnNPC(8461, new WorldTile(2629, 4892, 2), 0, true, true);	//turadel
	World.spawnNPC(5027, new WorldTile(2629, 4891, 2), 1, true, true); //Shop Tele
	World.spawnNPC(2676, new WorldTile(2629, 4890, 2), 0, true, true); //makeover mage
	World.spawnNPC(1039, new WorldTile(2629, 4889, 2), 0, true, true); //male clothes
	World.spawnNPC(285, new WorldTile(2629, 4888, 2), 0, true, true);	//female clothes	
	World.spawnNPC(3374, new WorldTile(2629, 4887, 2), 0, true, true); //Max
	World.spawnNPC(659, new WorldTile(2629, 4886, 2), 0, true, true);//vote?

	World.spawnNPC(945, new WorldTile(2635, 4886, 2), 0, true, true);//guide
	World.spawnNPC(220, new WorldTile(3090, 3489, 2), 0, true, true);//summoning scrolls
	World.spawnNPC(410, new WorldTile(2360, 4886, 2), 0, true, true); //Gambler
	//my shops
	World.spawnNPC(549, new WorldTile(3090, 3493, 0), 2, true, true);
	
 //Altars 
	World.spawnObject(new WorldObject(409, 10, 2, 2636, 4879, 2), true);//Guilded
	World.spawnObject(new WorldObject(6552, 10, 2, 2633, 4879, 2), true);//ancients
	World.spawnObject(new WorldObject(28698, 10, 2, 2630, 4879, 2), true);//lunar
	World.spawnObject(new WorldObject(47120, 10, 0, 2627, 4879, 2), true);//curses
	
//Other Objects
	World.spawnObject(new WorldObject(55301, 10, 2, 2624, 4893, 2), true);// Pk Kills/Deaths ranks board
	World.spawnObject(new WorldObject(50205, 10, 0, 2624, 4890, 2), true);//Summoming obelisk
	World.spawnObject(new WorldObject(17248, 10, 3, 2624, 4886, 2), true);//Lucky item exchange	
	World.spawnObject(new WorldObject(172, 10, 1, 2624, 4883, 2), true);//crystal key chest
	World.spawnObject(new WorldObject(13619, 10, 2, 2633, 4897, 2), true);//red portal for teleports
	World.spawnObject(new WorldObject(2793, 10, 1, 2639, 4879, 2), true);//stall to steal
	World.spawnNPC(3709, new WorldTile(3090, 3494, 0), 0, true, true); //Mr Ex
	
	
/* END OF STUFF TO CHANGE  	
//----------------------------------------------------------------------------------------------------------------------			 

*/


World.spawnNPC(5580, new WorldTile(2632, 4889, 2), 0, true, true);//Poanizer



//DZ shops
	World.spawnNPC(210, new WorldTile(3814, 2850, 0), 0, true, true);
	World.spawnNPC(576, new WorldTile(3813, 2850, 0), 0, true, true);
	World.spawnNPC(519, new WorldTile(3812, 2850, 0), 0, true, true);
	World.spawnNPC(454, new WorldTile(3811, 2850, 0), 0, true, true);
	World.spawnNPC(4555, new WorldTile(3810, 2850, 0), 0, true, true);
	World.spawnNPC(692, new WorldTile(3808, 2850, 0), 0, true, true);
	World.spawnNPC(566, new WorldTile(3807, 2850, 0), 0, true, true);

		
	World.spawnNPC(6537, new WorldTile(3814, 2855, 0), 0, true, true);
	World.spawnNPC(2830, new WorldTile(3813, 2855, 0), 0, true, true);
	World.spawnNPC(211, new WorldTile(3812, 2855, 0), 0, true, true);
	World.spawnNPC(1694, new WorldTile(3811, 2855, 0), 0, true, true);
	World.spawnNPC(346, new WorldTile(3810, 2855, 0), 0, true, true);
	World.spawnNPC(524, new WorldTile(3809, 2855, 0), 0, true, true);
	World.spawnNPC(546, new WorldTile(3808, 2855, 0), 0, true, true);
//Shops end
	
	
//nother training guy with dialogue.
World.spawnNPC(13295, new WorldTile(2326, 3801, 0), 0, true, true);
							
	
		 
		 World.spawnObject(new WorldObject(1, 10, 3, 2090, 5795, 0), true);
	
	//RS Deletes
	World.deleteObject(new WorldTile(3083, 3492, 0));

	//caste wars portal.
	World.spawnObject(new WorldObject(4388, 10, 1, 2438, 3082, 0), true);

	//box rack
	World.spawnObject(new WorldObject(13381, 10, 1, 1892, 5401, 0), true);

	//benches
	World.spawnObject(new WorldObject(42380, 10, 3, 3082, 3493, 0), true);
	World.spawnObject(new WorldObject(42380, 10, 3, 3082, 3491, 0), true);


//Kalphite King 

	// NW Corner /*
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2852, 0), true);
	// NW Corner /*

	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2850, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2848, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2846, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2844, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2842, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2840, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2838, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2836, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2834, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2832, 0), true);

	// SW Corner /*
	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2830, 0), true);
	// SW Corner /*

	World.spawnObject(new WorldObject(471, 10, -1, 3212, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3214, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3216, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3218, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3220, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3222, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3224, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3226, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3228, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3230, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3232, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3234, 2830, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3236, 2830, 0), true);

	// SE Corner /*
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2830, 0), true);
	// SE Corner /*

	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2832, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2834, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2836, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2838, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2840, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2842, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2844, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2846, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2848, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2850, 0), true);

	// NE Corner /*
	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2852, 0), true);
	// NE Corner /*

	World.spawnObject(new WorldObject(471, 10, -1, 3238, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3236, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3234, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3232, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3230, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3228, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3226, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3224, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3222, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3220, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3218, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3216, 2852, 0), true);
	World.spawnObject(new WorldObject(471, 10, -1, 3214, 2852, 0), true);
//End of KK
	
	
	//Castlewars
	World.spawnNPC(1526, new WorldTile(2443, 3089, 0), -1, true, true);

	//Train master
	World.spawnNPC(13295, new WorldTile(2326, 3801, 0), 0, true, true);

	
//DONATORZONE
	//Rocktail
	World.spawnNPC(8842, new WorldTile(3811, 2862, 0), 0, true, true);

	//Flags
	World.spawnObject(new WorldObject(10541, 10, 3, 3805, 2846, 0), true);
	World.spawnObject(new WorldObject(10541, 10, 1, 3805, 2841, 0), true);

	//Flour bin	
	World.spawnObject(new WorldObject(1782, 10, 1, 3786, 2825, 0), true);
	
	//Donator chest, unfinished
	World.spawnObject(new WorldObject(59731, 10, 1, 3796, 2844, 0), true);
	//Flag by chest	
	World.spawnObject(new WorldObject(59731, 10, 1, 3795, 2844, 0), true);
		
	//Red portal monster tels	
	World.spawnObject(new WorldObject(13619, 10, 1, 3815, 2852, 0), true);
		
	//Agility
	World.spawnObject(new WorldObject(2823, 10, 0, 3795, 2869, 0), true);
	World.spawnObject(new WorldObject(2823, 10, 0, 3783, 2869, 0), true);
		
	//Titan cave
		World.spawnObject(new WorldObject(5007, 10, 3, 3780, 2840, 0), true);
		
		
	//Banks
	World.spawnObject(new WorldObject(4483, 10, 3, 3805, 2850, 0), true);
	World.spawnObject(new WorldObject(4483, 10, 3, 3805, 2855, 0), true);
	
	//Farming	
	World.spawnObject(new WorldObject(7848, 10, 0, 3813, 2836, 0), true);//flower
	World.spawnObject(new WorldObject(8151, 10, 0, 3810, 2836, 0), true);//herb
	World.spawnObject(new WorldObject(8553, 10, 0, 3807, 2836, 0), true);//allotment
	World.spawnObject(new WorldObject(8553, 10, 0, 3807, 2837, 0), true);//allotment
	World.spawnObject(new WorldObject(8553, 10, 0, 3808, 2836, 0), true);//allotment
	World.spawnObject(new WorldObject(8553, 10, 0, 3808, 2837, 0), true);//allotment
	
	//hunter
	World.spawnNPC(5113, new WorldTile(3814, 2832, 0), 0, true, true);//hunter shop

	


	//nigel	
	World.spawnNPC(3681, new WorldTile(3787, 2828, 0), 0, true, true);
	//nigel	
	World.spawnNPC(219, new WorldTile(3807, 2862, 0), 0, true, true);

	//roof
	for(int roof = 3816; roof < 3807; roof++){//west
	World.deleteObject(new WorldTile(roof, 2845, 2));
	}
	for(int roof1 = 3816; roof1 < 3807; roof1++){//mid
	World.deleteObject(new WorldTile(roof1, 2844, 2));
	}
	for(int roof2 = 3816; roof2 < 3807; roof2++){//east
	World.deleteObject(new WorldTile(roof2, 2839, 2));
	}
	
	//beams on roof
	for(int beam = 2841; beam < 2848; beam++){
	World.deleteObject(new WorldTile(3813, beam, 1));
	}for(int beam2 = 2841; beam2 < 2848; beam2++){
	World.deleteObject(new WorldTile(3810, beam2, 1));
	}for(int beam3 = 2841; beam3 < 2848; beam3++){
	World.deleteObject(new WorldTile(3807, beam3, 1));
	}
	//spec altar
	 World.spawnObject(new WorldObject(61, 10, 1, 3804, 2843, 0), true);
	 World.spawnObject(new WorldObject(61, 10, 1, 3804, 2844, 0), true);

	
	
//END OF NEW DZ

//dead stuff

	//slaves
	//World.spawnObject(new WorldObject(46491, 10, 0, 2340, 3803, 0), true);
	
	World.spawnNPC(15032, new WorldTile(2332, 3803, 0), 0, true, true);//dieng soldier
	
	
	World.spawnNPC(15023, new WorldTile(2336, 3807, 0), 0, true, true);
	World.spawnNPC(3077, new WorldTile(2335, 3799, 0), 0, true, true);
	World.spawnNPC(3077, new WorldTile(2342, 3803, 0), 0, true, true);
	World.spawnNPC(3077, new WorldTile(2342, 3809, 0), 0, true, true);

	World.spawnNPC(3077, new WorldTile(2333, 3806, 0), 0, true, true);
	World.spawnNPC(15019, new WorldTile(2339, 3801, 0), 0, true, true);
	World.spawnNPC(15023, new WorldTile(2345, 3807, 0), 0, true, true);
	World.spawnNPC(15019, new WorldTile(2346, 3801, 0), 0, true, true);

	//burning trees
	World.spawnObject(new WorldObject(677, 10, 0, 2318, 3810, 0), true);
	World.spawnObject(new WorldObject(677, 10, 0, 2318, 3817, 0), true);
	World.spawnObject(new WorldObject(677, 10, 0, 2314, 3813, 0), true);
	
	//bones at BK 
	World.spawnObject(new WorldObject(79555, 10, 0, 2312, 3834, 0), true);
	World.spawnObject(new WorldObject(79555, 10, 0, 2315, 3831, 0), true);
	World.spawnObject(new WorldObject(79555, 10, 0, 2318, 3836, 0), true);
	
	
	//slaves at end
	
	
	World.spawnNPC(979, new WorldTile(2333, 3863, 0), 0, true, true);
	World.spawnNPC(980, new WorldTile(2333, 3861, 0), 0, true, true);
	World.spawnNPC(981, new WorldTile(2333, 3859, 0), 0, true, true);
	World.spawnNPC(982, new WorldTile(2333, 3857, 0), 0, true, true);
	World.spawnNPC(983, new WorldTile(2333, 3855, 0), 0, true, true);

	
		//dead slave
		World.spawnObject(new WorldObject(46491, 10, 0, 2325, 3859, 0), true);
	
	
	
//QEUST END
	
		//snow
		World.spawnObject(new WorldObject(21200, 10, 4, 3779, 2842, 0), true);
		World.spawnObject(new WorldObject(21200, 10, 4, 3779, 2841, 0), true);
		
		

//Camelot Banks
		World.spawnObject(new WorldObject(782, 10, 0, 2721, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2722, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2723, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2724, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2725, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2726, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2727, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2728, 3494, 0), true);
		World.spawnObject(new WorldObject(782, 10, 0, 2729, 3494, 0), true);

	//chicken exchange
		World.spawnObject(new WorldObject(782, 10, 0, 2992, 9636, 0), true);
		
//Sled event
	//track one.
	World.spawnObject(new WorldObject(7325, 10, 0, 2640, 4056, 1), true);
	//track 2
	World.spawnObject(new WorldObject(16082, 10, 0, 2666, 4078, 1), true);
	//lobby
	World.spawnObject(new WorldObject(27567, 10, 0, 2658, 4040, 1), true);	

 //Xmas End
 
	 //snow hunter portal
	 World.spawnObject(new WorldObject(16050, 10, 0, 2720, 3798, 0), true);
	 
 
	//Zookeeper
	World.spawnNPC(7571, new WorldTile(3297, 9825, 0), -1, true, true);

 
	//grifolapine
	World.spawnNPC(14698, new WorldTile(3283, 9836, 0), -1, true, true);
	World.spawnNPC(14698, new WorldTile(3277, 9836, 0), -1, true, true);
	World.spawnNPC(14698, new WorldTile(3272, 9843, 0), -1, true, true);
	World.spawnNPC(14698, new WorldTile(3281, 9852, 0), -1, true, true);
	//beasts	
	World.spawnNPC(14696, new WorldTile(3276, 9810, 0), -1, true, true);
	World.spawnNPC(14696, new WorldTile(3277, 9805, 0), -1, true, true);
	World.spawnNPC(14696, new WorldTile(3281, 9801, 0), -1, true, true);
	World.spawnNPC(14696, new WorldTile(3286, 9799, 0), -1, true, true);
	//grifalaroo		
	World.spawnNPC(14700, new WorldTile(3312, 9813, 0), -1, true, true);
	World.spawnNPC(14700, new WorldTile(3311, 9806, 0), -1, true, true);
	World.spawnNPC(14700, new WorldTile(3320, 9803, 0), -1, true, true);
	World.spawnNPC(14700, new WorldTile(3310, 9797, 0), -1, true, true);
	//fungal mage		
	World.spawnNPC(14690, new WorldTile(3311, 9844, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(3319, 9835, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(3319, 9849, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(3313, 9834, 0), -1, true, true);
			
//Rodent
	//rope remove
	World.removeObject(new WorldObject(2353, 10, 0, 3297, 9823, 0), true);
			
	//tzhaar place
	World.spawnNPC(7746, new WorldTile(2448, 5172, 0), 0, true, true);
	World.spawnObject(new WorldObject(782, 10, 2, 2446, 5175, 0), true);
	World.spawnObject(new WorldObject(782, 10, 2, 2445, 5175, 0), true);
	World.spawnObject(new WorldObject(782, 10, 2, 2444, 5175, 0), true);


			

	//DM Arena
World.spawnNPC(6539, new WorldTile(2462, 4253, 0), 1, true, true);

World.spawnObject(new WorldObject(1, 10, 0, 2462, 4252, 0), true);

World.spawnObject(new WorldObject(16043, 10, 3, 2993, 9688, 0), true);
World.spawnObject(new WorldObject(16044, 10, 3, 2994, 9688, 0), true);


World.spawnObject(new WorldObject(2494, 10, 0, 2992, 9688, 0), true);
World.spawnObject(new WorldObject(2494, 10, 0, 2995, 9688, 0), true);/*Slayer Tower*/
	/*Crawling Hands*/
	World.spawnNPC(1648, new WorldTile(3423, 3542, 0), -1, true, true);
	World.spawnNPC(1576, new WorldTile(3099, 3624, 0), 1, true, true);//stranger
	World.spawnNPC(1650, new WorldTile(3420, 3546, 0), -1, true, true);
	World.spawnNPC(1649, new WorldTile(3421, 3541, 0), -1, true, true);
	World.spawnNPC(1653, new WorldTile(3411, 3549, 0), -1, true, true);
	World.spawnNPC(1655, new WorldTile(3427, 3549, 0), -1, true, true);
	World.spawnNPC(1651, new WorldTile(3426, 3555, 0), -1, true, true);
	World.spawnNPC(1652, new WorldTile(3423, 3559, 0), -1, true, true);
	World.spawnNPC(1657, new WorldTile(3418, 3556, 0), -1, true, true);
	World.spawnNPC(1655, new WorldTile(3413, 3558, 0), -1, true, true);
	World.spawnNPC(1648, new WorldTile(3413, 3564, 0), -1, true, true);
	World.spawnNPC(1650, new WorldTile(3414, 3574, 0), -1, true, true);
	World.spawnNPC(1650, new WorldTile(3415, 3572, 0), -1, true, true);
	World.spawnNPC(1657, new WorldTile(3422, 3574, 0), -1, true, true);
	World.spawnNPC(1657, new WorldTile(3418, 3571, 0), -1, true, true);
	World.spawnNPC(1657, new WorldTile(3415, 3559, 0), -1, true, true);
	/*Banshees*/
	World.spawnNPC(1612, new WorldTile(3446, 3537, 0), -1, true, true);
	World.spawnNPC(1612, new WorldTile(3440, 3538, 0), -1, true, true);
	World.spawnNPC(1612, new WorldTile(3433, 3551, 0), -1, true, true);
	World.spawnNPC(1612, new WorldTile(3438, 3561, 0), -1, true, true);
	World.spawnNPC(1612, new WorldTile(3446, 3562, 0), -1, true, true);
	World.spawnNPC(1612, new WorldTile(3439, 3572, 0), -1, true, true);
	/*Infernall Mages*/
	World.spawnNPC(1643, new WorldTile(3447, 3571, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3443, 3574, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3440, 3568, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3434, 3571, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3440, 3557, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3435, 3559, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3438, 3563, 1), -1, true, true);
	World.spawnNPC(1643, new WorldTile(3433, 3562, 1), -1, true, true);
	/*BloodVelds*/
	World.spawnNPC(1618, new WorldTile(3422, 3557, 1), -1, true, true);
	World.spawnNPC(1618, new WorldTile(3425, 3566, 1), -1, true, true);
	World.spawnNPC(1618, new WorldTile(3418, 3565, 1), -1, true, true);
	World.spawnNPC(1618, new WorldTile(3412, 3560, 1), -1, true, true);
	World.spawnNPC(1618, new WorldTile(3409, 3572, 1), -1, true, true);
	World.spawnNPC(1618, new WorldTile(3413, 3573, 1), -1, true, true);
	/*Aberrant Spectre*/
	World.spawnNPC(1604, new WorldTile(3410, 3535, 1), -1, true, true);
	World.spawnNPC(1607, new WorldTile(3429, 3539, 1), -1, true, true);
	World.spawnNPC(1604, new WorldTile(3423, 3541, 1), -1, true, true);
	World.spawnNPC(1605, new WorldTile(3417, 3545, 1), -1, true, true);
	World.spawnNPC(1605, new WorldTile(3412, 3549, 1), -1, true, true);
	World.spawnNPC(1605, new WorldTile(3442, 3545, 1), -1, true, true);
	World.spawnNPC(1607, new WorldTile(3434, 3550, 1), -1, true, true);
	World.spawnNPC(1604, new WorldTile(3427, 3551, 1), -1, true, true);
	/*Gargoyles*/
	World.spawnNPC(1610, new WorldTile(3434, 3550, 2), -1, true, true);
	World.spawnNPC(1610, new WorldTile(3442, 3547, 2), -1, true, true);
	World.spawnNPC(1610, new WorldTile(3448, 3537, 2), -1, true, true);
	World.spawnNPC(1610, new WorldTile(3441, 3540, 2), -1, true, true);
	World.spawnNPC(1610, new WorldTile(3435, 3539, 2), -1, true, true);
	/*Nechreyal*/
	World.spawnNPC(1613, new WorldTile(3447, 3573, 2), -1, true, true);
	World.spawnNPC(1613, new WorldTile(3441, 3571, 2), -1, true, true);
	World.spawnNPC(1613, new WorldTile(3438, 3566, 2), -1, true, true);
	World.spawnNPC(1613, new WorldTile(3434, 3572, 2), -1, true, true);
	World.spawnNPC(1613, new WorldTile(3436, 3559, 2), -1, true, true);
	World.spawnNPC(1613, new WorldTile(3445, 3561, 2), -1, true, true);
	/*Abby Demons*/
	World.spawnNPC(1615, new WorldTile(3411, 3570, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3410, 3576, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3427, 3565, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3427, 3572, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3421, 3571, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3417, 3566, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3413, 3562, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3417, 3558, 2), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3415, 3551, 2), -1, true, true);
	/*Dark Beast*/
	World.spawnNPC(2783, new WorldTile(3411, 3571, 2), -1, true, true);
	World.spawnNPC(2783, new WorldTile(3420, 3566, 2), -1, true, true);
	World.spawnNPC(2783, new WorldTile(3415, 3558, 2), -1, true, true);
	
	
	/*IceFiends*/
	World.spawnNPC(3406, new WorldTile(2721, 10214, 0), -1, true, true);
	World.spawnNPC(3406, new WorldTile(2715, 10219, 0), -1, true, true);
	World.spawnNPC(3406, new WorldTile(2730, 10205, 0), -1, true, true);
	World.spawnNPC(3406, new WorldTile(2712, 10205, 0), -1, true, true);
	
	

			
	//Quest NPCS		
	World.spawnNPC(15581, new WorldTile(3428, 3163, 0), -3, true, true); //Party

		/* Christmas event
		World.spawnNPC(13637, new WorldTile(2716, 5727, 0), -2, true, true); //Snowman
		World.spawnNPC(6742, new WorldTile(2711, 5736, 0), -1, true, true); //Warrior
		World.spawnNPC(6742, new WorldTile(2713, 5740, 0), -1, true, true); //Warrior
		World.spawnNPC(6743, new WorldTile(2710, 5740, 0), -1, true, true); //drag
		World.spawnNPC(6743, new WorldTile(2712, 5737, 0), -1, true, true); //drag
		World.spawnNPC(6744, new WorldTile(2714, 5735, 0), -1, true, true); //dwarf
		World.spawnNPC(6744, new WorldTile(2708, 5735, 0), -1, true, true); //dwarf
		World.spawnNPC(6745, new WorldTile(2711, 5737, 0), -1, true, true); //pirate
		World.spawnNPC(6745, new WorldTile(2711, 5739, 0), -1, true, true); //pirate
		World.spawnNPC(8539, new WorldTile(3244, 3197, 0), -1, true, true); //Queen
		World.spawnNPC(8517, new WorldTile(3443, 3674, 0), -1, true, true); //Jack
		World.spawnNPC(6739, new WorldTile(2714, 5726, 0), -1, true, true); //Imp
		World.spawnNPC(6739, new WorldTile(2710, 5726, 0), -1, true, true); //Imp
		World.spawnNPC(6739, new WorldTile(2723, 5726, 0), -1, true, true); //Imp
		World.spawnNPC(8541, new WorldTile(2719, 5730, 0), -1, true, true); //Head Imp
*/
		
		
//qbd shop
	World.spawnNPC(400, new WorldTile(3535, 5186, 0), 1, true, true);
	
/*Hunter*/
	/*Chinchompa Carnivorus*/
	World.spawnNPC(5080, new WorldTile(2574, 2906, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2576, 2903, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2574, 2908, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2572, 2908, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2571, 2901, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2572, 2909, 0), -1, true, true);
	//geckos
	World.spawnNPC(6916, new WorldTile(2582, 2928, 0), -1, true, true); 		
	World.spawnNPC(6916, new WorldTile(2581, 2931, 0), -1, true, true); 		
	World.spawnNPC(6916, new WorldTile(2584, 2931, 0), -1, true, true); 		
		
	/*Wagtails*/
	World.spawnNPC(5072, new WorldTile(2595, 2911, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2597, 2912, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2593, 2914, 0), -1, true, true);
		
	/*Swifts*/
	World.spawnNPC(5073, new WorldTile(2607, 2925, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2602, 2918, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2609, 2920, 0), -1, true, true);
		
	/*Warbler*/
	World.spawnNPC(5075, new WorldTile(2592, 2887, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2594, 2883, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2597, 2887, 0), -1, true, true);
		
	/*Longtail*/
	World.spawnNPC(5076, new WorldTile(2604, 2896, 0), -1, true, true);
	World.spawnNPC(5076, new WorldTile(2607, 2893, 0), -1, true, true);
	
/*Jadinko's*/
	World.spawnNPC(13816, new WorldTile(3059, 9246, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3044, 9234, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3028, 9235, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3033, 9237, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3039, 9235, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3045, 9234, 0), -1, true, true);
	World.spawnNPC(13816, new WorldTile(3026, 9232, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3039, 9244, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3022, 9257, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3015, 9260, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3039, 9244, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3046, 9270, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3043, 9260, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3043, 9260, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3063, 9236, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3056, 9250, 0), -1, true, true);
	World.spawnNPC(13818, new WorldTile(3019, 9255, 0), -1, true, true);
	World.spawnNPC(13818, new WorldTile(3046, 9265, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3044, 9229, 0), -1, true, true);
	World.spawnNPC(13819, new WorldTile(3059, 9242, 0), -1, true, true);

	/*Mutated Jadinko's*/
	/*Babies*/
	World.spawnNPC(13820, new WorldTile(3060, 9238, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3060, 9247, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3036, 9231, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3030, 9236, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3020, 9234, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3040, 9262, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3045, 9270, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3022, 9253, 0), -1, true, true);
	World.spawnNPC(13820, new WorldTile(3015, 9259, 0), -1, true, true);
	
	/*Guards*/
	World.spawnNPC(13821, new WorldTile(3065, 9243, 0), -1, true, true);
	World.spawnNPC(13821, new WorldTile(3034, 9235, 0), -1, true, true);
		
	/*Males*/
	World.spawnNPC(13822, new WorldTile(3043, 9239, 0), -1, true, true);
	World.spawnNPC(13822, new WorldTile(3043, 9265, 0), -1, true, true);
	
//Old Polypore Dungeon
	/*Rats*/
	World.spawnNPC(14692, new WorldTile(2840, 9490, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2844, 9485, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2840, 9487, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2832, 9494, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2834, 9495, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2861, 9523, 0), -1, true, true);
	World.spawnNPC(14692, new WorldTile(2866, 9512, 0), -1, true, true);
	/*Mage*/
	World.spawnNPC(14690, new WorldTile(2833, 9493, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2836, 9502, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2835, 9511, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2831, 9510, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2831, 9518, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2837, 9522, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2838, 9518, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2838, 9518, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2852, 9519, 0), -1, true, true);
	World.spawnNPC(14690, new WorldTile(2856, 9523, 0), -1, true, true);
	/*Gano Runt*/
	World.spawnNPC(14698, new WorldTile(2864, 9496, 0), -1, true, true);
	World.spawnNPC(14698, new WorldTile(2861, 9495, 0), -1, true, true);
	World.spawnNPC(14698, new WorldTile(2864, 9496, 0), -1, true, true);
	/*Gano Beast*/
	World.spawnNPC(14696, new WorldTile(2870, 9509, 0), -1, true, true);
	/*Grifalaroo*/
	World.spawnNPC(14700, new WorldTile(2861, 9524, 0), -1, true, true);
	World.spawnNPC(14700, new WorldTile(2866, 9527, 0), -1, true, true);
	/*grifolapine*/
	World.spawnNPC(14688, new WorldTile(2853, 9520, 0), -1, true, true);
	World.spawnNPC(14688, new WorldTile(2847, 9520, 0), -1, true, true);
	World.spawnNPC(14688, new WorldTile(2841, 9518, 0), -1, true, true);
	/*Infested Axes*/
	World.spawnNPC(14694, new WorldTile(2831, 9520, 0), -1, true, true);
	World.spawnNPC(14694, new WorldTile(2835, 9514, 0), -1, true, true);
	World.spawnNPC(14694, new WorldTile(2838, 9511, 0), -1, true, true);
	
		
//Corp and GWD
	World.spawnNPC(8133, new WorldTile(2894, 4393, 0), -1, true, true);
	World.spawnNPC(6222, new WorldTile(2828, 5302, 2), -1, true, true);
	World.spawnNPC(6203, new WorldTile(2934, 5324, 2), -1, true, true);
	
//Crabs
	World.spawnNPC(1265, new WorldTile(2700, 3715, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2696, 3719, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2706, 3724, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2711, 3719, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2717, 3726, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2721, 3717, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2721, 3706, 0), -1, true, true);
	World.spawnNPC(1265, new WorldTile(2716, 3700, 0), -1, true, true);
	
	//End Crabs start abyss
	World.spawnNPC(1615, new WorldTile(3029, 4842, 0), -1, true, true);
	World.spawnNPC(3200, new WorldTile(3026, 4829, 0), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3033, 4821, 0), -1, true, true);
	World.spawnNPC(3200, new WorldTile(3047, 4822, 0), -1, true, true);
	World.spawnNPC(1615, new WorldTile(3051, 4837, 0), -1, true, true);
	
	//Start thieving npcs and home wolfs
	World.spawnNPC(1, new WorldTile(3490, 3491, 0), -1, true, true);
	World.spawnNPC(2, new WorldTile(3503, 3488, 0), -1, true, true);
	World.spawnNPC(4, new WorldTile(3490, 3483, 0), -1, true, true);
	World.spawnNPC(5, new WorldTile(3507, 3503, 0), -1, true, true);
	World.spawnNPC(7, new WorldTile(3472, 3499, 0), -1, true, true);
	World.spawnNPC(15, new WorldTile(3482, 3476, 0), -1, true, true);
	World.spawnNPC(1715, new WorldTile(3479, 3496, 1), -1, true, true);
	World.spawnNPC(1714, new WorldTile(3497, 3474, 1), -1, true, true);

	//Start dragons
	World.spawnNPC(51, new WorldTile(2914, 9807, 0), -1, true, true);
	World.spawnNPC(52, new WorldTile(2914, 9798, 0), -1, true, true);
	World.spawnNPC(53, new WorldTile(2910, 9801, 0), -1, true, true);
	World.spawnNPC(54, new WorldTile(2907, 9806, 0), -1, true, true);
	World.spawnNPC(55, new WorldTile(2902, 9803, 0), -1, true, true);
	//End Dragons
	
	//Snails
	World.spawnNPC(1227, new WorldTile(2861, 9732, 0), -1, true, true);
	World.spawnNPC(1228, new WorldTile(2857, 9733, 0), -1, true, true);
	World.spawnNPC(1229, new WorldTile(2858, 9738, 0), -1, true, true);
	World.spawnNPC(1230, new WorldTile(2861, 9738, 0), -1, true, true);
	World.spawnNPC(1231, new WorldTile(2866, 9736, 0), -1, true, true);
	World.spawnNPC(1232, new WorldTile(2862, 9734, 0), -1, true, true);
	World.spawnNPC(1233, new WorldTile(2860, 9733, 0), -1, true, true);
	World.spawnNPC(1234, new WorldTile(2859, 9736, 0), -1, true, true);
	World.spawnNPC(1235, new WorldTile(2860, 9740, 0), -1, true, true);
	//End Snails
//Hunter
	World.spawnNPC(5080, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5081, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(6916, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(7272, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(6942, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(1192, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5074, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(7031, new WorldTile(2715, 9203, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5081, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(6916, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(7272, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(6942, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(1192, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5074, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(7031, new WorldTile(2732, 9186, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5081, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(6916, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(7272, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(6942, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(1192, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5074, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(7031, new WorldTile(2714, 9170, 0), -1, true, true);
	World.spawnNPC(5080, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(5081, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(6916, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(7272, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(6942, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(1192, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(5073, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(5075, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(5074, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(5072, new WorldTile(2699, 9186, 0), -1, true, true);
	World.spawnNPC(7031, new WorldTile(2699, 9186, 0), -1, true, true);
	//Hunter master
	World.spawnNPC(5113, new WorldTile(2594, 2928, 0), -3, true, true); //Hunter
	
	//End Hunter

	//VyreWatches
	World.spawnNPC(7044, new WorldTile(3626, 5113, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3624, 5128, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3634, 5143, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3654, 5141, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3696, 5141, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3695, 5137, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3682, 5110, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3677, 5111, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3649, 5083, 0), -1, true, true);
	World.spawnNPC(7044, new WorldTile(3629, 5083, 0), -1, true, true);
	//End VyreWatches

	//Black Demons
	World.spawnNPC(82, new WorldTile(3668, 5088, 0), -1, true, true);
	World.spawnNPC(82, new WorldTile(3679, 5097, 0), -1, true, true);
	World.spawnNPC(83, new WorldTile(3669, 5114, 0), -1, true, true);
	World.spawnNPC(83, new WorldTile(3664, 5125, 0), -1, true, true);
	World.spawnNPC(84, new WorldTile(3672, 5135, 0), -1, true, true);
	World.spawnNPC(84, new WorldTile(3685, 5134, 0), -1, true, true);
	World.spawnNPC(84, new WorldTile(3677, 5154, 0), -1, true, true);
	World.spawnNPC(83, new WorldTile(3653, 5137, 0), -1, true, true);
	World.spawnNPC(84, new WorldTile(3642, 5145, 0), -1, true, true);
	//End Black Demons
	
//fishing
	World.spawnNPC(327, new WorldTile(2595, 3423, 0), -1, true, true); //Fishing
	World.spawnNPC(328, new WorldTile(2598, 3419, 0), -1, true, true); //Fishing
	World.spawnNPC(6267, new WorldTile(2601, 3422, 0), -1, true, true); //Fishing
	World.spawnNPC(312, new WorldTile(2604, 3426, 0), -1, true, true); //Fishing
	
//fish shop
	World.spawnNPC(219, new WorldTile(2591, 3421, 0), 1, true, true); 

	
	
//Bankers
	World.spawnNPC(44, new WorldTile(3018, 4517, 0), -3, true, true); 
	World.spawnNPC(44, new WorldTile(3018, 4516, 0), -3, true, true); 
	World.spawnNPC(44, new WorldTile(3018, 4515, 0), -3, true, true); 
	World.spawnNPC(44, new WorldTile(3018, 4514, 0), -3, true, true); 
	World.spawnNPC(44, new WorldTile(3017, 4513, 0), -3, true, true); 
	World.spawnNPC(44, new WorldTile(3018, 4513, 0), -3, true, true); 
	

//donor dung shop		
	World.spawnNPC(9711, new WorldTile(2889, 5905, 0), 0, true, true);	
//dung shop
	World.spawnNPC(9711, new WorldTile(3450, 3738, 0), 0, true, true);		
//Barrelchest Boss	
	World.spawnNPC(5666, new WorldTile(1803, 4405, 3), -1, true, true);	

//Construction 
	World.spawnNPC(5867, new WorldTile(2638, 3291, 0), -1, true, true);//builder
	World.spawnNPC(14923, new WorldTile(2594, 3085, 0), -2, true, true); //Bank
	World.spawnNPC(8528, new WorldTile(3360, 5860, 0), -1, true, true);// nomad
//rocktails
	World.spawnNPC(8842, new WorldTile(3038, 4519, 0), -3, true, true); 
//monkfish
	World.spawnNPC(952, new WorldTile(3046, 4521, 0), -3, true, true); 
//shark,manta,turtle
	World.spawnNPC(952, new WorldTile(3046, 4521, 0), -3, true, true); 
//Irksol DZ shop	
	World.spawnNPC(566, new WorldTile(3023, 4502, 0), -3, true, true); //Donator Shop
//DZ super shop	
	World.spawnNPC(692, new WorldTile(3028, 4501, 0), -3, true, true); //Super Donator Shop
//Notices
	World.spawnNPC(6136, new WorldTile(3024, 4508, 0), -1, true, true);

//Glacors
	World.spawnNPC(14301, new WorldTile(4194, 5716, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4190, 5708, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4182, 5708, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4714, 5722, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4192, 5720, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4203, 5716, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4176, 5721, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4171, 5715, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4184, 5717, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4212, 5710, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(4212, 5718, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(14301, 5710, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(14301, 5710, 0), -1, true, true);
	World.spawnNPC(14301, new WorldTile(14301, 5710, 0), -1, true, true);
//Dunno
	World.spawnNPC(819, new WorldTile(3291, 4506, 0), -0, true, true);
	World.spawnNPC(2825, new WorldTile(2095, 3916, 0), -0, true, true);
//bankers
	World.spawnNPC(44, new WorldTile(3794, 2838, 0), 0, true, true); 
	World.spawnNPC(44, new WorldTile(3794, 2837, 0), 0, true, true); 
	World.spawnNPC(44, new WorldTile(3794, 2836, 0), 0, true, true); 
	World.spawnNPC(44, new WorldTile(3794, 2835, 0), 0, true, true); 
	World.spawnNPC(44, new WorldTile(3794, 2834, 0), 0, true, true); 
	World.spawnNPC(44, new WorldTile(3794, 2833, 0), 0, true, true);
//Revs2chrust
	World.spawnNPC(1413, new WorldTile(3084, 3933, 0), -1, true, true);
	World.spawnNPC(2262, new WorldTile(3165, 3461, 0), -1, true, true); //ge mage

	



//Objects




	
	
		//fish banks 782
	World.spawnObject(new WorldObject(2215, 10, 1, 2584, 3420, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 2584, 3421, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 2584, 3422, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 2584, 3423, 0), true);
	World.spawnObject(new WorldObject(2215, 10, 1, 2584, 3424, 0), true);
	//fire
	World.spawnObject(new WorldObject(2732, 10, 1, 2596, 3416, 0), true);
	
	//Cupboard	
	World.spawnObject(new WorldObject(12258, 10, 3, 3017, 4501, 0), true);

	//Furnace
	World.spawnObject(new WorldObject(4304, 10, 4, 3015, 4507, 0), true);
	
	//Anvil
	World.spawnObject(new WorldObject(2782, 10, 3, 3019, 4507, 0), true);
	World.spawnObject(new WorldObject(2782, 10, 3, 3019, 4509, 0), true);
	
	//Train wall
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4506, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4505, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4504, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4503, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4502, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4501, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4500, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4499, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4498, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4497, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4496, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4495, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4494, 0), true);
	World.spawnObject(new WorldObject(860, 10, 1, 3043, 4493, 0), true);
	
	World.spawnObject(new WorldObject(860, 10, 1, 3043, 4491, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4490, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4489, 0), true);
	World.spawnObject(new WorldObject(5373738, 10, 1, 3043, 4488, 0), true);
	
//Banks
	World.spawnObject(new WorldObject(782, 10, 1, 3019, 4517, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3019, 4516, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3019, 4515, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3019, 4514, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3019, 4513, 0), true);
	World.spawnObject(new WorldObject(782, 10, 0, 3018, 4512, 0), true);
	World.spawnObject(new WorldObject(782, 10, 0, 3017, 4512, 0), true);
	
//Fires
	World.spawnObject(new WorldObject(2732, 10, 0, 3035, 4523, 0), true);
	World.spawnObject(new WorldObject(2732, 10, 0, 3034, 4523, 0), true);
	
//Farming
	World.spawnObject(new WorldObject(8151, 10, 0, 3028, 4514, 0), true);//Herb Patch
	
//Minigame ogre games
	World.spawnObject(new WorldObject(4483, 10, 0, 2438, 9436, 2), true);//Chest
	World.spawnObject(new WorldObject(3222, 10, 1, 2442, 9433, 2), true);//doors
	World.spawnObject(new WorldObject(3222, 10, 1, 2441, 9433, 2), true);//doors
	
//Mining
	World.spawnObject(new WorldObject(11954, 10, 0, 3020, 4519, 0), true);//Herb Patch
	World.spawnObject(new WorldObject(14859, 10, 0, 3021, 4520, 0), true);//Herb Patch


//cage 
	World.spawnObject(new WorldObject(21869, 10, 0, 3079, 3486, 0), true);

//Bank at train
	World.spawnObject(new WorldObject(27663, 10, 0, 3042, 4491, 0), true);
//Entry statues
	World.spawnObject(new WorldObject(562, 10, 2, 3043, 4528, 0), true);
	World.spawnObject(new WorldObject(562, 10, 2, 3038, 4528, 0), true);
	
//Fountain
	World.spawnObject(new WorldObject(880, 10, 2, 3032, 4504, 0), true);
	
//Agility hole
	World.spawnObject(new WorldObject(2823, 10, 1, 3043, 4508, 0), true);//to
	World.spawnObject(new WorldObject(2823, 10, 1, 3057, 4508, 0), true);//back
	
	World.spawnObject(new WorldObject(42425, 10, 1, 3060, 4508, 0), true);//portal		

//Bar at Sz
	World.spawnObject(new WorldObject(885, 10, -1, 3295, 4506, 0), true);
	World.spawnObject(new WorldObject(885, 10, -1, 3294, 4506, 0), true);
	World.spawnObject(new WorldObject(885, 10, -1, 3293, 4506, 0), true);
	World.spawnObject(new WorldObject(885, 10, -1, 3292, 4506, 0), true);
	World.spawnObject(new WorldObject(2661, 10, -1, 3296, 4506, 0), true);
	World.spawnObject(new WorldObject(2661, 10, -1, 3296, 4505, 0), true);
	World.spawnObject(new WorldObject(2661, 10, -1, 3295, 4505, 0), true);
	World.spawnObject(new WorldObject(20607, 10, -1, 3299, 4509, 0), true);
//nex bank
	World.spawnObject(new WorldObject(20607, 10, -1, 2903, 5206, 0), true);
//max cape and hometall
//World.spawnObject(new WorldObject(12683, 10, -1, 3090, 3492, 0), true);
	

//Stryewyrms
	World.spawnObject(new WorldObject(1, 10, -1, 3335, 3161, 0), true);
	World.spawnObject(new WorldObject(1, 10, -1, 3335, 3162, 0), true);
	World.spawnObject(new WorldObject(1, 10, -1, 3335, 3163, 0), true);
	World.spawnObject(new WorldObject(1, 10, -1, 3391, 3164, 0), true);
	World.spawnObject(new WorldObject(1, 10, -1, 3391, 3163, 0), true);			

//Lucien
	World.spawnObject(new WorldObject(23, 10, 4, 2917, 5299, 1), true);
	World.spawnObject(new WorldObject(23, 10, 4, 2917, 5300, 1), true);
			

//Construction
	World.spawnObject(new WorldObject(20360, 10, 0, 2637, 3292, 0), true); //bench
	World.spawnObject(new WorldObject(13389, 10, 1, 2634, 3294, 0), true); //chest
	World.spawnObject(new WorldObject(52776, 10, 3, 2634, 3292, 0), true); //clockwork bench
	World.spawnObject(new WorldObject(13306, 10, 3, 2634, 3291, 0), true); //gilded bench
	World.spawnObject(new WorldObject(13306, 10, 1, 2641, 3291, 0), true); //gilded bench
	World.spawnObject(new WorldObject(3, 10, 0, 2640, 3291, 0), true); //remover
	World.spawnObject(new WorldObject(38130, 10, 0, 3162, 3463, 0), true);
	World.spawnObject(new WorldObject(38130, 10, 0, 3166, 3464, 0), true);
	World.spawnObject(new WorldObject(38130, 10, 0, 3190, 3492, 0), true);
	World.spawnObject(new WorldObject(38130, 10, 0, 3190, 3490, 0), true);
	
	
//Revs from PR	
//rev totems
	World.spawnObject(new WorldObject(2937, 10, 1, 3092, 3932, 0), true);
	World.spawnObject(new WorldObject(2937, 10, 1, 3092, 3935, 0), true);


//Rocks at DZlook
	World.spawnObject(new WorldObject(45678, 10, 0, 3027, 4532, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3027, 4534, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3027, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3029, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3031, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3033, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3035, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3037, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3039, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3041, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3043, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3045, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3047, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3049, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4536, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4535, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4534, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4532, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4530, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4534, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4534, 2), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3051, 4534, 2), true);
//Dzlook rocks end
	

//Safe PvP
	World.spawnObject(new WorldObject(45678, 10, 0, 3428, 2850, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3428, 2852, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3428, 2854, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3431, 2856, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3431, 2854, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3431, 2852, 1), true);
	World.spawnObject(new WorldObject(45678, 10, 0, 3431, 2850, 1), true);
//Safe end

//portals to cyclops
	World.spawnObject(new WorldObject(7352, 10, 1, 2847, 3537, 2), true);
	World.spawnObject(new WorldObject(7353, 10, 1, 2846, 3536, 2), true);
	
//Secret pipe game
	World.spawnObject(new WorldObject(871, 10, 1, 3077, 3486, 0), true);//pipe
	World.spawnObject(new WorldObject(1, 10, 1, 2313, 9813, 0), true);//crate
	World.spawnObject(new WorldObject(1, 10, 1, 2312, 9813, 0), true);//crate

	World.spawnObject(new WorldObject(2273, 10, 0, 2858, 5932, 0), true); //Kiln Portal

//smithing anvil
	World.spawnObject(new WorldObject(2782, 10, 0, 3108, 3498, 0), true);

//Poanizer cutscene test
   World.spawnNPC(5580, new WorldTile(3798, 2874, 0), 0, true, true);		
	//Lewis
	World.spawnNPC(11254, new WorldTile(3800, 2874, 0), 0, true, true);	
//Home Banks
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2838, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2837, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2836, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2835, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2834, 0), true);
	World.spawnObject(new WorldObject(782, 10, 1, 3795, 2833, 0), true);


	World.spawnObject(new WorldObject(1392, 10, 0, 2604, 4782, 0), true); //bush for bank deposit
	
	World.spawnObject(new WorldObject(782, 10, 3, 2601, 4774, 0), true); //bank
	
		
//bushes
		//east side
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4768, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4770, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4772, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4774, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4776, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4778, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2608, 4780, 0), true); //bush
		//south side
	World.spawnObject(new WorldObject(1392, 10, 0, 2601, 4767, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2603, 4767, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2605, 4767, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2607, 4767, 0), true); //bush
	World.spawnObject(new WorldObject(1392, 10, 0, 2601, 4781, 0), true); //bush
	 //bush on bank
	World.spawnObject(new WorldObject(1392, 10, 0, 2604, 4781, 0), true);
//Shops Bush end


//COMP CAPE POOL
	World.spawnObject(new WorldObject(2878, 10, 4, 2620, 3402, 0), true);

//Po boss lamps
	World.spawnObject(new WorldObject(3918, 10, 1, 2195, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2194, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2193, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2192, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2191, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2189, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2188, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2187, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2186, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2185, 3303, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2195, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2194, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2193, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2192, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2191, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2189, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2188, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2187, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2186, 3314, 0), true);
	World.spawnObject(new WorldObject(3918, 10, 1, 2185, 3314, 0), true);
	//before drop
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3314, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3313, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3312, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3311, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3310, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3309, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3308, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3307, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3306, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3305, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3304, 0), true);
	World.spawnObject(new WorldObject(2305, 10, 1, 2196, 3303, 0), true);
//po Boss end


//barrelchest static
	World.spawnObject(new WorldObject(29292, 10, 0, 1808, 4404, 3), true);
	World.spawnObject(new WorldObject(29292, 10, 0, 1808, 4405, 3), true);
	World.spawnObject(new WorldObject(29292, 10, 0, 1808, 4406, 3), true);


//start of runecraft
	World.spawnObject(new WorldObject(2478, 10, 0, 2459, 4820, 0), true); //Air
	World.spawnObject(new WorldObject(2479, 10, 0, 2454, 4824, 0), true); //Mind
	World.spawnObject(new WorldObject(2480, 10, 0, 2450, 4820, 0), true); //Water
	World.spawnObject(new WorldObject(2481, 10, 0, 2466, 4820, 0), true); //Earth
	World.spawnObject(new WorldObject(2482, 10, 0, 2475, 4824, 0), true); //Fire
	World.spawnObject(new WorldObject(2483, 10, 0, 2474, 4819, 0), true); //Body
	World.spawnObject(new WorldObject(2484, 10, 0, 2472, 4814, 0), true); //Cosmic
	World.spawnObject(new WorldObject(2485, 10, 0, 3185, 5711, 0), true); //Law
	World.spawnObject(new WorldObject(2486, 10, 0, 2456, 4814, 0), true); //Nature
	World.spawnObject(new WorldObject(2487, 10, 0, 2450, 4828, 0), true); //Chaos
	World.spawnObject(new WorldObject(2488, 10, 0, 2482, 4830, 0), true); //Death
	World.spawnObject(new WorldObject(17010, 10, 0, 2473, 4873, 0), true); //Astral
	// end of rune craft\
//mine bank
	World.spawnObject(new WorldObject(782, 10, 3, 3293, 3297, 0), true); //Bank at Mining
	
//Frost drag barriers
	World.spawnObject(new WorldObject(2905, 10, 0, 1311, 4508, 0), true);
	World.spawnObject(new WorldObject(2905, 10, 0, 1314, 4508, 0), true);
	World.spawnObject(new WorldObject(2905, 10, 0, 1313, 4508, 0), true);
	
	World.spawnObject(new WorldObject(2905, 10, 0, 1299, 4509, 0), true);
	World.spawnObject(new WorldObject(2905, 10, 0, 1298, 4509, 0), true);
	World.spawnObject(new WorldObject(2905, 10, 0, 1297, 4509, 0), true);
	
//Kalger trio walls
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2862, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2863, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2864, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2865, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2866, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2867, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2868, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2869, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2870, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2871, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2872, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2873, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2874, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2875, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2876, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2877, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2878, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2879, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5924, 0), true);
	//2
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5925, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5926, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5927, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2880, 5928, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2880, 5929, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5930, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5931, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5932, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5933, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5934, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5935, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2880, 5936, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2880, 5937, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5938, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2880, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2880, 5940, 0), true);
	//3
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5924, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5925, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5926, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5927, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2861, 5928, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2861, 5929, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5930, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5931, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5932, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5933, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5934, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5935, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2861, 5936, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2861, 5937, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5938, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2861, 5940, 0), true);
	//4
	World.spawnObject(new WorldObject(61566, 10, 1, 2879, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2878, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2877, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2876, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2875, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2874, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2873, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2872, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2871, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2870, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2869, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2868, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2867, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2866, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2865, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2864, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2863, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2862, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2861, 5939, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2860, 5939, 0), true);
	
//bank of kalger and devour
	World.spawnObject(new WorldObject(1, 10, 1, 2860, 5928, 0), true);
	World.spawnObject(new WorldObject(1, 10, 1, 2859, 5928, 0), true);
	World.spawnObject(new WorldObject(1, 10, 1, 2858, 5928, 0), true);
	World.spawnObject(new WorldObject(1, 10, 1, 2857, 5928, 0), true);
	World.spawnObject(new WorldObject(1, 10, 1, 2856, 5928, 0), true);
	World.spawnObject(new WorldObject(1, 10, 1, 2856, 5920, 0), true);
	World.spawnObject(new WorldObject(1, 10, 0, 2857, 5920, 0), true);
	World.spawnObject(new WorldObject(1, 10, 3, 2858, 5920, 0), true);
	World.spawnObject(new WorldObject(1, 10, 3, 2859, 5920, 0), true);
	World.spawnObject(new WorldObject(1, 10, 0, 2860, 5920, 0), true);
//Kiwi trio boss portal
	World.spawnObject(new WorldObject(30146, 10, 0, 2860, 5927, 0), true);
	
//donator dung
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5908, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2897, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2896, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2895, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2894, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2893, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2892, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2891, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2890, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2889, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5904, 0), true);
//2
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2897, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2896, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2895, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2894, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2893, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2892, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2891, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2890, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2889, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5916, 0), true);
//3	
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5915, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5914, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5913, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5912, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2888, 5911, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5910, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2888, 5909, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2888, 5907, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5906, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5905, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5908, 0), true);
//4
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5916, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5915, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5914, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5913, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5912, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 1, 2898, 5911, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2898, 5910, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2898, 5909, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 3, 2898, 5907, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2898, 5906, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2898, 5905, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2898, 5904, 0), true);
	World.spawnObject(new WorldObject(61566, 10, 0, 2888, 5908, 0), true);
		
//, lawn mows
 
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5923, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5922, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5921, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5920, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5919, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5918, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5917, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2861, 5916, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2861, 5915, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5914, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5913, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5912, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5911, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5910, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2861, 5909, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5908, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2861, 5907, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2861, 5906, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5905, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5904, 0), true);
  //2
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5923, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5922, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5921, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5920, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2880, 5919, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2880, 5918, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5917, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5916, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5915, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5914, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5913, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5912, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2880, 5911, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2880, 5910, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5909, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2880, 5908, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5907, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5906, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5905, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5904, 0), true);
  //3
  
  World.spawnObject(new WorldObject(782, 10, 1, 2860, 5925, 0), true);
  World.spawnObject(new WorldObject(782, 10, 1, 2860, 5924, 0), true);
  World.spawnObject(new WorldObject(782, 10, 1, 2860, 5923, 0), true);
  
  World.spawnObject(new WorldObject(1, 10, 1, 2880, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2879, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2878, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2877, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2876, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2875, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2874, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2873, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2872, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2871, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2870, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2869, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2868, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2867, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 1, 2866, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2865, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2864, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 3, 2863, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2862, 5904, 0), true);
  World.spawnObject(new WorldObject(1, 10, 0, 2861, 5904, 0), true);
		
		
		
		
		
		
		
		
		
//~~~~~~~~~~~~~~~~~~~~~~REMOVES~~~~~~~~~~~~~~~~~~~~~~~//\

//Shops
	World.removeObject(new WorldObject(15645, 10, 0, 2612, 4776, 0), true); //portal removal
	World.removeObject(new WorldObject(1392, 10, 0, 2612, 4776, 0), true); //bush
	World.removeObject(new WorldObject(1392, 10, 0, 2607, 4766, 0), true); //bush
	
//lader remove
	World.removeObject(new WorldObject(10193, 10, 0, 1798, 4406, 3), true);
		
//magic tree
	World.removeObject(new WorldObject(1306, 10, 0, 3029, 4509, 0), true);	
//well at edge
	World.removeObject(new WorldObject(26945, 10, 0, 3083, 3500, 0), true);
//RC fix
	World.removeObject(new WorldObject(1306, 10, 0, 3175, 5713, 0), true);
//RC fix end
	World.removeObject(new WorldObject(59686, 10, 0, 5886, 4696, 0), true);
	World.removeObject(new WorldObject(20058, 10, 0, 2546, 3114, 0), true);
	World.removeObject(new WorldObject(20058, 10, 0, 2548, 3116, 0), true);
	World.removeObject(new WorldObject(20058, 10, 0, 2545, 3116, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2544, 3117, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2544, 3116, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2546, 3115, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2547, 3115, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2547, 3116, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2547, 3117, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2549, 3116, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 2549, 3117, 0), true);
	World.removeObject(new WorldObject(20065, 10, 0, 5886, 4696, 0), true);
//Home G.e.
	World.removeObject(new WorldObject(1754, 10, 0, 2594, 3085, 0), true);
	World.removeObject(new WorldObject(1722, 10, 0, 2590, 3089, 0), true);
	World.removeObject(new WorldObject(202, 10, 0, 2592, 3089, 0), true);
	World.removeObject(new WorldObject(202, 10, 0, 2589, 3089, 0), true);
	World.removeObject(new WorldObject(202, 10, 0, 2593, 3083, 0), true);
	World.removeObject(new WorldObject(202, 10, 0, 2588, 3083, 0), true);
	World.removeObject(new WorldObject(1102, 10, 0, 2593, 3091, 0), true);
	World.removeObject(new WorldObject(604, 10, 0, 2593, 3092, 0), true);
	World.removeObject(new WorldObject(574, 10, 0, 2594, 3091, 0), true);
	World.removeObject(new WorldObject(604, 10, 0, 2586, 3085, 0), true);
	World.removeObject(new WorldObject(1102, 10, 0, 2587, 3084, 0), true);
	World.removeObject(new WorldObject(3080, 10, 0, 2591, 3082, 0), true);
	World.removeObject(new WorldObject(3080, 10, 0, 2585, 3082, 0), true);
	World.removeObject(new WorldObject(1158, 10, 0, 2592, 3093, 0), true);
	World.removeObject(new WorldObject(618, 10, 0, 2590, 3093, 0), true);
	World.removeObject(new WorldObject(172, 10, 0, 2609, 3090, 0), true);
	World.removeObject(new WorldObject(1158, 10, 0, 2589, 3093, 0), true);
//Home Bank
	World.removeObject(new WorldObject(591, 10, 0, 2611, 3096, 0), true);
	World.removeObject(new WorldObject(1088, 10, 0, 2611, 3095, 0), true);
	World.removeObject(new WorldObject(1158, 10, 0, 2609, 3096, 0), true);
	World.removeObject(new WorldObject(1088, 10, 0, 2612, 3097, 0), true);
	World.removeObject(new WorldObject(1088, 10, 0, 2611, 3097, 0), true);
	World.removeObject(new WorldObject(9398, 10, 0, 2611, 3088, 0), true);
	World.removeObject(new WorldObject(1158, 10, 0, 2609, 3089, 0), true);
	World.removeObject(new WorldObject(1394, 10, 0, 2606, 3088, 0), true);
//REVS
	World.removeObject(new WorldObject(2905, 10, 0, 3092, 3933, 0), true);
	World.removeObject(new WorldObject(2905, 10, 0, 3092, 3934, 0), true);
	


//weat field
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3815, 2850, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3805, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3815, 2851, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3805, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2852, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3805, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2853, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3815, 2853, 0), true);



//Exv2 Mowing
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2838, 0), true);
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2837, 0), true);
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2836, 0), true);
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2835, 0), true);
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2834, 0), true);
	World.removeObject(new WorldObject(8576, 10, 0, 3794, 2833, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3805, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3815, 2854, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3806, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3807, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3808, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3809, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3810, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3811, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3812, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3813, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3814, 2855, 0), true);
	World.removeObject(new WorldObject(22300, 10, 0, 3815, 2855, 0), true);	
	World.removeObject(new WorldObject(1161, 10, 0, 3805, 2838, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3805, 2836, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3805, 2834, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3806, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3806, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3806, 2837, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3807, 2838, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3807, 2834, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3808, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3808, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3809, 2838, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3809, 2836, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3809, 2834, 0), true);
	//YOU OWE ME RHYS >:C shiet.
	World.removeObject(new WorldObject(1161, 10, 0, 3810, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3810, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3810, 2837, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2838, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2836, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2834, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3811, 2837, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3812, 2837, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3812, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3812, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3813, 2834, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3813, 2838, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3814, 2837, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3814, 2835, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3814, 2833, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3815, 2834, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3815, 2836, 0), true);
	World.removeObject(new WorldObject(1161, 10, 0, 3815, 2838, 0), true);
	World.removeObject(new WorldObject(16043, 10, 0, 2992, 9688, 0), true);
	World.removeObject(new WorldObject(16043, 10, 0, 2993, 9688, 0), true);
	World.removeObject(new WorldObject(16043, 10, 0, 2994, 9688, 0), true);
	World.removeObject(new WorldObject(16043, 10, 0, 2995, 9688, 0), true);

		}

	/**
	 * The NPC classes.
	 */
	private static final Map<Integer, Class<?>> CUSTOM_NPCS = new HashMap<Integer, Class<?>>();

	public static void npcSpawn() {
		int size = 0;
		boolean ignore = false;
		try {
			for (String string : FileUtilities
					.readFile("data/npcs/npcspawns.txt")) {
				if (string.startsWith("//") || string.equals("")) {
					continue;
				}
				if (string.contains("/*")) {
					ignore = true;
					continue;
				}
				if (ignore) {
					if (string.contains("*/")) {
						ignore = false;
					}
					continue;
				}
				String[] spawn = string.split(" ");
				@SuppressWarnings("unused")
				int id = Integer.parseInt(spawn[0]), x = Integer
						.parseInt(spawn[1]), y = Integer.parseInt(spawn[2]), z = Integer
						.parseInt(spawn[3]), faceDir = Integer
						.parseInt(spawn[4]);
				NPC npc = null;
				Class<?> npcHandler = CUSTOM_NPCS.get(id);
				if (npcHandler == null) {
					npc = new NPC(id, new WorldTile(x, y, z), -1, true, false);
				} else {
					npc = (NPC) npcHandler.getConstructor(int.class)
							.newInstance(id);
				}
				if (npc != null) {
					WorldTile spawnLoc = new WorldTile(x, y, z);
					npc.setLocation(spawnLoc);
					World.spawnNPC(npc.getId(), spawnLoc, -1, true, false);
					size++;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		System.err.println("Loaded " + size + " custom npc spawns!");
	}

}


		
		

//~~~~~~~~~~~~~~~~~~~~~~ UnUsed Homes etc ~~~~~~~~~~~~~~~~~~~~~~~//
	/*
	
	
	
	/*
		World.spawnObject(new WorldObject(27277, 10, 0, 1807, 4407, 3), true);
		World.spawnObject(new WorldObject(27277, 10, 0, 1807, 4406, 3), true);
		World.spawnObject(new WorldObject(27277, 10, 0, 1807, 4405, 3), true);
		World.spawnObject(new WorldObject(27277, 10, 0, 1807, 4404, 3), true);
		World.spawnObject(new WorldObject(27277, 10, 0, 1807, 4403, 3), true);
		World.spawnObject(new WorldObject(29292, 10, 0, 1807, 4407, 3), true);
		World.spawnObject(new WorldObject(29292, 10, 0, 1807, 4406, 3), true);
		World.spawnObject(new WorldObject(29292, 10, 0, 1807, 4405, 3), true);
		World.spawnObject(new WorldObject(29292, 10, 0, 1807, 4404, 3), true);
		World.spawnObject(new WorldObject(29292, 10, 0, 1807, 4403, 3), true);*/
		
//Unused
	
		 
	//World.spawnObject(new WorldObject(27254, 10, 0, 2611, 3095, 0), true);//Edge portal
	//World.spawnObject(new WorldObject(4388, 10, 0, 3037, 4503, 0), true); //party demon
	//World.spawnObject(new WorldObject(36972, 10, 2, 3083, 3506, 0), true); //Prayer Alter
	//World.spawnObject(new WorldObject(4390, 10, 0, 3420, 3168, 0), true); // Red Portal Exit
	//World.spawnObject(new WorldObject(47173, 10, 0, 2589, 3086, 0), true);//ge Home
	/*World.spawnObject(new WorldObject(6552, 10, 3, 3090, 3497, 0), true); //Ancients
	World.spawnObject(new WorldObject(47120, 10, 0, 3082, 3499, 0), true); //zaros
	World.spawnObject(new WorldObject(28698, 10, 0, 3100, 3494, 0), true); //Lunar
	World.spawnObject(new WorldObject(55301, 10, 2, 3090, 3493, 0), true); //PkRanks*/
	
		


//old edge shops
		/*
		World.spawnNPC(6537, new WorldTile(3082, 3484, 0), 0, true, true); //Special item shop
		World.spawnNPC(2830, new WorldTile(3090, 3475, 0), 0, true, true); //melee wepons
		World.spawnNPC(211, new WorldTile(3097, 3475, 0), 0, true, true);//melee armour
		World.spawnNPC(1694, new WorldTile(3090, 3481, 0), 0, true, true);//range weapon
		
		World.spawnNPC(346, new WorldTile(3090, 3480, 0), 0, true, true);//range armour
		World.spawnNPC(524, new WorldTile(3097, 3480, 0), 0, true, true); //mage gear
		World.spawnNPC(546, new WorldTile(3097, 3481, 0), 0, true, true); //Mmagic supplies
		World.spawnNPC(6971, new WorldTile(3089, 3484, 0), 0, true, true);//summoning supplies
		
		World.spawnNPC(454, new WorldTile(3090, 3478, 0), 0, true, true);//food n pots
		World.spawnNPC(4555, new WorldTile(3088, 3484, 0), 0, true, true); //Fog Shop
		World.spawnNPC(210, new WorldTile(3087, 3484, 0), 0, true, true); //pure acces
		World.spawnNPC(576, new WorldTile(3086, 3484, 0), 0, true, true); //skill shop
		World.spawnNPC(519, new WorldTile(3085, 3484, 0), 0, true, true); //herb shop
		World.spawnNPC(544, new WorldTile(3097, 3478, 0), 0, true, true); //skilcape shop
		World.spawnNPC(523, new WorldTile(3084, 3484, 0), 0, true, true); //sell to me
		World.spawnNPC(8461, new WorldTile(3083, 3488, 0), 0, true, true);	//turadel
				
		World.spawnNPC(2676, new WorldTile(3082, 3485, 0), 0, true, true); //makeover mage
		World.spawnNPC(1039, new WorldTile(3082, 3486, 0), 0, true, true); //male clothes
		World.spawnNPC(285, new WorldTile(3082, 3487, 0), 0, true, true);	//female clothes	
		World.spawnNPC(945, new WorldTile(3090, 3494, 0), 0, true, true);
		*/
		




//Old home.	
    //
	//World.spawnObject(new WorldObject(38698, 10, 4, 2862, 3537, 0), true);
	//World.spawnObject(new WorldObject(38699, 10, 4, 2862, 3547, 0), true);
	//World.spawnNPC(566, new WorldTile(2851, 3549, 0), 0, true, true);//ikor
	//World.spawnNPC(6893, new WorldTile(2846, 3546, 0), 0, true, true);//PETSHOP
	//World.spawnNPC(692, new WorldTile(2840, 3545, 0), 0, true, true);//TRIBAL
	//old stuff on harmony island
	/*
		//Crystal chests
		World.spawnObject(new WorldObject(172, 10, 4, 3805, 2856, 0), true);
		//banks
		World.spawnObject(new WorldObject(4483, 10, 1, 3804, 2854, 0), true);
		World.spawnObject(new WorldObject(4483, 10, 1, 3804, 2851, 0), true);
		//shops
		World.spawnNPC(220, new WorldTile(3795, 2839, 0), 0, true, true);
		World.spawnNPC(6537, new WorldTile(3805, 2855, 0), 0, true, true); //Special item shop
		World.spawnNPC(2830, new WorldTile(3806, 2855, 0), 0, true, true); //melee wepons
		World.spawnNPC(211, new WorldTile(3807, 2855, 0), 0, true, true);//melee armour
		World.spawnNPC(1694, new WorldTile(3808, 2855, 0), 0, true, true);//range weapon
		World.spawnNPC(346, new WorldTile(3809, 2855, 0), 0, true, true);//range armour
		World.spawnNPC(524, new WorldTile(3810, 2855, 0), 0, true, true); //mage gear
		World.spawnNPC(546, new WorldTile(3097, 3481, 0), 0, true, true); //Mmagic supplies
		World.spawnNPC(6971, new WorldTile(3812, 2855, 0), 0, true, true);//summoning supplies
		World.spawnNPC(454, new WorldTile(3813, 2855, 0), 0, true, true);//food n pots
		World.spawnNPC(4555, new WorldTile(3814, 2855, 0), 0, true, true); //Fog Shop
		World.spawnNPC(210, new WorldTile(3815, 2855, 0), 0, true, true); //pure acces
		World.spawnNPC(3709, new WorldTile(3051, 9675, 0), 0, true, true); //Mr Ex
		World.spawnNPC(3374, new WorldTile(3085, 3493, 0), 0, true, true); //Max
		World.spawnNPC(15582, new WorldTile(3090, 3484, 0), 0, true, true); //Prestige
		World.spawnNPC(576, new WorldTile(3808, 2850, 0), 0, true, true); //skill shop
		World.spawnNPC(519, new WorldTile(3809, 2850, 0), 0, true, true); //herb shop
		World.spawnNPC(544, new WorldTile(3810, 2850, 0), 0, true, true); //skilcape shop
		World.spawnNPC(523, new WorldTile(3811, 2850, 0), 0, true, true); //sell to me
		World.spawnNPC(8461, new WorldTile(3812, 2850, 0), 0, true, true);	//turadel
		//World.spawnNPC(2676, new WorldTile(2995, 9675, 0), 0, true, true); //makeover mage
		//World.spawnNPC(544, new WorldTile(3815, 2852, 0), 0, true, true); //Hair
		World.spawnNPC(1039, new WorldTile(3815, 2853, 0), 0, true, true); //male clothes
		World.spawnNPC(4518, new WorldTile(3815, 2854, 0), 0, true, true);	//female clothes	
		//summoming obelisk
		World.spawnObject(new WorldObject(50205, 10, 0, 3796, 2839, 0), true);
		//Lucky item exchange
		World.spawnObject(new WorldObject(17248, 10, 3, 3082, 3489, 0), true);	
		//Guilded altar
		World.spawnObject(new WorldObject(409, 10, 3, 3805, 2846, 0), true);
		//clan plaque
		World.spawnObject(new WorldObject(55301, 10, 2, 3805, 2840, 0), true);
//harmony home end	
		
		
		World.spawnNPC(534, new WorldTile(3091, 3492, 0), -3, true, true); //range shop
		//World.spawnNPC(531, new WorldTile(3091, 3493, 0), -3, true, true); //Mage Robe Shop
		World.spawnNPC(576, new WorldTile(3806, 2855, 0), 0, true, true);
		World.spawnNPC(4555, new WorldTile(3810, 2855, 0), 0, true, true);
	
		World.spawnNPC(2676, new WorldTile(3813, 2855, 0), 0, true, true);
		World.spawnNPC(3709, new WorldTile(3805, 2850, 0), 0, true, true);
		World.spawnNPC(3374, new WorldTile(3806, 2850, 0), 0, true, true);
		World.spawnNPC(15582, new WorldTile(3807, 2850, 0), 0, true, true);
		World.spawnNPC(524, new WorldTile(3814, 2855, 0), 0, true, true);
	


		World.spawnNPC(2830, new WorldTile(3813, 2850, 0), 0, true, true);
		
	*/		
		
		
		
