package com.rs.game.npc.slayer;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.Entity;
import com.rs.game.tasks.WorldTask;
import com.rs.game.World;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import java.util.concurrent.TimeUnit;
import com.rs.cores.CoresManager;

@SuppressWarnings("serial")
public class Strykewyrm extends NPC {

	private int stompId;

	public Strykewyrm(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, true);
		stompId = id;
	}

	@Override
	public void processNPC() {
		super.processNPC();
		if (isDead())
			return;
		if (getId() != stompId && !isCantInteract() && !isUnderCombat()) {
			setNextAnimation(new Animation(12796));
			setCantInteract(true);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					transformIntoNPC(9462);
					setCantInteract(false);
				}
			});
		}
	}

	@Override
	public void reset() {
		setNPC(stompId);
		super.reset();
	}

	public static void handleStomping(final Player player, final NPC npc) {
		if (npc.isCantInteract())
			return;
		if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
			if (player.getAttackedBy() != npc
					&& player.getAttackedByDelay() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(
						"You are already in combat.");
				return;
			}
			if (npc.getAttackedBy() != player
					&& npc.getAttackedByDelay() > Utils.currentTimeMillis()) {
				if (npc.getAttackedBy() instanceof NPC) {
					npc.setAttackedBy(player); // changes enemy to player,
					// player has priority over
					// npc on single areas
				} else {
					player.getPackets().sendGameMessage(
							"That npc is already in combat.");
					return;
				}
			}
		}
		switch (npc.getId()) {
		case 9462:
			if (player.getSkills().getLevel(18) < 93) {
				player.getPackets()
						.sendGameMessage(
								"You need at least a slayer level of 93 to fight this.");
				return;
			}
			break;
		default:
			return;
		}
		player.setNextAnimation(new Animation(4278));
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				npc.setNextAnimation(new Animation(12795));
				npc.transformIntoNPC(npc.getId() + 1);
				npc.setTarget(player);
				npc.setAttackedBy(player);
				stop();
			}

		}, 1, 2);
	}
   @Override
    public void sendDeath(Entity source) {
        final NPCCombatDefinitions defs = getCombatDefinitions();
        resetWalkSteps();
        getCombat().removeTarget();
        setNextAnimation(null);
        WorldTasksManager.schedule(new WorldTask() {

            int loop;

            @Override
            public void run() {
                if (loop == 0) {
                    setNextAnimation(new Animation(defs.getDeathEmote()));
                } else if (loop >= defs.getDeathDelay()) {
                    drop();
                    reset();
                    setLocation(getRespawnTile());
                    finish();
                    setRespawnTask();
                    stop();
                }
                loop++;
            }
        }, 0, 1);
    }

    @Override
    public void setRespawnTask() {
        if (!hasFinished()) {
            reset();
            setLocation(getRespawnTile());
            finish();
        }
        final NPC npc = this;
        CoresManager.slowExecutor.schedule(new Runnable() {

            @Override
            public void run() {
                setFinished(false);
                World.addNPC(npc);//getWorld().
                npc.setLastRegionId(0);
                World.updateEntityRegion(npc);
                loadMapRegions();
                checkMultiArea();
            }
        }, getCombatDefinitions().getRespawnDelay() * 600,
                TimeUnit.MILLISECONDS);
    }
}