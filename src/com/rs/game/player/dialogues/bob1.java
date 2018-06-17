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


public class bob1 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Congratulations you found me!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Awsome, What do i do next."}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Here is the 2nd clue.","Goodluck."}, IS_NPC, npcId, 9827);
			stage = 2;
	
		} else if (stage == 2) {
			player.setNextAnimation(new Animation(1));
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Clue 2**");
			player.sendMessage("I spend days as a child this land of yellow grains.");
			player.sendMessage("I use my spade and build the best castles and rule the land.");
			player.sendMessage("To come to my land first you must train. ");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
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