package com.rs.game.player.dialogues;

import java.util.HashMap;

public final class DialogueHandler {

	private static final HashMap<Object, Class<Dialogue>> handledDialogues = new HashMap<Object, Class<Dialogue>>();

	@SuppressWarnings("unchecked")
	public static final void init() {
		try {
            Class<Dialogue> value79 = (Class<Dialogue>) Class
					.forName(Varnis.class.getCanonicalName());
			handledDialogues.put("Varnis", value79);
			Class<Dialogue> value1 = (Class<Dialogue>) Class
					.forName(LevelUp.class.getCanonicalName());
			handledDialogues.put("LevelUp", value1);

			Class<Dialogue> value57 = (Class<Dialogue>) Class
					.forName(Crate.class.getCanonicalName());
			handledDialogues.put("DungLad", value57);
			
			Class<Dialogue> value61 = (Class<Dialogue>) Class
					.forName(Training.class.getCanonicalName());
			handledDialogues.put("Training", value61);

			Class<Dialogue> value62 = (Class<Dialogue>) Class
					.forName(Pool.class.getCanonicalName());
			handledDialogues.put("Pool", value62);
			
			
			Class<Dialogue> value2 = (Class<Dialogue>) Class
					.forName(ZarosAltar.class.getCanonicalName());
			handledDialogues.put("ZarosAltar", value2);
			Class<Dialogue> value3 = (Class<Dialogue>) Class
					.forName(ClimbNoEmoteStairs.class.getCanonicalName());
			handledDialogues.put("ClimbNoEmoteStairs", value3);
			Class<Dialogue> value4 = (Class<Dialogue>) Class
					.forName(Banker.class.getCanonicalName());
			handledDialogues.put("Banker", value4);
			Class<Dialogue> value5 = (Class<Dialogue>) Class
					.forName(DestroyItemOption.class.getCanonicalName());
			handledDialogues.put("DestroyItemOption", value5);
			Class<Dialogue> value6 = (Class<Dialogue>) Class
					.forName(FremennikShipmaster.class.getCanonicalName());
			handledDialogues.put("FremennikShipmaster", value6);
			Class<Dialogue> value7 = (Class<Dialogue>) Class
					.forName(DungeonExit.class.getCanonicalName());
			handledDialogues.put("DungeonExit", value7);
			Class<Dialogue> value8 = (Class<Dialogue>) Class
					.forName(NexEntrance.class.getCanonicalName());
			handledDialogues.put("NexEntrance", value8);
			Class<Dialogue> value9 = (Class<Dialogue>) Class
					.forName(MagicPortal.class.getCanonicalName());
			handledDialogues.put("MagicPortal", value9);
			Class<Dialogue> value10 = (Class<Dialogue>) Class
					.forName(LunarAltar.class.getCanonicalName());
			handledDialogues.put("LunarAltar", value10);
			Class<Dialogue> value11 = (Class<Dialogue>) Class
					.forName(AncientAltar.class.getCanonicalName());
			handledDialogues.put("AncientAltar", value11);
			// TODO 12 and 13
			Class<Dialogue> value12 = (Class<Dialogue>) Class
					.forName(FletchingD.class.getCanonicalName());
			handledDialogues.put("FletchingD", value12);
			Class<Dialogue> value14 = (Class<Dialogue>) Class
					.forName(RuneScapeGuide.class.getCanonicalName());
			handledDialogues.put("RuneScapeGuide", value14);
			Class<Dialogue> value15 = (Class<Dialogue>) Class
					.forName(SurvivalExpert.class.getCanonicalName());
			handledDialogues.put("SurvivalExpert", value15);
			Class<Dialogue> value16 = (Class<Dialogue>) Class
					.forName(SimpleMessage.class.getCanonicalName());
			handledDialogues.put("SimpleMessage", value16);
			Class<Dialogue> value17 = (Class<Dialogue>) Class
					.forName(ItemMessage.class.getCanonicalName());
			handledDialogues.put("ItemMessage", value17);
			Class<Dialogue> value18 = (Class<Dialogue>) Class
					.forName(ClimbEmoteStairs.class.getCanonicalName());
			handledDialogues.put("ClimbEmoteStairs", value18);
			Class<Dialogue> value19 = (Class<Dialogue>) Class
					.forName(DiengS.class.getCanonicalName());
			handledDialogues.put("DiengS", value19);
			Class<Dialogue> value20 = (Class<Dialogue>) Class
					.forName(GemCuttingD.class.getCanonicalName());
			handledDialogues.put("GemCuttingD", value20);
			Class<Dialogue> value21 = (Class<Dialogue>) Class
					.forName(CookingD.class.getCanonicalName());
			handledDialogues.put("CookingD", value21);
			Class<Dialogue> value22 = (Class<Dialogue>) Class
					.forName(HerbloreD.class.getCanonicalName());
			handledDialogues.put("HerbloreD", value22);
			Class<Dialogue> value23 = (Class<Dialogue>) Class
					.forName(BarrowsD.class.getCanonicalName());
			handledDialogues.put("BarrowsD", value23);
			Class<Dialogue> value24 = (Class<Dialogue>) Class
					.forName(SmeltingD.class.getCanonicalName());
			handledDialogues.put("SmeltingD", value24);
			Class<Dialogue> value25 = (Class<Dialogue>) Class
					.forName(LeatherCraftingD.class.getCanonicalName());
			handledDialogues.put("LeatherCraftingD", value25);
			Class<Dialogue> value26 = (Class<Dialogue>) Class
					.forName(EnchantedGemDialouge.class.getCanonicalName());
			handledDialogues.put("EnchantedGemDialouge", value26);
			Class<Dialogue> value27 = (Class<Dialogue>) Class
					.forName(ForfeitDialouge.class.getCanonicalName());
			handledDialogues.put("ForfeitDialouge", value27);
			Class<Dialogue> value28 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value28);
			Class<Dialogue> value29 = (Class<Dialogue>) Class
					.forName(WildernessDitch.class.getCanonicalName());
			handledDialogues.put("WildernessDitch", value29);
			Class<Dialogue> value30 = (Class<Dialogue>) Class
					.forName(SimpleNPCMessage.class.getCanonicalName());
			handledDialogues.put("SimpleNPCMessage", value30);
			Class<Dialogue> value31 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value31);
			Class<Dialogue> value32 = (Class<Dialogue>) Class
					.forName(DTSpectateReq.class.getCanonicalName());
			handledDialogues.put("DTSpectateReq", value32);
			Class<Dialogue> value33 = (Class<Dialogue>) Class
					.forName(StrangeFace.class.getCanonicalName());
			handledDialogues.put("StrangeFace", value33);
			Class<Dialogue> value34 = (Class<Dialogue>) Class
					.forName(AncientEffigiesD.class.getCanonicalName());
			handledDialogues.put("AncientEffigiesD", value34);
			Class<Dialogue> value35 = (Class<Dialogue>) Class
					.forName(DTClaimRewards.class.getCanonicalName());
			handledDialogues.put("DTClaimRewards", value35);
			Class<Dialogue> value36 = (Class<Dialogue>) Class
					.forName(SetSkills.class.getCanonicalName());
			handledDialogues.put("SetSkills", value36);
			Class<Dialogue> value37 = (Class<Dialogue>) Class
					.forName(DismissD.class.getCanonicalName());
			handledDialogues.put("DismissD", value37);
			Class<Dialogue> value38 = (Class<Dialogue>) Class
					.forName(MrEx.class.getCanonicalName());
			handledDialogues.put("MrEx", value38);
			Class<Dialogue> value39 = (Class<Dialogue>) Class
					.forName(MakeOverMage.class.getCanonicalName());
			handledDialogues.put("MakeOverMage", value39);
			Class<Dialogue> value40 = (Class<Dialogue>) Class
					.forName(KaramjaTrip.class.getCanonicalName());
			handledDialogues.put("KaramjaTrip", value40);
			Class<Dialogue> value41 = (Class<Dialogue>) Class
					.forName(OzanD.class.getCanonicalName());
			handledDialogues.put("OzanD", value41);
			Class<Dialogue> value43 = (Class<Dialogue>) Class
					.forName(Lucien.class.getCanonicalName());
			handledDialogues.put("Lucien", value43);
			//notices
			Class<Dialogue> value42 = (Class<Dialogue>) Class
					.forName(notices.class.getCanonicalName());
			handledDialogues.put("notices", value42);
			//notices
			//revs
			Class<Dialogue> value74 = (Class<Dialogue>) Class
					.forName(Nigel.class.getCanonicalName());
			handledDialogues.put("Nigel", value74);
			//revs end
			Class<Dialogue> value44 = (Class<Dialogue>) Class
					.forName(TeleportMinigame.class.getCanonicalName());
			handledDialogues.put("TeleportMinigame", value44);
			Class<Dialogue> value45 = (Class<Dialogue>) Class
					.forName(TeleportBosses.class.getCanonicalName());
			handledDialogues.put("TeleportBosses", value45);
			Class<Dialogue> value46 = (Class<Dialogue>) Class
					.forName(TeleportTraining.class.getCanonicalName());
			handledDialogues.put("TeleportTraining", value46);
			Class<Dialogue> value47 = (Class<Dialogue>) Class
					.forName(Turael.class.getCanonicalName());
			handledDialogues.put("Turael", value47);
            Class<Dialogue> value51 = (Class<Dialogue>) Class.forName(JadEnter.class.getCanonicalName());
            handledDialogues.put("JadEnter", value51);
            Class<Dialogue> value52 = (Class<Dialogue>) Class.forName(BorkEnter.class.getCanonicalName());
            handledDialogues.put("BorkEnter", value52);
			Class<Dialogue> value48 = (Class<Dialogue>) Class
					.forName(PoanizerPet.class.getCanonicalName());
			handledDialogues.put("PoanizerPet", value48);
			Class<Dialogue> value49 = (Class<Dialogue>) Class
					.forName(Max.class.getCanonicalName());
			handledDialogues.put("Max", value49);
                        Class<Dialogue> value50 = (Class<Dialogue>) Class
					.forName(GrimReaper.class.getCanonicalName());
			handledDialogues.put("GrimReaper", value50);
                        Class<Dialogue> value55 = (Class<Dialogue>) Class
					.forName(SantaClaus.class.getCanonicalName());
			handledDialogues.put("SantaClaus", value55);
			   Class<Dialogue> value53 = (Class<Dialogue>) Class
						.forName(Vote.class.getCanonicalName());
				handledDialogues.put("Vote", value53);
                        Class<Dialogue> value56 = (Class<Dialogue>) Class
					.forName(Queeeen.class.getCanonicalName());
			handledDialogues.put("Queeeen", value56);

