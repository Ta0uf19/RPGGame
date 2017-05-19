package items;

public class Item {
	
	private String name;
	private String description;
	/*
	 * Item type
	 */
	private int itemType;
	/*
	 *  The stats the item modifies
	 *	health, defense, strength (1 or 0)
	 */
	private boolean[] itemStats;
	

	public Item(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public boolean[] getItemStats() {
		return itemStats;
	}
	public void setItemStats(boolean[] itemStats) {
		this.itemStats = itemStats;
	}

	@Override
	public String toString() {
		return "Item [" + name + " -- " + description + "]";
	}
	
	
	
	
	
}
