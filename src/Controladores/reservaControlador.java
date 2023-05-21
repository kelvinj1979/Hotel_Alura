package Controladores;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.ReservaDAO;
import Factory.ConexionDB;
import modelo.Reservas;

public class reservaControlador {
	
	private ReservaDAO reservaD;

	public reservaControlador() {
		Connection con = new  ConexionDB().conectarDB();
		this.reservaD = new ReservaDAO(con);
	}
	
	public void guardar(Reservas reserva) {
		this.reservaD.guardar(reserva);
	}
	
	public List<Reservas> mostrar(){
		return this.reservaD.mostrar();
	}
	
	public List<Reservas> buscar(String id){
		return this.reservaD.buscarId(id);
	}
	
	public void actualizarReserva(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		this.reservaD.Actualizar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
	
	public void eliminaReserva(Integer id) {
		this.reservaD.Eliminar(id);
	}

}
