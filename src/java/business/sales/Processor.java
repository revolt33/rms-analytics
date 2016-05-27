package business.sales;

import business.employee.AuthToken;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {
	public static boolean isUniqueDiscountCode(String code, AuthToken authToken, Connection con) throws SQLException {
		if ( authToken.getType() == business.stock.Processor.MANAGER ) {
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
	public static char addDiscount(Discount discount,AuthToken authToken, Connection con) {
		if ( authToken.getType() == business.stock.Processor.MANAGER ) {
			synchronized ( con ) {
				try {
					con.setCatalog("sales");
					Statement st = con.createStatement();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else
			return business.stock.Processor.INVALID_USER_TYPE;
		return business.stock.Processor.SUCCESS;
	}
}
