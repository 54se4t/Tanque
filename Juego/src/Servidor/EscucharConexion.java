package Servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class EscucharConexion extends Thread {
	Socket cliente;
	ServerSocket servidor;
	EnviarDatos enviarDatos = new EnviarDatos();
	Juego juego;
	
	public EscucharConexion(ServerSocket servidor, Juego juego) {
		this.servidor = servidor;
		this.juego = juego;
	}
	
	
	// Aceptar el cliente y crea un nuevo tanque para el cliente y envia su ID
	public void run() {
		while (true) {
			try {
				cliente = servidor.accept();
				System.out.println("accept");
				juego.anyadirTanque();
				BufferedReader input = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				enviarDatos.addOutput(new PrintStream(cliente.getOutputStream()));
				System.out.println("enviarDatos addOutput");
				LeerDatos leerDatos = new LeerDatos(input, enviarDatos, juego);
				System.out.println("new LeerDatos");
				leerDatos.start();
				System.out.println(juego.tanques.get(juego.tanques.size()-1).getId());
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("EscucharConexion");
			}
		}
	}

}
