package Servidor;

import java.io.PrintStream;
import java.util.ArrayList;

public class EnviarDatos {
	ArrayList<PrintStream> outputs = new ArrayList<PrintStream>();
	
	public void addOutput(PrintStream output) {
		outputs.add(output);
	}
	public void enviarMensaje(Juego juego) {
		String mensaje;
		mensaje = juego.mapa.toString();
		for (int i = 0; i < juego.getTanques().size(); i++) {
			mensaje += juego.getTanques().get(i).toString();
		}
		System.out.println(mensaje);
		for (PrintStream p : outputs)
			p.println(mensaje);
	}
}
