package Servidor;

import java.io.Serializable;
import java.util.ArrayList;

public class Juego implements Serializable{
	private static boolean corriendo = false;
	ArrayList<Tanque> tanques = new ArrayList<Tanque>();
	Mapa mapa = new Mapa(960, 540);
	
	private static void actualizar() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
		while (corriendo) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				//***** codigos que hay que actualizar *****
			}
			
		}
	}
	
	public void anyadirTanque() {
		Tanque tq = new Tanque();
		tq.setY(100);
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

class Mapa implements Serializable{
	private int ancho;
	private int alto;
	private int[] pixeles;
	
	public Mapa(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
		pixeles = new int[ancho*alto];
	}
	
	public void pintar() {
		int cielo = (int) (pixeles.length*0.7);
		for (int i = 0; i < pixeles.length; i++ ) {
			if (i < cielo) 
				pixeles[i] = 0x000000; 
			else 
				pixeles[i] = 0xFFFFFF;
		}
	}
	
	public int[] getPiexeles() {
		return pixeles;
	}
}


