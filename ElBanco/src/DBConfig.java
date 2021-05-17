import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
	  Connection con = null;
	public static Connection connectDB(){
		
	//Ubicación de la base de dato 
	String database= "C:\\Users\\opc\\Documents\\DATOS.accdb";
	try {
			//Estableciendo conexión
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://"+database);

			if(con!=null){
				System.out.println("Connection Database Successful!");
			}
			return con;
			
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("ERROR"+e.getMessage());
	}
	return null;
	
}
}
