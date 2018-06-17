package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.content.Magic;

public class notices extends Dialogue {

	private int npcId;

	
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"This is the Donatorzone Noticeboard.",
						"This will have anything you want on it." }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Oh wow this is so cool!" },
					IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendDialogue(SEND_5_OPTIONS, "Stuff 1","X ",
					"X", "X", 
					"X", "Click for more info.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == 1)
				player.useStairs(382, new WorldTile(3087, 3488, 0), 2, 3,
						"Grr bad update! must stab!.");
			else if (componentId == 2)
				player.useStairs(68464, new WorldTile(3087, 3488, 0), 2, 3,
						"Woah thats cool!");
			else if (componentId == 3)
				player.useStairs(68528, new WorldTile(3087, 3488, 0), 2, 3,
						"Well i guess he's not a vampire...");
			else if (componentId == 4)
				player.useStairs(818, new WorldTile(3087, 3488, 0), 2, 3,
						"Do a little dance, make a little love. get down tonight!");
			else if (componentId == 5) {
				stage = 3;
				sendDialogue(SEND_5_OPTIONS, "Messages/News/Updates 2",
						"I will be trying to code in a minigame", "This minigame will hopefully award some previous donator rewards", "If you have heard the VPS problem i think its ok.",
						"Obviously if your reading this on wednesday then, yes its ok." , "Click for more");
			}
		} else if (stage == 3) {
			if (componentId == 1) {
				player.useStairs(837, new WorldTile(3087, 3488, 0), 2, 3,
						"Im le tired... TYPE ::HOME @@@@@@@@@@@@@@@@@@@@@");
			} else if (componentId == 2)
				player.useStairs(836, new WorldTile(3087, 3488, 0), 2, 3,
						"You are now dead, jokes TYPE ::HOME @@@@@@@@@@@@@@@@");
			else if (componentId == 3)
				player.useStairs(859, new WorldTile(3087, 3488, 0), 2, 3,
						"Why u make emotes glitch me!.");
			else if (componentId == 4)
				player.useStairs(882, new WorldTile(3087, 3488, 0), 2, 3,
						"GHgdfsjhdfkjsoiha[ojrhforeuhoHDASFOUH!!!!!!.");
			else if (componentId == 5) {
				stage = 4;
				sendDialogue(SEND_5_OPTIONS,"X", "X", "X", "X", "Click for more.");
			}
		} else if (stage == 4) {
			if (componentId == 1)
				player.useStairs(14788, new WorldTile(3087, 3488, 0), 2, 3,
						"Non donators can now have the korasi experiance. <3");
			else if (componentId == 2) {
				player.useStairs(10961, new WorldTile(3087, 3488, 0), 2, 3,
						"RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGE!.");
			} else if (componentId == 3)
				player.useStairs(4731, new WorldTile(3087, 3488, 0), 2, 3,
						"Extreme Sinkhole Runescape Edition.");
			else if (componentId == 4) {
				player.useStairs(1914, new WorldTile(3087, 3488, 0), 2, 3,
						"RAWR!.");
			} else if (componentId == 5) {
				stage = 5;
				sendDialogue(SEND_5_OPTIONS, "Messages/News/Updates 4",
						"If you click any option you get a hidden emote/dialougue", "X", "X", "X",
						"Click for more.");
			}
		} else if (stage == 5) {
			if (componentId == 1) {
				player.useStairs(2890, new WorldTile(3087, 3488, 0), 2, 3,
						"Darklight spec!!!");
			} else if (componentId == 2)
				player.useStairs(7041, new WorldTile(3087, 3488, 0), 2, 3,
						"I can swing my sword!.");
			else if (componentId == 3) {
				player.useStairs(7096, new WorldTile(3087, 3488, 0), 2, 3,
						"Woah! Jedi mind powers.");
			} else if (componentId == 4) {
				player.useStairs(1110, new WorldTile(3087, 3488, 0), 2, 3,
						"WOOOOOOOOOOSH MATRIX!!");
			} else if (componentId == 5) {
				stage = 6;
				sendDialogue(SEND_5_OPTIONS, "Messages/News/Updates 5",
						"X", "X", "X", "X",
						"Click for more.");
			}
		} else if (stage == 6) {
			if (componentId == 1)
				player.useStairs(1114, new WorldTile(3087, 3488, 0), 2, 3,
						"Untill you took an arrow to the knee.....");
			else if (componentId == 2)
				player.useStairs(2241, new WorldTile(3087, 3488, 0), 2, 3,
						"You smell a fart.");
			else if (componentId == 3)
				player.useStairs(1060, new WorldTile(3087, 3488, 0), 2, 3,
						"OFF WITH YOUR HEAD!.");
						
			else if (componentId == 4)
				player.useStairs(1810, new WorldTile(3087, 3488, 0), 2, 3,
						"Slightly inapropriate but... Crier got your head?.");
						
				//("http://www.runelocus.com/toplist/vote-9551.html") ;
			else if (componentId == 5) {
				sendDialogue(SEND_5_OPTIONS, "Messages/News/Updates 6",
						"X", "X", "X", "X",
						"End of pages.");
				stage = 2;
			}
		}
	}

	private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
		player.getControlerManager().startControler("GodWars");
	}

	@Override
	public void finish() {

	}
}
