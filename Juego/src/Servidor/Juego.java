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
					if (t.getBomba().y > 304) {
						for (Tanque t2 : tanques) {
							t2.recibirDanyo(t.getBomba().getX());
						}
						ckPartida();
						t.recargar();
					}
				}
				enviarDatos.enviarMensaje(this);
				delta--;
			}
		}
	}
	
	public void anyadirTanque() {
		Tanque tq = new Tanque();
		tq.setY(304);
		tq.setXAleatoria();
		tq.setXYbomba();
		tanques.add(tq);
	}

	public ArrayList<Tanque> getTanques() {
		return tanques;
	}
	
	public void ckPartida() {
		int total = tanques.size();
		int muertos = 0;
		for (Tanque t : tanques)
			if (t.getVida() <= 0) muertos++;
		if ((total-muertos) == 1)
			reempezarPartida();
	}
	public void reempezarPartida() {
		System.out.println("reempezarPartida");
		for (Tanque t : tanques) {
			t.setXAleatoria();
			t.setXYbomba();
			t.setVida(50);
		}
	}
}

