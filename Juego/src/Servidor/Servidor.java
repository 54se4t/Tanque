package Servidor;

import java.net.ServerSocket;
import java.util.Scanner;


public class Servidor {
    private final static int PORT = 5003;
   
	public static void main(String[] args) {
		try {
	    	Scanner tec = new Scanner(System.in);
	    	ServerSocket server = new ServerSocket(PORT);
	    	System.out.println("Server started");  
	    	Juego juego = new Juego();
	    	juego.start();
	    	EscucharConexion ec = new EscucharConexion(server, juego);
	    	ec.start();
	    	System.out.println("Enter to stop server");
	    	
	        tec.nextLine();//terminar servidor
	        server.close();
	        System.exit(1);
		} catch (Exception e) {
			
		}
	}
	
	
}	
