package ar.com.codoacodo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.com.codoacodo.entity.Orador;

public class MySqlOradorRepository implements OradorRepository {

	@Override
	public void save(Orador orador) {
		String sql = "insert into oradores (nombre,apellido,mail,tema,fecha_alta) values (?,?,?,?,?)";
		LocalDateTime localDateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		
		Connection conn = AdministradorDeConexiones.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, orador.getNombre());
			statement.setString(2, orador.getApellido());
			statement.setString(3, orador.getMail());
			statement.setString(4, orador.getTema());
			statement.setTimestamp(5, timestamp);
			
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			if(res.next()) {
			Long id = res.getLong(1);
			orador.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public Orador getById(Long id) {

		String sql = "select id_orador,nombre,apellido,mail,tema,fecha_alta from oradores where id_orador = ?";
		Connection conn = AdministradorDeConexiones.getConnection();
		Orador orador = null;
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultado = statement.executeQuery();
			
			
			if(resultado.next()) {
				Long _id = resultado.getLong(1);
				String nombre = resultado.getString(2);
				String apellido = resultado.getString(3);
				String mail = resultado.getString(4);
				String tema = resultado.getString(5);
				LocalDateTime fecha_alta = resultado.getTimestamp(6).toLocalDateTime();
				
				orador =  new Orador(_id, nombre, apellido, mail, tema, fecha_alta);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orador;		
		
	}

	@Override
	public void update(Orador orador) {
		
		String sql = "update oradores set nombre=?,apellido=?,mail=?,tema=? where id_orador = ?";
		Connection conn = AdministradorDeConexiones.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, orador.getNombre());
			statement.setString(2, orador.getApellido());
			statement.setString(3, orador.getMail());
			statement.setString(4, orador.getTema());
			statement.setLong(5, orador.getId());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		



	}

	@Override
	public void delete(Long id) {
		String sql = "delete from oradores where id_orador = ?";
		Connection conn = AdministradorDeConexiones.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public List<Orador> getAll() {

		String sql = "select id_orador,nombre,apellido,mail,tema,fecha_alta from oradores";
		Connection conn = AdministradorDeConexiones.getConnection();
		List<Orador> oradores = new ArrayList<>();
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);			
			ResultSet resultado = statement.executeQuery();			
			
			while(resultado.next()) {
				Long _id = resultado.getLong(1);
				String nombre = resultado.getString(2);
				String apellido = resultado.getString(3);
				String mail = resultado.getString(4);
				String tema = resultado.getString(5);
				LocalDateTime fecha_alta = resultado.getTimestamp(6).toLocalDateTime();
				
				oradores.add(new Orador(_id, nombre, apellido, mail, tema, fecha_alta));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return oradores;	
	}

}
