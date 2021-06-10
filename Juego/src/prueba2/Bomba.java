package prueba2;

public class Bomba {
	boolean volando = false;
	double cae = 0.1;
	double velocidad = 10;
	double x;
	double y;
	int angulo;
	
	public void setXY (int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void volar() {
		if (volando == true) {
			if (angulo <= 9) {
				x = x + 2*velocidad - velocidad * angulo / 4.5;
				y = y - velocidad * angulo / 4.5 + cae;
			} else if (angulo <= 18) {
				x = x - 2*velocidad + velocidad * (18-angulo) / 4.5;
				y = y - velocidad * (18-angulo) / 4.5 + cae;
			}
			cae += cae/10;
			if (velocidad >= 0)
				velocidad -= velocidad/100;
		}
		else {
			cae = 0.1;
			velocidad = 10;
		}
	}
}
