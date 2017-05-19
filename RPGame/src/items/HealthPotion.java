package items;

public class HealthPotion extends Item {

	private final static double DEFAULT_HEALTH = 50;
	private double health;
	
	
	public HealthPotion(String name, String description, double health) {
		super(name, description);
		this.health = health;
	}


	public double getHealth() {
		return health;
	}
	
}
