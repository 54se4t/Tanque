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
		enviarDatos.enviarMensaje(juego);
	}
	public void run() {
		String line = null;
		boolean repetir = true;
		while (repetir) {
			try {
				line = leerDatos.readLine();
				String mensaje = line.substring(line.indexOf(",")+1,line.length());
				int idTanque = Integer.parseInt(line.substring(0,line.indexOf(",")));
				//System.out.println(idTanque);
				//juego.tanques.get(idTanque).moverDerecha();
//				juego.tanques.get(idTanque).y += 100;
				switch (mensaje) {
					case "moverDerecha" :
						juego.tanques.get(idTanque).moverDerecha();
						break;
					case "moverIzquierda" :
						juego.tanques.get(idTanque).moverIzquierda();
						break;
					case "subirCanyon" :
						juego.tanques.get(idTanque).subirCanyon();
						break;
					case "bajarCanyon" :
						juego.tanques.get(idTanque).bajarCanyon();
						break;
					case "disparar" :
						juego.tanques.get(idTanque).disparar();
						break;
				}

				System.out.println(juego.tanques.get(idTanque).x);
				enviarDatos.enviarMensaje(juego);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				System.out.println("LeerDatos");
				break;
			}
			
		}
	}
}
	