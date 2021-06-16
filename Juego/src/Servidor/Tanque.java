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
		
		//bomba.y = 304;
	}
	
	public int getId() {
		return id;
	}

	public Bomba getBomba() {
		return bomba;
	}

	public void moverDerecha() {
		x -= VELOCIDAD_MOVER;
		setXYbomba();
	}
	public void moverIzquierda() {
		x += VELOCIDAD_MOVER;
		setXYbomba();
	}
	
	public void subirCanyon() {
		if (anguloCanyon < 15)
			anguloCanyon += 1;
		bomba.setAnguloVolar(anguloCanyon);
	}
	public void bajarCanyon() {
		if (anguloCanyon > 3)
			anguloCanyon -= 1;
		bomba.setAnguloVolar(anguloCanyon);
	}
	
	public void disparar() {
		bomba.setAnguloVolar(anguloCanyon);
		bomba.volar();
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