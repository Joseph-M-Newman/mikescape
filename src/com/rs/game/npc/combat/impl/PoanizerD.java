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


public class PoanizerD extends CombatScript {

	boolean spec = false;
	//setCapDamage(400);
	
	
	
	
	@Override
	public Object[] getKeys() {
		return new Object[] { 17158 };
	}

	
		@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	npc.setCapDamage(500);
	npc.setForceMultiAttacked(true);
		
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
		
				
					if (npc.getHitpoints() <= 1000){
				
				npc.heal(2000);
				npc.setNextForceTalk(new ForceTalk("Safe Up."));
				npc.setNextAnimation(new Animation(10753));
				}
				
				else{
				target.setNextGraphics(new Graphics(44 ));
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
					//target.getPoison().makePoisoned(150);
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
			
					if (npc.getHitpoints() <= 1000){
				npc.heal(2000);
				npc.setNextForceTalk(new ForceTalk("Safe Up."));
				npc.setNextAnimation(new Animation(10753));
				target.setNextGraphics(new Graphics(83  ));
				}
				
				else{
				//target.setNextGraphics(new Graphics(2400  ));
				npc.setNextAnimation(new Animation(884));
				//target.getPoison().makePoisoned(150);									//gfx , speed? x,y,z????
				World.sendProjectile(npc, target, 65, 38, 16, 41, 35, 16, 0);
				World.sendProjectile(npc, target, 66, 40, 16, 41, 35, 16, 0);
				delayHit(
							npc,
							0,
							target,
							getRangeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit(),
											NPCCombatDefinitions.RANGE, target)));

			}
			break;
		
		case 2:
		
		if(Utils.getRandom(0) == 0){
			
			npc.setNextAnimation(new Animation(14298));
				npc.getCombat().addCombatDelay(5);
				target.setNextGraphics(new Graphics(2600 ));
			WorldTasksManager.schedule(new WorldTask() {
	
				@Override
				public void run() {
					for (Entity t : npc.getPossibleTargets()) {
						delayHit(npc,1,t,getMagicHit(npc,getRandomMaxHit(npc, 1000, NPCCombatDefinitions.MAGE, target)));
						
					}
		//			npc.setCantInteract(false);
					//npc.setTarget(target);
					//npc.transformIntoNPC(3067);
				}

			}, 4);
			return 0;
				}
				
				
				
			break;
				case 3:
			
				npc.setNextAnimation(new Animation(14288));
				npc.setNextForceTalk(new ForceTalk("DRAGON FIST!!!!!!!!!!!!!"));
				delayHit(npc,1,target,getMeleeHit(npc,getRandomMaxHit(npc,  5000,NPCCombatDefinitions.MELEE, target)));
				npc.setNextGraphics(new Graphics(2407 ));
				target.setNextGraphics(new Graphics(2437));
				npc.setNextAnimation(new Animation(14288));
			break;
			}
			
			
		
		
		
			
			
		
		
			return defs.getAttackDelay();
		
	}

	
}

