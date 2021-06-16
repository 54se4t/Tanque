package Cliente;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	
	private Fondo fondo;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixeles = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private Image arbol, arbolEspejo, regalo, regaloEspejo;
	
	public Ventana() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		
		fondo = new Fondo(width, height);
		 
		try {
			arbol = ImageIO.read(new File("img/arbol.png"));
			arbolEspejo = ImageIO.read(new File("img/arbol-espejo.png"));
			regalo = ImageIO.read(new File("img/regalos.png"));
			regaloEspejo = ImageIO.read(new File("img/regalos-espejo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		final double ns2 = 1000000000.0 / 60.0;
		double delta = 0, delta2 = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			delta2 += (now - lastTime) / ns2;
			lastTime = now;
			while (delta >= 1) {
				updates++;
				delta--;
				render();
				if (delta2 > 1) {
					fondo.borrar();
					fondo.pintar();
					delta2--;
				}
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
		
		//fondo.borrar();
		//fondo.pintar();
		for (int i = 0; i < pixeles.length; i++) {
			pixeles[i] = fondo.pixeles[i];
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(arbol,900,(int)(getHeight()*fondo.TIERRA-104), 60, 104 ,null);
		g.drawImage(arbol,820,(int)(getHeight()*fondo.TIERRA-70), 40, 70 ,null);
		g.drawImage(arbol,850,(int)(getHeight()*fondo.TIERRA-157), 90, 157 ,null);
		g.drawImage(arbolEspejo,902,(int)(getHeight()*fondo.TIERRA), 60, 104 ,null);
		g.drawImage(arbolEspejo,822,(int)(getHeight()*fondo.TIERRA), 40, 70 ,null);
		g.drawImage(arbolEspejo,852,(int)(getHeight()*fondo.TIERRA), 90, 157 ,null);
		
		g.drawImage(regalo,-200,(int)(getHeight()*fondo.TIERRA-163), 400, 163 ,null);
		g.drawImage(regaloEspejo,-190,(int)(getHeight()*fondo.TIERRA), 400, 163 ,null);
		
		
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < leerdatos.tanqueX.size(); i++) {
			try {
			int tanqueX = (int)(leerdatos.tanqueX.get(i)+0);
			int tanqueY = (int) (getHeight()*fondo.TIERRA) - leerdatos.tanqueAlto.get(i);
			
			g.setColor(Color.black);//cañon de tanque
			Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
			g2.setColor(Color.BLACK);
			g2.rotate(-Math.PI/18*leerdatos.anguloCanyon.get(i), tanqueX + leerdatos.tanqueAncho.get(i)/2, tanqueY);
			g2.fillRect( tanqueX + leerdatos.tanqueAncho.get(i)/2 - 5 , tanqueY - 5, 30, 10);
			g2.dispose();	
			
			Graphics2D g3 = (Graphics2D) bs.getDrawGraphics();
			g3.setColor(new Color(185,203,214));
			g3.rotate(+Math.PI/18*leerdatos.anguloCanyon.get(i), tanqueX + leerdatos.tanqueAncho.get(i)/2, tanqueY + leerdatos.tanqueAlto.get(i)*2);
			g3.fillRect( tanqueX + leerdatos.tanqueAncho.get(i)/2 - 5 , tanqueY + leerdatos.tanqueAlto.get(i)*2 - 5, 30, 10);
			g3.dispose();
			
			g.setColor(new Color(255,0,0));//Bomba
			g.fillRoundRect(leerdatos.bombaX.get(i), leerdatos.bombaY.get(i), leerdatos.bombaAlto.get(i), leerdatos.bombaAncho.get(i), 50, 50);
			g.setColor(new Color(255,130,130));
			g.fillRoundRect(leerdatos.bombaX.get(i), -leerdatos.bombaY.get(i) + 620, leerdatos.bombaAlto.get(i), leerdatos.bombaAncho.get(i), 50, 50);
			
			g.setColor(new Color(34,90,120));//capa de tanque
			g.fillRoundRect(tanqueX+5, tanqueY-10, 40, 50, 40, 40);
			//Espejo de tanque
			g.setColor(new Color(204,222,233));
			g.fillRoundRect(tanqueX+5, tanqueY, 40, 50, 40, 40);
			g.setColor(new Color(185,203,214));
			g.fillRect((int)(leerdatos.tanqueX.get(i)+0), (int) (getHeight()*fondo.TIERRA), leerdatos.tanqueAncho.get(i), leerdatos.tanqueAlto.get(i));
						
			g.setColor(Color.black);
			//cuerpo de tanque
			g.fillRect(tanqueX, tanqueY, leerdatos.tanqueAncho.get(i), leerdatos.tanqueAlto.get(i));
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			
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
		ventana.frame.pack(); // Cambiar tamaÃ±o automatico por los objetos dentro de la ventana
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
