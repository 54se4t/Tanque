package Servidor;

import java.io.Serializable;
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
	
	public boolean isVolando() {
		return volando;
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

public class Tanque extends ObjetoJuego implements Serializable{
	private final int VELOCIDAD_MOVER = 5;
	private static int contador = 0; 
	private int id;
	private double anguloCanyon = 3;
	private Bomba bomba = new Bomba();
	
	public Tanque() {
		id = contador;
		contador++;
		ancho = 50;
		alto = 20;
	}
	
	public int getId() {
		return id;
	}

	public Bomba getBomba() {
		return bomba;
	}

	public void moverDerecha() {
		x -= VELOCIDAD_MOVER;
		if (!bomba.isVolando()) 
			setXYbomba();
	}
	public void moverIzquierda() {
		x += VELOCIDAD_MOVER;
		if (!bomba.isVolando()) 
			setXYbomba();
	}
	
	public void subirCanyon() {
		if (anguloCanyon < 15)
			anguloCanyon += 1;
		if (!bomba.isVolando()) 
			bomba.setAnguloVolar(anguloCanyon);
	}
	public void bajarCanyon() {
		if (anguloCanyon > 3)
			anguloCanyon -= 1;
		if (!bomba.isVolando()) 
			bomba.setAnguloVolar(anguloCanyon);
	}
	
	public void disparar() {
		if (!bomba.isVolando()) {
			bomba.setAnguloVolar(anguloCanyon);
			bomba.volar();
		}
	}
	public void recargar() {
		bomba = new Bomba();
	} 
	
	public void setXYbomba() {
		bomba.setX(x + 10);
		bomba.setY(y);
		System.out.println(y);
	}
	
	@Override
	public String toString() {
		return "id:" + id + ",ancho:" + ancho + ",alto:" + alto + ",x:" + x + ",y:" + y + ",anguloCanyon:" + anguloCanyon + ",bombaAlto:" + bomba.alto + ",bombaAncho:" + bomba.ancho + ",bombaX:" + Math.round(bomba.x) + ",bombaY:" + Math.round(bomba.y) + "|";
	}
}