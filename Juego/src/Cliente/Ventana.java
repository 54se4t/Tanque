package Cliente;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;

public class Ventana extends Canvas implements Runnable, KeyListener {

	private final static int PORT = 5003;

	private final static String SERVER = "127.0.0.1";

	public static int width = 960;
	public static int height = width / 16 * 9;
	public static int scale = 1;

	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private static Socket socket;
	private static PrintStream output;
	private String mensaje;
	private static int id;
	private static LeerDatos leerdatos;
	
	public Ventana() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
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
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				updates++;
				delta--;
				render();
				id = leerdatos.id;
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

	public void send() {
		output.println(id + "," + mensaje);
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
		g.setColor(Color.black);
		for (int i = 0; i < leerdatos.getJuego().getTanques().size(); i++) {
//			System.out.println(leerdatos.getJuego().getTanques().get(i).getY());
//			System.out.println(leerdatos.getJuego().getTanques().get(i).getX());
			Servidor.Tanque t = leerdatos.getJuego().getTanques().get(i);
			g.fillRect(t.getX(), t.getY(), t.getAncho(), t.getAlto());
			//System.out.println("pintar tanque");
		}

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		try {
			System.out.println("Client -> Start");
			socket = new Socket(SERVER, PORT);// open socket
			leerdatos = new LeerDatos(new ObjectInputStream(socket.getInputStream()));
			leerdatos.start();
			System.out.println("leerdatos start");
			output = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ventana ventana = new Ventana();
		ventana.addKeyListener(ventana);
		ventana.frame.setResizable(false);
		ventana.frame.setTitle("Tank");
		ventana.frame.add(ventana);
		ventana.frame.pack(); // Cambiar tamaño automatico por los objetos dentro de la ventana
		ventana.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.frame.setLocationRelativeTo(null); // Estar en centro por defecto
		ventana.frame.setVisible(true);

		ventana.start();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			mensaje = "moverDerecha";
			send();
			break;
		case KeyEvent.VK_RIGHT:
			mensaje = "moverIzquierda";
			send();
			break;
		case KeyEvent.VK_UP:
			mensaje = "subirCanyon";
			send();
			break;
		case KeyEvent.VK_DOWN:
			mensaje = "bajarCanyon";
			send();
			break;
		case KeyEvent.VK_SPACE:
			mensaje = "disparar";
			send();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
