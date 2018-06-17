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


public class PkerDDS extends CombatScript {


boolean change = false;
//max hit is roughly 470


	@Override
	public Object[] getKeys() {
		return new Object[] { 17178 };//dds pker
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	Player p2 = (Player) target;
	//checks to see if can veng
	if (change  == false){
			npc.setNextAnimation(new Animation(1062));
			npc.setNextGraphics(new Graphics(252, 0, 100));
			delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
			delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
			change = true;
		p2.getPackets().sendGameMessage("spec Change: " + String.valueOf(change));
		
		}
		
	else{	
		p2.getPackets().sendGameMessage("xform Change: "  + String.valueOf(change));
		npc.heal(100);
			change = false;
			npc.transformIntoNPC(17177);
		}
		
	
		
						
		
		
		
		
		
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



