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


public class Colin extends CombatScript {


@Override
	public Object[] getKeys() {
		return new Object[] { 2255 };
	}

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	


		
		
		switch (Utils.getRandom(1)) {
		case 0:
			getMeleeHit(npc,getRandomMaxHit(npc, 750 , NPCCombatDefinitions.MELEE, target));			
			npc.setNextForceTalk(new ForceTalk("GET IN DEES SHEETS"));
			break;
		case 1:
		getMagicHit(npc,getRandomMaxHit(npc, 710 , NPCCombatDefinitions.MAGE, target));			
			npc.setNextForceTalk(new ForceTalk("GTFO KID"));
			
		break;
		
		}
		

		
		return defs.getAttackDelay();
	}
	
	
	
	
}


/*		
		Some stuff that may be handy to use. Add your selecter thingy.... Npc/Target etc.
		The stuff in astrixes * * is what you need to change..
		
		
		
	.getHitpoints()
	.setNextForceTalk(new ForceTalk("*Message*"));
	.setNextAnimation(new Animation(*Emote id*));
	.setNextGraphics(new Graphics(*Gfx id*));
	.applyHit(new Hit(target, *Damage* ,HitLook.REGULAR_DAMAGE));	
	.applyHit(new Hit(npc, (int) (target.getHitpoints() *math function* *number*), HitLook.REGULAR_DAMAGE, 0));
	
	get*Attack Style*Hit(npc,getRandomMaxHit(npc, *damage*, NPCCombatDefinitions.*Attack style*, target)));			
	npc.getCombat().addCombatDelay(5);
	
	
	
	Look through the NPC.java file for any other functions you could use. They are near the bottom.
		*/
