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


public class KkCombat extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { "Kalphite King" };
	}
	

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	npc.setCapDamage(750);


		
		
		switch (Utils.getRandom(5)) {
		case 0:
		if(Utils.getRandom(0) == 0){
			
			npc.setNextAnimation(new Animation(6227));
			npc.setNextForceTalk(new ForceTalk("Meeeeeek!!!!"));
			if (target instanceof Player) {
				Player p2 = (Player) target;
				p2.getPackets().sendGameMessage( "You can tune a piano but you cant tune a fish.....");
			}
						
			target.setNextGraphics(new Graphics(2600 ));
			WorldTasksManager.schedule(new WorldTask() {
	
				@Override
				public void run() {
					for (Entity t : npc.getPossibleTargets()) {
						delayHit(npc,1,t,getMagicHit(npc,getRandomMaxHit(npc, 1000, NPCCombatDefinitions.MAGE, target)));
						
					}
		
				}

			}, 4);
			return 0;
				}
				
				
				
			break;
		case 1:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(6223));
			npc.setNextForceTalk(new ForceTalk("I Am Kiwi #Yolo"));
			getMeleeHit(npc,getRandomMaxHit(npc, 500,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 400,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 300,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 250,NPCCombatDefinitions.MELEE, target));
			getMeleeHit(npc,getRandomMaxHit(npc, 200,NPCCombatDefinitions.MELEE, target));
			break;
		case 2:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(6227));
			npc.setNextGraphics(new Graphics(2102)); 
			npc.setNextForceTalk(new ForceTalk("Rawr! Change my Cb script..."));
			delayHit(npc,2,target,getRangeHit(npc,getRandomMaxHit(npc, 250,NPCCombatDefinitions.RANGE, target)),
						getRangeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target)),
						getRangeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target)),
						getRangeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target)));
			npc.getCombat().addCombatDelay(3);
			break;
		case 3:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(6223));
			npc.setNextForceTalk(new ForceTalk("......."));
				target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 50), HitLook.REGULAR_DAMAGE, 0));
				npc.getCombat().addCombatDelay(5);
			break;
		case 4:
			npc.setNextForceTalk(new ForceTalk("While I'm a bug I still speak fluent English."));
			npc.setNextAnimation(new Animation(6223));
			
			target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 25), HitLook.REGULAR_DAMAGE, 0));
			target.applyHit(new Hit(npc, (int) (target.getHitpoints() / 10), HitLook.REGULAR_DAMAGE, 0));
			break;
		case 5:
			npc.setNextAnimation(new Animation(6223));
			npc.setNextForceTalk(new ForceTalk("What's the difference between a Piano and King Pvp?"));
			
			if (target instanceof Player) {
				Player p2 = (Player) target;
				p2.getPackets().sendGameMessage( "You can tune a piano but you cant tune a fish.....");
			}
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			npc.setNextGraphics(new Graphics(2656));
			
			break;
		}
		

		return defs.getAttackDelay();
	}
	

	
}
