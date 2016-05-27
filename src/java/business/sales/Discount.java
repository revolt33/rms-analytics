package business.sales;

public class Discount {
	private String code;
	private char type;
	private float percentage;
	private float max;
	private long from;
	private long to;
	private char applicable;
	private int count;
	private int id;
	private boolean active;
	private int empId;
	private String adddedBy;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public char getApplicable() {
		return applicable;
	}

	public void setApplicable(char applicable) {
		this.applicable = applicable;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getStatus() {
		return active;
	}

	public void setStatus(char status) {
		this.active = status=='a';
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getAdddedBy() {
		return adddedBy;
	}

	public void setAdddedBy(String adddedBy) {
		this.adddedBy = adddedBy;
	}
	
	public class Item {
		private int id;
		private char type;
		private int item;
		private String name; // name of item

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public char getType() {
			return type;
		}

		public void setType(char type) {
			this.type = type;
		}

		public int getItem() {
			return item;
		}

		public void setItem(int item) {
			this.item = item;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
