package Controladores;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.HuespedDao;
import Factory.ConexionDB;
import modelo.Huesped;

public class huespedControlador {
	
	private HuespedDao huespedDao;
	
	public huespedControlador() {
		Connection con = new ConexionDB().conectarDB();
		this.huespedDao = new HuespedDao(con);
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDao.guardar(huesped);
	}
	
	public List<Huesped> mostrarHuesped(){
		return this.huespedDao.mostrar();
	}
	
	public List<Huesped> buscarHuesped(String id){
		return this.huespedDao.buscarId(id);
	}
	
	public void actualizaHuesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		this.huespedDao.actualizaHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void eliminarHuesped(Integer id) {
		this.huespedDao.eliminarHuesped(id);
	}

	


}
