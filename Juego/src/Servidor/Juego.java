package Servidor;

import java.io.Serializable;
import java.util.ArrayList;

public class Juego extends Thread implements Serializable{
	private static boolean corriendo = true;
	ArrayList<Tanque> tanques = new ArrayList<Tanque>();
	Mapa mapa = new Mapa(960, 540);
	
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 30.0;
		double delta = 0;
		
		while (corriendo) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				//System.out.print("");
				for (Tanque t : tanques) 
						t.caer(mapa);
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
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public Mapa(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
		setPixeles((new int[ancho*alto/100]));
		pintar();
	}
	
	public void pintar() {
		int cielo = (int) (getPixeles().length*0.5);
		for (int i = 0; i < getPixeles().length; i++ ) {
			if (i < cielo) 
				getPixeles()[i] = 0xFFFFFF; 
			else 
				getPixeles()[i] = 0x000000;
		}
	}
	
	public void actualizar() {
		for (int i = 0; i < getPixeles().length; i++ ) {
			if (getPixeles()[i] == 0x000000) {
				int distIzq = i%(ancho/100);
				if ((i + ancho/100) < getPixeles().length && getPixeles()[i + ancho/100] == 0xFFFFFF){
					getPixeles()[i] = 0xFFFFFF;
					getPixeles()[i + ancho/100] = 0x000000;
				} 
				else if ((i + ancho/100 - 1) < getPixeles().length - 1 && getPixeles()[i + ancho/100 - 1] == 0xFFFFFF) {
					getPixeles()[i] = 0xFFFFFF;
					getPixeles()[i + ancho/100 - 1] = 0x000000;
				} 
				else if ((i + ancho/100 + 1) < getPixeles().length && getPixeles()[i + ancho/100 + 1] == 0xFFFFFF) {
					getPixeles()[i] = 0xFFFFFF;
					getPixeles()[i + ancho/100 + 1] = 0x000000;
				}
			}
		}
	}
	
	public int[] getPiexeles() {
		return getPixeles();
	}
	
	@Override
	public String toString() {
		String pixel = "";
		for (int i:pixeles) {
			pixel += i + "|";
		}
		return "ancho:" + ancho + ",alto:" + alto + ",pixeles:" + pixel + "-";
	}

	public int[] getPixeles() {
		return pixeles;
	}

	public void setPixeles(int[] pixeles) {
		this.pixeles = pixeles;
	}
}


