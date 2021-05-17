

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
		// TODO Auto-generated constructor stub
		determinaUsuario(usuarioEliminar);
	}	
	
	@SuppressWarnings("unchecked")
	private void determinaUsuario(String usuarioModificar){
		try {
			
			String sql = "DELETE From USUARIOS where user_id = ?";
			System.out.println("Registro Eliminado");
			con = DBConfig.connectDB();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, usuarioModificar);
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
			
		
			/*
			
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();	
			leer_fichero.close();
			
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			
			personal_Recuperado.toArray(listaNueva);
			
			for(ClientesDB e: listaNueva){
				if(e.getUsuario().equals(usuarioModificar)){
					encontrado = true;
					break; 
				}
				i++;
			}
			if(!encontrado){
				JOptionPane.showMessageDialog(new JFrame(), "Usuario no encontrado");	
				
			}else{
				reemplazar(i);
	
			}
			
			*/
		
		}catch (Exception e1) {
			System.out.println("error al eliminar" + e1.toString());

			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void reemplazar(int index){
		try {
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
			leer_fichero.close();
			
			personal_Recuperado.remove(index);
			
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			personal_Recuperado.toArray(listaNueva);
			
			for(ClientesDB e: listaNueva){
				lista.add(e);
			}
			

		}catch (Exception e1) {	}
		
		try{	
			ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("clientesBaseDatos.txt"));
			escribiendo_fichero.writeObject(lista);
			escribiendo_fichero.close();
			
		}catch(Exception e){ }	
		
	}
	
}
