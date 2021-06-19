package Cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class VentanaSala extends JFrame {

	private JPanel contentPane;
	JTextField textMensaje;
	JTextArea textLista = new JTextArea();
	JTextArea textChat = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSala frame = new VentanaSala(null,null);
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
	public VentanaSala(Socket socket, PrintStream output) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		textChat.setLineWrap(true);
		panel.add(textChat, BorderLayout.CENTER);
		
		JScrollBar scrollBar = new JScrollBar();
		panel.add(scrollBar, BorderLayout.EAST);
		
		textLista.setLineWrap(true);
		GridBagConstraints gbc_textLista = new GridBagConstraints();
		gbc_textLista.gridheight = 3;
		gbc_textLista.fill = GridBagConstraints.BOTH;
		gbc_textLista.gridx = 2;
		gbc_textLista.gridy = 0;
		contentPane.add(textLista, gbc_textLista);
		
		textMensaje = new JTextField();
		GridBagConstraints gbc_textMensaje = new GridBagConstraints();
		gbc_textMensaje.gridwidth = 2;
		gbc_textMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_textMensaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_textMensaje.gridx = 0;
		gbc_textMensaje.gridy = 1;
		contentPane.add(textMensaje, gbc_textMensaje);
		textMensaje.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviar.gridx = 0;
		gbc_btnEnviar.gridy = 2;
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.println(textMensaje.getText());
				textMensaje.setText("");
			}
		});
		contentPane.add(btnEnviar, gbc_btnEnviar);
		
		JButton btnPreparar = new JButton("Preparar");
		GridBagConstraints gbc_btnPreparar = new GridBagConstraints();
		gbc_btnPreparar.insets = new Insets(0, 0, 0, 5);
		gbc_btnPreparar.gridx = 1;
		gbc_btnPreparar.gridy = 2;
		btnPreparar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.println("preparar-");
				btnPreparar.setEnabled(false);
			}
		});
		contentPane.add(btnPreparar, gbc_btnPreparar);
	}

}
