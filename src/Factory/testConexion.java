package Factory;

import java.sql.Connection;
import java.sql.SQLException;

public class testConexion {
	
	public static void main(String[] args) throws SQLException {
		ConexionDB con = new ConexionDB();
		Connection conectar = con.conectarDB();
		
		System.out.println("Conexion Exitosa");
		conectar.close();
		
		System.out.println("Cerro con exito");
	}

}
