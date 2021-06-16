package Cliente;

import java.awt.Color;

public class Fondo {
	public final double TIERRA = 0.6;
	
	private int width, height;
	public int[] pixeles;
	public int[] lluvias;
	
	int time = 0;
	int counter = 0;
	int color = 0x000000;
	public Fondo(int width, int height) {
		this.width = width;
		this.height = height;
		pixeles = new int[width * height];
		lluvias = new int[width * height];
	}	
	
	public void borrar() {
		double r = 255;
		double g = 255;
		double b = 255;
		counter++;
		if (counter % 50 == 0) {
			time++;
		}
		if ((time + time * width) >= width*height) time = 0;
		for (int y = 0; y < pixeles.length-1; y++) {
			//pixeles[y] = Integer.parseInt(String.format("0x%02x%02x%02x", r, g, b));
			pixeles[y] = (((int)r&0x0ff)<<16)|(((int)g&0x0ff)<<8)|((int)b&0x0ff);
			if (y < pixeles.length*TIERRA) {
				r-= 0.0004;
				g-= 0.0002;
				if (lluvias[y] == 0xFFFFFF)
					pixeles[y] = lluvias[y];
			}
			else if (y == pixeles.length*TIERRA) {
				r = 255;
				g = 255;
				b = 255;
			}
			else {
				r-= 0.0004*1.5;
				g-= 0.0002*1.5;
			}
		}
	}
	
	public void pintar() {
		for (int i = 0; i < lluvias.length; i++) {
			if (i < width && Math.random() > 0.1) {
				lluvias[i] = 0xFFFFFF;
			}
		}
		for (int i = 0; i < lluvias.length; i+=Math.random()*5) {
			if (lluvias[i] == 0xFFFFFF && (i + width) < lluvias.length) {
				lluvias[i] = 0;
				lluvias[i + width] = 0xFFFFFF;
				if (i < 0) i = 0;
				//if (i > lluvias.length) break;
			}
		}
	}
	
}