                        Class<Dialogue> value60 = (Class<Dialogue>) Class
					.forName(Talker1.class.getCanonicalName());
			handledDialogues.put("Talker1", value60);
                        Class<Dialogue> value58 = (Class<Dialogue>) Class
					.forName(Talker2.class.getCanonicalName());
			handledDialogues.put("Talker2", value58);
                        Class<Dialogue> value59 = (Class<Dialogue>) Class
					.forName(Talker3.class.getCanonicalName());
			handledDialogues.put("Talker3", value59);
                        Class<Dialogue> value95 = (Class<Dialogue>) Class
					.forName(Talker4.class.getCanonicalName());
			handledDialogues.put("Talker4", value95);
                        Class<Dialogue> value98 = (Class<Dialogue>) Class
					.forName(Talker5.class.getCanonicalName());
			handledDialogues.put("Talker5", value98);
		
//ge					

						Class<Dialogue> value99 = (Class<Dialogue>) Class
					.forName(gemage.class.getCanonicalName());
			handledDialogues.put("gemage", value99);
			
						Class<Dialogue> value100 = (Class<Dialogue>) Class
			.		forName(polypore.class.getCanonicalName());
			handledDialogues.put("polypore", value100);
			
			Class<Dialogue> value101 = (Class<Dialogue>) Class
			.		forName(RandomEvent.class.getCanonicalName());
			handledDialogues.put("RandomEvent", value101);
			
