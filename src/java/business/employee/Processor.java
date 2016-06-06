package business.employee;

import java.sql.*;
import business.hashing.Password;
import business.Utility;
import business.stock.Result;
import java.util.ArrayList;
import business.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {

	// call this method to create new employee
	public static Result createEmp(Employee employee, AuthToken authToken, Connection con) {
		Result result = new Result();
		result.setStatus(Status.FAILED);
		String random = "";
		if (authToken.getType() == Status.MANAGER) {
			synchronized (con) {
				Statement st = null;
				try {
					con.setCatalog("employee");
					st = con.createStatement();
					random = Utility.getRandomString(20);
					st.execute("start transaction;");
					st.executeUpdate("insert into emp (username, type, name, mobile, email, fp_token, dob, house, colony, city, state, zip, landmark, gender) values ('" + employee.getUsername() + "', '" + employee.getType() + "', '" + employee.getName() + "', " + employee.getMobile() + ", '" + employee.getEmail() + "', '" + random + "', '" + employee.getDob() + "',  '" + employee.getStreet() + "', '" + employee.getColony() + "', '" + employee.getCity() + "', '" + employee.getState() + "', " + employee.getZip() + ", '" + employee.getLandmark() + "', '" + employee.getGender() + "')");
					ResultSet rs = st.executeQuery("select LAST_INSERT_ID();");
					if (rs.next()) {
						result.setCode(rs.getInt(1));
						st.executeUpdate("update emp set photo='img" + result.getCode() + employee.getImageExt() + "' where id=" + result.getCode());
						st.execute("commit;");
						result.setStatus(Status.SUCCESS);
					}
				} catch (Exception e) {
					try {
						st.execute("rollback;");
					} catch (SQLException ex) {
						e.printStackTrace();
						result.setStatus(Status.FAILED);
					}
					e.printStackTrace();
					result.setStatus(Status.FAILED);
				}
			}
		} else {
			result.setStatus(Status.NOT_MANAGER);
		}
		return result;
	}

	public static boolean checkUsername(String username, AuthToken authToken, Connection con) throws SQLException {
		if (authToken.getType() == Status.MANAGER) {
			synchronized (con) {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select count(*) as num from emp where username='" + username + "';");
				if (rs.next()) {
					int count = rs.getInt(1);
					return count == 0;
				}
			}
		}
		return false;
	}

	// pass login credentials and get LoginStatus object
	public static LoginStatus login(String username, String password, boolean remember, Connection con) {
		LoginStatus ls = null;
		AuthToken authToken = new AuthToken();
		synchronized (con) {
			try {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select password, id, type, name, active from emp where username='" + username + "'");
				if (rs.next()) {
					String pass = rs.getString(1);
					int id = rs.getInt(2);
					char type = rs.getString(3).charAt(0);
					String name = rs.getString(4);
					if (rs.getString("active").charAt(0) == 'y') {
						if (Password.check(password, pass)) {
							String token = Utility.getRandomString(6);
							st.executeUpdate("update emp set auth_token='" + token + "', remember='" + (remember ? 'y' : 'n') + "' where id=" + id);
							authToken.setId(id);
							authToken.setAuthToken(token);
							authToken.setType(Status.parseEmployee(type));
							authToken.setName(name);
							ls = new LoginStatus(authToken, Status.LOGIN_SUCCESS);
						} else {
							ls = new LoginStatus(null, Status.INCORRECT_PASSWORD);
						}
					} else {
						ls = new LoginStatus(null, Status.INACTIVE_USER);
					}
				} else {
					ls = new LoginStatus(null, Status.INCORRECT_USERNAME);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ls = new LoginStatus(null, Status.EXCEPTION_OCCURED);
			}
		}
		return ls;
	}
	
	public static void logout(AuthToken authToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				st.executeUpdate("update emp set remember='n', auth_token='"+Utility.getRandomString(6)+"' where id="+authToken.getId());
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		}
	}

	// call this method to authorize an employee
	public static boolean authorize(AuthToken authToken, boolean remember, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select auth_token, active, type, remember, name from emp where id=" + authToken.getId());
				if (rs.next()) {
					if (remember) {
						if (rs.getString(1).equals(authToken.getAuthToken()) && rs.getString(2).charAt(0) == 'y' && Status.parseEmployee(rs.getString(3).charAt(0)) == authToken.getType() && rs.getString(4).charAt(0) == 'y') {
							authToken.setName(rs.getString(5));
							return true;
						} else {
							return false;
						}
					} else if (rs.getString(1).equals(authToken.getAuthToken()) && rs.getString(2).charAt(0) == 'y' && Status.parseEmployee(rs.getString(3).charAt(0)) == authToken.getType()) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// call this method to change fp_token
	public static String forgotPassword(String username, Connection con) {
		String random = null;
		synchronized (con) {
			try {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select id from emp where username='" + username + "'");
				if (rs.next()) {
					int id = rs.getInt(1);
					random = Utility.getRandomString(20);
					st.executeUpdate("update emp set fp_token='" + random + "' where id=" + id);
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return random;
	}

	// change Password
	public static LoginStatus changePassword(String fp_token, int id, String password, Connection con) {
		LoginStatus ls = null;
		synchronized (con) {
			try {
				con.setCatalog("employee");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select fp_token, type from emp where id=" + id);
				if (rs.next()) {
					if (rs.getString(1).equals(fp_token)) {
						password = Password.getSaltedHash(password);
						AuthToken authToken = new AuthToken();
						authToken.setAuthToken(Utility.getRandomString(6));
						authToken.setId(id);
						authToken.setType(Status.parseEmployee(rs.getString(2).charAt(0)));
						st.executeUpdate("update emp set password='" + password + "', auth_token='" + authToken.getAuthToken() + "' where id=" + id);
						ls = new LoginStatus(authToken, Status.SUCCESS);
					} else {
						ls = new LoginStatus(null, Status.INCORRECT_TOKEN);
					}
				} else {
					ls = new LoginStatus(null, Status.INCORRECT_USERNAME);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ls = new LoginStatus(null, Status.EXCEPTION_OCCURED);
			}
		}
		return ls;
	}

	//	Get employee list
	public static ArrayList<Employee> getEmployeeList(AuthToken authToken, Connection con) {
		ArrayList<Employee> list = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER) {
			synchronized (con) {
				try {
					con.setCatalog("employee");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from emp;");
					while (rs.next()) {
						Employee emp = new Employee();
						emp.setName(rs.getString("name"));
						emp.setMobile(rs.getString("mobile"));
						emp.setEmail(rs.getString("email"));
						emp.setGender(rs.getString("gender").charAt(0));
						emp.setType(rs.getString("type").charAt(0));
						emp.setActive(rs.getString("active").charAt(0));
						emp.setId(rs.getInt("id"));
						list.add(emp);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		} else {
			return null;
		}
		return list;
	}

	//	Activate/Deactivate Employee
	public static Status alterStatusOfEmployee(int id, boolean activate, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER) {
			if (authToken.getId() != id) {
				synchronized (con) {
					try {
						con.setCatalog("employee");
						Statement st = con.createStatement();
						st.executeUpdate("update emp set active='" + (activate ? "y" : "n") + "' where id=" + id);
					} catch (SQLException ex) {
						ex.printStackTrace();
						return Status.EXCEPTION_OCCURED;
					}
				}
			} else {
				return Status.UNAUTHORIZED_OPERATION;
			}
		} else {
			return Status.NOT_MANAGER;
		}
		return Status.SUCCESS;
	}

	//	call this method to view employee detail
	public static Employee getEmpDetail(int id, AuthToken authToken, Connection con) {
		Employee emp = null;
		if (authToken.getType() == Status.MANAGER) {
			synchronized (con) {
				try {
					con.setCatalog("employee");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from emp where id=" + id);
					if (rs.next()) {
						emp = new Employee();
						emp.setName(rs.getString("name"));
						emp.setType(rs.getString("type").charAt(0));
						emp.setPhoto(rs.getString("photo"));
						emp.setEmail(rs.getString("email"));
						emp.setMobile(rs.getString("mobile"));
						emp.setStreet(rs.getString("house"));
						emp.setColony(rs.getString("colony"));
						emp.setLandmark(rs.getString("landmark"));
						emp.setCity(rs.getString("city"));
						emp.setZip(rs.getInt("zip"));
						emp.setState(rs.getString("state"));
						emp.setActive(rs.getString("active").charAt(0));
						emp.setDob(rs.getString("dob"));
						emp.setGender(rs.getString("gender").charAt(0));
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return emp;
	}
}
