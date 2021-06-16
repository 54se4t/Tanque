package Servidor;

import java.io.Serializable;
import java.util.ArrayList;

public class Juego extends Thread implements Serializable{
	private static boolean corriendo = true;
	private EnviarDatos enviarDatos;
	ArrayList<Tanque> tanques = new ArrayList<Tanque>();
	public Juego(EnviarDatos enviarDatos) {
		this.enviarDatos = enviarDatos;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		System.out.println("Emp");
		int count = 0;
		while (corriendo) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				for (Tanque t : tanques) {
					t.getBomba().mover();
					if (t.getBomba().x < 0 || t.getBomba().x > 960 || t.getBomba().y > 350) {
						t.setXYbomba();
						t.getBomba().volar();
					}
				}
				enviarDatos.enviarMensaje(this);
				delta--;
			}
		}
		System.out.println("terminado");
	}
	
	public void anyadirTanque() {
		Tanque tq = new Tanque();
		tq.setY(304);
		tq.setX(100);
		tq.setXYbomba();
		tanques.add(tq);
	}

	public ArrayList<Tanque> getTanques() {
		return tanques;
	}
}

