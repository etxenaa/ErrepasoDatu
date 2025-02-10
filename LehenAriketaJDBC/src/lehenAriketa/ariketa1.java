package lehenAriketa;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class ariketa1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        String datuBaseIzena = "kutxabank";
        String host = "localhost";
        String port = "3306";
        String parAdic = "serverTimezone=UTC";
        String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + datuBaseIzena + "?" + parAdic;

		String db_erabiltzailea = "root";
		String db_pasahitza = "";

		Connection c = null;

		try {
			c = DriverManager.getConnection(urlConnection, db_erabiltzailea, db_pasahitza);
			System.out.println("Sartu izen bat: ");
			String izena = sc.next();
			
			String sql_sententzia = "SELECT kantitatea FROM erabiltzaile_mugimenduak WHERE izena = '"+izena+"'";
            PreparedStatement ps_select = c.prepareStatement(sql_sententzia);
            ResultSet rs = ps_select.executeQuery();
            
            boolean aurkituta = false;
            
            while(rs.next()) {
            	int kantitatea = rs.getInt("kantitatea");
            	System.out.println(izena + "-ren diru kantitatea: " + kantitatea);
            	aurkituta = true;
            }
            
            if(!aurkituta) {
            	System.out.println("Ez da existitzen izen hori duen erabiltzailerik");
            }
            
			System.out.println("Programa bukaera");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

		sc.close();
	}

	
}
