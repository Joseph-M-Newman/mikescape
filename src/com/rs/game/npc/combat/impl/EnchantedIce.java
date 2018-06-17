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

public class EnchantedIce extends CombatScript {

  

	@Override
	public Object[] getKeys() {
		return new Object[] { 14257 }; //813
	}
	
	
	


	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int damage = 200;
		switch (Utils.getRandom(4)) {
			
			
				case 0:
					npc.setNextAnimation(new Animation(8183));
					delayHit(npc,4,target,getMeleeHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MELEE, target)));
					
					break;
				
				case 1:
					npc.setNextAnimation(new Animation(8185));
					delayHit(npc,4,target,getMagicHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MAGE, target)));
					break;
					
				case 2:
					delayHit(npc, 1, target, getMeleeHit(npc, damage));
					delayHit(npc, 2, target, getMagicHit(npc, damage));	

					npc.setNextAnimation(new Animation(8190));
					break;
					
				case 3:
					if (Utils.getRandom(9) == 1){// 1/10 chance of happening
					
					
						npc.setNextForceTalk(new ForceTalk("Sharpen up!"));
						npc.setNextAnimation(new Animation(8168));
						npc.setNextGraphics(new Graphics(94));
						damage += 100;
					}
					break;
				
				case 4:
				
					npc.setNextForceTalk(new ForceTalk("Feel the Freeze!"));
					npc.setNextAnimation(new Animation(8185));
					target.setNextGraphics(new Graphics(606));
					delayHit(npc,3,target,getMagicHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MAGE, target)));
					delayHit(npc,4,target,getMagicHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MAGE, target)));
					delayHit(npc,5,target,getMagicHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MAGE, target)));
					delayHit(npc,6,target,getMagicHit(npc,getRandomMaxHit(npc, damage, NPCCombatDefinitions.MAGE, target)));
					break;
					
		}
	return defs.getAttackDelay();
}

}