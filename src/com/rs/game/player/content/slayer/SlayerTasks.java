package com.rs.game.player.content.slayer;

/**
 * This is a list of assignable tasks
 * 
 * @author Emperial
 * 
 */
public enum SlayerTasks {
	/**
	 * TURAEL TASKS
	 */
			AHRIMTHEBLIGHTED("Ahrims", TaskSet.TURAEL, 15, 30, "Ahrim the Blighted"), 
			VERACTHEDEFILED("Verac", TaskSet.TURAEL, 15, 30, "Verac the Defiled"),
			TORAGTHECORRUPTED("Torag", TaskSet.TURAEL, 15, 30, "Torag the Corrupted"),
			KARILTHETAINTED("Karil", TaskSet.TURAEL, 15, 30, "Karil the Tainted"), 
			GUTHANTHEINFESTED("Guthans", TaskSet.TURAEL, 10, 30, "Guthan the Infested"), 
			DHAROKTHEWRETCHED("Dharoks", TaskSet.TURAEL, 15, 30, "Dharok the Wretched"),
			DARKBEAST("Dark beasts", TaskSet.TURAEL, 30, 50, "Dark beast"),
			ABERRANT("Abberant spectres", TaskSet.TURAEL, 15, 30, "Abberant spectre"),
			GARGOYLE("Gargoyles", TaskSet.TURAEL, 25, 50, "Gargoyle"), 
			BANSHEE("Banshees", TaskSet.TURAEL, 10, 20, "Banshee"), 
			NECHRYAEL("Nechryaels", TaskSet.TURAEL, 15, 20, "Nechryael"),
			ABYSSDEMON("Abyssal Demons", TaskSet.TURAEL, 15, 20, "Abyssal demon"), 
			INFERNALMAGE("Infernal Mages", TaskSet.TURAEL, 15, 20, "Infernal Mage"),
			BLOODVELD("Bloodvelds", TaskSet.TURAEL,15, 25, "Bloodveld"),
			//ICEFIENDS("Ice Fiend", TaskSet.TURAEL, 15, 50, "Ice Fiend"),
			HELLHOUNDS("Hellhound", TaskSet.TURAEL, 15, 50, "Hellhound"),
			FROSTDRAGONS("Frost dragon", TaskSet.TURAEL, 25, 50, "Frost dragon");
			//DRAGONS("Dragons", TaskSet.TURAEL, 15, 30, "Red dragon", "Black dragon","Blue Dragon");

	private SlayerTasks(String simpleName, TaskSet type, int min, int max,
			String... monsters) {
		this.type = type;
		this.slayable = monsters;
		this.simpleName = simpleName;
		this.min = min;
		this.max = max;
	}

	/**
	 * A simple name for the task
	 */
	public String simpleName;

	/**
	 * The task set
	 */
	public TaskSet type;
	/**
	 * The monsters that will effect this task
	 */
	public String[] slayable;
	/**
	 * The minimum amount of monsters the player may be assigned to kill
	 */
	public int min;
	/**
	 * The maximum amount of monsters the player may be assigned to kill
	 */
	public int max;
}
