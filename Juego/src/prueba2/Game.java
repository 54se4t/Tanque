package prueba2;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import prueba1.Map;
import prueba1.Tank;


public class Game extends Canvas implements Runnable, KeyListener {
	
	public static int width = 960;
	public static int height = width / 16 * 9;
	public static int scale = 1;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private Map map = new Map();
	private prueba2.Tank tank = new prueba2.Tank();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		map.setHeight(height);
		map.setWidth(width);
		tank.setY(map);
	}	
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 30.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		int count = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();	
				updates++;
				delta--;
				render();//pintar infinito/s
				System.out.println(count++);
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update() {
		tank.bomba.volar();
		tank.autoRecargar(map.getAir()-20);
	}
	
	public void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GRAY);
		g.fillRect(0, map.getAir(), getWidth(), map.getTerrain());
		
		g.setColor(Color.BLACK);
		g.fillRect(tank.getX(), tank.getY(), tank.width, tank.height);
		
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		g2.setColor(Color.BLACK);
		g2.rotate(-Math.PI/18*tank.angle,tank.getX() + 24, tank.getY() - 4);
		g2.drawRect( tank.getX() + 22, tank.getY() - 7, 30, 10);
		g.fillRoundRect(tank.getX() + 10, tank.getY() - 15,30,30,50,50);

		if (tank.bomba.volando == true) {
			g.setColor(Color.RED);
			g.drawRoundRect((int)tank.bomba.x, (int)tank.bomba.y, 30, 30, 50, 50);
		}

		g2.dispose();
		g.dispose();
		bs.show();
	}
	

	
	public static void main(String[] args) {
		Game game = new Game();
		game.addKeyListener(game);
		game.frame.setResizable(false);
		game.frame.setTitle("Tank");
		game.frame.add(game);
		game.frame.pack(); //Cambiar tamaño automatico por los objetos dentro de la ventana
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); //Estar en centro por defecto
		game.frame.setVisible(true);
		
		game.start();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			tank.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			tank.moveRight();
			break;
		case KeyEvent.VK_UP:
			tank.upCanno();
			break;
		case KeyEvent.VK_DOWN:
			tank.downCanno();
			break;
		case KeyEvent.VK_SPACE:
			tank.disparar();
			break;
		case KeyEvent.VK_R:
			tank.recargar();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
