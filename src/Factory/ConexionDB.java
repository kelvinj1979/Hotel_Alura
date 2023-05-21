package Factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConexionDB {
	
	public DataSource dataSou;
	
	public ConexionDB( ) {
		ComboPooledDataSource comboPool = new ComboPooledDataSource();
		comboPool.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		comboPool.setUser("root");
		comboPool.setPassword("Kemi.com@1");
		
		this.dataSou = comboPool;
	}
	
	public Connection conectarDB() {
		
		try {
			return this.dataSou.getConnection();
		} catch (SQLException e) {
			System.out.println("Hubo un error en la conexion");
			throw new RuntimeException(e);
		}
	}

}
