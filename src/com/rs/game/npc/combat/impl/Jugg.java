package com.rs.game.npc.combat.impl;


import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;
import com.rs.game.player.Appearence;
import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Jugg extends CombatScript {

boolean rage = false;
int spec = 4;

	@Override
	public Object[] getKeys() {
		return new Object[] { 17176 };//the juggernaut 138 full melee brute.
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
							//npc.setNextForceTalk(new ForceTalk("Very well, you think you can beat me?"));
							//npc.setNextAnimation(new Animation(14224));  
							//npc.setNextGraphics(new Graphics(2015));
							//intro = true;
							//return defs.getAttackDelay();
							//low hp
							//npc.getHitpoints() <= 1000)
							//npc.setNextForceTalk(new ForceTalk("0?"));
							//npc.transformIntoNPC(1688);	
								
			
			//413 - spec alrernative?
			
//RAGE TRUE			
		
			if(npc.getHitpoints() <= 400){
			rage = true;
			npc.setNextAnimation(new Animation(361));
			npc.setNextGraphics(new Graphics(565 ));
			npc.setNextForceTalk(new ForceTalk("Grrr! I will not lose to you!"));
							
			}
			
		
		
			switch (Utils.getRandom(3)) {
		case 0:
				//spec
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,3,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit()),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
					 
					npc.setNextGraphics(new Graphics(823 ));
					npc.setNextAnimation(new Animation(4959));  
				}
				else{
						//turns of prayer
						if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
						if(p2.getSkills().getLevel(2) < 99){//str
							p2.setFreezeDelay(60);
							npc.setNextForceTalk(new ForceTalk("Come back when your ready to fight me!"));
			
							p2.applyHit(new Hit(target, 50,HitLook.REGULAR_DAMAGE));
							p2.setNextForceTalk(new ForceTalk("oof!"));
							
							p2.setNextAnimation(new Animation(837));  //bounce
						}	
						}
						//if killer isnt 99 str wacks to floor for extra damage
						
						//normal hit
						npc.setNextAnimation(new Animation(14307)); 
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						

				}	
		break;
			
		case 1:
				//spec
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,3,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit()),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
					 
					npc.setNextGraphics(new Graphics(823 ));
					npc.setNextAnimation(new Animation(4959));  
				}
				else{
				//turns of prayer
						if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
							p2.setFreezeDelay(60);
							
						if(p2.getSkills().getLevel(1) < 99){//def
						npc.setNextForceTalk(new ForceTalk("Come back when your ready to fight me!"));
			
						p2.applyHit(new Hit(target, 50,HitLook.REGULAR_DAMAGE));
						p2.setNextForceTalk(new ForceTalk("Ack!"));
						p2.setNextAnimation(new Animation(2242));  //bounce
					}
						}
					
						
						npc.setNextAnimation(new Animation(14393));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						//npc.setNextForceTalk(new ForceTalk("hit 1"));

				}	
		break;
		
		case 2:
		
			//spec
				if((target.getHitpoints() <= 400) || (npc.getHitpoints() <= 550) && (spec > 0)){
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,3,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit()),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
					 
					npc.setNextGraphics(new Graphics(823 ));
					npc.setNextAnimation(new Animation(4959));  
				}
				else{
					
						npc.setNextAnimation(new Animation(3678));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /2 ),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /2 ),NPCCombatDefinitions.MELEE, target)));
						//npc.setNextForceTalk(new ForceTalk("hit 2"));

				}	
											
		break;
			
			
		case 3:
				//spec
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() /3),NPCCombatDefinitions.MELEE, target)));
						
						delayHit(npc,3,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit()),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
					 
					npc.setNextGraphics(new Graphics(823 ));
					npc.setNextAnimation(new Animation(4959));  
				}
				else{
						
						npc.setNextAnimation(new Animation(400));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						
						//npc.setNextForceTalk(new ForceTalk("hit 3"));
				}	
		break;
			}//end of switch
			
			
		
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



