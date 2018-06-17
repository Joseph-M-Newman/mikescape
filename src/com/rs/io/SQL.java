package com.rs.io;

import java.sql.*;
import java.security.MessageDigest;
import java.util.*;
import java.lang.*;
import com.rs.Launcher;
import com.rs.Settings;
import com.rs.io.*;
import com.rs.game.player.Player;
import com.rs.utils.Misc;
import com.rs.game.World;
import com.rs.utils.Logger;

public class SQL {


	public static Connection con = null;
	public static Statement stmt;

//Normal Sql Connection
	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String IP="SQL Database IP";
            String DB="Database Name";
            String User="Username for Database Account";
            String Pass="Password for Database Account";
            con = DriverManager.getConnection("jdbc:mysql://"+IP+"/"+DB, User, Pass);
			stmt = con.createStatement();
			Logger.log("SQL", "Connection to Servers database Successful.");
		

		} catch (Exception e) {
			Logger.log("SQL", "Connection to Servers database Failed.");
			e.printStackTrace();
		}
	}
	


//Highscore connection
	public static void createConnectionHS() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String IP="SQL Database IP";
            String DB="Database Name";
            String User="Username for Database Account";
            String Pass="Password for Database Account";
            con = DriverManager.getConnection("jdbc:mysql://"+IP+"/"+DB, User, Pass);
			stmt = con.createStatement();
			Logger.log("SQL", "Connection to HS database Successful.");
		

		} catch (Exception e) {
			Logger.log("SQL", "Connection to HS database Failed.");
			e.printStackTrace();
		}
	}
	
	//Result from a query I would guess.
	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(s);
				return rs;
			} else {
				stmt.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			createConnection();
			e.printStackTrace();
		}
		return null;
	}
	
