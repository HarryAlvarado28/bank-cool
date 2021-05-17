
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Login_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// MarcoBaseBankATM bankATM = new MarcoBaseBankATM();
		// bankATM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		MarcoBaseBankATM();
		// MarcoAcceso loginMarco = new MarcoAcceso("DMG Bank");
		// loginMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					MarcoAcceso frame = new MarcoAcceso("DMG Bank");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// generando un metodo estatico para crear el objeto de la clase MarcoAcceso
//	private static void MarcoBaseBankATM(){
//		MarcoAcceso loginMarco = new MarcoAcceso("Bank $.$");
//		loginMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

}

@SuppressWarnings("serial")
class MarcoAcceso extends JFrame implements KeyListener, ActionListener {
	private JButton jbEnter;
	private JTextField jtfUser;
	private JPasswordField jpfPass;
	private JLabel jlUser, jlPass;
	private JTextPane jtpMessage;
	private String alet = "Credenciales no correctas. Reint�ntalo.";
	private boolean acceso;

	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");

	// ---------------------------------------------------------------------------------
//	private URL ruta_externa = MarcoAcceso.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	// ---------------------------------------------------------------------------------

	private Dimension tamanoPantalla = mipantalla.getScreenSize();
	private int alturaPantalla = tamanoPantalla.height;
	private int anchoPantalla = tamanoPantalla.width;
	private int admin = 0;

	private InterfazAdministrador GUI_Administrador;
	private InterfazDeUsuario<?> GUI_Cliente;

	private String nombre;
	private String apellido;
	private String usuario;
	private String numCuenta;
	private double saldoInicial;
	private String contrasena;
	private String tipoCuenta;
	private Connection con;
	private PreparedStatement p;
	private ResultSet rs;
	Panel pan;

	public MarcoAcceso(String title) {
		con = null;
		p = null;
		rs = null;

		setTitle(title);

		// setBounds(300,150,260,300);
		setBounds(alturaPantalla / 2, anchoPantalla / 4, 245, 295);
//		setIconImage(miIcono);		

		setResizable(false);
		setLayout(null);

		jlUser = new JLabel("Usuario");
		jtfUser = new JTextField(10);
		jtfUser.addKeyListener(this);

		jlPass = new JLabel("Contrasena");
		jpfPass = new JPasswordField(10);
		jpfPass.addKeyListener(this);
		jtpMessage = new JTextPane();

		jtpMessage.setEditable(false);
		jtpMessage.setBackground(getBackground());

		jbEnter = new JButton("Acceder");
		jbEnter.addActionListener(this);

		add(jlUser).setBounds(100, 25, 100, 20);
		add(jtfUser).setBounds(60, 50, 130, 25);
		add(jlPass).setBounds(90, 85, 100, 20);
		add(jpfPass).setBounds(60, 110, 130, 25);
		add(jbEnter).setBounds(75, 160, 100, 40);

//		add(new LaminaConImagen()).setBounds(0, 0, 245, 295);
//		this.setBackground(new Color(245,136,8));
		setVisible(true);

		Container contetpane = getContentPane();
		pan = new Panel();
		contetpane.add(pan);
	}

	/**
	 * @return si existe acceso de usuario o no existe
	 */
	public boolean getAcceso() {
		return acceso;
	}

	@SuppressWarnings("unchecked")
	private boolean checkUser() {
		boolean checkU = false;
		String user = jtfUser.getText();
		char[] pass = jpfPass.getPassword();
		String passConv = new String(pass);
		ClientesDB[] listaNueva = null;

		try {
			String sql = "Select * from USUARIOS where user_id = ? and password= ?";
			con = DBConfig.connectDB();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.toLowerCase());
			stmt.setString(2, passConv);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) // userid found
			{
				checkU = true;
				String rol = rs.getString(4);
				if (rol.equals("admin")) {
					admin = 1;
				}
			} else // user not found
			{
				System.out.println("Invalid UserId");
			}
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
		}

		if (checkU) {
			add(jtpMessage).setBounds(60, 140, 130, 10);
			jtpMessage.setText(null);
			jtpMessage.setBackground(getBackground());
			add(jbEnter).setBounds(75, 160, 100, 40);

		} else {
			add(jtpMessage).setBounds(60, 140, 130, 40);
			jtpMessage.setText(alet);
			jtpMessage.setBackground(getBackground());
			jtpMessage.setForeground(Color.RED.brighter());
			add(jbEnter).setBounds(75, 200, 100, 40);
		}
		return checkU;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (jbEnter == e.getSource()) {
			acceso = checkUser();
			if (acceso & admin == 1) {
				this.dispose();
				GUI_Administrador = new InterfazAdministrador();
				GUI_Administrador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				GUI_Administrador.setVisible(true);
			} else if (acceso) {
				this.dispose();
				GUI_Cliente = new InterfazDeUsuario<Object>(nombre, apellido, usuario, numCuenta, saldoInicial,
						contrasena, tipoCuenta);
				GUI_Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				GUI_Cliente.setVisible(true);
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) {
			acceso = checkUser();
			if (acceso & admin == 1) {
				this.dispose();
				GUI_Administrador = new InterfazAdministrador();
				GUI_Administrador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				GUI_Administrador.setVisible(true);
			} else if (acceso) {
				this.dispose();
				GUI_Cliente = new InterfazDeUsuario<Object>(nombre, apellido, usuario, numCuenta, saldoInicial,
						contrasena, tipoCuenta);
				GUI_Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				GUI_Cliente.setVisible(true);
			}
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

	public class Panel extends JPanel {

		public Panel() {
			this.setSize(400, 280);
		}

		public void paint(Graphics g) {
			Dimension tamanio = getSize();
			ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/img/images.jpg"));
			g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
			super.paintComponent(g);

		}

	}

	/*
	 * class LaminaConImagen extends JPanel { private Image imagen; URL ruta_externa
	 * = MarcoAcceso.LaminaConImagen.class.getResource("dise�oLogin5.png");
	 * 
	 * public LaminaConImagen() { // URL ru =
	 * MarcoAcceso.class.getResource("dise�oLogin5.png"); // File mimagen = new
	 * File("src/img/dise�oLogin5.png");
	 * 
	 * try { imagen = ImageIO.read(ruta_externa);
	 * 
	 * // imagen = ImageIO.read(ru); // imagen = ImageIO.read(mimagen); } catch
	 * (IOException e) { // System.out.println("La imagen no se en cuentra"); } }
	 * 
	 * public void paintComponent(Graphics g) { super.paintComponent(g);
	 * 
	 * imagen.getWidth(this); imagen.getHeight(this); g.drawImage(imagen, 0, 0,
	 * null);
	 * 
	 * }
	 * 
	 * }
	 * 
	 */

}
