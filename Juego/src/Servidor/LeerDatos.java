package Servidor;

import java.io.BufferedReader;
import java.io.IOException;

public class LeerDatos extends Thread{
	private BufferedReader leerDatos = null;
	EnviarDatos enviarDatos = null;
	Juego juego = null;
	public LeerDatos(BufferedReader leerDatos, EnviarDatos enviarDatos, Juego juego) {
		this.leerDatos = leerDatos;
		this.enviarDatos = enviarDatos;
		this.juego = juego;
	}
	public void run() {
		String line = null;
		boolean repetir = true;
		while (repetir) {
			try {
				line = leerDatos.readLine();
				String mensaje = line.substring(line.indexOf(",")+1,line.length()-1);
				int idTanque = Integer.parseInt(line.substring(0,line.indexOf(",")));
				System.out.println(line);
				switch (mensaje) {
					case "moverDerecha" :
						juego.tanques.get(idTanque).moverDerecha();
						break;
					case "moverIzquierda" :
						juego.tanques.get(idTanque).moverIzquierda();
						break;
					case "disparar" :
						juego.tanques.get(idTanque).disparar();
						break;
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				System.out.println("LeerDatos");
			}
			
		}
	}
}
	