package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * Es la sala de charla y puede comprobar si estan preparado o no
 *  todos los jugadores para empezar juego
 */
public class Sala extends Thread{
    private final static int PORT = 5004;
	ServerSocket servidor;
	
	public Sala() {
		try {
			servidor = new ServerSocket(PORT); //Socket de la sala
			EscucharConexionSala ecs = new EscucharConexionSala(servidor);
			ecs.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Sala.constructor");
		}
	}
	
}
class EscucharConexionSala extends Thread {
	Socket cliente;
	ServerSocket servidor;
	EnviarDatosSala enviarDatos = new EnviarDatosSala();
	
	public EscucharConexionSala(ServerSocket servidor) {
		this.servidor = servidor;
	}
	
	
	public void run() {
		while (true) {
			try {
				cliente = servidor.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				PrintStream output = new PrintStream(cliente.getOutputStream());
				enviarDatos.addOutput(output);
				LeerDatosSala leerDatos = new LeerDatosSala(input, enviarDatos, output);
				leerDatos.start();
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("EscucharConexionSala.run");
			}
		}
	}
}
class LeerDatosSala extends Thread {
	private BufferedReader leerDatos = null;
	private ConexionDB conexion;
	EnviarDatosSala enviarDatos;
	PrintStream output;
	String usuario;
	
	public LeerDatosSala(BufferedReader leerDatos, EnviarDatosSala enviarDatos, PrintStream output) {
		this.leerDatos = leerDatos;
		this.enviarDatos = enviarDatos;
		this.output = output;
		try {
			conexion = new ConexionDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String line = null;
		bucle:
			while(true) {
			try {
				line = leerDatos.readLine();
				System.out.println(line);
				if (line.indexOf("-") != -1) { //Comprueba si el mensaje recibido es un evento o simplemente un texto
						String evento = line.substring(0, line.indexOf("-"));
					if (evento.compareTo("crear") == 0 || evento.compareTo("entrar") == 0) {
						line = line.substring(line.indexOf("-")+1);
						String usuario = line.substring(8, line.indexOf(","));
						line = line.substring(line.indexOf(",")+1);
						String contrasenya = line.substring(12);
						switch (evento) {
						case "crear": 
							output.println("crear-" + conexion.CrearUsuario(usuario, contrasenya));;
							break;
						case "entrar":
							for (int i = 0; i < enviarDatos.usuario.size(); i++) {
								if (usuario.compareTo(enviarDatos.usuario.get(i)) == 0) {
									output.println("entrar-" + false);
									break bucle;
								}
							}
							if (conexion.ckCuenta(usuario, contrasenya)) { //Añade el nuevo juegador a la lista
								this.usuario = usuario;
								enviarDatos.usuario.add(usuario);
								enviarDatos.preparado.add(false);
								enviarDatos.enviarMensajePreparado();
							} else {
								output.println("ERROR-Cuenta no valida.");
							}
							output.println("entrar-" + conexion.ckCuenta(usuario, contrasenya));
							break;
						}
						continue;
					} else if (evento.compareTo("preparar") == 0){
							enviarDatos.prepararUsuario(usuario); //Enviar lista de preparar
					} 
				} else
					enviarDatos.enviarMensaje(usuario + ":" + line);
			} catch (SocketException e) { //Excepción cuando disconecta un juegador, elimina todos los datos de este jugador
				output.println("usuario:" + usuario + " ha abandonado la partida");
				for (int i = 0; i < enviarDatos.usuario.size(); i++) {
					if (usuario.compareTo(enviarDatos.usuario.get(i)) == 0) {
						enviarDatos.usuario.remove(i);
						enviarDatos.preparado.remove(i);
						enviarDatos.outputs.remove(i);
					}
				}
				break;
			} catch (Exception e) {
				System.out.println(e);
				output.println("ERROR-" + e);
			}
		}
	}
}

class EnviarDatosSala {
	ArrayList<PrintStream> outputs = new ArrayList<PrintStream>();
	ArrayList<String> usuario = new ArrayList<String>(); //Lista de usuarios que corresponde a la lista de preparar
	ArrayList<Boolean> preparado = new ArrayList<Boolean>(); //Lista de preparar
	
	public void addOutput(PrintStream output) {
		outputs.add(output);
	}
	public void enviarMensaje(String mensaje) { //Reenviar mensaje de la charla
		for (PrintStream p : outputs)
			p.println(mensaje);
	}
	public void prepararUsuario(String usuario) {
		Boolean todosPreparado = true;
		for (int i = 0; i < this.usuario.size(); i++) {
			if (this.usuario.get(i).compareTo(usuario) == 0) {
				preparado.set(i, true);
				enviarMensajePreparado();
			}
			if (!preparado.get(i))
				todosPreparado = false;
		}
		if (this.usuario.size() >= 2 && todosPreparado) {
			System.out.println("empezar");
			for (PrintStream p : outputs)
				p.println("empezar-"); //Mensjae "empezar-" es la clave para comprobar el cliente
		}
	}
	public void enviarMensajePreparado() {
		String mensaje = "preparar-"; //Mensjae "preparar-" es la clave para comprobar el cliente
		for (int i = 0; i < usuario.size(); i++)
			mensaje += ("[" + usuario.get(i) + "]Preparado:" + preparado.get(i) + "|");
		for (PrintStream p : outputs)
			p.println(mensaje);
	}
}