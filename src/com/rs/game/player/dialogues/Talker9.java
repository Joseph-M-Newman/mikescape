package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;



public class Talker9 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];


		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Who are you? What do you want?"}, IS_NPC, npcId, 9785);}

	@Override
	public void run(int interfaceId, int componentId) {

		if (stage == -1) {			
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {player.getDisplayName() , "My name is " + player.getDisplayName(),
								"I don't know why i am even here."}, IS_PLAYER, player.getIndex(), 9827);
			stage = 1;

		} else if (stage == 1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "My name is not important", "You probably wouldnt even know who i am"}, IS_NPC, npcId, 9785);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "Cant you just tell me?" },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 4;

		} else if (stage == 4) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Just ask the owner he will know"," Now leave."}, IS_NPC, npcId, 9785);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "The owner? Hes probably not even online." },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 6;
		} else if (stage == 6) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "LEAVE NOW!"}, IS_NPC, npcId, 9785);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
								new String[] { player.getDisplayName(), "No." },
			IS_PLAYER, player.getIndex(), 9827);
			stage = 11;
		} else if (stage == 11) {
			player.setNextGraphics(new Graphics(343));
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "then i shall smite thee..."}, IS_NPC, npcId, 9785);

			stage = 12;
		} else if (stage == 12) {
		
                    	player.getSkills().resetSkillNoRefresh(Skills.PRAYER);
                    	player.getSkills().refresh(Skills.PRAYER);
						player.applyHit(new Hit(player, 90000, HitLook.REGULAR_DAMAGE));
						player.getPackets().sendGameMessage("You hear the words 'Poanizer' as your faith is removed.");
						player.getPackets().sendGameMessage("Eh you should of known, oh well back to training ;p.");
			
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