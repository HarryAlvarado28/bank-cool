

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

public class ReporteUsuarios extends JPanel{



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
	 private Connection con=null;
	 private Statement sentencia;


	DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };


	
	
	public ReporteUsuarios(){
//		super("Reporte de usuario");
//		setBounds(alturaPantalla/2,anchoPantalla/4,550,250);
//		setIconImage(miIcono);

		setLayout(new BorderLayout());
		//cargarTitulosColumas();
		//cargarDatos();


		addTablaClientes();
		
	}
	
/*
	
	public void cargarTitulosColumas(){
		JTable jtTitulo = new JTable(1,7);
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
	
	
	
    public void cargarDatos() {

        String datos[] = new String[7];    //Variable que almacena los datos de la consulta
        String sql = "SELECT * FROM DATOS";
        try {
            con = DBConfig.connectDB();
      		PreparedStatement stmtUser = con.prepareStatement(sql);
			ResultSet rs = stmtUser.executeQuery();

            while (rs.next()) {                                    //Bucle que recorre la consulta obtenida
                datos[0] = rs.getString("user_id");
                datos[1] = rs.getString("nombre");
                datos[2] = rs.getString("apellido");
                datos[3] = rs.getString("cuenta");
                datos[4] = rs.getString("Saldo");
                datos[5] = rs.getString("tipo_cuenta");
                datos[6] = rs.getString("comentario");
                tabla.addRow(datos);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
        }
    }
    
    */
   
	
	
	
	@SuppressWarnings("unchecked")
	private void addTablaClientes(){
		int f =0,s =0;
		
		JPanel jpTablas = new JPanel(new BorderLayout());
		
		JTable jtTitulo = new JTable(1,7);
		
		JTable jtDatos = null;// = new JTable(9,6);

		JScrollPane datos = null;//= new JScrollPane(jtDatos);	
		
		String[] titulo = {"Nombre","Apellido","Usuario","N. Cuenta","Saldo","Contrasena","Tipo Cuenta"};
		
	
		//------------recolectando datos---------------
		Object[][] tbBidimencional = null;
		try{
			/*
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
			
			leer_fichero.close();
			
			s = personal_Recuperado.size();
//			jtDatos = new JTable(s,7);
//			datos = new JScrollPane(jtDatos);	
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			personal_Recuperado.toArray(listaNueva);
			*/
			
			
			//s = personal_Recuperado.size();
			tbBidimencional = new Object[s][7];
			
			
			
			
			
			
			/*
			for(ClientesDB e: listaNueva){
				
				tbBidimencional[f][0] = e.getNombre();
				tbBidimencional[f][1] = e.getApellido();
				tbBidimencional[f][2] = e.getUsuario();
				tbBidimencional[f][3] = e.getNumCuenta();
				tbBidimencional[f][4] = e.getSaldoInicial();
				tbBidimencional[f][5] = e.getContrasena();
				tbBidimencional[f][6] = e.getTipoCuenta();
				
//				jtDatos.setValueAt(e.getNombre(), f, 0);
//				jtDatos.setValueAt(e.getApellido(), f, 1);
//				jtDatos.setValueAt(e.getUsuario(), f, 2);
//				jtDatos.setValueAt(e.getNumCuenta(), f, 3);
//				jtDatos.setValueAt(e.getSaldoInicial(), f, 4);
//				jtDatos.setValueAt(e.getContrasena(), f, 5);
//				jtDatos.setValueAt(e.getTipoCuenta(), f, 6);
				f++;
			}
			*/
			jtDatos = new JTable(tbBidimencional,titulo);
			datos = new JScrollPane(jtDatos);

			//-----------------------Acomodo y detalles en particular----------------------
			jtTitulo.setEnabled(false);
			jtDatos.setEnabled(false);
			
			
			jpTablas.add(datos,BorderLayout.CENTER);
		}catch(Exception e){
//			System.out.println("fichero no encontrado");
		}		
		
//		jpTablas.add(jtTitulo,BorderLayout.NORTH);
//		jpTablas.add(datos,BorderLayout.CENTER);
//		jpTablas.add(jthTitulo, BorderLayout.SOUTH);
		
		add(jpTablas,BorderLayout.CENTER);
		
	}
	
	
	
	

	
}

