package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;

import Servidor.Juego;
import Servidor.Tanque;

public class LeerDatos extends Thread{
	Servidor.Juego juego;
	ObjectInputStream obj;
	int id = 876565685;
	
	public LeerDatos(ObjectInputStream obj) {
		this.obj = obj;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("**********");
				System.out.println("Esperando objeto");
				juego = (Servidor.Juego) obj.readObject();
				System.out.println("Recibido objeto");
				if (id == 876565685) {
					id = juego.getTanques().get(juego.getTanques().size()-1).getId();
					System.out.println(id);
				}
				for (Tanque t : juego.getTanques()) {
					System.out.println("id:" + t.getId() + ",xy" + t.getX() + "|" +t.getY());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Servidor.Juego getJuego() {
		return juego;
	}

	
}
