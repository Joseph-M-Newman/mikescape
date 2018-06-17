package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.cutscenes.*;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;


public class PoanizerPet extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,new String[] { "Poanizer", "I do not serve anyone.", "But I will follow those who can beat me."}, IS_NPC, npcId, 9827);

		}
	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { player.getDisplayName(), "I could probably beat you.",
								"You dont look that tough."}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Poanizer", "Support the server", "Then we will talk."}, IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Poanizer", "Otherwise come see me at ::donatorboss", "Ill be waiting."}, IS_NPC, npcId, 9827);
			
			end();

		
		
			
                }}


    @Override
    public void finish() {
    }
}