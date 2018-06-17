package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

import com.rs.game.player.Player;
/**
 * 
 * @author Tyler
 *
 */
public class NomadCombat extends CombatScript {

	  public Object[] getKeys()
	    {
	        return (new Object[] {
	            Integer.valueOf(8528)
	        });
	    }
	//Melee Emote-12696
	//Mage Emote-9300
	//Range Gfx-451
	int nomadRage = 0;
	
	public int attack(NPC npc, Entity target) {
		int nomadAttack = Utils.getRandom(100);
		int nomadHit = Utils.getRandom(300);
		int nomadExplosion = Utils.random(500,700);
		NPCCombatDefinitions defs = npc.getCombatDefinitions();
		
if (target instanceof Player) {
	Player p2 = (Player) target;
	if (p2.getRights() >= 12){
		p2.setNextForceTalk(new ForceTalk(String.valueOf(nomadRage)));
		}
}	
	if(nomadRage < 60){
		
	
			if(nomadAttack >= 35){//attack >50
			
				
					npc.setNextAnimation(new Animation(12696));
					delayHit(npc, 1, target, new Hit[] {
								getMeleeHit(npc, nomadHit) });;
						
				
				
			}//end attack >50
			
			else{//attack less than 50
				
				
				npc.setNextAnimation(new Animation(12697));
					delayHit(npc, 1, target, new Hit[] {
								getMagicHit(npc, nomadHit)
														});
				
			
			}//end else < 50
	}


	else{
	npc.setNextAnimation(new Animation(12699));
				delayHit(npc, 2, target, new Hit[] {
						getMagicHit(npc, nomadExplosion)});
						npc.setNextGraphics(new Graphics(2994));
						target.setNextGraphics(new Graphics(2954));
	
			delayHit(npc, 3, target, new Hit[] {
								getMagicHit(npc, nomadHit)
														});
			delayHit(npc, 4, target, new Hit[] {
								getMagicHit(npc, nomadHit)
														});
				
	nomadRage = 0;
	npc.getCombat().addCombatDelay(5);
	}
			
		nomadRage ++;
		return defs.getAttackDelay();
	}

}
