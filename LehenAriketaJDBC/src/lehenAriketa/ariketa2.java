package lehenAriketa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ariketa2 {

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
			System.out.println("Sartu kantitate bat: ");
			int kantitatea = sc.nextInt();
			String sql_sententzia = "INSERT INTO erabiltzaile_mugimenduak (izena, kantitatea) VALUES (?, ?)";
			PreparedStatement ps = c.prepareStatement(sql_sententzia);
			
			ps.setString(1, izena);
			ps.setInt(2, kantitatea);
			
			ps.executeUpdate();
			
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
		if(sc != null)
			sc.close();
	}

	
}
