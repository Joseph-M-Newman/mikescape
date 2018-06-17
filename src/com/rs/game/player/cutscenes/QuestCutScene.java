package com.rs.game.player.cutscenes;

import java.util.ArrayList;

import com.rs.game.World;
import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.cutscenes.actions.*;
import com.rs.utils.Utils;
import com.rs.game.Graphics;
import com.rs.game.player.cutscenes.actions.ConstructMapAction;
import com.rs.game.player.cutscenes.actions.CreateNPCAction;
import com.rs.game.player.cutscenes.actions.CutsceneAction;
import com.rs.game.player.cutscenes.actions.DestroyCachedObjectAction;
import com.rs.game.player.cutscenes.actions.LookCameraAction;
import com.rs.game.player.cutscenes.actions.MoveNPCAction;
import com.rs.game.player.cutscenes.actions.MovePlayerAction;
import com.rs.game.player.cutscenes.actions.NPCAnimationAction;
import com.rs.game.player.cutscenes.actions.NPCFaceTileAction;
import com.rs.game.player.cutscenes.actions.NPCForceTalkAction;
import com.rs.game.player.cutscenes.actions.NPCGraphicAction;
import com.rs.game.player.cutscenes.actions.PlayerAnimationAction;
import com.rs.game.player.cutscenes.actions.PlayerFaceTileAction;
import com.rs.game.player.cutscenes.actions.PlayerForceTalkAction;
import com.rs.game.player.cutscenes.actions.PlayerGraphicAction;
import com.rs.game.player.cutscenes.actions.PlayerMusicEffectAction;
import com.rs.game.player.cutscenes.actions.PosCameraAction;

public class QuestCutScene extends Cutscene {

		private static int VANS = 1, HERO = 2;
		
	@Override
	public CutsceneAction[] getActions(Player player) {
		ArrayList<CutsceneAction> actionsList = new ArrayList<CutsceneAction>();
		
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PlayerFaceTileAction(1, 0, -1));
		actionsList.add(new PosCameraAction(53, 46, 600, 100, 100, -1));
		actionsList.add(new LookCameraAction(49, 65, 500, 100, 100, -1));
		actionsList.add(new PosCameraAction(51, 44, 600, 2, 2, 8));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(46, 44, 600, 2, 2, 8));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(46, 44, 600, 2, 2, 8));
		actionsList.add(new LookCameraAction(49, 49, 500, 100, 100, -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(54, 51, 2000, 100, 100, 8));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new CreateNPCAction(VANS, 4794, 25, -1, 0, -1));
		actionsList.add(new PosCameraAction(54, 51, 2000, 100, 100, 8));
		actionsList.add(new LookCameraAction(100, 49, 0, 100, 100, -1));
		actionsList.add(new MoveNPCAction(VANS, 5, -1, false, -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(40, 50, 1500, 100, 100, 15));
		actionsList.add(new LookCameraAction(100, 49, 0, 100, 100, -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(40, 50, 3000, 1, 1, 15));
		actionsList.add(new PlayerAnimationAction(new Animation(6280), -1));
		actionsList.add(new PosCameraAction(40, 50, 3000, 1, 1, 15));
		actionsList.add(new PlayerAnimationAction(new Animation(6281), 0));
		actionsList.add(new PlayerForceTalkAction("..Uhhh?", -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6282), 4));
		actionsList.add(new NPCForceTalkAction(VANS, "Arise!", -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6283), 0));
		actionsList.add(new PlayerAnimationAction(new Animation(6284), 4));
		actionsList.add(new PlayerForceTalkAction("whats going on?", 5));
		actionsList.add(new NPCForceTalkAction(VANS, "Welcome to your doom!", -1));
		actionsList.add(new PlayerAnimationAction(new Animation(6286), 4));
		actionsList.add(new PlayerForceTalkAction("What!?!", 3));
		actionsList.add(new PosCameraAction(57, 55, 4000, 100, 100, -1));
		actionsList.add(new LookCameraAction(45, 47, 0, 100, 100, -1));
		actionsList.add(new NPCForceTalkAction(VANS, "You will die now!", 3));
		actionsList.add(new CreateNPCAction(HERO, 14896, 1, 0, 0, -1));
		actionsList.add(new NPCFaceTileAction(HERO, 10, 0, -1));
		actionsList.add(new NPCAnimationAction(HERO, new Animation(8953), 6));
		actionsList.add(new NPCForceTalkAction(HERO, "I Think Not!", 3));

		return actionsList.toArray(new CutsceneAction[actionsList.size()]);
	}

	@Override
	public boolean hiddenMinimap() {
		return true;
	}

}