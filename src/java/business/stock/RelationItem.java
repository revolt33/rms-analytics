package business.stock;

import java.util.ArrayList;

public class RelationItem {
	
	private ArrayList<Relation> relation = new ArrayList<>();
	private String name;
	private int id; // Item's id
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Relation> getRelation() {
		return relation;
	}

	public void addRelation(Relation relation) {
		this.relation.add(relation);
	}
	public class Relation {
		
		private int id; // ingridient's id
		private float amount;
		private String name;
		private char state;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public float getAmount() {
			return amount;
		}

		public void setAmount(float amount) {
			this.amount = amount;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getState() {
			return state=='l'?"L":"Kg";
		}
		
		public void setState(char state) {
			this.state = state;
		}
	}
}
