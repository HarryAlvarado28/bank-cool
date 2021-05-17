

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarClientes{

	ArrayList <ClientesDB> lista = new ArrayList<ClientesDB>();
	private boolean encontrado = false;
	int i = 0;
	private PreparedStatement pstmt=null;
	private ResultSet rs;
	private Connection con;

	public EliminarClientes(String usuarioEliminar){
		determinaUsuario(usuarioEliminar);
	}	
	
	@SuppressWarnings("unchecked")
	private void determinaUsuario(String usuarioModificar){
		try {
			
			String sqlUsuarios = "DELETE From USUARIOS where user_id = ?";
			String sqlDatos = "DELETE From DATOS where user_id = ?";
			System.out.println("Registro Eliminado");
			con = DBConfig.connectDB();
			PreparedStatement stmt = con.prepareStatement(sqlUsuarios);
			PreparedStatement stmt1 = con.prepareStatement(sqlDatos);
			stmt.setString(1, usuarioModificar);
			stmt.executeUpdate();
			stmt1.setString(1, usuarioModificar);
			stmt1.executeUpdate();
			stmt.close();
			stmt1.close();
			con.close();
			
		
		}catch (Exception e1) {
			System.out.println("error al eliminar" + e1.toString());

			
		}
		
	}	
}
