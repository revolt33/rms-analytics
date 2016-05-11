package business.stock;

import java.util.ArrayList;

public class Category {
	
	private final ArrayList<Item> itemList = new ArrayList<>();
	private final ArrayList<RelationItem> relationList = new ArrayList<>();
	private final String name;
	private final int id;
	private final char active;
	
	public Category(String name, int id, char active) {
		this.name = name;
		this.id = id;
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public boolean getActive() {
		return active=='y';
	}
	public void addItem(Item item) {
		itemList.add(item);
	}
	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public ArrayList<RelationItem> getRelationList() {
		return relationList;
	}

	public void addRelationList(RelationItem relation) {
		this.relationList.add(relation);
	}
}
