package business.customer;

import business.Status;
import business.Utility;
import business.stock.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {

	public static Result signupUser(String username, String name, String email, Connection con) {
		Result result = new Result();
		if (checkUsername(username, con)) {
			synchronized (con) {
				try {
					con.setCatalog("customer");
					Statement st = con.createStatement();
					result.setToken(Utility.getRandomString(20));
					st.executeUpdate("insert into user (username, name, email, fp_token) values('" + username + "', '" + name + "', '" + email + "', '" + result.getToken() + "')");
					result.setStatus(Status.SUCCESS);
				} catch (SQLException ex) {
					ex.printStackTrace();
					result.setStatus(Status.EXCEPTION_OCCURED);
				}
			}
		} else {
			result.setStatus(Status.FAILED);
		}
		return result;
	}

	public static boolean checkUsername(String username, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select count(*) from user where username='" + username + "'");
				if (rs.next()) {
					if (rs.getInt(1) > 0) {
						return false;
					} else {
						return true;
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public static Status changePassword(String password, String username, String fpToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select fp_token from user where username='" + username + "'");
				if (rs.next()) {
					if (rs.getString("fp_token").equals(fpToken)) {
						password = business.hashing.Password.getSaltedHash(password);
						st.executeUpdate("update user set password='" + password + "', fp_token='" + Utility.getRandomString(20) + "' where username='" + username + "'");
					} else {
						return Status.UNAUTHORIZED_OPERATION;
					}
				} else {
					return Status.USER_UNAVAILABLE;
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				return Status.EXCEPTION_OCCURED;
			} catch (Exception ex) {
				ex.printStackTrace();
				return Status.EXCEPTION_OCCURED;
			}
		}
		return Status.SUCCESS;
	}

	public static LoginStatus login(String username, String password, boolean remember,Connection con) {
		LoginStatus ls = null;
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select password, id, name from user where username='" + username + "'");
				if (rs.next()) {
					String pass = rs.getString(1);
					int id = rs.getInt(2);
					String name = rs.getString(3);
					if (business.hashing.Password.check(password, pass)) {
						st.executeUpdate("update user set remember='"+(remember?"y":"n")+"' where id="+id);
						CustomerAuthToken authToken = new CustomerAuthToken();
						authToken.setId(id);
						authToken.setName(name);
						ls = new LoginStatus(authToken, Status.SUCCESS);
					} else {
						ls = new LoginStatus(null, Status.INCORRECT_PASSWORD);
					}
				} else {
					ls = new LoginStatus(null, Status.INCORRECT_USERNAME);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				ls = new LoginStatus(null, Status.EXCEPTION_OCCURED);
			} catch (Exception ex) {
				ex.printStackTrace();
				ls = new LoginStatus(null, Status.EXCEPTION_OCCURED);
			}
		}
		return ls;
	}
	public static Customer getUserDetail(CustomerAuthToken authToken, Connection con) {
		Customer customer = new Customer();
		synchronized ( con ) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from user where id="+authToken.getId());
				if ( rs.next() ) {
					customer.setName(rs.getString("name"));
					customer.setContact(rs.getLong("contact"));
					customer.setDob(rs.getString("dob"));
					customer.setEmail(rs.getString("email"));
					customer.setId(authToken.getId());
					customer.setUsername(rs.getString("username"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		}
		return customer;
	}
	public static boolean updateUserDetails(String dob, String email, long mobile, CustomerAuthToken authToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				int status = st.executeUpdate("update user set dob='"+dob+"', email='"+email+"', contact="+mobile+" where id="+authToken.getId());
				if ( status != 0 )
					return  true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		}
		return false;
	}
}
