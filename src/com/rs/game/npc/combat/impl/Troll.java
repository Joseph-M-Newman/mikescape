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


public class Troll extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { 16000 };
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
			
			switch (Utils.getRandom(3)) {
		case 0:
		npc.setNextAnimation(new Animation(1979));
		World.sendProjectile(npc, target, 362, 18, 18, 50, 50, 0, 0);
		npc.setNextForceTalk(new ForceTalk("Gf Bank."));
		target.setNextGraphics(new Graphics(369));
		delayHit(npc,2,target,getMagicHit(npc,getRandomMaxHit(npc, 1, NPCCombatDefinitions.MAGE, target)));
						
			break;
			
		case 1:
			
		npc.setNextAnimation(new Animation(1979));
		World.sendProjectile(npc, target, 362, 18, 18, 50, 50, 0, 0);
		target.setNextGraphics(new Graphics(1677));	
		delayHit(npc,2,target,getMagicHit(npc,getRandomMaxHit(npc, 1, NPCCombatDefinitions.MAGE, target)));
						
			break;
		
		case 2:
			npc.setNextAnimation(new Animation(1979));
		World.sendProjectile(npc, target, 362, 18, 18, 50, 50, 0, 0);
		target.setNextGraphics(new Graphics(1677));
		delayHit(npc,2,target,getMagicHit(npc,getRandomMaxHit(npc, 1, NPCCombatDefinitions.MAGE, target)));
						
			break;
			
			
		case 3:
				npc.setNextAnimation(new Animation(1979));
		World.sendProjectile(npc, target, 362, 18, 18, 50, 50, 0, 0);
		target.setNextGraphics(new Graphics(1677));
		npc.setNextForceTalk(new ForceTalk("Run kid"));
		delayHit(npc,2,target,getMagicHit(npc,getRandomMaxHit(npc, 1, NPCCombatDefinitions.MAGE, target)));
						
			break;
			}
			
			
		
		
		
			
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



