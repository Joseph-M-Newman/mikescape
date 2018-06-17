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


public class MateyCombat extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { 3196 };
	}
	

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
		switch (Utils.getRandom(3)) {
		case 0:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(2067));//slash down hit floor.
			delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, 200, NPCCombatDefinitions.MELEE, target)));
			break;
		case 1:
			npc.setNextAnimation(new Animation(-1));
			npc.setNextAnimation(new Animation(7050));
			npc.applyHit(new Hit(target, 50,HitLook.REGULAR_DAMAGE));
			target.applyHit(new Hit(target, 100,HitLook.REFLECTED_DAMAGE));;
			break;
		case 2:
			npc.setNextForceTalk(new ForceTalk("Ooh! my arm!"));
			npc.setNextAnimation(new Animation(799));
			target.applyHit(new Hit(target, 150,HitLook.REGULAR_DAMAGE));
			npc.applyHit(new Hit(npc, (int) (target.getHitpoints() / 90), HitLook.DESEASE_DAMAGE, 0));
			break;
		case 3:
			
			if (Utils.getRandom(10) <= 5){
			
			npc.setNextAnimation(new Animation(1060));
			delayHit(npc,2,target,getMeleeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.MELEE, target)));
			}
			
			else{
			
			npc.setNextAnimation(new Animation(451));
			npc.setNextForceTalk(new ForceTalk("Let me just get something."));
			
			}
			
			
			
			
			break;
			}
		return defs.getAttackDelay();
	}
	

	
	
}
