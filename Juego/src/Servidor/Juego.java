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
		tanques.add(tq);
	}

	public ArrayList<Tanque> getTanques() {
		return tanques;
	}
}

class ObjetoJuego implements Serializable{
	protected double x;
	protected double y;
	protected int ancho;
	protected int alto;
	
	public int getX() {
		return (int)x;
	}
	public void setX(double x) { 
		this.x = x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return (int)y;
	}
	public void setY(double y) { 
		this.y = y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getAncho() {
		return ancho;
	}
	public int getAlto() {
		return alto;
	}
		
}



class Bomba extends ObjetoJuego implements Serializable{
	private boolean volando = false;
	private double velocidadVolar;
	private double velocidadCaer;
	private double anguloVolar;
	
	public Bomba() {
		ancho = 30;
		alto = 30;
	}
	
	public double getAnguloVolar() {
		return anguloVolar;
	}

	public void setAnguloVolar(double anguloVolar) {
		this.anguloVolar = anguloVolar;
	}

	public void volar() {
		if (volando)
			volando = false;
		else
			volando = true;
	}
	
	public void mover() {
		if (volando == true) {
			if (anguloVolar <= 9) {
				x = x + 2*velocidadVolar - velocidadVolar * anguloVolar / 4.5;
				y = y - velocidadVolar * anguloVolar / 4.5 + velocidadCaer;
			} else if (anguloVolar <= 18) {
				x = x - 2*velocidadVolar + velocidadVolar * (18-anguloVolar) / 4.5;
				y = y - velocidadVolar * (18-anguloVolar) / 4.5 + velocidadCaer;
			}
			velocidadCaer += velocidadCaer/10;
			if (velocidadVolar >= 0)
				velocidadVolar -= velocidadVolar/100;
		}
		else {
			velocidadCaer = 0.1;
			velocidadVolar = 10;
		}
	}
}
