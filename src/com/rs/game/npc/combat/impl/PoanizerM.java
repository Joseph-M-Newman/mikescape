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


public class PoanizerM extends CombatScript {

boolean intro = false;

	@Override
	public Object[] getKeys() {
		return new Object[] { 17154 };
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	
		
							//npc.setNextForceTalk(new ForceTalk("Very well, you think you can beat me?"));
							//npc.setNextAnimation(new Animation(14224));  
							//npc.setNextGraphics(new Graphics(2015));
							//intro = true;
							//return defs.getAttackDelay();
							//low hp
							//npc.getHitpoints() <= 1000)
							//npc.setNextForceTalk(new ForceTalk("0?"));
							//npc.transformIntoNPC(1688);	
								
			
			
			switch (Utils.getRandom(3)) {
		case 0:
		
					if (npc.getHitpoints() <= 400){
					npc.transformIntoNPC(17158);
					npc.heal(10000);
					npc.setNextForceTalk(new ForceTalk("You wil need your clan for this one!"));
				}
				
				else{
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
					delayHit(
							npc,
							0,
							target,
							getMeleeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit(),
											NPCCombatDefinitions.MELEE, target)));

			}
			break;
			
		case 1:
			
					if (npc.getHitpoints() <= 400){
					npc.transformIntoNPC(17158);
					npc.heal(1000);
					npc.setNextForceTalk(new ForceTalk("Now you see my final form!"));
					}
				
				else{
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
					delayHit(
							npc,
							0,
							target,
							getMeleeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit(),
											NPCCombatDefinitions.MELEE, target)));

			}
			break;
		
		case 2:
				if (npc.getHitpoints() <= 400){
					npc.transformIntoNPC(17158);
					npc.heal(10000);
					npc.setNextForceTalk(new ForceTalk("Now you see my final form!"));
					}
				
				else{
				npc.setNextForceTalk(new ForceTalk("You will die in one minute."));
				delayHit(npc,60,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,61,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,62,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,63,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,64,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,65,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,66,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,67,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,68,target,getMeleeHit(npc,getRandomMaxHit(npc, 100, NPCCombatDefinitions.MELEE, target)));
				delayHit(npc,69,target,getMeleeHit(npc,getRandomMaxHit(npc, 1000, NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
					delayHit(
							npc,
							0,
							target,
							getMeleeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit(),
											NPCCombatDefinitions.MELEE, target)));
											}
			break;
			
			
				case 3:
				
					if (npc.getHitpoints() <= 400){
					npc.transformIntoNPC(17158);
					npc.heal(10000);
					npc.setNextForceTalk(new ForceTalk("Now you see my final form!"));
					}
				
				else{
				npc.setNextAnimation(new Animation(14221));
				npc.setNextForceTalk(new ForceTalk("KAAAAAAAAAAAAAAMEEEEEEEEEEEEEHAAAAAAAAMEEEEEEEEEHAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!"));
				delayHit(npc,3,target,getRangeHit(npc,getRandomMaxHit(npc, 200, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,4,target,getRangeHit(npc,getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,5,target,getRangeHit(npc,getRandomMaxHit(npc, 400, NPCCombatDefinitions.RANGE, target)));
				delayHit(npc,6,target,getRangeHit(npc,getRandomMaxHit(npc,  1000,NPCCombatDefinitions.RANGE, target)));
				npc.setNextGraphics(new Graphics(2407 ));
				}
			break;
			}
			
			
		
		
		
			
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



