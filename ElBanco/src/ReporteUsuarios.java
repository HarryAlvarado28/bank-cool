
import java.awt.BorderLayout;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReporteUsuarios extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ReporteUsuarios ur = new ReporteUsuarios();
//		ur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

//	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");
//	private Dimension tamanoPantalla = mipantalla.getScreenSize();
//	private int alturaPantalla = tamanoPantalla.height;
//	private int anchoPantalla = tamanoPantalla.width;
	private JTable jTable1;
	private Connection con = null;
	private Statement sentencia;

	DefaultTableModel tabla = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int Fila, int Colum) {
			return false;
		}
	};

	public ReporteUsuarios() {
//		super("Reporte de usuario");
//		setBounds(alturaPantalla/2,anchoPantalla/4,550,250);
//		setIconImage(miIcono);

		setLayout(new BorderLayout());
		cargarTitulosColumas();
		// cargarDatos();

		addTablaClientes();

	}

	public void cargarTitulosColumas() {
		JTable jtTitulo = new JTable(1, 7);
		tabla.addColumn("Nombre");
		tabla.addColumn("Apellido");
		tabla.addColumn("Usuario");
		tabla.addColumn("Numero De Cuenta");
		tabla.addColumn("Saldo");
		tabla.addColumn("Contrasena");
		tabla.addColumn("Tipo de Cuenta");
		tabla.addColumn("Comentario");
		jtTitulo.setModel(tabla);
	}

	String listaDatos[];

	/*
	 * public void cargarDatos() {
	 * 
	 * listaDatos = new String[15]; // Variable que almacena los datos de la
	 * consulta String sql = "SELECT * FROM DATOS"; try { con =
	 * DBConfig.connectDB(); PreparedStatement stmtUser = con.prepareStatement(sql);
	 * ResultSet rs = stmtUser.executeQuery(); while (rs.next()) { // Bucle que
	 * recorre la consulta obtenida listaDatos[0] = rs.getString("user_id");
	 * listaDatos[1] = rs.getString("nombre"); listaDatos[2] =
	 * rs.getString("apellido"); listaDatos[3] = rs.getString("cuenta");
	 * listaDatos[4] = rs.getString("Saldo"); listaDatos[5] =
	 * rs.getString("tipo_cuenta"); listaDatos[6] = rs.getString("comentario");
	 * System.out.print(listaDatos[0]); tabla.addRow(listaDatos); } } catch
	 * (SQLException ex) { JOptionPane.showMessageDialog(null,
	 * "Error al cargar los Datos\n" + ex); } }
	 */
	@SuppressWarnings("unchecked")
	private void addTablaClientes() {
		// cargarDatos();
		int f = 0, s = 0;

		JPanel jpTablas = new JPanel(new BorderLayout());

		JTable jtTitulo = new JTable(1, 7);

		JTable jtDatos = null;// = new JTable(9,6);

		JScrollPane datos = null;// = new JScrollPane(jtDatos);

		String[] titulo = { "Nombre", "Apellido", "Usuario", "N. Cuenta", "Saldo", "Tipo Cuenta" };

		// ------------recolectando datos---------------
		Object[][] tbBidimencional = null;
		listaDatos = new String[15]; // Variable que almacena los datos de la consulta
		String sql = "SELECT * FROM DATOS";
		try {
			con = DBConfig.connectDB();
			PreparedStatement stmtUser = con.prepareStatement(sql);
			ResultSet rs = stmtUser.executeQuery();

			s = 20;
			jtDatos = new JTable(s, 7);
			datos = new JScrollPane(jtDatos);
			// s = personal_Recuperado.size();
			tbBidimencional = new Object[s][7];
			while (rs.next()) { // Bucle que recorre la consulta obtenida
				// s++;
				/*
				 * tbBidimencional[f][0] = rs.getString("user_id"); listaDatos[1] =
				 * rs.getString("nombre"); listaDatos[2] = rs.getString("apellido");
				 * listaDatos[3] = rs.getString("cuenta"); listaDatos[4] =
				 * rs.getString("Saldo"); listaDatos[5] = rs.getString("tipo_cuenta");
				 * listaDatos[6] = rs.getString("comentario");
				 */
				tbBidimencional[f][0] = rs.getString("nombre");
				tbBidimencional[f][1] = rs.getString("apellido");
				tbBidimencional[f][2] = rs.getString("user_id");
				tbBidimencional[f][3] = rs.getString("cuenta");
				tbBidimencional[f][4] = rs.getString("Saldo");
				tbBidimencional[f][5] = rs.getString("tipo_cuenta");
				System.out.printf("\ntbBidimencional[f][0]--> %s",tbBidimencional[f][0]);
				tabla.addRow(listaDatos);
				f++;
			}
			System.out.printf("\\n-----:: %d", listaDatos.length);
			/*
			 * for(ClientesDB e: listaNueva){
			 * 
			 * tbBidimencional[f][0] = e.getNombre(); tbBidimencional[f][1] =
			 * e.getApellido(); tbBidimencional[f][2] = e.getUsuario();
			 * tbBidimencional[f][3] = e.getNumCuenta(); tbBidimencional[f][4] =
			 * e.getSaldoInicial(); tbBidimencional[f][5] = e.getContrasena();
			 * tbBidimencional[f][6] = e.getTipoCuenta();
			 * 
			 * // jtDatos.setValueAt(e.getNombre(), f, 0); //
			 * jtDatos.setValueAt(e.getApellido(), f, 1); //
			 * jtDatos.setValueAt(e.getUsuario(), f, 2); //
			 * jtDatos.setValueAt(e.getNumCuenta(), f, 3); //
			 * jtDatos.setValueAt(e.getSaldoInicial(), f, 4); //
			 * jtDatos.setValueAt(e.getContrasena(), f, 5); //
			 * jtDatos.setValueAt(e.getTipoCuenta(), f, 6); f++; }
			 */

			/*
			 * for (String e : listaDatos) { System.out.println(e); tbBidimencional[f][0] =
			 * "Nombre"; tbBidimencional[f][1] = "Nombre"; tbBidimencional[f][2] = "Nombre";
			 * tbBidimencional[f][3] = "Nombre"; tbBidimencional[f][4] = e;
			 * tbBidimencional[f][5] = e; tbBidimencional[f][6] = e;
			 * 
			 * // jtDatos.setValueAt(e.getNombre(), f, 0); //
			 * jtDatos.setValueAt(e.getApellido(), f, 1); //
			 * jtDatos.setValueAt(e.getUsuario(), f, 2); //
			 * jtDatos.setValueAt(e.getNumCuenta(), f, 3); //
			 * jtDatos.setValueAt(e.getSaldoInicial(), f, 4); //
			 * jtDatos.setValueAt(e.getContrasena(), f, 5); //
			 * jtDatos.setValueAt(e.getTipoCuenta(), f, 6); f++; }
			 */

			jtDatos = new JTable(tbBidimencional, titulo);
			datos = new JScrollPane(jtDatos);

			// -----------------------Acomodo y detalles en particular----------------------
			jtTitulo.setEnabled(false);
			jtDatos.setEnabled(false);

			jpTablas.add(datos, BorderLayout.CENTER);
		} catch (Exception e) {
			System.out.println("fichero no encontrado");
		}

//		jpTablas.add(jtTitulo,BorderLayout.NORTH);
//		jpTablas.add(datos,BorderLayout.CENTER);
//		jpTablas.add(jthTitulo, BorderLayout.SOUTH);

		add(jpTablas, BorderLayout.CENTER);

	}

}
