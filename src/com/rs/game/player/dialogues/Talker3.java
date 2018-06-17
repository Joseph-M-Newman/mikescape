package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.World;
import com.rs.game.minigames.CastleWars;
import com.rs.game.Animation;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class Talker3 extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (!player.getEquipment().wearingArmour())
			sendDialogue(SEND_1_TEXT_INFO, "Please remove your armour first.");
		else
		sendEntityDialogue(SEND_3_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Do you want to prestige?",
								"Your combat stats will be reset",
						"Current Prestige is "+player.prestige+""}, IS_NPC, npcId, 9850);}
	@Override
	public void run(int interfaceId, int componentId) {
		if (!player.getEquipment().wearingArmour()) {
			end();
		} else if (stage == -1) {			
				sendDialogue(SEND_2_OPTIONS, "Choose",
				"Yes I do.","Tell me more");
			stage = 1;

		} else if (stage == 1) {
            	if (player.getSkills().getCombatLevelWithSummoning() == 138){
            		
            			 if ((player.getSkills().getLevel(Skills.ATTACK) != 99)){
            				 player.sm("You need to have 99 attack to prestige!");
            				 return;
            			 }
                    	 if (player.getSkills().getLevel(Skills.STRENGTH) != 99){
            				 player.sm("You need to have 99 strength to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.DEFENCE) != 99){
            				 player.sm("You need to have 99 defence to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.HITPOINTS) != 99){
            				 player.sm("You need to have 99 hitpoints to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.RANGE) != 99){
            				 player.sm("You need to have 99 range to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.MAGIC) != 99){
            				 player.sm("You need to have 99 mage to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.PRAYER) != 99){
            				 player.sm("You need to have 99 prayer to prestige!");
                    		 return;
                    	 }
                    	 if (player.getSkills().getLevel(Skills.SUMMONING) != 99){
            				 player.sm("You need to have 99 prayer to prestige!");
                    		 return;
                    	 }
                    	 player.sm("Congrats on the prestige, now work your way up more! Shop will be here soon.");
                    	 player.prestige += 1;
                    	 int ui = player.getSkills().getXPForLevel(1);
                    	 int ut = player.getSkills().getXPForLevel(10);
                    	 player.getSkills().set(Skills.ATTACK, 1);
                    	 player.getSkills().set(Skills.STRENGTH, 1);
                    	 player.getSkills().set(Skills.DEFENCE, 1);
                    	 player.getSkills().set(Skills.HITPOINTS, 10);
                    	 player.getSkills().set(Skills.RANGE, 1);
                    	 player.getSkills().set(Skills.MAGIC, 1);
                    	 player.getSkills().set(Skills.PRAYER, 1);
                    	 player.getSkills().set(Skills.SUMMONING, 1);
                    	 player.getSkills().setXp(Skills.ATTACK, ui);
                    	 player.getSkills().setXp(Skills.STRENGTH, ui);
                    	 player.getSkills().setXp(Skills.DEFENCE, ui);
                    	 player.getSkills().setXp(Skills.HITPOINTS, ut);
                    	 player.getSkills().setXp(Skills.MAGIC, ui);
                    	 player.getSkills().setXp(Skills.RANGE, ui);
                    	 player.getSkills().setXp(Skills.PRAYER, ui);
                    	 player.getSkills().setXp(Skills.SUMMONING, ui);
                    	 player.getSkills().refresh(Skills.ATTACK);
                    	 player.getSkills().refresh(Skills.STRENGTH);
                    	 player.getSkills().refresh(Skills.DEFENCE);
                    	 player.getSkills().refresh(Skills.HITPOINTS);
                    	 player.getSkills().refresh(Skills.RANGE);
                    	 player.getSkills().refresh(Skills.MAGIC);
                    	 player.getSkills().refresh(Skills.PRAYER);
                    	 player.getSkills().refresh(Skills.SUMMONING);
                    	 World.sendWorldWideMessage("<img=5><col=ff0000><shad=D8D8D8>Congrats to "+player.getUsername()+" for reaching a prestige of "+player.prestige+".");
            		
            	} else {
            		player.sm("You need to have a combat level of 138 to prestige!");
            	}
			end();
			
		stage = 2;
		} else if (componentId == 2) {
			sendEntityDialogue(SEND_4_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Prestiging opens a shop to buy items other players",
						"May not have. And gives you a chance to work.",
						"The levels that you need for the shops are",
						"1 - 5 - 10 - 15 - 20"}, IS_NPC, npcId, 15582);
			end();
			}
                }
    @Override
    public void finish() {
    }
}
