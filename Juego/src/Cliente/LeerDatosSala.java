package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * La parte principar del juego, controla todas la ventanas,
 * recibe el mensaje del Servidor y realizar eventos que correponde
 */
public class LeerDatosSala extends Thread{
	BufferedReader leerDatos;
	VentanaSala sala;
	VentanaLogin login = new VentanaLogin();
	boolean juegoEmpezado = false;
	public LeerDatosSala() {
		try {
			this.leerDatos = new BufferedReader(new InputStreamReader(login.socket.getInputStream()));
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
				if (line.indexOf("-") != -1) { //Comprueba si el mensaje recibido es un evento o es simplemente un texto
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
								JOptionPane.showInternalMessageDialog(null, "Fallo al iniciar sesi�n", "ERROR", JOptionPane.ERROR_MESSAGE);
							}
							break;
						}
					} else {
						switch (evento) {
						case "preparar":
							line = line.substring(line.indexOf("-")+1);
							line = line.replace("|", "\n");
							sala.textLista.setText(line);
							break;
						case "ERROR":
							System.out.println(line);
							String error = line.substring(line.indexOf("-")+1);
							JOptionPane.showInternalMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						case "empezar":
							if (!juegoEmpezado) {
								Ventana v = new Ventana();
								v.main(null);
								juegoEmpezado = true;
							}
							break;
						}
					} 
				} 
				else {
					sala.textChat.setText(sala.textChat.getText() + line + "\n");
					sala.scrollPane.setScrollPosition(sala.scrollPane.getHAdjustable().getMinimum(),sala.scrollPane.getVAdjustable().getMaximum()+500);
				}
			} catch (IOException e) {
				JOptionPane.showInternalMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


}
