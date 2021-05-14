import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
	  Connection con = null;
	public static Connection connectDB(){
		
	//location where database is present in the system
	String database= "C:\\Users\\opc\\Documents\\DATOS.accdb";
	try {
			//establishing connection
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
