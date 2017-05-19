package characters;

public class Player extends Creature {
	
	private static final double DEFAULT_HEALTH = 200;
	private static final double DEFAULT_DEFENSE = 0.8;
	private static final double DEFAULT_STRENGHT = 50;
	private static final double DEFAULT_XP = 0;
	
	private double xp; // experience point
	
	public Player(String name, double health, double defense, double strenght, double xp) {
		super(name, health, defense, strenght);
		this.xp = xp;
	}
	
	public Player(String name) {
		this(name,DEFAULT_HEALTH,DEFAULT_DEFENSE,DEFAULT_STRENGHT,DEFAULT_XP);
	}

	public double getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
}
