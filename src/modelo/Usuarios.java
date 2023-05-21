package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Factory.ConexionDB;

public class Usuarios {

	private String nombre;
	
	private String contrasena;
	
	public Usuarios(String nombre, String contrasena) {
		this.nombre = nombre;
		this.contrasena = contrasena;
	}	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public static boolean validarUsuario(String nombre, String contrasena) {
		ConexionDB con = new ConexionDB();
		Connection conec = null;
		PreparedStatement state = null;
		ResultSet result = null;
		
		try {
			conec = con.conectarDB();
			state = conec.prepareStatement("SELECT * FROM usuarios WHERE nombre=? AND contrasena=?");
			state.setString(1, nombre);
			state.setString(2, contrasena);
			result = state.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(result != null)
					result.close();
				if(state != null)
					state.close();
				if(conec != null)
					conec.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
