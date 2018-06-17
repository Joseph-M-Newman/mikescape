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


public class snowman3 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"This is the Hide and Seek Event!"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "What do i do here"}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Follow the clues."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Sometimes you use the item on an object.","Other times you find a Npc" },
			IS_NPC, npcId, 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You will need some of he reward items, And a few tools."}, IS_NPC, npcId, 9785);

			stage = 5;
			
		} else if (stage == 5) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Remember to only do each seek once.","Each time you collect a reward your IGN + ip is recorded."}, IS_NPC, npcId, 9785);

			stage = 6;

			
			
		} else if (stage == 6) {
			player.setNextAnimation(new Animation(1));
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Clue 1**");
			player.sendMessage("I'm the guy people come to to fix the armour and get a free tool.");
			player.sendMessage("I' m fairly close to home, in more ways then one.");
			player.sendMessage("If you seek the 2nd clue you will not talk to me, but exchange words..");
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