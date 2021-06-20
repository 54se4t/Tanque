package Servidor;

import java.net.ServerSocket;
import java.util.Scanner;


public class EmpezarServidor {
    private final static int PORT = 5003;
   
	public static void main(String[] args) {
		try {
	    	Scanner tec = new Scanner(System.in);
	    	ServerSocket server = new ServerSocket(PORT); //Socket para la parte juego
	    	System.out.println("Server started");  
	    	Sala s = new Sala();
	    	EnviarDatos enviarDatos = new EnviarDatos(); //Para enviar datos del juego
	    	Juego juego = new Juego(enviarDatos);
	    	EscucharConexion ec = new EscucharConexion(server, juego, enviarDatos); //Para crear las conecciones de la ventana juego de los jugadores
	    	ec.start();
	    	juego.start();
	    	System.out.println("Enter to stop server");
	    	
	        tec.nextLine();//terminar servidor
	        server.close();
	        System.exit(1);
		} catch (Exception e) {
			
		}
	}
	
	
}	
