package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Max extends Dialogue {

	public Max() {
	}

	public void start() {
		npcId = ((Integer) parameters[0]).intValue();
		sendEntityDialogue(
				(short) 241,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Would you like to teleport to Skilling Areas?" },
				(byte) 1, npcId, 9850);
	}

	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendDialogue((short) 238, new String[] {
					"Max's Skill Teleports", "Theiving", "Fishing", "Mining",
					"Rune Crafting", "More Options"});
			stage = 1;
		} else if (stage == 1) {
			if (componentId == 1)
				teleportPlayer(2662, 3302, 0);
			else if (componentId == 2)
				teleportPlayer(2590,3423, 0);
			else if (componentId == 3)
				teleportPlayer(3298, 3299, 0);
			else if (componentId == 4)
				teleportPlayer(2464, 4818, 0);
			else if (componentId == 5) {
				stage = 2;
				sendDialogue(SEND_5_OPTIONS, "Max's Skill Teleports",
						"Woodcutting.", "Gnome Agility.", "Smithing",
						"Construction", "Farming");
			}
		} else if (stage == 2) {
			if (componentId == 1)
				teleportPlayer(2725, 3491, 0);
			else if (componentId == 2)
				teleportPlayer(2470, 3436, 0);
			else if (componentId == 3)
				teleportPlayer(3108, 3500, 0);
			else if (componentId == 4)
				teleportPlayer(2637, 3298, 0);
			else if (componentId == 5){
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						2816, 3462, 0), new int[0]);
				player.sm("Click on the patches, and have either potato, ranarr, guam, kuarm, lantadyme, or torstol seeds.");
			}stage = 1;
		}
		}

	private void teleportPlayer(int x, int y, int z) {
		Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(x, y, z),
				new int[0]);
	}

	public void finish() {
	}

	private int npcId;
}
