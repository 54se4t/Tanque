package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sala extends Thread{
    private final static int PORT = 5004;
	ServerSocket servidor;
	
	public Sala() {
		try {
			servidor = new ServerSocket(PORT);
			EscucharConexionSala ecs = new EscucharConexionSala(servidor);
			ecs.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Sala.constructor");
		}
	}
	
}
class EscucharConexionSala extends Thread {
	Socket cliente;
	ServerSocket servidor;
	
	public EscucharConexionSala(ServerSocket servidor) {
		this.servidor = servidor;
	}
	
	
	public void run() {
		while (true) {
			try {
				cliente = servidor.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				LeerDatosSala leerDatos = new LeerDatosSala(input);
				leerDatos.start();
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("EscucharConexionSala.run");
			}
		}
	}
}

class LeerDatosSala extends Thread {
	private BufferedReader leerDatos = null;
	private ConexionDB conexion;
	
	public LeerDatosSala(BufferedReader leerDatos) {
		this.leerDatos = leerDatos;
		try {
			conexion = new ConexionDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String line = null;
		while(true) {
			try {
				line = leerDatos.readLine();
				System.out.println(line);
				String evento = line.substring(0, line.indexOf("-"));
				line = line.substring(line.indexOf("-")+1);
				switch (evento) {
				case "crear": 
					String usuario = line.substring(8, line.indexOf(","));
					line = line.substring(line.indexOf(",")+1);
					String contrasenya = line.substring(11);
					conexion.CrearUsuario(usuario, contrasenya);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + evento);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("leerDatosSala.run");
			}
		}
	}
}
