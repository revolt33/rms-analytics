package business;

public enum Status {
	MANAGER('m'), SUCCESS, STOCK_EMPLOYEE('s'), SALES_EMPLOYEE('l'), DELIVERY_BOY('d'), All_ITEMS('a'), ITEM_LIST('i'), PICKUP('p'), DELIVERY('d'), CUSTOMER('c'), COUNTER('c'), PAID('p'), AWAITED('a'), CANCELLED('c'), ATTENDED('a'), PENDING('p'), DELIVERED('d'), EXCEPTION_OCCURED, INVALID_USER_TYPE, INACTIVE_EMPLOYEE, USER_UNAVAILABLE, ERROR_OCCURED, INSUFFICIENT_STOCK, ITEM_NOT_FOUND, BATCH_NOT_FOUND, INSUFFICIENT_BATCH_STOCK, NOT_MANAGER, UNAUTHORIZED_OPERATION, FAILED, LOGIN_SUCCESS, INCORRECT_PASSWORD, INACTIVE_USER, INCORRECT_USERNAME, INCORRECT_TOKEN, UNKNOWN;
	private char status;

	private Status() {
	}

	private Status(char status) {
		this.status = status;
	}

	public char getStatus() {
		return status;
	}

	public static Status parseEmployee(char status) {
		switch (status) {
			case 'm':
				return MANAGER;
			case 'l':
				return SALES_EMPLOYEE;
			case 's':
				return STOCK_EMPLOYEE;
			case 'd':
				return DELIVERY_BOY;
			default:
				return UNKNOWN;
		}
	}

	public static Status parseDiscountApplicable(char status) {
		switch (status) {
			case 'a':
				return All_ITEMS;
			case 'i':
				return ITEM_LIST;
			default:
				return UNKNOWN;
		}
	}

	public static Status parseSalesMode(char mode) {
		switch (mode) {
			case 'l':
				return SALES_EMPLOYEE;
			case 'c':
				return CUSTOMER;
			default:
				return UNKNOWN;
		}
	}

	public static Status parseDeliveryMode(char mode) {
		switch (mode) {
			case 'p':
				return PICKUP;
			case 'd':
				return DELIVERY;
			case 'c':
				return COUNTER;
			default:
				return UNKNOWN;
		}
	}

	public static Status parsePaymentStatus(char mode) {
		switch (mode) {
			case 'p':
				return PAID;
			case 'a':
				return AWAITED;
			case 'c':
				return CANCELLED;
			default:
				return UNKNOWN;
		}
	}

	public static Status parseOrderAttendanceStatus(char mode) {
		switch (mode) {
			case 'a':
				return ATTENDED;
			case 'p':
				return PENDING;
			case 'd':
				return DELIVERED;
			case 'c':
				return CANCELLED;
			default:
				return UNKNOWN;
		}
	}
}
