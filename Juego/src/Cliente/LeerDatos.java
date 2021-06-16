package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LeerDatos extends Thread{
	BufferedReader leerDatos;
	int id;
	String line;
	int mapaAncho;
	int mapaAlto;
	ArrayList<Integer> tanqueId;
	ArrayList<Integer> tanqueAncho;
	ArrayList<Integer> tanqueAlto;
	ArrayList<Double> tanqueX;
	ArrayList<Double> tanqueY;
	ArrayList<Double> anguloCanyon;
	ArrayList<Integer> bombaAlto;
	ArrayList<Integer> bombaAncho;
	ArrayList<Integer> bombaX;
	ArrayList<Integer> bombaY;
	
	
	
	public LeerDatos(BufferedReader leerDatos) {
		this.leerDatos = leerDatos;
		try {
			line = leerDatos.readLine();
			asignarDatos(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				line = leerDatos.readLine();
				asignarDatos(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void asignarDatos(boolean nuevoId) {
		reestablecerDatos();
		leerTanques(line);
		if (nuevoId) {
			id = tanqueId.get(tanqueId.size()-1);
			for (int i : tanqueId)
				System.out.println(i);
		}

	}
	
	private void leerTanques(String tanques) {
		String tanque;
		do {
			tanque= tanques.substring(0, tanques.indexOf("|"));
			tanques = tanques.substring(tanques.indexOf("|")+1);
			
			tanqueId.add(Integer.parseInt(tanque.substring(3, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			tanqueAncho.add(Integer.parseInt(tanque.substring(6, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			tanqueAlto.add(Integer.parseInt(tanque.substring(5, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			tanqueX.add(Double.parseDouble(tanque.substring(2, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			tanqueY.add(Double.parseDouble(tanque.substring(2, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			anguloCanyon.add(Double.parseDouble(tanque.substring(13, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			bombaAlto.add(Integer.parseInt(tanque.substring(10, tanque.indexOf(","))));
			
			tanque = tanque.substring(tanque.indexOf(",")+1);
			bombaAncho.add(Integer.parseInt(tanque.substring(11, tanque.indexOf(","))));

			tanque = tanque.substring(tanque.indexOf(",")+1);
			bombaX.add((int)Double.parseDouble(tanque.substring(7, tanque.indexOf(","))));

			tanque = tanque.substring(tanque.indexOf(",")+1);
			bombaY.add((int)Double.parseDouble(tanque.substring(7)));
			
		} while (tanques.indexOf(",") != -1);
	}
	
	private void reestablecerDatos() {
		tanqueId = new ArrayList<Integer>();
		tanqueAncho = new ArrayList<Integer>();
		tanqueAlto = new ArrayList<Integer>();
		tanqueX = new ArrayList<Double>();
		tanqueY = new ArrayList<Double>();
		anguloCanyon = new ArrayList<Double>();
		bombaAlto = new ArrayList<Integer>();
		bombaAncho = new ArrayList<Integer>();
		bombaX = new ArrayList<Integer>();
		bombaY = new ArrayList<Integer>();
	}
}
