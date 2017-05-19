package characters;

import java.util.Random;

public class Monster extends Creature {
	
	private static final double DEFAULT_HEALTH = 200;
	private static final double DEFAULT_DEFENSE = 0.6;
	private static final double DEFAULT_STRENGHT = 30;
	
	private static final double DEFAULT_ESCAPECHANCE = 0.20;
	/**
	 * Escape from Monster
	 */
	private double escapeChance;
	
	
	
	public Monster(String name, double health, double defense, double strenght, double escapeChance) {
		super(name, health, defense, strenght);
		this.escapeChance = escapeChance;
	}
	
	public Monster(String name) {
		this(name, DEFAULT_HEALTH, DEFAULT_DEFENSE, DEFAULT_STRENGHT, DEFAULT_ESCAPECHANCE);
	}
	/**
	 * Check if Monster is escapable
	 * Generate number between 0 and 1
	 * and if generate number is < chance (between 0 and 1)
	 * @return random boolean with chance
	 */
	public boolean isEscapable() {
		Random random = new Random();
		return random.nextDouble() < escapeChance;
	}

	
	/**
	 * When he died he can drop keys to open doors..
	 */
	
}
