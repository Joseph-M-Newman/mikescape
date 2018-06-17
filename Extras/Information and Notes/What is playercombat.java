Player combat is all about how weapons work and what specs and emotes they do 


to change how fast it attacks
------------------------------------------------------------------
private int getMeleeCombatDelay(Player player, int weaponId) {
		if (weaponId != -1) {
			String weaponName = ItemDefinitions.getItemDefinitions(weaponId)
					.getName().toLowerCase();
					
					
					
to change the attack emote. can also change for attack style
-----------------------------------------------------------------
public static int getWeaponAttackEmote(int weaponId, int attackStyle) {