			Class<Dialogue> value102 = (Class<Dialogue>) Class
			.		forName(XmasEvent.class.getCanonicalName());
			handledDialogues.put("XmasEvent", value102);
			Class<Dialogue> value103 = (Class<Dialogue>) Class
			.		forName(XmasInfo.class.getCanonicalName());
			handledDialogues.put("XmasInfo", value103);
			
			Class<Dialogue> value104 = (Class<Dialogue>) Class
			.		forName(snowman1.class.getCanonicalName());
			handledDialogues.put("snowman1", value104);
			
			Class<Dialogue> value105 = (Class<Dialogue>) Class
			.		forName(snowman2.class.getCanonicalName());
			handledDialogues.put("snowman2", value105);
			
			Class<Dialogue> value106 = (Class<Dialogue>) Class
			.		forName(snowman3.class.getCanonicalName());
			handledDialogues.put("snowman3", value106);
			
			Class<Dialogue> value107 = (Class<Dialogue>) Class
			.		forName(bob1.class.getCanonicalName());
			handledDialogues.put("bob1", value107);
			
			Class<Dialogue> value108 = (Class<Dialogue>) Class
			.		forName(pirate1.class.getCanonicalName());
			handledDialogues.put("pirate1", value108);
			
			Class<Dialogue> value109 = (Class<Dialogue>) Class
			.		forName(dance1.class.getCanonicalName());
			handledDialogues.put("dance1", value109);
			
