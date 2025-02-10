package ScrollableResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ariketa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String datuBaseIzena = "kutxabank";
		String host = "localhost";
		String port = "3306";
		String parAdic = "serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + datuBaseIzena + "?" + parAdic;
		String db_erabiltzailea = "root";
		String db_pasahitza = "";

		Statement stmt = null;

		ResultSet rs = null;
		Connection c = null;
		try {
			c = DriverManager.getConnection(urlConnection, db_erabiltzailea, db_pasahitza);

			stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String query = "SELECT izena, kantitatea, timestamp FROM erabiltzaile_mugimenduak";
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				System.out.println("Ez dago daturik");
				return;
			}

			while (true) {
				System.out.println("Timestamp: " + rs.getTimestamp(3) + " | Izena: " + rs.getString(1)
						+ " | Kantitatea: " + rs.getInt(2));
				System.out.println(
						"Aukeratu aukera bat: [a] Aurrekoa, [d] Hurrengoa, [1] Lehenengoa, [5] Bostgarrena, [9] Azkena, [+] Berria gehitu, [q] Irten: ");
				String aukera = sc.next();
				switch (aukera) {
				case "a":
					if (!rs.previous()) {
						System.out.println("Lehenengo erregistroan zaude.");
						rs.next();
					}
					break;
				case "d":
					if (!rs.next()) {
						System.out.println("Azkenengo erregistroan zaude.");
						rs.previous();
					}
					break;
				case "1":
					rs.first();
					break;
				case "5":
					int posicion = rs.getRow();
					if (!rs.absolute(5)) {
						System.out.println("Ez dago bostgarrena");
						rs.absolute(posicion);
					}
					break;
				case "9":
					rs.last();
					break;
				case "+":
					int posicion2 = rs.getRow();
					System.out.println("Sartu izen bat: ");
					String izena = sc.next();
					System.out.println("Sartu kantitate bat: ");
					int kantitatea = sc.nextInt();

					String sql_sententzia = "INSERT INTO erabiltzaile_mugimenduak (izena, kantitatea) VALUES (?, ?)";
					PreparedStatement ps = c.prepareStatement(sql_sententzia);

					ps.setString(1, izena);
					ps.setInt(2, kantitatea);

					ps.executeUpdate();
					ps.close();

					rs = stmt.executeQuery(query);

					rs.absolute(posicion2);
					break;
				case "q":
					System.out.println("Irten duzu.");
					System.exit(0);
					break;
				default:
					System.out.println("Aukera ez da baliozkoa.");
					break;
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (c != null)
					c.close();
				if (sc != null)
					sc.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

}