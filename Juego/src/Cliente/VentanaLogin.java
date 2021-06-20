package Cliente;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;


import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Toolkit;

public class VentanaLogin extends JFrame {
	
	private final static int PORT = 5004;
	private final static String SERVER = "127.0.0.1";
	public static PrintStream output;
	public static Socket socket;
	
	private JPanel contentPane;
	private JTextField textUsuario;
	private JTextField textContrasenya;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/arbol.png"));
		setTitle("Tanque-Iniciar sesi\u00F3n");
		try {
			socket = new Socket(SERVER, PORT);
			output = new PrintStream(socket.getOutputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 199);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		textUsuario = new JTextField();
		textUsuario.setToolTipText("Introduce tu usuario");
		GridBagConstraints gbc_textUsuario = new GridBagConstraints();
		gbc_textUsuario.gridwidth = 2;
		gbc_textUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsuario.gridx = 1;
		gbc_textUsuario.gridy = 1;
		contentPane.add(textUsuario, gbc_textUsuario);
		textUsuario.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textContrasenya = new JPasswordField();
		textContrasenya.setToolTipText("Introduce tu contrase\u00F1a");
		GridBagConstraints gbc_textContrasenya = new GridBagConstraints();
		gbc_textContrasenya.gridwidth = 2;
		gbc_textContrasenya.insets = new Insets(0, 0, 5, 5);
		gbc_textContrasenya.fill = GridBagConstraints.HORIZONTAL;
		gbc_textContrasenya.gridx = 1;
		gbc_textContrasenya.gridy = 2;
		contentPane.add(textContrasenya, gbc_textContrasenya);
		textContrasenya.setColumns(10);
		
		JButton btnCrearUsuario = new JButton("Crear cuenta");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.println("crear-usuario:" + textUsuario.getText() + ",contrasenya:" + textContrasenya.getText());
			}
		});
		GridBagConstraints gbc_btnCrearUsuario = new GridBagConstraints();
		gbc_btnCrearUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearUsuario.gridx = 1;
		gbc_btnCrearUsuario.gridy = 3;
		contentPane.add(btnCrearUsuario, gbc_btnCrearUsuario);
		
		JButton btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.println("entrar-usuario:" + textUsuario.getText() + ",contrasenya:" + textContrasenya.getText());
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 3;
		contentPane.add(btnLogin, gbc_btnLogin);
	}

}
