package com.rs.cores;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;
import com.rs.utils.PlayersOnline;
import com.rs.utils.Utils;
import com.rs.io.SQL;
public final class WorldThread extends Thread {

	protected WorldThread() {
		setPriority(Thread.MAX_PRIORITY);
		setName("World Thread");
	}
	
	
	
public String[] news = {"Use ::commands or ::cmd to see the commands."
		,"Dont forget to vote every 12 hours, do ::vote to go to the site."
		,"Rememeber to sign up to the forums. Type ::forum"
		,"PvP Armour Degrades on equip. Be careful handling it."
		,"Want to secure an account? Type ::securityinfo. "
		,"You drop items when poisoned. Be Aware."
		,"To wake up Poanizer type ::wakeypo. Only works if he is ingame."
		,"All Updates are posted on forums do ::updates"
		,"Want to chill and relax at home, type ::rest followed by ::seat (number)"
		,"If you find a bug, please report it on forums, Along with reporting staff."};
private int NewsTimer;
private int AdminCheck;
private int newsnum = 0;
private int ponline;
private int Vote2muteTimer;

// 100 = 1 minute

	@Override
	public final void run() {
		while (!CoresManager.shutdown) {
			long currentTime = Utils.currentTimeMillis();
			try {
				WorldTasksManager.processTasks();
				for (Player player : World.getPlayers()) {
					if (player == null || !player.hasStarted() || player.hasFinished())
						continue;
					if (currentTime - player.getPacketsDecoderPing() > Settings.MAX_PACKETS_DECODER_PING_DELAY
							&& player.getSession().getChannel().isOpen())
						player.getSession().getChannel().close();
					player.processEntity();
				}
				
				
//Players ingame count.
		/*	
				if (ponline == 0){
				SQL.createConnection();
				SQL.savePlayerCount();
				SQL.PlayersIngame();
				SQL.destroyConnection();
				ponline = 150;
				for (Player p2 : World.getPlayers()) {
					if(p2.getRights() >= 12){
					p2.getPackets().sendPanelBoxMessage("Player count saved");
					}
				}
				}
				if (ponline > 0){
					ponline --;
				}
		*/	
				
				
				
				
				if (NewsTimer == 0){
					if (newsnum  == 6)
						newsnum = 0;
					World.sendWorldWideMessage("<shad=000><col=FF0000>[Information]: <shad=000><col=01DF01>"+news[newsnum]);
					NewsTimer = 300;
					newsnum++;
				}
				if (NewsTimer > 0){
					NewsTimer--;
				}
				
			
				for (NPC npc : World.getNPCs()) {
					if (npc == null || npc.hasFinished())
						continue;
					npc.processEntity();
				}
				for (Player player : World.getPlayers()) {
					if (player == null || !player.hasStarted() || player.hasFinished())
						continue;
					player.getPackets().sendLocalPlayersUpdate();
					player.getPackets().sendLocalNPCsUpdate();
				}
				for (Player player : World.getPlayers()) {
					if (player == null || !player.hasStarted()
							|| player.hasFinished())
						continue;
					player.resetMasks();
				}
				for (NPC npc : World.getNPCs()) {
					if (npc == null || npc.hasFinished())
						continue;
					npc.resetMasks();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			LAST_CYCLE_CTM = Utils.currentTimeMillis();
			long sleepTime = Settings.WORLD_CYCLE_TIME + currentTime
					- LAST_CYCLE_CTM;
			if (sleepTime <= 0)
				continue;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static long LAST_CYCLE_CTM;

}
				
//VOTE 2 MUTE START	 [Failed]
/*			
				if(Settings.vote2mute < Settings.enoughvotes2mute){//if there is enough votes to mute them mute them
				String name = Settings.voted2mute;
				Player target = World.getPlayerByDisplayName(name);
				
				
				target.setMuted(Utils.currentTimeMillis()
							+ (10 * 60 * 1000));
					target.getPackets().sendGameMessage("You've been muted for 5 minutes");
				World.sendWorldWideMessage("<shad=000><col=FF0000>[Vote2Mute]: <shad=000><col=01DF01>" + name + "has been muted for 5 minutes");
					Settings.voted2mute = "";
				}
				
				if(Settings.voted2mute == ""){//if vote2mute hasnt been finished add a timer
				Vote2muteTimer = 0;
				}
				else{
				Vote2muteTimer ++;
				}
				
				if(Vote2muteTimer >= 100){//if timer is greater than 1 min reset
				Vote2muteTimer = 0;
				}
				*/
//VOTE 2 MUTE END	
