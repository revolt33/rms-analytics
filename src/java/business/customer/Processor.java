package business.customer;

import business.Status;
import business.Utility;
import business.sales.Order;
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

	public static Status changePassword(AuthToken authToken, String old, String password, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select password from user where id=" + authToken.getId());
				if (rs.next()) {
					if (business.hashing.Password.check(old, rs.getString("password"))) {
						password = business.hashing.Password.getSaltedHash(password);
						st.executeUpdate("update user set password='" + password + "' where id=" + authToken.getId());
					} else {
						return Status.INCORRECT_PASSWORD;
					}
				} else {
					return Status.USER_UNAVAILABLE;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
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

	public static Cart checkout(AuthToken authToken, ArrayList<CartItem> list, String coupon, Status mode, int address_code, Connection con) {
		Cart cart = new Cart();
		synchronized (con) {
			try {
				con.setCatalog("stock");
				PreparedStatement ps = con.prepareStatement("select * from item join item_config join price on item=item.id and item_config.price=price.serial where item_config.id=?");
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
						item.setPrice(rs.getFloat("price.value"));
						item.setPriceId(rs.getInt("price.serial"));
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
						cart.setDiscountId(rs.getInt("id"));
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
											if ((temp + off) >= max) {
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
							off = cart.getTotal() * discount;
							if (off > max) {
								off = max;
							}
						}
						cart.setEffectiveValue(cart.getTotal() - off);
						cart.setDiscount(off);
					}
				}
				switch (mode) {
					case DELIVERY:
						Address address = getAddress(authToken, address_code, con);
						if (address.getId() != 0) {
							cart.setHavingAddress(true);
							cart.setAddress(address);
						}
						break;
					case PICKUP:
						cart.setHavingPickup(true);
						break;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				cart.flush();
			}
		}
		return cart;
	}

	public static boolean placeOrder(AuthToken authToken, ArrayList<CartItem> list, String coupon, Status mode, int address_code, Connection con) {
		Cart cart = checkout(authToken, list, coupon, mode, address_code, con);
		if (cart.getSize() > 0) {
			Order order = new Order();
			order.setValue(cart.getTotal());
			if (cart.isCouponValid()) {
				order.setEffectiveValue(cart.getEffectiveValue());
				order.setCouponApplied(true);
			} else {
				order.setEffectiveValue(cart.getTotal());
			}
			order.setDeliveryMode(mode);
			order.setCustomer(authToken.getId());
			order.setDiscountCode(cart.getCoupon());
			if ( null != mode) switch (mode) {
				case DELIVERY:
					order.setCustomerName(cart.getAddress().getName());
					order.setAddress(cart.getAddress().getAddress() + " - " + cart.getAddress().getPin() + " Near: "+cart.getAddress().getLandmark());
					order.setContact(cart.getAddress().getContact());
					if ( address_code == 0 )
						return false;
					break;
				case PICKUP:
					order.setCustomerName(authToken.getName());
					order.setContact(getUserDetail(authToken, con).getContact());
					break;
				default:
					return false;
			}
			order.setDeliveryMode(mode);
			order.setMode(Status.CUSTOMER);
			order.setPaymentStatus(Status.AWAITED);
			if ( cart.isCouponValid() ) {
				order.setCouponApplied(true);
				order.setDiscount(cart.getDiscountId());
			}
			order.setTimestamp(application.Utility.getTimestamp());
			order.setOrderSatatus(Status.PENDING);
			for (CartItem item : cart.getList()) {
				Order.Info info = order.new Info();
				info.setPriceId(item.getPriceId());
				info.setQuantity(item.getQuantity());
				order.addInfo(info);
			}
			return business.sales.Processor.placeOrder(order, con);
		} else {
			return false;
		}
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

	public static boolean addAddress(Address address, AuthToken authToken, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				st.executeUpdate("insert into address (user, name, house, street, landmark, city, pin, contact) values(" + authToken.getId() + ", '" + address.getName() + "', '" + address.getHouse() + "', '" + address.getStreet() + "', '" + address.getLandmark() + "', '" + address.getCity() + "', " + address.getPin() + ", " + address.getContact() + ")");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static ArrayList<Address> getAddressList(AuthToken authToken, Connection con) {
		ArrayList<Address> list = new ArrayList<>();
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from address where user=" + authToken.getId());
				while (rs.next()) {
					Address address = new Address();
					address.setName(rs.getString("name"));
					address.setHouse(rs.getString("house"));
					address.setStreet(rs.getString("street"));
					address.setCity(rs.getString("city"));
					address.setLandmark(rs.getString("landmark"));
					address.setPin(rs.getInt("pin"));
					address.setId(rs.getInt("id"));
					address.setContact(rs.getLong("contact"));
					list.add(address);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	public static Address getAddress(AuthToken authToken, int id, Connection con) {
		Address address = new Address();
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from address where id=" + id + " and user=" + authToken.getId());
				if (rs.next()) {
					address.setName(rs.getString("name"));
					address.setHouse(rs.getString("house"));
					address.setStreet(rs.getString("street"));
					address.setCity(rs.getString("city"));
					address.setLandmark(rs.getString("landmark"));
					address.setPin(rs.getInt("pin"));
					address.setId(rs.getInt("id"));
					address.setContact(rs.getLong("contact"));
				} else {
					address.setId(0);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return address;
	}

	public static boolean editAddress(AuthToken authToken, Address address, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				st.executeUpdate("update address set name='" + address.getName() + "', house='" + address.getHouse() + "', street='" + address.getStreet() + "', landmark='" + address.getLandmark() + "', city='" + address.getCity() + "', pin=" + address.getPin() + ", contact=" + address.getContact() + " where id=" + address.getId() + " and user=" + authToken.getId());
				return true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
		return false;
	}

	public static boolean deleteAddress(AuthToken authToken, int id, Connection con) {
		synchronized (con) {
			try {
				con.setCatalog("customer");
				Statement st = con.createStatement();
				st.executeUpdate("delete from address where id=" + id + " and user=" + authToken.getId());
				return true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public static ArrayList<Order> getOrderHistory(AuthToken authToken, Connection con) {
		ArrayList<Order> list = new ArrayList<>();
		synchronized (con) {
			try {
				con.setCatalog("sales");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from bill left outer join discount on bill.discount_code=discount.id where customer=" + authToken.getId() + " order by timestamp desc LIMIT 10");
				while (rs.next()) {
					Order order = new Order();
					order.setId(rs.getInt("bill.id"));
					order.setValue(rs.getFloat("value"));
					order.setEffectiveValue(rs.getFloat("effective_value"));
					order.setAddress(rs.getString("address"));
					order.setDeliveryMode(Status.parseDeliveryMode(rs.getString("delivery_mode").charAt(0)));
					order.setDiscountCode(rs.getString("code"));
					order.setCustomerName(rs.getString("customer_name"));
					order.setOrderSatatus(Status.parseOrderAttendanceStatus(rs.getString("bill.status").charAt(0)));
					order.setTimestamp(rs.getLong("timestamp"));
					order.setContact(rs.getLong("contact"));
					order.setEstimatedDelivery(rs.getInt("estimated_time"));
					list.add(order);
				}
			} catch (SQLException ex) {
				list.clear();
				ex.printStackTrace();
			}

		}
		return list;
	}
	
	public static Order getOrder(AuthToken authToken, int id, Connection con) {
		Order order = new Order();
		synchronized ( con ) {
			try {
				con.setCatalog("sales");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from bill left outer join discount on discount_code=discount.id where bill.id="+id+" and customer="+authToken.getId());
				if ( rs.next() ) {
					order.setAddress(rs.getString("address"));
					order.setContact(rs.getLong("contact"));
					order.setCustomerName(rs.getString("customer_name"));
					order.setDiscountCode(rs.getString("code"));
					order.setValue(rs.getFloat("value"));
					order.setEffectiveValue(rs.getFloat("effective_value"));
					order.setTimestamp(rs.getLong("timestamp"));
					order.setDeliveryMode(Status.parseDeliveryMode(rs.getString("delivery_mode").charAt(0)));
					order.setOrderSatatus(Status.parseOrderAttendanceStatus(rs.getString("bill.status").charAt(0)));
					order.setEstimatedDelivery(rs.getLong("estimated_time"));
					rs = st.executeQuery("select bill_info.quantity, price.value, item_config.name, item_config.type, item.name from bill_info join stock.price as price natural join stock.item_config as item_config join stock.item as item on bill_info.price=price.serial and item_config.item=item.id where bill_info.id="+id);
					while ( rs.next() ) {
						Order.Info info = order.new Info();
						info.setQuantity(rs.getInt("quantity"));
						info.setPrice(rs.getFloat("value"));
						info.setName(rs.getString("item.name")+(rs.getString("type").equals("o")?(" ("+rs.getString("item_config.name")+")"):""));
						order.addInfo(info);
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return order;
	}
}
