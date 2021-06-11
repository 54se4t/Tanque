package Servidor;

import java.io.Serializable;

public class Tanque extends ObjetoJuego implements Serializable{
	private final int VELOCIDAD_MOVER = 5;
	private static int contador = 0;
	private int id;
	private double anguloCanyon;
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

	public void moverDerecha() {
		x -= VELOCIDAD_MOVER;
	}
	public void moverIzquierda() {
		x += VELOCIDAD_MOVER;
	}
	
	public void subirCanyon() {
		anguloCanyon += 1;
	}
	public void bajarCanyon() {
		anguloCanyon -= 1;
	}
	
	public void disparar() {
		bomba.volar();
	}
	public void recargar() {
		bomba = new Bomba();
	} 
}