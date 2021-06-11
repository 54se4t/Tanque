package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class pruevaCliente {
	public static void main(String[] args) {
		try {
			System.out.println("Client -> Start");
			Socket socket = new Socket("127.0.0.1", 5003);
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
			LeerDatos leerDatos = new LeerDatos(objIn);
			leerDatos.start();
		
//			System.out.println("recibido 1");
//			System.out.println(leerDatos.getJuego().getTanques().get(0).getId());
//			
//			PrintStream out = new PrintStream(socket.getOutputStream());
//			out.println("0,moverDerecha");
//			
//			System.out.println(leerDatos.getJuego().getTanques().get(0).getX());
//			System.out.println(leerDatos.getJuego().getTanques().get(0).getY());
//			
//			out.println("0,moverDerecha");
//			out.println("0,moverDerecha");
//			out.println("0,moverDerecha");
//			
//			System.out.println(leerDatos.getJuego().getTanques().get(0).getX());
//			System.out.println(leerDatos.getJuego().getTanques().get(0).getY());
			Scanner tec = new Scanner(System.in);
			tec.nextLine();
			PrintStream out = new PrintStream(socket.getOutputStream());
			out.println("0,moverDerecha");
			tec.nextLine();
			System.out.println(leerDatos.getJuego().getTanques().get(0).getX());
			System.out.println(leerDatos.getJuego().getTanques().get(0).getY());
			tec.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
