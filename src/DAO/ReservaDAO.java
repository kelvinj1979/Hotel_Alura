package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reservas;

public class ReservaDAO {
	
	private Connection con;

	public ReservaDAO(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Reservas reservas) {
		try {
			String sql = "INSERT INTO reservas(fecha_entrada,fecha_salida,valor,forma_pago)"
					+ " VALUES (?,?,?,?)";
			try(PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)){
				
				pstm.setObject(1, reservas.getFechaEntrada());
				pstm.setObject(2, reservas.getFechaSalida());
				pstm.setString(3, reservas.getValor());
				pstm.setString(4, reservas.getFormaPago());
				pstm.executeUpdate();
				
				try(ResultSet rst = pstm.getGeneratedKeys()){
					while(rst.next()) {
						reservas.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error" + e.getMessage(),e);
		}
	}
	
	public List<Reservas> mostrar() {
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor ,forma_pago FROM reservas";
			
			
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.execute();
				
				transformarResultado(reservas, pstm);				
				
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error" + e.getMessage(),e);
		}
	}
	
	public List<Reservas> buscarId(String id) {
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor ,forma_pago FROM reservas WHERE id= ?";
			
			
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();
				
				transformarResultado(reservas, pstm);				
				
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error" + e.getMessage(),e);
		}
	}
	
	public void Actualizar(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		
		try(PreparedStatement pstm = con.prepareStatement("UPDATE reservas SET fecha_entrada=?,fecha_salida=?,valor=?, "
				+ "forma_pago=? WHERE id=?")){
			
			pstm.setObject(1, java.sql.Date.valueOf(fechaEntrada));
			pstm.setObject(2, java.sql.Date.valueOf(fechaSalida));
			pstm.setString(3, valor);
			pstm.setString(4, formaPago);
			pstm.setInt(5, id);
			pstm.execute();	
						
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error update" + e.getMessage(),e);
		}
	}
	
	public void Eliminar(Integer id) {
		
		try(PreparedStatement pstm = con.prepareStatement("DELETE FROM reservas WHERE id=?")){			
			pstm.setInt(1, id);
			pstm.execute();	
						
		} catch (SQLException e) {
			throw new RuntimeException("aqui el error delete" + e.getMessage(),e);
		}
	}

	
	private void transformarResultado(List<Reservas> reservas, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.getResultSet()){
			while(rst.next()) {
				int id = rst.getInt("id");
				LocalDate fechaE = rst.getDate("fecha_entrada").toLocalDate().plusDays(1);
				LocalDate fechaS = rst.getDate("fecha_salida").toLocalDate().plusDays(1);
				String valor = rst.getString("valor");
				String formaPago = rst.getString("forma_pago");
				
				Reservas producto = new Reservas(id, fechaE, fechaS, valor, formaPago);
				reservas.add(producto);
			}
		}
		
	}

}
