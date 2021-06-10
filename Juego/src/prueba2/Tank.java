package prueba2;

import prueba1.Map;

public class Tank {
	int height = 20;
	int width = 50;
	int x = 50;
	int y;
	int angle = 4;
	Bomba bomba = new Bomba();

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setY(Map map) {
		this.y = map.getAir() - 20;
		if (bomba.volando == false) 
			bomba.y = y - 20;
	}
	public void moveLeft() {
		x -= 5;
		if (bomba.volando == false) 
			bomba.x = x + 5;
	}
	public void moveRight() {
		x += 5;
		if (bomba.volando == false) 
			bomba.x = x;
	}
	public void upCanno() {
		if (angle < 15)
		angle += 1;
		if (bomba.volando == false) 
		bomba.angulo = angle;
	}
	public void downCanno() {
		if (angle > 3)
		angle -= 1;
		if (bomba.volando == false) 
		bomba.angulo = angle;
	}
	public void disparar() {
		bomba.angulo = this.angle;
		bomba.volando = true;
	}
	public void recargar() {
		bomba.volando = false;
		bomba.y = y - 20;
		bomba.x = x + 5;
	}
	public void autoRecargar(int height) {
		if (bomba.y > height)
			recargar();
	}
}
