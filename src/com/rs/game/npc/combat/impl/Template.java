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


public class Template extends CombatScript {



	@Override
	public Object[] getKeys() {
		return new Object[] { 171555 };
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
			
			switch (Utils.getRandom(3)) {
		case 0:
		
			break;
			
		case 1:
			
				
			break;
		
		case 2:
			
			break;
			
			
		case 3:
				
			break;
			}
			
			
		
		
		
			
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



