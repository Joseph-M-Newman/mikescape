package com.rs.game.player;

import com.rs.game.player.Player;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;


public class PublicChatMessage {

	private String message;
	private int effects;

	public PublicChatMessage(String message, int effects) {
		this.message = message;
		this.effects = effects;
	}

	public String getMessage() {
		return message;
	}

	public int getEffects() {
		return effects;
	}
	
	
	
	/*private String getUsername() {
		return Player.getUsername();
	}*/
	
		

}
