package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Huesped;
import modelo.Reservas;

public class HuespedDao {
	
	private  Connection con;

	public HuespedDao(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Huesped huesped) {
		
		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva)"
					+ "VALUES (?,?,?,?,?,?)";
			try(PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pstm.setString(1, huesped.getNombre());
				pstm.setString(2, huesped.getApellido());
				pstm.setObject(3, huesped.getFechaNacimiento());
				pstm.setString(4, huesped.getNacionalidad());
				pstm.setString(5, huesped.getTelefono());
				pstm.setInt(6, huesped.getIdReserva());
				pstm.execute();
				
				try(ResultSet rst = pstm.getGeneratedKeys()){					
					while (rst.next()) {
						huesped.setId(rst.getInt(1));
					}
				}
			}
		
		} catch (SQLException e) {
			throw new RuntimeException("Aqui el error" + e.getMessage(),e);
		}
	}
	
	public List<Huesped> mostrar() {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";
			
			
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.execute();
				
				transformarResultado(huespedes, pstm);				
				
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error" + e.getMessage(),e);
		}
	}
	
	public List<Huesped> buscarId(String id) {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";
			
			
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();				
				transformarResultado(huespedes, pstm);				
				
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error" + e.getMessage(),e);
		}
	}
	
	private void transformarResultado(List<Huesped> huespedes, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.executeQuery()){
			while(rst.next()) {
				int id = rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");
				LocalDate fechaNacimiento = rst.getDate("fecha_nacimiento").toLocalDate().plusDays(1);
				String nacionalidad = rst.getString("nacionalidad");
				String telefono = rst.getString("telefono");
				int idReserva = rst.getInt("id_reserva");
				
				Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes.add(huesped);
			}
		}
		
	}
	
	public void actualizaHuesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		try(PreparedStatement pstm = con.prepareStatement("UPDATE huespedes SET nombre=?, apellido=?,fecha_nacimiento=?,nacionalidad=?, "
				+ "telefono=?, id_reserva=? WHERE id=?")){
			
			pstm.setObject(1, nombre);
			pstm.setObject(2, apellido);
			pstm.setObject(3, java.sql.Date.valueOf(fechaNacimiento));
			pstm.setString(4, nacionalidad);
			pstm.setString(5, telefono);
			pstm.setInt(6, idReserva);
			pstm.setInt(7, id);
			pstm.execute();	
						
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error update" + e.getMessage(),e);
		}
	}
	
	public void eliminarHuesped(Integer id) {
		
		try(PreparedStatement pstm = con.prepareStatement("DELETE FROM huespedes WHERE id=?")){			
			pstm.setInt(1, id);
			pstm.execute();	
						
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error delete" + e.getMessage(),e);
		}
	}

	
}
