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


public class Pker extends CombatScript {

int food = 20;
int spec = 100;
int vengtimer = 30;
boolean veng = false;
boolean canveng = true;

//max hit is roughly 470



	@Override
	public Object[] getKeys() {
		return new Object[] { 17177 };//the juggernaut 138 full melee brute.
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	Player p2 = (Player) target;
	//checks to see if can veng
	if (vengtimer == 0){
		canveng = true;
		}
		
		//Eat food unless none left
		if(food > 0){
			if(npc.getHitpoints() <= 430){
			food -= 1; //uses 1 food
			npc.heal(230);
			npc.setNextAnimation(new Animation(829));//eat anim
			vengtimer -= 1;			
			
			
			p2.getPackets().sendGameMessage("Eat: V-Timer: " + String.valueOf(vengtimer) + " Food left: " +  String.valueOf(food) + " Spec left: " +  String.valueOf(spec) + " Can veng: " + String.valueOf(canveng));
			}
		}
		//Vengance
		if(canveng == true){
			npc.setNextGraphics(new Graphics(726, 0, 100));
			npc.setNextAnimation(new Animation(4410));
		
		p2.getPackets().sendGameMessage("Veng: V-Timer: " + String.valueOf(vengtimer) + " Food left: " +  String.valueOf(food) + " Spec left: " +  String.valueOf(spec) + " Can veng: " + String.valueOf(canveng));
		}
		
		if(target.getHitpoints() <= 400){
		spec -= 25;
		vengtimer -= 1;		
		npc.transformIntoNPC(17178);
		
		p2.getPackets().sendGameMessage("Spec: V-Timer: " + String.valueOf(vengtimer) + " Food left: " +  String.valueOf(food) + " Spec left: " +  String.valueOf(spec) + " Can veng: " + String.valueOf(canveng));
		}
		
		else{
		vengtimer -= 1;	
		delayHit(npc,0,target,getMeleeHit(npc,getRandomMaxHit(npc, defs.getMaxHit(),NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(1658));		
		p2.getPackets().sendGameMessage("Hit: V-Timer: " + String.valueOf(vengtimer) + " Food left: " +  String.valueOf(food) + " Spec left: " +  String.valueOf(spec) + " Can veng: " + String.valueOf(canveng));
		}
		
		
		
			
		
		
			return defs.getAttackDelay();
		
	}
	
	

}



