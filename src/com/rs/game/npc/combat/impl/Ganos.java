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
//import com.rs.game.player.Player.useStairs;
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
import com.rs.game.ForceMovement;

public class Ganos extends CombatScript {


@Override
	public Object[] getKeys() {
		return new Object[] { 14696 };
	}

	

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	


		int attackz = Utils.getRandom(30);
		
		switch (Utils.getRandom(1)) {
		case 0:
		if (attackz >= 25){
				delayHit(npc,0,target,getMagicHit(npc,getRandomMaxHit(npc, 500,NPCCombatDefinitions.MAGE, target)));
				npc.setNextAnimation(new Animation(15470));
		}	
		else{
				delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, 300,NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(15466));
				}
		break;
		
		case 1:
		
		delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, 550,NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(15465));
				
		
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
