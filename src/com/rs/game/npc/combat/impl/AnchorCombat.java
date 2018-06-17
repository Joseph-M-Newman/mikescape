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


public class AnchorCombat extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { 5666 };
	}
	

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
		switch (Utils.getRandom(3)) {
		case 0:
			
		if (Utils.getRandom(20) >= 2){
			npc.setNextAnimation(new Animation(5895));//ultimate
			npc.setNextGraphics(new Graphics(2929));
			delayHit(npc,2,target,getMeleeHit(npc,getRandomMaxHit(npc, 900, NPCCombatDefinitions.MELEE, target)));
				}
		else {	
			npc.setNextAnimation(new Animation(-1));//normal smack
			npc.setNextAnimation(new Animation(5894));//slash down hit floor.
			delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.MELEE, target)));
			}
			break;
		case 1:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(5896));
			delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, 400, NPCCombatDefinitions.MELEE, target)));
			
			
			if (Utils.getRandom(2) == 1){
				boolean stun = false;
						if (stun == false){
							target.setNextGraphics(new Graphics(80, 5, 60));
								if (!target.addWalkSteps(target.getX() - npc.getX() + target.getX(), target.getY() - npc.getY() + target.getY(), 1))
									//npc.setNextFaceEntity(target);
									//target.setNextFaceEntity(npc);
										WorldTasksManager.schedule(new WorldTask() {
												@Override
												public void run() {
													target.setNextFaceEntity(null);
													npc.setNextFaceEntity(null);
												}
											});
							 stun = true; 
							 npc.getCombat().addCombatDelay(3);
							 }
				}
				break;
		case 2:
			
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(5897));
			npc.setNextGraphics(new Graphics(2325));
				int hit = Utils.getRandom(3);
				if (hit == 0){
						target.applyHit(new Hit(target, 100,HitLook.REFLECTED_DAMAGE));;
						npc.heal(100);
						if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(3000);}
					
						}
				if (hit == 1){
						target.applyHit(new Hit(target, 150,HitLook.REFLECTED_DAMAGE));;
						npc.heal(75);
						if (target instanceof Player) {
							Player p2 = (Player) target;
							p2.setPrayerDelay(3000);}
						}
				if (hit == 2){
						target.applyHit(new Hit(target, 250,HitLook.REFLECTED_DAMAGE));;
						npc.heal(50);
						}
				if (hit == 3){
						target.applyHit(new Hit(target, 300,HitLook.REFLECTED_DAMAGE));;
						npc.heal(25);
						}
				break;
			}
		return defs.getAttackDelay();
	}
	

	
	
}