//Closes the SQL connection. best to do.
	public static void destroyConnection() {
		try {
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//Will delete everything in the highscores SQL table.
	public static void refreshHS() {
		try {
			query("TRUNCATE TABLE skills");
			query("TRUNCATE TABLE skillsoverall");
} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	
//Saves player counts.
	public static boolean savePlayerCount() {
		try {
	//Number of players ingame: Deletes number in DB and adds new one.
			query("TRUNCATE TABLE playercount");
			query("INSERT INTO  `poanizer_Server`.`playercount` (`playersonline`)VALUES ("+ World.getPlayers().size() +")"); 
			Logger.log("SQL", "Player Count Updated.");
		} catch (Exception e) {
		Logger.log("SQL", "Player Count Update Failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

//Saves the players whoa are ingame: Deletes list of names and adds all the rest in.
	public static boolean PlayersIngame() {
		try {
		
				query("TRUNCATE TABLE playersingame");
			for(Player p5 : World.getPlayers()) {                           
				query("INSERT INTO  `poanizer_Server`.`playersingame` (`players`)VALUES ('"+ p5.getUsername() +"')"); 
			}									
		Logger.log("SQL", "Players ingame list Updated.");
		} catch (Exception e) {
		Logger.log("SQL", "Players ingame list update Failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

//Saves highscores	
	public static boolean saveHighScore(Player p) {
		if ((p.getUsername().equalsIgnoreCase("poanizer"))|| (p.getUsername().equalsIgnoreCase("ian"))|| (p.getUsername().equalsIgnoreCase("poanizer2"))){
		Logger.log("SQL", "Aborted HS for: " + p.getUsername());
		
		}
		
		else{
		try {
			query("DELETE FROM `skills` WHERE playerName = '"+p.getUsername()+"';");
			query("DELETE FROM `skillsoverall` WHERE playerName = '"+p.getUsername()+"';");
			query("DELETE FROM `skills` WHERE playerName = '"+p.getUsername()+"';");
			query("DELETE FROM `skillsoverall` WHERE playerName = '"+p.getUsername()+"';");
			query("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangedxp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`,`Hunterlvl`,`Hunterxp`,`Constructionlvl`,`Constructionxp`,`Summoninglvl`,`Summoningxp`, `Dungeoneeringlvl`,`Dungeoneeringxp`) VALUES ('"+p.getUsername()+"',"+p.getSkills().getLevel(0)+","+p.getSkills().getXp(0)+","+p.getSkills().getLevel(1)+","+p.getSkills().getXp(1)+","+p.getSkills().getLevel(2)+","+p.getSkills().getXp(2)+","+p.getSkills().getLevel(3)+","+p.getSkills().getXp(3)+","+p.getSkills().getLevel(4)+","+p.getSkills().getXp(4)+","+p.getSkills().getLevel(5)+","+p.getSkills().getXp(5)+","+p.getSkills().getLevel(6)+","+p.getSkills().getXp(6)+","+p.getSkills().getLevel(7)+","+p.getSkills().getXp(7)+","+p.getSkills().getLevel(8)+","+p.getSkills().getXp(8)+","+p.getSkills().getLevel(9)+","+p.getSkills().getXp(9)+","+p.getSkills().getLevel(10)+","+p.getSkills().getXp(10)+","+p.getSkills().getLevel(11)+","+p.getSkills().getXp(11)+","+p.getSkills().getLevel(12)+","+p.getSkills().getXp(12)+","+p.getSkills().getLevel(13)+","+p.getSkills().getXp(13)+","+p.getSkills().getLevel(14)+","+p.getSkills().getXp(14)+","+p.getSkills().getLevel(15)+","+p.getSkills().getXp(15)+","+p.getSkills().getLevel(16)+","+p.getSkills().getXp(16)+","+p.getSkills().getLevel(17)+","+p.getSkills().getXp(17)+","+p.getSkills().getLevel(18)+","+p.getSkills().getXp(18)+","+p.getSkills().getLevel(19)+","+p.getSkills().getXp(19)+","+p.getSkills().getLevel(20)+","+p.getSkills().getXp(20)+","+p.getSkills().getLevel(21)+","+p.getSkills().getXp(21)+","+p.getSkills().getLevel(22)+","+p.getSkills().getXp(22)+","+p.getSkills().getLevel(23)+","+p.getSkills().getXp(23)+","+p.getSkills().getLevel(24)+","+p.getSkills().getXp(24)+");");
			query("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) VALUES ('"+p.getUsername()+"',"+(p.getSkills().getLevel(0) + p.getSkills().getLevel(1) + p.getSkills().getLevel(2) + p.getSkills().getLevel(3) + p.getSkills().getLevel(4) + p.getSkills().getLevel(5) + p.getSkills().getLevel(6) + p.getSkills().getLevel(7) + p.getSkills().getLevel(8) + p.getSkills().getLevel(9) + p.getSkills().getLevel(10) + p.getSkills().getLevel(11) + p.getSkills().getLevel(12) + p.getSkills().getLevel(13) + p.getSkills().getLevel(14) + p.getSkills().getLevel(15) + p.getSkills().getLevel(16) + p.getSkills().getLevel(17) + p.getSkills().getLevel(18) + p.getSkills().getLevel(19) + p.getSkills().getLevel(20) + p.getSkills().getLevel(21) + p.getSkills().getLevel(22) + p.getSkills().getLevel(23))+","+((p.getSkills().getXp(0)) + (p.getSkills().getXp(1)) + (p.getSkills().getXp(2)) + (p.getSkills().getXp(3)) + (p.getSkills().getXp(4)) + (p.getSkills().getXp(5)) + (p.getSkills().getXp(6)) + (p.getSkills().getXp(7)) + (p.getSkills().getXp(8)) + (p.getSkills().getXp(9)) + (p.getSkills().getXp(10)) + (p.getSkills().getXp(11)) + (p.getSkills().getXp(12)) + (p.getSkills().getXp(13)) + (p.getSkills().getXp(14)) + (p.getSkills().getXp(15)) + (p.getSkills().getXp(16)) + (p.getSkills().getXp(17)) + (p.getSkills().getXp(18)) + (p.getSkills().getXp(19)) + (p.getSkills().getXp(20)) + (p.getSkills().getXp(21)) + (p.getSkills().getXp(22)) + (p.getSkills().getXp(23)) + (p.getSkills().getXp(24)))+");");
			Logger.log("SQL", "Updated: " + p.getUsername() + " In HS.");
		} catch (Exception e) {
			Logger.log("SQL", "HS Failed to update for: " + p.getUsername());
		
			e.printStackTrace();
			return false;
		}
		}
		return true;
	
	}
	
	}