
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditarCliente extends JFrame implements ActionListener {

	private static final long serialVersionUID = 28;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AgregarCliente h = new AgregarCliente();
//		h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");
	// ---------------------------------------------------------------------------------
//	private URL ruta_externa = EditarCliente.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	// ---------------------------------------------------------------------------------

	private Dimension tamanoPantalla = mipantalla.getScreenSize();
	private int alturaPantalla = tamanoPantalla.height, anchoPantalla = tamanoPantalla.width;

	private JLabel jlNombre, jlApellido, jlUsuario, jlNumCuenta, jlSaldoInicial, jlContrasena, jlTipoCuenta, jlMensaje;
	private JTextField jtfNombre, jtfApellido, jtfUsuario, jtfNumCuenta, jtfSaldoInicial, jtfContrasena;
	private JButton jbAdd, jbCancelar, jbCerrar;
	private JPanel jpInformacion, jpDatos, jpBotones, jpBtMsj;
	private String nombre, apellido, usuario, contrasena, tipoCuenta;
	private JComboBox<String> jcbTipoCuenta;
	private double saldoInicial;
	private String numCuenta;
	private ArrayList<ClientesDB> lista = new ArrayList<ClientesDB>();
	private Connection con = null;
	private PreparedStatement p = null;

	private boolean encontrado = false;
	private int index;
	private String userID;

	public EditarCliente(String usuarioModificar) {
		// TODO Auto-generated constructor stub
		setBounds(alturaPantalla / 2, anchoPantalla / 4, 340, 310);
//		setIconImage(miIcono);	
		setTitle("Editar Cliente");
		addInformacion(false);

		userID = determinaUsuario(usuarioModificar);

		setResizable(false);

	}

	public EditarCliente(String usuarioModificar, boolean cliente) {
		// TODO Auto-generated constructor stub
		setBounds(alturaPantalla / 2, anchoPantalla / 4, 340, 310);
//		setIconImage(miIcono);	
		setTitle("Mi Perfil");
		addInformacion(cliente);

		userID = determinaUsuario(usuarioModificar);
		// userID = usuarioModificar;
		setResizable(false);
	}

	@SuppressWarnings("unchecked")
	private String determinaUsuario(String usuarioModificar) {
		int i = 0;
		String user_id = null;
		try {
			String sql = "Select * from DATOS where user_id = ?";
			con = DBConfig.connectDB();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, usuarioModificar.toLowerCase());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) // userid found
			{
				user_id = rs.getString("user_id");
				System.out.printf("\n MI USUARIO-> %s", user_id);
				jtfNombre.setText(rs.getString("nombre"));
				jtfApellido.setText(rs.getString("apellido"));
				jtfUsuario.setText(rs.getString("user_id"));
				jtfNumCuenta.setText("" + rs.getString("cuenta"));
				jtfSaldoInicial.setText("" + rs.getString("saldo"));
				encontrado = true;
			} else // user not found
				System.out.println("Invalid UserId");
			stmt.close();

			String sqlUser = "Select * from USUARIOS where user_id = ?";
			PreparedStatement stmt1 = con.prepareStatement(sqlUser);
			stmt1.setString(1, usuarioModificar.toLowerCase());
			ResultSet rs1 = stmt1.executeQuery();

			if (rs1.next()) // userid found
			{
				System.out.printf("\n MI USUARIO-> %s", rs1.getString("user_id"));
				jtfContrasena.setText(rs1.getString("password"));
				encontrado = true;

			} else
				System.out.println("Invalid UserId");
			stmt1.close();

		} catch (SQLException e) {
			System.out.println(e);
		}

		try {

			/*
			 * ObjectInputStream leer_fichero = new ObjectInputStream(new
			 * FileInputStream("clientesBaseDatos.txt"));
			 * 
			 * ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>)
			 * leer_fichero.readObject();
			 * 
			 * leer_fichero.close();
			 * 
			 * ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			 * 
			 * personal_Recuperado.toArray(listaNueva);
			 * 
			 * for(ClientesDB e: listaNueva){ if(e.getUsuario().equals(usuarioModificar)){
			 * jtfNombre.setText(e.getNombre()); jtfApellido.setText(e.getApellido());
			 * jtfUsuario.setText(e.getUsuario()); jtfContrasena.setText(e.getContrasena());
			 * jtfSaldoInicial.setText(""+e.getSaldoInicial());
			 * jtfNumCuenta.setText(""+e.getNumCuenta()); encontrado = true; break; } i++;
			 * 
			 * }
			 */

			if (!encontrado) {
				JOptionPane.showMessageDialog(this, "Usuario no encontrado");
//				dispose();					
			} else {
				setVisible(true);
			}

		} catch (Exception e1) {

		}
		return user_id;
	}

	private void addInformacion(boolean cliente) {
		jpInformacion = new JPanel(new GridLayout(7, 1));
		jpDatos = new JPanel(new GridLayout(7, 1, 10, 13));
		jpBotones = new JPanel();
		jpBtMsj = new JPanel(new BorderLayout());

		jbAdd = new JButton("Editar");
		jbCancelar = new JButton("Cancelar");
		jbCerrar = new JButton("Cerrar");

		jbAdd.addActionListener(this);
		jbCancelar.addActionListener(this);
		jbCerrar.addActionListener(this);

		jlMensaje = new JLabel("Se han agregado las modificaciones Satisfactoriamente!!...");

		jlNombre = new JLabel(" Nombre: ");
		jlApellido = new JLabel(" Apellido: ");
		jlUsuario = new JLabel(" Usuario: ");
		jlNumCuenta = new JLabel(" Numero de Cuenta: ");
		jlSaldoInicial = new JLabel(" Saldo Inicial: ");
		jlContrasena = new JLabel(" Contrasena: ");
		jlTipoCuenta = new JLabel(" Tipo Cuenta: ");

		jtfNombre = new JTextField(15);
		jtfApellido = new JTextField(15);
		jtfUsuario = new JTextField(15);
		jtfNumCuenta = new JTextField(15);
		jtfSaldoInicial = new JTextField(12);
		jtfContrasena = new JTextField(12);
		// ------------------JComboBox-----------------
		jcbTipoCuenta = new JComboBox<String>();

		jcbTipoCuenta.addItem("Ahorro");
		jcbTipoCuenta.addItem("Corriente");
		// ---------------------------------------------

//		------------------------Zona de seteo--------------------

		jpInformacion.add(jlNombre);
		jpInformacion.add(jlApellido);
		jpInformacion.add(jlUsuario);
		jpInformacion.add(jlNumCuenta);
		jpInformacion.add(jlSaldoInicial);
		jpInformacion.add(jlContrasena);
		jpInformacion.add(jlTipoCuenta);

		jpDatos.add(jtfNombre);
		jpDatos.add(jtfApellido);
		jpDatos.add(jtfUsuario);
		jpDatos.add(jtfNumCuenta);
		jpDatos.add(jtfSaldoInicial);
		jpDatos.add(jtfContrasena);
		jpDatos.add(jcbTipoCuenta);

		if (cliente) {
			jtfNumCuenta.setEditable(false);
			jtfSaldoInicial.setEditable(false);
			jcbTipoCuenta.setEditable(false);
		}

		jpBotones.add(jbAdd);
		jpBotones.add(jbCancelar);

		jpBtMsj.add(jpBotones, BorderLayout.SOUTH);

		add(jpBtMsj, BorderLayout.SOUTH);
		add(jpDatos, BorderLayout.EAST);
		add(jpInformacion, BorderLayout.WEST);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (jbAdd == e.getSource()) {

			// -------Captura de los datos introducidos---------------
			nombre = jtfNombre.getText();
			apellido = jtfApellido.getText();
			usuario = jtfUsuario.getText();

			// ------------------Datos int y double---------------------
//				numCuenta = Integer.parseInt(jtfNumCuenta.getText());
			numCuenta = jtfNumCuenta.getText();
			saldoInicial = Double.parseDouble(jtfSaldoInicial.getText());
			// ----------------------------------------------------------
			contrasena = jtfContrasena.getText();
			tipoCuenta = (String) jcbTipoCuenta.getSelectedItem();

			// Editar Base de Datos
			// reemplazar(index, nombre, apellido, usuario, numCuenta, saldoInicial,
			// contrasena, tipoCuenta);
			agregandoClientes(userID, nombre, apellido, usuario, numCuenta, saldoInicial, contrasena, tipoCuenta);
			jpBotones.removeAll(); // remuevo los componentes anteriores
			jpBotones.add(jbCerrar); // agrego el un boton nuevo de cerrar
			setSize(340, 313); // seteo el tamano del marco (para hacer un refresh)

			jtfNombre.setEditable(false);
			jtfApellido.setEditable(false);
			jtfUsuario.setEditable(false);
			jtfNumCuenta.setEditable(false);
			jtfSaldoInicial.setEditable(false);
			jtfContrasena.setEditable(false);
//				System.out.println(jcbTipoCuenta.getSelectedItem());
			jlMensaje.setHorizontalAlignment(JLabel.CENTER);
			jlMensaje.setForeground(Color.GREEN.darker().darker().darker());
			jpBtMsj.add(jlMensaje, BorderLayout.CENTER);

		} else if (jbCancelar == e.getSource() || jbCerrar == e.getSource()) {
			dispose();
		}

//			String indicacion = 
//					"El Cliente "+nombre+" "+apellido+" con N� de cuenta "+numCuenta+" tipo "+tipoCuenta+
//					" pose un Saldo Inicial de B/. "+saldoInicial+"\n con las Credenciales de acceso "+
//					"usuario: "+usuario+" y contrase�a: "+contrasena;
//			JOptionPane.showMessageDialog(this, indicacion);
	}

	private void agregandoClientes(String user_id, String nombre, String apellido, String usuario, String numCuenta,
			double saldoInicial, String contrasena, String tipoCuenta) {
		System.out.printf("user_id---> %s", user_id);
		try {
//			String sqlInsertUsers = "INSERT INTO USUARIOS (user_id, password, rol) values (?,?,?)";
			String sqlInsertUsers = "UPDATE USUARIOS SET password = ? WHERE user_id = ?";
			con = DBConfig.connectDB();
			String user = usuario.toLowerCase();
			PreparedStatement stmtUser = con.prepareStatement(sqlInsertUsers);
			stmtUser.setString(1, contrasena);
			stmtUser.setString(2, user);
			stmtUser.executeUpdate();
			stmtUser.close();

			String sqlInsertData = "UPDATE DATOS SET nombre = ?, apellido = ?, cuenta = ?, saldo = ?, tipo_cuenta = ? WHERE user_id = ?";
			PreparedStatement stmtData = con.prepareStatement(sqlInsertData);
			stmtData.setString(1, nombre);
			stmtData.setString(2, apellido);
			stmtData.setString(3, numCuenta);
			stmtData.setDouble(4, saldoInicial);
			stmtData.setString(5, tipoCuenta);
			stmtData.setString(6, user);
			stmtData.executeUpdate();
			stmtData.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	/*
	 * @SuppressWarnings("unchecked") private void reemplazar(int index, String
	 * nombre, String apellido, String usuario, String numCuenta, double
	 * saldoInicial, String contrasena, String tipoCuenta) { try { ObjectInputStream
	 * leer_fichero = new ObjectInputStream(new
	 * FileInputStream("clientesBaseDatos.txt")); ArrayList<ClientesDB[]>
	 * personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
	 * leer_fichero.close();
	 * 
	 * personal_Recuperado.remove(index);
	 * 
	 * ClientesDB[] listaNueva = new ClientesDB[personal_Recuperado.size()];
	 * personal_Recuperado.toArray(listaNueva);
	 * 
	 * for (ClientesDB e : listaNueva) { lista.add(e); }
	 * 
	 * lista.add(new ClientesDB(nombre, apellido, usuario, numCuenta, saldoInicial,
	 * contrasena, tipoCuenta)); } catch (Exception e1) { }
	 * 
	 * try { ObjectOutputStream escribiendo_fichero = new ObjectOutputStream( new
	 * FileOutputStream("clientesBaseDatos.txt"));
	 * escribiendo_fichero.writeObject(lista); escribiendo_fichero.close();
	 * 
	 * } catch (Exception e) { }
	 * 
	 * }
	 */
}
