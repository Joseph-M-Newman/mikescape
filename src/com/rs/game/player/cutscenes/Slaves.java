package com.rs.game.player.cutscenes;

import java.util.ArrayList;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
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
public class Slaves extends Cutscene {

//PlayerAnimationAction(Animation anim, int actionDelay) 
//PlayerForceTalkAction(String text, int actionDelay) {
//PlayerFaceTileAction(int x, int y, int actionDelay) {
//MovePlayerAction(int x, int y, boolean run, int actionDelay) 
//MovePlayerAction(int x, int y, int plane, int movementType, int actionDelay) {
// PlayerMusicEffectAction(int id, int actionDelay) {

//NPCGraphicAction(int cachedObjectIndex, Graphics gfx, int actionDelay) {
//NPCForceTalkAction(int cachedObjectIndex, String text, int actionDelay) {
//NPCFaceTileAction(int cachedObjectIndex, int x, int y, int actionDelay) {
//NPCAnimationAction(int cachedObjectIndex, Animation anim, int actionDelay) {
//CreateNPCAction(int cachedObjectIndex, int id, int x, int y, int plane, int actionDelay) {
//MoveNPCAction(int cachedObjectIndex, int x, int y, boolean run, int actionDelay) {	
//MoveNPCAction(int cachedObjectIndex, int x, int y, int plane,int movementType, int actionDelay) {

//LookCameraAction(int viewLocalX, int viewLocalY, int viewZ, int speed, int speed2, int actionDelay) {
//LookCameraAction(int viewLocalX, int viewLocalY, int viewZ, int actionDelay) {
//public DestroyCachedObjectAction(int cachedObjectIndex, int actionDelay) {
// ConstructMapAction(int baseChunkX, int baseChunkY, int widthChunks, int heightChunks) {
//PosCameraAction(int moveLocalX, int moveLocalY, int moveZ, int speed, int speed2, int actionDelay) 
//PosCameraAction(int moveLocalX, int moveLocalY, int moveZ, int actionDelay) {

				
	private static int JUGG = 1, SLAVE1 = 2, SLAVE2 = 3, SLAVE3 = 4, SLAVE4 = 5, SLAVE5 = 6;

@Override
	public CutsceneAction[] getActions(Player player) {
		ArrayList<CutsceneAction> actionsList = new ArrayList<CutsceneAction>();

		
		/*
		actionsList.add(new CreateNPCAction(GUARD1, 296, 3, 7, 0, -1)); // Todo
		actionsList.add(new CreateNPCAction(GUARD2, 298, 3, 5, 0, -1)); // Todo
		actionsList.add(new MoveNPCAction(GUARD1, 9, 7, false, 0));
		actionsList.add(new MoveNPCAction(GUARD2, 9, 5, false, 2));
		actionsList.add(new NPCForceTalkAction(GUARD1, "Noob, What are you Doing Here?!?!", 2));
		*/
													//think these use rx etc coords
													
		actionsList.add(new PosCameraAction(1, 1, 1, 6, 6, -1));//<- 10 ^ 50,
		actionsList.add(new LookCameraAction(1, 1, 1, 6, 6, 10)); //spins around <- goes down
		actionsList.add(new CreateNPCAction(JUGG, 17176, 1, 1, 1, -1));
		
		actionsList.add(new NPCAnimationAction(JUGG, new Animation(15535), -1)); // headbang
		actionsList.add(new NPCForceTalkAction(JUGG, "Haha looks like we got another slave boys.", 5));
		
		actionsList.add(new NPCAnimationAction(JUGG, new Animation(12934), -1)); // headbang
		actionsList.add(new NPCForceTalkAction(JUGG, "Alright then, Come on then, come and get some!", 5));

		return actionsList.toArray(new CutsceneAction[actionsList.size()]);
	}

	@Override
	public boolean hiddenMinimap() {
		return true;
	}

}
