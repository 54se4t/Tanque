package prueba1;

public class Map {
	private int width;
	private int height;
	private int air = 400;
	private int terrain;

	
	
	
	public int getAir() {
		return air;
	}
	public void setAir(int air) {
		this.air = air;
	}
	public int getTerrain() {
		return terrain;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		terrain = height - air;
	}	
}
