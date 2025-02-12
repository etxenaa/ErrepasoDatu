package AccessetikMySqlraPasatu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class ariketa {

	public static void main(String[] args) {
		
		String dbIzena = "farmacia";
		String host = "localhost";
		String port = "3306";
		String parAdic = "serverTimezone=UTC";
		String erabiltzaileaMySql = "root";
		String pasahitzaMySql = "";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + dbIzena + "?" + parAdic;
		String urlConnectionAccess = "jdbc:ucanaccess://C:\\Users\\AIMAR - MARKEL\\Downloads\\farmacia.mdb";
		
		
		try (Connection c = DriverManager.getConnection(urlConnection, erabiltzaileaMySql, pasahitzaMySql);
				Connection c2 = DriverManager.getConnection(urlConnectionAccess)){
			
			String createTableSQL = "CREATE TABLE IF NOT EXISTS medicamentos (\r\n"
					+ "    codigo INT AUTO_INCREMENT PRIMARY KEY,\r\n"
					+ "    nombre VARCHAR(255),\r\n"
					+ "    cantidad INT,\r\n"
					+ "    precio DECIMAL(10, 2),\r\n"
					+ "    pvp DECIMAL(10, 2)\r\n"
					+ ");" 
	                ;
			
			try(Statement stCrear = c.createStatement()){
				stCrear.execute(createTableSQL);
			}catch (Exception e) {
				System.err.println("Taula sortzean errorea: " + e.getMessage());
			}
			
			String sqlAccess = "SELECT * FROM medicamentos";
			String insertTableSQL = "INSERT INTO medicamentos (\r\n"
					+ "    codigo,\r\n"
					+ "    nombre,\r\n"
					+ "    cantidad,\r\n"
					+ "    precio,\r\n"
					+ "    pvp\r\n"
					+ ") VALUES (?, ?, ?, ?, ?);"; 
	                
			try(Statement st = c2.createStatement(); ResultSet rs = st.executeQuery(sqlAccess);
					PreparedStatement ps = c.prepareStatement(insertTableSQL)){
				while(rs.next()) {
					ps.setInt(1, rs.getInt(1));
					ps.setString(2, rs.getString(2));
					ps.setInt(3, rs.getInt(3));
					ps.setDouble(4, rs.getDouble(4));
					ps.setDouble(5, rs.getDouble(5));
					ps.executeUpdate();
				}
			}catch (Exception e) {
				System.err.println("Accesseko datuak hartzean errorea: " + e.getMessage());
			}
			
		} catch (Exception e) {
			System.err.println("MySql datu baserako konexioak errorea: " + e.getMessage());
		} finally {
			
		}

	}

}
