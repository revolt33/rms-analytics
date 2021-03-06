package business.stock;

import application.Utility;
import business.employee.*;
import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;
import business.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {

	// To add a category in stock database, call this method
	public static Status addCategory(String name, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("employee");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select active from emp where id=" + authToken.getId());
					if (rs.next()) {
						if (rs.getString(1).charAt(0) == 'y') {
							con.setCatalog("stock");
							st = con.createStatement();
							st.executeUpdate("insert into category (name, added_by) values('" + name + "', " + authToken.getId() + ")");
						} else {
							return Status.EXCEPTION_OCCURED;
						}
					} else {
						return Status.USER_UNAVAILABLE;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	//	call this method to edit a category
	public static Status editCategory(int id, String category, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("update category set name='" + category + "' where id=" + id);
				} catch (SQLException ex) {
					ex.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	//	getCategories by calling this method
	public static ArrayList<Category> getCategories(Connection con) {
		ArrayList<Category> list = new ArrayList<>();
		synchronized (con) {
			try {
				con.setCatalog("stock");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from category order by active desc");
				PreparedStatement ps = con.prepareStatement("select * from stock.item as items join employee.emp as employees on items.added_by=employees.id where category=? order by items.name");
				PreparedStatement ps1 = con.prepareStatement("select item_config.id, item_config.name, item_config.type, price.value, item_config.status from item_config join price on item_config.price=price.serial where item=?");
				while (rs.next()) {
					Category category = new Category(rs.getString(2), rs.getInt(1), rs.getString(3).charAt(0));
					ps.setInt(1, rs.getInt(1));
					ResultSet rsItem = ps.executeQuery();
					while (rsItem.next()) {
						Item item = new Item();
						item.setCategory(rs.getInt(1));
						item.setId(rsItem.getInt("items.id"));
						item.setName(rsItem.getString("items.name"));
						item.setDescription(rsItem.getString("description"));
						item.setStock(rsItem.getFloat("stock"));
						item.setOneStar(rsItem.getInt("count1"));
						item.setTwoStar(rsItem.getInt("count2"));
						item.setThreeStar(rsItem.getInt("count3"));
						item.setFourStar(rsItem.getInt("count4"));
						item.setfiveStar(rsItem.getInt("count5"));
						item.setMinPercentage(rsItem.getFloat("min_percentage"));
						item.setOnOrder(rsItem.getString("on_order").charAt(0));
						item.setDelivery(rsItem.getString("delivery").charAt(0));
						item.setImage(rsItem.getString("image"));
						item.setActive(rsItem.getString("active").charAt(0));
						item.setAddedBy(rsItem.getString("employees.name"));
						ps1.setInt(1, rsItem.getInt("items.id"));
						ResultSet result = ps1.executeQuery();
						while (result.next()) {
							ItemUnit unit = new ItemUnit();
							unit.setId(result.getInt("item_config.id"));
							unit.setName(result.getString("item_config.name"));
							unit.setType(result.getString("item_config.type").charAt(0));
							unit.setStatus(result.getString("item_config.status").charAt(0));
							unit.setPrice(result.getFloat("price.value"));
							item.addItemUnits(unit);
						}
						category.addItem(item);
					}
					list.add(category);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	//	to revoke a category call this
	public static Status alterStatusOfCategory(int id, boolean active, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("update category set active='" + (active ? "y" : "n") + "' where id=" + id);
				} catch (SQLException ex) {
					ex.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// To add an item, call this method
	public static Result addItem(Item item, AuthToken authToken, Connection con) {
		Result result = new Result();
		result.setStatus(Status.ERROR_OCCURED);
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				boolean rollback = false;
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.execute("start transaction;");
					st.executeUpdate("insert into item (name, description, min_percentage, category, on_order, delivery,added_by) values('" + item.getName() + "', '" + item.getDescription() + "', " + item.getMinPercentage() + ", " + item.getCategory() + ", '" + (item.getOnOrder() ? 'y' : 'n') + "', '" + (item.getDelivery() ? 'y' : 'n') + "', " + authToken.getId() + ");");
					ResultSet rs = st.executeQuery("select LAST_INSERT_ID()");
					if (rs.next()) {
						result.setCode(rs.getInt(1));
						st.executeUpdate("insert into item_config (name, fraction, type, status, item, added_by) values('Default', 1, 'd', 'a', " + rs.getInt(1) + ", " + authToken.getId() + ")");
						rs = st.executeQuery("select LAST_INSERT_ID()");
						if (rs.next()) {
							int unit = rs.getInt(1);
							st.executeUpdate("insert into price (id, value, timestamp, added_by) values(" + unit + ", " + item.getPrice() + ", " + Utility.getTimestamp() + ", " + authToken.getId() + ")");
							rs = st.executeQuery("select LAST_INSERT_ID()");
							if (rs.next()) {
								st.executeUpdate("update item_config set price=" + rs.getInt(1) + " where id=" + unit);
							} else {
								rollback = true;
							}
						} else {
							rollback = true;
						}
						st.executeUpdate("update item set image='img" + result.getCode() + item.getImageExt() + "' where id=" + result.getCode() + ";");
					} else {
						rollback = true;
					}
					if (rollback) {
						st.execute("rollback");
					} else {
						st.execute("commit;");
						result.setStatus(Status.SUCCESS);
					}
				} catch (Exception e) {
					try {
						st.execute("rollback");
					} catch (SQLException | NullPointerException ex) {
						ex.printStackTrace();
					}
					e.printStackTrace();
					result.setStatus(Status.EXCEPTION_OCCURED);
				}
			}
		} else {
			result.setStatus(Status.INVALID_USER_TYPE);
		}
		return result;
	}

	// call this method to make an item available for purchase
	public static Status alterStatusOfItem(int id, boolean publish, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("update item set active='" + (publish ? "y" : "n") + "' where id=" + id);
				} catch (SQLException e) {
					e.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to update an item detail
	public Status alterItem(Item item, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("update item set name='" + item.getName() + "', image='" + item.getImage() + "', description='" + item.getDescription() + "', min_percentage=" + item.getMinPercentage() + ", on_order='" + item.getOnOrder() + "', delivery='" + item.getDelivery() + "' where id=" + item.getId());
				} catch (SQLException e) {
					e.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to increse stock of an item
	public static Result addItemInStock(int id, float count, long expiry, boolean override, AuthToken authToken, Connection con) {
		Result result = new Result();
		result.setStatus(Status.SUCCESS);
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.execute("start transaction;");
					st.executeUpdate("insert into item_entry (timestamp, expiry, current, count, serial, added_by) values(" + Utility.getTimestamp() + ", " + expiry + ", (select stock from item where id=" + id + "), " + count + ", " + id + ", " + authToken.getId() + ");");
					st.executeUpdate("update item set stock=stock+" + count + "where id=" + id + ";");
					ResultSet rs = st.executeQuery("select LAST_INSERT_ID();");
					if (rs.next()) {
						result.setCode(rs.getInt(1));
					}
					rs = st.executeQuery("select units, ingridient, stock from relation join ingridients on ingridient=ingridients.id where item=" + id);
					boolean proceed = true;
					while (rs.next()) {
						if (count * rs.getFloat(1) <= rs.getFloat(3)) {
							consumeIngridientStock(rs.getInt(2), count * rs.getFloat(1), st);
						} else if (override) {
							st.clearBatch();
							break;
						} else {
							st.clearBatch();
							st.addBatch("rollback;");
							proceed = false;
							result.setStatus(Status.INSUFFICIENT_STOCK); //Insufficient Ingridients
							break;
						}
					}
					if (proceed) {
						st.addBatch("commit;");
					}
					st.executeBatch();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						st.execute("rollback;");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					result.setStatus(Status.EXCEPTION_OCCURED);
				}
			}
		} else {
			result.setStatus(Status.INVALID_USER_TYPE);
		}
		return result;
	}

	// call this method to reduce an item in Stock
	public static Status reduceStock(int batch, int id, float amount, String table, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					ResultSet rs = st.executeQuery("select stock from " + table + " where id=" + id);
					if (rs.next()) {
						if (rs.getFloat(1) > amount) {
							if (batch > 0) {
								rs = st.executeQuery("select count, consumed from " + table + "_entry where id=" + batch + " and status='a';");
								if (rs.next()) {
									if (amount <= (rs.getFloat(1) - rs.getFloat(2))) {
										st.execute("start transaction;");
										st.executeUpdate("update " + table + " set stock=stock-" + amount + " where id=" + id);
										st.executeUpdate("update " + table + "_entry set consumed=consumed+" + amount + " where id=" + batch);
										st.execute("commit;");
									} else {
										return Status.INSUFFICIENT_BATCH_STOCK;
									}
								} else {
									return Status.BATCH_NOT_FOUND;
								}
							} else {
								st.execute("start transaction;");
								st.executeUpdate("update " + table + " set stock=stock-" + amount + " where id=" + id);
								st.execute("SET @amount = " + amount + ";");
								st.execute("SET @debit = 0;");
								st.executeUpdate("update " + table + "_entry set consumed=CASE WHEN @debit>0 THEN consumed+(@amount := 0)+(@debit := 0) WHEN @amount=0 THEN consumed WHEN @amount>=(count-consumed) THEN (@amount := @amount-(count-consumed))*0+count WHEN @amount<(count-consumed) THEN consumed+@amount+(@debit := 1)*0 END where serial=" + id + " and count>consumed and status='a' order by expiry;");
								st.execute("commit;");
							}
						} else {
							return Status.INSUFFICIENT_STOCK;
						}
					} else {
						return Status.ITEM_NOT_FOUND;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					try {
						st.execute("rollback;");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	//	call this method to get price variation
	public static ArrayList<DataRender> getPriceHistory(int id, AuthToken authToken, Connection con) {
		ArrayList<DataRender> list = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from stock.price join employee.emp on added_by=emp.id where price.id=" + id + " order by timestamp desc");
					while (rs.next()) {
						DataRender data = new DataRender();
						data.setDate(rs.getLong("timestamp"));
						data.setFloatData(rs.getFloat("value"));
						data.setDoubleData(rs.getDouble("count"));
						data.setString(rs.getString("emp.name"));
						list.add(data);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			list = null;
		}
		return list;
	}

	// call this method to get item unit list
	public static ArrayList<Category> getItemConfig(AuthToken authToken, Connection con) {
		ArrayList<Category> categories = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from category");
					PreparedStatement ps = con.prepareStatement("select id, name from item where category=?");
					PreparedStatement ps1 = con.prepareStatement("select item_config.id, item_config.name, item_config.fraction, item_config.type, item_config.status, emp.name from item_config join employee.emp on added_by=emp.id where item=?");
					while (rs.next()) {
						Category cat = new Category(rs.getString("name"), rs.getInt("id"), rs.getString("active").charAt(0));
						ps.setInt(1, rs.getInt("id"));
						ResultSet resultSet = ps.executeQuery();
						while (resultSet.next()) {
							Item item = new Item();
							item.setName(resultSet.getString("name"));
							item.setId(resultSet.getInt("id"));
							ps1.setInt(1, resultSet.getInt("id"));
							ResultSet result = ps1.executeQuery();
							while (result.next()) {
								ItemUnit unit = new ItemUnit();
								unit.setId(result.getInt("item_config.id"));
								unit.setName(result.getString("item_config.name"));
								unit.setFraction(result.getFloat("item_config.fraction"));
								unit.setType(result.getString("type").charAt(0));
								unit.setStatus(result.getString("status").charAt(0));
								unit.setAdded_by(result.getString("emp.name"));
								item.addItemUnits(unit);
							}
							cat.addItem(item);
						}
						categories.add(cat);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			categories = null;
		}
		return categories;
	}

	// alter status of ItemUnit
	public static Status alterStatusOfUnit(int id, boolean active, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("update item_config set status='" + (active ? 'a' : 'r') + "' where id=" + id);
				} catch (SQLException ex) {
					ex.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to add item unit
	public static Status addItemUnit(int item, String name, float fraction, float price, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				boolean rollback = false;
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.execute("start transaction");
					st.executeUpdate("insert into item_config (name, item, fraction, type, status, added_by) values('" + name + "', " + item + ", " + fraction + ", 'o', 'a', " + authToken.getId() + ")");
					ResultSet resultSet = st.executeQuery("SELECT LAST_INSERT_ID();");
					if (resultSet.next()) {
						int unit = resultSet.getInt(1);
						st.executeUpdate("insert into price (id, value, timestamp, added_by) values(" + unit + ", " + price + ", " + Utility.getTimestamp() + ", " + authToken.getId() + ")");
						resultSet = st.executeQuery("SELECT LAST_INSERT_ID();");
						if (resultSet.next()) {
							st.executeUpdate("update item_config set price=" + resultSet.getInt(1) + " where id=" + unit);
						} else {
							rollback = true;
						}
					} else {
						rollback = true;
					}
					if ( rollback ) {
						st.execute("rollback");
					} else {
						st.execute("commit");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					try {
						st.execute("rollback");
					} catch ( NullPointerException | SQLException ex1) {
						ex1.printStackTrace();
					}
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to change price of an item
	public static Status changePrice(int id, float value, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				boolean rollback = false;
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.execute("start transaction");
					st.executeUpdate("insert into price (id, value, timestamp, added_by) values(" + id + ", " + value + ", " + Utility.getTimestamp() + ", " + authToken.getId() + ")");
					ResultSet resultSet = st.executeQuery("SELECT LAST_INSERT_ID();");
					if (resultSet.next()) {
						st.executeUpdate("update item_config set price=" + resultSet.getInt(1) + " where id=" + id);
					} else {
						rollback = true;
					}
					if (rollback) {
						st.execute("rollback");
					} else {
						st.execute("commit");
					}
				} catch (SQLException ex) {
					try {
						st.execute("rollback");
					} catch (NullPointerException | SQLException ex1) {
						ex1.printStackTrace();
					}
					ex.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to remove a batch
	public static Status removeBatch(int id, String table, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.execute("start transaction;");
					st.execute("SET @id = 0;");
					st.addBatch("SET @debit = 0;");
					st.executeUpdate("update " + table + "_entry set status='r', serial=CASE WHEN (@id:=serial)+(@debit:=count-consumed) THEN serial ELSE serial END where status='a' and id=" + id);
					st.executeUpdate("update " + table + " set stock=stock-@debit where id=@id;");
					st.execute("commit;");
				} catch (SQLException e) {
					e.printStackTrace();
					try {
						st.execute("rollback;");
					} catch (SQLException ex) {
						ex.printStackTrace();
						return Status.EXCEPTION_OCCURED;
					}
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	//	call this method to get list of ingridients
	public static ArrayList<Ingredient> getIngridients(AuthToken authToken, Connection con) {
		ArrayList<Ingredient> list = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from stock.ingridients as ing join employee.emp as emp on ing.added_by=emp.id;");
					while (rs.next()) {
						Ingredient ingridient = new Ingredient();
						ingridient.setId(rs.getInt("ing.id"));
						ingridient.setName(rs.getString("ing.name"));
						ingridient.setState(rs.getString("ing.state").charAt(0));
						ingridient.setStock(rs.getDouble("ing.stock"));
						ingridient.setAddedBy(rs.getString("emp.name"));
						list.add(ingridient);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			list = null;
		}
		return list;
	}

	// call this method to add an ingridient
	public static Status addIngridient(Ingredient ingridient, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("insert into ingridients (name, state, added_by) values('" + ingridient.getName() + "', '" + ingridient.getState() + "', " + authToken.getId() + ")");
				} catch (Exception e) {
					e.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this mehtod to add an ingridient stock
	public static Result addIngridientStock(int id, float count, long expiry, AuthToken authToken, Connection con) {
		Result result = new Result();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				Statement st = null;
				try {
					con.setCatalog("stock");
					st = con.createStatement();
					st.addBatch("start transaction;");
					st.addBatch("insert into ingridients_entry (expiry, timestamp, count, serial, current, added_by) values(" + expiry + ", " + Utility.getTimestamp() + "," + count + ", " + id + ", (select stock from ingridients where id=" + id + "), " + authToken.getId() + ");");
					st.addBatch("update ingridients set stock=stock+" + count + " where id=" + id + ";");
					st.addBatch("commit;");
					st.executeBatch();
					ResultSet rs = st.executeQuery("select LAST_INSERT_ID();");
					if (rs.next()) {
						result.setCode(rs.getInt(1));
						result.setStatus(Status.SUCCESS);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					result.setStatus(Status.EXCEPTION_OCCURED);
					try {
						st.execute("rollback;");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else {
			result.setStatus(Status.INVALID_USER_TYPE);
		}
		return result;
	}

	//	call this method to get ingridient entry list
	public static ArrayList<Entry> getIngridientEntry(int id, String table, AuthToken authToken, Connection con) {
		ArrayList<Entry> list = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					ResultSet result = st.executeQuery("select * from stock." + table + "_entry as ing join employee.emp as empl on ing.added_by=empl.id where ing.serial=" + id + " order by status, expiry;");
					while (result.next()) {
						Entry entry = new Entry();
						entry.setId(result.getInt("ing.id"));
						entry.setAmount(result.getFloat("ing.count"));
						entry.setConsumed(result.getFloat("ing.consumed"));
						entry.setExpiry(result.getLong("ing.expiry"));
						entry.setTimestamp(result.getLong("ing.timestamp"));
						entry.setStock(result.getFloat("ing.current"));
						entry.setAddedBy(result.getString("empl.name"));
						entry.setStatus(result.getString("ing.status").charAt(0));
						list.add(entry);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			list = null;
		}
		return list;
	}

	// call this method to consume an ingridient
	private static void consumeIngridientStock(int id, float amount, Statement st) throws SQLException {
		st.addBatch("update ingridients set stock=stock-" + amount + " where id=" + id);
		st.addBatch("SET @amount = " + amount + ";");
		st.addBatch("SET @debit = 0;");
		st.addBatch("update ingridients_entry set consumed=CASE WHEN @debit>0 THEN consumed+(@amount := 0)+(@debit := 0) WHEN @amount=0 THEN consumed WHEN @amount>=(count-consumed) THEN (@amount := @amount-(count-consumed))*0+count WHEN @amount<(count-consumed) THEN consumed+@amount+(@debit := 1)*0 END where serial=" + id + " and count>consumed order by expiry;");
	}

	// call this mehtod to add a relation b/w item and ingridient
	public static Status addRelation(int item, int ingridient, double units, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			try {
				con.setCatalog("stock");
				Statement st = con.createStatement();
				st.executeUpdate("insert into relation (item, ingridient, units, added_by) values(" + item + ", " + ingridient + ", " + Float.parseFloat(df.format(units)) + ", " + authToken.getId() + ");");
			} catch (SQLException e) {
				e.printStackTrace();
				return Status.EXCEPTION_OCCURED;
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}

	// call this method to get relations
	public static ArrayList<Category> getRelations(AuthToken authToken, Connection con) {
		ArrayList<Category> list = new ArrayList<>();
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from category");
					PreparedStatement ps, ps1;
					ps = con.prepareStatement("select * from item where category=?");
					ps1 = con.prepareStatement("select * from relation join ingridients on ingridient=ingridients.id where item=?");
					while (rs.next()) {
						ps.setInt(1, rs.getInt("id"));
						ResultSet result = ps.executeQuery();
						Category category = new Category(rs.getString("name"), rs.getInt("id"), rs.getString("active").charAt(0));
						while (result.next()) {
							ps1.setInt(1, result.getInt("id"));
							RelationItem rel = new RelationItem();
							rel.setId(result.getInt("id"));
							rel.setName(result.getString("name"));
							ResultSet resultSet = ps1.executeQuery();
							while (resultSet.next()) {
								RelationItem.Relation relation = rel.new Relation();
								relation.setId(resultSet.getInt("relation.id"));
								relation.setName(resultSet.getString("ingridients.name"));
								relation.setAmount(resultSet.getFloat("relation.units"));
								relation.setState(resultSet.getString("ingridients.state").charAt(0));
								rel.addRelation(relation);
							}
							category.addRelationList(rel);
						}
						list.add(category);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			list = null;
		}
		return list;
	}

	// call this method to remove a relation
	public static Status removeRelation(int id, AuthToken authToken, Connection con) {
		if (authToken.getType() == Status.MANAGER || authToken.getType() == Status.STOCK_EMPLOYEE) {
			synchronized (con) {
				try {
					con.setCatalog("stock");
					Statement st = con.createStatement();
					st.executeUpdate("delete from relation where id=" + id);
				} catch (SQLException ex) {
					ex.printStackTrace();
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else {
			return Status.INVALID_USER_TYPE;
		}
		return Status.SUCCESS;
	}
}
