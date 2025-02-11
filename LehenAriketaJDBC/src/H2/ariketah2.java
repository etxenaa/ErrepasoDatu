package H2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.h2.tools.Server;

public class ariketah2 {

	public static void main(String[] args) {
		String jdbcURL = "jdbc:h2:~/DAazterketa4";

		String user = "sa";

		String password = "";

		Server webServer = null;

		ResultSet rs = null;

		ResultSet rs2 = null;

		try {

			webServer = Server.createWebServer("-webPort", "8085", "-tcpAllowOthers").start();

			System.out.println("Consola web de H2 iniciada en http://localhost:8085");

			try (Connection conn = DriverManager.getConnection(jdbcURL, user, password)) {

				if (conn != null) {
					Scanner sc = new Scanner(System.in);
					System.out.println("H2ra konekzioa ezarrita.");
					// taulakSortu(conn);
					// datuakSartu(conn);
					System.out.println("Liburuaren izenburua: ");
					String izenburua = sc.nextLine();
					System.out.println("Liburuaren egilea: ");
					String egilea = sc.nextLine();
					System.out.println("Irakurlearen izena: ");
					String irakurlearenIzena = sc.nextLine();
					System.out.println("Irakurlearen abizena: ");
					String irakurlearenAbizena = sc.nextLine();

					String sql_sententziaLiburuak = "INSERT INTO LIBURUAK (IZENBURUA, EGILEA) VALUES (?, ?)";
					String sql_sententziaIrakurleak = "INSERT INTO IRAKURLEAK (IZENA, ABIZENA) VALUES (?, ?)";

					PreparedStatement ps = conn.prepareStatement(sql_sententziaLiburuak,
							PreparedStatement.RETURN_GENERATED_KEYS);
					PreparedStatement ps2 = conn.prepareStatement(sql_sententziaIrakurleak,
							PreparedStatement.RETURN_GENERATED_KEYS);

					ps.setString(1, izenburua);
					ps.setString(2, egilea);

					ps2.setString(1, irakurlearenIzena);
					ps2.setString(2, irakurlearenAbizena);

					ps.executeUpdate();
					ps2.executeUpdate();

					rs = ps.getGeneratedKeys();
					rs2 = ps2.getGeneratedKeys();

					rs.next();
					rs2.next();

					int idLiburua = rs.getInt(1);
					int idIrakurlea = rs2.getInt(1);
					String sql_sententziaMaileguak = "INSERT INTO MAILEGUAK (IRAKURLE_ID, LIBURU_ID) VALUES (?, ?)";

					PreparedStatement ps3 = conn.prepareStatement(sql_sententziaMaileguak);

					ps3.setInt(1, idIrakurlea);
					ps3.setInt(2, idLiburua);

					ps3.executeUpdate();

					System.out.println("Datuak gehitu dira. ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (webServer != null) {
					// webServer.stop();
					// System.out.println("Consola web de H2 cerrada.");
				}

				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public static void taulakSortu(Connection conn) throws SQLException {
		String createLiburuak = "CREATE TABLE Liburuak (" + "liburu_id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "izenburua VARCHAR(200) NOT NULL, " + "egilea VARCHAR(100) NOT NULL" + ");";

		String createIrakurleak = "CREATE TABLE Irakurleak (" + "irakurle_id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "izena VARCHAR(100) NOT NULL, " + "abizena VARCHAR(100) NOT NULL" + ");";

		String createMaileguak = "CREATE TABLE Maileguak (" + "irakurle_id INT, " + "liburu_id INT, "
				+ "PRIMARY KEY (irakurle_id, liburu_id), "
				+ "FOREIGN KEY (irakurle_id) REFERENCES Irakurleak(irakurle_id) ON DELETE CASCADE, "
				+ "FOREIGN KEY (liburu_id) REFERENCES Liburuak(liburu_id) ON DELETE CASCADE" + ");";

		try (Statement stmt = conn.createStatement()) {

			stmt.execute(createLiburuak);

			stmt.execute(createIrakurleak);

			stmt.execute(createMaileguak);

			System.out.println("Tablas creadas con éxito.");

		}
	}

	public static void datuakSartu(Connection conn) throws SQLException {
		String insertLiburuak = "INSERT INTO Liburuak (izenburua, egilea) VALUES "
				+ "('Euskal Mitologia', 'Joseba Sarrionandia'), " + "('Zazpi Lore', 'Iñaki Irazu');";

		String insertIrakurleak = "INSERT INTO Irakurleak (izena, abizena) VALUES " + "('Miren', 'Etxeberria'), "
				+ "('Jon', 'Mendizabal');";

		String insertMaileguak = "INSERT INTO Maileguak (irakurle_id, liburu_id) VALUES " + "(1, 1), " + "(1, 2), "
				+ "(2, 1);";

		try (Statement stmt = conn.createStatement()) {

			stmt.execute(insertLiburuak);

			stmt.execute(insertIrakurleak);

			stmt.execute(insertMaileguak);

			System.out.println("Datos insertados con éxito.");

		}
	}
}
