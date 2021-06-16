package Cliente;

public class Fondo {
	public final double TIERRA = 0.6;
	
	private int width, height;
	public int[] pixeles;
	
	int time = 0;
	int counter = 0;
	int color = 0x000000;
	public Fondo(int width, int height) {
		this.width = width;
		this.height = height;
		pixeles = new int[width * height];
	}	
	
	public void borrar() {
		for (int i = 0; i < pixeles.length-1; i++) {
			if (i < pixeles.length*TIERRA)
				pixeles[i] = 0xC9DEFF;
			else
				pixeles[i] = 0xFFC9C9;
		}
	}
	
	public void pintar() {
		counter++;
		if (counter % 50 == 0) {
			time++;
		}
		if ((time + time * width) >= width*height) time = 0;
		for (int y = 0; y < height-1; y++) {
			for (int x = 0; x < width-1; x++) {
				pixeles[x + time * width] = color;
			}
		}
	}
	
}
