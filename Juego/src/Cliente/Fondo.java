package Cliente;

import java.awt.Color;
/*
 * La clase para pintar el fondo del juego
 */
public class Fondo {
	public final double TIERRA = 0.6; //Porcentaje de la altura de tierra
	private int width, height;
	public int[] pixeles;
	public int[] nieve;
	
	int color = 0x000000; //Color guarda como hexadecimal
	public Fondo(int width, int height) {
		this.width = width;
		this.height = height;
		pixeles = new int[width * height];
		nieve = new int[width * height];
	}	
	
	public void pintarFondo() { //Pinta el color del fondo y junta con nieve
		double r = 255;
		double g = 255;
		double b = 255;
		for (int y = 0; y < pixeles.length-1; y++) {
			pixeles[y] = (((int)r&0x0ff)<<16)|(((int)g&0x0ff)<<8)|((int)b&0x0ff); //Para convertid RGB a hexadecimal
			if (y < pixeles.length*TIERRA) {
				r-= 0.0008;
				g-= 0.0004;
				if (nieve[y] == 0xFFFFFF)
					pixeles[y] = nieve[y];
			}
			else if (y == pixeles.length*TIERRA) {
				r = 255;
				g = 255;
				b = 255;
			}
			else {
				r-= 0.0008*1.5;
				g-= 0.0004*1.5;
			}
		}
	}
	
	public void pintarNieve() { //Pinta nieve
		for (int i = 0; i < nieve.length; i++) {
			if (i < width && Math.random() > 0.1) {
				nieve[i] = 0xFFFFFF;
			}
		}
		for (int i = 0; i < nieve.length; i+=Math.random()*5) {
			if (nieve[i] == 0xFFFFFF && (i + width) < nieve.length) {
				nieve[i] = 0;
				nieve[i + width] = 0xFFFFFF;
				if (i < 0) i = 0;
			}
		}
	}
	
}
