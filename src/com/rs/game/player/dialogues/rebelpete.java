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


public class rebelpete extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello there...  ", "How are you today..."}, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		 if (stage == -1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] {player.getDisplayName() , "Yea im not to bad .", "How are you doing?"},
			IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Ok...lots of work to do.." },
			IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Dont you ever get bored just sitting there all day?"},
			IS_PLAYER, player.getIndex(), 9827);
			stage = 3;
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "NO iam plotting to take over The Poanizer Project!", "I just need young Lads like yourself to help me..." },
			IS_NPC, npcId, 9827);
			stage = 4;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Well I would love to stay and chat...", "but i better be off, Bye!" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 5;		
		} else if (stage == 5) {
			
			end();
			}
                }

	

    @Override
    public void finish() {
    }
}