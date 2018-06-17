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


public class Poanizer extends CombatScript {

boolean intro = false;

	@Override
	public Object[] getKeys() {
		return new Object[] { 17153 };
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
						//	npc.setNextForceTalk(new ForceTalk("Very well, you think you can beat me?"));
							//npc.setNextAnimation(new Animation(14224));  
							//npc.setNextGraphics(new Graphics(2015));
							//intro = true;
							//return defs.getAttackDelay();
							//low hp
							//if (npc.getHitpoints() <= 1000){
								
							//	return defs.getAttackDelay();
							//}
							
		
		
		
	
		
		npc.setNextForceTalk(new ForceTalk("Gl"));
			npc.transformIntoNPC(17154);	
			npc.heal(500);
		
	
	
	return defs.getAttackDelay();
	
}

	
}

