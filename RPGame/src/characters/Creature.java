package characters;

import java.util.ArrayList;
import java.util.List;

import items.Item;

public class Creature {
	
	private String name;
	private double health;
	private double defense; 
	private double strenght;
	private List<Item> inventory;
	
	public Creature(String name, double health, double defense, double strenght) {
		super();
		this.name = name;
		this.health = health;
		this.defense = defense;
		this.strenght = strenght;
		inventory = new ArrayList<Item>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double d) {
		this.health = d;
	}
	public double getDefense() {
		return defense;
	}
	public void setDefense(double defense) {
		this.defense = defense;
	}
	public double getStrenght() {
		return strenght;
	}
	public void setStrenght(double strenght) {
		this.strenght = strenght;
	}
	
	/*
	 * Attack
	 */
	public int attack(Creature creature) {
		if(creature != null) {
			double rand = Math.random();
			if(!(rand > defense)) {
				double zf = Math.random() + 1;
                int dmg = (int) (strenght * zf);
                if(dmg>0) 

                	creature.setHealth(creature.getHealth() - dmg);
                	if(creature.isKilled()) {
                		addInventory(creature.getInventory());
                	}
                return dmg;
                
			}
		}
		return -1;	
	}
	
	public boolean isKilled() {
		return health <= 0;
	}
	
	/**
	 * Print consols stats after attack
	 */
	public void printStats() {
		
		String comment;
		
		if(health >= 200) {
			comment = "Its looks uninjured";
		}else if(health < 200 && health >= 100) {
			comment = "Its look barely injured";
		}else if(health< 100 && health > 0 ) {
			comment = "Its look near death";
		}else {
			comment = "Its look death";
		}
		
		if(health >0)
			System.out.println(">> " + name.toUpperCase() + " || HP " + health + " || ATTACK " + strenght + " [ " + comment + " ]");
		else
			System.out.println(">> " + name.toUpperCase() + " [ " + comment + " ]");
	}


	public List<Item> getInventory() {
		return inventory;
	}
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
	
	public void addInventory(List<Item> invent) {
		for(Item i : invent) {
			inventory.add(i);
		}
	}
	/**
	 * add element to inventory
	 */
	public void setInventory(Item e) {
		inventory.add(e);
	}
	
	
}
