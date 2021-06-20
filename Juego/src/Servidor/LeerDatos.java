package Servidor;

import java.io.BufferedReader;
import java.io.IOException;

public class LeerDatos extends Thread{
	private BufferedReader leerDatos = null;
	EnviarDatos enviarDatos = null;
	Juego juego = null;
	int id;
	public LeerDatos(BufferedReader leerDatos, EnviarDatos enviarDatos, Juego juego) {
		this.leerDatos = leerDatos;
		this.enviarDatos = enviarDatos;
		this.juego = juego;
		enviarDatos.enviarMensaje(juego);
		id = juego.tanques.get(juego.tanques.size()-1).getId();
	}
	public void run() {
		String line = null;
		boolean repetir = true;
		while (repetir) {
			try { //Llamar el objeto Tanque que correpondiente para realizar la acción
				line = leerDatos.readLine();
				String mensaje = line.substring(line.indexOf(",")+1,line.length());
				int idTanque = Integer.parseInt(line.substring(0,line.indexOf(",")));
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
				//enviarDatos.enviarMensaje(juego); //No es necesario porque ya envia datos 60 veces cada segundo
			} catch (IOException e) {
				//Eliminar juegador desconectado
				for (int i = 0; i < juego.tanques.size(); i++) 
					if (id == juego.tanques.get(i).getId()) {
						juego.tanques.get(i).setVida(-100);
						System.out.println("Jugador " + i + " ha abandonado la partida");
					}
				break;
			}
			
		}
	}
}
	