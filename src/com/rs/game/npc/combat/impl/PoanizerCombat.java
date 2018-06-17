package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.ForceTalk;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.utils.Utils;

public class PoanizerCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 696969699 };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int distanceX = target.getX() - npc.getX();
		int distanceY = target.getY() - npc.getY();
		boolean distant = false;
		int size = npc.getSize();
		Familiar familiar = (Familiar) npc;
		boolean usingSpecial = familiar.hasSpecialOn();
		int damage = 0;
		if (distanceX > size || distanceX < -1 || distanceY > size
				|| distanceY < -1)
			distant = true;
		if (usingSpecial) {// priority over regular attack
			npc.setNextAnimation(new Animation(14221));
				npc.setNextForceTalk(new ForceTalk("KAAAAAAAAAAAAAAMEEEEEEEEEEEEEHAAAAAAAAMEEEEEEEEEHAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!"));
				delayHit(npc,3,target,getRangeHit(npc,getRandomMaxHit(npc, 200, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,4,target,getRangeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,5,target,getRangeHit(npc,getRandomMaxHit(npc, 400, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,6,target,getRangeHit(npc,getRandomMaxHit(npc,  1000,NPCCombatDefinitions.RANGE, target)));
				npc.setNextGraphics(new Graphics(2407 ));
			if (distant) {// range hit
				npc.setNextForceTalk(new ForceTalk("Stop Farcasting Ked. >:C"));
				
				
			} else {// RANGE hit
				npc.setNextAnimation(new Animation(2661));
				delayHit(
							npc,
							0,
							target,
							getMeleeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit(),
											NPCCombatDefinitions.MELEE, target)));
										
			}
		} 
		return defs.getAttackDelay();
	}
}
