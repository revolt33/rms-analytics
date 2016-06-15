package business.sales;

import business.Status;
import business.employee.AuthToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {
	public static boolean isUniqueDiscountCode(String code, AuthToken authToken, Connection con) throws SQLException {
		if ( authToken.getType() == Status.MANAGER ) {
			synchronized (con) {
				con.setCatalog("sales");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select count(*) from discount where code='"+code+"'");
				rs.next();
				if ( rs.getInt(1) > 0 )
					return false;
			}
		} else {
			return false;
		}
		return true;
	}
	public static Status addDiscount(Discount discount, AuthToken authToken, Connection con) throws SQLException {
		if ( authToken.getType() == Status.MANAGER ) {
			Statement st = null;
			synchronized ( con ) {
				try {
					con.setCatalog("sales");
					st = con.createStatement();
					st.executeQuery("start transaction;");
					st.executeUpdate("insert into discount (code, begin, end, percentage, max, type, applicable_to, added_by) values('"+discount.getCode()+"', "+discount.getFrom()+", "+discount.getTo()+", "+discount.getPercentage()+", "+discount.getMax()+", '"+discount.getType()+"', '"+discount.getApplicable()+"', "+discount.getEmpId()+" )");
					if ( discount.getApplicable() == 'i' ) {
						st.execute("SET @id = LAST_INSERT_ID()");
						for ( Discount.Item item: discount.getItemList() ) {
							st.executeUpdate("insert into discount_list (discount, id) values(@id, "+item.getItem()+");");
						}
					}
					st.execute("commit;");
				} catch (SQLException ex) {
					ex.printStackTrace();
					if ( st != null )
						st.execute("rollback;");
					return Status.EXCEPTION_OCCURED;
				}
			}
		} else
			return Status.NOT_MANAGER;
		return Status.SUCCESS;
	}
	
	public static ArrayList<Discount> getDiscountList(AuthToken authToken, Connection con) {
		ArrayList<Discount> list = new ArrayList<>();
		if ( authToken.getType() == Status.MANAGER ) {
			synchronized ( con ) {
				try {
					con.setCatalog("sales");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from discount join employee.emp on added_by=emp.id");
					PreparedStatement ps = con.prepareStatement("select discount_list.id, discount, item.name from discount_list join stock.item on discount_list.id=stock.item.id where discount=?");
					while ( rs.next() ) {
						Discount discount = new Discount();
						discount.setId(rs.getInt("discount.id"));
						discount.setCode(rs.getString("code"));
						discount.setFrom(rs.getLong("begin"));
						discount.setTo(rs.getLong("end"));
						discount.setPercentage(rs.getFloat("percentage"));
						discount.setMax(rs.getFloat("max"));
						discount.setType(rs.getString("discount.type").charAt(0));
						discount.setStatus(rs.getString("status").charAt(0));
						discount.setApplicable(rs.getString("applicable_to").charAt(0));
						discount.setAddedBy(rs.getString("name"));
						if ( discount.getApplicable() == 'i' ) {
							ps.setInt(1, discount.getId());
							ResultSet resultSet = ps.executeQuery();
							while ( resultSet.next() ) {								
								Discount.Item item = discount.new Item();
								item.setId(resultSet.getInt("discount"));
								item.setItem(resultSet.getInt("id"));
								item.setName(resultSet.getString("name"));
								discount.addItem(item);
							}
						}
						list.add(discount);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return list;
	}
}
