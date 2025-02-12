package uCanAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ariketaAccess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
		ResultSet rs = null;
		try {
			c = DriverManager.getConnection("jdbc:ucanaccess://access-sample.mdb");
			Statement s = c.createStatement();
			
			String sql_sententzia = "SELECT category_id, name FROM category ORDER BY name DESC";
			
			rs = s.executeQuery(sql_sententzia);
			
			while(rs.next()) {
				System.out.println("Category_id: " + rs.getInt(1) + " | Category name: " + rs.getString(2));
			}
			
		} catch (Exception e) {
			System.err.print(e);
		} finally {
			if(c != null)
				try {
					if(c != null)
						c.close();
					if(rs != null)
						rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
	}

}
