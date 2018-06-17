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


public class KiwiCombat extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { "Leon d'Cour" };
	}
	

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	


		
		
		switch (Utils.getRandom(5)) {
		case 0:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(10248));
			npc.setNextForceTalk(new ForceTalk("KIWIWIWIWIWIIWIWIWIWIW!!!!"));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			break;
		case 1:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(1060));
			npc.setNextForceTalk(new ForceTalk("Young Canis..."));
			if (target instanceof Player) {
					Player p2 = (Player) target;
					p2.setPrayerDelay(5000);}
			npc.getCombat().addCombatDelay(2);
			break;
		case 2:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(10737));
			npc.setNextGraphics(new Graphics(2102));
			npc.setNextForceTalk(new ForceTalk("Lewis is Newb!"));
			delayHit(
						npc,
						2,
						target,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 250,
										NPCCombatDefinitions.RANGE, target)),
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 250,
										NPCCombatDefinitions.RANGE, target)),
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 250,
										NPCCombatDefinitions.RANGE, target)),
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 100,
										NPCCombatDefinitions.RANGE, target)));
			npc.getCombat().addCombatDelay(3);
			break;
		case 3:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(10501));
			npc.setNextForceTalk(new ForceTalk("I am noob"));
				target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 50), HitLook.REGULAR_DAMAGE, 0));
				npc.getCombat().addCombatDelay(5);
			break;
		case 4:
			npc.setNextForceTalk(new ForceTalk("LOLnoLOLnoLOLnoLOLnoLOLnoLOLnoLOLnoLOLnoLOLno"));
			npc.setNextAnimation(new Animation(10499));
			
			target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 25), HitLook.REGULAR_DAMAGE, 0));
			target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 10), HitLook.REGULAR_DAMAGE, 0));
			break;
		case 5:
			npc.setNextAnimation(new Animation(10468));
			npc.setNextForceTalk(new ForceTalk("I WILL DESTROY YOU!"));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REGULAR_DAMAGE));
			npc.setNextGraphics(new Graphics(2656));
			
			break;
		}
		
/*		
		if(npc.getHitpoints() => 10){
		

		npc.setNextForceTalk(new ForceTalk("Fuck..."));
		npc.setNextAnimation(new Animation(10719));
		}
			
if(target.getHitpoints() => 5){
			
		npc.setNextForceTalk(new ForceTalk("GF Noob, You got PWNED!"));
		npc.setNextAnimation(new Animation(2304));
		}
		*/
		
		return defs.getAttackDelay();
	}
	
    public void sendDeath(final NPC npc, final Entity target) {
		                              
        WorldTasksManager.schedule(new WorldTask() {

            int loop;

            @Override
            public void run() {
                if (loop == 0) {
					npc.setNextForceTalk(new ForceTalk("I cant believe I lost?!"));
					npc.setNextAnimation(new Animation(10719));
                } if (loop == 1)  { 
                   
					target.setNextAnimation(new Animation(10738));
                    
                  
                }
                loop++;
            }
        }, 0, 1);
    
	
	
	
	
	}
	
	
}
