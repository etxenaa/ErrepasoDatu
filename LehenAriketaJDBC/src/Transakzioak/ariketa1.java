package Transakzioak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			c.setAutoCommit(false);
			
			System.out.print("Nork emango du dirua ");
			String emanIzena = sc.next();
			System.out.print("Nori? ");
			String jasoIzena = sc.next();
			System.out.print("Zenbat diru? ");
			int kantitatea = sc.nextInt();
			
			String sql_sententzia = "UPDATE erabiltzaile_mugimenduak SET kantitatea = kantitatea - ? WHERE izena = ?";
			String sql_sententzia2 = "UPDATE erabiltzaile_mugimenduak SET kantitatea = kantitatea + ? WHERE izena = ?";
			
			try(PreparedStatement ps = c.prepareStatement(sql_sententzia); PreparedStatement ps2 = c.prepareStatement(sql_sententzia2)){
				
				ps.setInt(1, kantitatea);
				ps.setString(2, emanIzena);
				int filasAfectadas = ps.executeUpdate();
				
				ps2.setInt(1, kantitatea);
				ps2.setString(2, jasoIzena);
				int filasAfectadas2 = ps2.executeUpdate();
				
				if (filasAfectadas == 0 || filasAfectadas2 == 0) {
                    System.out.println("Error: Ez dago nahikoa datu edo errore bat egon da.");
                    c.rollback();
                    return;
                }
			}
			
			
			
			c.commit();
		} catch (Exception e) {
			try {
				c.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null) c.close();
                if(sc!=null) sc.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
	}

}
