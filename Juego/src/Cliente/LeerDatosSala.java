package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LeerDatosSala extends Thread{
	private final static int PORT = 5003;
	private final static String SERVER = "127.0.0.1";
	BufferedReader leerDatos;
	VentanaSala sala;
	VentanaLogin login = new VentanaLogin();
	boolean juegoEmpezado = false;
	public LeerDatosSala() {
		try {
			leerDatos = new BufferedReader(new InputStreamReader(login.socket.getInputStream()));
			this.leerDatos = leerDatos;
			login.setVisible(true);
			sala = new VentanaSala(login.socket, login.output);
			sala.setLocationRelativeTo(null);
			login.setLocationRelativeTo(null);
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		String line;
		bucle: while(true) {
			try {
				line = leerDatos.readLine();
				System.out.println(line);
				if (line.indexOf("-") != -1) {
					String evento = line.substring(0, line.indexOf("-"));
					if (evento.compareTo("crear") == 0 || evento.compareTo("entrar") == 0) {
						Boolean b = Boolean.parseBoolean(line.substring(line.indexOf("-")+1));
						switch (evento) {
						case "crear": 
							if (b)
								JOptionPane.showInternalMessageDialog(null, "Has creado la cuenta", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
							else 
								JOptionPane.showInternalMessageDialog(null, "Fallo al crear la cuenta", "ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						case "entrar":
							if (b) {
								sala.setVisible(true);
								login.setVisible(false);
							} else {
								JOptionPane.showInternalMessageDialog(null, "Fallo al iniciar sesión", "ERROR", JOptionPane.ERROR_MESSAGE);
							}
							break;
						}
						continue;
					} else {
						switch (evento) {
						case "preparar":
							line = line.substring(line.indexOf("-")+1);
							line = line.replace("|", "\n");
							sala.textLista.setText(line);
							continue bucle;
						case "ERROR":
							System.out.println(line);
							String error = line.substring(line.indexOf("-")+1);
							JOptionPane.showInternalMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
							continue bucle;
						case "empezar":
							if (!juegoEmpezado) {
								Ventana v = new Ventana();
								v.main(null);
								juegoEmpezado = true;
							}
							continue bucle;
						}
					} 
				}
					sala.textChat.setText(sala.textChat.getText() + line + "\n");
			} catch (IOException e) {
				JOptionPane.showInternalMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


}
