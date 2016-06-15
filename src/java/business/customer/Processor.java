package business.customer;

import business.Status;
import business.Utility;
import business.stock.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
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

	public static LoginStatus login(String username, String password, boolean remember, Connection con) {
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
						String token = Utility.getRandomString(6);
						st.executeUpdate("update user set auth_token='" + token + "', remember='" + (remember ? "y" : "n") + "' where id=" + id);
						AuthToken authToken = new AuthToken();
						authToken.setId(id);
						authToken.setAuthToken(token);
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

	public static Customer getUserDetail(AuthToken authToken, Connection con) {
		Customer customer = new Customer();
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from user where id=" + authToken.getId());
				if (rs.next()) {
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

	public static boolean updateUserDetails(String dob, String email, long mobile, AuthToken authToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				int status = st.executeUpdate("update user set dob='" + dob + "', email='" + email + "', contact=" + mobile + " where id=" + authToken.getId());
				if (status != 0) {
					return true;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
		return false;
	}

	public static Cart checkout(ArrayList<CartItem> list, String coupon, Connection con) {
		Cart cart = new Cart();
		synchronized (con) {
			try {
				con.setCatalog("stock");
				PreparedStatement ps = con.prepareStatement("select * from item join item_config on item=item.id where item_config.id=?");
				PreparedStatement ps1 = con.prepareStatement("select max(value) as price from price where id=?");
				for (CartItem item : list) {
					ps.setInt(1, item.getUnit());
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						if (rs.getString("active").equals("y") && rs.getString("status").equals("a")) {
							if (rs.getString("on_order").equals("n") && Float.parseFloat(rs.getString("stock")) < (Float.parseFloat(rs.getString("fraction")) * item.getQuantity())) {
								item.setAvailable(false);
							}
						} else {
							item.setValid(false);
						}
						if (rs.getString("type").equals("d")) {
							item.setName(rs.getString("item.name"));
						} else {
							item.setName(rs.getString("item.name") + " (" + rs.getString("item_config.name") + ")");
						}
						item.setItem(rs.getInt("item.id"));
						ps1.setInt(1, item.getUnit());
						rs = ps1.executeQuery();
						if (rs.next()) {
							item.setPrice(Float.parseFloat(rs.getString("price")));
						} else {
							item.setValid(false);
						}
						cart.addCartItem(item);
					}
				}
				if (coupon != null && coupon.trim().length() > 0) {
					cart.setCouponApplied(true);
					cart.setCoupon(coupon);
					con.setCatalog("sales");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from discount where code='" + coupon + "'");
					if (rs.next()) {
						float max = rs.getFloat("max");
						float discount = rs.getFloat("percentage") / 100;
						float off = 0f;
						cart.setCouponValid(true);
						Status type = Status.parseDiscountApplicable(rs.getString("applicable_to").charAt(0));
						if (type == Status.ITEM_LIST) {
							rs = st.executeQuery("select * from discount_list where discount=" + rs.getInt("id"));
							float temp;
							label:
							while (rs.next()) {
								int id = rs.getInt("id");
								for (CartItem item : cart.getList()) {
									if (item.isAvailable() && item.isValid()) {
										if (id == item.getItem()) {
											temp = item.getPrice() * item.getQuantity() * discount;
											if ( (temp + off) >= max) {
												off = max;
												break label;
											} else {
												off += temp;
											}
										}
									}
								}
							}
						} else if (type == Status.All_ITEMS) {
							off = cart.getTotal()*discount;
							if ( off > max ) {
								off = max;
							}
						}
						cart.setEffectiveValue(cart.getTotal()-off);
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				cart.flush();
			}
		}
		return cart;
	}

	public static boolean authorize(AuthToken authToken, boolean remember, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select auth_token, remember, name from user where id=" + authToken.getId());
				if (rs.next()) {
					if (authToken.getAuthToken().equals(rs.getString(1))) {
						if (remember) {
							if (rs.getString(2).charAt(0) == 'y') {
								authToken.setName(rs.getString("name"));
								return true;
							}
						} else {
							return true;
						}
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public static ArrayList<Coupon> browseCoupons(Connection con) {
		ArrayList<Coupon> coupons = new ArrayList<>();
		synchronized (con) {
			try {
				con.setCatalog("sales");
				Statement st = con.createStatement();
				PreparedStatement ps = con.prepareStatement("select name from discount_list natural join stock.item where discount=?");
				Calendar timestamp = Calendar.getInstance();
				timestamp.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
				ResultSet rs = st.executeQuery("select id, code, applicable_to, max, percentage from discount where type='e' and begin<=" + timestamp.getTimeInMillis() + " and end>=" + timestamp.getTimeInMillis());
				while (rs.next()) {
					Coupon coupon = new Coupon();
					coupon.setCode(rs.getString("code"));
					coupon.setApplicable(Status.parseDiscountApplicable(rs.getString("applicable_to").charAt(0)));
					coupon.setMax(rs.getFloat("max"));
					coupon.setPercentage(rs.getFloat("percentage"));
					if (coupon.getApplicable() == Status.ITEM_LIST) {
						ps.setInt(1, rs.getInt("id"));
						ResultSet resultSet = ps.executeQuery();
						StringBuilder items = new StringBuilder();
						while (resultSet.next()) {
							if (items.length() > 0) {
								items.append(", ");
							}
							items.append(resultSet.getString("name"));
						}
						coupon.setItems(items.toString());
					}
					coupons.add(coupon);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return coupons;
	}

	public static void logout(AuthToken authToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				st.executeUpdate("update user set remember='n', auth_token='" + Utility.getRandomString(6) + "' where id=" + authToken.getId());
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}
}
