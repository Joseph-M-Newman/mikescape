package com.rs.game.npc.combat.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class BombCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 8771 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.getRandom(2);

		if (attackStyle == 1) { // Spare.
		
			npc.setNextGraphics(new Graphics(2656));
			npc.heal(1000);
			npc.setNextForceTalk(new ForceTalk("I guess ill spare you this time..."));
			npc.transformIntoNPC(3067);

		}
		else if (attackStyle == 2) {
			/*
			 * 11364 - even better k0 move. fire balls from sky into everyone
			 * 80% max hp or gfx 2600 everyone near dies
			 */
			npc.setNextForceTalk(new ForceTalk("LELELELELELELLELELELE!"));
			npc.setNextGraphics(new Graphics(2600));
			//npc.setCantInteract(true);
			//npc.getCombat().removeTarget();
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					for (Entity t : npc.getPossibleTargets()) {
						t.applyHit(new Hit(npc, (int) (t.getHitpoints() - 1), HitLook.REGULAR_DAMAGE, 0));
						npc.applyHit(new Hit(npc, (int) (t.getHitpoints() - 1), HitLook.REGULAR_DAMAGE, 0));
					}
		//			npc.setCantInteract(false);
					//npc.setTarget(target);
					npc.transformIntoNPC(3067);
				}

			}, 4);
			return 0;
		}

		return defs.getAttackDelay();
	}
}
