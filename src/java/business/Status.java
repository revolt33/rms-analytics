package business;

public enum Status {
	MANAGER('m'), SUCCESS, STOCK_EMPLOYEE('s'), SALES_EMPLOYEE('l'), EXCEPTION_OCCURED, INVALID_USER_TYPE, INACTIVE_EMPLOYEE, USER_UNAVAILABLE, ERROR_OCCURED, INSUFFICIENT_STOCK, ITEM_NOT_FOUND, BATCH_NOT_FOUND, INSUFFICIENT_BATCH_STOCK, NOT_MANAGER, UNAUTHORIZED_OPERATION, FAILED, LOGIN_SUCCESS, INCORRECT_PASSWORD, INACTIVE_USER, INCORRECT_USERNAME, INCORRECT_TOKEN,UNKNOWN;
	private char status;

	private Status() {
	}

	private Status(char status) {
		this.status = status;
	}

	public char getStatus() {
		return status;
	}

	public static Status parseEmployee(char emp) {
		switch (emp) {
			case 'm':
				return MANAGER;
			case 'l':
				return SALES_EMPLOYEE;
			case 's':
				return STOCK_EMPLOYEE;
			default:
				return UNKNOWN;
		}
	}
}
