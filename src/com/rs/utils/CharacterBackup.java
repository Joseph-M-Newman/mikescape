package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.rs.cache.Cache;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.GraphicDefinitions;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class CharacterBackup {

	public static void main(String[] args){
	  String sourceFileName = "./data/characters";
	  String destinationFileName = "./data/characters2";
	  backup(sourceFileName, destinationFileName);
  }	

 private static void backup(String sourceFileName,String destinationFileName) { 

      BufferedReader br = null;
      BufferedWriter bw = null; 

      try {
      	br = new BufferedReader(new FileReader( sourceFileName ));
      	bw = new BufferedWriter(new FileWriter( destinationFileName ));
        int c;
        while ((c = br.read()) != -1)  {
           bw.write(c);
        }
        br.close();
        bw.close();
      }catch (Exception e) {
	  e.printStackTrace();
      }
}

}
