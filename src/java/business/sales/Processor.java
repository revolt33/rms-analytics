package business.sales;

import business.employee.AuthToken;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		}
		return true;
	}
}
