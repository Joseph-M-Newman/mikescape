package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;


public class OgreGames extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to the Ogre Dungeon!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Where am I?",
								"What do i do here?"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "This is the Ogre Dungeon Minigame"}, IS_NPC, npcId, 9785);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] {NPCDefinitions.getNPCDefinitions(npcId).name, "Walk around, you will see coffins exposed and hidden." }, IS_NPC, npcId, 9785);
			stage = 3;

		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "The exposed ones you may loot for various items","This is the same for hidden ones."}, IS_NPC, npcId, 9785);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Every now and again an ogre may spawn." },
			 IS_NPC, npcId, 9785);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "If you find our sacred item Take it to the door."}, IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Use it on the door to teleport to the ancient lair." }, IS_NPC, npcId, 9785);
			stage = 7;
		} else if (stage == 7) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "In this lair you can test your luck."}, IS_NPC, npcId, 9827);

			stage = 8;
			
		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You can either Kill the ogre Warlord,", "Or use it on the chest for a random reward."}, IS_PLAYER, player.getIndex(), 9827);

			stage = 9;
			
		} else if (stage == 9) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Goodluck"}, IS_NPC, npcId, 9827);

			stage = 10;
			
		}else if (stage == 10) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Wow this could be interesting.",
								"I will come back if i need any help"}, IS_PLAYER, player.getIndex(), 9827);

			stage = 11;
		
			
		} else if (stage == 11) {
			end();
			}
                }

	private void teleportPlayer(int x, int y, int z) {
		Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(x, y, z),
				new int[0]);
	}

    @Override
    public void finish() {
    }
}