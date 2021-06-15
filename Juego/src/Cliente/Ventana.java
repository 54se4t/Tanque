package Cliente;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixeles = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
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
				//id = leerdatos.id;
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
		
		for (int i = 0; i < pixeles.length; i++) {
			pixeles[i] = leerdatos.mapaPixeles[i/100];
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);

		
		for (int i = 0; i < leerdatos.tanqueId.size(); i++) {
			g.fillRect((int)(leerdatos.tanqueX.get(i)+0), (int)(leerdatos.tanqueY.get(i)+0), leerdatos.tanqueAncho.get(i), leerdatos.tanqueAlto.get(i));
		}
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		try {
			System.out.println("Client -> Start");
			socket = new Socket(SERVER, PORT);// open socket
			System.out.println("Client -> conectar");
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			leerdatos = new LeerDatos(input);
			leerdatos.start();
			System.out.println("Client -> leerdatos");
			output = new PrintStream(socket.getOutputStream());
			id = leerdatos.id;
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
		System.out.println("Client -> empezarVentana");
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
