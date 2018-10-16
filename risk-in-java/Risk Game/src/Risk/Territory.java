package Risk;

import java.util.ArrayList; 
import java.util.Random;
import java.util.Arrays;
public class Territory {

	ArrayList<Territory> connections = new ArrayList<Territory>();
	static String name;
	public int troopCount;
	
	public Territory(String title) {
		name = title;
	}
	
	public void connect(Territory touching) {
		connections.add(touching);
	}
	
	public boolean draft(int newTroops) {
		if (newTroops > 0) {
			troopCount += newTroops;
			return true;
		} else {
			return false;
		}
	}

	public int defend(int attackerA, int attackerB) {//The attacking dice should already be sorted. If attacking with only one troop, the last die will be 0
		//returns the number of attackers killed in the attack. NOTE: it is possible that this will return 2 even if only one troop is attacking
		int killed = 0;
		int defendA;
		int defendB;
		Random die = new Random();

		if (troopCount >= 2) {
			defendA = die.nextInt(6)+1;
			defendB = die.nextInt(6)+1;
			
			if (defendA > defendB) {
				if (defendA >= attackerA) {//if the defender wins on the first set of dice
					killed++;
				} else {//if the defender lost on the first set of dice
					troopCount --;
				}
				if (defendB >= attackerB) {
					killed++;
				} else {
					troopCount--;
				}
			} else {//same thing, but if the second die was higher
				if (defendB >= attackerA) {
					killed++;
				} else {
					troopCount--;
				}
				if (defendA >= attackerB) {
					killed++;
				} else {
					troopCount--;
				}
			}
			
		} else {//if only one troop is defending
			defendA = die.nextInt(6)+1;
			if (defendA >= attackerA) {
				killed++;
			} else {
				troopCount--;
			}
		}
		return killed;
	}

	public boolean attack(Territory defender) {
		if (!connections.contains(defender)) {//makes sure that it is actually connected to the territory
			return false;
		}
				
		Random roll = new Random();

		if (troopCount >= 4) {
			int[] dice = new int[3];
			for (int i = 0; i < 3; i++) {
				dice[i] = roll.nextInt(6)+1;
			}
			Arrays.sort(dice);
			defender.defend(dice[0], dice[1]);
		} else {
			int[] dice = new int[troopCount - 1];
			for (int i = 0; i < troopCount -1; i++) {
				dice[i] = roll.nextInt(6)+1;
			}
			Arrays.sort(dice);
			if (troopCount == 3) {
				defender.defend(dice[0], dice[1]);
			} else if(troopCount == 2) {
				defender.defend(dice[0], 0);
			} else {//if you can't actually attack
				return false;
			}
		}
		
		if (defender.troopCount == 0) {//if the territory has been taken
			return true;
		}
		return false;//if you took the territory
		
	}
	
}
