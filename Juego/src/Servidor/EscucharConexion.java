package Servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class EscucharConexion extends Thread {
	Socket cliente;
	ServerSocket servidor;
	EnviarDatos enviarDatos;
	Juego juego;
	
	public EscucharConexion(ServerSocket servidor, Juego juego, EnviarDatos enviarDatos) {
		this.servidor = servidor;
		this.juego = juego;
		this.enviarDatos = enviarDatos;
	}
	
	
	// Aceptar el cliente y crea un nuevo tanque para el cliente y envia su ID
	public void run() {
		while (true) {
			try {
				cliente = servidor.accept();
				juego.anyadirTanque();
				BufferedReader input = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				enviarDatos.addOutput(new PrintStream(cliente.getOutputStream()));
				LeerDatos leerDatos = new LeerDatos(input, enviarDatos, juego);
				leerDatos.start();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
