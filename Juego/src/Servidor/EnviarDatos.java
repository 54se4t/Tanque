package Servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class EnviarDatos {
	ArrayList<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	
	public void anyadirOutput(Socket socket) {
		try {
			OutputStream output = socket.getOutputStream();
			outputs.add(new ObjectOutputStream(output));
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("EnviarDatos");
		}
	}
	public void enviarDatos(Juego juego) {
		for (ObjectOutputStream ou : outputs) {
			try {
				ou.writeObject(juego.mapa);
				ou.writeObject(juego.tanques);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				System.out.println("EnviarDatos");
			}
		}
	}
}
