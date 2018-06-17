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


public class exam1 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Ready for the exam?"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Sure what do i do?."}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Well your finished the medium Seek."," Now its time for the hard one."}, IS_NPC, npcId, 9827);
			stage = 2;
	
		} else if (stage == 2) {
			player.setNextAnimation(new Animation(1));
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.sendMessage("<col=FF00BF>**Exam**");
			player.sendMessage("To test your skills and knowlege this will be kinda like the cracker search.");
			player.sendMessage("You Have recieved the Ice Sword and a Shield.");
			player.sendMessage("You need to find 2 objects and use each item on it. ");
			player.sendMessage("If you get the correct fuse, you will recieve a rare sword and shield. ");
			player.sendMessage("<col=0000FF>----------------------------------------------------------------");
			player.getInventory().addItem(11952, 1);  //sword
			player.getInventory().addItem(11054, 1);  //shield
			player.getInventory().addItem(2794, 1);  //clue scroll
			player.getInventory().addItem(4084, 1);  //sled
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