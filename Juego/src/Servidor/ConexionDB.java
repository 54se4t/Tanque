package Servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
	private String url = "jdbc:mysql://localhost:3306/Tanque";
	private String user = "root";
	private String password = "administrador";
	private Connection connect;
	private static Statement statement;
	public ConexionDB() throws ClassNotFoundException, SQLException{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);
			statement = connect.createStatement();
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ConexionDB c = new ConexionDB();
		c.CrearUsuario("usuario2", "contra");
	}
	public void CrearUsuario(String usuario, String constrasenya) throws SQLException {
		statement.execute("insert into Cuenta values (\"" + usuario + "\",\"" + constrasenya + "\");");
	}
	public boolean CkCuenta(String usuario, String constrasenya) {
		return true;
	}
	public void addPartida(String usuario, boolean partida) {
		
	}
}
