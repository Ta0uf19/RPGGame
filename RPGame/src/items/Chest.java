package items;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Item {
	
	private List<Item> items;
	private Key key = null;
	public Chest(String name, String description, Key key, List<Item> items) {
		super(name, description);
		this.key = key;
		this.items = items;
	}
	
	public Chest(String name, String description) {
		this(name, description, null, new ArrayList<Item>());
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void setItem(Item item) {
		items.add(item);
	}
	
	
	
}
