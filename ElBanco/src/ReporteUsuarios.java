
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

	private static final long serialVersionUID = 28;

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
		setLayout(new BorderLayout());
		cargarTitulosColumas();
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
			while (rs.next()) { 
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
			jtDatos = new JTable(tbBidimencional, titulo);
			datos = new JScrollPane(jtDatos);

			// -----------------------Acomodo y detalles en particular----------------------
			
			jtTitulo.setEnabled(false);
			jtDatos.setEnabled(false);

			jpTablas.add(datos, BorderLayout.CENTER);
		} catch (Exception e) {
			System.out.println("fichero no encontrado");
		}

		add(jpTablas, BorderLayout.CENTER);

	}

}
