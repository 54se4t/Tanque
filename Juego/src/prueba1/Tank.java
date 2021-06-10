package prueba1;

import java.util.ArrayList;

public class Tank {
	int height = 20;
	int width = 50;
	int x = 50;
	int y;
	int angle = 3;
	ArrayList<Point> cannon = new ArrayList<Point>(2);
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setY(Map map) {
		this.y = map.getAir() - 20;
	}
	
	public void moveLeft() {
		x -= 5;
	}
	public void moveRight() {
		x += 5;
	}
	public void upCanno() {
		if (angle < 9)
		angle += 1;
	}
	public void downCanno() {
		if (angle > 0)
		angle -= 1;
	}
}
class Point {
	int x;
	int y;
}
