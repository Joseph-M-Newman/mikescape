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


public class EliteBK extends CombatScript {


int spec = 4;

	@Override
	public Object[] getKeys() {
		return new Object[] { 8327 };//pray mage
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
			
			
			switch (Utils.getRandom(3)) {
		case 0:
		if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
				}
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						target.setNextGraphics(new Graphics(982 ));
						npc.setNextForceTalk(new ForceTalk("Die!"));
						npc.setNextAnimation(new Animation(10502));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() * 2),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
				}
				else{
						npc.setNextAnimation(new Animation(407));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						//npc.setNextForceTalk(new ForceTalk("hit 0"));

				}	
		break;
			
		case 1:
			if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
				}
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						target.setNextGraphics(new Graphics(982 ));
						npc.setNextForceTalk(new ForceTalk("Die!"));
						npc.setNextAnimation(new Animation(10502));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() * 2),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
				}
				else{
						npc.setNextAnimation(new Animation(385));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						//npc.setNextForceTalk(new ForceTalk("hit 1"));

				}	
		break;
		
		case 2:
		if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
				}
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						target.setNextGraphics(new Graphics(982 ));
						npc.setNextForceTalk(new ForceTalk("Die!"));
						npc.setNextAnimation(new Animation(10502));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() * 2),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
				}
				else{
						npc.setNextAnimation(new Animation(410));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						//npc.setNextForceTalk(new ForceTalk("hit 2"));

				}	
											
		break;
			
			
		case 3:
				if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(10000);
				}
				if((target.getHitpoints() <= 300) || (npc.getHitpoints() <= 550) && (spec > 0)){
						target.setNextGraphics(new Graphics(982 ));
						npc.setNextForceTalk(new ForceTalk("Die!"));
						npc.setNextAnimation(new Animation(10502));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, (defs.getMaxHit() * 2),NPCCombatDefinitions.MELEE, target)));
					spec -=1;
				}
				else{
						npc.setNextAnimation(new Animation(400));  
						delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
						
						//npc.setNextForceTalk(new ForceTalk("hit 3"));
				}	
		break;
			}
			
			
		
		
		
			
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



