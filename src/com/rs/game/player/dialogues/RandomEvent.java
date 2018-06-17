package com.rs.game.player.dialogues;


import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.player.Player;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class RandomEvent extends Dialogue {

	

	@Override
	public void start() {
			player.stopAll();
		player.getPackets().sendGameMessage("-Random Event-");
		player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
		player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
		player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
		player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
		player.getPackets().sendGameMessage("<col=FF0000>Click the minimap to close.");
		player.getPackets().sendGameMessage("-Random Event-");
		player.getInterfaceManager().sendInterface(115);
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		
	}

	@Override
	public void finish() {

	}

}