			Class<Dialogue> value110 = (Class<Dialogue>) Class
			.		forName(exam1.class.getCanonicalName());
			handledDialogues.put("exam1", value110);
			
			Class<Dialogue> value111 = (Class<Dialogue>) Class
			.		forName(ConBuilder.class.getCanonicalName());
			handledDialogues.put("ConBuilder", value111);
			
			
			Class<Dialogue> value112 = (Class<Dialogue>) Class
			.		forName(ConHelp.class.getCanonicalName());
			handledDialogues.put("ConHelp", value112);
			
			Class<Dialogue> value113 = (Class<Dialogue>) Class
			.		forName(ArenaOut.class.getCanonicalName());
			handledDialogues.put("ArenaOut", value113);
			
			Class<Dialogue> value114 = (Class<Dialogue>) Class
			.		forName(ArenaIn.class.getCanonicalName());
			handledDialogues.put("ArenaIn", value114);
			
			Class<Dialogue> value115 = (Class<Dialogue>) Class
			.		forName(OgreGames.class.getCanonicalName());
			handledDialogues.put("OgreGames", value115);
			
			
			
			Class<Dialogue> value117 = (Class<Dialogue>) Class
			.		forName(Reply.class.getCanonicalName());
			handledDialogues.put("Reply", value117);
			
			Class<Dialogue> value118 = (Class<Dialogue>) Class
			.		forName(Talker9.class.getCanonicalName());
			handledDialogues.put("Talker9", value118);

			Class<Dialogue> value119 = (Class<Dialogue>) Class
			.		forName(Poanizer.class.getCanonicalName());
			handledDialogues.put("Poanizer", value119);
			
			
			Class<Dialogue> value120 = (Class<Dialogue>) Class
			.		forName(MStyler.class.getCanonicalName());
			handledDialogues.put("MStyler", value120);
			
			Class<Dialogue> value121 = (Class<Dialogue>) Class
			.		forName(FStyler.class.getCanonicalName());
			handledDialogues.put("FStyler", value121);
			
			
			Class<Dialogue> value122 = (Class<Dialogue>) Class
			.		forName(Summon.class.getCanonicalName());
			handledDialogues.put("Summon", value122);
			
		
			Class<Dialogue> value123 = (Class<Dialogue>) Class
			.		forName(Scrolls.class.getCanonicalName());
			handledDialogues.put("Scrolls", value123);
			
			
			Class<Dialogue> value124 = (Class<Dialogue>) Class
			.		forName(purpleportal.class.getCanonicalName());
			handledDialogues.put("purpleportal", value124);
		
		
			Class<Dialogue> value125 = (Class<Dialogue>) Class
			.		forName(Guide.class.getCanonicalName());
			handledDialogues.put("Guide", value125);
			
		
			Class<Dialogue> value126 = (Class<Dialogue>) Class
			.		forName(SlayShop.class.getCanonicalName());
			handledDialogues.put("SlayShop", value126);
			
			
            Class<Dialogue> value127 = (Class<Dialogue>) Class
            .       forName(Gambler.class.getCanonicalName());
            handledDialogues.put("Gambler", value127);
			
			
			Class<Dialogue> value128 = (Class<Dialogue>) Class
            .       forName(SantaClaus2013.class.getCanonicalName());
            handledDialogues.put("SantaClaus2013", value128);
			
			
			Class<Dialogue> value129 = (Class<Dialogue>) Class
            .       forName(Headsnowimp2013.class.getCanonicalName());
            handledDialogues.put("Headsnowimp2013", value129);
			
			
			Class<Dialogue> value130 = (Class<Dialogue>) Class
            .       forName(Frank.class.getCanonicalName());
            handledDialogues.put("Frank", value130);
			
			
			Class<Dialogue> value131 = (Class<Dialogue>) Class
            .       forName(Poanizerfight.class.getCanonicalName());
            handledDialogues.put("Poanizerfight", value131);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static final void reload() {
		handledDialogues.clear();
		init();
	}

	public static final Dialogue getDialogue(Object key) {
		if (key instanceof Dialogue)
			return (Dialogue) key;
		Class<Dialogue> classD = handledDialogues.get(key);
		if (classD == null)
			return null;
		try {
			return classD.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DialogueHandler() {

	}
}
